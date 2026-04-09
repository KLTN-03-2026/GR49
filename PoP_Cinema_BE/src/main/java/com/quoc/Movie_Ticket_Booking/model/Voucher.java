package com.quoc.Movie_Ticket_Booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vouchers")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_code")
    private String maCode;

    @Column(name = "ten_voucher")
    private String tenVoucher;

    @Column(name = "thoi_gian_bat_dau")
    private LocalDate thoiGianBatDau;

    @Column(name = "thoi_gian_ket_thuc")
    private LocalDate thoiGianKetThuc;

    @Column(name = "so_giam_gia")
    private Integer soGiamGia;

    @Column(name = "so_tien_toi_da")
    private Integer soTienToiDa;

    @Column(name = "so_tien_giam_gia")
    private Integer soTienGiamGia;

    @Column(name = "so_diem")
    private Integer soDiem;

    @Column(name = "tinh_trang")
    private Integer tinhTrang = 1;

    @Column(name = "hinh_anh")
    private String hinhAnh;

    @Column(name = "mo_ta")
    private String moTa;


}
