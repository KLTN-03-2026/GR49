package com.quoc.Movie_Ticket_Booking.repository;

import com.quoc.Movie_Ticket_Booking.dto.response.LoaiGheResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ThongKeLoaiVeResponseDto;
import com.quoc.Movie_Ticket_Booking.model.Ghe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GheRepository extends JpaRepository<Ghe,Long> {

    boolean existsByPhongChieuId(Long phongChieuId);

    boolean existsByTenGheAndPhongChieuId(String tenGhe, Long phongChieuId);

    int countByPhongChieuId(Long phongChieuId);

    public List<Ghe> findByPhongChieuIdAndTinhTrang(Long phongChieuId,Integer tinhTrang);

    Optional<Ghe> findFirstByPhongChieuId(Long idPhongChieu);

    public Page<Ghe> findAll(Pageable pageable);

    public Page<Ghe> findByPhongChieuId(Long phongChieuId,Pageable pageable);

    @Query("SELECT new com.quoc.Movie_Ticket_Booking.dto.response.LoaiGheResponseDto(g.loaiGhe, g.giaGhe) " +
            "FROM Ghe g GROUP BY g.loaiGhe, g.giaGhe")
    List<LoaiGheResponseDto> findDistinctLoaiGhe();


    // Thống ke So luong phim dang chieu
    @Query("""
    SELECT new com.quoc.Movie_Ticket_Booking.dto.response.ThongKeLoaiVeResponseDto(g.loaiGhe, COUNT(g.id))
    FROM Ghe g 
    WHERE g.tinhTrang = 1 
    GROUP BY g.loaiGhe
""")
    List<ThongKeLoaiVeResponseDto> getThongKeLoaiVe();
}
