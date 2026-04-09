package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.config.CustomerUserDetailsService;
import com.quoc.Movie_Ticket_Booking.config.EmployeeUserDetailsService;
import com.quoc.Movie_Ticket_Booking.config.JwtProvide;
import com.quoc.Movie_Ticket_Booking.dto.request.LoginRequest;
import com.quoc.Movie_Ticket_Booking.dto.response.AuthResponse;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.USER_ROLE;
import com.quoc.Movie_Ticket_Booking.model.Users;
import com.quoc.Movie_Ticket_Booking.repository.NhanVienRepository;
import com.quoc.Movie_Ticket_Booking.repository.UsersRepository;
import com.quoc.Movie_Ticket_Booking.service.AuthService;
import com.quoc.Movie_Ticket_Booking.service.UsersService;
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
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UsersService usersService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvide jwtProvider;
    @Autowired
    private EmployeeUserDetailsService employeeUserDetailsService;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;




    @Override
    public Map<String, Object> nhanVienSignin(LoginRequest loginRequest) {

        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        //Kiem tra nguoi dung
        Authentication authentication = authentication(username,password);


        // Lấy nhanvien trực tiếp từ DB
        NhanVien nhanViens = nhanVienRepository.findByEmail(username);
        if (nhanViens== null) {
            throw new UsernameNotFoundException("user not found with email: " + nhanViens);
        }



        // Kiểm tra trạng thái tài khoản
        if (nhanViens.getTinhTrang()== null || nhanViens.getTinhTrang() == 0) {
            return buildResponse(false, "Tài khoản đang tạm thời tắt. Vui lòng liên hệ quản trị để mở.");
        }

        //lấy role
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        // tao token
        String jwt= jwtProvider.generateToken(authentication);

        AuthResponse authResponse= new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login success");
        authResponse.setRole(USER_ROLE.valueOf(role));

        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Đăng nhập thành công");
        response.put("data", authResponse);
        response.put("role", authResponse.getRole());

        return response;

    }


    @Override
    public Map<String, Object> clientSignin(LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticationUser(username,password);

        // Lấy user trực tiếp từ DB
        Users user = usersRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found with email: " + user);
        }



        // Kiểm tra trạng thái tài khoản
        if (user.getIsActive() == null || user.getIsActive() == 0) {
            return buildResponse(false, "Tài khoản chưa được kích hoạt. Vui lòng kích hoạt để đăng nhập.");
        }

        if (user.getIsBlock() != null && user.getIsBlock() == 1) {
            return buildResponse(false, "Tài khoản của bạn đã bị khoá. Vui lòng liên hệ quản trị viên.");
        }


        //lấy role
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        // tao token
        String jwt= jwtProvider.generateToken(authentication);

        AuthResponse authResponse= new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login success");
        authResponse.setRole(USER_ROLE.valueOf(role));

        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Đăng nhập thành công");
        response.put("data", authResponse);
        response.put("role", authResponse.getRole());
        return response;
    }

    //CHo nhân viên
    private Authentication authentication(String username, String password) {

        UserDetails userDetails = employeeUserDetailsService.loadUserByUsername(username);

        if (userDetails==null) {
            throw new BadCredentialsException("Invalid username.....");
        }
        if (!passwordEncoder.matches(password,userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password.....");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }


    //Cho user
    private Authentication authenticationUser(String username, String password) {

        UserDetails userDetails= customerUserDetailsService.loadUserByUsername(username);

        if (userDetails==null) {
            throw new BadCredentialsException("Invalid username.....");
        }
        if (!passwordEncoder.matches(password,userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password.....");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private Map<String, Object> buildResponse(boolean status, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("message", message);
        return map;
    }
}
