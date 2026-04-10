package com.quoc.Movie_Ticket_Booking.controller.client;

import com.quoc.Movie_Ticket_Booking.dto.request.ResetMatKhauRequest;
import com.quoc.Movie_Ticket_Booking.dto.response.*;
import com.quoc.Movie_Ticket_Booking.exception.ResourceNotFoundException;
import com.quoc.Movie_Ticket_Booking.model.DonHang;
import com.quoc.Movie_Ticket_Booking.model.Phim;
import com.quoc.Movie_Ticket_Booking.model.Users;
import com.quoc.Movie_Ticket_Booking.model.Ve;
import com.quoc.Movie_Ticket_Booking.repository.DonHangRepository;
import com.quoc.Movie_Ticket_Booking.repository.VeRepository;
import com.quoc.Movie_Ticket_Booking.service.*;
import com.quoc.Movie_Ticket_Booking.util.CommonUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HomeController {


    @Autowired
    private PhimService phimService;

    @Autowired
    private UsersService usersService;


    @Autowired
    private BaiVietService baiVietService;

    @Autowired
    private VoucherService voucherService;


    private ResponseEntity<Map<String, Object>> createResponse(String status, Object data, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("data", data);
        response.put("message", message);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/phim/get-data")
    public ResponseEntity<?> getDataPhim()  {

        Map<String, Object> dataClientPhim = phimService.getDataClientPhim();
        return new ResponseEntity<>(dataClientPhim, HttpStatus.OK);
    }

    @GetMapping("/bai-viet/get-data")
    public ResponseEntity<?> getDataBaiViet()  {

        ApiResponse<?> dataClientBaiViet = baiVietService.getDataClientBaiViet();
        return new ResponseEntity<>(dataClientBaiViet, HttpStatus.OK);
    }

    @GetMapping("/chi-tiet-bai-viet/get-data/{id}")
    public ResponseEntity<?> getChiTietBaiViet(@PathVariable Long id)  {

        ApiResponse<?> dataClientBaiViet = baiVietService.getChiTietBaiViet(id);
        return new ResponseEntity<>(dataClientBaiViet, HttpStatus.OK);
    }


//    @GetMapping("/chi-tiet-phim/{id}")
//    public ResponseEntity<Phim> getChiTietPhim(@PathVariable Long id)  {
//
//        Phim phim = phimService.getPhimById(id);
//        return new ResponseEntity<>(phim, HttpStatus.OK);
//    }


    @PostMapping("/kich-hoat")
    public ResponseEntity<?> kichHoatTaiKhoan(@RequestParam String maKichHoat)  {

        ApiResponse<?> kichHoatTaiKhoan = usersService.kichHoatTaiKhoan(maKichHoat);
        return new ResponseEntity<>(kichHoatTaiKhoan, HttpStatus.OK);
    }

    @PostMapping("/quen-mat-khau")
    public ResponseEntity<?> getQuenMatKhau(@RequestParam String email) throws Exception {

        ApiResponse<?> quenMatKhau= usersService.forgotPassWord(email);
        return new ResponseEntity<>(quenMatKhau, HttpStatus.OK);
    }

    @PostMapping("/dat-lai-mat-khau")
    public ResponseEntity<?> resetMatKhau(@RequestParam String maReset, @RequestBody ResetMatKhauRequest req) throws Exception {

        ApiResponse<?> resetMatKhau = usersService.resetPassWord(maReset, req);
        return new ResponseEntity<>(resetMatKhau, HttpStatus.CREATED);
    }


    @GetMapping("/get-qua-voucher")
    public ResponseEntity<?> getAllQuaVoucher()  {
        List<QuaVoucherResponseDto> allVoucher = voucherService.getAllQuaVoucher();
        ApiResponse<?> success = ApiResponse.success("Hiển thị danh sách qua voucher thành công!", allVoucher);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }



}
