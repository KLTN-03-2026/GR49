package com.quoc.Movie_Ticket_Booking.controller.admin;

import com.quoc.Movie_Ticket_Booking.dto.request.ChangStatusDto;
import com.quoc.Movie_Ticket_Booking.dto.request.KhachHangRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.NhanVienRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.KhachHangResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.NhanVienResponseDto;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.service.KhachHangService;
import com.quoc.Movie_Ticket_Booking.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/khach-hangs")
public class KhachHangController {

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private NhanVienService nhanVienService;

    @GetMapping("/get-data")
    public ResponseEntity<?> getAllKhachHang()  {

        ApiResponse<?> allKhachHang = khachHangService.getAllKhachHang();
        return new ResponseEntity<>(allKhachHang, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<?>> updateKhacHang(@RequestBody KhachHangRequestDto req, @RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> updateKhachHang = khachHangService.updateKhachHang(req.getId(),req,nhanVienId);
        return new ResponseEntity<>(updateKhachHang, HttpStatus.OK);
    }

    @PostMapping("/update-status")
    public ResponseEntity<ApiResponse<?>> updateStatusKhachHang(@RequestBody ChangStatusDto req, @RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> updateKhachHang = khachHangService.updateStatus(req.getId(),nhanVienId);
        return new ResponseEntity<>(updateKhachHang, HttpStatus.OK);
    }


    @PostMapping("/update-kich-hoat")
    public ResponseEntity<ApiResponse<?>> updateKichHoatKhachHang(@RequestBody ChangStatusDto req, @RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> updateKhachHang = khachHangService.updateKichHoat(req.getId(),nhanVienId);
        return new ResponseEntity<>(updateKhachHang, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteKhachHang(@PathVariable Long id,@RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> deleteKhachHang = khachHangService.deleteKhachHang(id,nhanVienId);
        return new ResponseEntity<>(deleteKhachHang, HttpStatus.OK);
    }
}
