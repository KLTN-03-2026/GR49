package com.quoc.Movie_Ticket_Booking.controller.admin;

import com.quoc.Movie_Ticket_Booking.dto.request.LoginRequest;
import com.quoc.Movie_Ticket_Booking.dto.response.AuthResponse;
import com.quoc.Movie_Ticket_Booking.model.USER_ROLE;
import com.quoc.Movie_Ticket_Booking.model.Users;
import com.quoc.Movie_Ticket_Booking.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AdminAuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/nhan-viens/dang-nhap")
    public ResponseEntity<?> signin(@RequestBody LoginRequest loginRequest)  {
        Map<String, Object> nhanVienSignin = authService.nhanVienSignin(loginRequest);

        return new ResponseEntity<>(nhanVienSignin, HttpStatus.OK);

    }

}
