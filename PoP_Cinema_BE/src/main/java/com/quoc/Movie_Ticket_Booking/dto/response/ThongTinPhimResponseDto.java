package com.quoc.Movie_Ticket_Booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class ThongTinPhimResponseDto {

    private String tenPhim;

    private String ngonNgu;

    private String hinhAnh;

    private LocalDate ngayChieu;

    private LocalTime thoiGianBatDau;

    private LocalTime thoiGianKetThuc;




}
