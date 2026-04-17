package com.quoc.Movie_Ticket_Booking.service.impl;

import com.quoc.Movie_Ticket_Booking.dto.request.VoucherRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.*;
import com.quoc.Movie_Ticket_Booking.exception.ResourceAlreadyExistsException;
import com.quoc.Movie_Ticket_Booking.exception.ResourceNotFoundException;
import com.quoc.Movie_Ticket_Booking.model.*;
import com.quoc.Movie_Ticket_Booking.repository.*;
import com.quoc.Movie_Ticket_Booking.service.PhanQuyenService;
import com.quoc.Movie_Ticket_Booking.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private DonHangRepository donHangRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private QuaVoucherRepository quaVoucherRepository;

    @Autowired
    private TichDiemRepository tichDiemRepository;

    @Autowired
    private HangThanhVienRepository hangThanhVienRepository;

    @Override
    public ApiResponse<?> createVoucher(VoucherRequestDto voucherRequestDto, NhanVien nhanVienId) {
        long idChucNang = 10;
        long idChucVu = nhanVienId.getChucVu().getId();
        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }


        Boolean existsByMaCode = voucherRepository.existsByMaCode(voucherRequestDto.getMaCode());
        if (existsByMaCode) {
            throw new ResourceAlreadyExistsException("Voucher có mã code '" + voucherRequestDto.getMaCode()+ "' đã tồn tại.");
        }
        Voucher voucher = new Voucher();
        voucher.setMaCode(voucherRequestDto.getMaCode());
        voucher.setTenVoucher(voucherRequestDto.getTenVoucher());
        voucher.setThoiGianBatDau(voucherRequestDto.getThoiGianBatDau());
        voucher.setThoiGianKetThuc(voucherRequestDto.getThoiGianKetThuc());
        voucher.setSoGiamGia(voucherRequestDto.getSoGiamGia());
        voucher.setSoTienToiDa(voucherRequestDto.getSoTienToiDa());
        voucher.setSoTienGiamGia(voucherRequestDto.getSoTienGiamGia());

        voucherRepository.save(voucher);
        return ApiResponse.success("Thêm voucher thành công!");
    }

    @Override
    public ApiResponse<?> updateVoucher(Long id, VoucherRequestDto updateDto,NhanVien nhanVienId) {

        long idChucNang = 10;
        long idChucVu = nhanVienId.getChucVu().getId();
        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

       Voucher voucher = getVoucherById(id);

        if (updateDto.getMaCode()!= null) {
            voucher.setMaCode(updateDto.getMaCode());
        }
        if (updateDto.getTenVoucher()!= null) {
            voucher.setTenVoucher(updateDto.getTenVoucher());
        }
        if (updateDto.getThoiGianBatDau()!= null) {
            voucher.setThoiGianBatDau(updateDto.getThoiGianBatDau());
        }
        if (updateDto.getThoiGianKetThuc()!= null) {
            voucher.setThoiGianKetThuc(updateDto.getThoiGianKetThuc());
        }
        if (updateDto.getSoGiamGia()!= null) {
            voucher.setSoGiamGia(updateDto.getSoGiamGia());
        }
        if (updateDto.getSoTienToiDa()!= null) {
            voucher.setSoTienToiDa(updateDto.getSoTienToiDa());
        }
        if (updateDto.getSoTienGiamGia()!= null) {
            voucher.setSoTienGiamGia(updateDto.getSoTienGiamGia());
        }
        if (updateDto.getTinhTrang()!= null) {
            voucher.setTinhTrang(updateDto.getTinhTrang());
        }
         voucherRepository.save(voucher);
        return ApiResponse.success("Cập nhật voucher thành công!");
    }

    @Override
    public ApiResponse<?> deleteVoucher(Long id,NhanVien nhanVienId) {

        long idChucNang = 10;
        long idChucVu = nhanVienId.getChucVu().getId();
        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }


        Voucher voucherById = getVoucherById(id);

        // ✅ Kiểm tra ràng buộc: voucher đã được sử dụng trong đơn hàng chưa
        boolean daSuDung = donHangRepository.existsByVoucherId(id);
        if (daSuDung) {
            return ApiResponse.fail("Không thể xóa voucher này vì đã được sử dụng trong đơn hàng!");
        }

        voucherRepository.delete(voucherById);
        return ApiResponse.success("Xóa voucher thành công!");
    }

    @Override
    public ApiResponse<?> updateStatus(Long id,NhanVien nhanVienId) {

        long idChucNang = 10;
        long idChucVu = nhanVienId.getChucVu().getId();
        //Kiểm tra quyền
        if(!permissionService.checkQuyen(idChucNang,idChucVu)){
            return ApiResponse.fail("Bạn không có quyền thực hiện chức năng này!");
        }

        Voucher voucherById = getVoucherById(id);
        voucherById.setTinhTrang(voucherById.getTinhTrang()==1 ? 0 : 1);
        voucherRepository.save(voucherById);
        return ApiResponse.success("Cập nhật trạng thái thành công!");
    }

    @Override
    public List<VoucherResponseDto> getAllVoucher() {
        List<Voucher> dsVoucher = voucherRepository.findAll();
        List<VoucherResponseDto> voucherDtoList = dsVoucher.stream()
                .map(this::mapToVoucherDto)
                .toList();
        return voucherDtoList;
    }

    @Override
    public Voucher getVoucherById(Long id) {
        Optional<Voucher> otp = voucherRepository.findById(id);
        if(otp.isEmpty()){
            throw new ResourceNotFoundException("Voucher not found id "+id);
        }
        return otp.get();
    }

    private VoucherResponseDto mapToVoucherDto(Voucher voucher) {
        return new VoucherResponseDto (
                voucher.getId(),
                voucher.getMaCode(),
                voucher.getTenVoucher(),
                voucher.getHinhAnh(),
                voucher.getMoTa(),
                voucher.getThoiGianBatDau(),
                voucher.getThoiGianKetThuc(),
                voucher.getSoGiamGia(),
                voucher.getSoTienToiDa(),
                voucher.getSoTienGiamGia(),
                voucher.getTinhTrang(),
                voucher.getSoDiem()
        );
    }

    @Override
    public ApiResponse<?> findVoucherByMaCode(String maCode) {

        LocalDate now= LocalDate.now();
        Optional<Voucher> validVoucher = voucherRepository.findValidVoucher(maCode, now);

        if (validVoucher.isPresent()) {

            return ApiResponse.success( "Đã áp mã voucher thành công!",validVoucher.get());

        }else {

            return ApiResponse.fail("Voucher đã hết hạn hoặc không đúng!");

        }


    }




    @Override
    public List<Voucher> getVoucherByTinhTrang() {
        return voucherRepository.findByTinhTrang(1);
    }


    @Override
    public ApiResponse<?> doiQuaVoucher(Long voucherId, Long userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User không tồn tại"));

        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new ResourceNotFoundException("Voucher không tồn tại"));

        int diemHienTai = user.getDiem();
        int diemCanDoi = voucher.getSoDiem();

        // 0. Kiểm tra user đã đổi voucher này chưa (CHỈ CHO ĐỔI 1 LẦN)
        boolean daDoi = quaVoucherRepository.existsByKhachHangIdAndVoucherId(userId, voucherId);
        if (daDoi) {
            return ApiResponse.fail("Bạn đã đổi voucher này rồi!");
        }

        // 1. Kiểm tra đủ điểm
        if (diemHienTai < diemCanDoi) {
            return ApiResponse.fail( "Không đủ điểm để đổi voucher");
        }

        // 2. Trừ điểm
        int tongDiemMoi = diemHienTai - diemCanDoi;
        user.setDiem(tongDiemMoi);
        usersRepository.save(user);


        // 👉 Cập nhật lại hạng thành viên
        capNhatHangThanhVien(user);

        // 3. Lưu lịch sử trừ điểm vào bảng TichDiem
        TichDiem ls = new TichDiem();
        ls.setKhachHang(user);
        ls.setDonHang(null); // đổi voucher không từ đơn hàng
        ls.setDiem(diemCanDoi); // điểm âm
        ls.setLoai(2);
        ls.setMoTa("Đổi voucher: " + voucher.getTenVoucher());
        ls.setTinhTrang(TichDiem.DA_TRU);
        ls.setCreatedAt(LocalDateTime.now());
        ls.setTongDiem(tongDiemMoi);

        tichDiemRepository.save(ls);

        // 4.   Lưu quà voucher đc đổi
        QuaVoucher quaVoucher = new QuaVoucher();
        quaVoucher.setVoucher(voucher);
        quaVoucher.setKhachHang(user);
        quaVoucher.setMaCode(voucher.getMaCode());
        quaVoucher.setNgayNhan(LocalDateTime.now());
        quaVoucher.setTinhTrang(QuaVoucher.CHUA_SU_DUNG);


        quaVoucherRepository.save(quaVoucher);
        return ApiResponse.success( "Đổi Voucher thành công!");
    }

    // cập nhật hạng thành viên
    private void capNhatHangThanhVien(Users user) {
        int diem = user.getDiem() == null ? 0 : user.getDiem();

        // Lấy danh sách hạng, sắp xếp theo điểm tăng dần
        List<HangThanhVien> hangList = hangThanhVienRepository.findAllByOrderByDiemToiThieuAsc();

        HangThanhVien hangMoi = hangList.get(0); // mặc định hạng thấp nhất

        for (HangThanhVien h : hangList) {
            if (diem >= h.getDiemToiThieu()) {
                hangMoi = h; // chọn hạng cao nhất user đạt được
            }
        }

        // Nếu khác hạng hiện tại -> cập nhật
        if (user.getHangThanhVien() == null ||
                !user.getHangThanhVien().getId().equals(hangMoi.getId())) {

            user.setHangThanhVien(hangMoi);
            usersRepository.save(user);
        }
    }

    @Override
    public ApiResponse<?> findQuaVoucherByMaCode(String maCode,Long userId) {
        LocalDate now= LocalDate.now();
        Optional<QuaVoucher> validVoucher = quaVoucherRepository.findValidQuaVoucher(maCode,userId,now);

        if (validVoucher.isPresent()) {

            return ApiResponse.success( "Đã áp mã voucher thành công!",validVoucher.get());

        }else {

            return ApiResponse.fail("Voucher không tồn tại, đã hết hạn hoặc đã sử dụng!");

        }
    }

    @Override
    public List<QuaVoucherResponseDto> getAllQuaVoucher() {
        List<Voucher> dsVoucher = voucherRepository.findByTinhTrang(1);
        List<QuaVoucherResponseDto> voucherDtoList = dsVoucher.stream()
                .map(this::mapToQuaVoucherDto)
                .toList();
        return voucherDtoList;
    }

    private QuaVoucherResponseDto mapToQuaVoucherDto(Voucher voucher) {
        return new QuaVoucherResponseDto (
                voucher.getId(),
                voucher.getTenVoucher(),
                voucher.getThoiGianBatDau(),
                voucher.getThoiGianKetThuc(),
                voucher.getSoTienGiamGia(),
                voucher.getSoDiem(),
                voucher.getTinhTrang()
        );
    }

    @Override
    public ApiResponse<?> getLichSuQuaVoucher(Long userId) {
        List<LichSuQuaVoucherResponseDto> lichSuQuaVoucher = quaVoucherRepository.getLichSuQuaVoucher(userId);
        return ApiResponse.success("Hiển thị thành công",lichSuQuaVoucher);
    }
}

