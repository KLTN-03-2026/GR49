package com.quoc.Movie_Ticket_Booking.repository;

import com.quoc.Movie_Ticket_Booking.dto.response.DonHangDetailsResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.LichSuQuaVoucherResponseDto;
import com.quoc.Movie_Ticket_Booking.model.QuaVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QuaVoucherRepository extends JpaRepository<QuaVoucher,Long> {

    boolean existsByKhachHangIdAndVoucherId(Long khachHangId, Long voucherId);

    @Query("""
    SELECT q FROM QuaVoucher q
    JOIN FETCH q.voucher v
    WHERE q.maCode = :maCode
      AND q.khachHang.id = :userId
      AND q.tinhTrang = 0
      AND v.thoiGianBatDau <= :now
      AND v.thoiGianKetThuc >= :now
      AND v.tinhTrang = 1
""")
    Optional<QuaVoucher> findValidQuaVoucher(
            @Param("maCode") String maCode,
            @Param("userId") Long userId,
            @Param("now") LocalDate now
    );

    Optional<QuaVoucher> findByVoucherIdAndKhachHangId(
            Long voucherId,
            Long userId
    );

    Optional<QuaVoucher> findByMaCodeAndKhachHangId(
            String maCode,
            Long userId
    );


    //Hiển thị lịch sử quà voucher của khách hàng
    @Query("SELECT new com.quoc.Movie_Ticket_Booking.dto.response.LichSuQuaVoucherResponseDto(qv.voucher.hinhAnh,qv.voucher.tenVoucher,qv.voucher.moTa,qv.maCode,qv.ngayNhan,qv.voucher.thoiGianBatDau,qv.voucher.thoiGianKetThuc,qv.voucher.soTienGiamGia,qv.tinhTrang) " +
            "FROM QuaVoucher qv " +
            "JOIN  qv.voucher v" +
            " WHERE qv.khachHang.id = :idUser ")
    public List<LichSuQuaVoucherResponseDto> getLichSuQuaVoucher(@Param("idUser") Long idUser);

    boolean existsByVoucherId(Long voucherId);

}
