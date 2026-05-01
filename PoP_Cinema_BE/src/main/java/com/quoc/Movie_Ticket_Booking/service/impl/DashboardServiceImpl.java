package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.dto.response.*;
import com.quoc.Movie_Ticket_Booking.repository.*;
import com.quoc.Movie_Ticket_Booking.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.*;
import java.util.*;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private ThongKeRepository thongKeRepository;

    @Autowired
    private ThongKeVeRepository thongKeVeRepository;

    @Autowired
    private PhongChieuRepository phongChieuRepository;

    @Autowired
    private PhimRepository phimRepository;

    @Autowired
    private GheRepository gheRepository;


    @Override
    public Map<String, Object> getTongDoanhThu() {
        Map<String,Object> response = new HashMap<>();
        Double tongDoanhThu = thongKeRepository.getTongDoanhThu();

        response.put("status", true);
        response.put("tong_doanh_thu", tongDoanhThu);
        return response;
    }

    @Override
    public Map<String, Object> getTongPhim() {
        Map<String,Object> response = new HashMap<>();
        Long tongPhim = phimRepository.getTongPhim();

        response.put("status", true);
        response.put("tong_phim", tongPhim);
        return response;
    }

    @Override
    public Map<String, Object> getTongVeDaBan() {
        Map<String,Object> response = new HashMap<>();
        Long tongVeDaBan = thongKeVeRepository.getTongVeDaBan();

        response.put("status", true);
        response.put("tong_ve_da_ban", tongVeDaBan);
        return response;
    }

    @Override
    public Map<String, Object> getTongPhongChieu() {
        Map<String,Object> response = new HashMap<>();
        Long tongPhongChieu = phongChieuRepository.getTongPhongChieu();

        response.put("status", true);
        response.put("tong_phong_chieu", tongPhongChieu);
        return response;
    }

    @Override
    public Map<String, Object> getDatVeGanDay() {
        Map<String,Object> response = new HashMap<>();
        LocalDate today = LocalDate.now();

        // Lấy thứ trong tuần (Thứ 2 = MONDAY, Chủ nhật = SUNDAY)
        LocalDate startOfWeek = today.with(java.time.temporal.TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = today.with(java.time.temporal.TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

//
        LocalDateTime start = startOfWeek.atStartOfDay();
        LocalDateTime end = endOfWeek.atTime(LocalTime.MAX);
        try {
            List<Object[]> thongKeVeDatGanDay = thongKeVeRepository.getThongKeVeDatGanDay(start, end);

            List<DatVeGanDayResponseDto> data = thongKeVeDatGanDay.stream()
                    .map(this::mapDatVeGanDayDTO)
                    .toList();

            response.put("status", true);
            response.put("data", data);
        } catch (Exception e) {
            response.put("status", false);
            response.put("message", e.getMessage());
        }
        return response;
    }

    @Override
    public Map<String, Object> getPhimPhoBien() {
        Map<String,Object> response = new HashMap<>();
        List<PhimPhoBienResponseDto> phimPhoBien = thongKeVeRepository.getPhimPhoBien();
        response.put("status", true);
        response.put("get_phim_pho_bien", phimPhoBien);
        return response;
    }

    @Override
    public Map<String, Object> getThongKeLoaiVe() {
        Map<String,Object> response = new HashMap<>();
        List<ThongKeLoaiVeResponseDto> thongKeLoaiVe=gheRepository.getThongKeLoaiVe();

        // List Loại vé
        List<String> listLoaiVe = new ArrayList<>();
        for (ThongKeLoaiVeResponseDto item : thongKeLoaiVe) {
            listLoaiVe.add(item.getLoaiVe());
        }

        //List phim dang chieu
        List<Long> listSoLuongVe = new ArrayList<>();
        for (ThongKeLoaiVeResponseDto item : thongKeLoaiVe) {
            listSoLuongVe.add(item.getSoVe());
        }

        response.put("status", true);
        response.put("data", thongKeLoaiVe);
        response.put("list_loai_ve", listLoaiVe);
        response.put("list_so_luong_ve",listSoLuongVe);
        return response;
    }

    private DatVeGanDayResponseDto mapDatVeGanDayDTO(Object[] row) {

        return new DatVeGanDayResponseDto(
                Objects.toString(row[0], ""),              // tenKhach
                Objects.toString(row[1], ""),              // tenPhim
                row[2] == null ? 0L : ((Number) row[2]).longValue(),  // soVe
                row[3] == null ? 0.0 : ((Number) row[3]).doubleValue(), // tongTien
                row[4] instanceof Date d ? d.toLocalDate() : (LocalDate) row[4]
        );

    }

}
