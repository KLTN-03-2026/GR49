package com.quoc.Movie_Ticket_Booking.dto.response;

import com.quoc.Movie_Ticket_Booking.model.DonHang;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonHangDetailsResponseDto {
    private Long id;

    private String maDonHang;

    private LocalDateTime ngayDat;

    private Integer tongTien;

    private Integer tienThucNhan;

    private Integer giamGia;

    private Integer isThanhToan;

    private String tenPhim;

    private String hinhAnh;

    private String theLoai;

    private Integer thoiLuong;

    private String noiDung;

    private String hoVaTen;

    private String email;

    private String soDienThoai;


}
