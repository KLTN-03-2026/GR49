package com.quoc.Movie_Ticket_Booking.controller.admin;

import com.quoc.Movie_Ticket_Booking.dto.request.ChangStatusDto;
import com.quoc.Movie_Ticket_Booking.dto.request.PhimRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.PhongChieuRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.SuatChieuRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.SuatChieuResponseDto;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.PhongChieu;
import com.quoc.Movie_Ticket_Booking.model.SuatChieu;
import com.quoc.Movie_Ticket_Booking.service.NhanVienService;
import com.quoc.Movie_Ticket_Booking.service.SuatChieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/suat-chieu")
public class SuatChieuController {
    @Autowired
    private SuatChieuService suatChieuService;

    @Autowired
    private NhanVienService nhanVienService;


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createSuatChieu(@RequestBody SuatChieuRequestDto req,@RequestHeader("Authorization") String jwt) {
        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> saveSuatChieu = suatChieuService.createSuatChieu(req, nhanVienId);
        return new ResponseEntity<>(saveSuatChieu, HttpStatus.CREATED);
    }


    @PutMapping("/update")
    public ResponseEntity<ApiResponse<?>> updateSuatChieu(@RequestBody SuatChieuRequestDto req,@RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> updateSuatChieu = suatChieuService.updateSuatChieu(req.getId(), req,nhanVienId);
        return new ResponseEntity<>(updateSuatChieu, HttpStatus.OK);
    }


    @PostMapping("/update-status")
    public ResponseEntity<ApiResponse<?>> updateStatusSuatChieu(@RequestBody ChangStatusDto req,@RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> updateStatus = suatChieuService.updateStatus(req.getId(),nhanVienId);
        return new ResponseEntity<>(updateStatus, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteSuatChieu(@PathVariable Long id,@RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> deleteSuatChieu = suatChieuService.deleteSuatChieu(id, nhanVienId);
        return new ResponseEntity<>(deleteSuatChieu, HttpStatus.OK);
    }

    @GetMapping("/get-data")
    public ResponseEntity<?> getAllSuatChieu() {
        List<SuatChieuResponseDto> allSuatChieu = suatChieuService.getAllSuatChieu();
        ApiResponse<?> success = ApiResponse.success("Hiển thị danh sách suất chiếu thành công!", allSuatChieu);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    @PostMapping("/create-ve-auto")
    public ResponseEntity<ApiResponse<?>> createVeAuto(@RequestBody SuatChieuResponseDto req,@RequestHeader("Authorization") String jwt) {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> createVeAuto = suatChieuService.createVeAuto(req,nhanVienId);
        return new ResponseEntity<>(createVeAuto, HttpStatus.CREATED);
    }
}
