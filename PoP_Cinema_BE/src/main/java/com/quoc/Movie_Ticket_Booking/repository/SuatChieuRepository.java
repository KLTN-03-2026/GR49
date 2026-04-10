package com.quoc.Movie_Ticket_Booking.repository;

import com.quoc.Movie_Ticket_Booking.dto.response.SuatChieuResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ThongKeSuatChieuResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ThongTinPhimResponseDto;
import com.quoc.Movie_Ticket_Booking.model.SuatChieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface SuatChieuRepository extends JpaRepository<SuatChieu, Long> {

   boolean existsByPhimId(Long phimId);
   boolean existsByPhongChieuId(Long phongChieuId);

   public List<SuatChieu> findByPhimIdAndTinhTrang(Long id,Integer tinhTrang);


   //Kiểm tra trùng suất chiếu
   @Query("""
        SELECT s FROM SuatChieu s
        WHERE s.phongChieu.id = :idPhongChieu
          AND s.ngayChieu = :ngayChieu
          AND (
                (:thoiGianBatDau < s.thoiGianKetThuc)
            AND (:thoiGianKetThuc > s.thoiGianBatDau)
          )
    """)
   List<SuatChieu> checkTrungSuatChieu(
           @Param("idPhongChieu") Long idPhongChieu,
           @Param("ngayChieu") LocalDate ngayChieu,
           @Param("thoiGianBatDau") LocalTime thoiGianBatDau,
           @Param("thoiGianKetThuc") LocalTime thoiGianKetThuc
   );

   //Lấy chi tiết tất cả của suất chiếu
   @Query("SELECT new com.quoc.Movie_Ticket_Booking.dto.response.SuatChieuResponseDto(sc.id, sc.phim.id,p.tenPhim, sc.phongChieu.id, pc.tenPhong,sc.ngayChieu, sc.thoiGianBatDau,sc.thoiGianKetThuc,sc.tinhTrang) " +
           "FROM SuatChieu sc " +
           "JOIN sc.phim p " +
           "JOIN sc.phongChieu pc")
   public List<SuatChieuResponseDto> getAllSuatChieuWithDetails();



   //Lấy thông tin phim và suất chiếu
   @Query("SELECT new com.quoc.Movie_Ticket_Booking.dto.response.ThongTinPhimResponseDto(p.tenPhim, p.ngonNgu ,p.hinhAnh ,sc.ngayChieu, sc.thoiGianBatDau,sc.thoiGianKetThuc ) " +
           "FROM SuatChieu sc " +
           "JOIN sc.phim p " +
           "WHERE sc.id = :id")
   public ThongTinPhimResponseDto getThongTinPhimWithSuatChieu(@Param("id") Long id);


   // Thống ke tat ca suat chieu
   @Query("""
    SELECT new com.quoc.Movie_Ticket_Booking.dto.response.ThongKeSuatChieuResponseDto(s.ngayChieu, COUNT(s.id))
    FROM SuatChieu s 
    GROUP BY s.ngayChieu
    ORDER BY s.ngayChieu
""")
   List<ThongKeSuatChieuResponseDto> getThongKeSuatChieu();


   // Thống ke số lượng suất chiếu theo khoang ngày
   @Query("""
     SELECT new com.quoc.Movie_Ticket_Booking.dto.response.ThongKeSuatChieuResponseDto(s.ngayChieu,COUNT(s.id))
    FROM SuatChieu s 
    WHERE s.ngayChieu BETWEEN :ngayBatDau AND :ngayKetThuc
    GROUP BY s.ngayChieu
    ORDER BY s.ngayChieu
""")
   List<ThongKeSuatChieuResponseDto> getThongKeSoLuongSuatChieuTheoNgay(@Param("ngayBatDau") LocalDate ngayBatDau,
                                       @Param("ngayKetThuc") LocalDate ngayKetThuc);



}
