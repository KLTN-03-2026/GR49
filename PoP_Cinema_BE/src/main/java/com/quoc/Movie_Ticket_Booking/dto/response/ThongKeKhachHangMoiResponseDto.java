package com.quoc.Movie_Ticket_Booking.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ThongKeKhachHangMoiResponseDto {
    private LocalDate ngay;
    private Long soLuongKhacHangMoi;
}
