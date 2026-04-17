package com.quoc.Movie_Ticket_Booking.repository;

import com.quoc.Movie_Ticket_Booking.model.ChucNang;
import com.quoc.Movie_Ticket_Booking.model.ChucVu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChucVuRepository extends JpaRepository<ChucVu,Long> {
}
