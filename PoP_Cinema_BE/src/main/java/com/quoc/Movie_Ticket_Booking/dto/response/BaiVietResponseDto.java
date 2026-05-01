package com.quoc.Movie_Ticket_Booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaiVietResponseDto {

    private  Long id;

    private String tieuDe;

    private String moTaNgan;

    private String noiDung;

    private String hinhAnh;

    private String tag;

    private Integer tinhTrang = 1;
}
