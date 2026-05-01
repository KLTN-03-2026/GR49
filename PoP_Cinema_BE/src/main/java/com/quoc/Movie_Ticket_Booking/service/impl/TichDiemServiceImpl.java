package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.dto.response.*;
import com.quoc.Movie_Ticket_Booking.model.HangThanhVien;
import com.quoc.Movie_Ticket_Booking.model.TichDiem;
import com.quoc.Movie_Ticket_Booking.model.Users;
import com.quoc.Movie_Ticket_Booking.repository.TichDiemRepository;
import com.quoc.Movie_Ticket_Booking.repository.UsersRepository;
import com.quoc.Movie_Ticket_Booking.service.TichDiemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class TichDiemServiceImpl implements TichDiemService {

    @Autowired
    private TichDiemRepository tichDiemRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public ApiResponse<?> getAllTichDiem( LocalDate from, LocalDate to, Integer tinhTrang) {

        // 1. Xử lý thời gian
        LocalDateTime start = (from != null)
                ? from.atStartOfDay()
                : LocalDateTime.MIN;

        LocalDateTime end = (to != null)
                ? to.atTime(LocalTime.MAX)
                : LocalDateTime.now();

        // 2. Xác định load mặc định
        boolean isDefault = (from == null && to == null && tinhTrang == null);

        List<TichDiem> list;

        // 3. Query
        if (isDefault) {
            list = tichDiemRepository.findAllByOrderByCreatedAtDesc();

        } else if (tinhTrang != null) {
            list = tichDiemRepository.findByTinhTrangAndCreatedAtBetween(
                    tinhTrang, start, end
            );

        } else {
            list = tichDiemRepository.findByCreatedAtBetween(start, end);
        }

        // 4. Map DTO
        List<TichDiemResponseDto> dtoList = list.stream()
                .map(this::mapToTichDiemDto)
                .toList();

        return ApiResponse.success("Hiển thị thành công!", dtoList);

    }

    @Override
    public ApiResponse<?> getLichSuTichDiem(Long userId, LocalDate from, LocalDate to, Integer tinhTrang) {
        // 1. Xử lý ngày
        LocalDateTime start = (from != null)
                ? from.atStartOfDay()
                : LocalDateTime.MIN;

        LocalDateTime end = (to != null)
                ? to.atTime(LocalTime.MAX)
                : LocalDateTime.now();

        // 2. Chọn query
        List<TichDiem> list;

        boolean isDefault = (from == null && to == null && tinhTrang == null);

        if (isDefault) {
            list = tichDiemRepository
                    .findByKhachHangIdAndTinhTrangNotOrderByCreatedAtDesc(
                            userId,
                            TichDiem.CHUA_XAC_DINH
                    );
        } else if (tinhTrang != null) {
            list = tichDiemRepository
                    .findByKhachHangIdAndTinhTrangAndCreatedAtBetween(
                            userId, tinhTrang, start, end
                    );
        } else {
            list = tichDiemRepository
                    .findByKhachHangIdAndCreatedAtBetween(
                            userId, start, end
                    );
        }

        List<LichSuTichDiemUserResponseDto > tichDiemDtoList = list.stream()
                .map(this::mapToLichSuTichDiemDto)
                .toList();

        return ApiResponse.success("Hiển thị thành công!",tichDiemDtoList);
    }



    private TichDiemResponseDto mapToTichDiemDto(TichDiem tichDiem) {
        String maDonHang = null;
        String tenKhachHang = null;
        Integer tongDiem = null;

        if (tichDiem.getDonHang() != null) {
            maDonHang = tichDiem.getDonHang().getMaDonHang();
        }

        if (tichDiem.getKhachHang() != null) {
            tenKhachHang = tichDiem.getKhachHang().getHoVaTen();
//            tongDiem = tichDiem.getKhachHang().getDiem();
        }

        return new TichDiemResponseDto(
                tichDiem.getId(),
                tenKhachHang,
                tichDiem.getDiem(),
                tichDiem.getTongDiem(),
                tichDiem.getLoai(),
                tichDiem.getMoTa(),
                tichDiem.getCreatedAt(),
                tichDiem.getTinhTrang()
        );
    }


    private LichSuTichDiemUserResponseDto mapToLichSuTichDiemDto(TichDiem tichDiem) {
        return new LichSuTichDiemUserResponseDto(
                tichDiem.getCreatedAt(),
                tichDiem.getDiem(),
                tichDiem.getLoai(),
                tichDiem.getMoTa(),
                tichDiem.getTinhTrang()
        );
    }

    @Override
    public ApiResponse<?> getTop5KhachHang() {
        List<TopKhachHangNhieuDiemDto> result = usersRepository
                .findTop5ByOrderByDiemDesc()
                .stream()
                .map(this::mapToTopKhachHangDto)
                .toList();

        return ApiResponse.success("Lấy top 5 thành công!", result);
    }

    private TopKhachHangNhieuDiemDto mapToTopKhachHangDto(Users u) {
        return new TopKhachHangNhieuDiemDto(
                u.getId(),
                u.getHoVaTen(),
                u.getHangThanhVien() != null
                        ? u.getHangThanhVien().getTenHang()
                        : "Chưa có",
                u.getDiem()
        );
    }
}
