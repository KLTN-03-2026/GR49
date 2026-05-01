package com.quoc.Movie_Ticket_Booking.controller.client;

import com.quoc.Movie_Ticket_Booking.dto.request.BinhLuanRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.DanhGiaRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.model.Users;
import com.quoc.Movie_Ticket_Booking.service.DanhGiaVaBinhLuanService;
import com.quoc.Movie_Ticket_Booking.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/client/danh-gia")
public class DanhGiaBinhLuanController {
    @Autowired
    private DanhGiaVaBinhLuanService danhGiaBinhLuanService;

    @Autowired
    private UsersService usersService;

    @PostMapping("/create-danh-gia")
    public ResponseEntity<ApiResponse<?>> createDanhGia(@RequestBody DanhGiaRequestDto req, @RequestHeader("Authorization") String jwt) {
        Users khachhang = usersService.findUserByJwtToken(jwt);
        ApiResponse<?> danhGia = danhGiaBinhLuanService.createDanhGia(req,khachhang.getId());


        return new ResponseEntity<>(danhGia, HttpStatus.CREATED);
    }


    @PostMapping("/create-binh-luan")
    public ResponseEntity<ApiResponse<?>> createBinhLuan(@RequestBody BinhLuanRequestDto req, @RequestHeader("Authorization") String jwt) {
        Users khachhang = usersService.findUserByJwtToken(jwt);
        ApiResponse<?> binhLuan = danhGiaBinhLuanService.createBinhLuan(req, khachhang.getId());


        return new ResponseEntity<>(binhLuan, HttpStatus.CREATED);
    }


}
