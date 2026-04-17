package com.quoc.Movie_Ticket_Booking.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public interface ThongKeService {

   public Map<String, Object> getAllDoanhThu();

    public Map<String, Object> getDoanhThuTheoNgay(String ngayBatDau, String ngayKetThuc);

    public Map<String, Object> getVeDaBanTheoNgay(String ngayBatDau, String ngayKetThuc);

    public Map<String, Object> getSuatChieuTheoNgay(String ngayBatDau, String ngayKetThuc);

    public Map<String, Object> getPhimDangChieuTheoNgay(String ngayBatDau, String ngayKetThuc);

    public Map<String, Object> getVoucherSuDungTheoNgay(String ngayBatDau, String ngayKetThuc);

    public Map<String, Object> getDichVuDaBanTheoNgay(String ngayBatDau, String ngayKetThuc);

    public Map<String, Object> getChiTieuTheoNgay(String ngayBatDau, String ngayKetThuc);

    public Map<String, Object> getKhacHangMoiTheoNgay(String ngayBatDau, String ngayKetThuc);

}
