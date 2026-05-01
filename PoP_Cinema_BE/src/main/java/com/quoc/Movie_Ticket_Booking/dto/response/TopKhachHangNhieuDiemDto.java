package com.quoc.Movie_Ticket_Booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopKhachHangNhieuDiemDto {
    private Long userId;
    private String tenKhachHang;
    private String hang;
    private Integer diem;
}
