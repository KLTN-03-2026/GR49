<template>
    <div class="row">
        <div class="col-lg-12">
            <div class="card">
                <div class="card-header mt-2">
                    <h4 class="fw-bold text-primary">
                        Thống Kê Doanh Thu Đơn Hàng
                    </h4>
                </div>
                <div class="card-body">
                    <div class="mb-3">
                        <div class="row g-3 align-items-center">
                            <div class="col-lg-5 col-md-6">
                                <label for="">Từ ngày</label>
                                <input type="date" class="form-control mt-2 mb-2 w-100" v-model="ngayBatDau">
                            </div>
                            <div class="col-lg-5 col-md-6">
                                <label for="">Đến ngày</label>
                                <input type="date" class="form-control mt-2 mb-2 w-100" v-model="ngayKetThuc">
                            </div>
                            <div class="col-lg-2 col-md-12">
                                <label for="">&nbsp;</label>
                                <button class="btn btn-primary w-100" v-on:click="getData()">Thống Kê</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-6">
            <div class="card">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-hover table-bordered">
                            <thead class="table-light">
                                <tr class="text-center table-warning">
                                    <th>Ngày</th>
                                    <th>Đơn Hàng</th>
                                    <th>Tổng Doanh Thu (VND)</th>
                                    <th>Tổng Giảm Giá (VND)</th>
                                    <th>Doanh Thu Thực (VND)</th>
                                </tr>
                            </thead>
                            <tbody>
                                <template v-for="(value, index) in list_data" :key="index">
                                    <tr>
                                        <td class="text-center">{{ value.ngay }}</td>
                                        <td class="text-end">{{ value.soDon }}</td>
                                        <td class="text-end">{{ formatVND(value.tongDoanhThu) }}</td>
                                        <td class="text-end">{{ formatVND(value.tongGiamGia) }}</td>
                                        <td class="text-end">{{ formatVND(value.tongDoanhThuThuc) }}</td>

                                    </tr>
                                </template>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-6">
             <div class="card">
                <div class="card-body">
                    <h6 class="mb-2"><b>Biểu Đồ</b></h6>
                    <hr>
                    <div class="row">
                        <div class="col-lg-2"></div>
                        <div class="col-lg-8">
                            <!-- Bar chart với 2 dataset -->
                            <Bar v-if="is_loading" id="my-chart-id" :options="chartOptions" :data="chartData" />
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
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'
import { Bar } from 'vue-chartjs'
// đăng ký các thành phần cần cho bar chart
ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)
const token = localStorage.getItem("key_admin");
export default {
     components: { Bar },
    data() {
        return {
            ngayBatDau: '',
            ngayKetThuc: '',
        

              chartData: {
                labels: ['January', 'February', 'March'],
                datasets: [
                    {
                        label: "Tổng Doanh Thu",
                        data: [40, 20, 12],
                        backgroundColor: '#2F4F6F'
                    },

                ]
            },
            chartOptions: {
                responsive: true,
                // plugins: {
                //     legend: { position: 'top' },
                //     title: { display: true, text: 'Thống kê vé bán theo ngày' }
                // }
            },
            is_loading: false,
            list_data: [],

        }
    },
    mounted() {
        this.getData();

    },
    methods: {
        formatVND(number) {
            return new Intl.NumberFormat("vi-VI", { style: "currency", currency: "VND" }).format(number,);
        },
        getData() {
            console.log(this.ngayBatDau);
            this.is_loading = false;
            var payload = {
                ngayBatDau: this.ngayBatDau,
                ngayKetThuc: this.ngayKetThuc,
            }
            axios
                .post('http://localhost:8080/api/admin/thong-ke/thong-ke-doanh-thu', payload, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then((response) => {
                    this.chartData.labels = response.data.list_ngay,
                    this.chartData.datasets[0].data = response.data.list_tongDoanhThu,
                    this.is_loading = true,
                
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
}
</script>
<style></style>