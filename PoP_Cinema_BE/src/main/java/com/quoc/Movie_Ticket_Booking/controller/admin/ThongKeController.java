package com.quoc.Movie_Ticket_Booking.controller.admin;

import com.quoc.Movie_Ticket_Booking.dto.request.NgayRequestDto;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/thong-ke")
public class ThongKeController {

    @Autowired
    private ThongKeService thongkeService;



    @GetMapping("/get-doanh-thu")
    public ResponseEntity<Map<String, Object>> getDoanhThuDonHang( @RequestHeader("Authorization") String jwt) {
//        NhanVien nhanVien = nhanVienService.findUserByJwtToken(jwt);

        Map<String, Object> doanhThu = thongkeService.getAllDoanhThu();
        return new ResponseEntity<>(doanhThu, HttpStatus.OK);
    }

    @PostMapping("/thong-ke-doanh-thu")
    public ResponseEntity<?> getDoanhThuTheoNgay(
            @RequestBody NgayRequestDto ngayRequestDto) {

        Map<String, Object> doanhThu = thongkeService.getDoanhThuTheoNgay(ngayRequestDto.getNgayBatDau(), ngayRequestDto.getNgayKetThuc());
        return new ResponseEntity<>(doanhThu, HttpStatus.OK);
    }



    @PostMapping("/thong-ke-ve-ban")
    public ResponseEntity<?> getVeBanTheoNgay(@RequestBody NgayRequestDto ngayRequestDto) {

        Map<String, Object> thongKeVe = thongkeService.getVeDaBanTheoNgay(ngayRequestDto.getNgayBatDau(), ngayRequestDto.getNgayKetThuc());
        return new ResponseEntity<>(thongKeVe, HttpStatus.OK);
    }

    @PostMapping("/thong-ke-suat-chieu")
    public ResponseEntity<?> getSoLuongSuatChieuTheoNgay(@RequestBody NgayRequestDto ngayRequestDto) {

        Map<String, Object> thongKeSuatChieu = thongkeService.getSuatChieuTheoNgay(ngayRequestDto.getNgayBatDau(), ngayRequestDto.getNgayKetThuc());
        return new ResponseEntity<>(thongKeSuatChieu, HttpStatus.OK);
    }

    @PostMapping("/thong-ke-phim-dang-chieu")
    public ResponseEntity<?> getPhimDangChieuTheoNgay(@RequestBody NgayRequestDto ngayRequestDto) {

        Map<String, Object> thongKePhim = thongkeService.getPhimDangChieuTheoNgay(ngayRequestDto.getNgayBatDau(), ngayRequestDto.getNgayKetThuc());
        return new ResponseEntity<>(thongKePhim, HttpStatus.OK);
    }

    @PostMapping("/thong-ke-voucher")
    public ResponseEntity<?> getVoucherSuDungTheoNgay(@RequestBody NgayRequestDto ngayRequestDto) {

        Map<String, Object> thongKeVoucher = thongkeService.getVoucherSuDungTheoNgay(ngayRequestDto.getNgayBatDau(), ngayRequestDto.getNgayKetThuc());
        return new ResponseEntity<>(thongKeVoucher, HttpStatus.OK);
    }

    @PostMapping("/thong-ke-dich-vu")
    public ResponseEntity<?> getDichVuDaBanTheoNgay(@RequestBody NgayRequestDto ngayRequestDto) {

        Map<String, Object> thongKeDichVu = thongkeService.getDichVuDaBanTheoNgay(ngayRequestDto.getNgayBatDau(), ngayRequestDto.getNgayKetThuc());
        return new ResponseEntity<>(thongKeDichVu, HttpStatus.OK);
    }

    @PostMapping("/thong-ke-chi-tieu")
    public ResponseEntity<?> getChiTieuTheoNgay(@RequestBody NgayRequestDto ngayRequestDto) {

        Map<String, Object> thongKeChiTieu = thongkeService.getChiTieuTheoNgay(ngayRequestDto.getNgayBatDau(), ngayRequestDto.getNgayKetThuc());
        return new ResponseEntity<>(thongKeChiTieu, HttpStatus.OK);
    }

    @PostMapping("/thong-ke-khach-hang")
    public ResponseEntity<?> getSoLuongKhachMoiTheoNgay(@RequestBody NgayRequestDto ngayRequestDto) {

        Map<String, Object> thongKeKhachMoi = thongkeService.getKhacHangMoiTheoNgay(ngayRequestDto.getNgayBatDau(), ngayRequestDto.getNgayKetThuc());
        return new ResponseEntity<>(thongKeKhachMoi, HttpStatus.OK);
    }
}
