package com.quoc.Movie_Ticket_Booking.controller.admin;

import com.quoc.Movie_Ticket_Booking.dto.request.BaiVietRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.ChangStatusDto;
import com.quoc.Movie_Ticket_Booking.dto.request.ChucVuRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.model.BaiViet;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.service.BaiVietService;
import com.quoc.Movie_Ticket_Booking.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/bai-viet")
public class BaiVietController {

    @Autowired
    private BaiVietService baiVietService;

    @Autowired
    private NhanVienService nhanVienService;


    @PostMapping("/create")
    public ResponseEntity<  ApiResponse<?>> createBaiViet(@RequestBody BaiVietRequestDto req, @RequestHeader("Authorization") String jwt) {
        NhanVien nhanVien = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> createBaiViet = baiVietService.createBaiViet(req,nhanVien);

        return new ResponseEntity<>(createBaiViet, HttpStatus.CREATED);
    }


    @GetMapping("/get-data")
    public ResponseEntity<  ApiResponse<?>> getAllChucVu( @RequestHeader("Authorization") String jwt) {
        NhanVien nhanVien = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> dsBaiViet = baiVietService.getAllBaiViet();

        return new ResponseEntity<>(dsBaiViet, HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity<ApiResponse<?>> updateBaiViet(@RequestBody BaiVietRequestDto req, @RequestHeader("Authorization") String jwt) {
        NhanVien nhanVien = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> createBaiViet = baiVietService.updateBaiViet(req.getId(),req,nhanVien);

        return new ResponseEntity<>(createBaiViet, HttpStatus.OK);
    }

    @PostMapping("/update-status")
    public ResponseEntity<?> updateStatus(@RequestBody ChangStatusDto req, @RequestHeader("Authorization") String jwt) {
        NhanVien nhanVien = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> updateStatus = baiVietService.updateStatus(req.getId(),nhanVien);

        return new ResponseEntity<>(updateStatus, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteBaiViet(@PathVariable Long id ,@RequestHeader("Authorization") String jwt) {
        NhanVien nhanVien = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> createBaiViet = baiVietService.deleteBaiViet(id,nhanVien);

        return new ResponseEntity<>(createBaiViet, HttpStatus.OK);
    }
}
