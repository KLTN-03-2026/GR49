package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.config.JwtProvide;
import com.quoc.Movie_Ticket_Booking.dto.request.NhanVienRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.GheResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.NhanVienResponseDto;
import com.quoc.Movie_Ticket_Booking.exception.ResourceAlreadyExistsException;
import com.quoc.Movie_Ticket_Booking.exception.ResourceNotFoundException;
import com.quoc.Movie_Ticket_Booking.model.*;
import com.quoc.Movie_Ticket_Booking.repository.NhanVienRepository;
import com.quoc.Movie_Ticket_Booking.repository.PhanQuyenRepository;
import com.quoc.Movie_Ticket_Booking.service.ChucVuService;
import com.quoc.Movie_Ticket_Booking.service.NhanVienService;
import com.quoc.Movie_Ticket_Booking.service.PhanQuyenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NhanVienServiceImpl implements NhanVienService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ChucVuService chucVuService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private JwtProvide jwtProvider;

    @Override
    public ApiResponse<?> createNhanVien(NhanVienRequestDto nhanVienRequestDto,NhanVien nhanVienId) {

        long idChucNang = 7;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        NhanVien isEmailExits = nhanVienRepository.findByEmail(nhanVienRequestDto.getEmail());

        if (isEmailExits != null) {
            throw new ResourceAlreadyExistsException("Email da ton tai, vui long nhap email khac");
        }

        NhanVien createNhanVien = new NhanVien();
        createNhanVien.setHoVaTen(nhanVienRequestDto.getHoVaTen());
        createNhanVien.setEmail(nhanVienRequestDto.getEmail());
        createNhanVien.setPassword(passwordEncoder.encode(nhanVienRequestDto.getPassWord()));
        createNhanVien.setSoDienThoai(nhanVienRequestDto.getSoDienThoai());
        createNhanVien.setNgaySinh(nhanVienRequestDto.getNgaySinh());
        createNhanVien.setDiaChi(nhanVienRequestDto.getDiaChi());
        ChucVu chucVuId = chucVuService.getChucVuId(nhanVienRequestDto.getIdChucVu());
        createNhanVien.setChucVu(chucVuId);
        nhanVienRepository.save(createNhanVien);
        return ApiResponse.success("Thêm nhân viên thành công");

    }

    @Override
    public ApiResponse<?> updateNhanVien(Long id, NhanVienRequestDto updateDto,NhanVien nhanVienId) {

        long idChucNang = 7;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        NhanVien nhanVien = getNhanVienById(id);

        // Nếu DTO có email mới
        if (updateDto.getEmail() != null && !updateDto.getEmail().equals(nhanVien.getEmail())) {
            NhanVien existingNhanVien = nhanVienRepository.findByEmail(updateDto.getEmail());
            if (existingNhanVien != null && !existingNhanVien.getId().equals(id)) {
                throw new ResourceAlreadyExistsException("Email đã tồn tại, vui lòng nhập email khác");
            }
            nhanVien.setEmail(updateDto.getEmail());
        }

        if (updateDto.getHoVaTen() != null) {
            nhanVien.setHoVaTen(updateDto.getHoVaTen());
        }
        if (updateDto.getSoDienThoai() != null) {
            nhanVien.setSoDienThoai(updateDto.getSoDienThoai());
        }
        if (updateDto.getNgaySinh() != null) {
            nhanVien.setNgaySinh(updateDto.getNgaySinh());
        }
        if (updateDto.getDiaChi() != null) {
            nhanVien.setDiaChi(updateDto.getDiaChi());
        }
        if (updateDto.getTinhTrang() != null) {
            nhanVien.setTinhTrang(updateDto.getTinhTrang());
        }
        ChucVu chucVuId = chucVuService.getChucVuId(updateDto.getIdChucVu());
        if (updateDto.getIdChucVu() != null) {
            nhanVien.setChucVu(chucVuId);
        }
         nhanVienRepository.save(nhanVien);
        return ApiResponse.success("Cập nhật nhân viên thành công");
    }

    @Override
    public ApiResponse<?> deleteNhanVien(Long id,NhanVien nhanvienId) {

        long idChucNang = 7;
        long idChucVu = nhanvienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        // ❌ Không cho phép tự xóa chính mình
        if (nhanvienId.getId().equals(id)) {
            return ApiResponse.fail("Bạn không thể xóa tài khoản của chính mình!");
        }
        NhanVien nhanVienById = getNhanVienById(id);
        nhanVienRepository.delete(nhanVienById);
        return ApiResponse.success("Xóa nhân viên thành công");
    }

    @Override
    public ApiResponse<?> updateStatus(Long id,NhanVien nhanvienId) {

        long idChucNang = 7;
        long idChucVu = nhanvienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        NhanVien nhanVienById = getNhanVienById(id);
        nhanVienById.setTinhTrang(nhanVienById.getTinhTrang()==1?0:1);
         nhanVienRepository.save(nhanVienById);
        return ApiResponse.success("Cập nhật trạng thái thành công");
    }

    @Override
    public List<NhanVienResponseDto> getAllNhanVien() {
        List<NhanVien> dsNhanVien = nhanVienRepository.findAll();
        List<NhanVienResponseDto> nhanVienDtoList = dsNhanVien.stream()
                .map(this::mapToNhanVienDto)
                .toList();
        return nhanVienDtoList;
    }

    @Override
    public NhanVien getNhanVienById(Long id) {
        Optional<NhanVien> otp = nhanVienRepository.findById(id);
        if(otp.isEmpty()){
            throw new ResourceNotFoundException("Nhan Vien not found id "+id);
        }
        return otp.get();
    }

    private NhanVienResponseDto mapToNhanVienDto(NhanVien nhanVien) {
        return new NhanVienResponseDto(
                nhanVien.getId(),
                nhanVien.getHoVaTen(),
                nhanVien.getEmail(),
                nhanVien.getSoDienThoai(),
                nhanVien.getNgaySinh(),
                nhanVien.getDiaChi(),
                nhanVien.getTinhTrang(),
                nhanVien.getChucVu().getId(),
                nhanVien.getChucVu().getTenChucVu()
        );
    }

    @Override
    public NhanVien findUserByJwtToken(String jwt) {
        String emailFromJwtToken = jwtProvider.getEmailFromJwtToken(jwt);
        NhanVien nhanVien =nhanVienRepository.findByEmail(emailFromJwtToken);
        return nhanVien;
    }

    @Override
    public Map<String, Object> getEmployee(String jwt) {
        Map<String, Object> response = new HashMap<>();
        try {

            // Tìm nhan vien theo token
            NhanVien nhanVien = findUserByJwtToken(jwt);

            if (nhanVien!= null) {
                response.put("status", true);
                response.put("data", nhanVien);
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
            // Tìm nhan vien theo token
            NhanVien nhanVien = findUserByJwtToken(jwt);

            if (nhanVien != null) {
                response.put("status", true);
                response.put("ho_ten", nhanVien.getHoVaTen());
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


}
