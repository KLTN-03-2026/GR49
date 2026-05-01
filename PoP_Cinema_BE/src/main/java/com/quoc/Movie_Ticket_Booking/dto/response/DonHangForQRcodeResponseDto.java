package com.quoc.Movie_Ticket_Booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonHangForQRcodeResponseDto {
    private Long id;

    private String maDonHang;

    private LocalDateTime ngayDat;

    private Integer tongTien;

}
