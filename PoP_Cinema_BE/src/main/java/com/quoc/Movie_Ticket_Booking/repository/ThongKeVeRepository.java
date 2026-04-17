package com.quoc.Movie_Ticket_Booking.repository;

import com.quoc.Movie_Ticket_Booking.dto.response.PhimPhoBienResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ThongKeSuatChieuResponseDto;
import com.quoc.Movie_Ticket_Booking.model.Ve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ThongKeVeRepository extends JpaRepository<Ve,Long> {

    // Thống ke tat ca ve
    @Query("""
    SELECT DATE(d.ngayDat), COUNT(v.donHang.id),  SUM(CASE WHEN v.tinhTrang = 2 THEN 1 ELSE 0 END)
    FROM Ve v
    JOIN DonHang d ON v.donHang.id = d.id
    GROUP BY DATE(d.ngayDat)
    ORDER BY DATE(d.ngayDat)
""")
    List<Object[]> getThongKeVe();


    // Thống ke ve theo khoang ngày
    @Query("""
    SELECT DATE(d.ngayDat), COUNT(v.donHang.id),  SUM(CASE WHEN v.tinhTrang = 2 THEN 1 ELSE 0 END)
    FROM Ve v
    JOIN DonHang d ON v.donHang.id = d.id
    WHERE d.ngayDat BETWEEN :ngayBatDau AND :ngayKetThuc
    GROUP BY DATE(d.ngayDat)
    ORDER BY DATE(d.ngayDat)
""")
    List<Object[]> getThongKeVeTheoNgay(@Param("ngayBatDau") LocalDateTime ngayBatDau,
                                              @Param("ngayKetThuc") LocalDateTime ngayKetThuc);


    @Query("SELECT COUNT(v) FROM Ve v WHERE v.tinhTrang = 2 ")
    public Long getTongVeDaBan();


    // Hiển thị đặt vé gần đây
    @Query(""" 
    SELECT u.hoVaTen,p.tenPhim,COUNT(v.id),d.tienThucNhan, MAX(DATE(d.ngayDat)) 
    FROM Ve v
    JOIN SuatChieu s ON v.suatChieu.id = s.id
    JOIN Phim p ON s.phim.id = p.id
    JOIN DonHang d ON v.donHang.id = d.id
    JOIN Users u ON d.khachHang.id = u.id
    WHERE d.ngayDat BETWEEN :ngayBatDau AND :ngayKetThuc
    GROUP BY d.id, u.hoVaTen, p.tenPhim, d.tienThucNhan
    ORDER BY d.ngayDat DESC
""")
    List<Object[]> getThongKeVeDatGanDay(@Param("ngayBatDau") LocalDateTime ngayBatDau,
                                        @Param("ngayKetThuc") LocalDateTime ngayKetThuc);

    // Thống ke phim phổ biến
    @Query("""
     SELECT new com.quoc.Movie_Ticket_Booking.dto.response.PhimPhoBienResponseDto(p.tenPhim,p.theLoai,COUNT(v.id))
    FROM Ve v
    JOIN SuatChieu s ON v.suatChieu.id = s.id
    JOIN Phim p ON s.phim.id = p.id
    WHERE v.tinhTrang = 2 
    GROUP BY p.tenPhim,p.theLoai
    ORDER BY COUNT(v.id) DESC
    LIMIT 5
""")
    List<PhimPhoBienResponseDto> getPhimPhoBien();


}
