package com.quoc.Movie_Ticket_Booking.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThongKeChiTieuResponseDto {
    private LocalDate ngay;
    private String tenKhachHang;
    private Long soDonHang;
    private Double tongChiTieu;


}
