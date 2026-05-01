package com.quoc.Movie_Ticket_Booking.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LichSuQuaVoucherResponseDto {

    private String hinhAnh;

    private String tenVoucher;

    private String moTa;

    private String maCode;

    private LocalDateTime ngayNhan;

    private LocalDate thoiGianBatDau;

    private LocalDate  thoiGianKetThuc;

    private Integer soTienGiamGia;

    private Integer tinhTrang;
}
