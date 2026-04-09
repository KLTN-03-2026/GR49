package com.quoc.Movie_Ticket_Booking.dto.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhimResponseDto {

    private Long id;

    private String tenPhim;

    private String daoDien;

    private String dienVien;

    private String hinhAnh;

    private String quocGia;

    private String nhaSanXuat;

    private String trailer;

    private LocalDate ngayPhatHanh;

    private String ngonNgu;

    private String theLoai;

    private Integer thoiLuong; // Tính bằng phút

    private String noiDung;

    private Integer tinhTrang;







}
