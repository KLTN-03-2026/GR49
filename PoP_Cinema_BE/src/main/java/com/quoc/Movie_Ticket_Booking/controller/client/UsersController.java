package com.quoc.Movie_Ticket_Booking.controller.client;

import com.quoc.Movie_Ticket_Booking.dto.request.CapNhatAvatarProfile;
import com.quoc.Movie_Ticket_Booking.dto.request.ThayDoiMatKhauRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.ThayDoiThongTinUserRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.model.Users;
import com.quoc.Movie_Ticket_Booking.service.HangThanhVienService;
import com.quoc.Movie_Ticket_Booking.service.TichDiemService;
import com.quoc.Movie_Ticket_Booking.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/client")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private HangThanhVienService hangThanhVienService;

    @Autowired
    private TichDiemService tichDiemService;


    @PostMapping("/profile")
    public ResponseEntity<Users> findUserByJwtToken(@RequestHeader("Authorization") String jwt){
        Users userByJwtToken = usersService.findUserByJwtToken(jwt);

        return new ResponseEntity<Users>(userByJwtToken, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getUsers(@RequestHeader("Authorization") String jwt){
        Map<String, Object> checkToken = usersService.getUser(jwt);

        return new ResponseEntity<Map<String, Object>>(checkToken, HttpStatus.OK);
    }


    @GetMapping("/check-token")
    public ResponseEntity<Map<String, Object>> checkToken(@RequestHeader("Authorization") String jwt){
        Map<String, Object> checkToken = usersService.checkToken(jwt);

        return new ResponseEntity<Map<String, Object>>(checkToken, HttpStatus.OK);
    }


    @PostMapping("/change-pass")
    public ResponseEntity<?> changePassWord(@Valid @RequestBody ThayDoiMatKhauRequestDto req, @RequestHeader("Authorization") String jwt){
        ApiResponse<?> changePassWord = usersService.changePassWord(req, jwt);

        return new ResponseEntity<>(changePassWord, HttpStatus.OK);
    }

    @PutMapping("/update-thong-tin")
    public ResponseEntity<?> updateThongTin(@RequestBody ThayDoiThongTinUserRequestDto req, @RequestHeader("Authorization") String jwt){
        ApiResponse<?> updateThongTinCaNhan = usersService.updateThongTinCaNhan(req, jwt);

        return new ResponseEntity<>(updateThongTinCaNhan, HttpStatus.OK);
    }

    @PostMapping("/update-avatar")
    public ResponseEntity<?> updateAvatar(@RequestBody CapNhatAvatarProfile req, @RequestHeader("Authorization") String jwt){
        ApiResponse<?> updateThongTinCaNhan = usersService.updateAvatarProfile(req,jwt);

        return new ResponseEntity<>(updateThongTinCaNhan, HttpStatus.OK);
    }

    @GetMapping("/get-hang-thanh-vien")
    public ResponseEntity<?> getThongTinHangThanhVien()  {
        ApiResponse<?> thongTinHangThanVien = hangThanhVienService.getThongTinHangThanVien();
        return new ResponseEntity<>(thongTinHangThanVien, HttpStatus.OK);
    }

    @GetMapping("/get-lich-su-diem")
    public ResponseEntity<?> getLichSuTichDiem(@RequestHeader("Authorization") String jwt) {
        Users users= usersService.findUserByJwtToken(jwt);
        ApiResponse<?> ls = tichDiemService.getLichSuTichDiem(users.getId());
        return new ResponseEntity<>(ls, HttpStatus.OK);
    }

}
