<template>
    <div class="row row-cols-1 row-cols-md-2 row-cols-xl-4">
        <div class="col">
            <div class="card radius-10 border-start border-0 border-3 border-info">
                <div class="card-body">
                    <div class="d-flex align-items-center">
                        <div class="text-wrap">
                            <p class="mb-0 text-secondary">Tổng doanh thu</p>
                            <h4 class="my-1 text-info"> {{ formatVND(list_tong_doanh_thu) }}</h4>

                        </div>
                        <div class="widgets-icons-2 rounded-circle bg-gradient-scooter text-white ms-auto"><i
                                class='bx bxs-wallet'></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card radius-10 border-start border-0 border-3 border-danger">
                <div class="card-body">
                    <div class="d-flex align-items-center">
                        <div class=" text-wrap">
                            <p class="mb-0 text-secondary">Tổng Phim</p>
                            <h4 class="my-1 text-danger"> {{ list_tong_phim }} </h4>

                        </div>
                        <div class="widgets-icons-2 rounded-circle bg-gradient-bloody text-white ms-auto"><i
                                class='bx bx-film'></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card radius-10 border-start border-0 border-3 border-warning">
                <div class="card-body">
                    <div class="d-flex align-items-center">
                        <div>
                            <p class="mb-0 text-secondary" style="margin-right: 10;">Tổng Vé Đã Bán</p>
                            <h4 class="my-1 text-warning" style="margin-right: 10;"> {{ list_tong_ve_ban }} </h4>
                        </div>

                        <div class="widgets-icons-2 rounded-circle bg-gradient-blooker text-white ms-auto"><i
                                class='bx bxs-group'></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card radius-10 border-start border-0 border-3 border-success">
                <div class="card-body">
                    <div class="d-flex align-items-center">
                        <div>
                            <p class="mb-0 text-secondary" style="margin-right: 10;">Tổng Phòng Chiếu</p>
                            <h4 class="my-1 text-success" style="margin-right: 10;"> {{ list_tong_phong_chieu }} </h4>
                        </div>

                        <div class="widgets-icons-2 rounded-circle bg-gradient-ohhappiness text-white ms-auto"><i
                                class='bx bx-building'></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-7 mb-3">
            <div class="card radius-10 border-top border-0 border-3 h-60">
                <div class="card-body d-flex flex-column">
                    <h6 class="mb-3"><b>Tổng doanh thu</b></h6>
                    <div class="d-flex justify-content-center align-items-center flex-fill">
                        <!-- Bar chart với 2 dataset -->
                        <Bar v-if="is_loading" :options="chartOptions1" :data="chartData"
                            style="max-width:100%; height:300px;" />
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-5 mb-3">
            <div class="card radius-10 border-top border-0 border-3  h-60">
                <div class="card-body d-flex flex-column">
                    <h6 class="mb-3"><b>Vé theo loại</b></h6>
                    <div class="d-flex justify-content-center align-items-center flex-fill">
                        <!-- Doughnut chart -->
                        <Doughnut v-if="is_loading_donut" :options="chartOptions" :data="chartData2"
                            style="max-width:100%; height:300px;" />
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row ">
        <div class="col-lg-8 d-flex">
            <div class="card radius-10 border-top border-0 border-3 border-info flex-fill ">
                <div class="card-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <h4 class="mt-2"><b>ĐẶT VÉ GẦN ĐÂY</b></h4>
                            <p class="text-secondary">Có {{ list_dat_ve_gan_day.length }} lượt đặt vé trong 7 ngày qua.
                            </p>

                            <!-- Nếu có đặt vé -->
                            <div class="booking-item border rounded p-3 mb-2"
                                v-for="(value, index) in list_dat_ve_gan_day" :key="index">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h5 class="mb-0">{{ value.tenKhach }}</h5>
                                        <p class="mb-0">{{ value.tenPhim }} - {{ value.soVe }} vé</p>
                                    </div>
                                    <div class="text-end">
                                        <h5 class="mb-0">{{ formatVND(value.tongTien) }}</h5>
                                        <p class="mb-0">{{ formatDateTime(value.ngay) }}</p>
                                    </div>
                                </div>
                            </div>
                            <!-- Nếu KHÔNG có đặt vé -->
                            <div v-if="list_dat_ve_gan_day.length === 0"
                                class="alert alert-light border text-center py-4" role="alert">
                                <i class="bx bx-calendar-x fs-1 text-secondary d-block mb-2"></i>
                                <span class="fw-semibold text-secondary">Chưa có đặt vé nào hôm nay</span>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-lg-4 d-flex">
            <div class="card radius-10 border-top border-0 border-3 border-success flex-fill">
                <div class="card-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <h4 class="mt-2"><b>PHIM PHỔ BIẾN</b></h4>
                            <p class="text-secondary">Top phim bán chạy nhất tháng này.</p>

                            <div class="d-flex justify-content-between align-items-center mb-3"
                                v-for="(value, index) in list_phim_pho_bien" :key="index">
                                <div class="d-flex align-items-center">
                                    <!-- Số thứ tự -->
                                    <span class="fw-bold text-muted" style="min-width: 20px;">{{ index + 1 }}.</span>

                                    <!-- Ảnh nằm ngang, cố định -->
                                    <img :src="value.hinhAnh" alt="Hình Ảnh"
                                        style="width: 60px; height: 80px; object-fit: cover; border-radius: 6px; flex-shrink: 0;">

                                    <!-- Thông tin phim -->
                                    <div class="overflow-hidden ms-2">
                                        <h6 class="mb-0 text-truncate">{{ value.tenPhim }}</h6>
                                        <p class="mb-0 text-secondary small text-truncate">{{ value.theLoai }}</p>
                                    </div>
                                </div>

                                <!-- Badge vé -->
                                <span class="badge bg-primary text-nowrap">{{ value.soVe }} vé</span>
                            </div>


                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</template>
