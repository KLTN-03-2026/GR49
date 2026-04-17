package com.quoc.Movie_Ticket_Booking.service;

import com.quoc.Movie_Ticket_Booking.dto.request.DichVuRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.VoucherRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.*;
import com.quoc.Movie_Ticket_Booking.model.DichVu;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.Voucher;

import java.util.List;
import java.util.Map;

public interface VoucherService {

    public ApiResponse<?> createVoucher(VoucherRequestDto voucherRequestDto, NhanVien nhanVienId);

    public ApiResponse<?> updateVoucher(Long id,VoucherRequestDto updateDto,NhanVien nhanVienId);

    public ApiResponse<?> deleteVoucher(Long id,NhanVien nhanVienId);

    public ApiResponse<?> updateStatus(Long id,NhanVien nhanVienId);

    public List<VoucherResponseDto> getAllVoucher();

    public Voucher getVoucherById(Long id);

    public ApiResponse<?> findVoucherByMaCode(String maCode);

    public List<Voucher> getVoucherByTinhTrang();

    public ApiResponse<?> doiQuaVoucher(Long voucherId, Long userId);

    public ApiResponse<?> findQuaVoucherByMaCode(String maCode,Long userId);

    public List<QuaVoucherResponseDto> getAllQuaVoucher();

    public ApiResponse<?> getLichSuQuaVoucher(Long userId);
}
