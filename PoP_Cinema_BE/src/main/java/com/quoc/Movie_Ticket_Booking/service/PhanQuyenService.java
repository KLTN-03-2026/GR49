package com.quoc.Movie_Ticket_Booking.service;

import com.quoc.Movie_Ticket_Booking.dto.request.PhanQuyenRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.PhanQuyenResponseDto;
import com.quoc.Movie_Ticket_Booking.model.ChucNang;
import com.quoc.Movie_Ticket_Booking.model.ChucVu;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.PhanQuyen;

import java.util.List;
import java.util.Map;

public interface PhanQuyenService {

    public ApiResponse<?> addPhanQuyen (PhanQuyenRequestDto phanQuyenRequestDto, NhanVien nhanVien);

    public ApiResponse<?> deletePhanQuyen (Long phanQuyenId,NhanVien nhanVien);

    public ApiResponse<?> getPhanQuyenByChucVuId(Long chucVuId,NhanVien nhanVien);

    public ChucNang getChucNangId(Long id);

    public ApiResponse<?> getAllChucNang();

    public void deletePhanQuyen(Long id);

    public PhanQuyen getPhanQuyenId(Long id);



}
