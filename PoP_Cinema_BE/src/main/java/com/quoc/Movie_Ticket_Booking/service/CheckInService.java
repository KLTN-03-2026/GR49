package com.quoc.Movie_Ticket_Booking.service;

import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;

public interface CheckInService {
    ApiResponse<?> checkIn(String qr);
}
