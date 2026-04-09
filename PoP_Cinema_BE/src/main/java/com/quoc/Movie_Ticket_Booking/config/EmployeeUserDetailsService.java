package com.quoc.Movie_Ticket_Booking.config;

import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.USER_ROLE;
import com.quoc.Movie_Ticket_Booking.model.Users;
import com.quoc.Movie_Ticket_Booking.repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeUserDetailsService implements UserDetailsService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NhanVien nhanVien = nhanVienRepository.findByEmail(username);

        if (nhanVien == null) {
            throw new UsernameNotFoundException("Nhan vien not found with email: " + username);
        }

        USER_ROLE role= nhanVien.getRole();

        List<GrantedAuthority> authorities=new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(role.toString()));

        return new org.springframework.security.core.userdetails.User(nhanVien.getEmail(), nhanVien.getPassword(), authorities);
    }

}
