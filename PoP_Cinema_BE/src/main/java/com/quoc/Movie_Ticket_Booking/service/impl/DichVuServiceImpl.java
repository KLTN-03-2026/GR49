package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.dto.request.ChangStatusDto;
import com.quoc.Movie_Ticket_Booking.dto.request.DichVuRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.*;
import com.quoc.Movie_Ticket_Booking.exception.ResourceAlreadyExistsException;
import com.quoc.Movie_Ticket_Booking.exception.ResourceNotFoundException;
import com.quoc.Movie_Ticket_Booking.model.*;
import com.quoc.Movie_Ticket_Booking.repository.ChiTietDichVuRepository;
import com.quoc.Movie_Ticket_Booking.repository.DichVuRepository;
import com.quoc.Movie_Ticket_Booking.repository.DonHangRepository;
import com.quoc.Movie_Ticket_Booking.service.DichVuService;
import com.quoc.Movie_Ticket_Booking.service.PhanQuyenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DichVuServiceImpl implements DichVuService {

    @Autowired
    private DichVuRepository dichVuRepository;

    @Autowired
    private ChiTietDichVuRepository chiTietDichVuRepository;

    @Autowired
    private DonHangRepository donHangRepository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public  ApiResponse<?> createDichVu(DichVuRequestDto dichVuRequestDto,NhanVien nhanVienId) {

        long idChucNang = 9;
        long idChucVu = nhanVienId.getChucVu().getId();
        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        Boolean existsByTenDichVu = dichVuRepository.existsByTenDichVu(dichVuRequestDto.getTenDichVu().trim());
        if (existsByTenDichVu) {
            throw new ResourceAlreadyExistsException("Dịch vụ có tên '" + dichVuRequestDto.getTenDichVu() + "' đã tồn tại.");
        }

        DichVu dichVu= new DichVu();
        dichVu.setTenDichVu(dichVuRequestDto.getTenDichVu());
        dichVu.setHinhAnh(dichVuRequestDto.getHinhAnh());
        dichVu.setGia(dichVuRequestDto.getGia());
        dichVu.setMoTa(dichVuRequestDto.getMoTa());
        dichVu.setTinhTrang(dichVuRequestDto.getTinhTrang());

        dichVuRepository.save(dichVu);
        return ApiResponse.success("Thêm dịch vụ thành công!");
    }

    @Override
    public ApiResponse<?> updateDichVu(Long id, DichVuRequestDto dichVuRequestDto, NhanVien nhanVienId) {


        long idChucNang = 9;
        long idChucVu = nhanVienId.getChucVu().getId();
        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        DichVu dichVu= getDichVuById(id);

        if (dichVuRequestDto.getTenDichVu() != null) {
            dichVu.setTenDichVu(dichVuRequestDto.getTenDichVu());
        }
        if (dichVuRequestDto.getHinhAnh() != null) {
            dichVu.setHinhAnh(dichVuRequestDto.getHinhAnh());
        }
        if (dichVuRequestDto.getGia() != null) {
            dichVu.setGia(dichVuRequestDto.getGia());
        }
        if (dichVuRequestDto.getMoTa()!= null) {
            dichVu.setMoTa(dichVuRequestDto.getMoTa());
        }
        if (dichVuRequestDto.getTinhTrang()!= null) {
            dichVu.setTinhTrang(dichVuRequestDto.getTinhTrang());
        }
        dichVuRepository.save(dichVu);
        return ApiResponse.success("Cập nhật dịch vụ thành công!");
    }

    @Override
    public ApiResponse<?> deleteDichVu(Long id, NhanVien nhanVienId) {
        long idChucNang = 9;
        long idChucVu = nhanVienId.getChucVu().getId();
        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }


       DichVu dichVu= getDichVuById(id);

        // ✅ Kiểm tra ràng buộc: dịch vụ đã có chi tiết sử dụng chưa
        boolean daTaoChiTiet = chiTietDichVuRepository.existsByDichVuId(id);
        if (daTaoChiTiet) {
            return ApiResponse.fail("Không thể xóa dịch vụ này vì đã tồn tại chi tiết sử dụng!");
        }

       dichVuRepository.delete(dichVu);
        return ApiResponse.success("Xoá dịch vụ thành công!");
    }

    @Override
    public ApiResponse<?> updateStatus(Long id,NhanVien nhanVienId) {
        long idChucNang = 9;
        long idChucVu = nhanVienId.getChucVu().getId();
        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        DichVu dichVuById= getDichVuById(id);
        dichVuById.setTinhTrang(dichVuById.getTinhTrang()==1 ? 0 : 1);
         dichVuRepository.save(dichVuById);
        return ApiResponse.success("Cập nhật trạng thái thành công!");
    }

    @Override
    public List<DichVuResponseDto> getAllDichVu() {
        List<DichVu> dsDichVu = dichVuRepository.findAll();
        List<DichVuResponseDto> dichVuDtoList = dsDichVu.stream()
                .map(this::mapToDichVuDto)
                .toList();
        return dichVuDtoList;
    }

    @Override
    public DichVu getDichVuById(Long id) {
        Optional<DichVu> otp = dichVuRepository.findById(id);
        if(otp.isEmpty()){
            throw new ResourceNotFoundException("Dich Vu not found id "+id);
        }
        return otp.get();
    }


    @Override
    public ApiResponse<?> getDataClientDichVu() {

        List<DichVu> dsDichVu = dichVuRepository.findByTinhTrangGreaterThan(0);
        List<DichVuResponseDto> dichVuDtoList = dsDichVu.stream()
                .map(this::mapToDichVuDto)
                .toList();

        return ApiResponse.success("Hiển thị thành công!",dichVuDtoList);
    }


    private DichVuResponseDto mapToDichVuDto(DichVu dichVu) {
        return new DichVuResponseDto(
                dichVu.getId(),
                dichVu.getTenDichVu(),
                dichVu.getHinhAnh(),
                dichVu.getGia(),
                dichVu.getMoTa(),
                dichVu.getTinhTrang()
        );
    }


    @Override
    public Map<String, Object> getDataChiTietDichVu(Long idDonHang) {

        List<ChiTietDichVuResponseDto> allDichVu = chiTietDichVuRepository.getDichVuByIdDonHang(idDonHang);
        List<VeDetailsResponseDto> veByIdDonHang = donHangRepository.getVeByIdDonHang(idDonHang);
        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("data_dich_vu", allDichVu);
        response.put("ds_ve", veByIdDonHang);
        return response;
    }


    @Override
    public List<DichVu> getDichVuByTinhTrang() {
        return dichVuRepository.findByTinhTrangGreaterThan(0);
    }
}
