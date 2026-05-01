package com.quoc.Movie_Ticket_Booking.controller.client;

import com.quoc.Movie_Ticket_Booking.dto.request.DatVeRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.model.Phim;
import com.quoc.Movie_Ticket_Booking.model.Users;
import com.quoc.Movie_Ticket_Booking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chi-tiet-phim")
public class ChiTietPhimController {

    @Autowired
    private SuatChieuService suatChieuService;

    @Autowired
    private PhimService phimService;

    @Autowired
    private DanhGiaVaBinhLuanService danhGiaBinhLuanService;


    @GetMapping("/{id}")
    public ResponseEntity<?> getDataPhim(@PathVariable Long id)  {

        Map<String, Object> chiTietPhimAndSuatChieu = suatChieuService.getChiTietPhimAndSuatChieu(id);
        boolean status = (boolean) chiTietPhimAndSuatChieu.get("status");

        if (status) {
            return ResponseEntity.ok(chiTietPhimAndSuatChieu); // HTTP 200
        } else {
            return ResponseEntity.status(404).body(chiTietPhimAndSuatChieu); // HTTP 404
        }
    }

    @GetMapping("/get-goi-y")
    public ResponseEntity<?> getDataPhimGoiY()  {

        ApiResponse<?> phimGoiY = phimService.getPhimGoiY();
        return new ResponseEntity<>(phimGoiY, HttpStatus.OK);
    }

    @GetMapping("/get-danh-gia")
    public ResponseEntity<ApiResponse<?>> getDanhGia(@RequestParam Long idPhim) {
        ApiResponse<?> getDanhGia = danhGiaBinhLuanService.getDanhGia(idPhim);


        return new ResponseEntity<>(getDanhGia, HttpStatus.OK);
    }

    @GetMapping("/get-binh-luan")
    public ResponseEntity<ApiResponse<?>> getBinhLuan(@RequestParam Long idPhim) {

        ApiResponse<?> getBinhLuan = danhGiaBinhLuanService.getBinhLuan(idPhim);


        return new ResponseEntity<>(getBinhLuan, HttpStatus.OK);
    }

}
