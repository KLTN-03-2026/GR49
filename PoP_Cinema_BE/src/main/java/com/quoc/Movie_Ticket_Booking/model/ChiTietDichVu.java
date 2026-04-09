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
@Table(name = "chi_tiet_dich_vus")
public class ChiTietDichVu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_don_hang")
    private DonHang donHang;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dich_vu")
    private DichVu dichVu;

    @Column(name = "don_gia")
    private Integer donGia;

    @Column(name = "ghi_chu",length = 5000)
    private String ghiChu;

}
