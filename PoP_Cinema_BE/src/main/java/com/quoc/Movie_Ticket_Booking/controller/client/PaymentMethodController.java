package com.quoc.Movie_Ticket_Booking.controller.client;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.quoc.Movie_Ticket_Booking.controller.payospayment.PayOsService;
import com.quoc.Movie_Ticket_Booking.dto.request.ThanhToanDonHangRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.CreateMomoResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.DonHangDetailsResponseDto;
import com.quoc.Movie_Ticket_Booking.service.MomoService;
import com.quoc.Movie_Ticket_Booking.vnpaypayment.PaymentResponse;
import com.quoc.Movie_Ticket_Booking.vnpaypayment.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/client/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentService paymentService;

    private final PayOsService payOsService;

    private final MomoService momoService;

    private ResponseEntity<Map<String, Object>> createResponse(String status, Object data, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("data", data);
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/vnpay/create")
    public ResponseEntity<Map<String, Object>> createVNPay(@RequestBody ThanhToanDonHangRequestDto responseDto, HttpServletRequest request) {
        PaymentResponse response = paymentService.createVnPayPayment(responseDto,request);
        return createResponse("succes",response,"Đang chuyển hướng sang VNPAY...");
    }

    @PostMapping("/momo/create")
    public ResponseEntity<Map<String, Object>> createMoMo(@RequestBody ThanhToanDonHangRequestDto dto) {
        CreateMomoResponse createMomo = momoService.createQR(dto);
        return createResponse("succes",createMomo,"Đang chuyển hướng sang MoMo...");
    }

    @PostMapping( "/payos/create")
    public ResponseEntity<?> createPayOs(@RequestBody DonHangDetailsResponseDto responseDto) {
        ObjectNode payOsPayment = payOsService.createPayOsPayment(responseDto);
        return new ResponseEntity<>(payOsPayment, HttpStatus.CREATED);
    }
}
