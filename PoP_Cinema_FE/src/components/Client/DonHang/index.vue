<template>
    <!-- Main Content -->

    <div class="container py-5">
        <!-- <template v-for="(item, index) in data" :key="index"> -->
        <!-- Order Header -->
        <div class="card mb-4">
            <div class="card-body d-flex justify-content-between align-items-start">

                <div>
                    <h2 class="h3">Chi tiết đơn hàng</h2>
                    <p class="text-muted">Mã đơn hàng: {{ data.maDonHang }}</p>
                </div>
                <div class="text-end d-flex flex-column align-items-end">
                    <spanc
                        class="rounded-pill fs-6 text-dark text-capitalize px-3 py-2 rounded d-flex justify-center align-items-center gap-2"
                        style="background-color: rgba(134, 239, 172, 0.5);">
                        <i class="fa-solid fa-circle-check fs-5"></i>
                        đặt hàng thành công
                    </spanc>
                    <p class="text-muted mt-2">{{ data.ngayDat }}</p>
                </div>
            </div>
            <div class="card-footer d-flex gap-2">
                <!-- Xem vé -->
                <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
                    <i class="fa-solid fa-ticket me-2"></i>View Tickets
                </button>
                <!-- hủy đơn -->
                <button v-on:click="cancelOder()" class="btn btn-danger">
                    <i class="fas fa-times me-2"></i>Hủy đặt vé
                </button>
                <!-- tải về máy -->
                <!-- <button class="btn btn-outline-secondary">
                    <i class="fas fa-download me-2"></i>Download Receipt
                </button> -->
            </div>
        </div>

        <div class="row g-4">
            <!-- Left Column -->
            <div class="col-lg-8">
                <!-- Movie Details -->
                <div class="card mb-4">
                    <div class="card-body">
                        <h5 class="card-title">Chi tiết phim</h5>
                        <div class="d-flex gap-3">
                            <!-- ảnh phim -->
                            <img class="img-thumbnail" :src="data.hinhAnh" style="width: 18rem; height: 14rem;">
                            <div>
                                <h4>{{ data.tenPhim || 'Sample Movie' }}</h4>
                                <ul class="list-unstyled text-muted d-flex flex-column gap-2">
                                    <li><i class="fas fa-clock me-2"></i>{{ data.thoiLuong || 'N/A' }}</li>
                                    <li><i class="fas fa-tag me-2"></i>{{ data.theLoai || 'N/A' }}</li>
                                    <li><i class="fas fa-star me-2 text-warning"></i>{{ data.danhGia || 'N/A' }}</li>
                                </ul>
                                <div class="mt-3">
                                    <strong>Nội dung</strong>
                                    <p class="small text-muted mt-2">{{ data.noiDung || 'N/A' }}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Seat Selection -->
                <div class="card mb-4">
                    <div class="card-body">
                        <h6>Ghế của bạn</h6>
                        <div class="d-flex flex-wrap gap-2">
                            <span class="badge rounded-pill bg-danger-subtle text-danger px-3 py-2"
                                style="background-color: rgba(252, 165, 165, 0.5); font-size: 13px;"
                                v-for="(item, index) in ds_ve" :key="index">
                                Ghế {{ item.tenGhe || 'N/A' }}
                            </span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Right Column -->
            <div class="col-lg-4">
                <!-- Payment Summary -->
                <div class="card mb-4">
                    <div class="card-body">
                        <h5 class="fw-semibold mt-3 text-dark">Chi Tiết Thanh Toán</h5>
                        <!-- <div class="d-flex justify-content-between mb-2">
                            <span class="text-muted">ghế người lớn</span>
                            <span class="fw-medium" >80k</span>
                        </div> -->
                        <div class="d-flex justify-content-between">
                            <span class="text-muted">Số lượng vé</span>
                            <span class="fw-medium">{{ ds_ve.length }}</span>
                        </div>
                        <hr>
                        <div class="d-flex justify-content-between mb-2">
                            <span class="text-muted">Tổng vé</span>
                            <span>{{ formatVND(ds_ve.reduce((total, item) => total + item.giaVe, 0)) }}</span> 
                        </div>
                        <div class="d-flex justify-content-between mb-2">
                            <span class="text-muted">Dịch vụ</span>
                            <span>{{ formatVND(ds_dv.reduce((total, item) => total + item.gia, 0))  }}</span>
                        </div>

                        <div class="d-flex justify-content-between mb-2">
                            <span class="text-muted">VAT (8%):</span>
                            <span> {{ formatVND(data.tienThucNhan * 0.08) }} </span>
                        </div>
                        <div class="d-flex justify-content-between mb-2">
                            <span>Giảm giá</span>
                            <span>{{ data.giamGia }}%</span>
                        </div>

                        <div class="d-flex justify-content-between fw-bold fs-6">
                            <span>Tổng tiền</span>
                            <span class="text-success">{{ formatVND(data.tienThucNhan) }}</span>
                        </div>
                        <hr>

                        <h6 class="fw-semibold mt-3 text-dark">Phương thức thanh toán</h6>
                    
                        <div class="form-check mt-2">
                            <input class="form-check-input" type="radio" name="pttt" id="momo" value="momo"
                                v-model="phuongThucThanhToan">
                            <label class="form-check-label" for="momo">
                                <img src="https://img.icons8.com/color/48/000000/bank-cards.png" width="24"
                                    class="me-1" />
                                Internet Banking (MoMo)
                            </label>
                        </div>
                        <div class="form-check mt-2">
                            <input class="form-check-input" type="radio" name="pttt" id="vnpay" value="vnpay"
                                v-model="phuongThucThanhToan">
                            <label class="form-check-label" for="vnpay  ">
                                <img src="https://img.icons8.com/color/48/000000/bank-cards.png" width="24"
                                    class="me-1" />
                                Internet Banking (VNPAY)
                            </label>
                        </div>
                        <div class="form-check mt-2">
                            <input class="form-check-input" type="radio" name="pttt" id="payos" value="payos"
                                v-model="phuongThucThanhToan">
                            <label class="form-check-label" for="payos">
                                <img src="https://img.icons8.com/color/48/000000/bank-cards.png" width="24"
                                    class="me-1" />
                                Internet Banking (PayOS)
                            </label>
                        </div>
                        <div class="d-flex justify-content-end">
                            <button type="button" class="mt-3 btn btn-secondary px-8" @click="thanhToan()">Tiếp
                                tục</button>
                        </div>
                    </div>
                </div>


                <!-- Customer Information -->
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Thông tin khách hàng</h5>
                        <ul class="list-unstyled text-muted d-flex flex-column gap-3 m-0">
                            <li><i class="fas fa-user me-2"></i>{{ data.hoVaTen }}</li>
                            <li><i class="fas fa-envelope me-2"></i>{{ data.email }}</li>
                            <li><i class="fas fa-phone me-2"></i>{{ data.soDienThoai }}</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- </template> -->
    </div>

    <!-- Tickets Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Ticket</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="card rounded mb-3">
                        <div class="card-body">
                            <div class="d-flex justify-content-between">
                                <div>
                                    <template v-for="(item, index) in ds_ve" :key="index">
                                        <h6 class="mb-1">{{ item.tenPhim }}</h6>
                                        <small class="text-muted">CinemaHub Downtown - Rạp số 1</small><br>
                                        <small class="text-muted">{{ formatDate(item.ngayChieu) }} at {{
                                            item.thoiGianBatDau }}- {{
                                                item.thoiGianKetThuc }}</small>
                                        <p class="text-danger fw-semibold mt-2 mb-0">Ghế {{ item.tenGhe }}</p>
                                    </template>
                                </div>
                                <div class="text-end">
                                    <div class="bg-light p-2 rounded">
                                        <i class="fas fa-qrcode fs-3"></i>
                                    </div>
                                    <small class="text-muted d-block mt-1">Scan at entry</small>
                                </div>
                            </div>
                        </div>
                    </div>
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
const token = localStorage.getItem("key_client");
export default {
    props: ["ma_hoa_don"],
    data() {
        return {
            ma_hoa_don: this.$route.params.ma_hoa_don,
            data: {},
            ds_ve: [],
            ds_dv: [],
            phuongThucThanhToan: ''
        }
    },
    computed: {

    },
    mounted() {

        this.getDonHang();

        //De tat modal khi load trang
        document.querySelectorAll('.modal-backdrop').forEach(el => el.remove());
        document.body.classList.remove('modal-open');
        document.body.style = '';
    },
    methods: {

        formatVND(number) {
            return new Intl.NumberFormat("vi-VI", { style: "currency", currency: "VND" }).format(number,);
        },
        formatTime(time) {
            return time.slice(0, 5); // Convert HH:MM:SS to HH:MM
        },
        formatDate(date) {
            const d = new Date(date);
            return `${d.getDate()}/${d.getMonth() + 1}/${d.getFullYear()}`;
        },
        getDonHang() {
            // var payload = {
            //     id: this.id_phim
            // }
            console.log(this.ma_hoa_don);
           
            axios.get(`http://localhost:8080/api/client/don-hangs/${this.ma_hoa_don}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.data = res.data.data;
                        // console.log(this.data);
                        this.ds_ve = res.data.ds_ve;
                        this.ds_dv = res.data.ds_dichVu;

                    } else {
                        this.$toast.error("Không tìm thấy đơn hàng");
                        this.$router.push('/');
                    }
                });
        },

        cancelOder() {
            // var payload = {
            //     id: this.id_phim
            // }

            axios.put(`http://localhost:8080/api/client/don-hangs/cancel/${this.ma_hoa_don}`, null, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then(() => {
                    this.$toast.success("Huỷ đơn hàng thành công");
                    this.$router.push('/');
                })
                .catch(err => {
                    this.$toast.error("Huỷ đơn hàng thất bại");
                    console.error(err);
                });



        },

        thanhToan() {

            if (!this.phuongThucThanhToan) {
                this.$toast.error("Vui lòng chọn phương thức thanh toán");
                return;
            }
            // Optional: thông báo và điều hướng


            //     // Đóng modal
            //     const modalEl = document.getElementById('exampleModal');
            //     const modalInstance = bootstrap.Modal.getInstance(modalEl);
            //     if (modalInstance) {
            //         modalInstance.hide();
            //     }

            // }
            //thánh toán MOMO
            if (this.phuongThucThanhToan === "momo") {
                // Điều hướng sang giao diện quét mã
                axios.post('http://localhost:8080/api/client/momo/create', this.data, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                    .then(res => {
                        if (res.data && res.data.data && res.data.data.payUrl) {
                            this.$toast.success(res.data.message);
                            window.location.href = res.data.data.payUrl; // redirect sang MoMo
                        } else {
                            this.$toast.error("Không nhận được link thanh toán từ MoMo");
                        }
                    })
                    .catch(err => {
                        this.$toast.error("Tạo thanh toán thất bại");
                        console.error(err);
                    });



            }
            //Thanh toán qr payos
             else if (this.phuongThucThanhToan === "payos") {
                // Điều hướng sang giao diện quét mã
                axios.post('http://localhost:8080/api/client/payment/create', this.data, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                    .then(res => {
                        if (res.data && res.data.data && res.data.data.checkoutUrl) {
                            this.$toast.success(res.data.message);
                            window.location.href = res.data.data.checkoutUrl; // redirect sang PayOS
                        } else {
                            this.$toast.error("Không nhận được link thanh toán từ PayOS");
                        }
                    })
                    .catch(err => {
                        this.$toast.error("Tạo thanh toán thất bại");
                        console.error(err);
                    });



            }
             //Thanh toán VNPAY
             else if (this.phuongThucThanhToan === "vnpay") {
                // Điều hướng sang giao diện quét mã
                axios.post('http://localhost:8080/api/client/vnpay/create', this.data, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                    .then(res => {
                        if (res.data && res.data.data && res.data.data.paymentUrl) {
                            this.$toast.success(res.data.message);
                            window.location.href = res.data.data.paymentUrl; // redirect sang VNPAy
                        } else {
                            this.$toast.error("Không nhận được link thanh toán từ VNPay");
                        }
                    })
                    .catch(err => {
                        this.$toast.error("Tạo thanh toán thất bại");
                        console.error(err);
                    });



            }


        },
         


    },
}
</script>
<style></style>