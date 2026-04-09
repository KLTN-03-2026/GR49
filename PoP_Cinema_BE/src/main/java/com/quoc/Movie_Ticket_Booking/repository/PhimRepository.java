package com.quoc.Movie_Ticket_Booking.repository;

import com.quoc.Movie_Ticket_Booking.dto.response.ThongKePhimResponseDto;
import com.quoc.Movie_Ticket_Booking.model.Phim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PhimRepository extends JpaRepository<Phim, Long> {

    public Boolean existsByTenPhim(String name);

    public List<Phim> findByTinhTrangGreaterThan (int tinhTrang);

    List<Phim> findByTinhTrang(int trangThai);

    // Lấy 4 phim mới nhất theo ngày phát hành
    List<Phim> findTop4ByTinhTrangOrderByNgayPhatHanhDesc(Integer tinhTrang);


    // Lấy phim mới nhất theo tình trạng đang chiếu và ngày phát hành
    @Query("SELECT p FROM Phim p WHERE p.tinhTrang = :status ORDER BY p.ngayPhatHanh DESC")
    List<Phim> findByTinhTrangInOrderByNgayPhatHanhDesc(@Param("status") Integer status);

    // Lấy 5 phim mới nhất theo thể loại , tình trạng đang chiếu và ngày phát hành
    List<Phim> findTop5ByTheLoaiAndTinhTrangOrderByNgayPhatHanhDesc(String theLoai, Integer status);


    // Thống ke So luong phim dang chieu
    @Query("""
    SELECT new com.quoc.Movie_Ticket_Booking.dto.response.ThongKePhimResponseDto(s.ngayChieu, COUNT(DISTINCT p.id))
    FROM SuatChieu s 
    JOIN Phim p ON s.phim.id = p.id
    WHERE p.tinhTrang = 2 
    GROUP BY s.ngayChieu
    ORDER BY s.ngayChieu
""")
    List<ThongKePhimResponseDto> getThongKePhimDangChieu();


    // // Thống ke So luong phim dang chieu theo khoang ngày
    @Query("""
    SELECT new com.quoc.Movie_Ticket_Booking.dto.response.ThongKePhimResponseDto(s.ngayChieu, COUNT(DISTINCT p.id))
    FROM SuatChieu s 
    JOIN Phim p ON s.phim.id = p.id
    WHERE p.tinhTrang = 2 AND s.ngayChieu BETWEEN :ngayBatDau AND :ngayKetThuc
    GROUP BY s.ngayChieu
    ORDER BY s.ngayChieu
""")
    List<ThongKePhimResponseDto> getThongKePhimDangChieuTheoNgay(@Param("ngayBatDau") LocalDate ngayBatDau,
                                               @Param("ngayKetThuc") LocalDate ngayKetThuc);


    @Query("SELECT COUNT(p) FROM Phim p ")
    public Long getTongPhim();
}
