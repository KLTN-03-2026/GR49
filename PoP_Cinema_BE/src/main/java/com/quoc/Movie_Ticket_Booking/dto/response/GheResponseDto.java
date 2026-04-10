package com.quoc.Movie_Ticket_Booking.dto.response;

import com.quoc.Movie_Ticket_Booking.model.PhongChieu;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GheResponseDto {

    private Long id;

    private String tenGhe;

    private String giaGhe;  // Nếu cần xử lý toán học, nên chuyển sang BigDecimal

    private Integer tinhTrang ;

    private Long phongChieu;

    private String tenPhongChieu;

    private String loaiGhe;
}
