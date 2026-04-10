package com.quoc.Movie_Ticket_Booking.vnpaypayment;

import com.quoc.Movie_Ticket_Booking.config.VNPAYConfig;
import com.quoc.Movie_Ticket_Booking.dto.request.ThanhToanDonHangRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.DonHangDetailsResponseDto;
import com.quoc.Movie_Ticket_Booking.exception.ResourceNotFoundException;
import com.quoc.Movie_Ticket_Booking.model.*;
import com.quoc.Movie_Ticket_Booking.repository.*;
import com.quoc.Movie_Ticket_Booking.util.CommonUtil;
import com.quoc.Movie_Ticket_Booking.util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Autowired
    private DonHangRepository donHangRepository;

    @Autowired
    private VeRepository veRepository;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private TichDiemRepository tichDiemRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private HangThanhVienRepository hangThanhVienRepository;

    @Autowired
    private QuaVoucherRepository quaVoucherRepository;

    private final VNPAYConfig vnPayConfig;
    public PaymentResponse createVnPayPayment(ThanhToanDonHangRequestDto responseDto, HttpServletRequest request) {

//        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        //  Thông tin động từ đơn hàng
        vnpParamsMap.put("vnp_TxnRef", responseDto.getMaDonHang());
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan don hang: " + responseDto.getMaDonHang());
        long amount = Math.round(Integer.parseInt(responseDto.getTienThucNhan().toString()) * 100);
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));


        if (bankCode != null && !bankCode.isEmpty() && !bankCode.equals("null")) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));

        String returnUrlWithOrder = vnPayConfig.getVnp_ReturnUrl() + "?maDonHang=" + responseDto.getMaDonHang();
        vnpParamsMap.put("vnp_ReturnUrl", returnUrlWithOrder);
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return PaymentResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }


    //thành công
    public void success(String maDonHang){

        DonHang byMaDonHang = donHangRepository.findByMaDonHang(maDonHang);
        if (byMaDonHang == null) {
            throw new ResourceNotFoundException("Don hang not found ma don hang "+maDonHang);
        }

        // ==== 1. Update đơn hàng ====
        byMaDonHang.setIsThanhToan(1);
        byMaDonHang.setPhuongThucThanhToan(2);
        donHangRepository.save(byMaDonHang);

        //=== 2.Lưu tất cả trạng thái vé theo mã don hàng khi thanh toán thành công ===
        List<Ve> ve= veRepository.findByDonHangId(byMaDonHang.getId());
        for (Ve ve1 : ve) {
            ve1.setTinhTrang(2);
            veRepository.save(ve1);
        }


        // ==== 3. CỘNG ĐIỂM + NÂNG HẠNG====
        congDiemSauThanhToan(byMaDonHang);

        // ==== 4. Đánh dấu voucher đổi quà đã sử dụng (NẾU CÓ) ====
        capNhatTrangThaiQuaVoucher(byMaDonHang);

        // ==== 5. Gửi mail ====
        DonHangDetailsResponseDto dto = donHangRepository.getDonHangToEmail(maDonHang);
        try {

            commonUtil.sendEmailForProductOrder(dto);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    //huỷ
    public void cancel(String maDonHang){

        DonHang byMaDonHang = donHangRepository.findByMaDonHang(maDonHang);
        if (byMaDonHang == null) {
            throw new ResourceNotFoundException("Don hang not found ma don hang "+maDonHang);
        }
        byMaDonHang.setIsThanhToan(2);
        byMaDonHang.setPhuongThucThanhToan(2);
        donHangRepository.save(byMaDonHang);

        //Lưu tất cả trạng thái vé theo mã don hàng khi huỷ order
        List<Ve> ve= veRepository.findByDonHangId(byMaDonHang.getId());
        for (Ve ve1 : ve) {
            ve1.setTinhTrang(0);
            veRepository.save(ve1);
        }



    }

    //Cộng điểm
    @Transactional
    public void congDiemSauThanhToan(DonHang donHang) {

        Optional<TichDiem> opt = tichDiemRepository.findByDonHangId(donHang.getId());

        if (opt.isEmpty()) return;

        TichDiem tichDiem = opt.get();

        // Đã cộng rồi thì bỏ qua
        if (tichDiem.getTinhTrang() == TichDiem.DA_CONG) return;

        Users user = donHang.getKhachHang();

        int diemHienTai = user.getDiem();
        int diemCong = tichDiem.getDiem();
        // Cộng điểm cho user
        int tongDiemMoi = diemHienTai + diemCong;
        user.setDiem(tongDiemMoi);
        usersRepository.save(user);

        // Đánh dấu đã cộng
        tichDiem.setTinhTrang(TichDiem.DA_CONG);
        tichDiem.setTongDiem(tongDiemMoi);
        tichDiemRepository.save(tichDiem);

        // Cập nhật hạng
        capNhatHangThanhVien(user);
    }

    //cập nhật hạng thành viên
    private void capNhatHangThanhVien(Users user) {

        int tongDiem = user.getDiem();

        List<HangThanhVien> list =
                hangThanhVienRepository.findHangByTongDiem(tongDiem);

        if (list.isEmpty()) return;

        HangThanhVien hangMoi = list.get(0); // HẠNG CAO NHẤT

        // Nếu chưa có hạng → gán luôn
        if (user.getHangThanhVien() == null) {
            user.setHangThanhVien(hangMoi);
            usersRepository.save(user);
            return;
        }

        // Chỉ nâng hạng, không tụt
        if (user.getHangThanhVien().getDiemToiThieu()
                >= hangMoi.getDiemToiThieu()) return;

        user.setHangThanhVien(hangMoi);
        usersRepository.save(user);
    }

    //câp nhật voucher đã sử dụng
    private void capNhatTrangThaiQuaVoucher(DonHang donHang) {

        if (donHang.getVoucher() == null) return;

        Optional<QuaVoucher> opt = quaVoucherRepository
                .findByVoucherIdAndKhachHangId(
                        donHang.getVoucher().getId(),
                        donHang.getKhachHang().getId()
                );

        if (opt.isEmpty()) return;

        QuaVoucher quaVoucher = opt.get();

        // tránh update lại
        if (quaVoucher.getTinhTrang() == QuaVoucher.DA_SU_DUNG) return;

        quaVoucher.setTinhTrang(QuaVoucher.DA_SU_DUNG);
        quaVoucherRepository.save(quaVoucher);
    }
}