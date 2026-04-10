package com.quoc.Movie_Ticket_Booking.controller.client;

import com.quoc.Movie_Ticket_Booking.dto.request.DatVeRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.VoucherRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.model.Users;
import com.quoc.Movie_Ticket_Booking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/client/dat-ve")
public class DatVeController {

    @Autowired
    private VeService veService;

    @Autowired
    private DonHangService donHangService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private DichVuService dichVuService;

    @Autowired
    private VoucherService voucherService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientAllVe(@PathVariable Long id)  {

        Map<String, Object> clientAllVe = veService.getClientAllVe(id);
        boolean status = (boolean) clientAllVe.get("status");

        if (status) {
            return ResponseEntity.ok(clientAllVe); // HTTP 200
        } else {
            return ResponseEntity.status(404).body(clientAllVe); // HTTP 404
        }
    }


    @PostMapping("/thanh-toan")
    public ResponseEntity<?> payMent(@RequestBody DatVeRequestDto req, @RequestHeader("Authorization") String jwt)  {

        Users users= usersService.findUserByJwtToken(jwt);
        Map<String, Object> payment = donHangService.Payment(req, users.getId());
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    @GetMapping("/get-data")
    public ResponseEntity<?> getDataClientDichVu()  {

        ApiResponse<?> dataClientDichVu = dichVuService.getDataClientDichVu();
        return new ResponseEntity<>(dataClientDichVu, HttpStatus.OK);
    }


    @PostMapping("/ap-voucher")
    public ResponseEntity<?> findVoucher(@RequestBody VoucherRequestDto dto) {
        ApiResponse<?> voucherByMaCode = voucherService.findVoucherByMaCode(dto.getMaCode());
        return new ResponseEntity<>(voucherByMaCode, HttpStatus.OK);
    }

    @PostMapping("/ap-qua-voucher")
    public ResponseEntity<?> findQuaVoucher(@RequestBody VoucherRequestDto dto, @RequestHeader("Authorization") String jwt) {
        Users users= usersService.findUserByJwtToken(jwt);
        ApiResponse<?> voucherByMaCode = voucherService.findQuaVoucherByMaCode(dto.getMaCode(),users.getId());
        return new ResponseEntity<>(voucherByMaCode, HttpStatus.OK);
    }



}
