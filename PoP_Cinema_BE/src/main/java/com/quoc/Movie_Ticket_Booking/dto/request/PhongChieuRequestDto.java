package com.quoc.Movie_Ticket_Booking.dto.request;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class PhongChieuRequestDto {

    private Long id;

    private String tenPhong;

    private String tenHinhAnh;

    private Integer hangNgang;

    private Integer hangDoc;

    private Integer tinhTrang;
}
