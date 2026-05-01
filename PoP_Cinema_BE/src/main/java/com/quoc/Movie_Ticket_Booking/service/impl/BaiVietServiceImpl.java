package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.dto.request.BaiVietRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.BaiVietResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.GheResponseDto;
import com.quoc.Movie_Ticket_Booking.exception.ResourceNotFoundException;
import com.quoc.Movie_Ticket_Booking.model.*;
import com.quoc.Movie_Ticket_Booking.repository.BaiVietRepository;
import com.quoc.Movie_Ticket_Booking.service.BaiVietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BaiVietServiceImpl implements BaiVietService {

    @Autowired
    private BaiVietRepository baiVietRepository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public ApiResponse<?> createBaiViet(BaiVietRequestDto baiVietRequestDto, NhanVien nhanVienId) {

        long idChucNang = 11;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        BaiViet saveBaiViet = new BaiViet();
        saveBaiViet.setTieuDe(baiVietRequestDto.getTieuDe());
        saveBaiViet.setMoTaNgan(baiVietRequestDto.getMoTaNgan());
        saveBaiViet.setNoiDung(baiVietRequestDto.getNoiDung());
        saveBaiViet.setHinhAnh(baiVietRequestDto.getHinhAnh());
        saveBaiViet.setTag(baiVietRequestDto.getTag());
        saveBaiViet.setTinhTrang(baiVietRequestDto.getTinhTrang());
       baiVietRepository.save(saveBaiViet);
        return ApiResponse.success("Thêm bài viết thành công");
    }

    @Override
    public ApiResponse<?> updateBaiViet(Long id,BaiVietRequestDto baiVietRequestDto,NhanVien nhanVienId) {

        long idChucNang = 11;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        BaiViet baiViet = getBaiVietId(id);
        if (baiVietRequestDto.getTieuDe() != null) {
            baiViet.setTieuDe(baiVietRequestDto.getTieuDe());
        }
        if (baiVietRequestDto.getMoTaNgan() != null) {
            baiViet.setMoTaNgan(baiVietRequestDto.getMoTaNgan());
        }
        if (baiVietRequestDto.getNoiDung()!= null) {
            baiViet.setNoiDung(baiVietRequestDto.getNoiDung());
        }
        if (baiVietRequestDto.getHinhAnh()!= null) {
            baiViet.setHinhAnh(baiVietRequestDto.getHinhAnh());
        }
        if (baiVietRequestDto.getTag()!= null) {
            baiViet.setTag(baiVietRequestDto.getTag());
        }
        if (baiVietRequestDto.getTinhTrang()!= null) {
            baiViet.setTinhTrang(baiVietRequestDto.getTinhTrang());
        }
        baiVietRepository.save(baiViet);
        return ApiResponse.success("Cập nhật bài viết thành công");
    }

    @Override
    public ApiResponse<?> updateStatus(Long id,NhanVien nhanVienId) {

        long idChucNang = 11;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }
        BaiViet baiViet = getBaiVietId(id);
        baiViet.setTinhTrang(baiViet.getTinhTrang()==1?0:1);
        baiVietRepository.save(baiViet);
        return ApiResponse.success("Cập nhật trạng thái thành công");
    }

    @Override
    public ApiResponse<?> deleteBaiViet(Long baiVietId,NhanVien nhanVienId) {
        long idChucNang = 11;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }
        BaiViet baiViet = getBaiVietId(baiVietId);
        baiVietRepository.delete(baiViet);
        return ApiResponse.success("Xoá bài viết thành công");
    }

    @Override
    public ApiResponse<?> getAllBaiViet() {

        List<BaiViet> dsBaiViet = baiVietRepository.findAll();
        List<BaiVietResponseDto> baiVietDtoList = dsBaiViet.stream()
                .map(this::mapToBaiVietDto)
                .toList();

        return ApiResponse.success("hiển thị thành công",baiVietDtoList);
    }

    @Override
    public BaiViet getBaiVietId(Long id) {
        Optional<BaiViet> otp = baiVietRepository.findById(id);
        if(otp.isEmpty()){
            throw new ResourceNotFoundException("bai Viet not found id "+id);
        }
        return otp.get();
    }

    @Override
    public ApiResponse<?> getDataClientBaiViet() {
        List<BaiViet> dsBaiViet = baiVietRepository.findByTinhTrangGreaterThan(0);
        List<BaiVietResponseDto> baiVietDtoList = dsBaiViet.stream()
                .map(this::mapToBaiVietDto)
                .toList();

        return ApiResponse.success("hiển thị thành công",baiVietDtoList);
    }

    @Override
    public ApiResponse<?> getChiTietBaiViet(Long id) {
        // Lấy bài viết theo id
        BaiViet baiViet = getBaiVietId(id);

        // Nếu không tìm thấy bài viết
        if (baiViet == null) {
            return ApiResponse.fail("Bài viết không tồn tại");
        }

        // Chuyển sang DTO
        BaiVietResponseDto baiVietResponseDto = mapToBaiVietDto(baiViet);

        // Trả về thành công
        return ApiResponse.success("Hiển thị thành công", baiVietResponseDto);
    }

    private BaiVietResponseDto mapToBaiVietDto(BaiViet baiViet) {
        return new BaiVietResponseDto(
                baiViet.getId(),
                baiViet.getTieuDe(),
                baiViet.getMoTaNgan(),
                baiViet.getNoiDung(),
                baiViet.getHinhAnh(),
                baiViet.getTag(),
                baiViet.getTinhTrang()
        );
    }


}
