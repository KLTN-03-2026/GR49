package com.quoc.Movie_Ticket_Booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThongKeVeResponseDto {

    private LocalDate ngay;

    private Long soVeBan;

    private Double veDaThanhToan;
}
