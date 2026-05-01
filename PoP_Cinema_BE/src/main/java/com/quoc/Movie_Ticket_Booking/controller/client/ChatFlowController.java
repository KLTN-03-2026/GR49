package com.quoc.Movie_Ticket_Booking.controller.client;

import com.quoc.Movie_Ticket_Booking.dto.request.DatVeChatFlowRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ChatResponse;
import com.quoc.Movie_Ticket_Booking.model.Users;
import com.quoc.Movie_Ticket_Booking.service.ChatFlowService;
import com.quoc.Movie_Ticket_Booking.service.UsersService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/chat/flow")
public class ChatFlowController {

    private final ChatFlowService chatFlowService;

    private final UsersService usersService;

    public ChatFlowController(ChatFlowService chatFlowService, UsersService usersService) {
        this.chatFlowService = chatFlowService;
        this.usersService = usersService;
    }

    @GetMapping("/phim")
    public ChatResponse phim() {
        return chatFlowService.hienThiPhim();
    }

    @GetMapping("/suatchieu/{idPhim}")
    public ChatResponse suat(@PathVariable Long idPhim) {
        return chatFlowService.hienThiSuatChieu(idPhim);
    }

    @GetMapping("/ghe/{idSuatChieu}")
    public ChatResponse getClientAllVe(@PathVariable Long idSuatChieu,@RequestHeader(value = "Authorization", required = false) String jwt)  {

        return chatFlowService.hienThiDanhSachGhe(idSuatChieu, jwt);
    }

    @PostMapping("/dat-ve")
    public  ChatResponse datVe(@RequestBody DatVeChatFlowRequestDto dto, @RequestHeader("Authorization") String jwt)  {

        Users users= usersService.findUserByJwtToken(jwt);
        ChatResponse datVe = chatFlowService.datVe(dto, users.getId());
        return datVe;
    }
}
