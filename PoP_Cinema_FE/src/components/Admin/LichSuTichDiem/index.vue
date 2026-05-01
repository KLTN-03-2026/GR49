<template>
    <div class="row">
        <div class="col-lg-12">
            <div class="card radius-10 border-top border-0 border-3 border-info">
                <div class="card-header d-flex justify-content-between">
                    <h4 class="mt-2"><b>DANH SÁCH TÍCH ĐIỂM</b></h4>

                </div>
                <div class="card-body table-responsive">
                    <!-- <div class="input-group mb-3">
                        <input type="text" class="form-control"
                            placeholder="Tìm kiếm theo tên, email, số điện thoại...." v-model="searchText">
                        <button class="btn btn-success input-group-text" style="width: 140px;">Tìm kiếm</button>
                    </div> -->
                    <div class="table-responsive">
                        <div class="card mb-3 shadow-sm">
                            <div class="card-header bg-dark text-white">
                                <b>🏆 TOP 5 KHÁCH HÀNG NHIỀU ĐIỂM</b>
                            </div>

                            <div class="card-body">

                                <div v-for="(item, index) in top_khach_hang" :key="index"
                                    class="d-flex justify-content-between align-items-center p-2 mb-2 rounded hover-item"
                                    style="cursor: pointer; background: #f8f9fa;">

                                    <!-- LEFT -->
                                    <div class="d-flex align-items-center gap-2">

                                        <!-- Rank -->
                                        <div style="width: 40px;">
                                            <span v-if="index == 0" class="fs-4">🥇</span>
                                            <span v-else-if="index == 1" class="fs-4">🥈</span>
                                            <span v-else-if="index == 2" class="fs-4">🥉</span>
                                            <span v-else class="fw-bold">#{{ index + 1 }}</span>
                                        </div>

                                        <!-- Info -->
                                        <div>
                                            <div class="fw-bold">{{ item.tenKhachHang }}</div>

                                            <!-- Rank badge -->
                                            <span class="badge mt-1 d-inline-flex align-items-center gap-1 px-2 py-1"
                                                :style="badgeStyle(item.hang)">

                                                <i :class="iconClass(item.hang)" class="me-1"></i>{{ item.hang }}
                                            </span>
                                        </div>
                                    </div>

                                    <!-- RIGHT -->
                                    <div>
                                        <span class="badge bg-success fs-6">
                                            {{ item.diem }} điểm
                                        </span>
                                    </div>

                                </div>

                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-md-3">
                                <input type="date" class="form-control" v-model="ngayTruoc">
                            </div>
                            <div class="col-md-3">
                                <input type="date" class="form-control" v-model="ngaySau">
                            </div>
                            <div class="col-md-3">
                                <select class="form-control" v-model="trangThai">
                                    <option value="">Tất cả trạng thái</option>
                                    <option value="0">Chưa xác định</option>
                                    <option value="1">Đã tích điểm</option>
                                    <option value="2">Đã tiêu điểm</option>
                                </select>
                            </div>
                        </div>
                        <table class="table table-bordered table-hover">
                            <thead>
                                <tr class="bg-primary text-light text-nowrap">
                                    <th class="text-center">#</th>
                                    <th class="text-center">Khách hàng </th>
                                    <th class="text-center">Điểm</th>
                                    <th class="text-center">Mô tả</th>
                                    <th class="text-center">Tổng điểm</th>
                                    <th class="text-center">Thời gian</th>
                                    <th class="text-center">Loại</th>
                                    <th class="text-center">Tình Trạng</th>

                                </tr>
                            </thead>
                            <tbody>
                                <template v-for="(item, index) in list_tich_diem" :key="index">
                                    <tr class="text-nowrap">
                                        <th class="align-middle text-center">{{ index + 1 }}</th>
                                        <td class="align-middle">{{ item.tenKhachHang }}</td>
                                        <td class="align-middle fw-bold">
                                            <span v-if="item.loai == 1" class="text-success">
                                                <i class="fas fa-arrow-up me-1"></i> +{{ item.diem }}
                                            </span>
                                            <span v-else-if="item.loai == 2" class="text-danger">
                                                <i class="fas fa-arrow-down me-1"></i> -{{ item.diem }}
                                            </span>
                                        </td>
                                        <td class="align-middle text-center">{{ item.moTa }}</td>
                                        <td class="align-middle text-center">

                                            <span class="fw-bold">
                                                {{ item.diemConLai }} điểm
                                            </span>

                                            <br>

                                            <small v-if="item.loai == 1" class="text-success">
                                                +{{ item.diem }}
                                            </small>

                                            <small v-else-if="item.loai == 2" class="text-danger">
                                                -{{ item.diem }}
                                            </small>

                                        </td>
                                        <td class="align-middle text-center">
                                            {{ new Date(item.ngayTao).toLocaleString('vi-VN') }}
                                        </td>
                                        <td class="align-middle text-center">
                                            <span v-if="item.loai == 1" class="badge bg-success">
                                                <i class="fas fa-plus me-1"></i> Cộng điểm
                                            </span>

                                            <span v-else-if="item.loai == 2" class="badge bg-danger">
                                                <i class="fas fa-minus me-1"></i> Trừ điểm
                                            </span>

                                            <span v-else class="badge bg-secondary">
                                                Không xác định
                                            </span>
                                        </td>
                                        <td class="align-middle text-center">
                                            <button v-if="item.tinhTrang == 0" class="btn btn-info w-100"
                                                style="color: white;">
                                                Chưa xác định
                                            </button>
                                            <button v-else-if="item.tinhTrang == 1" class="btn btn-success w-100"
                                                style="color: white;">
                                                Đã tích điểm
                                            </button>
                                            <button v-else class="btn btn-danger w-100" style="color: white;">
                                                Đã tiêu điểm
                                            </button>
                                        </td>

                                    </tr>
                                </template>
                            </tbody>
                        </table>
                    </div>
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
            list_tich_diem: [],
            list_chuc_vu: [],
            searchText: '',
            ngayTruoc: '',
            ngaySau: '',
            trangThai: '',
            top_khach_hang: []
        };
    },
    mounted() {
        this.getTichDiem();
        this.getTop5KhachHang();

    },
    computed: {
        // Lọc nhân viên theo tên (không phân biệt hoa thường)
        filteredNhanVien() {
            const keyword = this.searchText.toLowerCase().trim();
            return this.list_nhan_vien.filter((nv) => {
                const ho_va_ten = nv.hoVaTen.toLowerCase();
                const so_dien_thoai = nv.soDienThoai.toLowerCase();
                const email = nv.email.toLowerCase();
                return (
                    ho_va_ten.includes(keyword) ||
                    so_dien_thoai.includes(keyword) ||
                    email.includes(keyword)
                );
            });
        },
    },
    watch: {
        ngayTruoc() {
            this.getTichDiem();
        },
        ngaySau() {
            this.getTichDiem();
        },
        trangThai() {
            this.getTichDiem();
        }
    },
    methods: {



        getTichDiem() {
            axios.get('http://localhost:8080/api/admin/tich-diem/get-data', {
                params: {
                    ngayTruoc: this.ngayTruoc || null,
                    ngaySau: this.ngaySau || null,
                    tinhTrang: this.trangThai || null
                },
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    this.list_tich_diem = res.data.data;
                });
        },

        getTop5KhachHang() {
            axios.get('http://localhost:8080/api/admin/tich-diem/top-khach-hang', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    this.top_khach_hang = res.data.data;
                });
        },
        badgeStyle(hang) {
            if (hang === "Đồng") {
                return "background: linear-gradient(135deg, #E6CFC3, #C89F94); color: #5A3E36;";
            }
            if (hang === "Bạc") {
                return "background: linear-gradient(135deg, #F1F1F1, #D6D6D6); color: #555;";
            }
            if (hang === "Vàng") {
                return "background: linear-gradient(135deg, #FFF3CD, #FFE08A); color: #B8860B;";
            }
            if (hang === "Bạch Kim") {
                return "background: linear-gradient(135deg, #E0F7FA, #B2EBF2); color: #006064;";
            }
            return "background:#eee; color:#333;";
        },
        iconClass(hang) {

            if (hang === "Đồng") return "fas fa-star";
            if (hang === "Bạc") return "fas fa-shield-alt";
            if (hang === "Vàng") return "fas fa-crown";
            if (hang === "Bạch Kim") return "fas fa-gem";

            return "fas fa-star";
        },

    },
};
</script>

<style></style>
