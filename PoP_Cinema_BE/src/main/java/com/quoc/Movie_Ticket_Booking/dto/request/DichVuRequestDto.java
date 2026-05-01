package com.quoc.Movie_Ticket_Booking.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class DichVuRequestDto {

    private Long id;

    private String tenDichVu;

    private String hinhAnh;

    private Integer gia;

    private String moTa;

    private Integer tinhTrang ;
}
