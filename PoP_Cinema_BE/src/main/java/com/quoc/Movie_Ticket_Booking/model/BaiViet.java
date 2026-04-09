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
@Table(name = "bai_viets")
public class BaiViet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tieu_de")
    private String tieuDe;

    @Column(name = "mo_ta_ngan")
    private String moTaNgan;

    @Lob
    @Column(name = "noi_dung",columnDefinition = "TEXT")
    private String noiDung;

    @Column(name = "hinh_anh")
    private String hinhAnh;

    @Column(name = "tag")
    private String tag;

    @Column(name = "tinh_trang")
    private Integer tinhTrang = 1;
}
