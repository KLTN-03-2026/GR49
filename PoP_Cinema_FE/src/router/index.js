import { createRouter, createWebHistory } from "vue-router"; // cài vue-router: npm install vue-router@next --save
import checkAdmin from "./checkAdmin.js";
import checkKhachHang from "./checkkhachhang.js";
import checkkhachhang from "./checkkhachhang.js";
const routes = [
    {
        path: "/",
        component: () => import("../components/Client/TrangChu/index.vue"),
        meta: { layout: "client" },
    },
    {
        path: "/client/dang-nhap",
        component: () => import("../components/Client/DangNhap/index.vue"),
        meta: { layout: "blank" },
    },
    {
        path: "/client/dang-ky",
        component: () => import("../components/Client/DangKy/index.vue"),
        meta: { layout: "blank" },
    },
    {
        path: "/client/kich-hoat/:hash_active",
        component: () => import("../components/Client/KichHoat/index.vue"),
        meta: { layout: "client" },
        props: true,
    },
    {
        path: "/client/quen-mat-khau",
        component: () => import("../components/Client/QuenMatKhau/index.vue"),
        meta: { layout: "client" },
    },
    {
        path: "/client/dat-lai-mat-khau/:hash_reset",
        component: () => import("../components/Client/DoiMatKhau/index.vue"),
        meta: { layout: "client" },
    },
    {
        path: "/client/profile",
        component: () => import("../components/Client/Profile/index.vue"),
        meta: { layout: "client" },
        beforeEnter: checkKhachHang
    },
    {
        path: "/phim/dang-chieu",
        component: () => import("../components/Client/Phim/DangChieu/index.vue"),
        meta: { layout: "client" },
        // beforeEnter: checkKhachHang
    },
    {
        path: "/phim/sap-chieu",
        component: () => import("../components/Client/Phim/SapChieu/index.vue"),
        meta: { layout: "client" },
        // beforeEnter: checkKhachHang
    },
    {
        path: "/chi-tiet-phim/:id_phim",
        component: () => import("../components/Client/ChiTietPhim/index.vue"),
        meta: { layout: "client" },
        props: true,
    },
    {
        path: "/bai-viet",
        component: () => import("../components/Client/BaiViet/index.vue"),
        meta: { layout: "client" },
    },
    {
        path: "/chi-tiet-bai-viet/:id_bai_viet",
        component: () => import("../components/Client/ChiTietBaiViet/index.vue"),
        meta: { layout: "client" },
        props: true,
    },
    {
        path: "/about",
        component: () => import("../components/Client/About/index.vue"),
        meta: { layout: "client" },
    },
    {
        path: "/client/dat-ve/:id_suat_chieu",
        component: () => import("../components/Client/DatVe/index.vue"),
        meta: { layout: "client" },
        props: true,
        beforeEnter: checkkhachhang
    },

    {
        path: "/client/don-hang/:ma_hoa_don",
        component: () => import("../components/Client/DonHang/index.vue"),
        meta: { layout: "client" },
        props: true,
        beforeEnter: checkkhachhang
    },

    {
        path: "/thanh-toan-momo",
        component: () => import("../components/Client/ThanhToanMoMoQR/index.vue"),
        meta: { layout: "client" },

    },

    {
        path: "/cancel",
        component: () => import("../components/Client/Cancel/index.vue"),
        meta: { layout: "client" },
        // beforeEnter: checkkhachhang
    },

    {
        path: "/success",
        component: () => import("../components/Client/Success/index.vue"),
        meta: { layout: "client" },
        beforeEnter: checkkhachhang
    },

    {
        path: "/client/danh-sach-don-hang",
        component: () => import("../components/Client/DanhSachDonHang/index.vue"),
        meta: { layout: "client" },
        beforeEnter: checkkhachhang
    },
    {
        path: "/client/tich-diem-doi-qua",
        component: () => import("../components/Client/DoiQua/index.vue"),
        meta: { layout: "client" }
    },
    {
        path: "/client/danh-sach-doi-qua",
        component: () => import("../components/Client/DanhSachQua/index.vue"),
        meta: { layout: "client" },
        beforeEnter: checkkhachhang
    },
     {
        path: "/client/in-ve/:ma_hoa_don",
        component: () => import("../components/Client/InVeKhachHang/index.vue"),
        meta: { layout: "blank" },
        beforeEnter: checkkhachhang,
        props: true,
    },



    // ============= Admin ==================
    {
        path: "/admin/dang-nhap",
        component: () => import("../components/Admin/DangNhap/index.vue"),
        meta: { layout: "blank" },
    },
    {
        path: "/admin",
        component: () => import("../components/Admin/Dashboard/index.vue"),
        beforeEnter: checkAdmin
    },
    {
        path: "/profile",
        component: () => import("../components/Admin/Profile/index.vue"),
        beforeEnter: checkAdmin
    },
    {
        path: "/admin/phong-chieu",
        component: () => import("../components/Admin/PhongChieu/index.vue"),
        beforeEnter: checkAdmin,
    },
    {
        path: "/admin/dich-vu",
        component: () => import("../components/Admin/DichVu/index.vue"),
        beforeEnter: checkAdmin,
    },
    {
        path: "/admin/voucher",
        component: () => import("../components/Admin/Voucher/index.vue"),
        beforeEnter: checkAdmin,
    },
    {
        path: "/admin/nhan-vien",
        component: () => import("../components/Admin/NhanVien/index.vue"),
        beforeEnter: checkAdmin,
    },
    {
        path: "/admin/phan-quyen",
        component: () => import("../components/Admin/PhanQuyen/index.vue"),
        beforeEnter: checkAdmin,
    },
    {
        path: "/admin/khach-hang",
        component: () => import("../components/Admin/KhachHang/index.vue"),
        beforeEnter: checkAdmin,
    },
    {
        path: "/admin/phim",
        component: () => import("../components/Admin/Phim/index.vue"),
        beforeEnter: checkAdmin,
    },
    {
        path: "/admin/ve",
        component: () => import("../components/Admin/Ve/index.vue"),
        beforeEnter: checkAdmin,
    },
    {
        path: "/admin/suat-chieu",
        component: () => import("../components/Admin/SuatChieu/index.vue"),
        beforeEnter: checkAdmin,
    },
    {
        path: "/admin/ghe",
        component: () => import("../components/Admin/Ghe/index.vue"),
        beforeEnter: checkAdmin,
    },
    {
        path: "/admin/bai-viet",
        component: () => import("../components/Admin/BaiViet/index.vue"),
        beforeEnter: checkAdmin,
    },
    {
        path: "/admin/danh-sach-don-hang",
        component: () => import("../components/Admin/DonHang/index.vue"),
        beforeEnter: checkAdmin,
    },

    {
        path: "/admin/danh-sach-tich-diem",
        component: () => import("../components/Admin/LichSuTichDiem/index.vue"),
        beforeEnter: checkAdmin,
    },



    //Thống kê
    {
        path: "/admin/thong-ke/khach-hang-chi-tieu",
        component: () => import("../components/Admin/ThongKe/TKChiTieuKhachHang/index.vue"),
        beforeEnter: checkAdmin,
    },
    {
        path: "/admin/thong-ke/don-hang",
        component: () => import("../components/Admin/ThongKe/TKDonHangTheoNgay/index.vue"),
        beforeEnter: checkAdmin,
    },
    {
        path: "/admin/thong-ke/ve",
        component: () => import("../components/Admin/ThongKe/TKVe/index.vue"),
        beforeEnter: checkAdmin,
    },
    {
        path: "/admin/thong-ke/khach-hang-dang-ky",
        component: () => import("../components/Admin/ThongKe/TKKhachHangDangKy/index.vue"),
        beforeEnter: checkAdmin,
    },
    {
        path: "/admin/thong-ke/phim",
        component: () => import("../components/Admin/ThongKe/TKPhim/index.vue"),
        beforeEnter: checkAdmin,
    },
    {
        path: "/admin/thong-ke/suat-chieu",
        component: () => import("../components/Admin/ThongKe/TKSuatChieu/index.vue"),
        beforeEnter: checkAdmin,
    },
    {
        path: "/admin/thong-ke/dich-vu",
        component: () => import("../components/Admin/ThongKe/TKDichVu/index.vue"),
        beforeEnter: checkAdmin,
    },
    {
        path: "/admin/thong-ke/voucher",
        component: () => import("../components/Admin/ThongKe/TKVoucher/index.vue"),
        beforeEnter: checkAdmin,
    },
    {
        path: "/admin/in-ve/:ma_hoa_don",
        component: () => import("../components/Admin/InVe/index.vue"),
        meta: { layout: "blank" },
        beforeEnter: checkAdmin,
        props: true,
    },

    {
        path: "/admin/scan-qr",
        component: () => import("../components/Admin/ScanQR/index.vue"),
        beforeEnter: checkAdmin,
        
    },


];

const router = createRouter({
    history: createWebHistory(),
    routes: routes,
});

export default router;
