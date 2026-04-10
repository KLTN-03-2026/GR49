package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.dto.request.SuatChieuRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.GheResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.PhimResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.SuatChieuResponseDto;
import com.quoc.Movie_Ticket_Booking.exception.ResourceNotFoundException;
import com.quoc.Movie_Ticket_Booking.model.*;
import com.quoc.Movie_Ticket_Booking.repository.*;
import com.quoc.Movie_Ticket_Booking.service.*;
import jakarta.annotation.security.DenyAll;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SuatChieuServiceImpl implements SuatChieuService {

    @Autowired
    private SuatChieuRepository suatChieuRepository;

    @Autowired
    private PhimService phimService;

    @Autowired
    private PhongChieuService phongChieuService;

    @Autowired
    private GheRepository gheRepository;

    @Autowired
    private VeRepository veRepository;


    @Autowired
    private PermissionService permissionService;

    @Override
    public ApiResponse<?> createSuatChieu(SuatChieuRequestDto suatChieuRequestDto, NhanVien nhanVienId) {
        long idChucNang = 2;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

//        1. Kiểm tra trùng suất chiếu
        List<SuatChieu> checkSuatChieu = suatChieuRepository.checkTrungSuatChieu(suatChieuRequestDto.getIdPhongChieu(),
                suatChieuRequestDto.getNgayChieu(), suatChieuRequestDto.getThoiGianBatDau(),
                suatChieuRequestDto.getThoiGianKetThuc());

        if (!checkSuatChieu.isEmpty()){

            return ApiResponse.fail("Suất chiếu bị trùng thời gian trong cùng phòng!");
        }


        //        Tạo suất chiếu mới
        SuatChieu suatChieu = new SuatChieu();
        Phim phimById = phimService.getPhimById(suatChieuRequestDto.getIdPhim());
        suatChieu.setPhim(phimById);
        PhongChieu phongChieuById = phongChieuService.getPhongChieuById(suatChieuRequestDto.getIdPhongChieu());
        suatChieu.setPhongChieu(phongChieuById);
        suatChieu.setNgayChieu(suatChieuRequestDto.getNgayChieu());
        suatChieu.setThoiGianBatDau(suatChieuRequestDto.getThoiGianBatDau());
        suatChieu.setThoiGianKetThuc(suatChieuRequestDto.getThoiGianKetThuc());
        suatChieuRepository.save(suatChieu);
        return ApiResponse.success("Tạo suất chiếu thành công.");

    }


    @Override
    public ApiResponse<?> updateSuatChieu(Long id, SuatChieuRequestDto updateDto,NhanVien nhanVienId) {
        long idChucNang = 2;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }


        //2. Cập nhật suất chiếu
        SuatChieu suatChieu= getSuatChieuById(id);

        Phim phimById = phimService.getPhimById(updateDto.getIdPhim());

        if (updateDto.getIdPhim() != null) {
            suatChieu.setPhim(phimById);
        }
        PhongChieu phongChieuById = phongChieuService.getPhongChieuById(updateDto.getIdPhongChieu());
        if (updateDto.getIdPhongChieu() != null) {
            suatChieu.setPhongChieu(phongChieuById);
        }
        if (updateDto.getNgayChieu() != null) {
            suatChieu.setNgayChieu(updateDto.getNgayChieu());
        }
        if (updateDto.getThoiGianBatDau() != null) {
            suatChieu.setThoiGianBatDau(updateDto.getThoiGianBatDau());
        }
        if (updateDto.getThoiGianKetThuc() != null) {
        suatChieu.setThoiGianKetThuc(updateDto.getThoiGianKetThuc());
    }
        if (updateDto.getTinhTrang() != null) {
            suatChieu.setTinhTrang(updateDto.getTinhTrang());
        }
        suatChieuRepository.save(suatChieu);
        return ApiResponse.success("Cập nhật suất chiếu thành công.");

    }

    @Override
    public ApiResponse<?> deleteSuatChieu(Long id,NhanVien nhanVienId) {
        long idChucNang = 2;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        SuatChieu suatChieu= getSuatChieuById(id);

        // ✅ Kiểm tra ràng buộc — suất chiếu đã có vé chưa
        boolean daTaoVe = veRepository.existsBySuatChieuId(id);
        if (daTaoVe) {
            return ApiResponse.fail("Không thể xóa suất chiếu này vì đã tồn tại vé được tạo!");
        }


        suatChieuRepository.delete(suatChieu);
        return ApiResponse.success("Xóa suất chiếu thành công");
    }

    @Override
    public ApiResponse<?> updateStatus(Long id,NhanVien nhanVienId) {
        long idChucNang = 2;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        SuatChieu suatChieu= getSuatChieuById(id);
        suatChieu.setTinhTrang(suatChieu.getTinhTrang()==1?0:1);
        suatChieuRepository.save(suatChieu);
        return ApiResponse.success("Cập nhật trạng thái thành công!");
    }

    @Override
    public List<SuatChieuResponseDto> getAllSuatChieu() {
        return suatChieuRepository.getAllSuatChieuWithDetails();
    }

    @Override
    public SuatChieu getSuatChieuById(Long id) {
        Optional<SuatChieu> otp = suatChieuRepository.findById(id);
        if(otp.isEmpty()){
            throw new ResourceNotFoundException("Suat chieu not found id "+id);
        }
        return otp.get();
    }

    @Override
    public Map<String, Object> getChiTietPhimAndSuatChieu(Long id) {
        Map<String, Object> response = new HashMap<>();

        Phim phimById = phimService.getPhimById(id);

        if (phimById != null) {

            PhimResponseDto phimDto = mapToPhimDto(phimById);



            List<SuatChieu> dsSuatChieu = suatChieuRepository.findByPhimIdAndTinhTrang(id, 1);

            List<SuatChieuResponseDto> suatChieuDtoList = dsSuatChieu.stream()
                    .map(this::mapToSuatChieuDto)
                    .toList();

            response.put("status", true);
            response.put("data_phim", phimDto);
            response.put("data_suat_chieu", suatChieuDtoList);
        } else {
            response.put("status", false);
            response.put("message", "Không tồn tại phim này!!!");
        }

        return response;
    }



    private PhimResponseDto mapToPhimDto(Phim phim) {
        return PhimResponseDto.builder()
                .id(phim.getId())
                .tenPhim(phim.getTenPhim())
                .daoDien(phim.getDaoDien())
                .dienVien(phim.getDienVien())
                .hinhAnh(phim.getHinhAnh())
                .ngonNgu(phim.getNgonNgu())
                .noiDung(phim.getNoiDung())
                .quocGia(phim.getQuocGia())
                .nhaSanXuat(phim.getNhaSanXuat())
                .trailer(phim.getTrailer())
                .ngayPhatHanh(phim.getNgayPhatHanh())
                .theLoai(phim.getTheLoai())
                .thoiLuong(phim.getThoiLuong())
                .noiDung(phim.getNoiDung())
                .build();
    }

    private SuatChieuResponseDto mapToSuatChieuDto(SuatChieu sc) {
        return new SuatChieuResponseDto(
                sc.getId(),
                sc.getPhim().getId(),
                sc.getPhim().getTenPhim(),
                sc.getPhongChieu().getId(),
                sc.getPhongChieu().getTenPhong(),
                sc.getNgayChieu(),
                sc.getThoiGianBatDau(),
                sc.getThoiGianKetThuc(),
                sc.getTinhTrang()
        );
    }


    @Transactional
    @Override
    public ApiResponse<?> createVeAuto(SuatChieuResponseDto dto,NhanVien nhanVienId) {

        long idChucNang = 2;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }


        Optional<Ve> firstBySuatChieuId = veRepository.findFirstBySuatChieuId(dto.getId());

        //Kiểm tra suất chiếu đã tạo vé chưa
        if(firstBySuatChieuId.isPresent()){
            return ApiResponse.fail("Đã tạo vé cho suất chiếu này!");

        }



        //Kiểm tra phòng chiếu có ghế chưa
        List<Ghe> danhSachGhe = gheRepository.findByPhongChieuIdAndTinhTrang(dto.getIdPhongChieu(),1);
        if (danhSachGhe.isEmpty()) {
            return ApiResponse.fail("Phòng chiếu này hiện chưa có ghế!");

        }


        //Xử lý tạo vé theo suất chiếu
        SuatChieu idSuatChieu= getSuatChieuById(dto.getId());
        List<Ve> danhSachVe = new ArrayList<>();
        for (Ghe ghes: danhSachGhe){
            Ve ve = new Ve();
            ve.setMaVe(UUID.randomUUID().toString());
            ve.setDonHang(null);
            ve.setSuatChieu(idSuatChieu);
            ve.setTenGhe(ghes.getTenGhe());
            ve.setCreatedAt(LocalDateTime.now());

            int giaVe=0 ;
            if ("THUONG".equalsIgnoreCase(ghes.getLoaiGhe())) {
                giaVe = Integer.valueOf(ghes.getGiaGhe());
            }
            else if ("VIP".equalsIgnoreCase(ghes.getLoaiGhe())) {
                giaVe = Integer.valueOf(ghes.getGiaGhe());
            }

            ve.setGiaVe(giaVe);

            // Gắn ghế cho vé (chỉ set ID, không save ghế)
            Ghe gheRef = new Ghe();
            gheRef.setId(ghes.getId());
            ve.setGhe(gheRef);

            danhSachVe.add(ve);
        }
        List<Ve> ves = veRepository.saveAll(danhSachVe);

        if (!ves.isEmpty()) {
            return ApiResponse.success( "Tạo vé tự động cho suất chiếu thành công!");
        } else {

            return ApiResponse.fail("Lỗi khi tạo vé, vui lòng thử lại");
        }

    }


    @Override
    public List<SuatChieu> getSuatChieuByPhimId(Long id) {
        List<SuatChieu> dsSuatChieu = suatChieuRepository.findByPhimIdAndTinhTrang(id, 1);
        return dsSuatChieu;
    }
}
