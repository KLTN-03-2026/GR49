package com.quoc.Movie_Ticket_Booking.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "phims")
public class Phim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_phim", nullable = false)
    private String tenPhim;

    @Column(name = "dao_dien")
    private String daoDien;

    @Column(name = "dien_vien")
    private String dienVien;

    @Column(name = "hinh_anh")
    private String hinhAnh;

    @Column(name = "quoc_gia")
    private String quocGia;

    @Column(name = "nha_san_xuat")
    private String nhaSanXuat;

    @Column(name = "trailer")
    private String trailer;

    @Column(name = "ngay_phat_hanh")
    private LocalDate ngayPhatHanh;

    @Column(name = "ngon_ngu")
    private String ngonNgu;

    @Column(name = "the_loai")
    private String theLoai;

    @Column(name = "thoi_luong")
    private Integer thoiLuong; // Tính bằng phút

    @Lob
    @Column(name = "noi_dung",columnDefinition = "TEXT")
    private String noiDung;

    @Column(name = "tinh_trang")
    private Integer tinhTrang = 1; // 1 = Sắp chiếu, 2 = Đang chiếu, 0 = Ngừng chiếu


    public static final int SAP_CHIEU = 1;
    public static final int DANG_CHIEU = 2;
    public static final int NGUNG_CHIEU = 0;

}
