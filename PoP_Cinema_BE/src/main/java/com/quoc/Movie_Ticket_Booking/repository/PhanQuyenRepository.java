package com.quoc.Movie_Ticket_Booking.repository;

import com.quoc.Movie_Ticket_Booking.dto.request.PhanQuyenRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.PhanQuyenResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.VeResponseDto;
import com.quoc.Movie_Ticket_Booking.model.ChucNang;
import com.quoc.Movie_Ticket_Booking.model.PhanQuyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PhanQuyenRepository extends JpaRepository<PhanQuyen,Long> {


    boolean existsByChucVuId(Long chucVuId);

    public Optional<PhanQuyen> findByChucNangIdAndChucVuId(Long chucNangId, Long chucVuId);

    @Query("SELECT new com.quoc.Movie_Ticket_Booking.dto.response.PhanQuyenResponseDto(p.id,cv.id,cv.tenChucVu,cn.id,cn.tenChucNang) " +
            "FROM PhanQuyen p " +
            "JOIN p.chucVu cv " +
            "JOIN p.chucNang cn "+
             "WHERE cv.id= :idChucVu ")
    public List<PhanQuyenResponseDto> getAllPhanQuyenByChucVuId(@Param("idChucVu") Long idChucVu);

    void deleteByChucVuIdAndChucNangId(Long idChucVu, Long idChucNang);
}