<script>
import axios from 'axios';
// import cho Bar chart
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, ArcElement, } from 'chart.js'
import { Bar } from 'vue-chartjs'
import { Doughnut } from 'vue-chartjs'


// đăng ký các thành phần cần cho bar chart
ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, ArcElement)
const token = localStorage.getItem("key_admin");
export default {
    components: { Bar, Doughnut },
    data() {
        return {

            list_tong_doanh_thu: 0,
            list_tong_phim: 0,
            list_tong_ve_ban: 0,
            list_tong_phong_chieu: 0,
            list_dat_ve_gan_day: [],
            list_phim_pho_bien: [],

            // dữ liệu cho biểu đồ cột
            chartData: {
                labels: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'],
                datasets: [
                    {
                        label: "Doanh thu đã thanh toán",
                        data: [350000, 220000, 300000, 280000, 120000, 280000, 150000],
                        backgroundColor: '#2F7CFF',
                        borderRadius: 6
                    },
                    {
                        label: "Doanh thu đã huỷ",
                        data: [100000, 100000, 70000, 200000, 100000, 120000, 140000],
                        backgroundColor: '#20C997',
                        borderRadius: 6
                    }
                ]
            },
            chartOptions1: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'bottom',
                        labels: {
                            usePointStyle: true,
                            boxWidth: 10
                        }
                    },

                },
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            callback: function (value) {
                                return value >= 1000 ? value / 1000 + 'k' : value;
                            }
                        },
                        grid: {
                            color: '#F1F5F9'
                        }
                    },
                    x: {
                        grid: {
                            display: false
                        }
                    }
                }
            }
            ,
            is_loading: true,


            // dữ liệu cho biểu đồ tròn
            chartData2: {
                labels: ['Vé VIP', 'Vé Thường', 'Vé Đôi'],
                datasets: [
                    {
                        data: [40, 20, 12],
                        backgroundColor: ['#4ADE80', '#F472B6', '#A78BFA'], // 💖💚💜 màu sáng đẹp
                        hoverBackgroundColor: ['#22C55E', '#EC4899', '#8B5CF6'], // màu khi hover
                        borderWidth: 2,
                        borderColor: '#fff',
                    }
                ]
            },
            chartOptions: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'bottom',
                        labels: {
                            usePointStyle: true,
                            boxWidth: 10,
                            color: '#374151'
                        }
                    },

                }
            },
            is_loading_donut: false,
            list_data: [],



        }
    },
    mounted() {
        this.getTongDoanhThu();
        this.getTongPhim();
        this.getTongVeBan();
        this.getTongPhongChieu();
        this.getDatVeGanDay();
        this.getPhimPhoBien();
        this.getLoaiVe();

    },
    methods: {

        formatDateTime(dateTime) {
            if (!dateTime) return "";

            const date = new Date(dateTime);
            const today = new Date();
            const yesterday = new Date();
            const twoDaysAgo = new Date();

            yesterday.setDate(today.getDate() - 1);
            twoDaysAgo.setDate(today.getDate() - 2);

            const isToday = date.toDateString() === today.toDateString();
            const isYesterday = date.toDateString() === yesterday.toDateString();



            if (isToday) {
                return `Hôm nay`;
            } else if (isYesterday) {
                return `Hôm qua`;
            } else {
                // Hôm kia hoặc xa hơn → chỉ hiển thị ngày
                return date.toLocaleDateString("vi-VN");
            }
        },

        formatVND(number) {
            return new Intl.NumberFormat("vi-VI", { style: "currency", currency: "VND" }).format(number,);
        },

        getTongDoanhThu() {

            axios
                .get('http://localhost:8080/api/admin/dashboard/get-tong-doanh-thu', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then((response) => {

                    this.list_tong_doanh_thu = response.data.tong_doanh_thu
                    console.log(this.list_tong_doanh_thu)
                })



        },
        getTongPhim() {

            axios
                .get('http://localhost:8080/api/admin/dashboard/get-tong-phim', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then((response) => {

                    this.list_tong_phim = response.data.tong_phim

                })



        },
        getTongVeBan() {

            axios
                .get('http://localhost:8080/api/admin/dashboard/get-tong-ve-ban', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then((response) => {

                    this.list_tong_ve_ban = response.data.tong_ve_da_ban

                })



        },
        getTongPhongChieu() {

            axios
                .get('http://localhost:8080/api/admin/dashboard/get-tong-phong-chieu', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then((response) => {

                    this.list_tong_phong_chieu = response.data.tong_phong_chieu

                })



        },
        getDatVeGanDay() {

            axios
                .get('http://localhost:8080/api/admin/dashboard/get-dat-ve-gan-day', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then((response) => {

                    this.list_dat_ve_gan_day = response.data.data

                })



        },

        getPhimPhoBien() {

            axios
                .get('http://localhost:8080/api/admin/dashboard/get-phim-pho-bien', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then((response) => {

                    this.list_phim_pho_bien = response.data.get_phim_pho_bien

                })



        },

        getLoaiVe() {
            axios
                .get('http://localhost:8080/api/admin/dashboard/thong-ke-loai-ve', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then((response) => {
                    this.chartData2.labels = response.data.list_loai_ve,
                        this.chartData2.datasets[0].data = response.data.list_so_luong_ve,
                        this.is_loading_donut = true,

                        this.list_data = response.data.data
                })
                .catch(err => {
                    if (err.response && err.response.data && err.response.data.errors) {
                        const list = Object.values(err.response.data.errors);
                        list.forEach((v) => {
                            this.$toast.error(v[0]);
                        });
                    } else {
                        this.$toast.error('Lỗi không xác định');
                    }
                });
        }



    },
};

</script>
<style></style>