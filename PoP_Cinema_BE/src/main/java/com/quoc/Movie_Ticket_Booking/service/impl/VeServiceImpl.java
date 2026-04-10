package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.dto.request.VeRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.*;
import com.quoc.Movie_Ticket_Booking.exception.ResourceNotFoundException;
import com.quoc.Movie_Ticket_Booking.model.*;
import com.quoc.Movie_Ticket_Booking.repository.GheRepository;
import com.quoc.Movie_Ticket_Booking.repository.PhanQuyenRepository;
import com.quoc.Movie_Ticket_Booking.repository.SuatChieuRepository;
import com.quoc.Movie_Ticket_Booking.repository.VeRepository;
import com.quoc.Movie_Ticket_Booking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
public class VeServiceImpl implements VeService {


    @Autowired
    private SuatChieuRepository suatChieuRepository;


    @Autowired
    private SuatChieuService suatChieuService;


    @Autowired
    private VeRepository veRepository;

    @Autowired
    private DonHangService donHangService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private GheService gheService;

    @Override
    public Ve createVe(VeRequestDto veRequestDto) {
        SuatChieu suatChieuById = suatChieuService.getSuatChieuById(veRequestDto.getIdSuatChieu());
        DonHang donHangById = donHangService.getDonHangById(veRequestDto.getIdDonHang());

        Ve saveVe = new Ve();
        saveVe.setMaVe(UUID.randomUUID().toString());
        saveVe.setGiaVe(Integer.valueOf(veRequestDto.getGiaVe()));
        saveVe.setDonHang(null);
        saveVe.setSuatChieu(suatChieuById);
        saveVe.setTenGhe(veRequestDto.getTenGhe());



        return null;
    }

    @Override
    public ApiResponse<?> updateVe(Long id, VeRequestDto updateDto, NhanVien nhanVienId) {

        long idChucNang = 8;
        long idChucVu = nhanVienId.getChucVu().getId();
        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        Ve veById = getVeById(id);

        // Cập nhật giá vé
        if (updateDto.getGiaVe() != null) {
            veById.setGiaVe(updateDto.getGiaVe());
        }

        // Cập nhật tên ghế
        if (updateDto.getTenGhe() != null) {
            veById.setTenGhe(updateDto.getTenGhe());
        }

        // Cập nhật suất chiếu (chỉ khi có ID)
        if (updateDto.getIdSuatChieu() != null) {
            SuatChieu suatChieu = suatChieuService.getSuatChieuById(updateDto.getIdSuatChieu());
            veById.setSuatChieu(suatChieu);
        }

        // Cập nhật đơn hàng (chỉ khi có ID)
        if (updateDto.getIdDonHang() != null) {
            DonHang donHang = donHangService.getDonHangById(updateDto.getIdDonHang());
            veById.setDonHang(donHang);
        }

        // Cập nhật tình trạng
        if (updateDto.getTinhTrang() != null) {
            veById.setTinhTrang(updateDto.getTinhTrang());
        }

        veRepository.save(veById);
        return ApiResponse.success("Cập nhật vé thành công!");

    }

    @Override
    public ApiResponse<?> updateStatus(Long id,NhanVien nhanVienId) {

        long idChucNang = 8;
        long idChucVu = nhanVienId.getChucVu().getId();
        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        Ve veById = getVeById(id);
        Integer curretnStatus = veById.getTinhTrang();

        if(Objects.equals(curretnStatus,Ve.CHUA_THANH_TOAN)){ //1
            veById.setTinhTrang(Ve.DA_THANH_TOAN); //2
        }else if(Objects.equals(curretnStatus,Ve.DA_HUY)){ //0
            veById.setTinhTrang(Ve.CHUA_THANH_TOAN); // 1
        }else{
            veById.setTinhTrang(Ve.DA_HUY); //0
        }
          veRepository.save(veById);

        return ApiResponse.success("Cập nhật trạng thái thành công!");
    }


    @Override
    public ApiResponse<?> deleteVe(Long id,NhanVien nhanVienId) {

        long idChucNang = 8;
        long idChucVu = nhanVienId.getChucVu().getId();
        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        Ve veById = getVeById(id);
        veById.setDonHang(null);
        veRepository.save(veById);
        return ApiResponse.success("Huỷ vé của khách hàng thành công!");
    }

    @Override
    public ApiResponse<?> destroyVe(Long id, NhanVien nhanVienId) {
        long idChucNang = 8;
        long idChucVu = nhanVienId.getChucVu().getId();
        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }


        Ve veById = getVeById(id);

