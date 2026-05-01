package com.quoc.Movie_Ticket_Booking.repository;

import com.quoc.Movie_Ticket_Booking.dto.response.BinhLuanResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.DanhGiaResponseDto;
import com.quoc.Movie_Ticket_Booking.model.BinhLuan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BinhLuanRepository extends JpaRepository<BinhLuan, Long> {

// Hiển thị Binh luan

    @Query("""
     SELECT new com.quoc.Movie_Ticket_Booking.dto.response.BinhLuanResponseDto(u.id,u.hoVaTen,b.noiDung,b.createdAt,u.avatar)
    FROM BinhLuan b
    JOIN b.khachHang u
    WHERE b.phim.id = :idPhim 
""")
    List<BinhLuanResponseDto> getBinhLuanByIdPhim(@Param("idPhim") Long idPhim);
}
