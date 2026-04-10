package com.quoc.Movie_Ticket_Booking.service;

import com.quoc.Movie_Ticket_Booking.dto.request.PhongChieuRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.SuatChieuRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.SuatChieuResponseDto;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.PhongChieu;
import com.quoc.Movie_Ticket_Booking.model.SuatChieu;

import java.util.List;
import java.util.Map;

public interface SuatChieuService {

    public ApiResponse<?> createSuatChieu(SuatChieuRequestDto suatChieuRequestDto, NhanVien nhanvienId);

    public  ApiResponse<?> updateSuatChieu(Long id, SuatChieuRequestDto updateDto, NhanVien nhanvienId);

    public ApiResponse<?> deleteSuatChieu(Long id, NhanVien nhanvienId);

    public ApiResponse<?> updateStatus(Long id, NhanVien nhanvienId);

    public List<SuatChieuResponseDto> getAllSuatChieu();

    public SuatChieu getSuatChieuById(Long id);

    public Map<String, Object> getChiTietPhimAndSuatChieu(Long id);

    public ApiResponse<?> createVeAuto(SuatChieuResponseDto dto, NhanVien nhanvienId);

    public List<SuatChieu> getSuatChieuByPhimId(Long id);



}
