package com.quoc.Movie_Ticket_Booking.service;

import com.quoc.Movie_Ticket_Booking.dto.request.SuatChieuRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.VeRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.*;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.SuatChieu;
import com.quoc.Movie_Ticket_Booking.model.Ve;
import com.quoc.Movie_Ticket_Booking.model.Voucher;

import java.util.List;
import java.util.Map;

public interface VeService {

    public Ve createVe(VeRequestDto veRequestDto);

    public ApiResponse<?>  updateVe(Long id, VeRequestDto updateDto, NhanVien nhanVienId);

    public ApiResponse<?> updateStatus(Long id , NhanVien nhanVienId);

    public ApiResponse<?> deleteVe(Long id, NhanVien nhanVienId);

    public ApiResponse<?> destroyVe(Long id,NhanVien nhanVienId);

    public Ve getVeById(Long id);

    public List<VeResponseDto> getAllVe();

    public VePhanTrangResponseDto getAllVePhanTrang(Integer pageNo, Integer pageSize);

    public Map<String, Object> getClientAllVe(Long idSuatChieu);

    public Map<String, Object> searchVe(String maVe);

    public ApiResponse<?> searchVeByPhanTrang(String maVe,Integer pageNo, Integer pageSize);

    public List<VeResponseDto> getAllVeChuaThanhToan();
}
