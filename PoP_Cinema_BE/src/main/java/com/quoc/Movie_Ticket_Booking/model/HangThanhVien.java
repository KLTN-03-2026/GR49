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
@Table(name = "hang_thanh_viens")
public class HangThanhVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_hang")
    private String tenHang;

    @Column(name = "diem_toi_thieu")
    private Integer diemToiThieu;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "tinh_trang")
    private Integer tinhTrang;
}
