package com.quoc.Movie_Ticket_Booking.repository;

import com.quoc.Movie_Ticket_Booking.model.DichVu;
import com.quoc.Movie_Ticket_Booking.model.Phim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DichVuRepository extends JpaRepository<DichVu,Long> {
    public Boolean existsByTenDichVu(String name);

    public List<DichVu> findByTinhTrangGreaterThan (int tinhTrang);
}
