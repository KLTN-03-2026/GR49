package com.quoc.Movie_Ticket_Booking.service;

import com.quoc.Movie_Ticket_Booking.dto.request.LoginRequest;

import java.util.Map;

public interface AuthService {
    public Map<String, Object> nhanVienSignin(LoginRequest loginRequest);

    public Map<String, Object> clientSignin(LoginRequest loginRequest);
}
