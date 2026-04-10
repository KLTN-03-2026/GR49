package com.quoc.Movie_Ticket_Booking.controller.admin;

import com.quoc.Movie_Ticket_Booking.dto.request.ChangStatusDto;
import com.quoc.Movie_Ticket_Booking.dto.request.PhimRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.PhongChieuRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.PhongChieuResponseDto;
import com.quoc.Movie_Ticket_Booking.model.Ghe;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.Phim;
import com.quoc.Movie_Ticket_Booking.model.PhongChieu;
import com.quoc.Movie_Ticket_Booking.service.NhanVienService;
import com.quoc.Movie_Ticket_Booking.service.PhongChieuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/phong-chieu")
public class PhongChieuController {

    @Autowired
    private PhongChieuService phongChieuService;

    @Autowired
    private NhanVienService nhanVienService;


    private ResponseEntity<Map<String, Object>> createResponse(String status, Object data, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("data", data);
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createPhongChieu(@RequestBody PhongChieuRequestDto req,@RequestHeader("Authorization") String jwt) {
        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> savePhongChieu = phongChieuService.createPhongChieu(req,nhanVienId);
        return new ResponseEntity<>(savePhongChieu, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<?>> updatePhongChieu(@RequestBody PhongChieuRequestDto req,@RequestHeader("Authorization") String jwt) throws Exception {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> savePhongChieu = phongChieuService.updatePhongChieu(req.getId(), req,nhanVienId);
        return new ResponseEntity<>(savePhongChieu, HttpStatus.OK);
    }


    @PostMapping("/update-status")
    public ResponseEntity<ApiResponse<?>> updateStatusPhongChieu(@RequestBody ChangStatusDto req, @RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> updateStatus = phongChieuService.updateStatus(req.getId(),nhanVienId);
        return new ResponseEntity<>(updateStatus, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deletePhongChieu(@PathVariable Long id, @RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> deletePhongChieu = phongChieuService.deletePhongChieu(id, nhanVienId);
        return new ResponseEntity<>(deletePhongChieu, HttpStatus.OK);
    }


    @GetMapping("/get-data")
    public ResponseEntity<?> getAllPhongChieu()  {

        List<PhongChieuResponseDto> allPhongChieu = phongChieuService.getAllPhongChieu();
        ApiResponse<?> success = ApiResponse.success("Hiển thị danh sách phòng chiếu thành công!", allPhongChieu);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    @PostMapping("/create-auto")
    public ResponseEntity<ApiResponse<?>> createAutoGhe(@RequestBody PhongChieuRequestDto req,@RequestHeader("Authorization") String jwt) {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> createAutoGhe = phongChieuService.createAutoGhe(req,nhanVienId);
        return new ResponseEntity<>(createAutoGhe, HttpStatus.CREATED);
    }
}
