package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.dto.request.ChucVuRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.ChucVuResponseDto;
import com.quoc.Movie_Ticket_Booking.exception.ResourceNotFoundException;
import com.quoc.Movie_Ticket_Booking.model.ChucVu;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.PhanQuyen;
import com.quoc.Movie_Ticket_Booking.model.PhongChieu;
import com.quoc.Movie_Ticket_Booking.repository.ChucVuRepository;
import com.quoc.Movie_Ticket_Booking.repository.NhanVienRepository;
import com.quoc.Movie_Ticket_Booking.repository.PhanQuyenRepository;
import com.quoc.Movie_Ticket_Booking.service.ChucVuService;
import com.quoc.Movie_Ticket_Booking.service.PhanQuyenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ChucVuServiceImpl implements ChucVuService {

    @Autowired
    private ChucVuRepository chucVuRepository;

    @Autowired
    private PhanQuyenRepository phanQuyenRepository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Override
    public ApiResponse<?> createChucVu(ChucVuRequestDto chucVuRequestDto, NhanVien nhanVienId) {

        long idChucNang = 5;
        long idChucVu = nhanVienId.getChucVu().getId();
        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        ChucVu saveChucVu = new ChucVu();
        saveChucVu.setTenChucVu(chucVuRequestDto.getTenChucVu());
        saveChucVu.setSlugChucVu(chucVuRequestDto.getSlugChucVu());
        saveChucVu.setTinhTrang(chucVuRequestDto.getTinhTrang());
         chucVuRepository.save(saveChucVu);

        return ApiResponse.success("Thêm chức vụ thành công!");
    }


    @Override
    public ApiResponse<?> updateChucVu(Long id, ChucVuRequestDto updateDto,NhanVien nhanVienId) {

        long idChucNang = 5;
        long idChucVu = nhanVienId.getChucVu().getId();
        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }



        ChucVu chucVu = getChucVuId(id);

        if (updateDto.getTenChucVu() != null) {
            chucVu.setTenChucVu(updateDto.getTenChucVu());
        }
        if (updateDto.getSlugChucVu() != null) {
            chucVu.setSlugChucVu(updateDto.getSlugChucVu());
        }
        if (updateDto.getTinhTrang() != null) {
            chucVu.setTinhTrang(updateDto.getTinhTrang());
        }
        chucVuRepository.save(chucVu);

        return ApiResponse.success("Cập nhật chức vụ thành công!");
    }

    @Override
    public ApiResponse<?> deleteChucVu(Long id,NhanVien nhanVienId) {
        long idChucNang = 5;
        long idChucVu = nhanVienId.getChucVu().getId();
        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        ChucVu chucVu = getChucVuId(id);

        // ✅ Kiểm tra ràng buộc 1: chức vụ đang được phân quyền
        boolean dangDuocPhanQuyen = phanQuyenRepository.existsByChucVuId(id);
        if (dangDuocPhanQuyen) {
            return ApiResponse.fail("Không thể xóa chức vụ này vì đang được phân quyền!");
        }

        // ✅ Kiểm tra ràng buộc 2: chức vụ đang có nhân viên sử dụng
        boolean dangDuocSuDung = nhanVienRepository.existsByChucVuId(id);
        if (dangDuocSuDung) {
            return ApiResponse.fail("Không thể xóa chức vụ này vì đang có nhân viên sử dụng!");
        }

        // Xoá chức vụ
        chucVuRepository.delete(chucVu);
        return ApiResponse.success("Xoá chức vụ thành công!");
    }

    @Override
    public ApiResponse<?> updateStatus(Long id,NhanVien nhanVienId) {
        long idChucNang = 5;
        long idChucVu = nhanVienId.getChucVu().getId();
        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        ChucVu chucVu = getChucVuId(id);
        chucVu.setTinhTrang(chucVu.getTinhTrang()==1?0:1);
         chucVuRepository.save(chucVu);
        return ApiResponse.success("Cập nhật trạng thái thành công!");
    }

    @Override
    public ApiResponse<?> getAllChucVu() {
        List<ChucVu> chucVuList = chucVuRepository.findAll();

        return ApiResponse.success("Hiênr thị thành công!",chucVuList);
    }

    @Override
    public ChucVu getChucVuId(Long id) {
        Optional<ChucVu> otp = chucVuRepository.findById(id);
        if(otp.isEmpty()){
            throw new ResourceNotFoundException("Chuc vu not found id "+id);
        }
        return otp.get();
    }
}
