package com.quoc.Movie_Ticket_Booking.service;

import com.quoc.Movie_Ticket_Booking.dto.request.BinhLuanRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.DanhGiaRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.PhanQuyenRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.model.BinhLuan;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;

import java.util.Map;

public interface DanhGiaVaBinhLuanService {

    public ApiResponse<?> createDanhGia (DanhGiaRequestDto danhGiaRequestDto, Long idKhach);

    public ApiResponse<?> getDanhGia (Long idPhim);

    public ApiResponse<?> createBinhLuan (BinhLuanRequestDto binhLuanRequestDto,Long idKhach);

    public ApiResponse<?> getBinhLuan (Long idPhim);
}
