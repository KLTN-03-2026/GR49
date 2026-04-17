<template>
    <div class="card border-top border-0 border-4 border-info">
        <div class="card-body">
            <div class="row align-items-center">
                <div class="col-lg-4 col-md-12">
                    <label for="">Tìm kiếm</label>
                    <div class="input-group mt-2 mb-2">
                        <input type="text" class="form-control" placeholder="Tìm kiếm đơn đặt..."
                            v-model="searchTerm" />
                        <button class="btn btn-primary" @click="timDonHang">Tìm Kiếm</button>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <label for="">Từ ngày</label>
                    <input type="date" class="form-control mt-2 mb-2 w-100" v-model="ngayTruoc">
                </div>
                <div class="col-lg-3 col-md-6">
                    <label for="">Đến ngày</label>
                    <input type="date" class="form-control mt-2 mb-2 w-100" v-model="ngaySau">
                </div>
                <div class="col-lg-2 col-md-12">
                    <label for="">&nbsp;</label>
                    <button class="btn btn-primary w-100" @click="locDonHang">Lọc</button>
                </div>
            </div>
        </div>
    </div>
    <div class="card border-top border-0 border-4 border-success">
        <div class="card-header">
            <h5 class="mt-2">DANH SÁCH ĐƠN ĐẶT HÀNG</h5>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered table-hover">
                    <thead class="align-middle">
                        <tr class="text-center table-success">
                            <th style="width: 150px;">Mã Hóa Đơn</th>
                            <th>Tên Khách Hàng</th>
                            <th>Tổng Tiền</th>
                            <th>Giảm Giá</th>
                            <th>Tiền Thực Nhận</th>
                            <th>Ngày Đặt</th>
                            <th>Trạng thái</th>
                            <th>Phương thức thanh toán</th>
                            <th>Chi Tiết</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <template v-for="(value, index) in filteredDonHang" :key="index">
                            <tr class="align-middle">
                                <td class="align-middle text-center">{{ value.maDonHang }}</td>
                                <td class="align-middle">{{ value.khachHang.hoVaTen }}</td>
                                <td class="align-middle text-end">{{ formatVND(value.tongTien) }}</td>
                                <td class="align-middle text-end">{{ formatVND(value.giamGia) }}</td>
                                <td class="align-middle text-end">{{ formatVND(value.tienThucNhan) }}</td>
                                <td class="align-middle text-center">{{ formatDate(value.ngayDat) }}</td>
                                <td @click="doiTrangThai(value)" class="align-middle text-center" style="width: 100px;">
                                    <button v-if="value.isThanhToan == 0"
                                        class="btn btn-warning btn-sm w-100 text-light">Chờ Thanh
                                        Toán</button>
                                    <button v-if="value.isThanhToan == 1" class="btn btn-success btn-sm w-100">Đã
                                        Thanh
                                        Toán</button>

                                    <button v-if="value.isThanhToan == 2" class="btn btn-danger btn-sm w-100">Đã
                                        Huỷ</button>
                                </td>
                                <td class="align-middle text-center" v-if="value.phuongThucThanhToan == 0">
                                    Chưa xác định
                                </td>
                                <td class="align-middle text-center" v-else-if="value.phuongThucThanhToan == 1">
                                    MOMO
                                </td>
                                <td class="align-middle text-center" v-else>
                                    VNPAY
                                </td>


                                <td class="align-middle text-center">
                                    <button v-on:click="getChiTietDichVu(value.id)"
                                        class="btn btn-info w-100 btn-sm me-2 px-0" data-bs-toggle='modal'
                                        data-bs-target='#chiTietModal' style="color: white;">
                                        <i class="fas fa-eye"></i>
                                    </button>
                                </td>
                                <td class="align-middle text-center">
                                    <!-- <a target="_blank" :href="'/admin/in-ve/' + value.maDonHang"
                                        class="btn btn-secondary btn-sm pe-1 me-2">
                                        <i class="fa-solid fa-print"></i>
                                    </a> -->
                                    <a :href="value.isThanhToan == 1 ? `/admin/in-ve/${value.maDonHang}` : null"
                                        target="_blank" class="btn btn-secondary btn-sm pe-1 me-2"
                                        :class="{ 'disabled': value.isThanhToan == 0 || value.isThanhToan == 2 }"
                                        :title="value.isThanhToan == 1 ? 'In vé' : 'Chưa thanh toán, không thể in vé'">
                                        <i class="fa-solid fa-print"></i>
                                    </a>

                                    <button v-if="value.isThanhToan == 1" v-on:click="Object.assign(don_huy, value)"
                                        class="btn btn-danger btn-sm pe-1 " disabled data-bs-toggle='modal'
                                        data-bs-target='#huyModal'>
                                        <i class="fas fa-times "></i>
                                    </button>
                                    <button v-else v-on:click="Object.assign(don_huy, value)"
                                        class="btn btn-danger btn-sm pe-1 " data-bs-toggle='modal'
                                        data-bs-target='#huyModal'>
                                        <i class="fas fa-times "></i>
                                    </button>
                                </td>
                            </tr>
                        </template>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <!-- Modal xem chi tiết -->
    <div class="modal fade" id="chiTietModal" tabindex="-1" aria-labelledby="chiTietModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-xl">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="chiTietModalLabel">Chi tiết đơn hàng
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-6">
                            <div class="table-responsive">
                                <table class="table table-bordered table-stripped">
                                    <thead>
                                        <tr class="text-center">
                                            <th>#</th>
                                            <th>Dịch Vụ</th>
                                            <th>Đơn Giá</th>
                                            <th>Ghi Chú</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <template v-for="(value, index) in list_dich_vu" :key="index">
                                            <tr>
                                                <td class="text-center align-middle">{{ index + 1 }}</td>
                                                <td class="align-middle">{{ value.tenDichVu }}</td>
                                                <td class="text-center align-middle">{{ formatVND(value.gia) }}</td>
                                                <td class="align-middle">{{ value.ghiChu }}</td>
                                            </tr>
                                        </template>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <div class="table-responsive">
                                <table class="table table-bordered table-stripped">
                                    <thead>
                                        <tr class="text-center">
                                            <th>#</th>
                                            <th>Mã Vé</th>
                                            <th>Tên Ghế</th>
                                            <th>Tên Phim</th>
                                            <th>Suất Chiếu</th>
                                            <th>Giá Vé</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <template v-for="(value, index) in list_ve" :key="index">
                                            <tr>
                                                <td class="text-center align-middle">{{ index + 1 }}</td>
                                                <td class="align-middle">{{ value.maVe }}</td>
                                                <td class="align-middle">Ghế {{ value.tenGhe }}</td>
                                                <td class="text-center align-middle">{{ value.tenPhim }}</td>
                                                <td class="text-center align-middle">{{ value.ngayChieu }} - {{
                                                    value.thoiGianBatDau }} - {{ value.thoiGianKetThuc }}</td>
                                                <td class="text-center align-middle">{{ formatVND(value.giaVe) }}</td>
                                            </tr>
                                        </template>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                </div>
            </div>
        </div>
    </div>
    <!-- modal hủy đơn hàng -->
    <div class="modal fade" id="huyModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Hủy Đơn Hàng</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="alert alert-warning border-0 bg-warning alert-dismissible fade show py-2">
                        <div class="d-flex align-items-center">
                            <div class="font-35 text-dark"><i class="bx bx-info-circle"></i>
                            </div>
                            <div class="ms-3">
                                <h6 class="mb-0 text-dark">Cảnh Báo</h6>
                                <div class="text-dark">
                                    <span>Bạn muốn xóa hóa đơn đơn hàng <b>{{ don_huy.maDonHang }}</b> này</span> <br>
                                    <span>
                                        <b>Lưu ý:</b> Điều này không thể hoàn tác!
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    <button type="button" class="btn btn-danger" v-on:click="xoaDonHang()" data-bs-dismiss="modal">Xác
                        Nhận</button>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal in hóa đơn-->
    <div class="modal fade" id="inModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">In Hóa Đơn</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    .......
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Save changes</button>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import axios from 'axios';
const token = localStorage.getItem("key_admin");
export default {
    data() {
        return {
            list_don_hang: [],
            don_huy: {},
            list_dich_vu: [],
            list_ve: [],
            searchTerm: '',
            ngayTruoc: '',
            ngaySau: '',
            searchTerm: ''
        }
    },
    mounted() {
        this.getDonHang();
    },
    computed: {
        // Lọc đơn hàng theo tên (không phân biệt hoa thường)
        filteredDonHang() {
            const keyword = this.searchTerm.toLowerCase().trim();
            return this.list_don_hang.filter(don_hang =>
                don_hang.maDonHang.toLowerCase().includes(keyword)
            );
        },
    },
    methods: {
        formatVND(number) {
            return new Intl.NumberFormat("vi-VI", { style: "currency", currency: "VND" }).format(number,);
        },
        formatDate(dateStr) {
            const date = new Date(dateStr);
            return date.toLocaleString("vi-VN", {
                day: "2-digit",
                month: "2-digit",
                year: "numeric",
                hour: "2-digit",
                minute: "2-digit",
                hour12: false
            });
        },
        getDonHang() {
            axios.get('http://localhost:8080/api/admin/don-hangs/get-data', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    this.list_don_hang = res.data.data;
                });
        },
        getChiTietDichVu(id) {

            axios.get(`http://localhost:8080/api/admin/don-hangs/chi-tiet-dich-vu/${id}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {

                    this.list_dich_vu = res.data.data.data_dich_vu;
                    this.list_ve = res.data.data.ds_ve;
                });
        },


        doiTrangThai(value) {

            axios.post('http://localhost:8080/api/admin/don-hangs/update-status', value, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.getDonHang();
                    } else {
                        this.$toast.error(res.data.message);
                    }
                });
        },

        xoaDonHang() {
            axios.delete(`http://localhost:8080/api/admin/don-hangs/delete/${this.don_huy.id}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.getDonHang();
                    } else {
                        this.$toast.error(res.data.message);
                    }
                });
        },
        timDonHang() {
            console.log(this.searchTerm);
            axios.get(`http://localhost:8080/api/admin/don-hangs/search?maDonHang=${this.searchTerm}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (this.searchTerm == null || this.searchTerm == '') {
                        this.$toast.warning("Vui lòng nhập mã đơn hàng để tìm kiếm");

                    } else {

                        if (res.data.status) {
                            this.$toast.success(res.data.message);
                            this.list_don_hang = res.data.data;

                        } else {
                            this.$toast.error(res.data.message);
                        }
                    }
                });
        },
        locDonHang() {
            if (this.ngayTruoc == '' || this.ngaySau == '') {
                this.$toast.warning("Vui lòng chọn đầy đủ ngày để lọc");
                return;
            }
            axios.get(`http://localhost:8080/api/admin/don-hangs/filter?ngayTruoc=${this.ngayTruoc}&&ngaySau=${this.ngaySau}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {

                        this.$toast.success(res.data.message);
                        this.list_don_hang = res.data.data;

                    }
                });
        },


    },
}
</script>
<style>
a.disabled {
    pointer-events: none;
    opacity: 0.6;
    cursor: not-allowed;
}
</style>