package com.quoc.Movie_Ticket_Booking.dto.request;

import com.quoc.Movie_Ticket_Booking.model.Phim;
import com.quoc.Movie_Ticket_Booking.model.PhongChieu;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SuatChieuRequestDto {


    private Long id;

    private Long idPhim;

    private Long idPhongChieu;

    private LocalDate ngayChieu;

    private LocalTime thoiGianBatDau;

    private LocalTime thoiGianKetThuc;

    private Integer tinhTrang ;
}
