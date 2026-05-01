package com.quoc.Movie_Ticket_Booking.repository;

import com.quoc.Movie_Ticket_Booking.model.BaiViet;
import com.quoc.Movie_Ticket_Booking.model.Phim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaiVietRepository extends JpaRepository<BaiViet, Long> {

    public List<BaiViet> findByTinhTrangGreaterThan (int tinhTrang);
}
