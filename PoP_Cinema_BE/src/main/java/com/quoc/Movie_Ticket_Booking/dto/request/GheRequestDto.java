package com.quoc.Movie_Ticket_Booking.dto.request;

import com.quoc.Movie_Ticket_Booking.model.PhongChieu;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class GheRequestDto {

    private Long id;

    private String tenGhe;

    private String giaGhe;  // Nếu cần xử lý toán học, nên chuyển sang BigDecimal

    private Long phongChieu;

    private Integer tinhTrang;

    private String loaiGhe;
}
