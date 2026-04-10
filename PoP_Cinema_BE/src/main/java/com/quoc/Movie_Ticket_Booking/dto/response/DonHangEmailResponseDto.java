package com.quoc.Movie_Ticket_Booking.dto.response;

import com.quoc.Movie_Ticket_Booking.model.DonHang;

import java.time.LocalDateTime;

public class DonHangEmailResponseDto {
    private String maDonHang;
    private String tenPhim;            // 1 phim / nhiều vé cùng phim
    private LocalDateTime ngayDat;

    public DonHangEmailResponseDto(DonHang donHang) {
        this.maDonHang = donHang.getMaDonHang();
    }

    public String getMaDonHang() {
        return maDonHang;
    }

    public String getTenPhim() {
        return tenPhim;
    }

    public LocalDateTime getNgayDat() {
        return ngayDat;
    }

    public void setMaDonHang(String maDonHang) {
        this.maDonHang = maDonHang;
    }

    public void setTenPhim(String tenPhim) {
        this.tenPhim = tenPhim;
    }

    public void setNgayDat(LocalDateTime ngayDat) {
        this.ngayDat = ngayDat;
    }
}
