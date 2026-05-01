package com.quoc.Movie_Ticket_Booking.dto.request;

import lombok.Data;

import java.time.LocalDate;
@Data
public class KhachHangRequestDto {

    private Long id;

    private String hoVaTen;

    private String email;

    private String passWord;

    private String soDienThoai;

    private String cccd;

    private LocalDate ngaySinh;


    private Integer isActive;


    private Integer isBlock;
}
