package com.quoc.Movie_Ticket_Booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ThongKePhimResponseDto {
    private LocalDate ngay;
    private Long soLuongPhimDangChieu;
}
