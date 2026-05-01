package com.quoc.Movie_Ticket_Booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatVeGanDayResponseDto {

    private String tenKhach;

    private String tenPhim;

    private Long soVe;

    private Double tongTien;

    private LocalDate ngay;

}
