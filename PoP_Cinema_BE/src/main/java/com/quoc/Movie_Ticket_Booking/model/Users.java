package com.quoc.Movie_Ticket_Booking.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users {

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

    @Column(name = "cccd")
    private String cccd;

    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "hash_reset")
    private String hashReset; // ma dat lai mat khau

    @Column(name = "hash_active")
    private String hashActive; //ma kich hoat  sau khi dang ki

    @Column(name = "is_active")
    private Integer isActive=0; //'0: chưa kích hoạt, 1: đã kích hoạt'

    @Column(name = "is_block")
    private Integer isBlock=0; //'0: chưa bị khóa, 1: đã bị khóa'

    @Column(name = "role")
    private USER_ROLE role=USER_ROLE.ROLE_USER;

    @Column(name = "diem")
    private Integer diem = 0;

    @ManyToOne
    @JoinColumn(name = "id_hang_thanh_vien")
    private HangThanhVien hangThanhVien;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}
