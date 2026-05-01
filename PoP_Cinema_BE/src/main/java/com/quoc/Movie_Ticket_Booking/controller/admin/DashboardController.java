package com.quoc.Movie_Ticket_Booking.controller.admin;

import com.quoc.Movie_Ticket_Booking.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;



    @GetMapping("/get-tong-doanh-thu")
    public ResponseEntity<Map<String, Object>> getTongDoanhThu() {

        Map<String, Object> tongDoanhThu = dashboardService.getTongDoanhThu();
        return new ResponseEntity<>(tongDoanhThu, HttpStatus.OK);
    }

    @GetMapping("/get-tong-phim")
    public ResponseEntity<Map<String, Object>> getTongPhim() {

        Map<String, Object> tongPhim = dashboardService.getTongPhim();
        return new ResponseEntity<>(tongPhim, HttpStatus.OK);
    }

    @GetMapping("/get-tong-ve-ban")
    public ResponseEntity<Map<String, Object>> getTongVeBan() {

        Map<String, Object> tongVeDaBan = dashboardService.getTongVeDaBan();
        return new ResponseEntity<>(tongVeDaBan, HttpStatus.OK);
    }

    @GetMapping("/get-tong-phong-chieu")
    public ResponseEntity<Map<String, Object>> getTongPhongChieu() {

        Map<String, Object> tongPhongChieu = dashboardService.getTongPhongChieu();
        return new ResponseEntity<>(tongPhongChieu, HttpStatus.OK);
    }

    @GetMapping("/get-dat-ve-gan-day")
    public ResponseEntity<Map<String, Object>> getDatVeGanDay() {

        Map<String, Object> datVeGanDay = dashboardService.getDatVeGanDay();
        return new ResponseEntity<>(datVeGanDay, HttpStatus.OK);
    }


    @GetMapping("/get-phim-pho-bien")
    public ResponseEntity<Map<String, Object>> getPhimPhoBien() {

        Map<String, Object> phimPhoBien = dashboardService.getPhimPhoBien();
        return new ResponseEntity<>(phimPhoBien, HttpStatus.OK);
    }

    @GetMapping("/thong-ke-loai-ve")
    public ResponseEntity<Map<String, Object>> getSoLuongLoaive(@RequestHeader("Authorization") String jwt) {
//        NhanVien nhanVien = nhanVienService.findUserByJwtToken(jwt);

        Map<String, Object> getLoaiVe = dashboardService.getThongKeLoaiVe();
        return new ResponseEntity<>(getLoaiVe, HttpStatus.OK);
    }
}
