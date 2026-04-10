package com.quoc.Movie_Ticket_Booking.controller.admin;

import com.quoc.Movie_Ticket_Booking.dto.request.ChangStatusDto;
import com.quoc.Movie_Ticket_Booking.dto.request.ChucVuRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.VeRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.*;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.service.NhanVienService;
import com.quoc.Movie_Ticket_Booking.service.VeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/ves")
public class VeController {

    @Autowired
    private VeService veService;

    @Autowired
    private NhanVienService nhanVienService;



    @GetMapping("/get-data")
    public ResponseEntity<?> getAllSVe() {
        List<VeResponseDto> allVe = veService.getAllVe();
        ApiResponse<?> success = ApiResponse.success("Hiển thị danh sách vé thành công!", allVe);
        return new ResponseEntity<>(success, HttpStatus.OK);

    }


    @GetMapping("/search")
    public ResponseEntity<?> searchVe(@RequestParam String mave)  {

        Map<String, Object> searchVe = veService.searchVe(mave);

        return new ResponseEntity<>(searchVe,HttpStatus.OK);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deleteVe(@PathVariable Long id,@RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> deleteVe = veService.deleteVe(id, nhanVienId);
        return new ResponseEntity<>(deleteVe, HttpStatus.OK);
    }


    @DeleteMapping("/destroy/{id}")
    public ResponseEntity<?> destroyVe(@PathVariable Long id,@RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> deleteVe = veService.destroyVe(id, nhanVienId);
        return new ResponseEntity<>(deleteVe, HttpStatus.OK);
    }


    @GetMapping("/get-all-ve")
    public ResponseEntity<?> getAllVePhanTrang(  @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                                  @RequestParam(name = "pageSize", defaultValue = "16") Integer pageSize)  {

        VePhanTrangResponseDto allVePhanTrang = veService.getAllVePhanTrang(pageNo, pageSize);
        return new ResponseEntity<>(allVePhanTrang, HttpStatus.OK);
    }

    @GetMapping("/search-ve")
    public ResponseEntity<?> searchVeByPhanTrang(@RequestParam String mave,
                                                 @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                                 @RequestParam(name = "pageSize", defaultValue = "16") Integer pageSize)  {

        ApiResponse<?> searchVe = veService.searchVeByPhanTrang(mave, pageNo, pageSize);

        return new ResponseEntity<>(searchVe,HttpStatus.OK);
    }

    @PostMapping("/update-status")
    public ResponseEntity<?> updateStatus(@RequestBody ChangStatusDto req,@RequestHeader("Authorization") String jwt) {
        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> updateStatus = veService.updateStatus(req.getId(),nhanVienId);

        return new ResponseEntity<>(updateStatus,HttpStatus.OK);
    }


    @PutMapping ("/update")
    public ResponseEntity<?> updateVe(@RequestBody VeRequestDto req, @RequestHeader("Authorization") String jwt) {
        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> updateStatus = veService.updateVe(req.getId(),req,nhanVienId);

        return new ResponseEntity<>(updateStatus, HttpStatus.OK);
    }
}
