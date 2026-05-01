package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.DonHangDetailsResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.DonHangForQRcodeResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.VeDetailsResponseDto;
import com.quoc.Movie_Ticket_Booking.model.DonHang;
import com.quoc.Movie_Ticket_Booking.repository.DonHangRepository;
import com.quoc.Movie_Ticket_Booking.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CheckInServiceImpl implements CheckInService {
    @Autowired
    private DonHangRepository donHangRepository;

    @Override
    public ApiResponse checkIn(String qr) {

        DonHang donHang = donHangRepository.findByQrCode(qr).orElse(null);

        if (donHang == null) {
            return new ApiResponse(false, "Không tìm thấy đơn hàng", null);
        }

        if (!donHang.getIsThanhToan().equals(DonHang.DA_THANH_TOAN)) {
            return new ApiResponse(false, "Chưa thanh toán", null);
        }

        if (donHang.isCheckedIn()) {
            return new ApiResponse(false, "Vé Đã được check-in rồi", null);
        }

        // lưu checkin
        donHang.setCheckedIn(true);
        donHangRepository.save(donHang);

        // Lấy dữ liệu trả về 
        DonHangForQRcodeResponseDto donHangDto =
                donHangRepository.getDonHangById(donHang.getId());

        List<VeDetailsResponseDto> dsVe =
                donHangRepository.getVeByListIdDonHang(List.of(donHang.getId()));

        //  Response
        Map<String, Object> data = new HashMap<>();
        data.put("donHang", donHangDto);
        data.put("dsVe", dsVe);

        return new ApiResponse(true, "Check-in thành công", data);
    }
}
