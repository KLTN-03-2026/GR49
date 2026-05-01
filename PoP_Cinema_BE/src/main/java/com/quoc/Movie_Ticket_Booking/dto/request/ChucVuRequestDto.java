package com.quoc.Movie_Ticket_Booking.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class ChucVuRequestDto {

    private Long id;

    private String tenChucVu;

    private String slugChucVu;

    private Integer tinhTrang;
}
