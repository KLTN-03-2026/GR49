package com.quoc.Movie_Ticket_Booking.repository;

import com.quoc.Movie_Ticket_Booking.dto.response.ThongKeChiTieuResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ThongKeDonHangResponseDto;
import com.quoc.Movie_Ticket_Booking.model.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ThongKeRepository extends JpaRepository<DonHang,Long> {


    // Thống kê tất cả
    @Query("""
    SELECT DATE(d.ngayDat), COUNT(d.id), SUM(d.tongTien), SUM(d.giamGia), SUM(d.tienThucNhan)
    FROM DonHang d
    WHERE d.isThanhToan = 1
    GROUP BY DATE(d.ngayDat)
    ORDER BY DATE(d.ngayDat)
""")
    List<Object[]> getThongKeDoanhThu();



    // Thống kê theo khoảng ngày
    @Query("""
    SELECT DATE(d.ngayDat), COUNT(d.id), SUM(d.tongTien), SUM(d.giamGia), SUM(d.tienThucNhan)
    FROM DonHang d
    WHERE d.isThanhToan = 1
      AND d.ngayDat BETWEEN :ngayBatDau AND :ngayKetThuc
    GROUP BY DATE(d.ngayDat)
    ORDER BY DATE(d.ngayDat)
""")
    List<Object[]> getThongKeDoanhThuTheoNgay(@Param("ngayBatDau") LocalDateTime ngayBatDau,
                                              @Param("ngayKetThuc") LocalDateTime ngayKetThuc);





    // Thống kê Chi tiêu
    @Query(""" 
    SELECT DATE(d.ngayDat),u.hoVaTen,COUNT(d.id),SUM(d.tienThucNhan)
    FROM DonHang d  
    JOIN Users u ON d.khachHang.id = u.id
    WHERE d.isThanhToan = 1
    GROUP BY DATE(d.ngayDat),u.hoVaTen
    ORDER BY DATE(d.ngayDat),u.hoVaTen DESC 
""")
    List<Object[]> getThongKeChiTieu();


    // Thống kê Chi tiêu theo khoảng ngày
    @Query("""
    SELECT DATE(d.ngayDat),u.hoVaTen,COUNT(d.id),SUM(d.tienThucNhan)
    FROM DonHang d
    JOIN Users u ON d.khachHang.id = u.id
    WHERE d.isThanhToan = 1
    AND d.ngayDat BETWEEN :ngayBatDau AND :ngayKetThuc
    GROUP BY DATE(d.ngayDat),u.hoVaTen
    ORDER BY DATE(d.ngayDat),u.hoVaTen DESC 
""")
    List<Object[]> getThongKeChiTieuTheoNgay(@Param("ngayBatDau") LocalDateTime ngayBatDau,
                                                              @Param("ngayKetThuc") LocalDateTime ngayKetThuc);


    @Query("SELECT SUM(d.tienThucNhan) FROM DonHang d WHERE d.isThanhToan = 1")
    public Double getTongDoanhThu();


    @Query("SELECT SUM(d.tienThucNhan) FROM DonHang d WHERE d.khachHang.id = :userId AND d.isThanhToan = 1")
    Double sumThanhTienByUser(@Param("userId") Long userId);


}
