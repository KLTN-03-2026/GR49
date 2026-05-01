package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.dto.request.ThayDoiMatKhauRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.ThayDoiThongTinNhanVienRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.ThayDoiThongTinUserRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.Users;
import com.quoc.Movie_Ticket_Booking.repository.NhanVienRepository;
import com.quoc.Movie_Ticket_Booking.service.NhanVienService;
import com.quoc.Movie_Ticket_Booking.service.ThongTinNhanVien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ThongTinNhanVienImpl implements ThongTinNhanVien {

    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Override
    public ApiResponse<?> changePassWord(ThayDoiMatKhauRequestDto req, String jwt) {
        NhanVien nhanViens = nhanVienService.findUserByJwtToken(jwt);

        //kiểm tra mật khẩu nhập cũ vào có khớp mật khẩu cũ trước đó không
        if(!passwordEncoder.matches(req.getMatKhauCu(), nhanViens.getPassword())) {

            return ApiResponse.fail( "Mật khẩu cũ không đúng!");
        }

        // Kiểm tra mật khẩu mới và xác nhận
        if (!req.getMatkhauMoi().equals(req.getXacNhanMatKhau())) {

            return ApiResponse.fail( "Hai trường mật khẩu không khớp!");
        }

        String matKhauMoi = passwordEncoder.encode(req.getMatkhauMoi());
        nhanViens.setPassword(matKhauMoi);
        nhanVienRepository.save(nhanViens);

        return ApiResponse.success("Thay đổi mật khẩu thành công!");
    }


    @Override
    public ApiResponse<?> updateThongTinCaNhan(ThayDoiThongTinNhanVienRequestDto req, String jwt) {

        NhanVien nhanViens = nhanVienService.findUserByJwtToken(jwt);
        if (req.getHoVaTen() != null) {
            nhanViens.setHoVaTen(req.getHoVaTen());
        }
        if (req.getSoDienThoai() != null) {
            nhanViens.setSoDienThoai(req.getSoDienThoai());
        }
        if (req.getNgaySinh() != null) {
            nhanViens.setNgaySinh(req.getNgaySinh());
        }
        if (req.getDiaChi() != null) {
            nhanViens.setDiaChi(req.getDiaChi());
        }
        nhanVienRepository.save(nhanViens);


        return ApiResponse.success("Thay đổi thông tin thành công!");
    }
}
