package com.quoc.Movie_Ticket_Booking.schedular;

import com.quoc.Movie_Ticket_Booking.model.DonHang;
import com.quoc.Movie_Ticket_Booking.model.Ve;
import com.quoc.Movie_Ticket_Booking.repository.DonHangRepository;
import com.quoc.Movie_Ticket_Booking.repository.VeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component

public class VeScheduler {
    @Autowired
    private VeRepository veRepository;

    @Autowired
    private DonHangRepository donHangRepository;


    /**
     * Tự động huỷ các vé chưa thanh toán sau 5 phút
     * Chạy mỗi 1 phút
     */
    @Scheduled(fixedRate = 60000) // kiểm tra mỗi phút
    @Transactional
    public void autoResetVe() {
        LocalDateTime expiredTime = LocalDateTime.now().minusMinutes(5);
        List<Ve> expiredVes = veRepository.findExpiredVe(expiredTime);

        for (Ve ve : expiredVes) {
            DonHang donHang = ve.getDonHang();
            if (donHang != null && (donHang.getIsThanhToan() == DonHang.CHUA_THANH_TOAN
                    || donHang.getIsThanhToan() == DonHang.DA_HUY) ) {
                ve.setDonHang(null);
                ve.setTinhTrang(Ve.CHUA_THANH_TOAN);
                veRepository.save(ve);

                // Huỷ luôn đơn hàng chưa thanh toán:
                donHang.setIsThanhToan(DonHang.DA_HUY);
                donHangRepository.save(donHang);
            }
        }
    }

}
