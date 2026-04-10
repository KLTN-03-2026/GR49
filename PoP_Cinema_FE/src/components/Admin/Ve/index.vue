<template>
    <div class="row">
        <div class="col-lg-12">
            <div class="card radius-10 border-top border-0 border-3 border-info">
                <div class="card-header d-flex justify-content-between">
                    <h4 class="mt-2"><b>DANH SÁCH VÉ</b></h4>
                    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addModal">
                        Thêm vé
                    </button>
                </div>
                <div class="card-body table-responsive">
                    <div class="input-group mb-3">
                        <input type="text" class="form-control " placeholder="Search...." v-model="searchTerm">
                        <button class="btn btn-success " style="width: 90px;" @click="timVe">Tìm kiếm</button>
                    </div>
                    <table class="table table-bordered table-hover">
                        <thead>
                            <tr class="bg-primary text-light text-nowrap">
                                <th class="text-center">#</th>
                                <th class="text-center">Mã Vé</th>
                                <th class="text-center">Khách Hàng</th>
                                <th class="text-center">Tên Phim</th>
                                <th class="text-center">Suất Chiếu</th>
                                <th class="text-center">Ghế</th>
                                <th class="text-center">Giá Vé</th>
                                <th class="text-center">Ngày Đặt</th>
                                <th class="text-center">Trạng Thái</th>
                                <th class="text-center">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <template v-for="(item, index) in list_ve" :key="index">
                                <tr class="text-nowrap">
                                    <th class="align-middle text-center">{{ index + 1 }}</th>
                                    <td class="align-middle text-wrap">{{ item.maVe }}</td>
                                    <td class="align-middle">{{ item.hoVaTen }}</td>
                                    <td class="align-middle text-wrap">{{ item.tenPhim }}</td>
                                    <td class="align-middle text-center">{{ item.ngayChieu }} - {{
                                        item.thoiGianBatDau }} - {{
                                            item.thoiGianKetThuc }}</td>
                                    <td class="align-middle text-center">{{ item.tenGhe }}</td>
                                    <td class="align-middle text-center">{{ formatVND(item.giaVe) }}</td>
                                    <td class="align-middle text-center">{{ item.ngayDat }}</td>
                                    <td @click="doiTrangThai(item)" class="align-middle text-center"
                                        style="width: 180px;">
                                        <button v-if="item.tinhTrang == 0" class="btn btn-danger w-100 btn-sm"
                                            style="color: white;">
                                            Đã Hủy
                                        </button>
                                        <button v-if="item.tinhTrang == 1" class="btn btn-warning w-100 btn-sm"
                                            style="color: white;">
                                            Chưa Thanh Toán
                                        </button>
                                        <button v-if="item.tinhTrang == 2" class="btn btn-success w-100 btn-sm"
                                            style="color: white;">
                                            Đã Thanh Toán
                                        </button>
                                    </td>
                                    <td class="align-middle text-center">
                                        <button class="btn btn-info text-light me-2 btn-sm" data-bs-toggle="modal"
                                            data-bs-target="#updateModal" @click="edit_ve = item">
                                            Cập nhật
                                        </button>
                                        <button v-if="item.tinhTrang == 2" class="btn btn-dark btn-sm me-2" data-bs-toggle="modal"
                                            data-bs-target="#resetModal" disabled @click="del_ve = item">
                                            Reset
                                        </button>
                                        <button v-else class="btn btn-dark btn-sm me-2" data-bs-toggle="modal"
                                            data-bs-target="#resetModal" @click="del_ve = item">
                                            Reset
                                        </button>
                                          <button class="btn btn-danger btn-sm" data-bs-toggle="modal"
                                            data-bs-target="#deleteModal" @click="del_ve = item">
                                            Xoá
                                        </button>
                                    </td>
                                </tr>
                            </template>
                        </tbody>
                    </table>
                </div>

                <!-- Phân Trang -->
                <nav aria-label="Page navigation example" class="mt-4">
                    <ul class="pagination justify-content-end custom-pagination">
                        <li class="page-item" :class="{ disabled: currentPage === 1 }">
                            <button class="page-link" @click="changePage(currentPage - 1)">
                                &lsaquo; Previous
                            </button>
                        </li>

                        <li v-for="page in totalPages" :key="page" class="page-item"
                            :class="{ active: currentPage === page }">
                            <button class="page-link" @click="changePage(page)">
                                {{ page }}
                            </button>
                        </li>

                        <li class="page-item" :class="{ disabled: currentPage === totalPages }">
                            <button class="page-link" @click="changePage(currentPage + 1)">
                                Next &rsaquo;
                            </button>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    </div>

    <!-- Modal Thêm Mới -->
    <div class="modal fade" id="addModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Thêm Vé Mới</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Khách Hàng</label>
                            <select class="form-select" v-model="create_ve.id_khach_hang">
                                <option v-for="khach in list_khach_hang" :key="khach.id" :value="khach.id">{{
                                    khach.ho_va_ten }}</option>
                            </select>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Suất Chiếu</label>
                            <select class="form-select" v-model="create_ve.id_suat_chieu" @change="layDanhSachGhe()">
                                <option v-for="suat in list_suat_chieu" :key="suat.id" :value="suat.id">
                                    {{ suat.ten_phim }} - {{ suat.ngay_chieu }} {{ suat.gio_bat_dau }}
                                </option>
                            </select>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Ghế</label>
                            <select class="form-select" v-model="create_ve.id_ghe">
                                <option v-for="ghe in list_ghe" :key="ghe.id" :value="ghe.id">{{ ghe.ten_ghe }}</option>
                            </select>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Trạng Thái</label>
                            <select class="form-select" v-model="create_ve.tinh_trang">
                                <option value="0">Chưa Thanh Toán</option>
                                <option value="1">Đã Thanh Toán</option>
                                <option value="2">Đã Hủy</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        Đóng
                    </button>
                    <button type="button" class="btn btn-primary" data-bs-dismiss="modal" v-on:click="themVe()">
                        Thêm mới
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Cập Nhật -->
    <div class="modal fade" id="updateModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Cập Nhật Thông Tin Vé</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Khách Hàng</label>
                            <select class="form-select" v-model="edit_ve.idDonHang">
                                <option v-for="khach in list_don_hang" :key="khach.id" :value="khach.id">
                                    {{khach.maDonHang}} - {{khach.khachHang.hoVaTen }}</option>
                            </select>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Suất Chiếu</label>
                            <select class="form-select" v-model="edit_ve.idSuatChieu" @change="layDanhSachGheEdit()">
                                <option v-for="suat in list_suat_chieu" :key="suat.id" :value="suat.id">
                                    {{ suat.tenPhim }} - {{ suat.ngayChieu }} -{{ suat.thoiGianBatDau }}
                                </option>
                            </select>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Ghế</label>
                            <!-- <select class="form-select" v-model="edit_ve.id_ghe">
                                <option v-for="ghe in list_ghe_edit" :key="ghe.id" :value="ghe.id">{{ ghe.ten_ghe }}
                                </option>
                            </select> -->
                            <input type="text" class="form-control" v-model="edit_ve.tenGhe" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Trạng Thái</label>
                            <select class="form-select" v-model="edit_ve.tinhTrang">
                                <option value="1">Chưa Thanh Toán</option>
                                <option value="2">Đã Thanh Toán</option>
                                <option value="0">Đã Hủy</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        Đóng
                    </button>
                    <button type="button" class="btn btn-success" data-bs-dismiss="modal" v-on:click="capNhatVe()">
                        Cập nhật
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Reset vé -->
    <div class="modal fade" id="resetModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Reset Vé</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="alert alert-danger" role="alert">
                        Bạn có chắc chắn muốn reset vé
                        <strong>{{ del_ve.maVe }}</strong> của khách hàng <strong>{{ del_ve.hoVaTen }}</strong>?
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        Đóng
                    </button>
                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal" v-on:click="resetVe()">
                        Xác nhận
                    </button>
                </div>
            </div>
        </div>
    </div>


     <!-- Modal Xóa -->
    <div class="modal fade" id="deleteModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Xóa Vé</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="alert alert-danger" role="alert">
                        Bạn có chắc chắn muốn xóa vé
                        <strong>{{ del_ve.maVe }}</strong> khỏi hệ thống?
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        Đóng
                    </button>
                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal" v-on:click="xoaVe()">
                        Xác nhận
                    </button>
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
            list_ve: [],
            list_khach_hang: [],
            list_suat_chieu: [],
            list_don_hang: [],
            list_ghe: [],
            list_ghe_edit: [],
            create_ve: {
                id_khach_hang: '',
                id_suat_chieu: '',
                id_ghe: '',
                ma_ve: '',
                tinh_trang: '0'
            },
            edit_ve: {},
            del_ve: {},
            searchTerm: '',

            currentPage: 1,
            totalPages: 0,
            pageSize: 16,
        };
    },
    mounted() {
        this.layDataVe();
        // this.layDataKhachHang();
        this.layDataSuatChieu();
        this.layDataDonHang();
    },
    methods: {
        formatVND(number) {
            return new Intl.NumberFormat("vi-VI", { style: "currency", currency: "VND" }).format(number,);
        },
        // layDataVe() {
        //     axios
        //         .get('http://localhost:8080/api/admin/ves/get-data', {
        //             headers: {
        //                 Authorization: `Bearer ${token}`
        //             }
        //         })
        //         .then(response => {
        //             this.list_ve = response.data.data;
        //         })
        // },

        //Hiển thị data khách hàng
         layDataDonHang() {
            axios.get('http://localhost:8080/api/admin/don-hangs/get-data', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    this.list_don_hang = res.data.data;
                });
        },
        layDataSuatChieu() {
            axios
                .get('http://localhost:8080/api/admin/suat-chieu/get-data', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then(response => {
                    this.list_suat_chieu = response.data.data;
                })
        },

    
        layDanhSachGhe() {
            if (this.create_ve.id_suat_chieu) {
                axios
                    .get('http://127.0.0.1:8000/api/admin/ghe/get-ghe-trong/' + this.create_ve.id_suat_chieu)
                    .then(response => {
                        this.list_ghe = response.data.data;
                    })
            }
        },
       
        themVe() {
            axios
                .post('http://127.0.0.1:8000/api/admin/ve/create', this.create_ve)
                .then(response => {
                    if (response.data.status) {
                        this.layDataVe();
                        this.create_ve = {
                            id_khach_hang: '',
                            id_suat_chieu: '',
                            id_ghe: '',
                            ma_ve: '',
                            tinh_trang: '0'
                        };
                        this.$toast.success(response.data.message);
                    } else {
                        this.$toast.error(response.data.message);
                    }
                })
        },
        capNhatVe() {
            axios
                .put('http://localhost:8080/api/admin/ves/update', this.edit_ve, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then(response => {
                    if (response.data.status) {
                        
                        this.$toast.success(response.data.message);
                        this.layDataVe();
                    } else {
                        this.$toast.error(response.data.message);
                    }
                })
        },
        resetVe() {
            axios
                .put(`http://localhost:8080/api/admin/ves/delete/${this.del_ve.id}`,null, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then(response => {
                    if (response.data.status) {
                        // Đảm bảo currentPage tồn tại
                        const page = this.currentPage ?? 1;

                        // Reload đúng trang hiện tại
                        this.layDataVe(page);
                        this.$toast.success(response.data.message);
                    } else {
                        this.$toast.error(response.data.message);
                    }
                })
        },

        xoaVe() {
            axios
                .delete(`http://localhost:8080/api/admin/ves/destroy/${this.del_ve.id}`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then(response => {
                    if (response.data.status) {
                        // Đảm bảo currentPage tồn tại
                        const page = this.currentPage ?? 1;

                        // Reload đúng trang hiện tại
                        this.layDataVe(page);
                        this.$toast.success(response.data.message);
                    } else {
                        this.$toast.error(response.data.message);
                    }
                })
        },


        timVe() {

            axios.get(`http://localhost:8080/api/admin/ves/search-ve?mave=${this.searchTerm}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (this.searchTerm == null || this.searchTerm == '') {
                        this.$toast.warning("Vui lòng nhập mã vé để tìm kiếm");

                    } else {
                        if (res.data.status) {
                            this.$toast.success(res.data.message);
                            this.list_ve = res.data.data;

                        } else {
                            this.$toast.error(res.data.message);

                        }
                    }
                });
        },

        doiTrangThai(value) {
            axios
                .post('http://localhost:8080/api/admin/ves/update-status', value, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then((res) => {
                    if (res.data.status) {
                        // Đảm bảo currentPage tồn tại
                        const page = this.currentPage ?? 1;

                        // Reload đúng trang hiện tại
                        this.layDataVe(page);
                        this.$toast.success(res.data.message);

                    }else{
                         this.$toast.error(res.data.message);
                    }
                });

        },

       //Hiển thị data vé với phân trang
        layDataVe(page = 1) {
            axios
                .get('http://localhost:8080/api/admin/ves/get-all-ve', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    },
                    params: {
                        pageNo: page - 1,   // backend bắt đầu từ 0
                        pageSize: this.pageSize
                    }
                })
                .then(response => {

                    this.list_ve = response.data.ves;
                    this.currentPage = (response.data.pageNo ?? 0) + 1;
                    this.totalPages = response.data.totalPages ?? 1;
                })
                .catch(error => {
                    console.error("Lỗi khi tải dữ liệu vé:", error);
                });
        },

        changePage(page) {
            if (page >= 1 && page <= this.totalPages) {
                this.layDataVe(page);
            }
        }
    },

};
</script>

<style src="./index.css"></style>