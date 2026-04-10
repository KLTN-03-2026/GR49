package com.quoc.Movie_Ticket_Booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class VeResponseDto {

    private Long id;

    private String maVe;

    private Long donHang;

    private String hoVaTen;

    private String tenPhim;

    private Long idSuatChieu;

    private LocalDate ngayChieu;

    private LocalTime thoiGianBatDau;

    private LocalTime thoiGianKetThuc;

    private String tenGhe;

    private Integer giaVe;

    private LocalDateTime ngayDat;

    private Integer tinhTrang;
}
