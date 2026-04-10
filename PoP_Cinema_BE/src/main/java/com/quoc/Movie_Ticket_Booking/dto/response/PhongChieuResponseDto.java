package com.quoc.Movie_Ticket_Booking.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhongChieuResponseDto {

    private Long id;

    private String tenPhong;

    private Integer hangNgang;

    private Integer hangDoc;

    private Integer tinhTrang;

    private String tenHinhAnh;
}
