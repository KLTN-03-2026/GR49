package com.quoc.Movie_Ticket_Booking.service;

import com.quoc.Movie_Ticket_Booking.dto.request.BaiVietRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.PhanQuyenRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.model.BaiViet;
import com.quoc.Movie_Ticket_Booking.model.ChucVu;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;

import java.util.Map;

public interface BaiVietService {

    public ApiResponse<?> createBaiViet (BaiVietRequestDto baiVietRequestDto,NhanVien nhanVienId);

    public  ApiResponse<?> updateBaiViet (Long id,BaiVietRequestDto baiVietRequestDto,NhanVien nhanVienId);

    public  ApiResponse<?> updateStatus(Long id,NhanVien nhanVienId);

    public  ApiResponse<?> deleteBaiViet(Long baiVietId,NhanVien nhanVienId);

    public  ApiResponse<?> getAllBaiViet();

    public BaiViet getBaiVietId(Long id);

    public ApiResponse<?> getDataClientBaiViet();

    public ApiResponse<?> getChiTietBaiViet(Long id);
}
