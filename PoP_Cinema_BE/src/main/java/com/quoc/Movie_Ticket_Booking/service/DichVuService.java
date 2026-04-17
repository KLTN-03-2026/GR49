package com.quoc.Movie_Ticket_Booking.service;

import com.quoc.Movie_Ticket_Booking.dto.request.ChangStatusDto;
import com.quoc.Movie_Ticket_Booking.dto.request.DichVuRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.PhimRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.DichVuResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.PhimResponseDto;
import com.quoc.Movie_Ticket_Booking.model.DichVu;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.Phim;
import com.quoc.Movie_Ticket_Booking.model.Voucher;

import java.util.List;
import java.util.Map;

public interface DichVuService {
    public ApiResponse<?> createDichVu(DichVuRequestDto dichVuRequestDto, NhanVien nhanVienId);

    public ApiResponse<?> updateDichVu(Long id,DichVuRequestDto dichVuRequestDto,NhanVien nhanVienId);

    public ApiResponse<?> deleteDichVu(Long id,NhanVien nhanVienId);

    public ApiResponse<?> updateStatus(Long id,NhanVien nhanVienId);

    public List<DichVuResponseDto> getAllDichVu();

    public DichVu getDichVuById(Long id);

    public ApiResponse<?> getDataClientDichVu();

    public Map<String, Object> getDataChiTietDichVu(Long idDonHang);

    public List<DichVu> getDichVuByTinhTrang();
}
