package com.quoc.Movie_Ticket_Booking.service;

import com.quoc.Movie_Ticket_Booking.dto.request.ChangStatusDto;
import com.quoc.Movie_Ticket_Booking.dto.request.DatVeRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.model.DonHang;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DonHangService {

    public Map<String, Object> Payment(DatVeRequestDto datVeRequestDto,Long idUser);

    public ApiResponse<?> deleteDonHangById(Long idDonHang, NhanVien nhanVienId);

    public Map<String, Object> getByHoaDon(String maDonHang);

    public Map<String, Object> getByHoaDonForUser(String maDonHang,Long userById);

    public DonHang cancelOder(String maDonHang);

    public List<DonHang> getAllDonHang();

    public Map<String, Object> searchDonHang(String maDonHang);

    public ApiResponse<?> updateStatus(ChangStatusDto dto,NhanVien nhanVienId);

    public List<DonHang> filterDonHang(LocalDateTime ngayTruoc, LocalDateTime NgaySau);

    public DonHang getDonHangById(Long id);

    public Map<String, Object> getHistoryDonHangByUser(Long userById);

    public Long countLichSuDonHang(Long userId, int days);
}
