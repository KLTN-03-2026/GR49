package com.quoc.Movie_Ticket_Booking.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonHangResponseDto {

    private String maDonHang;

    private LocalDateTime ngayDat;

    private String tenPhim;

    private String theLoai;

    private LocalDate ngayChieu;

    private LocalTime thoiGianBatDau;

    private LocalTime thoiGianKetThuc;

    private String tenPhongChieu;

    private Integer tongTien;

    private Integer tienThucNhan;

    private Integer giamGia;

}
