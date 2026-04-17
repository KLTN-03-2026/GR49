package com.quoc.Movie_Ticket_Booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Builder
public class DichVuResponseDto {
    private Long id;

    private String tenDichVu;

    private String hinhAnh;

    private Integer gia;

    private String moTa;

    private Integer tinhTrang ;
}
