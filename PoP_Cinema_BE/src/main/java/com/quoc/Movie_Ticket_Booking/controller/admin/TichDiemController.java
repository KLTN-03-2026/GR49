package com.quoc.Movie_Ticket_Booking.controller.admin;

import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.VoucherResponseDto;
import com.quoc.Movie_Ticket_Booking.service.TichDiemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admin/tich-diem")
public class TichDiemController {

    @Autowired
    private TichDiemService tichDiemService;

    @GetMapping("/get-data")
    public ResponseEntity<?> getAllTichDiem(@RequestParam(required = false) LocalDate ngayTruoc,
                                            @RequestParam(required = false) LocalDate ngaySau,
                                            @RequestParam(required = false) Integer tinhTrang) {
        ApiResponse<?> allTichDiem = tichDiemService.getAllTichDiem(ngayTruoc, ngaySau, tinhTrang);
        return new ResponseEntity<>(allTichDiem, HttpStatus.OK);
    }


    @GetMapping("/top-khach-hang")
    public ResponseEntity<?> getTop5KhachHang() {
        ApiResponse<?> response = tichDiemService.getTop5KhachHang();
        return ResponseEntity.ok(response);
    }

}
