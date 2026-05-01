package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.dto.request.BinhLuanRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.DanhGiaRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.BinhLuanResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.DanhGiaResponseDto;
import com.quoc.Movie_Ticket_Booking.exception.ResourceNotFoundException;
import com.quoc.Movie_Ticket_Booking.model.BinhLuan;
import com.quoc.Movie_Ticket_Booking.model.DanhGia;
import com.quoc.Movie_Ticket_Booking.model.Phim;
import com.quoc.Movie_Ticket_Booking.model.Users;
import com.quoc.Movie_Ticket_Booking.repository.BinhLuanRepository;
import com.quoc.Movie_Ticket_Booking.repository.DanhGiaRepository;
import com.quoc.Movie_Ticket_Booking.repository.UsersRepository;
import com.quoc.Movie_Ticket_Booking.service.DanhGiaVaBinhLuanService;
import com.quoc.Movie_Ticket_Booking.service.PhimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DanhGiaVaBinhLuanServiceImpl implements DanhGiaVaBinhLuanService {

    @Autowired
    private DanhGiaRepository danhGiaRepository;

    @Autowired
    private BinhLuanRepository binhLuanRepository;

    @Autowired
    private PhimService phimService;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public ApiResponse<?> createDanhGia(DanhGiaRequestDto danhGiaRequestDto,Long idKhach) {

//        // 0. Kiểm tra đăng nhập
//        if (idKhach == null) {
//            response.put("status", false);
//            response.put("message", "Bạn cần đăng nhập để đánh giá phim.");
//            return response;
//        }

        // 1 Kiểm tra dữ liệu đầu vào
        if (danhGiaRequestDto.getSoSao() == null || danhGiaRequestDto.getSoSao() < 1 || danhGiaRequestDto.getSoSao() > 5) {

            return ApiResponse.fail("Vui lòng chọn số sao hợp lệ (1–5).");
        }

        Phim phimById = phimService.getPhimById(danhGiaRequestDto.getIdPhim());
        Users khachById = usersRepository.findById(idKhach)
                .orElseThrow(() -> new ResourceNotFoundException("Khách hàng không tồn tại, id=" + idKhach));


        // 3 Kiểm tra người này đã đánh giá phim này chưa
        boolean daDanhGia = danhGiaRepository.existsByPhimIdAndKhachHangId(danhGiaRequestDto.getIdPhim(), idKhach);
        if (daDanhGia) {

            return ApiResponse.fail("Bạn đã đánh giá phim này rồi!");
        }

        DanhGia danhGia = new DanhGia();
        danhGia.setSoSao(danhGiaRequestDto.getSoSao());
        danhGia.setPhim(phimById);
        danhGia.setKhachHang(khachById);
        danhGia.setCreatedAt(LocalDateTime.now());
        danhGiaRepository.save(danhGia);

        return ApiResponse.success("Cảm ơn bạn đã đánh giá.");
    }


    @Override
    public ApiResponse<?> getDanhGia(Long idPhim) {

        List<DanhGiaResponseDto> danhGiaByIdPhim = danhGiaRepository.getDanhGiaByIdPhim(idPhim);

        return ApiResponse.success("Hiển thị thành công.",danhGiaByIdPhim);
    }

    @Override
    public ApiResponse<?> createBinhLuan(BinhLuanRequestDto binhLuanRequestDto, Long idKhach) {


//        // 1. Kiểm tra đăng nhập
//        if (idKhach == null) {
//            response.put("status", false);
//            response.put("message", "Bạn cần đăng nhập để bình luận phim.");
//            return response;
//        }

        // 2.Kiểm tra nội dung bình luận
        if (binhLuanRequestDto.getNoiDung() == null || binhLuanRequestDto.getNoiDung().trim().isEmpty()) {

            return ApiResponse.fail("Vui lòng nhập nội dung bình luận!");
        }
        
        Phim phimById = phimService.getPhimById(binhLuanRequestDto.getIdPhim());
        Users khachById = usersRepository.findById(idKhach)
                .orElseThrow(() -> new ResourceNotFoundException("Khách hàng không tồn tại, id=" + idKhach));

        BinhLuan binhLuan= new BinhLuan();
        binhLuan.setNoiDung(binhLuanRequestDto.getNoiDung());
        binhLuan.setPhim(phimById);
        binhLuan.setKhachHang(khachById);
        binhLuan.setCreatedAt(LocalDateTime.now());

         binhLuanRepository.save(binhLuan);

        return ApiResponse.success("Bình luận của bạn đã được gửi.");
    }

    @Override
    public ApiResponse<?> getBinhLuan(Long idPhim) {

        List<BinhLuanResponseDto> binhLuanByIdPhim = binhLuanRepository.getBinhLuanByIdPhim(idPhim);

        return ApiResponse.success("Hiển thị thành công.",binhLuanByIdPhim);
    }
}
