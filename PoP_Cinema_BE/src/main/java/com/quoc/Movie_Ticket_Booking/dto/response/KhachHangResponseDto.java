package com.quoc.Movie_Ticket_Booking.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KhachHangResponseDto {

    private Long id;

    private String hoVaTen;

    private String email;

    private String soDienThoai;

    private String cccd;

    private LocalDate ngaySinh;


    private Integer isActive;


    private Integer isBlock;


}
