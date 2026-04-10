package com.quoc.Movie_Ticket_Booking.dto.request;

import com.quoc.Movie_Ticket_Booking.model.DonHang;
import com.quoc.Movie_Ticket_Booking.model.SuatChieu;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class VeRequestDto {

    private Long id;

    private Integer giaVe;

    private Long idDonHang;

    private Long idSuatChieu;

    private String tenGhe;

    private Integer tinhTrang;
}
