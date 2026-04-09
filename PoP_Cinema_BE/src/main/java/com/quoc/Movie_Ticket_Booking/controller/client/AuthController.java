package com.quoc.Movie_Ticket_Booking.controller.client;

import com.quoc.Movie_Ticket_Booking.config.CustomerUserDetailsService;
import com.quoc.Movie_Ticket_Booking.config.JwtProvide;
import com.quoc.Movie_Ticket_Booking.dto.request.LoginRequest;
import com.quoc.Movie_Ticket_Booking.dto.request.UserRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.AuthResponse;
import com.quoc.Movie_Ticket_Booking.model.USER_ROLE;
import com.quoc.Movie_Ticket_Booking.model.Users;
import com.quoc.Movie_Ticket_Booking.repository.UsersRepository;
import com.quoc.Movie_Ticket_Booking.service.AuthService;
import com.quoc.Movie_Ticket_Booking.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvide jwtProvider;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    private UsersRepository usersRepository;


    @PostMapping("/dang-ky")
    public ResponseEntity<?> createUserHandler(@Valid @RequestBody UserRequestDto dto) throws Exception {
        Users saveUser = usersService.registerUser(dto);
        // Tạo response dạng JSON
        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Đăng ký tài khoản thành công, vui lòng kiểm tra email để kích hoạt tài khoản");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/dang-nhap")
    public ResponseEntity<?> signin(@RequestBody LoginRequest loginRequest)  {

        Map<String, Object> signin = authService.clientSignin(loginRequest);
        return new ResponseEntity<>(signin, HttpStatus.OK);

    }

    private Authentication authentication(String username, String password) {

        UserDetails userDetails= customerUserDetailsService.loadUserByUsername(username);

        if (userDetails==null) {
            throw new BadCredentialsException("Invalid username.....");
        }
        if (!passwordEncoder.matches(password,userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password.....");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
