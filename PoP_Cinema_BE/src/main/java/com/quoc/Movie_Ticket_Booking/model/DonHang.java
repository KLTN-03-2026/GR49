package com.quoc.Movie_Ticket_Booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "don_hangs")
public class DonHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_khach_hang")
    private Users khachHang;

    @Column(name = "ma_don_hang", nullable = false, unique = true)
    private String maDonHang;

    @Column(name = "ngay_dat", nullable = false)
    private LocalDateTime ngayDat;

    @Column(name = "tien_thuc_nhan")
    private Integer tienThucNhan;

    @Column(name = "tong_tien")
    private Integer tongTien;

    @Column(name = "giam_gia")
    private Integer giamGia;

    @Column(name = "is_thanh_toan")
    private Integer isThanhToan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_voucher",nullable = true)
    private Voucher voucher;

    @Column(name = "phuong_thuc_thanh_toan")
    private Integer phuongThucThanhToan;


    public static final int CHUA_THANH_TOAN = 0;
    public static final int DA_THANH_TOAN = 1;
    public static final int DA_HUY = 2;
}
