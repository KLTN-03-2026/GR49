package com.quoc.Movie_Ticket_Booking.controller.admin;

import com.quoc.Movie_Ticket_Booking.dto.request.ChangStatusDto;
import com.quoc.Movie_Ticket_Booking.dto.request.ChucVuRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.PhanQuyenRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.service.ChucVuService;
import com.quoc.Movie_Ticket_Booking.service.NhanVienService;
import com.quoc.Movie_Ticket_Booking.service.PhanQuyenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/chuc-vus")
public class ChucVuCotroller {

    @Autowired
    private ChucVuService chucVuService;

    @Autowired
    private NhanVienService nhanVienService;


    @PostMapping("/create")
    public ResponseEntity<?> createChucVu(@RequestBody ChucVuRequestDto req, @RequestHeader("Authorization") String jwt) {
        NhanVien nhanVien = nhanVienService.findUserByJwtToken(jwt);
       ApiResponse<?> createChucVu = chucVuService.createChucVu(req,nhanVien);

        return new ResponseEntity<>(createChucVu, HttpStatus.CREATED);
    }


    @GetMapping("/get-data")
    public ResponseEntity< ApiResponse<?>> getAllChucVu(@RequestHeader("Authorization") String jwt) {

        ApiResponse<?> allChucVu = chucVuService.getAllChucVu();

        return new ResponseEntity<>(allChucVu, HttpStatus.OK);
    }


    @PutMapping ("/update")
    public ResponseEntity<ApiResponse<?>> updateChucVu(@RequestBody ChucVuRequestDto req, @RequestHeader("Authorization") String jwt) {
        NhanVien nhanVien = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> createChucVu = chucVuService.updateChucVu(req.getId(),req,nhanVien);

        return new ResponseEntity<>(createChucVu, HttpStatus.OK);
    }

    @PostMapping ("/update-status")
    public ResponseEntity<ApiResponse<?>> updateStatus(@RequestBody ChangStatusDto req, @RequestHeader("Authorization") String jwt) {
        NhanVien nhanVien = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> createChucVu = chucVuService.updateStatus(req.getId(),nhanVien);

        return new ResponseEntity<>(createChucVu, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteChucVu(@PathVariable Long id, @RequestHeader("Authorization") String jwt) {
        NhanVien nhanVien = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> createChucVu = chucVuService.deleteChucVu(id,nhanVien);

        return new ResponseEntity<>(createChucVu, HttpStatus.OK);
    }


}
