<template>
    <div class="row">
        <div class="col-lg-12">
            <div class="card">
                <div class="card-header mt-2">
                    <h4 class="fw-bold text-primary">
                        Thống Kê Voucher Đã Dùng
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
                                <input type="date" class="form-control mt-2 mb-2" v-model="ngayKetThuc">
                            </div>
                            <div class="col-lg-2 col-md-12">
                                <label for="">&nbsp;</label>
                                <button class="btn btn-primary w-100" @click="getData()">Thống Kê</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-5">
            <div class="card">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-hover table-bordered">
                            <thead class="table-light">
                                <tr class="table-warning">
                                    <th class="text-center">Ngày</th>
                                    <th class="text-center">Tên Voucher</th>
                                    <th class="text-center">Lượt Sử Dụng</th>
                                </tr>
                            </thead>
                            <tbody>
                                 <template v-for="(value, index) in list_data" :key="index">
                                <tr>
                                    <td class="text-center">{{ value.ngay }}</td>
                                    <td class="text-center">{{ value.tenVoucher }}</td>
                                    <td class="text-center">{{ value.luotSuDung }}</td>
                                </tr>
                                </template>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-7">
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
                        data: [40, 20, 12] , 
                        backgroundColor: [  '#e9771b','#8734b0', '#FFCE56' ]}
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
         getData() {
            console.log(this.ngayBatDau);
            this.is_loading = false;
            var payload = {
                ngayBatDau: this.ngayBatDau,
                ngayKetThuc: this.ngayKetThuc,
            }
            axios
                .post('http://localhost:8080/api/admin/thong-ke/thong-ke-voucher', payload, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then((response) => {
                    this.chartData.labels = response.data.list_ten,
                    this.chartData.datasets[0].data = response.data.list_luot_dung,
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