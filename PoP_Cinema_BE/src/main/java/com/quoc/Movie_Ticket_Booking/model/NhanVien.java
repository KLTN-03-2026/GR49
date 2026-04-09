package com.quoc.Movie_Ticket_Booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nhan_viens")
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ho_va_ten")
    private String hoVaTen;

    @Column(name = "email")
    private String email;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "password")
    private String password;

    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "tinh_trang")
    private Integer tinhTrang=1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_chuc_vu")
    private ChucVu chucVu;

    @Column(name = "role")
    private USER_ROLE role=USER_ROLE.ROLE_STAFF;


}
