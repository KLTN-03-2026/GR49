<template>
    <div class="row">
        <div class="col-lg-12">
            <div class="card radius-10 border-top border-0 border-3 border-info">
                <div class="card-header d-flex justify-content-between">
                    <h4 class="mt-2"><b>DANH SÁCH SUẤT CHIẾU</b></h4>
                    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addModal">
                        Thêm suất chiếu
                    </button>
                </div>
                <div class="card-body table-responsive">
                    <div class="input-group mb-3">
                        <input type="text" class="form-control" placeholder="Tìm kiếm theo tên phim hoặc phòng chiếu..." v-model="searchText" />
                        <button class="btn btn-success input-group-text" style="width: 150px;">Tìm kiếm</button>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead>
                                <tr class="bg-primary text-light text-nowrap">
                                    <th class="text-center">#</th>
                                    <th class="text-center">Tên Phim</th>
                                    <th class="text-center">Phòng Chiếu</th>
                                    <th class="text-center">Ngày Chiếu</th>
                                    <th class="text-center">Giờ Bắt Đầu</th>
                                    <th class="text-center">Giờ Kết Thúc</th>
                                    <th class="text-center">Tình Trạng</th>
                                    <th class="text-center">Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <template v-for="(item, index) in filteredSuatChieu" :key="index">
                                    <!-- <td class="align-middle text-wrap" hidden>{{ item.idPhim }}</td>
                                        <td class="align-middle text-center" hidden>{{ item.idPhongChieu }}</td> -->
                                    <tr class="text-nowrap">
                                        <th class="align-middle text-center">{{ index + 1 }}</th>
                                        <td class="align-middle text-wrap" :value="item.idPhim">{{ item.tenPhim }}</td>
                                        <td class="align-middle text-center" :value="item.idPhongChieu">{{
                                            item.tenPhongChieu }}</td>
                                        <td class="align-middle text-center">{{ item.ngayChieu }}</td>
                                        <td class="align-middle text-center">{{ item.thoiGianBatDau }}</td>
                                        <td class="align-middle text-center">{{ item.thoiGianKetThuc }}</td>
                                        <td v-on:click="doiTrangThaiSuatChieu(item)" class="align-middle text-center">
                                            <button v-if="item.tinhTrang == 1" class="btn btn-success w-100"
                                                style="color: white;">
                                                Hoạt Động
                                            </button>
                                            <button v-else class="btn btn-danger w-100" style="color: white;">
                                                Đã Hủy
                                            </button>
                                        </td>
                                        <td class="align-middle text-center">
                                            <button class="btn btn-primary text-light me-2" data-bs-toggle="modal"
                                                data-bs-target="#taoVeModal" @click="info_ve = item">
                                                Tạo vé
                                            </button>
                                            <button class="btn btn-info text-light me-2" data-bs-toggle="modal"
                                                data-bs-target="#updateModal" @click="edit_suat_chieu = item">
                                                Cập nhật
                                            </button>
                                            <button class="btn btn-danger" data-bs-toggle="modal"
                                                data-bs-target="#deleteModal" @click="del_suat_chieu = item">
                                                Xóa
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

    <!-- Modal Thêm Mới -->
    <div class="modal fade" id="addModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Thêm Suất Chiếu Mới</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Phim</label>
                            <select class="form-select" v-model="create_suat_chieu.idPhim">
                                <option v-for="phim in list_phim" :key="phim.id" :value="phim.id">{{ phim.tenPhim }}
                                </option>
                            </select>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Phòng Chiếu</label>
                            <select class="form-select" v-model="create_suat_chieu.idPhongChieu">
                                <option v-for="phong in list_phong" :key="phong.id" :value="phong.id">{{ phong.tenPhong
                                    }}
                                </option>
                            </select>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Giờ Bắt Đầu</label>
                            <input type="time" class="form-control" v-model="create_suat_chieu.thoiGianBatDau" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Giờ Kết Thúc</label>
                            <input type="time" class="form-control" v-model="create_suat_chieu.thoiGianKetThuc"
                                :min="create_suat_chieu.thoiGianBatDau" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Ngày Chiếu</label>
                            <input type="date" class="form-control" v-model="create_suat_chieu.ngayChieu"
                                :min="todayStr" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Tình Trạng</label>
                            <select class="form-select" v-model="create_suat_chieu.tinhTrang">
                                <option value="1">Hoạt Động</option>
                                <option value="0">Đã Hủy</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        Đóng
                    </button>
                    <button type="button" class="btn btn-primary" v-on:click="themSuatChieu()">
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
                    <h5 class="modal-title">Cập Nhật Thông Tin Suất Chiếu</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Phim</label>
                            <select class="form-select" v-model="edit_suat_chieu.idPhim">
                                <option v-for="phim in list_phim" :key="phim.id" :value="phim.id">{{ phim.tenPhim }}
                                </option>
                            </select>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Phòng Chiếu</label>
                            <select class="form-select" v-model="edit_suat_chieu.idPhongChieu">
                                <option v-for="phong in list_phong" :key="phong.id" :value="phong.id">{{ phong.tenPhong
                                    }}
                                </option>
                            </select>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Giờ Bắt Đầu</label>
                            <input type="time" class="form-control" v-model="edit_suat_chieu.thoiGianBatDau" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Giờ Kết Thúc</label>
                            <input type="time" class="form-control" v-model="edit_suat_chieu.thoiGianKetThuc" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Ngày Chiếu</label>
                            <input type="date" class="form-control" v-model="edit_suat_chieu.ngayChieu" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Tình Trạng</label>
                            <select class="form-select" v-model="edit_suat_chieu.tinhTrang">
                                <option value="1">Hoạt Động</option>
                                <option value="0">Đã Hủy</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        Đóng
                    </button>
                    <button type="button" class="btn btn-success" data-bs-dismiss="modal"
                        v-on:click="capNhatSuatChieu()">
                        Cập nhật
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
                    <h5 class="modal-title">Xóa Suất Chiếu</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="alert alert-danger" role="alert">
                        Bạn có chắc chắn muốn xóa suất chiếu phim
                        <strong>{{ del_suat_chieu.tenPhim }}</strong> vào ngày <strong>{{ del_suat_chieu.ngayChieu
                            }}</strong>?
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        Đóng
                    </button>
                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal" v-on:click="xoaSuatChieu()">
                        Xác nhận
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Tạo Vé -->
    <div class="modal fade" id="taoVeModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Tạo Vé Cho Suất Chiếu</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="alert alert-danger" role="alert">
                        Bạn có chắc chắn muốn tạo vé cho phim
                        <strong>{{ info_ve.tenPhim }}</strong> vào ngày <strong>{{ info_ve.ngayChieu
                            }}</strong>?
                    </div>
                    <div class="alert alert-warning mt-3" role="alert">
                        ⚠️ <strong>Lưu ý:</strong> Hãy kiểm tra kỹ lại danh sách ghế và giá tiền trước khi tạo vé.
                        Sau khi tạo, việc chỉnh sửa có thể không thực hiện được.
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        Đóng
                    </button>
                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal" v-on:click="taoVeAuto()">
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
            todayStr: new Date().toISOString().split("T")[0],
            list_suat_chieu: [],
            list_phim: [],
            list_phong: [],
            create_suat_chieu: {
                id_phim: '',
                id_phong_chieu: '',
                ngay_chieu: '',
                thoi_gian_bat_dau: '',
                thoi_gian_ket_thuc: '',
                gia_ve: '',
                tinh_trang: 1
            },
            edit_suat_chieu: {},
            del_suat_chieu: {},
            info_ve: {},
            searchText: ""
        };
    },
    mounted() {
        this.layDataSuatChieu();
        this.layDataPhim();
        this.layDataPhong();
    },
    computed: {
        filteredSuatChieu() {
            const q = this.searchText.trim().toLowerCase();
            if (!q) return this.list_suat_chieu;
            return this.list_suat_chieu.filter((suat) => {
                const phim = this.getTenPhim(suat.idPhim).toLowerCase();
                const phong = this.getTenPhong(suat.idPhongChieu).toLowerCase();
                return (
                    phim.includes(q) ||
                    phong.includes(q) ||
                    suat.ngayChieu.toLowerCase().includes(q)
                );
            });
        }
    },
    methods: {
        taoVeAuto() {
            axios
                .post('http://localhost:8080/api/admin/suat-chieu/create-ve-auto', this.info_ve, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then(response => {
                    if (response.data.status) {
                        this.$toast.success(response.data.message);
                        this.layDataSuatChieu();
                    } else {
                        this.$toast.error(response.data.message);
                    }
                })
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
        layDataPhim() {
            axios
                .get('http://localhost:8080/api/admin/phims/get-data', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then(response => {
                    this.list_phim = response.data.data;
                })
        },
        layDataPhong() {
            axios
                .get('http://localhost:8080/api/admin/phong-chieu/get-data', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then(response => {
                    this.list_phong = response.data.data;
                })
        },
        themSuatChieu() {

            // Kiểm tra các trường bắt buộc
            if (!this.create_suat_chieu.idPhim || !this.create_suat_chieu.idPhongChieu || !this.create_suat_chieu.ngayChieu || !this.create_suat_chieu.thoiGianBatDau || !this.create_suat_chieu.thoiGianKetThuc) {
                this.$toast.error("Vui lòng nhập đầy đủ thông tin cho suất chiếu!");
                return;
            }



            const ngayChieu = new Date(this.create_suat_chieu.ngayChieu);
            ngayChieu.setSeconds(0);

            // ==== Validate giờ ====
            const [startHour, startMinute] = this.create_suat_chieu.thoiGianBatDau.split(":").map(Number);
            const [endHour, endMinute] = this.create_suat_chieu.thoiGianKetThuc.split(":").map(Number);

            const startTime = new Date(ngayChieu);
            startTime.setHours(startHour, startMinute, 0);

            const endTime = new Date(ngayChieu);
            endTime.setHours(endHour, endMinute, 0);

            if (endTime <= startTime) {
                this.$toast.error("Giờ kết thúc phải sau giờ bắt đầu!");
                return;
            }

            axios
                .post('http://localhost:8080/api/admin/suat-chieu/create', this.create_suat_chieu, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then(response => {
                    if (response.data.status) {
                        this.layDataSuatChieu();
                        this.create_suat_chieu = {
                            phim: '',
                            phongChieu: '',
                            ngayChieu: '',
                            thoiGianBatDau: '',
                            thoiGianKetThuc: '',
                            giaVe: '',
                            tinhTrang: 1
                        };
                        this.$toast.success(response.data.message);
                    } else {
                        this.$toast.error(response.data.message);
                    }
                })
        },
        capNhatSuatChieu() {
            axios
                .put('http://localhost:8080/api/admin/suat-chieu/update', this.edit_suat_chieu, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then(response => {
                    if (response.data.status) {
                        this.$toast.success(response.data.message);
                        this.layDataSuatChieu();
                    } else {
                        this.$toast.error(response.data.message);
                    }
                })
        },
        xoaSuatChieu() {
            axios
                .delete(`http://localhost:8080/api/admin/suat-chieu/delete/${this.del_suat_chieu.id}`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then(response => {
                    if (response.data.status) {
                        this.layDataSuatChieu();
                        this.$toast.success(response.data.message);
                    } else {
                        this.$toast.error(response.data.message);
                    }
                })
        },
        doiTrangThaiSuatChieu(item) {
            axios
                .post('http://localhost:8080/api/admin/suat-chieu/update-status', item, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then(response => {
                    if (response.data.status) {
                        this.layDataSuatChieu();
                        this.$toast.success(response.data.message);
                    } else {
                        this.$toast.error(response.data.message);
                    }
                })
        },
        
        // Hàm lấy tên phim từ idPhim
        getTenPhim(idPhim) {
            const phim = this.list_phim.find(phim => phim.id === idPhim);
            return phim ? phim.tenPhim : 'Unknown';
        },

        // Hàm lấy tên phòng chiếu từ idPhongChieu
        getTenPhong(idPhongChieu) {
            const phong = this.list_phong.find(phong => phong.id === idPhongChieu);
            return phong ? phong.tenPhong : 'Unknown';
        },

    },
};
</script>

<style></style>