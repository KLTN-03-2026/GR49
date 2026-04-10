package com.quoc.Movie_Ticket_Booking.service.impl;
import com.quoc.Movie_Ticket_Booking.dto.request.ChangStatusDto;
import com.quoc.Movie_Ticket_Booking.dto.request.PhongChieuRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.GheResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.PhongChieuResponseDto;
import com.quoc.Movie_Ticket_Booking.exception.ResourceAlreadyExistsException;
import com.quoc.Movie_Ticket_Booking.exception.ResourceNotFoundException;
import com.quoc.Movie_Ticket_Booking.model.*;
import com.quoc.Movie_Ticket_Booking.repository.GheRepository;
import com.quoc.Movie_Ticket_Booking.repository.PhanQuyenRepository;
import com.quoc.Movie_Ticket_Booking.repository.PhongChieuRepository;
import com.quoc.Movie_Ticket_Booking.repository.SuatChieuRepository;
import com.quoc.Movie_Ticket_Booking.service.PhanQuyenService;
import com.quoc.Movie_Ticket_Booking.service.PhongChieuService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PhongChieuServiceImpl implements PhongChieuService {

    @Autowired
    private PhongChieuRepository phongChieuRepository;

    @Autowired
    private GheRepository gheRepository;


    @Autowired
    private SuatChieuRepository suatChieuRepository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public ApiResponse<?> createPhongChieu(PhongChieuRequestDto phongChieuRequestDto, NhanVien nhanVienId) {

        long idChucNang = 3;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        Boolean existsByName = phongChieuRepository.existsByTenPhong(phongChieuRequestDto.getTenPhong().trim());
        if (existsByName) {
            throw new ResourceAlreadyExistsException("Phòng chiếu có tên '" + phongChieuRequestDto.getTenPhong() + "' đã tồn tại.");
        }

        PhongChieu phongChieu = new PhongChieu();
        phongChieu.setTenPhong(phongChieuRequestDto.getTenPhong());
        phongChieu.setHinhAnh(phongChieuRequestDto.getTenHinhAnh());
        phongChieu.setHangDoc(phongChieuRequestDto.getHangDoc());
        phongChieu.setHangNgang(phongChieuRequestDto.getHangNgang());
         phongChieuRepository.save(phongChieu);
        return ApiResponse.success("Tạo phòng chiếu thành công!");
    }

    @Override
    public ApiResponse<?> updatePhongChieu(Long id, PhongChieuRequestDto updateDto,NhanVien nhanVienId) {

        long idChucNang = 3;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        PhongChieu phongChieu= getPhongChieuById(id);

        if (updateDto.getTenPhong() != null) {
            phongChieu.setTenPhong(updateDto.getTenPhong());
        }
        if (updateDto.getTenHinhAnh() != null) {
            phongChieu.setHinhAnh(updateDto.getTenHinhAnh());
        }
        if (updateDto.getHangDoc() != null) {
            phongChieu.setHangDoc(updateDto.getHangDoc());
        }
        if (updateDto.getHangNgang() != null) {
            phongChieu.setHangNgang(updateDto.getHangNgang());
        }
        if (updateDto.getTinhTrang() != null) {
            phongChieu.setTinhTrang(updateDto.getTinhTrang());
        }
         phongChieuRepository.save(phongChieu);
        return ApiResponse.success("Cập nhật phòng chiếu thành công!");
    }


    @Override
    public ApiResponse<?> deletePhongChieu(Long id,NhanVien nhanVienId) {
        long idChucNang = 3;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        PhongChieu phongChieu= getPhongChieuById(id);

        // ✅ Kiểm tra xem phòng chiếu có suất chiếu nào không
        boolean daCoSuatChieu = suatChieuRepository.existsByPhongChieuId(id);
        if (daCoSuatChieu) {
            return ApiResponse.fail("Không thể xóa phòng chiếu này vì đã tồn tại suất chiếu liên quan!");
        }

        // ✅ Kiểm tra xem phòng chiếu có ghế nào không
        boolean daCoGhe = gheRepository.existsByPhongChieuId(id);
        if (daCoGhe) {
            return ApiResponse.fail("Không thể xóa phòng chiếu này vì vẫn còn ghế trong phòng!");
        }

        phongChieuRepository.delete(phongChieu);
        return ApiResponse.success("Xoá phòng chiếu thành công!");
    }


    @Override
    public ApiResponse<?> updateStatus(Long id,NhanVien nhanVienId) {
        long idChucNang = 3;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        PhongChieu phongChieu= getPhongChieuById(id);
        phongChieu.setTinhTrang(phongChieu.getTinhTrang()==1?0:1);
        phongChieuRepository.save(phongChieu);
        return ApiResponse.success("Cập nhật trạng thái thành công!");
    }

    @Override
    public List<PhongChieuResponseDto> getAllPhongChieu() {
        List<PhongChieu> dsPhong = phongChieuRepository.findAll();
        List<PhongChieuResponseDto> phongDtoList = dsPhong.stream()
                .map(this::mapToPhongChieuDto)
                .toList();
        return phongDtoList;
    }

    @Override
    public PhongChieu getPhongChieuById(Long id) {
        Optional<PhongChieu> otp = phongChieuRepository.findById(id);
        if(otp.isEmpty()){
            throw new ResourceNotFoundException("Phong chieu not found id "+id);
        }
        return otp.get();
    }


    @Transactional
    @Override
    public ApiResponse<?> createAutoGhe(PhongChieuRequestDto phongChieuRequestDto,NhanVien nhanVienId) {
        long idChucNang = 3;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        PhongChieu phongChieu= getPhongChieuById(phongChieuRequestDto.getId());

        Optional<Ghe> firstByPhongChieuId = gheRepository.findFirstByPhongChieuId(phongChieu.getId());

        //Kiểm tra suất chiếu đã tạo vé chưa
        if(firstByPhongChieuId.isPresent()){

            return ApiResponse.fail("Đã tạo ghế cho phòng chiếu này!");
        }

        //tạo ghế tự động
        List<Ghe> danhSachGhe = new ArrayList<>();
        for (int i=0;i<phongChieuRequestDto.getHangDoc();i++){
            for (int j=0;j<phongChieuRequestDto.getHangNgang();j++){
                String tenGhe = (char) ('A' + i) + String.valueOf(j + 1);
                int giaGhe = 45000;

                Ghe saveGhe= new Ghe();
                saveGhe.setTenGhe(tenGhe);
                saveGhe.setGiaGhe(String.valueOf(giaGhe));
                saveGhe.setPhongChieu(phongChieu);
                saveGhe.setLoaiGhe("THUONG");
                danhSachGhe.add(saveGhe);
            }
        }
        List<Ghe> ghes = gheRepository.saveAll(danhSachGhe);
        if (!ghes.isEmpty()) {

            return ApiResponse.success("Tạo ghế tự động cho phòng chiếu thành công!");
        } else {

            return ApiResponse.fail( "Lỗi khi tạo ghế, vui lòng thử lại");
        }

    }


    private PhongChieuResponseDto mapToPhongChieuDto(PhongChieu phongChieu) {
        return new PhongChieuResponseDto(
                phongChieu.getId(),
                phongChieu.getTenPhong(),
                phongChieu.getHangNgang(),
                phongChieu.getHangDoc(),
                phongChieu.getTinhTrang(),
                phongChieu.getHinhAnh()
        );
    }
}
