package com.quoc.Movie_Ticket_Booking.controller.admin;

import com.quoc.Movie_Ticket_Booking.dto.request.ChangStatusDto;
import com.quoc.Movie_Ticket_Booking.dto.request.GheRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.NhanVienRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.NhanVienResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.PhimResponseDto;
import com.quoc.Movie_Ticket_Booking.model.Ghe;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.Phim;
import com.quoc.Movie_Ticket_Booking.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/nhan-viens")
public class NhanVienController {

    @Autowired
    private NhanVienService nhanVienService;


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createNhanVien(@RequestBody NhanVienRequestDto req,@RequestHeader("Authorization") String jwt) {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> saveNhanVien = nhanVienService.createNhanVien(req, nhanVienId);
        return new ResponseEntity<>(saveNhanVien, HttpStatus.CREATED);
    }


    @PutMapping("/update")
    public ResponseEntity<ApiResponse<?>> updateNhanVien(@RequestBody NhanVienRequestDto req,@RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> updateNhanVien = nhanVienService.updateNhanVien(req.getId(),req,nhanVienId);
        return new ResponseEntity<>(updateNhanVien, HttpStatus.OK);
    }

    @PostMapping("/update-status")
    public ResponseEntity<ApiResponse<?>> updateStatusNhanVien(@RequestBody ChangStatusDto req,@RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> updateStatus = nhanVienService.updateStatus(req.getId(),nhanVienId);
        return new ResponseEntity<>(updateStatus, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteNhanVien(@PathVariable Long id,@RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> apiResponse = nhanVienService.deleteNhanVien(id,nhanVienId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-data")
    public ResponseEntity<?> getAllNhaVien()  {

        List<NhanVienResponseDto> allNhanVien = nhanVienService.getAllNhanVien();
        ApiResponse<?> success = ApiResponse.success("Hiển thị danh sách thành công!", allNhanVien);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

}
