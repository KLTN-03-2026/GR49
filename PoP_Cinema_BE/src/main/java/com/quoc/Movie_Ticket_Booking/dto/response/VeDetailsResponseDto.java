package com.quoc.Movie_Ticket_Booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VeDetailsResponseDto {

    private String maVe;

    private String tenPhim;

    private LocalDate ngayChieu;

    private LocalTime thoiGianBatDau;

    private LocalTime thoiGianKetThuc;

    private String tenGhe;

    private Integer giaVe;

    private Long idDonHang;

}
