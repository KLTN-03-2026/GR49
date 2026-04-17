package com.quoc.Movie_Ticket_Booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherResponseDto {

    private Long id;

    private String maCode;

    private String tenVoucher;

    private String hinhAnh;

    private String moTa;

    private LocalDate thoiGianBatDau;

    private LocalDate thoiGianKetThuc;

    private Integer soGiamGia;

    private Integer soTienToiDa;

    private Integer soTienGiamGia;

    private Integer tinhTrang;

    private Integer soDiem;
}
