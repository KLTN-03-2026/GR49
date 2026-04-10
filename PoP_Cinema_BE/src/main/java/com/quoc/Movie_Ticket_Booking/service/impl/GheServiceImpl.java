package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.dto.request.ChangStatusDto;
import com.quoc.Movie_Ticket_Booking.dto.request.GheRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.*;
import com.quoc.Movie_Ticket_Booking.exception.ResourceAlreadyExistsException;
import com.quoc.Movie_Ticket_Booking.exception.ResourceNotFoundException;
import com.quoc.Movie_Ticket_Booking.model.*;
import com.quoc.Movie_Ticket_Booking.repository.GheRepository;
import com.quoc.Movie_Ticket_Booking.repository.PhanQuyenRepository;
import com.quoc.Movie_Ticket_Booking.repository.PhongChieuRepository;
import com.quoc.Movie_Ticket_Booking.repository.VeRepository;
import com.quoc.Movie_Ticket_Booking.service.GheService;
import com.quoc.Movie_Ticket_Booking.service.PhanQuyenService;
import com.quoc.Movie_Ticket_Booking.service.PhongChieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GheServiceImpl implements GheService {

    @Autowired
    private GheRepository gheRepository;

    @Autowired
    private PhongChieuRepository phongChieuRepository;

    @Autowired
    private PhongChieuService phongChieuService;

    @Autowired
    private VeRepository veRepository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public ApiResponse<?> createGhe(GheRequestDto gheRequestDto,  NhanVien nhanVienId) {

        long idChucNang = 4;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        PhongChieu phongChieuById = phongChieuService.getPhongChieuById(gheRequestDto.getPhongChieu());

        //  1 Kiểm tra trùng tên ghế trong cùng phòng
        boolean exists = gheRepository.existsByTenGheAndPhongChieuId(
                gheRequestDto.getTenGhe(),
                phongChieuById.getId()
        );
        if (exists) {
            return ApiResponse.fail("Ghế " + gheRequestDto.getTenGhe() + " đã tồn tại trong phòng này!");
        }

        // 2  Kiểm tra số lượng ghế tối đa nếu bạn có giới hạn
        int currentCount = gheRepository.countByPhongChieuId(phongChieuById.getId());
        int maxSeats = phongChieuById.getHangDoc() * phongChieuById.getHangNgang();
        if (currentCount >= maxSeats) {
            return ApiResponse.fail("Phòng chiếu đã đầy, không thể thêm ghế mới!");
        }

        //3. tạo ghế
        Ghe ghe = new Ghe();
         ghe.setTenGhe(gheRequestDto.getTenGhe());
         ghe.setGiaGhe(gheRequestDto.getGiaGhe());
         ghe.setLoaiGhe(gheRequestDto.getLoaiGhe());


        ghe.setPhongChieu(phongChieuById);

        gheRepository.save(ghe);
        return ApiResponse.success("Thêm ghế thành công!");
    }

    @Override
    public ApiResponse<?> updateGhe(Long id, GheRequestDto updateDto,  NhanVien nhanVienId) {


        long idChucNang = 4;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        Ghe ghe = getGheById(id);

//        Optional<PhongChieu> idPhongChieu = phongChieuRepository.findById(updateDto.getPhongChieu());
//        if(idPhongChieu.isEmpty()){
//            throw new ResourceNotFoundException("Phong chieu not found id "+updateDto.getPhongChieu());
//        }

        if (updateDto.getTenGhe() != null) {
            ghe.setTenGhe(updateDto.getTenGhe());
        }
        if (updateDto.getGiaGhe() != null) {
            ghe.setGiaGhe(updateDto.getGiaGhe());
        }
        if (updateDto.getLoaiGhe() != null) {
            ghe.setLoaiGhe(updateDto.getLoaiGhe());
        }
        PhongChieu phongChieuById = phongChieuService.getPhongChieuById(updateDto.getPhongChieu());
        if (updateDto.getPhongChieu() != null) {
            ghe.setPhongChieu(phongChieuById);
        }
        if (updateDto.getTinhTrang() != null) {
            ghe.setTinhTrang(updateDto.getTinhTrang());
        }
         gheRepository.save(ghe);
        return ApiResponse.success("Cập nhật ghế thành công!");
    }

    @Override
    public ApiResponse<?> deleteGhe(Long id, NhanVien nhanVienId) {
        long idChucNang = 4;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        Ghe gheById= getGheById(id);

        // ✅ Kiểm tra ràng buộc — ghế đã có vé chưa
        boolean daTaoVe = veRepository.existsByGheId(id);
        if (daTaoVe) {
            return ApiResponse.fail("Không thể xóa ghế này vì đã tồn tại vé được tạo!");
        }

      gheRepository.delete(gheById);
        return ApiResponse.success("Xóa ghế thành công");
    }

    @Override
    public ApiResponse<?> updateStatus(Long id,  NhanVien nhanVienId) {
        long idChucNang = 4;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        Ghe gheById= getGheById(id);
        gheById.setTinhTrang(gheById.getTinhTrang()==1 ? 0 : 1);
        gheRepository.save(gheById);
        return ApiResponse.success("Cập nhật trạng thái thành công!");
    }

    @Override
    public List<GheResponseDto> getAllGhe() {
        List<Ghe> dsGhe = gheRepository.findAll();
        List<GheResponseDto> gheDtoList = dsGhe.stream()
                .map(this::mapToGheDto)
                .toList();
        return gheDtoList;
    }


    @Override
    public GhePhanTrangResponseDto getAllGhePhanTrang(Integer pageNo,Integer pageSize) {
        //10 elements  = 5,5 = 2 pages
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Ghe> pageGhe = gheRepository.findAll(pageable);
        List<GheResponseDto> gheDtoList = pageGhe.stream()
                .map(this::mapToGheDto)
                .toList();

        GhePhanTrangResponseDto ghes =GhePhanTrangResponseDto.builder()
                .ghes(gheDtoList)
                .pageNo(pageGhe.getNumber())
                .pageSize(pageGhe.getSize())
                .totalElements((int) pageGhe.getTotalElements())
                .totalPages(pageGhe.getTotalPages())
                .isFirst(pageGhe.isFirst())
                .isLast(pageGhe.isLast())
                .build();
        return ghes;
    }


    @Override
    public GhePhanTrangResponseDto getGheByPhongChieuId(Long idPhongChieu, Integer pageNo, Integer pageSize) {
        //10 elements  = 5,5 = 2 pages
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Ghe> pageGhe = gheRepository.findByPhongChieuId(idPhongChieu,pageable);
        List<GheResponseDto> gheDtoList = pageGhe.stream()
                .map(this::mapToGheDto)
                .toList();

        GhePhanTrangResponseDto ghes =GhePhanTrangResponseDto.builder()
                .ghes(gheDtoList)
                .pageNo(pageGhe.getNumber())
                .pageSize(pageGhe.getSize())
                .totalElements((int) pageGhe.getTotalElements())
                .totalPages(pageGhe.getTotalPages())
                .isFirst(pageGhe.isFirst())
                .isLast(pageGhe.isLast())
                .build();
        return ghes;
    }

    @Override
    public Map<String, Object> getMapGheByPhongChieuId(Long idPhongChieu) {

        Map<String, Object> response = new HashMap<>();

        //Lất tất cả ghế theo phòng chiếu
        List<Ghe> getGheByPhongChieu = gheRepository.findByPhongChieuIdAndTinhTrang(idPhongChieu, 1);
        List<GheResponseDto> gheDtoList = getGheByPhongChieu.stream()
                .map(this::mapToGheDto)
                .toList();

        PhongChieu phongChieuById = phongChieuService.getPhongChieuById(idPhongChieu);

        //lấy hàng ngang từ phòng chiếu
        Integer hangNgang= phongChieuById.getHangNgang();
        response.put("status", true);
        response.put("data_ghe", gheDtoList);
        response.put("hang_ngang", hangNgang);
        response.put("ten_phong", phongChieuById.getTenPhong());

        return response;
    }

    @Override
    public Ghe getGheById(Long id) {
        Optional<Ghe> otp = gheRepository.findById(id);
        if(otp.isEmpty()){
            throw new ResourceNotFoundException("Ghe not found id "+id);
        }
        return otp.get();
    }


    @Override
    public List<LoaiGheResponseDto> getDistinctLoaiGhe() {
        return gheRepository.findDistinctLoaiGhe();
    }

    private GheResponseDto mapToGheDto(Ghe ghe) {
        return new GheResponseDto(
                ghe.getId(),
                ghe.getTenGhe(),
                ghe.getGiaGhe(),
                ghe.getTinhTrang(),
                ghe.getPhongChieu().getId(),
                ghe.getPhongChieu().getTenPhong(),
                ghe.getLoaiGhe()
        );
    }
}
