package com.quoc.Movie_Ticket_Booking.repository;

import com.quoc.Movie_Ticket_Booking.dto.response.DanhGiaResponseDto;
import com.quoc.Movie_Ticket_Booking.model.DanhGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DanhGiaRepository extends JpaRepository<DanhGia,Long> {

    boolean existsByPhimIdAndKhachHangId(Long phimId, Long khachHangId);

    // Hiển thị danh gia

    @Query("""
     SELECT new com.quoc.Movie_Ticket_Booking.dto.response.DanhGiaResponseDto( AVG(d.soSao),COUNT(d.id))
    FROM DanhGia d
    WHERE d.phim.id = :idPhim 
""")
    List<DanhGiaResponseDto> getDanhGiaByIdPhim(@Param("idPhim") Long idPhim);


    @Query("SELECT AVG(d.soSao) FROM DanhGia d WHERE d.phim.id = :phimId")
    Double getDiemTrungBinh(@Param("phimId") Long phimId);


}
