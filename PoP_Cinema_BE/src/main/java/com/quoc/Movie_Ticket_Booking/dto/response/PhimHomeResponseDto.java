package com.quoc.Movie_Ticket_Booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhimHomeResponseDto {
    private Long id;

    private String tenPhim;

    private String hinhAnh;

    private String theLoai;

    private LocalDate ngayPhatHanh;

    private Integer tinhTrang;
}
