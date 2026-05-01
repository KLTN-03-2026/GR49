package com.quoc.Movie_Ticket_Booking.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class BaiVietRequestDto {

    private Long id;

    private String tieuDe;

    private String moTaNgan;

    private String noiDung;

    private String hinhAnh;

    private String tag;

    private Integer tinhTrang = 1;
}
