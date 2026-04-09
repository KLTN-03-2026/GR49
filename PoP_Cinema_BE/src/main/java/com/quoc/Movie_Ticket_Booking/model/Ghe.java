package com.quoc.Movie_Ticket_Booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ghes")
public class Ghe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_ghe")
    private String tenGhe;

    @Column(name = "gia_ghe")
    private String giaGhe;  // Nếu cần xử lý toán học, nên chuyển sang BigDecimal

    @Column(name = "tinh_trang")
    private Integer tinhTrang = 1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_phong_chieu")
    private PhongChieu phongChieu;

    @Column(name = "loai_ghe")
    private String loaiGhe;
}
