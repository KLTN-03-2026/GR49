package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.dto.request.KhachHangRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.GheResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.KhachHangResponseDto;
import com.quoc.Movie_Ticket_Booking.exception.ResourceAlreadyExistsException;
import com.quoc.Movie_Ticket_Booking.exception.ResourceNotFoundException;
import com.quoc.Movie_Ticket_Booking.model.ChucVu;
import com.quoc.Movie_Ticket_Booking.model.Ghe;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.Users;
import com.quoc.Movie_Ticket_Booking.repository.KhachHangRepository;
import com.quoc.Movie_Ticket_Booking.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class KhachHangServiceImpl implements KhachHangService {
    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse<?> getAllKhachHang() {
        Map<String,Object> response = new HashMap<>();
        List<Users> all = khachHangRepository.findAll();
        List<KhachHangResponseDto> khachHangDtoList = all.stream()
                .map(this::mapToKhachHangDto)
                .toList();

        return ApiResponse.success("Hiển thị thành công!",khachHangDtoList);
    }

    private KhachHangResponseDto mapToKhachHangDto(Users users) {
        return new KhachHangResponseDto(
                users.getId(),
                users.getHoVaTen(),
                users.getEmail(),
                users.getSoDienThoai(),
                users.getCccd(),
                users.getNgaySinh(),
                users.getIsActive(),
                users.getIsBlock()
        );
    }

    @Override
    public ApiResponse<?> updateKhachHang(Long id, KhachHangRequestDto updateDto, NhanVien nhanVienId) {
        long idChucNang = 6;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        Users khachHang = getKhachHangById(id);

        // Nếu DTO có email mới
        if (updateDto.getEmail() != null) {
            khachHang.setEmail(updateDto.getEmail());
        }

        if (updateDto.getHoVaTen() != null) {
            khachHang.setHoVaTen(updateDto.getHoVaTen());
        }
        if (updateDto.getPassWord() != null) {
            khachHang.setPassword(passwordEncoder.encode(updateDto.getPassWord()));
        }
        if (updateDto.getSoDienThoai() != null) {
            khachHang.setSoDienThoai(updateDto.getSoDienThoai());
        }
        if (updateDto.getNgaySinh() != null) {
            khachHang.setNgaySinh(updateDto.getNgaySinh());
        }
        if (updateDto.getCccd() != null) {
            khachHang.setCccd(updateDto.getCccd());
        }

        khachHangRepository.save(khachHang);
        return ApiResponse.success("Cập nhật khách hàng thành công");
    }

    @Override
    public ApiResponse<?> deleteKhachHang(Long id, NhanVien nhanVienId) {
        long idChucNang = 6;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        Users khachHangById = getKhachHangById(id);

        // Kiểm tra isActive
        if (khachHangById.getIsActive() == 1) {
            return ApiResponse.fail("Không thể xóa khách hàng đã kích hoạt!");
        }

        khachHangRepository.delete(khachHangById);
        return ApiResponse.success("Xóa khách hàng thành công!");
    }

    @Override
    public ApiResponse<?> updateStatus(Long id, NhanVien nhanVienId) {
        long idChucNang = 6;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }
        Users khachHangById = getKhachHangById(id);
        khachHangById.setIsBlock(khachHangById.getIsBlock()==1?0:1);
        khachHangRepository.save(khachHangById);
        return ApiResponse.success("Cập nhật trạng thái thành công");

    }

    @Override
    public ApiResponse<?> updateKichHoat(Long id, NhanVien nhanVienId) {
        long idChucNang = 6;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }
        Users khachHangById = getKhachHangById(id);
        khachHangById.setIsActive(khachHangById.getIsActive()==1?0:1);
        khachHangRepository.save(khachHangById);
        return ApiResponse.success("Thay đổi kích hoạt thành công");
    }

    @Override
    public Users getKhachHangById(Long id) {
        Optional<Users> otp = khachHangRepository.findById(id);
        if(otp.isEmpty()){
            throw new ResourceNotFoundException("Khach Hang not found id "+id);
        }
        return otp.get();
    }
}
