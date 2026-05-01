package com.quoc.Movie_Ticket_Booking.controller.admin;

import com.quoc.Movie_Ticket_Booking.dto.request.ThayDoiMatKhauRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.ThayDoiThongTinNhanVienRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.ThayDoiThongTinUserRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.Users;
import com.quoc.Movie_Ticket_Booking.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminTokenController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private NhanVienService nhanVienService;

    @Autowired
    private ThongTinNhanVien thongTinNhanVien;

    @Autowired
    private CheckInService checkInService;

    @GetMapping("/check-token")
    public ResponseEntity<Map<String, Object>> checkToken(@RequestHeader("Authorization") String jwt){
        Map<String, Object> checkToken = nhanVienService.checkToken(jwt);

        return new ResponseEntity<>(checkToken, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getNhanVien(@RequestHeader("Authorization") String jwt){
        Map<String, Object> checkToken = nhanVienService.getEmployee(jwt);

        return new ResponseEntity<>(checkToken, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<NhanVien> findNhanVienByJwtToken(@RequestHeader("Authorization") String jwt){
        NhanVien userByJwtToken = nhanVienService.findUserByJwtToken(jwt);

        return new ResponseEntity<>(userByJwtToken, HttpStatus.OK);
    }

    @PostMapping("/change-pass")
    public ResponseEntity<?> changePassWord(@Valid @RequestBody ThayDoiMatKhauRequestDto req, @RequestHeader("Authorization") String jwt){
        ApiResponse<?> changePassWord = thongTinNhanVien.changePassWord(req, jwt);

        return new ResponseEntity<>(changePassWord, HttpStatus.OK);
    }

    @PutMapping("/update-thong-tin")
    public ResponseEntity<?> updateThongTin(@RequestBody ThayDoiThongTinNhanVienRequestDto req, @RequestHeader("Authorization") String jwt){
        ApiResponse<?> updateThongTinCaNhan = thongTinNhanVien.updateThongTinCaNhan(req, jwt);

        return new ResponseEntity<>(updateThongTinCaNhan, HttpStatus.OK);
    }

    @GetMapping("/check-in")
    public ResponseEntity<ApiResponse> checkIn(@RequestParam String qr) {

        ApiResponse response = checkInService.checkIn(qr);

        if (!response.isStatus()) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }
}
