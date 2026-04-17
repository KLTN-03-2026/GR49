package com.quoc.Movie_Ticket_Booking.controller.admin;

import com.quoc.Movie_Ticket_Booking.dto.request.ChangStatusDto;
import com.quoc.Movie_Ticket_Booking.dto.request.DichVuRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.GheRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.PhimRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.DichVuResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.GheResponseDto;
import com.quoc.Movie_Ticket_Booking.model.DichVu;
import com.quoc.Movie_Ticket_Booking.model.Ghe;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.service.DichVuService;
import com.quoc.Movie_Ticket_Booking.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/dich-vu")
public class DichVuController {
    @Autowired
    private DichVuService dichVuService;

    @Autowired
    private NhanVienService nhanVienService;



    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createDichVu(@RequestBody DichVuRequestDto req,@RequestHeader("Authorization") String jwt) {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> saveDichVu = dichVuService.createDichVu(req,nhanVienId);
        return new ResponseEntity<>(saveDichVu, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<?>> updateDichVu(@RequestBody DichVuRequestDto req,@RequestHeader("Authorization") String jwt) throws Exception {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> updateDichVu = dichVuService.updateDichVu(req.getId(), req,nhanVienId);
        return new ResponseEntity<>(updateDichVu, HttpStatus.OK);
    }


    @PostMapping("/update-status")
    public ResponseEntity<ApiResponse<?>> updateStatusGhe(@RequestBody ChangStatusDto req,@RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> updateStatus = dichVuService.updateStatus(req.getId(),nhanVienId);
        return new ResponseEntity<>(updateStatus, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity< ApiResponse<?>> deleteDichVu(@PathVariable Long id,@RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> deleteDichVu = dichVuService.deleteDichVu(id, nhanVienId);
        return new ResponseEntity<>(deleteDichVu, HttpStatus.OK);
    }


    @GetMapping("/get-data")
    public ResponseEntity<?> getAllDichVu()  {

        List<DichVuResponseDto> allDichVu = dichVuService.getAllDichVu();
        ApiResponse<?> success = ApiResponse.success("Hiển thị danh sách dịch vụ thành công!", allDichVu);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }


}
