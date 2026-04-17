package com.quoc.Movie_Ticket_Booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChucVuResponseDto {

    private Long id;

    private String tenChucVu;

    private String slugChucVu;

    private Integer tinhTrang;
}