        veRepository.delete(veById);
        return ApiResponse.success("Xóa vé thành công!");
    }

    @Override
    public Ve getVeById(Long id) {
        Optional<Ve> otp = veRepository.findById(id);
        if(otp.isEmpty()){
            throw new ResourceNotFoundException("Ve not found id "+id);
        }
        return otp.get();
    }

    @Override
    public List<VeResponseDto> getAllVe() {
        return veRepository.getAllVeWithDetails();
    }

    @Override
    public VePhanTrangResponseDto getAllVePhanTrang(Integer pageNo, Integer pageSize) {
        //10 elements  = 5,5 = 2 pages
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<VeResponseDto> pageVes = veRepository.getAllVePaginationWithDetails(pageable);
        VePhanTrangResponseDto ves =VePhanTrangResponseDto.builder()
                .ves(pageVes.getContent())
                .pageNo(pageVes.getNumber())
                .pageSize(pageVes.getSize())
                .totalElements((int) pageVes.getTotalElements())
                .totalPages(pageVes.getTotalPages())
                .isFirst(pageVes.isFirst())
                .isLast(pageVes.isLast())
                .build();
        return ves;
    }

    @Override
    public Map<String, Object> searchVe(String maVe) {
        Map<String,Object> response = new HashMap<>();
        List<VeResponseDto> veList=null;

        if (maVe!= null && maVe.length()>0) {
          veList = veRepository.searchVeByMaVe(maVe);

            if (ObjectUtils.isEmpty(veList)) {

                response.put("status", false);
                response.put("message","Không tìm thấy vé." );
            }
            else{
                response.put("status", true);
                response.put("data", veList);
                response.put("message", "Tìm thấy vé thành công");
            }
        }else{
            veList=veRepository.getAllVeWithDetails();
            response.put("status", true );
            response.put("data",veList);
            response.put("message", "Danh sách tất cả vé.");
        }


        return response;
    }

        @Override
        public ApiResponse<?> searchVeByPhanTrang(String maVe,Integer pageNo, Integer pageSize) {
            String message;

            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<VeResponseDto> pageVes;

            // Nếu có mã vé thì tìm theo mã
            if (maVe != null && !maVe.trim().isEmpty()) {
                pageVes = veRepository.searchVeByMaVePhanTrang(maVe.trim(), pageable);

                if (pageVes.isEmpty()) {
                    return ApiResponse.fail("Không tìm thấy vé nào khớp mã '" + maVe + "'.");
                }

                message = "Tìm thấy vé khớp mã '" + maVe + "'.";
            }
            // Nếu không có mã thì lấy tất cả vé
            else {
                pageVes = veRepository.getAllVePaginationWithDetails(pageable);
                message = "Danh sách tất cả vé.";
            }

            // Đóng gói dữ liệu phân trang
            VePhanTrangResponseDto dto = VePhanTrangResponseDto.builder()
                    .ves(pageVes.getContent())
                    .pageNo(pageVes.getNumber())
                    .pageSize(pageVes.getSize())
                    .totalElements((int) pageVes.getTotalElements())
                    .totalPages(pageVes.getTotalPages())
                    .isFirst(pageVes.isFirst())
                    .isLast(pageVes.isLast())
                    .build();

            // Trả thẳng dto làm data
            return ApiResponse.success(message, dto.getVes());
        }

    @Override
    public Map<String, Object> getClientAllVe(Long idSuatChieu) {

        Map<String, Object> response = new HashMap<>();


        //cách 1 lồng entity vào dto
        List<Ve> allVe = veRepository.findBySuatChieuId(idSuatChieu);

//        List<AllVeResponseDto> veDtoList = allVe.stream()
//                .map(this::mapToVeDto)
//                .toList();

        //Cách 2 bằng JPQL
        //Lấy ds vé theo id suất chiếu
        List<AllVeResponseDto> veDtoList= veRepository.getDatVeBySuatChieuId(idSuatChieu);


        //Lấy suất chiếu theo id suất chiêú
        SuatChieu suatChieuById = suatChieuService.getSuatChieuById(idSuatChieu);

        //Lấy thông tin phim và suất chiếu theo id suất chiếu
        ThongTinPhimResponseDto thongTinPhimWithSuatChieu = suatChieuRepository.getThongTinPhimWithSuatChieu(idSuatChieu);

        //lấy hàng ngang từ phòng chiếu
        Integer hangNgang= suatChieuById.getPhongChieu().getHangNgang();

        String tenHinhAnh = suatChieuById.getPhongChieu().getHinhAnh();
        response.put("status", true);
        response.put("data_ve", veDtoList);
        response.put("data_phim", thongTinPhimWithSuatChieu);
        response.put("hang_ngang", hangNgang);
        response.put("hinh_anh", tenHinhAnh);


        return response;
    }


    @Override
    public List<VeResponseDto> getAllVeChuaThanhToan() {
        return veRepository.getAllVeChuaThanhToan();
    }




//    private AllVeResponseDto mapToVeDto(Ve v) {
//        ;
//
//        return new AllVeResponseDto(
//                v.getId(),
//                v.getMaVe(),
//                v.getGiaVe(),
//                v.getDonHang().getId(),
//                v.getSuatChieu().getId(),
//                v.getTenGhe(),
//                v.getTinhTrang(),
//                v.getGhe().getLoaiGhe()
//
//        );
//    }
}
