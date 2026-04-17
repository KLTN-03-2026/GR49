package com.quoc.Movie_Ticket_Booking.service;

import com.quoc.Movie_Ticket_Booking.dto.request.GheRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.NhanVienRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.GheResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.NhanVienResponseDto;
import com.quoc.Movie_Ticket_Booking.model.Ghe;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.Users;

import java.util.List;
import java.util.Map;

public interface NhanVienService {

    public ApiResponse<?> createNhanVien(NhanVienRequestDto nhanVienRequestDto,NhanVien nhanVien);

    public  ApiResponse<?> updateNhanVien(Long id, NhanVienRequestDto updateDto,NhanVien nhanVien);

    public ApiResponse<?> deleteNhanVien(Long id, NhanVien nhanvienId);

    public ApiResponse<?> updateStatus(Long id,NhanVien nhanvienId);

    public List<NhanVienResponseDto> getAllNhanVien();

    public NhanVien getNhanVienById(Long id);

    public NhanVien findUserByJwtToken(String jwt);

    public Map<String, Object> getEmployee(String jwt);

    public Map<String, Object> checkToken(String jwt);
}
