package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.HangThanhVienResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.KhachHangResponseDto;
import com.quoc.Movie_Ticket_Booking.model.HangThanhVien;
import com.quoc.Movie_Ticket_Booking.model.Users;
import com.quoc.Movie_Ticket_Booking.repository.HangThanhVienRepository;
import com.quoc.Movie_Ticket_Booking.service.HangThanhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HangThanhVienServiceImpl implements HangThanhVienService {

    @Autowired
    private HangThanhVienRepository hangThanhVienRepository;

    @Override
    public ApiResponse<?> getThongTinHangThanVien() {
        List<HangThanhVien> all = hangThanhVienRepository.findAll();
        List<HangThanhVienResponseDto> HangThanhVienResponseDtoDtoList = all.stream()
                .map(this::mapToHangThanhVienDto)
                .toList();

        return ApiResponse.success("Hiển thị thành công!",HangThanhVienResponseDtoDtoList);
    }


    private HangThanhVienResponseDto mapToHangThanhVienDto(HangThanhVien hangThanhVien) {
        return new  HangThanhVienResponseDto(
                hangThanhVien.getTenHang(),
                hangThanhVien.getDiemToiThieu(),
                hangThanhVien.getMoTa()
        );
    }

}
