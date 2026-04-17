package com.quoc.Movie_Ticket_Booking.service;

import com.quoc.Movie_Ticket_Booking.dto.request.KhachHangRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.NhanVienRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.GheResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.KhachHangResponseDto;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.Users;

import java.util.List;
import java.util.Map;

public interface KhachHangService {

    public ApiResponse<?> getAllKhachHang();

    public ApiResponse<?> updateKhachHang(Long id, KhachHangRequestDto updateDto, NhanVien nhanVien);

    public ApiResponse<?> deleteKhachHang(Long id, NhanVien nhanvienId);

    public ApiResponse<?> updateStatus(Long id,NhanVien nhanvienId);

    public ApiResponse<?> updateKichHoat(Long id,NhanVien nhanvienId);

    public Users getKhachHangById(Long id);
}
