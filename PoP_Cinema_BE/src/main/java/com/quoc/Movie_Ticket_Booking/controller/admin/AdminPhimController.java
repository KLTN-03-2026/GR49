package com.quoc.Movie_Ticket_Booking.controller.admin;

import com.quoc.Movie_Ticket_Booking.dto.request.ChangStatusDto;
import com.quoc.Movie_Ticket_Booking.dto.request.PhimRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.PhimResponseDto;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.Phim;
import com.quoc.Movie_Ticket_Booking.service.NhanVienService;
import com.quoc.Movie_Ticket_Booking.service.PhimService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/phims")
public class AdminPhimController {

    @Autowired
    private PhimService phimService;

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
    public ResponseEntity<ApiResponse<?>> createPhim(@Valid @RequestBody PhimRequestDto req,@RequestHeader("Authorization") String jwt) {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> savePhim = phimService.createPhim(req, nhanVienId);
        return new ResponseEntity<>(savePhim, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<?>> updatePhim(@RequestBody PhimRequestDto req,@RequestHeader("Authorization") String jwt) {


        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> updatePhim = phimService.updatePhim(req.getId(),req, nhanVienId);
        return new ResponseEntity<>(updatePhim, HttpStatus.OK);
    }


    @PostMapping("/update-status")
    public ResponseEntity<ApiResponse<?>> updateStatusPhim(@RequestBody ChangStatusDto req,@RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?>  updateStatus = phimService.updateStatus(req,nhanVienId);
        return new ResponseEntity<>(updateStatus, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deletePhim(@PathVariable Long id,@RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> deletePhim = phimService.deletePhim(id, nhanVienId);
        return new ResponseEntity<>(deletePhim, HttpStatus.OK);
    }

    @GetMapping("/get-data")
    public ResponseEntity<?> getAllPhim()  {

        List<PhimResponseDto> allPhim = phimService.getAllPhim();
        ApiResponse<?> success = ApiResponse.success("Hiển thị list phim thành công!", allPhim);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    @GetMapping("/get-data/{id}")
    public ResponseEntity<Map<String, Object>> getPhimById(@PathVariable Long id)  {

        Phim phimById = phimService.getPhimById(id);
        return createResponse("success", phimById, "Movie List successfully.");
    }


}
