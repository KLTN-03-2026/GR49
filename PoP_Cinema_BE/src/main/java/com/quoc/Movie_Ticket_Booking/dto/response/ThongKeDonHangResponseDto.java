package com.quoc.Movie_Ticket_Booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThongKeDonHangResponseDto {

    private LocalDate ngay;
    private Long soDon;
    private Double tongDoanhThu;
    private Double tongGiamGia;
    private Double tongDoanhThuThuc;



}
