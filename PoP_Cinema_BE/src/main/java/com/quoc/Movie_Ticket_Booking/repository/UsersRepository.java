package com.quoc.Movie_Ticket_Booking.repository;

import com.quoc.Movie_Ticket_Booking.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {


    public Users findByEmail(String username);

    public Users findByHashActive(String maKichHoat);

    public Users findByHashReset(String maReset);
}
