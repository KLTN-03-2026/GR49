package com.quoc.Movie_Ticket_Booking.repository;

import com.quoc.Movie_Ticket_Booking.model.HangThanhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HangThanhVienRepository extends JpaRepository<HangThanhVien,Long> {

    @Query("""
        SELECT h FROM HangThanhVien h
        WHERE h.tinhTrang = 1
          AND :tongDiem >= h.diemToiThieu
        ORDER BY h.diemToiThieu DESC
    """)
    List<HangThanhVien> findHangByTongDiem(@Param("tongDiem") int tongDiem);

    // Lấy danh sách hạng, sắp xếp theo điểm tăng dần
    List<HangThanhVien> findAllByOrderByDiemToiThieuAsc();
}
