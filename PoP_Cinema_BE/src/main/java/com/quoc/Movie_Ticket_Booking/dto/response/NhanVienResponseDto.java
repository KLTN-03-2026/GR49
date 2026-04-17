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
public class NhanVienResponseDto {
    private Long id;

    private String hoVaTen;

    private String email;

    private String soDienThoai;

    private LocalDate ngaySinh;

    private String diaChi;

    private Integer tinhTrang;

    private Long idChucVu;

    private String tenChucVu;
}
