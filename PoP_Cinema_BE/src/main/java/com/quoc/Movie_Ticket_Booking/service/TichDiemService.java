package com.quoc.Movie_Ticket_Booking.service;

import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;

import java.time.LocalDate;

public interface TichDiemService {

    public ApiResponse<?> getAllTichDiem( LocalDate from, LocalDate to, Integer tinhTrang) ;

    public ApiResponse<?> getLichSuTichDiem(Long userId, LocalDate from, LocalDate to, Integer tinhTrang) ;

    public ApiResponse<?> getTop5KhachHang() ;
}
