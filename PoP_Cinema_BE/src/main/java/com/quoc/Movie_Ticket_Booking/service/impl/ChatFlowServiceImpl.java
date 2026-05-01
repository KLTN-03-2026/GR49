package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.dto.request.DatVeChatFlowRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.AllVeResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ChatResponse;
import com.quoc.Movie_Ticket_Booking.exception.ResourceNotFoundException;
import com.quoc.Movie_Ticket_Booking.model.*;
import com.quoc.Movie_Ticket_Booking.repository.DonHangRepository;
import com.quoc.Movie_Ticket_Booking.repository.TichDiemRepository;
import com.quoc.Movie_Ticket_Booking.repository.UsersRepository;
import com.quoc.Movie_Ticket_Booking.repository.VeRepository;
import com.quoc.Movie_Ticket_Booking.service.ChatFlowService;
import com.quoc.Movie_Ticket_Booking.service.PhimService;
import com.quoc.Movie_Ticket_Booking.service.SuatChieuService;
import com.quoc.Movie_Ticket_Booking.service.UsersService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatFlowServiceImpl implements ChatFlowService {

    private final PhimService phimService;
    private final SuatChieuService suatChieuService;
    private final VeRepository veRepository;
    private final UsersService usersService;
    private final UsersRepository usersRepository;
    private final DonHangRepository donHangRepository;
    private final TichDiemRepository tichDiemRepository;

    public ChatFlowServiceImpl(PhimService phimService, SuatChieuService suatChieuService,VeRepository veRepository, UsersService usersService,
                               UsersRepository usersRepository, DonHangRepository donHangRepository, TichDiemRepository tichDiemRepository) {
        this.phimService = phimService;
        this.suatChieuService = suatChieuService;
        this.veRepository = veRepository;
        this.usersService = usersService;
        this.usersRepository = usersRepository;
        this.donHangRepository = donHangRepository;
        this.tichDiemRepository = tichDiemRepository;
    }

    @Override
    public ChatResponse hienThiPhim() {
        List<Phim> ds = phimService.getPhimDangChieuu();

        List<Map<String, Object>> options = ds.stream()
                .map(p -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("label", p.getTenPhim());
                    map.put("value", p.getId());
                    map.put("hinhAnh", p.getHinhAnh());
                    return map;
                })
                .toList();

        ChatResponse res = new ChatResponse();
        res.setType("ui");              
        res.setComponent("buttons");    
        res.setMessage("🎬 Chọn phim bạn muốn xem:");
        res.setOptions(options);

        return res;
    }

    @Override
    public ChatResponse hienThiSuatChieu(Long idPhim) {
        var ds = suatChieuService.getSuatChieuByPhimId(idPhim);

        List<Map<String, Object>> options = ds.stream()
                .map(sc -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("label", sc.getThoiGianBatDau().toString().substring(0, 5)); // HH:mm
                    map.put("ngay", sc.getNgayChieu().toString()); // thêm ngày riêng
                    map.put("value", sc.getId());
                    return map;
                })
                .toList();


        ChatResponse res = new ChatResponse();
        res.setType("ui");              
        res.setComponent("time_grid");     
        res.setMessage("🕒 Chọn suất chiếu:");
        res.setOptions(options);

        return res;
    }

    @Override
    public ChatResponse hienThiDanhSachGhe(Long idSuatChieu, String jwt) {

        Users user = usersService.findUserByJwtToken(jwt);

        if (user == null) {
            ChatResponse res = new ChatResponse();
            res.setType("ui");
            res.setComponent("notification");
            res.setMessage("⚠️ Bạn cần đăng nhập để xem và chọn ghế!");
            res.setRequireLogin(true);
            return res;
        }

        // Lấy thông tin suất chiếu
        SuatChieu sc = suatChieuService.getSuatChieuById(idSuatChieu);

        String tenPhim = sc.getPhim().getTenPhim();
        LocalTime gio = sc.getThoiGianBatDau();
        LocalDate ngay = sc.getNgayChieu();

        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd/MM");

        String timeStr = gio.format(timeFmt);
        String dateStr = ngay.format(dateFmt);

        Map<String, Object> data = getClientAllVe(idSuatChieu);
        List<AllVeResponseDto> gheList = (List<AllVeResponseDto>) data.get("data_ve");

        // Gom theo hàng
        Map<String, List<AllVeResponseDto>> grouped =
                gheList.stream()
                        .collect(Collectors.groupingBy(g -> g.getTenGhe().substring(0, 1)));

        List<Map<String, Object>> matrix = new ArrayList<>();

        for (var row : grouped.entrySet()) {

            Map<String, Object> rowData = new HashMap<>();
            rowData.put("row", row.getKey());

            List<Map<String, Object>> seats = row.getValue().stream()
                    .map(g -> {
                        Map<String, Object> m = new HashMap<>();
                        m.put("label", g.getTenGhe());
                        m.put("value", g.getId());
                        m.put("disabled", g.getDonHang() != null); // ✔ ghế đã đặt
                        return m;
                    }).toList();

            rowData.put("seats", seats);
            matrix.add(rowData);
        }

        String message =
                "🎟️ Chọn ghế\n"
                        + "━━━━━━━━━━━━━━\n"
                        + "🎬 Phim: " + tenPhim + "\n"
                        + "🕒 Suất: " + timeStr + " (" + dateStr + ")\n\n";

        ChatResponse res = new ChatResponse();
        res.setType("ui");
        res.setComponent("seat_matrix");
        res.setMessage(message);
        res.setMatrix(matrix);

        return res;
    }

    public Map<String, Object> getClientAllVe(Long idSuatChieu) {

        Map<String, Object> response = new HashMap<>();


        //cách 1 lồng entity vào dto
        List<Ve> allVe = veRepository.findBySuatChieuId(idSuatChieu);

//        List<AllVeResponseDto> veDtoList = allVe.stream()
//                .map(this::mapToVeDto)
//                .toList();

        //Cách 2 bằng JPQL
        //Lấy ds vé theo id suất chiếu
        List<AllVeResponseDto> veDtoList= veRepository.getDatVeBySuatChieuId(idSuatChieu);


        //Lấy suất chiếu theo id suất chiêú
        SuatChieu suatChieuById = suatChieuService.getSuatChieuById(idSuatChieu);

        response.put("status", true);
        response.put("data_ve", veDtoList);



        return response;
    }

    @Transactional
    @Override
    public ChatResponse datVe(DatVeChatFlowRequestDto dto, Long idUser) {
        Users user = usersRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Khách hàng không tồn tại, id=" + idUser));
        List<Long> veIds = dto.getListVeIds();
        List<Ve> dsVe = veRepository.findByIdInAndIdDonHangs(veIds);

        // GHẾ ĐÃ BỊ NGƯỜI KHÁC ĐẶT
        if (dsVe.size() != veIds.size()) {
            ChatResponse res = new ChatResponse();
            res.setType("ui");
            res.setComponent("notification");
            res.setMessage("❗ Một hoặc nhiều ghế đã có người đặt trước bạn!");
            return res;
        }

        // TÍNH TIỀN
        int tongTien = dsVe.stream().mapToInt(Ve::getGiaVe).sum();

        // Tạo đơn hàng
        DonHang dh= createDonHang(tongTien,user);
        // Tạo tích điểm
        createTichDiem(dh);
        // GÁN VÉ
        dsVe.forEach(v -> v.setDonHang(dh));
        veRepository.saveAll(dsVe);

        // TRẢ VỀ CHATBOT
        ChatResponse res = new ChatResponse();
        res.setType("ui");
        res.setComponent("notification");
        res.setMessage(
                "🎉 Đặt vé thành công!\n\n" +
                        "🧾 Mã hoá đơn: **" + dh.getMaDonHang() + "**\n" +
                        "💰 Tổng tiền: **" + dh.getTienThucNhan() + "đ**\n\n" +
                        "👉 Vui lòng tiến hành thanh toán."
        );
        // ===== THÊM BUTTON =====
        List<Map<String, Object>> btns = new ArrayList<>();
        
        btns.add(Map.of(
                "label", "💳 VNPay",
                "value", "pay_vnpay",
                "maDonHang", dh.getMaDonHang(),
                "tienThucNhan", dh.getTienThucNhan()
        ));

        btns.add(Map.of(
                "label", "📱 MoMo",
                "value", "pay_momo",
                "maDonHang", dh.getMaDonHang(),
                "tienThucNhan", dh.getTienThucNhan()
        ));
        btns.add(Map.of(
                "label", "📱 PayOs",
                "value", "pay_payos",
                "maDonHang", dh.getMaDonHang(),
                "tienThucNhan", dh.getTienThucNhan()
        ));
        res.setOptions(btns);
        return res;
    }

    private DonHang createDonHang(int tongTien,Users userId) {

        DonHang donHang = new DonHang();
        donHang.setKhachHang(userId);
        String shortId = generateNumberRicId(8);
        donHang.setMaDonHang("DH"+shortId);
        donHang.setNgayDat(LocalDateTime.now());
        donHang.setTienThucNhan(tongTien);
        donHang.setTongTien(tongTien);
        donHang.setGiamGia(0);
        donHang.setIsThanhToan(0);
        donHang.setPhuongThucThanhToan(0);
        return donHangRepository.save(donHang);

    }


    //tạo tích điểm cho đơn hàng
    private void createTichDiem(DonHang donHang) {

        // Quy ước: 10.000đ = 1 điểm
        int diem = donHang.getTienThucNhan() / 10000;

        if (diem <= 0) return;

        String moTa = "Cộng điểm đơn đặt vé: " + donHang.getMaDonHang();
        TichDiem tichDiem = new TichDiem();
        tichDiem.setDonHang(donHang);
        tichDiem.setKhachHang(donHang.getKhachHang());
        tichDiem.setDiem(diem);
        tichDiem.setLoai(1);
        tichDiem.setMoTa(moTa);
        tichDiem.setTinhTrang(TichDiem.CHUA_XAC_DINH);
        tichDiem.setCreatedAt(LocalDateTime.now());
        tichDiem.setTongDiem(0);

        tichDiemRepository.save(tichDiem);
    }

    public String generateNumberRicId(Integer length){
        String digits = "0123456789";
        StringBuilder id = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            id.append(digits.charAt(random.nextInt(digits.length())));
        }

        return id.toString();
    }

    @Override
    public ChatResponse xemSoDoGhe(Long idSuatChieu) {

        // Lấy thông tin suất chiếu
        SuatChieu sc = suatChieuService.getSuatChieuById(idSuatChieu);

        String tenPhim = sc.getPhim().getTenPhim();
        LocalTime gio = sc.getThoiGianBatDau();
        LocalDate ngay = sc.getNgayChieu();

        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd/MM");

        String timeStr = gio.format(timeFmt);
        String dateStr = ngay.format(dateFmt);

        Map<String, Object> data = getClientAllVe(idSuatChieu);
        List<AllVeResponseDto> gheList = (List<AllVeResponseDto>) data.get("data_ve");

        // Đếm ghế
        long total = gheList.size();
        long empty = gheList.stream().filter(g -> g.getDonHang() == null).count();

        double percent = (double) empty / total;

        String status;
        if (percent > 0.7) {
            status = "🟢 Còn rất nhiều ghế trống";
        } else if (percent > 0.4) {
            status = "🟡 Còn khá nhiều ghế";
        } else if (percent > 0.1) {
            status = "🟠 Sắp hết ghế";
        } else {
            status = "🔴 Gần như hết ghế";
        }

        // Gom hàng
        Map<String, List<AllVeResponseDto>> grouped =
                gheList.stream()
                        .collect(Collectors.groupingBy(g -> g.getTenGhe().substring(0, 1)));

        List<Map<String, Object>> matrix = new ArrayList<>();

        for (var row : grouped.entrySet()) {

            Map<String, Object> rowData = new HashMap<>();
            rowData.put("row", row.getKey());

            List<Map<String, Object>> seats = row.getValue().stream()
                    .map(g -> {
                        Map<String, Object> m = new HashMap<>();
                        m.put("label", g.getTenGhe());
                        m.put("disabled", g.getDonHang() != null); // chỉ hiển thị
                        return m;
                    }).toList();

            rowData.put("seats", seats);
            matrix.add(rowData);
        }

        String message =
                "🎟️ Sơ đồ ghế\n"
                        + "━━━━━━━━━━━━━━\n"
                        + "🎬 Phim: " + tenPhim + "\n"
                        + "🕒 Suất: " + timeStr + " (" + dateStr + ")\n\n"
                        + status + " (" + empty + "/" + total + " ghế trống)";

        ChatResponse res = new ChatResponse();
        res.setType("ui");
        res.setComponent("seat_matrix");
        res.setMessage(message);
        res.setMatrix(matrix);

        return res;
    }
}
