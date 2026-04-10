package com.quoc.Movie_Ticket_Booking.dto.response;

import com.quoc.Movie_Ticket_Booking.model.Phim;
import com.quoc.Movie_Ticket_Booking.model.PhongChieu;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class SuatChieuResponseDto {

    private Long id;

    private  Long idPhim;

    private String tenPhim;

    private Long idPhongChieu;

    private String tenPhongChieu;

    private LocalDate ngayChieu;

    private LocalTime thoiGianBatDau;

    private LocalTime thoiGianKetThuc;

    private Integer tinhTrang;

}
