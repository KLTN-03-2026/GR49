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
@Table(name = "dich_vus")
public class DichVu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_dich_vu")
    private String tenDichVu;

    @Column(name = "hinh_anh")
    private String hinhAnh;

    @Column(name = "gia")
    private Integer gia;

    @Column(name = "mo_ta",length = 5000)
    private String moTa;

    @Column(name = "tinh_trang")
    private Integer tinhTrang = 1;
}
