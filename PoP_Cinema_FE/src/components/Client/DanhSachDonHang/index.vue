<template>
    <div class="container-fluid">
        <div class="row">
            <div class="col-12 mt-4">
                <h3 class="text-center">Danh Sách Đơn Hàng</h3>
                <div class="card border-top border-0 border-4 border-dark">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered">
                                <thead class="align-middle">
                                    <tr class="text-center">
                                        <th>STT</th>
                                        <th>Mã Đơn Hàng</th>
                                        <th>Phim</th>
                                        <th>Tên Phim</th>
                                        <th>Ghế</th>
                                        <th>Ngày Đặt</th>
                                        <th>Tổng Tiền</th>
                                        <th>Trạng Thái</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <template v-for="(value, index) in list_don_hang" :key="index">
                                        <tr class="text-nowrap">
                                            <td class="text-center align-middle">{{ index + 1 }}</td>
                                            <td class="text-center align-middle">{{ value.maDonHang }}</td>
                                            <td class="text-center align-middle">
                                                <img :src="value.hinhAnh" alt="Poster" class="me-3 rounded"
                                                    style="width: 120px; height: 180px; object-fit: cover;">
                                            </td>
                                            <td class="text-center align-middle">{{ value.tenPhim }}
                                            </td>

                                            <td class="text-center align-middle">
                                                <template
                                                    v-if="(ves = ds_ve.filter(v => v.idDonHang === value.id)).length > 0"
                                                    :key="i">
                                                    <p class="mb-0">
                                                        Suất chiếu: <strong>{{ ves[0].ngayChieu }} - {{
                                                            ves[0].thoiGianBatDau }} - {{ ves[0].thoiGianKetThuc
                                                            }}</strong><br />
                                                        Ghế: <strong>[{{ves.map(item => item.tenGhe).join(', ')
                                                        }}]</strong>

                                                    </p>
                                                </template>
                                            </td>

                                            <td class="text-center align-middle">{{ value.ngayDat }}
                                            </td>
                                            <td class="text-center align-middle">{{ formatVND(value.tongTien) }}</td>

                                            <td class="align-middle text-center" style="width: 100px;">
                                                <button v-if="value.isThanhToan == 0"
                                                    class="btn btn-warning btn-sm w-100 text-light">Chờ Thanh
                                                    Toán</button>
                                                <button v-else class="btn btn-success btn-sm w-100">Đã Thanh
                                                    Toán</button>
                                            </td>
                                            <td class="align-middle text-center">
                                                <div class="d-flex justify-content-center gap-1">
                                                    <button v-on:click="getChiTietDichVu(value.id)"
                                                        class="btn btn-info btn-sm" data-bs-toggle='modal'
                                                        data-bs-target='#chiTietModal' style="color: white;">
                                                        Chi Tiết
                                                    </button>
                                                    <a target="_blank" :href="'/client/in-ve/' + value.maDonHang"
                                                        class="btn btn-secondary btn-sm">
                                                        <i class="fa-solid fa-print"></i>
                                                    </a>
                                                </div>
                                            </td>
                                            <!-- <td class="align-middle text-center">
                                                <a target="_blank" :href="'/client/in-ve/' + value.maDonHang"
                                                    class="btn btn-secondary btn-sm pe-1 me-2">
                                                    <i class="fa-solid fa-print"></i>
                                                </a>
                                            </td> -->
                                        </tr>
                                    </template>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
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
</template>
<script>
import axios from 'axios';
const token = localStorage.getItem("key_client");
export default {
    data() {
        return {
            list_don_hang: [],
            list_dich_vu: [],
            list_ve: [],
        }
    },
    mounted() {
        this.getDonHang();
    },
    methods: {
        getDonHang() {


            axios.get(`http://localhost:8080/api/client/don-hangs/history`, {
                headers: {
                    Authorization: "Bearer " + token
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.list_don_hang = res.data.don_hang;

                        this.ds_ve = res.data.dsVe;

                    }
                })
                .catch((err) => {
                    console.error("Error:", err.response);
                });
        },

        getChiTietDichVu(id) {

            axios.get(`http://localhost:8080/api/client/don-hangs/chi-tiet-dich-vu/${id}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {

                    this.list_dich_vu = res.data.data.data_dich_vu;
                    this.list_ve = res.data.data.ds_ve;
                });
        },

        formatDateTime(datetimeStr) {
            const [datePart, timePart] = datetimeStr.split(' ');
            const [year, month, day] = datePart.split('-');
            return `${timePart} ${day}-${month}-${year}`;
        },
        formatVND(number) {
            return new Intl.NumberFormat("vi-VI", { style: "currency", currency: "VND" }).format(number,);
        },
    },
}
</script>
<style></style>