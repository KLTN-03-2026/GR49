package com.quoc.Movie_Ticket_Booking.controller.payospayment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.quoc.Movie_Ticket_Booking.dto.response.DonHangDetailsResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;


@RestController
@RequestMapping("/api/client/payos")
public class OrderController {

    @Autowired
    private  PayOS payOS;

    @Autowired
    private PayOsService payOsService;



        @PostMapping( "/create")
        public ResponseEntity<?> createPaymentLink(@RequestBody DonHangDetailsResponseDto responseDto) {
        ObjectNode payOsPayment = payOsService.createPayOsPayment(responseDto);
        return new ResponseEntity<>(payOsPayment, HttpStatus.CREATED);
    }

//    @GetMapping(path = "/{orderId}")
//    public ObjectNode getOrderById(@PathVariable("orderId") long orderId) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        ObjectNode response = objectMapper.createObjectNode();
//
//        try {
//            PaymentLinkData order = payOS.getPaymentLinkInformation(orderId);
//
//            response.set("data", objectMapper.valueToTree(order));
//            response.put("error", 0);
//            response.put("message", "ok");
//            return response;
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.put("error", -1);
//            response.put("message", e.getMessage());
//            response.set("data", null);
//            return response;
//        }
//
//    }



}

