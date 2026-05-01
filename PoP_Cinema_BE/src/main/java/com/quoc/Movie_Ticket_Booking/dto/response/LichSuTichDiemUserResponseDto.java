package com.quoc.Movie_Ticket_Booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LichSuTichDiemUserResponseDto {

    private LocalDateTime ngayTao;

    private Integer diem;

    private Integer loai;

    private String moTa;

    private Integer tinhTrang;

}
