package com.quoc.Movie_Ticket_Booking.repository;

import com.quoc.Movie_Ticket_Booking.dto.response.ChiTietDichVuResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.DonHangDetailsResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ThongKeDichVuResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.VeDetailsResponseDto;
import com.quoc.Movie_Ticket_Booking.model.ChiTietDichVu;
import com.quoc.Movie_Ticket_Booking.model.DichVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ChiTietDichVuRepository extends JpaRepository<ChiTietDichVu,Long> {


    boolean existsByDichVuId(Long dichVuId);

    //Lấy dịch vụ bằng id đơn hàng
    @Query("SELECT new com.quoc.Movie_Ticket_Booking.dto.response.ChiTietDichVuResponseDto(ct.id,ct.donHang.id,d.tenDichVu,d.gia,ct.ghiChu) " +
            "FROM ChiTietDichVu ct " +
            "JOIN ct.dichVu d " +
            "WHERE ct.donHang.id = :id")
    public List<ChiTietDichVuResponseDto> getDichVuByIdDonHang(@Param("id") Long idDonHang);

    //Lấy dịch vụ bằng mã đơn hàng
    @Query("SELECT new com.quoc.Movie_Ticket_Booking.dto.response.ChiTietDichVuResponseDto(ct.id,ct.donHang.id,d.tenDichVu,d.gia,ct.ghiChu) " +
            "FROM ChiTietDichVu ct " +
            "JOIN ct.dichVu d " +
            "WHERE ct.donHang.maDonHang = :maDonHang")
    public List<ChiTietDichVuResponseDto> getDichVuByMaDonHang(@Param("maDonHang") String maDonHang);

//    public List<ChiTietDichVu> findByDonHangId(Long idDonHang);

    void deleteByDonHangId(Long donHangId);

    // Thống Kê Dịch Vụ Đã Bán
    @Query("""
    SELECT DATE(d.ngayDat),COUNT(d.id),SUM(CASE WHEN d.isThanhToan =1 THEN 1 ELSE 0 END)
    FROM ChiTietDichVu c
    JOIN DonHang d ON c.donHang.id = d.id
    GROUP BY DATE(d.ngayDat)
    ORDER BY DATE(d.ngayDat)
""")
    List<Object[]> getThongKeDichVuDaBan();


    // Thống Kê Dịch Vụ Đã Bán  theo khoang ngày
    @Query("""
    SELECT DATE(d.ngayDat),COUNT(c.id),SUM(CASE WHEN d.isThanhToan = 1 THEN 1 ELSE 0 END)
    FROM ChiTietDichVu c
    JOIN DonHang d ON c.donHang.id = d.id
    WHERE d.ngayDat BETWEEN :ngayBatDau AND :ngayKetThuc
    GROUP BY DATE(d.ngayDat)
    ORDER BY DATE(d.ngayDat)
""")
    List<Object[]> getThongKeDichVuDaBanTheoNgay(@Param("ngayBatDau") LocalDateTime ngayBatDau,
                                                   @Param("ngayKetThuc") LocalDateTime ngayKetThuc);
}
