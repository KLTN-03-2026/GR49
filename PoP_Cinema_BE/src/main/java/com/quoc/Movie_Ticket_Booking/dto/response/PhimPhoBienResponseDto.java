package com.quoc.Movie_Ticket_Booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhimPhoBienResponseDto {

    private String hinhAnh;

    private String tenPhim;

    private String theLoai;

    private Long soVe;
}
