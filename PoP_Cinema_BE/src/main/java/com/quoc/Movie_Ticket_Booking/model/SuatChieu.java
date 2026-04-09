package com.quoc.Movie_Ticket_Booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "suat_chieus")
public class SuatChieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_phim")
    private Phim phim;

    // Quan hệ với Phòng Chiếu
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_phong_chieu")
    private PhongChieu phongChieu;

    @Column(name = "ngay_chieu")
    private LocalDate ngayChieu;

    @Column(name = "thoi_gian_bat_dau")
    private LocalTime thoiGianBatDau;

    @Column(name = "thoi_gian_ket_thuc")
    private LocalTime thoiGianKetThuc;

    @Column(name = "tinh_trang")
    private Integer tinhTrang = 1;

}
