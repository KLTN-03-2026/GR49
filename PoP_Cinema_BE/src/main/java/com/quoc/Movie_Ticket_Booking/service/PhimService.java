package com.quoc.Movie_Ticket_Booking.service;

import com.quoc.Movie_Ticket_Booking.dto.request.ChangStatusDto;
import com.quoc.Movie_Ticket_Booking.dto.request.PhimRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.PhimHomeResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.PhimResponseDto;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.Phim;

import java.util.List;
import java.util.Map;

public interface PhimService {

    public ApiResponse<?> createPhim(PhimRequestDto phimRequestDto, NhanVien nhanVienId);

    public ApiResponse<?> updatePhim(Long id,PhimRequestDto updatePhimDto,NhanVien nhanVienId);

    public ApiResponse<?> deletePhim(Long id,NhanVien nhanVienId);

    public ApiResponse<?> updateStatus(ChangStatusDto dto,NhanVien nhanVienId);

    public List<PhimResponseDto> getAllPhim();

    public Phim getPhimById(Long id);

    public Map<String, Object> getDataClientPhim();

    public ApiResponse<?> getPhimGoiY();

    public List<PhimHomeResponseDto> getPhimDangChieu();

    public List<PhimHomeResponseDto> getPhimSapChieu();


    public List<Phim> getPhimDangChieuu();


}
