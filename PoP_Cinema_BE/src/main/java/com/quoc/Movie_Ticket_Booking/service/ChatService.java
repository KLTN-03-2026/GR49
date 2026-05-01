package com.quoc.Movie_Ticket_Booking.service;

import com.quoc.Movie_Ticket_Booking.dto.response.ChatResponse;
import reactor.core.publisher.Flux;

public interface ChatService {

    public Object chatWithStream (String message,String jwt);

}
