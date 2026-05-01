package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.dto.response.ChatResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.PhimGoiYResponseDto;
import com.quoc.Movie_Ticket_Booking.model.Phim;
import com.quoc.Movie_Ticket_Booking.model.SuatChieu;
import com.quoc.Movie_Ticket_Booking.model.Users;
import com.quoc.Movie_Ticket_Booking.repository.DanhGiaRepository;
import com.quoc.Movie_Ticket_Booking.service.*;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {
    private final ChatClient chatClient;

    private final JdbcChatMemoryRepository jdbcChatMemoryRepository;

    private final PhimService phimService;
    private final SuatChieuService suatChieuService;
    private final GheService gheService;

    private final DonHangService donHangService;
    private final UsersService usersService;
    private final RecommendationService recommendationService;

    private final ChatFlowService chatFlowService;
    private final DanhGiaRepository danhGiaRepository;

    private Map<String, Long> userLastMovie = new HashMap<>();
    private Map<String, Long> userLastSuatChieu = new HashMap<>();
    Map<String, LocalDate> userLastDate = new HashMap<>();

    public ChatServiceImpl(ChatClient.Builder builder, JdbcChatMemoryRepository jdbcChatMemoryRepository, PhimService phimService,
                           SuatChieuService suatChieuService, GheService gheService
            , DonHangService donHangService, UsersService usersService
            , RecommendationService recommendationService, ChatFlowService chatFlowService
            , DanhGiaRepository danhGiaRepository) {

        this.phimService = phimService;
        this.suatChieuService = suatChieuService;
        this.gheService = gheService;
        this.donHangService = donHangService;
        this.usersService = usersService;
        this.recommendationService = recommendationService;
        this.chatFlowService = chatFlowService;
        this.danhGiaRepository = danhGiaRepository;

        this.jdbcChatMemoryRepository = jdbcChatMemoryRepository;

        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(jdbcChatMemoryRepository)
                .maxMessages(10)  // lưu 10 tin nhắn
                .build();

        chatClient = builder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }


    //Hàm normalize
    private String normalize(String text) {
        text = text.toLowerCase().trim();

        // bỏ dấu tiếng Việt
        text = Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // bỏ ký tự đặc biệt
        text = text.replaceAll("[^a-z0-9 ]", " ");

        // remove multiple spaces
        text = text.replaceAll("\\s+", " ").trim();

        return text;
    }

    //Hàm tính điểm match
    private int calculateScore(String msg, String movieName) {
        int score = 0;

        String[] words = movieName.split(" ");

        for (String word : words) {
            if (word.length() > 2 && msg.contains(word)) {
                score++;
            }
        }

        // bonus nếu match gần full tên
        if (msg.contains(movieName)) {
            score += 3;
        }

        return score;
    }


    private Long detectMovieId(String message) {
        String msg = normalize(message);

        return phimService.getPhimDangChieu().stream()
                .map(p -> {
                    String movieName = normalize(p.getTenPhim());
                    int score = calculateScore(msg, movieName);
                    return Map.entry(p, score);
                })
                .filter(e -> e.getValue() > 0)
                .max(Map.Entry.comparingByValue()) // chọn phim điểm cao nhất
                .map(e -> e.getKey().getId())
                .orElse(null);
    }


    @Override
    public Object chatWithStream(String message, String jwt) {
        String conversationId;
        // Nếu có JWT → lấy userId để tạo hội thoại riêng cho từng người
        Users user = null;

        try {
            if (jwt != null && !jwt.isBlank()) {
                user = usersService.findUserByJwtToken(jwt);
            }
        } catch (Exception e) {
            System.err.println("⚠️ Lỗi khi parse JWT: " + e.getMessage());
        }


        if (user != null) {
            conversationId = String.valueOf(user.getId()); // ép kiểu Long → String
        } else {
            conversationId = "guest_conversation"; // fallback cho khách vãng lai
        }
        String msg = message.toLowerCase();

        UserMessage userMessage = new UserMessage(userGreeting + message);

        Prompt prompt = new Prompt(systemMessage, userMessage);
        try {
            return chatClient
                    .prompt(prompt)
                    .advisors(advisorSpec -> advisorSpec.param(
                            ChatMemory.CONVERSATION_ID, conversationId
                    ))
                    .call()
                    .content();
        } catch (Exception e) {
            if (e.getMessage().contains("429")) {
                return "⏳ Hệ thống đang bận, vui lòng chờ vài giây rồi thử lại.";
            }

            return "❌ Có lỗi xảy ra, vui lòng thử lại sau.";
        }
    }


    private String getSystemPrompt(String message, String jwt, String conversationId) {

        StringBuilder context = new StringBuilder("""
        Bạn là PoP AI — tư vấn rạp PoP Cinema.
        - Trả lời ngắn gọn, thân thiện (2–4 câu), dùng dữ liệu hệ thống.
        - Hỏi phim, giá vé → trả lời ngay.
        - Hỏi danh sách phim → liệt kê rồi hỏi: "Bạn muốn xem suất chiếu, chi tiết hay đặt vé phim nào ạ?"
        - Suất chiếu, ghế, đặt vé → hệ thống tự xử lý, KHÔNG nhắc liên hệ nhân viên.
        - Hotline 0905923427 chỉ khi sự cố kỹ thuật.
        """);


        return context.toString();
    }

  


}
