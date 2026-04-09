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
@Table(name = "danh_gias")
public class DanhGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "so_sao", nullable = false)
    private Integer soSao;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_khach_hang",nullable = false)
    private Users khachHang;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_phim",nullable = false)
    private Phim phim;


    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

}
