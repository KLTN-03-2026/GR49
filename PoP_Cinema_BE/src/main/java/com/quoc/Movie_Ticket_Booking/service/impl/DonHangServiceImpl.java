package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.config.JwtProvide;
import com.quoc.Movie_Ticket_Booking.dto.request.ChangStatusDto;
import com.quoc.Movie_Ticket_Booking.dto.request.DatVeRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.*;
import com.quoc.Movie_Ticket_Booking.exception.ResourceNotFoundException;
import com.quoc.Movie_Ticket_Booking.model.*;
import com.quoc.Movie_Ticket_Booking.repository.*;
import com.quoc.Movie_Ticket_Booking.service.DonHangService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DonHangServiceImpl implements DonHangService {

    @Autowired
    private DonHangRepository donHangRepository;

    @Autowired
    private VeRepository veRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtProvide jwtProvider;

    @Autowired
    private DichVuRepository dichVuRepository;

    @Autowired
    private ChiTietDichVuRepository chiTietDichVuRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private TichDiemRepository tichDiemRepository;

    @Autowired
    private QuaVoucherRepository quaVoucherRepository;


    //Đặt vé
    @Override
    public Map<String, Object> Payment(DatVeRequestDto datVeRequestDto,Long idUser) {
        Map<String, Object> response = new HashMap<>();
        Users user = usersRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Khách hàng không tồn tại, id=" + idUser));


        //Lấy json ve list ben phai
        List<Long> listVeIds = datVeRequestDto.getList_ben_phai().stream()
                .filter(i -> i.getType() == 1)
                .map(DatVeRequestDto.ItemRequestDto::getIdVe)
                .toList();

        //Lấy json dich vu list ben phai
        List<Long> listDichVu = datVeRequestDto.getList_ben_phai().stream()
                .filter(i -> i.getType() == 2)
                .map(DatVeRequestDto.ItemRequestDto::getIdVe)
                .toList();


        //Lấy tiền dịch vụ
        int tienDv=0;
        for (Long id : listDichVu) {
            DichVu dv = dichVuRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dịch vụ id = " + id));


            tienDv += dv.getGia();
        }

        //Lay danh sach ve theo list id khi nhập json va don hang la null
        List<Ve> dsVe = veRepository.findByIdInAndIdDonHangs(listVeIds);

        //kiểm tra vé còn trống
        if (dsVe.size() != listVeIds.size()) {
            response.put("status", false);
            response.put(  "message", "Vé của bạn đã có người khác mua rồi!");

            return response;
        }

        // Tính tong tiền
        int tienVe = dsVe.stream().mapToInt(Ve::getGiaVe).sum();
        int tongTien = tienVe +tienDv ;
        int tienGiamGia = 0;

        //áp dụng voucher
        Voucher voucher = null;
        String maCode = datVeRequestDto.getMaCode();
        QuaVoucher quaVoucher = null;

        if(maCode != null && !maCode.trim().isEmpty()) {

            LocalDate now = LocalDate.now();

            // ===== 1. Kiểm tra voucher đổi quà (KHÔNG lọc trạng thái) =====
            Optional<QuaVoucher> optQuaVoucher =
                    quaVoucherRepository.findByMaCodeAndKhachHangId(maCode, user.getId());

            if (optQuaVoucher.isPresent()) {

                quaVoucher = optQuaVoucher.get();

                if (quaVoucher.getTinhTrang() == QuaVoucher.DA_SU_DUNG) {
                    response.put("status", false);
                    response.put("message", "Voucher này đã được sử dụng");
                    return response;
                }

                Voucher v = quaVoucher.getVoucher();

                if (v.getTinhTrang() != 1 ||
                        v.getThoiGianBatDau().isAfter(now) ||
                        v.getThoiGianKetThuc().isBefore(now)) {

                    response.put("status", false);
                    response.put("message", "Voucher đã hết hạn hoặc không còn hiệu lực");
                    return response;
                }

                voucher = v;

            } else {
                // 2. Voucher thường
                voucher = findValidVoucher(maCode);
                if (voucher == null) {
                    response.put("status", false);
                    response.put("message", "Voucher không tồn tại hoặc đã hết hạn");
                    return response;
                }
            }

            // 3. Tính giảm giá
            if (voucher != null && tongTien >= voucher.getSoTienToiDa()) {

                tienGiamGia = tongTien * voucher.getSoGiamGia();

                if (tienGiamGia > voucher.getSoTienGiamGia()) {

                    tienGiamGia = voucher.getSoTienGiamGia();
                }
            }
        }
//        else {
//                response.put("status", true);
//                response.put("message", "Không áp dụng voucher");
//
//            }

        // Tạo đơn hàng
        DonHang donHang= createDonHang(tongTien,tienGiamGia,user,voucher);

        // Tạo tích điểm
         createTichDiem(donHang);

        // Cập nhật hoá đơn vào vé
        dsVe.forEach(ve -> {
            ve.setDonHang(donHang);

        });
        veRepository.saveAll(dsVe);

        // Lưu chi tiết dịch vụ
        for (Long id : listDichVu) {
            createChiTietDichVu(id, donHang);
        }

        response.put("status", true);
        response.put( "message", "Đã tạo đơn hàng thành công , vui lòng thanh toán!");
        response.put("ma_hoa_don",donHang.getMaDonHang());


        return response;
    }


    private void createChiTietDichVu(Long id,DonHang donHang) {
        DichVu dv = dichVuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dịch vụ id = " + id));

        ChiTietDichVu ctdv = new ChiTietDichVu();
        ctdv.setDonHang(donHang);
        ctdv.setDichVu(dv);
        ctdv.setDonGia(dv.getGia());
        ctdv.setGhiChu("");
        chiTietDichVuRepository.save(ctdv);

    }

    private DonHang createDonHang(int tongTien,int tienGiamGia,Users userId,Voucher voucher) {

        DonHang donHang = new DonHang();
        donHang.setKhachHang(userId);
        String shortId = generateNumberRicId(8);
        donHang.setMaDonHang("DH"+shortId);
        donHang.setNgayDat(LocalDateTime.now());
        donHang.setTienThucNhan(tongTien - tienGiamGia);
        donHang.setTongTien(tongTien);
        donHang.setGiamGia(tienGiamGia);
        donHang.setIsThanhToan(0);
        donHang.setPhuongThucThanhToan(0);
        donHang.setVoucher(voucher);
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

    //=== Voucher thường ===
    private Voucher findValidVoucher(String macode){
        LocalDate now= LocalDate.now();
        Optional<Voucher> validVoucher = voucherRepository.findValidVoucher(macode, now);
        return validVoucher.orElse(null);
    }


    //=== Quà voucher ===
    private QuaVoucher findValidQuaVoucher(String macode,Users user){

        Optional<QuaVoucher> quaVoucherOpt =
                quaVoucherRepository.findByMaCodeAndKhachHangId(macode,user.getId());

        return quaVoucherOpt.orElse(null);
    }




   //Xoá đơn hàng
    @Transactional
    @Override
    public ApiResponse<?> deleteDonHangById(Long idDonHang,NhanVien nhanVienId) {

        long idChucNang = 12;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        Optional<DonHang> optionalDonHang = donHangRepository.findByIdAndIsThanhToanIn(idDonHang,  Arrays.asList(0, 2));
        if (optionalDonHang.isEmpty()) {
            throw new ResourceNotFoundException("Đơn hàng không tồn tại hoặc đã thanh toán, id = " + idDonHang);
        }

        DonHang donHang= optionalDonHang.get();
        // Xoá chi tiết dịch vụ
            deleteChitietDichVu(donHang.getId());

        //Lưu tất cả các vé theo mã don hàng là null khi huỷ đơn hàng
            List<Ve> ves = veRepository.findByDonHangId(idDonHang);
            for (Ve ve : ves) {
                ve.setDonHang(null);
                ve.setTinhTrang(1);
                veRepository.save(ve);
            }
            donHangRepository.delete(donHang);

        return ApiResponse.success("Xoá đơn hàng thành công");

        }


    private void deleteChitietDichVu(Long idDonHang) {
//        List<ChiTietDichVu> byDonHangId = chiTietDichVuRepository.findByDonHangId(idDonHang);
        chiTietDichVuRepository.deleteByDonHangId(idDonHang);
    }


    //Hiển thị thông tin đơn hàng cho vé in
    @Override
    public Map<String, Object> getByHoaDon(String maDonHang,NhanVien nhanVienId) {
        Map<String, Object> response = new HashMap<>();

        long idChucNang = 12;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            response.put("status",false);
            response.put("message", "Bạn không có quyền thực hiện chức năng này!");
            return response;
        }

        List<DonHangResponseDto> dataList = donHangRepository.getDonHangByMaDonHang(maDonHang);
        //Lấy 1 hoá đơn duy nhất nếu có nhiều vé
        DonHangResponseDto data = dataList.isEmpty() ? null : dataList.get(0);

        List<AllVeResponseDto> ds_ve = donHangRepository.getVeByMaDonHang(maDonHang);
        List<ChiTietDichVuResponseDto> dsDichVu = chiTietDichVuRepository.getDichVuByMaDonHang(maDonHang);


        response.put("status", true);
        response.put("data", data);
        response.put("ds_ve", ds_ve);
        response.put("ds_dv", dsDichVu);
        return response;
    }


    //Hiển thị đơn hàng cho khách hanàng
    @Override
    public Map<String, Object> getByHoaDonForUser(String maDonHang,Long userById) {
        Map<String, Object> response = new HashMap<>();

        List<DonHangDetailsResponseDto> dataList = donHangRepository.getDonHangForUser(maDonHang,userById);
        //Lấy 1 hoá đơn duy nhất nếu có nhiều vé
        DonHangDetailsResponseDto data = dataList.isEmpty() ? null : dataList.get(0);

        List<VeDetailsResponseDto> ds_ve = donHangRepository.getVeByMaDonHangForUser(maDonHang);
        List<ChiTietDichVuResponseDto> ds_dichVu = donHangRepository.getDichVuForUserByMaDonHang(maDonHang);
        response.put("status", true);
        response.put("data", data);
        response.put("ds_ve", ds_ve);
        response.put("ds_dichVu", ds_dichVu);
        return response;
    }

    //HUỷ đơn hàng
    @Override
    public DonHang cancelOder(String maDonHang) {

        DonHang byMaDonHang = donHangRepository.findByMaDonHang(maDonHang);
        if (byMaDonHang == null) {
            throw new ResourceNotFoundException("Don hang not found ma don hang "+maDonHang);
        }
       byMaDonHang.setIsThanhToan(2);
        byMaDonHang.setPhuongThucThanhToan(3);
        donHangRepository.save(byMaDonHang);

        //Lưu tất cả trạng thái vé theo mã don hàng khi huỷ order
        List<Ve> ve= veRepository.findByDonHangId(byMaDonHang.getId());
          for (Ve ve1 : ve) {
              ve1.setTinhTrang(0);
              veRepository.save(ve1);
          }
        return byMaDonHang;
    }

    @Override
    public List<DonHang> getAllDonHang() {
        return donHangRepository.findAll();
    }


    //Tìm đơn hàng
    @Override
    public Map<String, Object> searchDonHang(String maDonHang) {
       Map<String,Object> response = new HashMap<>();
        List<DonHang> donHangList = null;
        if (maDonHang != null && maDonHang.length()>0) {
            donHangList=donHangRepository.searchDonHangByMaDonHang(maDonHang);

            if (ObjectUtils.isEmpty(donHangList)) {
                response.put("status", false);
                response.put("message", "Không tìm thấy đơn hàng.");
            } else {
                response.put("status", true);
                response.put("data", donHangList);
                response.put("message", "Tìm thấy đơn hàng thành công.");
            }

        }else{
            donHangList= donHangRepository.findAll();
            response.put("status", false );
            response.put("data",donHangList);
            response.put("message", "Danh sách tất cả đơn hàng.");

        }


        return response;
    }


    //Cập nhật trang thái đơn hàng
    @Override
    public ApiResponse<?>  updateStatus(ChangStatusDto dto,NhanVien nhanVienId) {

        long idChucNang = 12;
        long idChucVu = nhanVienId.getChucVu().getId();

        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        Optional<DonHang> donHangById = donHangRepository.findById(dto.getId());
        DonHang donHang = donHangById.get();
        Integer curretnStatus = donHang.getIsThanhToan();

        if(Objects.equals(curretnStatus,DonHang.DA_THANH_TOAN)){ //1
            donHang.setIsThanhToan(DonHang.DA_HUY); //2
        }else if(Objects.equals(curretnStatus,DonHang.CHUA_THANH_TOAN)){ //0
            donHang.setIsThanhToan(DonHang.DA_THANH_TOAN); //1
        }else{
            donHang.setIsThanhToan(DonHang.CHUA_THANH_TOAN); //0
        }
        donHangRepository.save(donHang);
        return ApiResponse.success("Cập nhật trạng thái thành công");
    }


    //lọc đơn hàng theo ngày
    @Override
    public List<DonHang> filterDonHang(LocalDateTime ngayTruoc, LocalDateTime NgaySau) {

        List<DonHang> donHangList = donHangRepository.filterDonHangByNgayDat(ngayTruoc, NgaySau);
        return donHangList;
    }


    @Override
    public DonHang getDonHangById(Long id) {
        Optional<DonHang> otp = donHangRepository.findById(id);
        if(otp.isEmpty()){
            throw new ResourceNotFoundException("Don hang not found id "+id);
        }
        return otp.get();
    }


    //Hiển thị lịch sử đơn haàng của khách hanng
    @Override
    public Map<String, Object> getHistoryDonHangByUser(Long userById) {
        Map<String,Object> response = new HashMap<>();

        List<DonHangDetailsResponseDto> donHangList = donHangRepository.getHistoryDonHang(userById,1);

        // Lây list id đơn hàng
        List<Long> listIdDonHang = donHangList.stream()
                .map(DonHangDetailsResponseDto::getId)
                .collect(Collectors.toList());


//            System.out.println("id don hang: "+listIdDonHang);
//        Lấy danh sách vé theo list đơn hàng
            List<VeDetailsResponseDto> ds_ve = donHangRepository.getVeByListIdDonHang(listIdDonHang);



        response.put("status", true);
        response.put("don_hang", donHangList);
        response.put("dsVe", ds_ve);

        return response;
    }


    @Override
    public Long countLichSuDonHang(Long userId, int days) {
        LocalDateTime fromDate = LocalDate.now().minusDays(days).atStartOfDay();
        return donHangRepository.countLichSuDonHang(userId, fromDate);
    }
}



