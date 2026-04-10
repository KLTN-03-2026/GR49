package com.quoc.Movie_Ticket_Booking.repository;

import com.quoc.Movie_Ticket_Booking.model.Ghe;
import com.quoc.Movie_Ticket_Booking.model.PhongChieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PhongChieuRepository  extends JpaRepository<PhongChieu,Long> {
    public Boolean existsByTenPhong(String name);

    @Query("SELECT COUNT(p) FROM PhongChieu p ")
    public Long getTongPhongChieu();
}
