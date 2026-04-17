package com.quoc.Movie_Ticket_Booking.repository;

import com.quoc.Movie_Ticket_Booking.dto.response.ThongKeVoucherResponseDto;
import com.quoc.Movie_Ticket_Booking.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher,Long> {
    public Boolean existsByMaCode(String maCode);

    @Query("SELECT v FROM Voucher v " +
            "WHERE v.maCode = :maCode " +
            "AND v.thoiGianBatDau <= :now " +
            "AND v.thoiGianKetThuc >= :now " +
            "AND v.tinhTrang = 1")
    Optional<Voucher> findValidVoucher(@Param("maCode") String maCode,
                                       @Param("now") LocalDate now);



    //Tìm tất cả voucher theo tình trạng
    List<Voucher> findByTinhTrang(Integer tinhTrang);


    // Thống ke voucher sử dụng
    @Query("""
    SELECT DATE(d.ngayDat),v.maCode,COUNT(d.id)
    FROM DonHang d
    JOIN Voucher v ON d.voucher.id = v.id
    WHERE d.isThanhToan = 1 AND d.voucher.id IS NOT NULL
    GROUP BY DATE(d.ngayDat), v.maCode
    ORDER BY DATE(d.ngayDat), v.maCode
""")
    List<Object[]> getThongKeVoucher();


    // // Thống kê voucher sử dụng  theo khoang ngày
    @Query("""
    SELECT DATE(d.ngayDat),v.maCode,COUNT(d.id)
    FROM DonHang d
    JOIN Voucher v ON d.voucher.id = v.id
    WHERE d.isThanhToan = 1 AND d.voucher.id IS NOT NULL 
    AND d.ngayDat BETWEEN :ngayBatDau AND :ngayKetThuc
    GROUP BY DATE(d.ngayDat), v.maCode
    ORDER BY DATE(d.ngayDat), v.maCode
""")
    List<Object[]> getThongKeVoucherSuDungTheoNgay(@Param("ngayBatDau") LocalDateTime ngayBatDau,
                                                   @Param("ngayKetThuc") LocalDateTime ngayKetThuc);

}
