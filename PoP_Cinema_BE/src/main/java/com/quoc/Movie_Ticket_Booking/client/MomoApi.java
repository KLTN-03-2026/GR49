package com.quoc.Movie_Ticket_Booking.client;


import com.quoc.Movie_Ticket_Booking.dto.request.CreateMomoRequest;
import com.quoc.Movie_Ticket_Booking.dto.response.CreateMomoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="momo", url = "https://test-payment.momo.vn/v2/gateway/api")
public interface MomoApi {

    @PostMapping("/create")
    CreateMomoResponse createMomoQr(@RequestBody CreateMomoRequest request);

}
