package com.quoc.Movie_Ticket_Booking.controller.client;

import com.quoc.Movie_Ticket_Booking.dto.request.ThanhToanDonHangRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.DonHangDetailsResponseDto;
import com.quoc.Movie_Ticket_Booking.vnpaypayment.PaymentResponse;
import com.quoc.Movie_Ticket_Booking.vnpaypayment.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/client/vnpay")
@RequiredArgsConstructor
public class PaymentVnpayController {
    private final PaymentService paymentService;


    private ResponseEntity<Map<String, Object>> createResponse(String status, Object data, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("data", data);
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> pay(@RequestBody ThanhToanDonHangRequestDto responseDto, HttpServletRequest request) {
        PaymentResponse response = paymentService.createVnPayPayment(responseDto,request);
        return createResponse("succes",response,"Đang chuyển hướng sang VNPAY...");
    }

}
