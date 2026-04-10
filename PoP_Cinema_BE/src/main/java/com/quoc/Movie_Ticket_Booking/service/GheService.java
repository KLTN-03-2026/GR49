package com.quoc.Movie_Ticket_Booking.service;

import com.quoc.Movie_Ticket_Booking.dto.request.ChangStatusDto;
import com.quoc.Movie_Ticket_Booking.dto.request.GheRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.PhongChieuRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.GhePhanTrangResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.GheResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.LoaiGheResponseDto;
import com.quoc.Movie_Ticket_Booking.model.Ghe;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.PhongChieu;

import java.util.List;
import java.util.Map;

public interface GheService {

    public ApiResponse<?> createGhe(GheRequestDto gheRequestDto, NhanVien nhanVienId);

    public  ApiResponse<?> updateGhe(Long id, GheRequestDto updateDto,NhanVien nhanVienId);

    public ApiResponse<?> deleteGhe(Long id,NhanVien nhanVienId);

    public  ApiResponse<?> updateStatus(Long id,NhanVien nhanVienId);

    public List<GheResponseDto> getAllGhe();

    public Ghe getGheById(Long id);

    public GhePhanTrangResponseDto getAllGhePhanTrang(Integer pageNo,Integer pageSize);

    public GhePhanTrangResponseDto getGheByPhongChieuId(Long idPhongChieu,Integer pageNo,Integer pageSize);

    public Map<String, Object> getMapGheByPhongChieuId(Long idPhongChieu);

    public List<LoaiGheResponseDto> getDistinctLoaiGhe();

}
