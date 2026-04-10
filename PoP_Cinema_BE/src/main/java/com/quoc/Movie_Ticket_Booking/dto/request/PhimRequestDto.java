package com.quoc.Movie_Ticket_Booking.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PhimRequestDto {

   private Long id;
//    @NotBlank(message = "Movie name cannot be blank")
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

    public Integer tinhTrang;

}
