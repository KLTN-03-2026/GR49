package com.quoc.Movie_Ticket_Booking.service;

import com.quoc.Movie_Ticket_Booking.dto.request.ChucVuRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.GheRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.ChucVuResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.GheResponseDto;
import com.quoc.Movie_Ticket_Booking.model.ChucVu;
import com.quoc.Movie_Ticket_Booking.model.Ghe;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;

import java.util.List;
import java.util.Map;

public interface ChucVuService {

    public ApiResponse<?> createChucVu(ChucVuRequestDto chucVuRequestDto, NhanVien nhanVien);

    public ApiResponse<?> updateChucVu(Long id,ChucVuRequestDto updateDto,NhanVien nhanVien);

    public ApiResponse<?> deleteChucVu(Long id,NhanVien nhanVien);

    public ApiResponse<?> updateStatus(Long id,NhanVien nhanVien);

    public ApiResponse<?> getAllChucVu();

    public ChucVu getChucVuId(Long id);
}
