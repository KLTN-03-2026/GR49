package com.quoc.Movie_Ticket_Booking.dto.response;

import com.quoc.Movie_Ticket_Booking.model.DichVu;
import com.quoc.Movie_Ticket_Booking.model.DonHang;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietDichVuResponseDto {

    private Long id;

    private Long idDonHang;

    private String  tenDichVu;

    private Integer gia;

    private String ghiChu;
}
