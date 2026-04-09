package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.config.JwtProvide;
import com.quoc.Movie_Ticket_Booking.dto.request.*;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.HangThanhVienResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.KhachHangResponseDto;
import com.quoc.Movie_Ticket_Booking.exception.ResourceAlreadyExistsException;
import com.quoc.Movie_Ticket_Booking.model.HangThanhVien;
import com.quoc.Movie_Ticket_Booking.model.Users;
import com.quoc.Movie_Ticket_Booking.repository.UsersRepository;
import com.quoc.Movie_Ticket_Booking.service.UsersService;
import com.quoc.Movie_Ticket_Booking.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvide jwtProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Override
    public Users registerUser(UserRequestDto userRequestDto) throws Exception {

        // Kiểm tra email đã tồn tại
        Users isEmailExits = usersRepository.findByEmail(userRequestDto.getEmail());

        if (isEmailExits != null) {
            throw new ResourceAlreadyExistsException("Email is already used with another account");
        }

        Users createUser = new Users();
        createUser.setEmail(userRequestDto.getEmail());
        createUser.setHoVaTen(userRequestDto.getHoVaTen());
        createUser.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        createUser.setSoDienThoai(userRequestDto.getSoDienThoai());
        createUser.setCccd(userRequestDto.getCccd());
        createUser.setNgaySinh(userRequestDto.getNgaySinh());
        createUser.setAvatar("https://res.cloudinary.com/dvxkhsfyj/image/upload/v1756310316/popcorn_ry5fwe.png");
        createUser.setCreatedAt(LocalDateTime.now());
        createUser.setHashActive(UUID.randomUUID().toString());



        Users saveUser = usersRepository.save(createUser);

//        commonUtil.emailSend(saveUser);
        // 📧 Gửi mail xác thực (có kiểm tra lỗi)
        try {
            commonUtil.emailSend(saveUser);
        } catch (Exception e) {
            throw new RuntimeException("Đăng ký thành công nhưng gửi email thất bại: " + e.getMessage(), e);
        }
        return saveUser;
    }

    @Override
    public Users findUserByJwtToken(String jwt) {

        String emailFromJwtToken = jwtProvider.getEmailFromJwtToken(jwt);
        Users users = usersRepository.findByEmail(emailFromJwtToken);

        return users;
    }

    @Override
    public Users findUserByEmail(String email) {
        Users user = usersRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }


    @Override
    public Map<String, Object> getUser(String jwt) {
        Map<String, Object> response = new HashMap<>();
        try {

            // Tìm user theo token
            Users users = findUserByJwtToken(jwt);

            if (users != null) {
                response.put("status", true);
                response.put("data", users);
            } else {
                response.put("status", false);
                response.put("message", "Không tìm thấy người dùng từ token.");
            }

        } catch (Exception e) {
            response.put("status", false);
            response.put("message", "Token không hợp lệ hoặc đã hết hạn.");
        }

        return response;
    }

    @Override
    public Map<String, Object> checkToken(String jwt) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Tìm user theo token
            Users users = findUserByJwtToken(jwt);

        if (users != null) {
            response.put("status", true);
            response.put("ho_ten", users.getHoVaTen());

        } else {
            response.put("status", false);
                response.put("message", "Không tìm thấy người dùng từ token.");
        }
        } catch (Exception e) {
            response.put("status", false);
            response.put("message", "Token không hợp lệ hoặc đã hết hạn.");
        }

        return response;
    }

   //Kích hoạt tài khoa khi đăng kí
    @Override
    public ApiResponse<?> kichHoatTaiKhoan(String maKichHoat) {

        Users saveUser = usersRepository.findByHashActive(maKichHoat);
        saveUser.setIsActive(1);
        usersRepository.save(saveUser);
        return ApiResponse.success("Đã kích hoạt tài khoản thành công!");
    }

    //Quên mặt khẩu
    @Override
    public  ApiResponse<?> forgotPassWord(String email) throws Exception {
        Map<String, Object> response = new HashMap<>();
        Users user = usersRepository.findByEmail(email);
        if (user == null) {

            return ApiResponse.fail( "Tài khoản chưa được đăng ký!");
        }
        user.setHashReset(UUID.randomUUID().toString());
        Users saveUser = usersRepository.save(user);
//        commonUtil.emailSendToResetPass(saveUser);
        // 📧 Gửi email đặt lại mật khẩu
        try {
            commonUtil.emailSendToResetPass(saveUser);
        } catch (Exception e) {
            throw new RuntimeException("Gửi email đặt lại mật khẩu thất bại: " + e.getMessage(), e);
        }

        return ApiResponse.success("Vui lòng kiểm tra email để đổi mật khẩu.");
    }

    //Đặt lại mật khẩu
    @Override
    public  ApiResponse<?> resetPassWord(String maReset,ResetMatKhauRequest req) {
        Map<String, Object> response = new HashMap<>();
        Users byHashReset = usersRepository.findByHashReset(maReset);
        if (byHashReset == null) {

            return ApiResponse.fail( "Mã không tồn tại.");
        }

        // Kiểm tra mật khẩu mới và xác nhận
        if (!req.getMatKhauMoi().equals(req.getXacNhanMatKhauMoi())) {

            return ApiResponse.fail( "Hai trường mật khẩu không giống nhau.");
        }

        // Cập nhật mật khẩu
        byHashReset.setPassword(passwordEncoder.encode(req.getMatKhauMoi()));
        byHashReset.setHashReset(null);
        usersRepository.save(byHashReset);


        return ApiResponse.success("Đặt lại mật khẩu thành công.");
    }

    //Thay đổi mật khẩu
    @Override
    public  ApiResponse<?> changePassWord(ThayDoiMatKhauRequestDto req, String jwt) {
        Users users = findUserByJwtToken(jwt);

        //kiểm tra mật khẩu nhập cũ vào có khớp mật khẩu cũ trước đó không
        if(!passwordEncoder.matches(req.getMatKhauCu(), users.getPassword())) {

            return ApiResponse.fail( "Mật khẩu cũ không đúng!");
        }

        // Kiểm tra mật khẩu mới và xác nhận
        if (!req.getMatkhauMoi().equals(req.getXacNhanMatKhau())) {

            return ApiResponse.fail( "Hai trường mật khẩu không khớp!");
        }

        String matKhauMoi = passwordEncoder.encode(req.getMatkhauMoi());
        users.setPassword(matKhauMoi);
        usersRepository.save(users);

        return ApiResponse.success("Thay đổi mật khẩu thành công!");
    }


    //Cập nhât thong tin cá nhân
    @Override
    public  ApiResponse<?> updateThongTinCaNhan(ThayDoiThongTinUserRequestDto req, String jwt) {
        Users users = findUserByJwtToken(jwt);
        if (req.getHoVaTen() != null) {
            users.setHoVaTen(req.getHoVaTen());
        }
        if (req.getSoDienThoai() != null) {
            users.setSoDienThoai(req.getSoDienThoai());
        }
        if (req.getNgaySinh() != null) {
            users.setNgaySinh(req.getNgaySinh());
        }
        if (req.getCccd() != null) {
            users.setCccd(req.getCccd());
        }
        usersRepository.save(users);


        return ApiResponse.success("Thay đổi thông tin thành công!");

    }

    @Override
    public ApiResponse<?> updateAvatarProfile(CapNhatAvatarProfile req, String jwt) {
        Users users = findUserByJwtToken(jwt);
         if (req.getAvatarUrl()!=null) {
             users.setAvatar(req.getAvatarUrl());
         }
        usersRepository.save(users);


        return ApiResponse.success("Thay đổi avatar thành công!");
    }
}
