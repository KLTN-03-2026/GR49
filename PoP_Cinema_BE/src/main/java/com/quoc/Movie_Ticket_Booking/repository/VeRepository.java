package com.quoc.Movie_Ticket_Booking.repository;

import com.quoc.Movie_Ticket_Booking.dto.response.AllVeResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ThongTinPhimResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.VeResponseDto;
import com.quoc.Movie_Ticket_Booking.model.DonHang;
import com.quoc.Movie_Ticket_Booking.model.Ve;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public  interface  VeRepository extends JpaRepository<Ve,Long> {

    boolean existsByGheId(Long gheId);

    boolean existsBySuatChieuId(Long suatChieuId);

    List<Ve> findBySuatChieuId(Long idSuatChieu);


//  Vi Du:  @Query("SELECT v FROM Ve v WHERE v.suatChieu.id = :suatChieuId ORDER BY v.id ASC")
    Optional<Ve> findFirstBySuatChieuId(Long idSuatChieu);

    @Query("SELECT new com.quoc.Movie_Ticket_Booking.dto.response.VeResponseDto(v.id, v.maVe, v.donHang.id,dh.khachHang.hoVaTen,p.tenPhim, sc.id,sc.ngayChieu, sc.thoiGianBatDau,sc.thoiGianKetThuc,v.tenGhe,v.giaVe,dh.ngayDat,v.tinhTrang) " +
            "FROM Ve v " +
            "JOIN v.suatChieu sc " +
            "JOIN sc.phim p "+
            "LEFT JOIN v.donHang dh "+
            "LEFT JOIN dh.khachHang u ")
    public List<VeResponseDto> getAllVeWithDetails();


    @Query("SELECT new com.quoc.Movie_Ticket_Booking.dto.response.VeResponseDto(v.id, v.maVe, v.donHang.id,dh.khachHang.hoVaTen,p.tenPhim, sc.id,sc.ngayChieu, sc.thoiGianBatDau,sc.thoiGianKetThuc,v.tenGhe,v.giaVe,dh.ngayDat,v.tinhTrang) " +
            "FROM Ve v " +
            "JOIN v.suatChieu sc " +
            "JOIN sc.phim p "+
            "LEFT JOIN v.donHang dh "+
            "LEFT JOIN dh.khachHang u ")
    public Page<VeResponseDto> getAllVePaginationWithDetails(Pageable pageable);


    @Query("SELECT v FROM Ve v WHERE v.id IN :ids AND v.donHang.id IS NULL")
    List<Ve> findByIdInAndIdDonHangs(@Param("ids") List<Long> ids);


    //Hiển thị thông tin sơ đồ ghế (vé) theo suất chiếu
    @Query("SELECT new com.quoc.Movie_Ticket_Booking.dto.response.AllVeResponseDto(v.id, v.maVe, v.giaVe,v.donHang.id, v.suatChieu.id,v.tenGhe,v.tinhTrang ,v.ghe.loaiGhe) " +
            "FROM Ve v " +
            "JOIN v.suatChieu sc " +
            "LEFT JOIN v.donHang dh " +
            "WHERE sc.id = :id")
    public List<AllVeResponseDto> getDatVeBySuatChieuId(@Param("id") Long idsuatchieu);

    public List<Ve> findByDonHangId(Long idDonHang);


    @Query("SELECT new com.quoc.Movie_Ticket_Booking.dto.response.VeResponseDto(v.id, v.maVe, v.donHang.id,dh.khachHang.hoVaTen,p.tenPhim,sc.id, sc.ngayChieu, sc.thoiGianBatDau,sc.thoiGianKetThuc,v.tenGhe,v.giaVe,dh.ngayDat,v.tinhTrang) " +
            "FROM Ve v " +
            "JOIN  SuatChieu sc ON sc.id = v.suatChieu.id " +
            "JOIN Phim p ON p.id = v.suatChieu.phim.id "+
            "LEFT JOIN DonHang dh ON dh.id = v.donHang.id "+
            "LEFT JOIN Users u ON u.id = dh.khachHang.id "+
            "WHERE (lower(v.maVe) like lower(concat('%',:keyword,'%') )) ")
    public List<VeResponseDto> searchVeByMaVe(String keyword);

    @Query("SELECT new com.quoc.Movie_Ticket_Booking.dto.response.VeResponseDto(v.id, v.maVe, v.donHang.id,dh.khachHang.hoVaTen,p.tenPhim,sc.id, sc.ngayChieu, sc.thoiGianBatDau,sc.thoiGianKetThuc,v.tenGhe,v.giaVe,dh.ngayDat,v.tinhTrang) " +
            "FROM Ve v " +
            "JOIN v.suatChieu sc " +
            "JOIN sc.phim p "+
            "LEFT JOIN v.donHang dh "+
            "LEFT JOIN dh.khachHang u "+
            "WHERE (lower(v.maVe) like lower(concat('%',:keyword,'%') )) ")
    public Page<VeResponseDto> searchVeByMaVePhanTrang(String keyword,Pageable pageable);


    //tìm thời gian hết hạn quá 5p
    @Query("""
    SELECT v FROM Ve v 
     JOIN v.donHang dh 
    WHERE (v.tinhTrang = 1 OR v.tinhTrang = 0)
      AND dh.ngayDat < :time 
      AND v.donHang IS NOT NULL
""")
    List<Ve> findExpiredVe(@Param("time") LocalDateTime time);


    @Query("SELECT new com.quoc.Movie_Ticket_Booking.dto.response.VeResponseDto(v.id, v.maVe, v.donHang.id,dh.khachHang.hoVaTen,p.tenPhim, sc.id,sc.ngayChieu, sc.thoiGianBatDau,sc.thoiGianKetThuc,v.tenGhe,v.giaVe,dh.ngayDat,v.tinhTrang) " +
            "FROM Ve v " +
            "JOIN v.suatChieu sc " +
            "JOIN sc.phim p "+
            "LEFT JOIN v.donHang dh "+
            "LEFT JOIN dh.khachHang u "+
            "WHERE v.tinhTrang = 1")
    public List<VeResponseDto> getAllVeChuaThanhToan();

}
