package com.quoc.Movie_Ticket_Booking.controller.admin;


import com.quoc.Movie_Ticket_Booking.dto.request.ChangStatusDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.model.DonHang;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.PhongChieu;
import com.quoc.Movie_Ticket_Booking.service.DichVuService;
import com.quoc.Movie_Ticket_Booking.service.DonHangService;
import com.quoc.Movie_Ticket_Booking.service.NhanVienService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/don-hangs")
public class DonHangController {
    @Autowired
    private DonHangService donHangService;

    @Autowired
    private DichVuService dichVuService;

    @Autowired
    private NhanVienService nhanVienService;

    private ResponseEntity<Map<String, Object>> createResponse(String status, Object data, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("data", data);
        response.put("message", message);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/update-status")
    public ResponseEntity<?> updateStatusDonhang(@RequestBody ChangStatusDto req,@RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVien = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> updateStatus = donHangService.updateStatus(req,nhanVien);
        return new ResponseEntity<>(updateStatus, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDonHang(@PathVariable Long id,@RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVien = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> deleteDonHangById = donHangService.deleteDonHangById(id, nhanVien);
        return new ResponseEntity<>(deleteDonHangById, HttpStatus.OK);
    }

        @PostMapping("/in-ve/{maDonHang}")
    public ResponseEntity<?> getHoaDon(@PathVariable("maDonHang") String maDonHang,@RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVien = nhanVienService.findUserByJwtToken(jwt);
        Map<String, Object> byHoaDon = donHangService.getByHoaDon(maDonHang,nhanVien);
        return new ResponseEntity<>(byHoaDon, HttpStatus.OK);
    }

    @GetMapping("/get-data")
    public ResponseEntity<?> getAllDonHang()  {

        List<DonHang> allDonHang = donHangService.getAllDonHang();
        return createResponse("success", allDonHang, "Thêm Phim thành công.");
    }

    @GetMapping("/chi-tiet-dich-vu/{id}")
    public ResponseEntity<?> getChiTietDichVuByDonHang(@PathVariable("id") Long idDonHang)  {

        Map<String, Object> dataChiTietDichVu = dichVuService.getDataChiTietDichVu(idDonHang);
        return createResponse("success", dataChiTietDichVu, "Hiển thị  thành công thành công.");
    }


    @GetMapping("/search")
    public ResponseEntity<?> searchDonHang(@RequestParam String maDonHang)  {

        Map<String, Object> searchDonHang = donHangService.searchDonHang(maDonHang);

        return new ResponseEntity<>(searchDonHang,HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterDonHang(@RequestParam String ngayTruoc, @RequestParam String ngaySau)  {
        // parse String -> LocalDate
        LocalDate start = LocalDate.parse(ngayTruoc); // yyyy-MM-dd
        LocalDate end   = LocalDate.parse(ngaySau);

        // chuyển sang LocalDateTime để quét trọn ngày
        LocalDateTime startDateTime = start.atStartOfDay();         // 00:00:00
        LocalDateTime endDateTime   = end.atTime(23, 59, 59);
        List<DonHang> donHangList = donHangService.filterDonHang(startDateTime, endDateTime);

        return createResponse("success", donHangList, "Hiển thị thành công.");
    }


}
