package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.repository.PhanQuyenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    @Autowired
    private PhanQuyenRepository phanQuyenRepository;

    public boolean checkQuyen(long idChucNang, long idChucVu) {
        return phanQuyenRepository.findByChucNangIdAndChucVuId(idChucNang, idChucVu).isPresent();
    }
}
