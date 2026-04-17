package com.quoc.Movie_Ticket_Booking.controller.admin;

import com.quoc.Movie_Ticket_Booking.dto.request.NhanVienRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.PhanQuyenRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.Users;
import com.quoc.Movie_Ticket_Booking.service.NhanVienService;
import com.quoc.Movie_Ticket_Booking.service.PhanQuyenService;
import com.quoc.Movie_Ticket_Booking.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/phan-quyens")
public class PhanQuyenController {

    @Autowired
    private PhanQuyenService phanQuyenService;

    @Autowired
    private NhanVienService nhanVienService;


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createPhanQuyen(@RequestBody PhanQuyenRequestDto req, @RequestHeader("Authorization") String jwt) {
        NhanVien nhanVien = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> addPhanQuyen = phanQuyenService.addPhanQuyen(req,nhanVien);

        return new ResponseEntity<>(addPhanQuyen, HttpStatus.CREATED);
    }

    @PostMapping("/get-data/{id}")
    public ResponseEntity<ApiResponse<?>> getPhanQuyenByChucVuId(@PathVariable Long id, @RequestHeader("Authorization") String jwt) {
        NhanVien nhanVien = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> listPhanQuyen = phanQuyenService.getPhanQuyenByChucVuId(id,nhanVien);

        return new ResponseEntity<>(listPhanQuyen, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deletePhanQuyen(@PathVariable Long id , @RequestHeader("Authorization") String jwt) {
        NhanVien nhanVien = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> deletePhanQuyen = phanQuyenService.deletePhanQuyen(id,nhanVien);

        return new ResponseEntity<>(deletePhanQuyen, HttpStatus.OK);
    }


    @GetMapping("/get-chucNang")
    public ResponseEntity<ApiResponse<?>> getAllChucNang( @RequestHeader("Authorization") String jwt) {
        NhanVien nhanVien = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> allChucNang = phanQuyenService.getAllChucNang();

        return new ResponseEntity<>(allChucNang, HttpStatus.OK);
    }
}
