package com.quoc.Movie_Ticket_Booking.controller.client;

import com.quoc.Movie_Ticket_Booking.dto.request.ThanhToanDonHangRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.CreateMomoResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.DonHangDetailsResponseDto;
import com.quoc.Movie_Ticket_Booking.service.MomoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/client/momo")
public class MomoController {
    @Autowired
    private MomoService momoService;

    private ResponseEntity<Map<String, Object>> createResponse(String status, Object data, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("data", data);
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

        @PostMapping("/create")
        public ResponseEntity<Map<String, Object>> createQr(@RequestBody ThanhToanDonHangRequestDto dto) {
            CreateMomoResponse createMomo = momoService.createQR(dto);
            return createResponse("succes",createMomo,"Đang chuyển hướng sang MoMo...");
        }

}
