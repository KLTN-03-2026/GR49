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
@Table(name = "qua_vouchers")
public class QuaVoucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_voucher")
    private Voucher voucher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_khach_hang")
    private Users khachHang;

    @Column(name = "ma_code")
    private String maCode;

    @Column(name = "ngay_nhan")
    private LocalDateTime ngayNhan;

    @Column(name = "tinh_trang")
    private Integer tinhTrang;

    public static final int CHUA_SU_DUNG = 0;
    public static final int DA_SU_DUNG = 1;

}
