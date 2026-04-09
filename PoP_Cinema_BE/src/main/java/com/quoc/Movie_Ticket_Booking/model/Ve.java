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
@Table(name = "ves")
public class Ve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_ve", unique = true)
    private String maVe;

    @Column(name = "gia_ve")
    private Integer giaVe;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_don_hang")
    private DonHang donHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_suat_chieu")
    private SuatChieu suatChieu;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ghe")
    private Ghe ghe;


    @Column(name = "ten_ghe")
    private String tenGhe;

    @Column(name = "tinh_trang")
    private Integer tinhTrang=1 ; // 1: Chưa thanh toán, 2: Đã thanh toán, 0: Đã hủy


    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;


    public static final int DA_HUY = 0;
    public static final int CHUA_THANH_TOAN = 1;
    public static final int DA_THANH_TOAN = 2;

}
