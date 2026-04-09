package com.quoc.Movie_Ticket_Booking.dto.response;

import com.quoc.Movie_Ticket_Booking.model.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;

    private String message;

    private USER_ROLE role;
}
