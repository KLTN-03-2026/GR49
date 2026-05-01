package com.quoc.Movie_Ticket_Booking.dto.request;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VoucherRequestDto {

    private Long id;

    private String maCode;

    private String tenVoucher;

    private Integer soDiem;

    private String hinhAnh;

    private String moTa;

    private LocalDate thoiGianBatDau;

    private LocalDate thoiGianKetThuc;

    private Integer soGiamGia;

    private Integer soTienToiDa;

    private Integer soTienGiamGia;

    private Integer tinhTrang ;
}
