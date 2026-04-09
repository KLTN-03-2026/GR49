package com.quoc.Movie_Ticket_Booking.service;

import com.quoc.Movie_Ticket_Booking.dto.request.*;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.model.Users;

import java.util.Map;

public interface UsersService {

    public Users registerUser(UserRequestDto userRequestDto) throws Exception;

    public Users findUserByJwtToken(String jwt);

    public Users findUserByEmail(String email);

    public Map<String, Object> getUser(String jwt);

    public Map<String, Object> checkToken(String jwt);

    public ApiResponse<?> kichHoatTaiKhoan(String maKichHoat);

    public  ApiResponse<?> forgotPassWord(String email) throws Exception;

    public  ApiResponse<?> resetPassWord(String maReset,ResetMatKhauRequest req) ;

    public  ApiResponse<?> changePassWord(ThayDoiMatKhauRequestDto req,String jwt) ;

    public  ApiResponse<?> updateThongTinCaNhan(ThayDoiThongTinUserRequestDto req, String jwt) ;

    public  ApiResponse<?> updateAvatarProfile(CapNhatAvatarProfile req, String jwt) ;

}
