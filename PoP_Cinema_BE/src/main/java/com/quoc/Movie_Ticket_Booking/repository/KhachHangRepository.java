package com.quoc.Movie_Ticket_Booking.repository;

import com.quoc.Movie_Ticket_Booking.dto.response.ThongKeKhachHangMoiResponseDto;
import com.quoc.Movie_Ticket_Booking.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface KhachHangRepository extends JpaRepository<Users,Long> {

    // Thống ke tat cả số  lượng khach hang mới
    @Query("""
    SELECT DATE(u.createdAt), COUNT(u.id)
    FROM Users u
    GROUP BY DATE(u.createdAt)
    ORDER BY DATE(u.createdAt)
""")
    List<Object[]> getThongKeSoLuongKhachHangMoi();


    // Thống ke số  lượng khach hang mới  theo khoang ngày
    @Query("""
    SELECT DATE(u.createdAt), COUNT(u.id)
    FROM Users u
    WHERE u.createdAt BETWEEN :ngayBatDau AND :ngayKetThuc
    GROUP BY DATE(u.createdAt)
    ORDER BY DATE(u.createdAt)
""")
    List<Object[]> getThongKeSoluongKhachMoiTheoNgay(@Param("ngayBatDau") LocalDateTime ngayBatDau,
                                        @Param("ngayKetThuc") LocalDateTime ngayKetThuc);



}
