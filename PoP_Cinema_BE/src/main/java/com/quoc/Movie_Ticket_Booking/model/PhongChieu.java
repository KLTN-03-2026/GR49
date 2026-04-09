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
@Table(name = "phong_chieus")
public class PhongChieu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_phong")
    private String tenPhong;

    @Column(name = "hang_ngang")
    private Integer hangNgang;

    @Column(name = "hang_doc")
    private Integer hangDoc;

    @Column(name = "tinh_trang")
    private Integer tinhTrang = 1;

    @Column(name = "hinh_anh")
    private String hinhAnh;

}
