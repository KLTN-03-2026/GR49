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
@Table(name = "tich_diems")
public class TichDiem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_don_hang")
    private DonHang donHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_khach_hang")
    private Users khachHang;

    @Column(name = "diem")
    private Integer diem;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "tinh_trang")
    private Integer tinhTrang;

    @Column(name = "loai")
    private Integer loai;

    @Column(name = "moTa")
    private String moTa;

    @Column(name = "tong_diem")
    private Integer tongDiem;

    public static final int CHUA_XAC_DINH = 0;
    public static final int DA_CONG = 1;
    public static final int DA_TRU = 2;

}
