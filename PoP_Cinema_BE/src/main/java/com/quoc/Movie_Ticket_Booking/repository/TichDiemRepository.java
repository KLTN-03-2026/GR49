package com.quoc.Movie_Ticket_Booking.repository;

import com.quoc.Movie_Ticket_Booking.model.TichDiem;
import com.quoc.Movie_Ticket_Booking.model.Ve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TichDiemRepository extends JpaRepository<TichDiem, Long> {

    Optional<TichDiem> findByDonHangId(Long idDonHang);

    List<TichDiem> findAllByOrderByCreatedAtDesc();

    List<TichDiem> findByKhachHangIdAndTinhTrangNotOrderByCreatedAtDesc(
            Long khachHangId,
            Integer tinhTrang
    );

    void deleteByDonHangId(Long donHangId);

    List<TichDiem> findByCreatedAtBetween(
            LocalDateTime from,
            LocalDateTime to
    );

    List<TichDiem> findByKhachHangIdAndCreatedAtBetween(
            Long khachHangId,
            LocalDateTime from,
            LocalDateTime to
    );

    List<TichDiem> findByTinhTrangAndCreatedAtBetween(
            Integer tinhTrang,
            LocalDateTime from,
            LocalDateTime to
    );

    List<TichDiem> findByKhachHangIdAndTinhTrangAndCreatedAtBetween(
            Long khachHangId,
            Integer tinhTrang,
            LocalDateTime from,
            LocalDateTime to
    );

}
