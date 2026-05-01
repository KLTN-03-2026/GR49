package com.quoc.Movie_Ticket_Booking.controller.client;

import com.quoc.Movie_Ticket_Booking.dto.response.DonHangDetailsResponseDto;
import com.quoc.Movie_Ticket_Booking.exception.ResourceNotFoundException;
import com.quoc.Movie_Ticket_Booking.model.DonHang;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.Users;
import com.quoc.Movie_Ticket_Booking.repository.DonHangRepository;
import com.quoc.Movie_Ticket_Booking.service.DichVuService;
import com.quoc.Movie_Ticket_Booking.service.DonHangService;
import com.quoc.Movie_Ticket_Booking.service.UsersService;
import com.quoc.Movie_Ticket_Booking.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/api/client/don-hangs")
public class DonHangsController {
    @Autowired
    private DonHangService donHangService;

    @Autowired
    private DonHangRepository donHangRepository;

    @Autowired
    private DichVuService dichVuService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private CommonUtil commonUtil;


    @GetMapping("/{maDonHang}")
    public ResponseEntity<?> getHoaDonForUser(@PathVariable("maDonHang") String maDonHang, @RequestHeader("Authorization") String jwt)  {

        Users user = usersService.findUserByJwtToken(jwt);
        Map<String, Object> byHoaDon = donHangService.getByHoaDonForUser(maDonHang,user.getId());
        return new ResponseEntity<>(byHoaDon, HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistoryDonHangByUser( @RequestHeader("Authorization") String jwt)  {

        Users user = usersService.findUserByJwtToken(jwt);
        Map<String, Object> byHoaDon = donHangService.getHistoryDonHangByUser(user.getId());
        return new ResponseEntity<>(byHoaDon, HttpStatus.OK);
    }


    @PutMapping("/cancel/{maDonHang}")
    public ResponseEntity<?> cancalOder(@PathVariable("maDonHang") String maDonHang)  {

        DonHang donHang = donHangService.cancelOder(maDonHang);
        return new ResponseEntity<>("Huỷ đơn hàng thành công", HttpStatus.OK);
    }

//    @PostMapping("/send-email")
//    public ResponseEntity<?> sendEmail(@RequestBody DonHangDetailsResponseDto dto, @RequestHeader("Authorization") String jwt)  {
//        Map<String, Object> response = new HashMap<>();
//
//
//        DonHang byMaDonHang = donHangRepository.findByMaDonHang(dto.getMaDonHang());
//        if (byMaDonHang == null) {
//            throw new ResourceNotFoundException("Don hang not found ma don hang "+dto.getMaDonHang());
//        }
//        byMaDonHang.setPhuongThucThanhToan(1);
//        donHangRepository.save(byMaDonHang);
//
//        try {
//            // 📧 Gửi email xác nhận đơn hàng
//            boolean mailSent = commonUtil.sendEmailForProductOrder(dto);
//
//            response.put("status", mailSent);
//            response.put("message", mailSent
//                    ? "Gửi email xác nhận đơn hàng thành công."
//                    : "Không thể gửi email xác nhận đơn hàng.");
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            log.error("Lỗi khi gửi email đơn hàng {}: {}", dto.getMaDonHang(), e.getMessage(), e);
//
//            response.put("status", false);
//            response.put("message", "Đã xảy ra lỗi khi gửi email xác nhận đơn hàng.");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }


    @GetMapping("/chi-tiet-dich-vu/{id}")
    public ResponseEntity<?> getChiTietDichVuByDonHang(@PathVariable("id") Long idDonHang)  {

        Map<String, Object> dataChiTietDichVu = dichVuService.getDataChiTietDichVu(idDonHang);
        return createResponse("success", dataChiTietDichVu, "Hiển thị  thành công thành công.");
    }

    @PostMapping("/in-ve/{maDonHang}")
    public ResponseEntity<?> getHoaDon(@PathVariable("maDonHang") String maDonHang,@RequestHeader("Authorization") String jwt)  {
        Map<String, Object> byHoaDon = donHangService.getByHoaDon(maDonHang);
        return new ResponseEntity<>(byHoaDon, HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> createResponse(String status, Object data, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("data", data);
        response.put("message", message);
        return ResponseEntity.ok(response);
    }


}
