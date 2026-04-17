<template>
    <div class="row">
        <div class="col-lg-12">
            <div class="card">
                <div class="card-header mt-2">
                    <h4 class="fw-bold text-primary">
                        Thống Kê Chi Tiêu Khách Hàng
                    </h4>
                </div>
                <div class="card-body">
                    <div class="mb-3">
                        <div class="row g-3 align-items-center">
                            <div class="col-lg-5 col-md-6">
                                <label for="">Từ ngày</label>
                                <input type="date" class="form-control mt-2 mb-2 w-100" v-model="ngayBatDau" >
                            </div>
                            <div class="col-lg-5 col-md-6">
                                <label for="">Đến ngày</label>
                                <input  type="date" class="form-control mt-2 mb-2" v-model="ngayKetThuc">
                            </div>
                            <div class="col-lg-2 col-md-12">
                                <label for="">&nbsp;</label>
                                <button  v-on:click="getData()" class="btn btn-primary w-100">Thống Kê</button>
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
                                <tr class="table-warning">
                                    
                                    <th class="text-center">Ngày</th>
                                    <th class="text-center">Tên Khách Hàng</th>
                                    <th class="text-center">Số Đơn Hàng</th>
                                    <th class="text-center">Tổng Chi Tiêu</th>
                                </tr>
                            </thead>
                            <tbody>
                                 <template v-for="(value, index) in list_data" :key="index">
                                    <tr>
                                        <td class="text-center">{{ value.ngay }}</td>
                                        <td class="text-center">{{ value.tenKhachHang }}</td>
                                        <td class="text-center">{{ value.soDonHang }}</td>
                                        <td class="text-center">{{ formatVND(value.tongChiTieu) }}</td>
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
                            <Doughnut v-if="is_loading == true" id="my-chart-id" :options="chartOptions"
                                :data="chartData" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js'
import { Doughnut } from 'vue-chartjs'
ChartJS.register(ArcElement, Tooltip, Legend)
const token = localStorage.getItem("key_admin");
export default {
     components: { Doughnut },
    data() {
        return {
            ngayBatDau: '',
            ngayKetThuc: '',
        

             chartData: {
                labels: [ 'January', 'February', 'March' ],
                datasets: [
                    { 
                        data: [] , 
                      backgroundColor: ['#f87979', '#7dcea0', '#3498db', '#17202a', '#4a235a', '#aab7b8', '#d4ac0d']}
                ]
            },
            chartOptions: {
                responsive: true
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
                .post('http://localhost:8080/api/admin/thong-ke/thong-ke-chi-tieu', payload, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then((response) => {
                    this.chartData.labels = response.data.list_ten,
                    this.chartData.datasets[0].data = response.data.list_chi_tieu,
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
};
</script>
<style></style>