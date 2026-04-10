package com.quoc.Movie_Ticket_Booking.repository;

import com.quoc.Movie_Ticket_Booking.dto.response.*;
import com.quoc.Movie_Ticket_Booking.model.DonHang;
import com.quoc.Movie_Ticket_Booking.model.Ve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DonHangRepository extends JpaRepository<DonHang, Long> {


    boolean existsByVoucherId(Long voucherId);

    public DonHang findByMaDonHang(String maDonHang);

//    Optional<DonHang> findByIdAndIsThanhToan(Long idDonHang, Integer tinhTrang);


    Optional<DonHang> findByIdAndIsThanhToanIn(Long idDonHang, List<Integer> isThanhToan);

    //Hiển thị đơn hàng theo mã đơn hàng cho admin
    @Query("SELECT DISTINCT new com.quoc.Movie_Ticket_Booking.dto.response.DonHangResponseDto(dh.maDonHang,dh.ngayDat, p.tenPhim, p.theLoai,sc.ngayChieu, sc.thoiGianBatDau,sc.thoiGianKetThuc , sc.phongChieu.tenPhong,dh.tongTien,dh.tienThucNhan,dh.giamGia) " +
            "FROM DonHang dh " +
            "JOIN  Ve v ON v.donHang.id = dh.id " +
            "JOIN  SuatChieu sc ON sc.id = v.suatChieu.id " +
            "JOIN  Phim p ON p.id = sc.phim.id " +
            "WHERE dh.maDonHang = :maDonHang")
    public List<DonHangResponseDto> getDonHangByMaDonHang(@Param("maDonHang") String maDonHang);


    //Hiển thị vé theo mã đơn hàng cho admin
    @Query("SELECT new com.quoc.Movie_Ticket_Booking.dto.response.AllVeResponseDto(v.id,v.maVe, v.giaVe,v.donHang.id , v.suatChieu.id,v.tenGhe,v.tinhTrang ,v.ghe.loaiGhe ) " +
            "FROM Ve v " +
            "WHERE v.donHang.maDonHang = :maDonHang")
    public List<AllVeResponseDto> getVeByMaDonHang(@Param("maDonHang") String maDonHang);


    //Hiển thị đơn hàng theo cho khách hàng
    @Query("SELECT DISTINCT  new com.quoc.Movie_Ticket_Booking.dto.response.DonHangDetailsResponseDto(dh.id,dh.maDonHang,dh.ngayDat,dh.tongTien,dh.tienThucNhan,dh.giamGia,dh.isThanhToan, p.tenPhim, p.hinhAnh,p.theLoai,p.thoiLuong,p.noiDung,u.hoVaTen,u.email,u.soDienThoai) " +
            "FROM DonHang dh " +
            "JOIN  Ve v ON v.donHang.id = dh.id " +
            "JOIN  SuatChieu sc ON sc.id = v.suatChieu.id " +
            "JOIN  Phim p ON p.id = sc.phim.id " +
            "JOIN  Users u ON u.id = dh.khachHang.id " +
            "WHERE dh.maDonHang = :maDonHang AND dh.khachHang.id = :idUser")
    public List<DonHangDetailsResponseDto> getDonHangForUser(@Param("maDonHang") String maDonHang, @Param("idUser") Long idUser);


    //Hiển thị vé cho khách hàng theo mã đơn hàng
    @Query("SELECT  new com.quoc.Movie_Ticket_Booking.dto.response.VeDetailsResponseDto(v.maVe,p.tenPhim,sc.ngayChieu,sc.thoiGianBatDau,sc.thoiGianKetThuc,v.tenGhe,v.giaVe,v.donHang.id) " +
            "FROM Ve v " +
            "JOIN v.suatChieu sc " +
            "JOIN sc.phim p " +
            "WHERE v.donHang.maDonHang = :maDonHang")
    public List<VeDetailsResponseDto> getVeByMaDonHangForUser(@Param("maDonHang") String maDonHang);


    //hHiển thị dịch vụ cho khách hàng theo mã đơn hàng
    @Query("SELECT new com.quoc.Movie_Ticket_Booking.dto.response.ChiTietDichVuResponseDto(c.id,c.donHang.id,d.tenDichVu,d.gia,c.ghiChu) " +
            "FROM ChiTietDichVu c " +
            "JOIN c.dichVu d " +
            "WHERE c.donHang.maDonHang = :maDonHang")
    public List<ChiTietDichVuResponseDto> getDichVuForUserByMaDonHang(@Param("maDonHang") String maDonHang);



    //Hiển thị vé theo id đơn hàng
    @Query("SELECT new com.quoc.Movie_Ticket_Booking.dto.response.VeDetailsResponseDto(v.maVe,p.tenPhim,sc.ngayChieu,sc.thoiGianBatDau,sc.thoiGianKetThuc,v.tenGhe,v.giaVe,v.donHang.id) " +
            "FROM Ve v " +
            "JOIN v.suatChieu sc " +
            "JOIN sc.phim p " +
            "WHERE v.donHang.id = :id")
    public List<VeDetailsResponseDto> getVeByIdDonHang(@Param("id") Long idDonHang);


    //Tìm kiếm đơn hàng
    @Query("select d from DonHang d where (lower(d.maDonHang) like lower(concat('%',:keyword,'%') )) ")
    public List<DonHang> searchDonHangByMaDonHang(String keyword);


    //Lọc dơn hàng theo ngày
    @Query("SELECT d FROM DonHang d WHERE d.ngayDat BETWEEN :ngayBatDau AND :ngayKetThuc")
    List<DonHang> filterDonHangByNgayDat(
            @Param("ngayBatDau") LocalDateTime ngayBatDau,
            @Param("ngayKetThuc") LocalDateTime ngayKetThuc
    );



    //Hiển thị đơn hàng đến email khi khách đặt
    @Query("SELECT DISTINCT  new com.quoc.Movie_Ticket_Booking.dto.response.DonHangDetailsResponseDto(dh.id,dh.maDonHang,dh.ngayDat,dh.tongTien,dh.tienThucNhan,dh.giamGia,dh.isThanhToan, p.tenPhim, p.hinhAnh,p.theLoai,p.thoiLuong,p.noiDung,u.hoVaTen,u.email,u.soDienThoai) " +
            "FROM DonHang dh " +
            "JOIN  Ve v ON v.donHang.id = dh.id " +
            "JOIN  SuatChieu sc ON sc.id = v.suatChieu.id " +
            "JOIN  Phim p ON p.id = sc.phim.id " +
            "JOIN  Users u ON u.id = dh.khachHang.id " +
            "WHERE dh.maDonHang = :maDonHang")
    public DonHangDetailsResponseDto getDonHangToEmail(@Param("maDonHang") String maDonHang);


    //Hiển thị lịch sử đơn đặt hàng của khách hàng
    @Query("SELECT DISTINCT  new com.quoc.Movie_Ticket_Booking.dto.response.DonHangDetailsResponseDto(dh.id,dh.maDonHang,dh.ngayDat,dh.tongTien,dh.tienThucNhan,dh.giamGia,dh.isThanhToan, p.tenPhim, p.hinhAnh,p.theLoai,p.thoiLuong,p.noiDung,u.hoVaTen,u.email,u.soDienThoai) " +
            "FROM DonHang dh " +
            "JOIN  Ve v ON v.donHang.id = dh.id " +
            "JOIN  SuatChieu sc ON sc.id = v.suatChieu.id " +
            "JOIN  Phim p ON p.id = sc.phim.id " +
            "JOIN  Users u ON u.id = dh.khachHang.id " +
            "WHERE dh.khachHang.id = :idUser AND dh.isThanhToan = :tinhTrang ")
    public List<DonHangDetailsResponseDto> getHistoryDonHang( @Param("idUser") Long idUser ,@Param("tinhTrang") Integer tinhTrang);


    //Hiển thị tát cả vé  đặt theo đơn hàng
    @Query("SELECT new com.quoc.Movie_Ticket_Booking.dto.response.VeDetailsResponseDto(v.maVe,p.tenPhim,sc.ngayChieu,sc.thoiGianBatDau,sc.thoiGianKetThuc,v.tenGhe,v.giaVe,v.donHang.id) " +
            "FROM Ve v " +
            "JOIN v.suatChieu sc " +
            "JOIN sc.phim p " +
            "WHERE v.donHang.id IN :id")
    public List<VeDetailsResponseDto> getVeByListIdDonHang(@Param("id") List<Long> idDonHang);



    // Đếm số lịch sử đơn hàng của khách hàng
    @Query("""
    SELECT COUNT(d)
    FROM DonHang d
    WHERE d.khachHang.id = :userId
     AND d.ngayDat >= :fromDate
""")
    Long countLichSuDonHang(@Param("userId") Long userId,  @Param("fromDate") LocalDateTime fromDate);


}
