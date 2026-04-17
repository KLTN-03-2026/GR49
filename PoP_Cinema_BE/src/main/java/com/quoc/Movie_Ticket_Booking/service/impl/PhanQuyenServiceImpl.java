package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.dto.request.PhanQuyenRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.ChucNangResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.GheResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.PhanQuyenResponseDto;
import com.quoc.Movie_Ticket_Booking.exception.ResourceNotFoundException;
import com.quoc.Movie_Ticket_Booking.model.*;
import com.quoc.Movie_Ticket_Booking.repository.ChucNangRepository;
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
public class PhanQuyenServiceImpl implements PhanQuyenService {

    @Autowired
    private PhanQuyenRepository phanQuyenRepository;

    @Autowired
    private ChucNangRepository chucNangRepository;

    @Autowired
    private ChucVuService chucVuService;

    @Autowired
    private PermissionService permissionService;


    @Override
    public ApiResponse<?> addPhanQuyen(PhanQuyenRequestDto phanQuyenRequestDto, NhanVien nhanVien) {
        long idChucNang = 5;
        long idChucVu = nhanVien.getChucVu().getId();

//        Optional<PhanQuyen> checkQuyen = phanQuyenRepository.findByChucNangIdAndChucVuId(idChucNang, idChucVu);
//        if(checkQuyen.isEmpty()){
//            response.put("status", false);
//            response.put("message", "bạn không có quyền thực hiện chức năng này!");
//            return response;
//        }

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }


        //Kiểm tra tồn tại quyền đã đươcj cấp
//        Optional<PhanQuyen> existsQuyen = phanQuyenRepository.findByChucNangIdAndChucVuId(phanQuyenRequestDto.getIdChucNang(), phanQuyenRequestDto.getIdChucVu());
//        if(!existsQuyen.isEmpty()){
//            response.put("status", false);
//            response.put("message", "Quyền đã được cấp!");
//            return ApiResponse.success("Quyền đã được cấp!");
//        }

        //Kiểm tra tồn tại quyền đã đươcj cấp
        if(permissionService.checkQuyen(phanQuyenRequestDto.getIdChucNang(), phanQuyenRequestDto.getIdChucVu())){
            return ApiResponse.fail("Quyền đã được cấp!");
        }


        //tạo quyền
        PhanQuyen phanQuyen = new PhanQuyen();
        ChucNang chucNangId = getChucNangId(phanQuyenRequestDto.getIdChucNang());
        phanQuyen.setChucNang(chucNangId);
        ChucVu chucVuId = chucVuService.getChucVuId(phanQuyenRequestDto.getIdChucVu());
        phanQuyen.setChucVu(chucVuId);
        PhanQuyen savePhanQuyen=phanQuyenRepository.save(phanQuyen);

        return ApiResponse.success("Thêm phân quyền thành công!");
    }


    @Override
    public ApiResponse<?> deletePhanQuyen(Long phanQuyenId,NhanVien nhanVien) {
        long idChucNang = 5;
        long idChucVu = nhanVien.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        deletePhanQuyen(phanQuyenId);
        return ApiResponse.success("Xoá quyền thành công!");
    }


    @Override
    public ApiResponse<?> getPhanQuyenByChucVuId(Long chucVuId,NhanVien nhanVien) {


        long idChucNang = 5;
        long idChucVu = nhanVien.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }


        List<PhanQuyenResponseDto> allPhanQuyenByChucVuId = phanQuyenRepository.getAllPhanQuyenByChucVuId(chucVuId);

        return ApiResponse.success("List phân quyen thành công.",allPhanQuyenByChucVuId);
    }

    @Override
    public ChucNang getChucNangId(Long id) {
        Optional<ChucNang> otp = chucNangRepository.findById(id);
        if(otp.isEmpty()){
            throw new ResourceNotFoundException("Chuc Nang not found id "+id);
        }
        return otp.get();
    }




    @Override
    public ApiResponse<?> getAllChucNang() {

        List<ChucNang> dsChucNang = chucNangRepository.findAll();
        List<ChucNangResponseDto> chucNangDtoList = dsChucNang.stream()
                .map(this::mapToChucNangDto)
                .toList();

        return ApiResponse.success("List Chuc năng thành công.",chucNangDtoList);
    }


    private ChucNangResponseDto mapToChucNangDto(ChucNang chucNang) {
        return new ChucNangResponseDto(
                chucNang.getId(),
                chucNang.getTenChucNang()
        );
    }


    @Override
    public void deletePhanQuyen(Long id) {
        PhanQuyen phanQuyenId = getPhanQuyenId(id);
        phanQuyenRepository.delete(phanQuyenId);
    }

    @Override
    public PhanQuyen getPhanQuyenId(Long id) {
        Optional<PhanQuyen> otp = phanQuyenRepository.findById(id);
        if(otp.isEmpty()){
            throw new ResourceNotFoundException("Phan Quyen not found id "+id);
        }
        return otp.get();
    }

}
