package com.quoc.Movie_Ticket_Booking.dto.request;

import com.quoc.Movie_Ticket_Booking.model.ChucVu;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NhanVienRequestDto {

    private Long id;

    private String hoVaTen;

    private String email;

    private String soDienThoai;

    private String passWord;

    private String confirmPassWord;

    private LocalDate ngaySinh;

    private String diaChi;

    private Integer tinhTrang;

    private Long idChucVu;

}
