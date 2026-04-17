package com.quoc.Movie_Ticket_Booking.service;

import com.quoc.Movie_Ticket_Booking.dto.request.ThayDoiMatKhauRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.ThayDoiThongTinNhanVienRequestDto;

import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;

public interface ThongTinNhanVien {

    public ApiResponse<?> changePassWord(ThayDoiMatKhauRequestDto req, String jwt) ;

    public  ApiResponse<?> updateThongTinCaNhan(ThayDoiThongTinNhanVienRequestDto req, String jwt) ;
}
