package com.quoc.Movie_Ticket_Booking.controller.client;

import com.quoc.Movie_Ticket_Booking.dto.request.ChangStatusDto;
import com.quoc.Movie_Ticket_Booking.dto.response.PhimHomeResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.PhimResponseDto;
import com.quoc.Movie_Ticket_Booking.model.Phim;
import com.quoc.Movie_Ticket_Booking.service.PhimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/client/phims")
public class ClientPhimController {

    @Autowired
    private PhimService phimService;

    private ResponseEntity<Map<String, Object>> createResponse(String status, Object data, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("data", data);
        response.put("message", message);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/get-data")
    public ResponseEntity<?> getDataPhim()  {

        Map<String, Object> dataClientPhim = phimService.getDataClientPhim();
        return new ResponseEntity<>(dataClientPhim, HttpStatus.OK);
    }
}
