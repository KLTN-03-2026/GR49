package com.quoc.Movie_Ticket_Booking.dto.response;

import com.quoc.Movie_Ticket_Booking.model.ChucNang;
import com.quoc.Movie_Ticket_Booking.model.ChucVu;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhanQuyenResponseDto {

    private Long id;

    private Long idChucVu;

    private String tenChucVu;

    private Long idChucNang;

    private String tenChucNang;
}
