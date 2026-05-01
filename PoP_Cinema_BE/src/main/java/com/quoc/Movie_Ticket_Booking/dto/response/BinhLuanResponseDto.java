package com.quoc.Movie_Ticket_Booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class BinhLuanResponseDto {

    private Long idKhach;

    private String tenKhach;

    private String noiDung;

    private String ngay;

    private String avatar;

    public BinhLuanResponseDto() {
    }

    public BinhLuanResponseDto(Long idKhach, String tenKhach, String noiDung, LocalDateTime createdAt, String avatar) {
        this.idKhach = idKhach;
        this.tenKhach = tenKhach;
        this.noiDung = noiDung;
        this.ngay = createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.avatar = avatar;
    }
}
