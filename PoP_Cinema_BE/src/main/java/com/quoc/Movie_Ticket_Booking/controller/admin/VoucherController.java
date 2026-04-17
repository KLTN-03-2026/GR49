package com.quoc.Movie_Ticket_Booking.controller.admin;

import com.quoc.Movie_Ticket_Booking.dto.request.ChangStatusDto;
import com.quoc.Movie_Ticket_Booking.dto.request.SuatChieuRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.VoucherRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.SuatChieuResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.VoucherResponseDto;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.SuatChieu;
import com.quoc.Movie_Ticket_Booking.model.Voucher;
import com.quoc.Movie_Ticket_Booking.service.NhanVienService;
import com.quoc.Movie_Ticket_Booking.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/voucher")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private NhanVienService nhanVienService;


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createVoucher(@RequestBody VoucherRequestDto req,@RequestHeader("Authorization") String jwt) {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> saveVoucher = voucherService.createVoucher(req,nhanVienId);
        return new ResponseEntity<>(saveVoucher, HttpStatus.CREATED);
    }


    @PutMapping("/update")
    public ResponseEntity<ApiResponse<?>> updateVoucher(@RequestBody VoucherRequestDto req,@RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> updateVoucher = voucherService.updateVoucher(req.getId(), req,nhanVienId);
        return new ResponseEntity<>(updateVoucher, HttpStatus.OK);
    }


    @PostMapping("/update-status")
    public ResponseEntity<?> updateStatusSuatChieu(@RequestBody ChangStatusDto req,@RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> updateStatus = voucherService.updateStatus(req.getId(),nhanVienId);
        return new ResponseEntity<>(updateStatus, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVoucher(@PathVariable Long id,@RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> deleteVoucher = voucherService.deleteVoucher(id, nhanVienId);
        return new ResponseEntity<>(deleteVoucher, HttpStatus.OK);
    }

    @GetMapping("/get-data")
    public ResponseEntity<?> getAllVoucher() {
        List<VoucherResponseDto> allVoucher = voucherService.getAllVoucher();
        ApiResponse<?> success = ApiResponse.success("Hiển thị danh sách voucher thành công!", allVoucher);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }



}
