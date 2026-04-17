package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.dto.response.*;
import com.quoc.Movie_Ticket_Booking.model.DonHang;
import com.quoc.Movie_Ticket_Booking.repository.*;
import com.quoc.Movie_Ticket_Booking.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ThongKeServiceImpl implements ThongKeService {

    @Autowired
    private ThongKeRepository thongKeRepository;

    @Autowired
    private ThongKeVeRepository thongKeVeRepository;

    @Autowired
    private SuatChieuRepository suatChieuRepository;

    @Autowired
    private PhimRepository phimRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private ChiTietDichVuRepository chiTietDichVuRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;




    @Override
    public Map<String, Object> getAllDoanhThu() {
        Map<String,Object> response = new HashMap<>();
        List<Object[]> raw = thongKeRepository.getThongKeDoanhThu();

        List<ThongKeDonHangResponseDto> data = raw.stream()
                .map(this::mapToDTO)
                .toList();

        response.put("status", true);
        response.put("data", data);
        return response;
    }

    @Override
    public Map<String, Object> getDoanhThuTheoNgay(String ngayBatDau, String ngayKetThuc) {
        Map<String,Object> response = new HashMap<>();
        List<Object[]> doanhThu;
        if (ngayBatDau != null && ngayKetThuc != null && !ngayKetThuc.isEmpty()) {

            // parse String -> LocalDate
            LocalDate start = LocalDate.parse(ngayBatDau); // yyyy-MM-dd
            LocalDate end   = LocalDate.parse(ngayKetThuc);

            // chuyển sang LocalDateTime để quét trọn ngày
            LocalDateTime startDateTime = start.atStartOfDay();         // 00:00:00
            LocalDateTime endDateTime   = end.atTime(23, 59, 59);

            // Thống kê theo khoảng ngày
            doanhThu= thongKeRepository.getThongKeDoanhThuTheoNgay(startDateTime,endDateTime);

        } else {
            // Thống kê tất cả
            doanhThu = thongKeRepository.getThongKeDoanhThu();
        }
        List<ThongKeDonHangResponseDto> data = doanhThu.stream()
                .map(this::mapToDTO)
                .toList();


        // List ngày
        List<LocalDate> listNgay = new ArrayList<>();
        for (ThongKeDonHangResponseDto item : data) {
            listNgay.add(item.getNgay());
        }

        //List tong doanh thu thuc
        List<Double> listTongDoanhThuThuc = new ArrayList<>();
        for (ThongKeDonHangResponseDto item : data) {
            listTongDoanhThuThuc.add(item.getTongDoanhThu());
        }


        response.put("status", true);
        response.put("data", data);
        response.put("list_ngay", listNgay);
        response.put("list_tongDoanhThu",listTongDoanhThuThuc);
        return response;
    }



    @Override
    public Map<String, Object> getVeDaBanTheoNgay(String ngayBatDau, String ngayKetThuc) {
        Map<String,Object> response = new HashMap<>();
        List<Object[]> veDaBan;
        if (ngayBatDau != null && ngayKetThuc != null && !ngayKetThuc.isEmpty()) {

            // parse String -> LocalDate
            LocalDate start = LocalDate.parse(ngayBatDau); // yyyy-MM-dd
            LocalDate end   = LocalDate.parse(ngayKetThuc);

            // chuyển sang LocalDateTime để quét trọn ngày
            LocalDateTime startDateTime = start.atStartOfDay();         // 00:00:00
            LocalDateTime endDateTime   = end.atTime(23, 59, 59);

            // Thống kê theo khoảng ngày
            veDaBan= thongKeVeRepository.getThongKeVeTheoNgay(startDateTime,endDateTime);

        } else {
            // Thống kê tất cả
           veDaBan = thongKeVeRepository.getThongKeVe();
        }
        List<ThongKeVeResponseDto> data = veDaBan.stream()
                .map(this::mapVeToDTO)
                .toList();

        // List ngày
        List<LocalDate> listNgay = new ArrayList<>();
        for (ThongKeVeResponseDto item : data) {
            listNgay.add(item.getNgay());
        }

        //List so ve ban
        List<Long> listSoVe = new ArrayList<>();
        for (ThongKeVeResponseDto item : data) {
            listSoVe.add(item.getSoVeBan());
        }

        //List ve da thanh toan
        List<Double> listVeDaThanhToan = new ArrayList<>();
        for (ThongKeVeResponseDto item : data) {
            listVeDaThanhToan.add(item.getVeDaThanhToan());
        }
        response.put("status", true);
        response.put("data", data);
        response.put("list_ngay", listNgay);
        response.put("list_so_ve_ban",listSoVe);
        response.put("list_ve_thanh_toan",listVeDaThanhToan);
        return response;
    }



    @Override
    public Map<String, Object> getSuatChieuTheoNgay(String ngayBatDau, String ngayKetThuc) {
        Map<String,Object> response = new HashMap<>();
        List<ThongKeSuatChieuResponseDto> suatChieu;
        if (ngayBatDau != null && ngayKetThuc != null && !ngayKetThuc.isEmpty()) {

            // parse String -> LocalDate
            LocalDate start = LocalDate.parse(ngayBatDau); // yyyy-MM-dd
            LocalDate end   = LocalDate.parse(ngayKetThuc);

            // Thống kê theo khoảng ngày
            suatChieu= suatChieuRepository.getThongKeSoLuongSuatChieuTheoNgay(start,end);

        } else {
            // Thống kê tất cả
            suatChieu = suatChieuRepository.getThongKeSuatChieu();
        }

        // List ngày
        List<LocalDate> listNgay = new ArrayList<>();
        for (ThongKeSuatChieuResponseDto item : suatChieu) {
            listNgay.add(item.getNgay());
        }

        //List so ve ban
        List<Long> listSLSuatChieu = new ArrayList<>();
        for (ThongKeSuatChieuResponseDto item : suatChieu) {
            listSLSuatChieu.add(item.getSoLuongSuatChieu());
        }
        response.put("status", true);
        response.put("data", suatChieu);
        response.put("list_ngay", listNgay);
        response.put("list_so_luong_suat_chieu",listSLSuatChieu);
        return response;
    }

    @Override
    public Map<String, Object> getPhimDangChieuTheoNgay(String ngayBatDau, String ngayKetThuc) {
        Map<String,Object> response = new HashMap<>();
        List<ThongKePhimResponseDto> thongKePhim;
        if (ngayBatDau != null && ngayKetThuc != null && !ngayKetThuc.isEmpty()) {

            // parse String -> LocalDate
            LocalDate start = LocalDate.parse(ngayBatDau); // yyyy-MM-dd
            LocalDate end   = LocalDate.parse(ngayKetThuc);

            // Thống kê theo khoảng ngày
           thongKePhim= phimRepository.getThongKePhimDangChieuTheoNgay(start,end);

        } else {
            // Thống kê tất cả
            thongKePhim=phimRepository.getThongKePhimDangChieu();
        }

        // List ngày
        List<LocalDate> listNgay = new ArrayList<>();
        for (ThongKePhimResponseDto item : thongKePhim) {
            listNgay.add(item.getNgay());
        }

        //List phim dang chieu
        List<Long> listPhim = new ArrayList<>();
        for (ThongKePhimResponseDto item : thongKePhim) {
            listPhim.add(item.getSoLuongPhimDangChieu());
        }

        response.put("status", true);
        response.put("data", thongKePhim);
        response.put("list_ngay", listNgay);
        response.put("list_so_luong_phim",listPhim);
        return response;
    }

    @Override
    public Map<String, Object> getVoucherSuDungTheoNgay(String ngayBatDau, String ngayKetThuc) {
        Map<String,Object> response = new HashMap<>();
        List<Object[]> thongKeVoucher;
        if (ngayBatDau != null && ngayKetThuc != null && !ngayKetThuc.isEmpty()) {

            // parse String -> LocalDate
            LocalDate start = LocalDate.parse(ngayBatDau); // yyyy-MM-dd
            LocalDate end   = LocalDate.parse(ngayKetThuc);

            // chuyển sang LocalDateTime để quét trọn ngày
            LocalDateTime startDateTime = start.atStartOfDay();         // 00:00:00
            LocalDateTime endDateTime   = end.atTime(23, 59, 59);

            // Thống kê theo khoảng ngày
           thongKeVoucher= voucherRepository.getThongKeVoucherSuDungTheoNgay(startDateTime,endDateTime);

        } else {
            // Thống kê tất cả
            thongKeVoucher= voucherRepository.getThongKeVoucher();
        }
        List<ThongKeVoucherResponseDto> data = thongKeVoucher.stream()
                .map(this::mapVoucherToDTO)
                .toList();

        //List ten Voucher
        List<String> listTenVoucher = new ArrayList<>();
        for (ThongKeVoucherResponseDto item : data) {
            listTenVoucher.add(item.getTenVoucher());
        }

        //Lt lượt sử dụng
        List<Long> listLuotSuDung = new ArrayList<>();
        for (ThongKeVoucherResponseDto item : data) {
            listLuotSuDung.add(item.getLuotSuDung());
        }
        response.put("status", true);
        response.put("data", data);
        response.put("list_ten", listTenVoucher);
        response.put("list_luot_dung", listLuotSuDung);
        return response;
    }

    @Override
    public Map<String, Object> getDichVuDaBanTheoNgay(String ngayBatDau, String ngayKetThuc) {
        Map<String,Object> response = new HashMap<>();
        List<Object[]> thongKeDichVu;
        if (ngayBatDau != null && ngayKetThuc != null && !ngayKetThuc.isEmpty()) {

            // parse String -> LocalDate
            LocalDate start = LocalDate.parse(ngayBatDau); // yyyy-MM-dd
            LocalDate end   = LocalDate.parse(ngayKetThuc);

            // chuyển sang LocalDateTime để quét trọn ngày
            LocalDateTime startDateTime = start.atStartOfDay();         // 00:00:00
            LocalDateTime endDateTime   = end.atTime(23, 59, 59);

            // Thống kê theo khoảng ngày
            thongKeDichVu=chiTietDichVuRepository.getThongKeDichVuDaBanTheoNgay(startDateTime,endDateTime);

        } else {
            // Thống kê tất cả
            thongKeDichVu=chiTietDichVuRepository.getThongKeDichVuDaBan();
        }

        List<ThongKeDichVuResponseDto> data = thongKeDichVu.stream()
                .map(this::mapDichVuToDTO)
                .toList();

        // List ngày
        List<LocalDate> listNgay = new ArrayList<>();
        for (ThongKeDichVuResponseDto item : data) {
            listNgay.add(item.getNgay());
        }

        //List so dich vu da ban
        List<Long> listSoDichVu = new ArrayList<>();
        for (ThongKeDichVuResponseDto item : data) {
            listSoDichVu.add(item.getSoDichVuDaBan());
        }

        //Lt lượt sử dụng
        List<Double> listDvDaThanhToan = new ArrayList<>();
        for (ThongKeDichVuResponseDto item : data) {
            listDvDaThanhToan.add(item.getDaThanhToan());
        }
        response.put("status", true);
        response.put("data", data);
        response.put("list_ngay", listNgay);
        response.put("list_so_dv", listSoDichVu);
        response.put("list_dv_thanh_toan", listDvDaThanhToan);
        return response;
    }

    @Override
    public Map<String, Object> getChiTieuTheoNgay(String ngayBatDau, String ngayKetThuc) {

        Map<String,Object> response = new HashMap<>();
        List<Object[]> thongKeChiTieu;
        if (ngayBatDau != null && ngayKetThuc != null && !ngayKetThuc.isEmpty()) {

            // parse String -> LocalDate
            LocalDate start = LocalDate.parse(ngayBatDau); // yyyy-MM-dd
            LocalDate end   = LocalDate.parse(ngayKetThuc);

            // chuyển sang LocalDateTime để quét trọn ngày
            LocalDateTime startDateTime = start.atStartOfDay();         // 00:00:00
            LocalDateTime endDateTime   = end.atTime(23, 59, 59);

            // Thống kê theo khoảng ngày
           thongKeChiTieu= thongKeRepository.getThongKeChiTieuTheoNgay(startDateTime,endDateTime);

        } else {
            // Thống kê tất cả
            thongKeChiTieu= thongKeRepository.getThongKeChiTieu();
        }

        List<ThongKeChiTieuResponseDto> data = thongKeChiTieu.stream()
                .map(this::mapChiTieuToDTO)
                .toList();



        //List ten khach hang
        List<String> listTenKhachHang = new ArrayList<>();
        for (ThongKeChiTieuResponseDto item : data) {
            listTenKhachHang.add(item.getTenKhachHang());
        }

        //List tổng chi tiêu
        List<Double> listTongChiTieu = new ArrayList<>();
        for (ThongKeChiTieuResponseDto item : data) {
            listTongChiTieu.add(item.getTongChiTieu());
        }
        response.put("status", true);
        response.put("data", data);
        response.put("list_ten", listTenKhachHang);
        response.put("list_chi_tieu", listTongChiTieu);
        return response;
    }


    @Override
    public Map<String, Object> getKhacHangMoiTheoNgay(String ngayBatDau, String ngayKetThuc) {
        Map<String,Object> response = new HashMap<>();
        List<Object[]> khachHangMoi;
        if (ngayBatDau != null && ngayKetThuc != null && !ngayKetThuc.isEmpty()) {

            // parse String -> LocalDate
            LocalDate start = LocalDate.parse(ngayBatDau); // yyyy-MM-dd
            LocalDate end   = LocalDate.parse(ngayKetThuc);

            // chuyển sang LocalDateTime để quét trọn ngày
            LocalDateTime startDateTime = start.atStartOfDay();         // 00:00:00
            LocalDateTime endDateTime   = end.atTime(23, 59, 59);

            // Thống kê theo khoảng ngày
           khachHangMoi= khachHangRepository.getThongKeSoluongKhachMoiTheoNgay(startDateTime,endDateTime);

        } else {
            // Thống kê tất cả
            khachHangMoi= khachHangRepository.getThongKeSoLuongKhachHangMoi();
        }

        List<ThongKeKhachHangMoiResponseDto> thongKeKhachMoi = khachHangMoi.stream()
                .map(this::mapKhachHangMoiToDTO)
                .toList();

        // List ngày
        List<LocalDate> listNgay = new ArrayList<>();
        for (ThongKeKhachHangMoiResponseDto item : thongKeKhachMoi) {
            listNgay.add(item.getNgay());
        }

        //List so ve ban
        List<Long> listSLKhachMoi = new ArrayList<>();
        for (ThongKeKhachHangMoiResponseDto item : thongKeKhachMoi) {
            listSLKhachMoi.add(item.getSoLuongKhacHangMoi());
        }
        response.put("status", true);
        response.put("data", thongKeKhachMoi);
        response.put("list_ngay", listNgay);
        response.put("list_so_luong_khach_hang_moi",listSLKhachMoi);
        return response;
    }

    private ThongKeDonHangResponseDto mapToDTO(Object[] row) {
        return new ThongKeDonHangResponseDto(
                row[0] instanceof Date d ? d.toLocalDate() : (LocalDate) row[0],
                row[1] == null ? 0L : ((Number) row[1]).longValue(),
                row[2] == null ? 0L : ((Number) row[2]).doubleValue(),
                row[3] == null ? 0L : ((Number) row[3]).doubleValue(),
                row[4] == null ? 0L : ((Number) row[4]).doubleValue()
        );
    }

    private ThongKeVeResponseDto mapVeToDTO(Object[] row) {
        return new ThongKeVeResponseDto(
                row[0] instanceof Date d ? d.toLocalDate() : (LocalDate) row[0],
                row[1] == null ? 0L : ((Number) row[1]).longValue(),
                row[2] == null ? 0L : ((Number) row[2]).doubleValue()
        );
    }

    private ThongKeDichVuResponseDto mapDichVuToDTO(Object[] row) {
        return new ThongKeDichVuResponseDto(
                row[0] instanceof Date d ? d.toLocalDate() : (LocalDate) row[0],
                row[1] == null ? 0L : ((Number) row[1]).longValue(),
                row[2] == null ? 0L : ((Number) row[2]).doubleValue()
        );
    }

    private ThongKeVoucherResponseDto mapVoucherToDTO(Object[] row) {
        return new ThongKeVoucherResponseDto(
                row[0] instanceof Date d ? d.toLocalDate() : (LocalDate) row[0],
                Objects.toString(row[1], ""),   // nếu null thì ra ""
                row[2] == null ? 0L : ((Number) row[2]).longValue()
        );
    }

    private ThongKeChiTieuResponseDto mapChiTieuToDTO(Object[] row) {
        return new ThongKeChiTieuResponseDto(
                row[0] instanceof Date d ? d.toLocalDate() : (LocalDate) row[0],
                Objects.toString(row[1], ""),   // nếu null thì ra ""
                row[2] == null ? 0L : ((Number) row[2]).longValue(),
                row[3] == null ? 0L : ((Number) row[3]).doubleValue()
        );
    }

    private ThongKeKhachHangMoiResponseDto mapKhachHangMoiToDTO(Object[] row) {
        return new ThongKeKhachHangMoiResponseDto(
                row[0] instanceof Date d ? d.toLocalDate() : (LocalDate) row[0],
                row[1] == null ? 0L : ((Number) row[1]).longValue()
        );
    }
}
