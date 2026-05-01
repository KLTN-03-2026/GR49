package com.quoc.Movie_Ticket_Booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TichDiemResponseDto {

    private Long id;

    private String tenKhachHang;

    private Integer diem;

    private Integer diemConLai;

    private Integer loai;

    private String moTa;

    private LocalDateTime ngayTao;

    private Integer tinhTrang;





}
