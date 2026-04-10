package com.quoc.Movie_Ticket_Booking.service;

import com.quoc.Movie_Ticket_Booking.dto.request.ChangStatusDto;
import com.quoc.Movie_Ticket_Booking.dto.request.PhimRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.PhongChieuRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.PhongChieuResponseDto;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.Phim;
import com.quoc.Movie_Ticket_Booking.model.PhongChieu;

import java.util.List;
import java.util.Map;

public interface PhongChieuService {

    public ApiResponse<?> createPhongChieu(PhongChieuRequestDto phongChieuRequestDto, NhanVien nhanVienId);

    public   ApiResponse<?> updatePhongChieu(Long id, PhongChieuRequestDto updateDto,NhanVien  nhanVienId);

    public  ApiResponse<?> deletePhongChieu(Long id,NhanVien  nhanVienId);

    public  ApiResponse<?> updateStatus(Long id,NhanVien  nhanVienId);

    public List<PhongChieuResponseDto> getAllPhongChieu();

    public PhongChieu getPhongChieuById(Long id);

    public  ApiResponse<?> createAutoGhe(PhongChieuRequestDto phongChieuRequestDto,NhanVien  nhanVienId);


}
