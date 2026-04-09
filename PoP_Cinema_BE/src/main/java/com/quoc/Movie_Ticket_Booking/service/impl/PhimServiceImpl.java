package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.dto.request.ChangStatusDto;
import com.quoc.Movie_Ticket_Booking.dto.request.PhimRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.PhimHomeResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.PhimResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.SuatChieuResponseDto;
import com.quoc.Movie_Ticket_Booking.exception.ResourceAlreadyExistsException;
import com.quoc.Movie_Ticket_Booking.exception.ResourceNotFoundException;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.PhanQuyen;
import com.quoc.Movie_Ticket_Booking.model.Phim;
import com.quoc.Movie_Ticket_Booking.model.Users;
import com.quoc.Movie_Ticket_Booking.repository.PhanQuyenRepository;
import com.quoc.Movie_Ticket_Booking.repository.PhimRepository;
import com.quoc.Movie_Ticket_Booking.repository.SuatChieuRepository;
import com.quoc.Movie_Ticket_Booking.service.PhanQuyenService;
import com.quoc.Movie_Ticket_Booking.service.PhimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PhimServiceImpl implements PhimService {

    @Autowired
    private PhimRepository phimRepository;

    @Autowired
    private SuatChieuRepository suatChieuRepository;

    @Autowired
    private PermissionService permissionService;



    @Override
    public ApiResponse<?> createPhim(PhimRequestDto phimRequestDto, NhanVien nhanVienId) {

        long idChucNang = 1;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        Boolean existsByName = phimRepository.existsByTenPhim(phimRequestDto.getTenPhim().trim());
        if (existsByName) {
            throw new ResourceAlreadyExistsException("Phim có tên '" + phimRequestDto.getTenPhim() + "' đã tồn tại.");
        }
        Phim phim = new Phim();
        phim.setTenPhim(phimRequestDto.getTenPhim());
        phim.setDaoDien(phimRequestDto.getDaoDien());
        phim.setDienVien(phimRequestDto.getDienVien());
        phim.setHinhAnh(phimRequestDto.getHinhAnh());
        phim.setQuocGia(phimRequestDto.getQuocGia());
        phim.setNhaSanXuat(phimRequestDto.getNhaSanXuat());
        phim.setTrailer(phimRequestDto.getTrailer());
        phim.setNgayPhatHanh(phimRequestDto.getNgayPhatHanh());
        phim.setNgonNgu(phimRequestDto.getNgonNgu());
        phim.setTheLoai(phimRequestDto.getTheLoai());
        phim.setThoiLuong(phimRequestDto.getThoiLuong());
        phim.setNoiDung(phimRequestDto.getNoiDung());

         phimRepository.save(phim);

        return ApiResponse.success("Thêm phim thành công");
    }

    @Override
    public ApiResponse<?> updatePhim(Long id, PhimRequestDto updatePhimDto,NhanVien nhanVienId) {

        long idChucNang = 1;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        Phim phim = getPhimById(id);

        if (updatePhimDto.getTenPhim() != null) {
            phim.setTenPhim(updatePhimDto.getTenPhim());
        }
        if (updatePhimDto.getDaoDien() != null) {
            phim.setDaoDien(updatePhimDto.getDaoDien());
        }
        if (updatePhimDto.getDienVien() != null) {
            phim.setDienVien(updatePhimDto.getDienVien());
        }
        if (updatePhimDto.getHinhAnh() != null) {
            phim.setHinhAnh(updatePhimDto.getHinhAnh());
        }
        if (updatePhimDto.getQuocGia() != null) {
            phim.setQuocGia(updatePhimDto.getQuocGia());
        }
        if (updatePhimDto.getNhaSanXuat() != null) {
            phim.setNhaSanXuat(updatePhimDto.getNhaSanXuat());
        }
        if (updatePhimDto.getTrailer() != null) {
            phim.setTrailer(updatePhimDto.getTrailer());
        }
        if (updatePhimDto.getNgayPhatHanh() != null) {
            phim.setNgayPhatHanh(updatePhimDto.getNgayPhatHanh());
        }
        if (updatePhimDto.getNgonNgu() != null) {
            phim.setNgonNgu(updatePhimDto.getNgonNgu());
        }
        if (updatePhimDto.getTheLoai() != null) {
            phim.setTheLoai(updatePhimDto.getTheLoai());
        }
        if (updatePhimDto.getThoiLuong() != null) {
            phim.setThoiLuong(updatePhimDto.getThoiLuong());
        }
        if (updatePhimDto.getNoiDung() != null) {
            phim.setNoiDung(updatePhimDto.getNoiDung());
        }
        if (updatePhimDto.getTinhTrang() != null) {
            phim.setTinhTrang(updatePhimDto.getTinhTrang());
        }

        phimRepository.save(phim);

        return ApiResponse.success("Cập nhật phim thành công");
    }

    @Override
    public ApiResponse<?> deletePhim(Long id,NhanVien nhanVienId) {
        long idChucNang = 1;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }


        Phim phim = getPhimById(id);

        // ✅ Kiểm tra xem phim có suất chiếu nào không
        boolean daCoSuatChieu = suatChieuRepository.existsByPhimId(id);
        if (daCoSuatChieu) {
            return ApiResponse.fail("Không thể xóa phim này vì đã tồn tại suất chiếu liên quan!");
        }

        phimRepository.delete(phim);
        return ApiResponse.success("Xóa phim thành công");
    }

    @Override
    public ApiResponse<?> updateStatus(ChangStatusDto dto,NhanVien nhanVienId) {
        long idChucNang = 1;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        Optional<Phim> phimById = phimRepository.findById(dto.getId());
        Phim phim = phimById.get();
        Integer curretnStatus = phim.getTinhTrang();

        if(Objects.equals(curretnStatus,Phim.DANG_CHIEU)){
            phim.setTinhTrang(Phim.NGUNG_CHIEU);
        }else if(Objects.equals(curretnStatus,Phim.SAP_CHIEU)){
            phim.setTinhTrang(Phim.DANG_CHIEU);
        }else{
            phim.setTinhTrang(Phim.SAP_CHIEU);
        }
         phimRepository.save(phim);
        return ApiResponse.success("Cập nhật trạng thái thành công");
    }


    @Override
    public List<PhimResponseDto> getAllPhim() {
        List<Phim> allPhim = phimRepository.findAll();
        List<PhimResponseDto> phimDtoList = allPhim.stream()
                .map(this::mapToAllPhimDto)
                .toList();
        return phimDtoList;
    }

    @Override
    public Phim getPhimById(Long id) {
        Optional<Phim> otp = phimRepository.findById(id);
        if(otp.isEmpty()){
            throw new ResourceNotFoundException("Movie not found id "+id);
        }
        return otp.get();
    }

    @Override
    public Map<String, Object> getDataClientPhim() {
        List<Phim> listPhim = phimRepository.findByTinhTrangGreaterThan(0);
        List<PhimHomeResponseDto> phimDtoList = listPhim.stream()
                .map(this::mapToPhimDto)
                .toList();

        Map<String, Object> response = new HashMap<>();


        response.put("status", true);
        response.put("data_phim", phimDtoList);
        return response;
    }


    @Override
    public ApiResponse<?> getPhimGoiY() {
        List<Phim> listPhim = phimRepository.findTop4ByTinhTrangOrderByNgayPhatHanhDesc(2);
        List<PhimHomeResponseDto> phimDtoGoiY = listPhim.stream()
                .map(this::mapToPhimDto)
                .toList();

        return ApiResponse.success("Hiển thị thành công",phimDtoGoiY);
    }


    @Override
    public List<PhimHomeResponseDto> getPhimDangChieu() {
        List<Phim> allPhim = phimRepository.findByTinhTrang(2);
        List<PhimHomeResponseDto> phimDtoList = allPhim.stream()
                .map(this::mapToPhimDto)
                .toList();
        return phimDtoList;
    }

    @Override
    public List<PhimHomeResponseDto> getPhimSapChieu() {
        List<Phim> allPhim = phimRepository.findByTinhTrang(1);
        List<PhimHomeResponseDto> phimDtoList = allPhim.stream()
                .map(this::mapToPhimDto)
                .toList();
        return phimDtoList;
    }

    private PhimHomeResponseDto mapToPhimDto(Phim phim) {
        return PhimHomeResponseDto.builder()
                .id(phim.getId())
                .tenPhim(phim.getTenPhim())
                .hinhAnh(phim.getHinhAnh())
                .theLoai(phim.getTheLoai())
                .ngayPhatHanh(phim.getNgayPhatHanh())
                .tinhTrang(phim.getTinhTrang())
                .build();
    }

    private PhimResponseDto mapToAllPhimDto(Phim phim) {
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
                .tinhTrang(phim.getTinhTrang())
                .build();
    }


    @Override
    public List<Phim> getPhimDangChieuu() {
        List<Phim> allPhim = phimRepository.findByTinhTrang(2);
        return allPhim;
    }
}
