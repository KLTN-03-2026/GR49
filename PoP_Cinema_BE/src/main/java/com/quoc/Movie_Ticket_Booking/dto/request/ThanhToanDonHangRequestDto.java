package com.quoc.Movie_Ticket_Booking.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThanhToanDonHangRequestDto {
    private String maDonHang;
    private Integer tongTien;
    private Integer tienThucNhan;
}
