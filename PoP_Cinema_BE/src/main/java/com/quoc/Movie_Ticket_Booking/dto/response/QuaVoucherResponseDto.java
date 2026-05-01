package com.quoc.Movie_Ticket_Booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuaVoucherResponseDto {

    private Long id;

    private String hinhAnh;

    private String tenVoucher;

    private String moTa;

    private LocalDate thoiGianBatDau;

    private LocalDate thoiGianKetThuc;

    private Integer soTienGiamGia;

    private Integer soTienToiDa;

    private Integer soDiem;
}
