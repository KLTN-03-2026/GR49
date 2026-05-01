package com.quoc.Movie_Ticket_Booking.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThayDoiThongTinNhanVienRequestDto {

    private String hoVaTen;

    private String email;

    private String soDienThoai;

    private String diaChi;

    private LocalDate ngaySinh;
}
