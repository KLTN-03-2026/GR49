package com.quoc.Movie_Ticket_Booking.controller.client;

import com.quoc.Movie_Ticket_Booking.dto.request.VoucherRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.QuaVoucherResponseDto;
import com.quoc.Movie_Ticket_Booking.model.Users;
import com.quoc.Movie_Ticket_Booking.service.UsersService;
import com.quoc.Movie_Ticket_Booking.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/client/qua-voucher")
public class QuaVoucherController {

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private UsersService usersService;

    @PostMapping("/doi-qua")
    public ResponseEntity<?> doiQua(@RequestParam Long voucherId, @RequestHeader("Authorization") String jwt) {
        Users users= usersService.findUserByJwtToken(jwt);
        ApiResponse<?> voucherByMaCode = voucherService.doiQuaVoucher(voucherId,users.getId());
        return new ResponseEntity<>(voucherByMaCode, HttpStatus.OK);
    }


    @GetMapping("/get-lich-su-qua-voucher")
    public ResponseEntity<?> getLichSuQuaVoucherForKhachHang(@RequestHeader("Authorization") String jwt)  {
        Users users= usersService.findUserByJwtToken(jwt);
        ApiResponse<?> lichSuQuaVoucher = voucherService.getLichSuQuaVoucher(users.getId());
        return new ResponseEntity<>(lichSuQuaVoucher, HttpStatus.OK);
    }



}
