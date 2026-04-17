package com.quoc.Movie_Ticket_Booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ThongKeDichVuResponseDto {
    private LocalDate ngay;
    private Long soDichVuDaBan;
    private Double daThanhToan;
}
