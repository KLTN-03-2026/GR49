package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.dto.response.DonHangDetailsResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.PhimGoiYResponseDto;
import com.quoc.Movie_Ticket_Booking.model.Phim;
import com.quoc.Movie_Ticket_Booking.repository.DanhGiaRepository;
import com.quoc.Movie_Ticket_Booking.repository.DonHangRepository;
import com.quoc.Movie_Ticket_Booking.repository.PhimRepository;
import com.quoc.Movie_Ticket_Booking.repository.ThongKeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
     private DonHangRepository donHangRepository;

    @Autowired
     private PhimRepository phimRepository;

    @Autowired
    private DanhGiaRepository danhGiaRepository;

    @Autowired
    private ThongKeRepository thongKeRepository;

    public List<PhimGoiYResponseDto> recommendSimple(Long userId){
        // 1. Lấy lịch sử khách đã thanh toán
        List<DonHangDetailsResponseDto> history = donHangRepository.getHistoryDonHang(userId, 1);

        // 2. Fallback nếu chưa có lịch sử
        List<Phim> dangChieu = phimRepository.findByTinhTrangInOrderByNgayPhatHanhDesc(Phim.DANG_CHIEU);

        // Fallback nếu chưa có lịch sử
        if (history.isEmpty()) {
            return dangChieu.stream()
                    .limit(3)
                    .map(this::mapToDto)
                    .toList();
        }

        // 3. Thể loại yêu thích
        String favoriteGenre = history.stream()
                .collect(Collectors.groupingBy(DonHangDetailsResponseDto::getTheLoai, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        // Fallback nếu không xác định được thể loại
        if (favoriteGenre == null) {
            return dangChieu.stream()
                    .limit(3)
                    .map(this::mapToDto)
                    .toList();
        }

        // 4. Lấy phim cùng thể loại đang chiếu hoặc sắp chiếu
        List<Phim> list = phimRepository.findTop5ByTheLoaiAndTinhTrangOrderByNgayPhatHanhDesc(favoriteGenre,Phim.DANG_CHIEU);

        // Fallback nếu không có phim cùng thể loại
        if (list.isEmpty()) {
            return dangChieu.stream()
                    .limit(3)
                    .map(this::mapToDto)
                    .toList();
        }

        // 5. Trả tối đa 3 phim, sắp xếp theo điểm trung bình giảm dần
        List<PhimGoiYResponseDto> sorted = list.stream()
                .map(this::mapToDto)
                .sorted((p1, p2) -> Double.compare(p2.getDiemTrungBinh(), p1.getDiemTrungBinh()))
                .toList();

        List<PhimGoiYResponseDto> result = new ArrayList<>();

        // lấy 2 phim điểm cao
        result.addAll(sorted.stream().limit(2).toList());

        // lấy 1 phim bất kỳ (có thể là 5.0)
        Collections.shuffle(sorted);
        result.add(sorted.get(0));

        return result;
    }

    private PhimGoiYResponseDto mapToDto(Phim p) {
        Double diem = danhGiaRepository.getDiemTrungBinh(p.getId());
        if (diem == null) diem = 0.0;
        diem = diem * 2; // thang 10
        return new PhimGoiYResponseDto(p.getId(), p.getTenPhim(), p.getTheLoai(), diem);
    }

    // Thông điệp kiểu fan cứng dựa vào tổng chi tiêu
    public String getChiTieuUser(Long userId) {
        Double chiTieu = thongKeRepository.sumThanhTienByUser(userId);
        if (chiTieu == null) chiTieu = 0.0;

        if (chiTieu >= 900000) {
            return "🎬 Wow! Bạn là fan cứng rạp phim với tổng chi tiêu " + chiTieu + " VND!";
        } else if (chiTieu >= 300000) {
            return "🍿 Bạn khá thường xuyên đi xem phim, tổng chi tiêu " + chiTieu + " VND.";
        } else if (chiTieu > 0) {
            return "🎥 Bạn mới bắt đầu khám phá rạp phim với tổng chi tiêu " + chiTieu + " VND.";
        } else {
            return "👀 Có vẻ bạn chưa chi tiêu gì trong rạp phim. Hãy xem phim cùng chúng mình nhé!";
        }
    }
}
