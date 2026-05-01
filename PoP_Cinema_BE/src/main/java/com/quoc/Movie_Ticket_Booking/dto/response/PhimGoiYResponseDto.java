package com.quoc.Movie_Ticket_Booking.dto.response;

public class PhimGoiYResponseDto {
    private Long id;
    private String tenPhim;
    private String theLoai;
    private Double diemTrungBinh; // thang 10

    public PhimGoiYResponseDto(Long id, String tenPhim, String theLoai, Double diemTrungBinh) {
        this.id = id;
        this.tenPhim = tenPhim;
        this.theLoai = theLoai;
        this.diemTrungBinh = diemTrungBinh;
    }

    public Long getId() {
        return id;
    }

    public String getTenPhim() {
        return tenPhim;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public Double getDiemTrungBinh() {
        return diemTrungBinh;
    }
}

