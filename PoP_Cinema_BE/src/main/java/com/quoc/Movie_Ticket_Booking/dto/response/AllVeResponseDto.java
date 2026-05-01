package com.quoc.Movie_Ticket_Booking.dto.response;

import com.quoc.Movie_Ticket_Booking.model.DonHang;
import com.quoc.Movie_Ticket_Booking.model.SuatChieu;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class AllVeResponseDto {
    private Long id;

    private String maVe;

    private Integer giaVe;

    private Long donHang;

    private Long suatChieu;

    private String tenGhe;

    private Integer tinhTrang ;

    private String loaiVe;



}
