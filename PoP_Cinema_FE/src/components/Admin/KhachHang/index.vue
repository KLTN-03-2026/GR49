<template>
    <div class="row">
        <div class="col-lg-12">
            <div class="card radius-10 border-top border-0 border-3 border-info">
                <div class="card-header d-flex justify-content-between">
                    <h4 class="mt-2"><b>DANH SÁCH KHÁCH HÀNG</b></h4>
                    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addModal">
                        Thêm Khách Hàng
                    </button>
                </div>
                <div class="card-body table-responsive">
                    <div class="input-group mb-3">
                        <input type="text" class="form-control"
                            placeholder="Tìm kiếm theo tên, email, số điện thoại...." v-model="searchText">
                        <button class="btn btn-success input-group-text" style="width: 155px;">Tìm kiếm</button>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead>
                                <tr class="bg-primary text-light text-nowrap">
                                    <th class="text-center">#</th>
                                    <th class="text-center">Họ Và Tên</th>
                                    <th class="text-center">Email</th>
                                    <th class="text-center">Số Điện Thoại</th>
                                    <th class="text-center">Ngày Sinh</th>
                                    <th class="text-center">Kich Hoạt</th>
                                    <th class="text-center">Tình Trạng</th>
                                    <th class="text-center">Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <template v-for="(item, index) in filteredKhachHang" :key="index">
                                    <tr class="text-nowrap">
                                        <th class="align-middle text-center">{{ index + 1 }}</th>
                                        <td class="align-middle">{{ item.hoVaTen }}</td>
                                        <td class="align-middle">{{ item.email }}</td>
                                        <td class="align-middle text-center">{{ item.soDienThoai }}</td>
                                        <td class="align-middle text-center">{{ item.ngaySinh }}</td>
                                        <td @click="doiKichHoat(item)" class="align-middle text-center">
                                            <button v-if="item.isActive == 1" class="btn btn-warning text-light w-100"
                                                style="color: white;">
                                                Đã Kích Hoạt
                                            </button>
                                            <button v-else class="btn btn-secondary w-100">
                                                Chưa Kích Hoạt
                                            </button>
                                        </td>
                                        <td @click="doiTrangThai(item)" class="align-middle text-center">
                                            <button v-if="item.isBlock == 1" class="btn btn-danger w-100"
                                                style="color: white;">
                                                Đã Khóa
                                            </button>
                                            <button v-else class="btn btn-info w-100" style="color: white;">
                                                Chưa Khóa
                                            </button>
                                        </td>
                                        <td class="align-middle text-center" style="width: 200px;">
                                            <button class="btn btn-success me-2" data-bs-toggle="modal"
                                                data-bs-target="#updateModal" @click="edit_khach_hang = item">
                                                Cập nhật
                                            </button>
                                            <button class="btn btn-danger" data-bs-toggle="modal"
                                                data-bs-target="#deleteModal" @click="del_khach_hang = item">
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
                    <h5 class="modal-title">Thêm Khách Hàng Mới</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Họ Và Tên</label>
                            <input v-model="create_khach_hang.ho_va_ten" type="text" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Email</label>
                            <input v-model="create_khach_hang.email" type="email" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Mật Khẩu</label>
                            <input v-model="create_khach_hang.password" type="password" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Số Điện Thoại</label>
                            <input v-model="create_khach_hang.so_dien_thoai" type="text" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Ngày Sinh</label>
                            <input v-model="create_khach_hang.ngay_sinh" type="date" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">CCCD</label>
                            <input v-model="create_khach_hang.cccd" type="text" class="form-control" />
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        Đóng
                    </button>
                    <button type="button" class="btn btn-primary" data-bs-dismiss="modal" v-on:click="themNhanVien()">
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
                    <h5 class="modal-title">Cập Nhật Thông Tin Khách Hàng</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Họ Và Tên</label>
                            <input v-model="edit_khach_hang.hoVaTen" type="text" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Email</label>
                            <input v-model="edit_khach_hang.email" type="email" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Mật Khẩu</label>
                            <input v-model="edit_khach_hang.passWord" type="password" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Số Điện Thoại</label>
                            <input v-model="edit_khach_hang.soDienThoai" type="text" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Ngày Sinh</label>
                            <input v-model="edit_khach_hang.ngaySinh" type="date" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">CCCD</label>
                            <input v-model="edit_khach_hang.cccd" type="text" class="form-control" />
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        Đóng
                    </button>
                    <button type="button" class="btn btn-success" data-bs-dismiss="modal" @click="capNhatKhachHang()">
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
                    <h5 class="modal-title">Xóa Khách Hàng</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="alert alert-danger" role="alert">
                        Bạn có chắc chắn muốn xóa khách hàng
                        <strong>{{ del_khach_hang.hoVaTen }}</strong>?
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        Đóng
                    </button>
                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal" v-on:click="xoaKhachHang()">
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
            list_khach_hang: [],
            create_khach_hang: {},
            edit_khach_hang: {},
            del_khach_hang: {},
            searchText: ''
        };
    },
    mounted() {
        this.layDataKhachHang();
    },
    computed: {
        // Lọc khách hàng theo tên (không phân biệt hoa thường)
        filteredKhachHang() {
            const keyword = this.searchText.toLowerCase().trim();
            return this.list_khach_hang.filter((kh) => {
                const ho_va_ten = kh.hoVaTen.toLowerCase();
                const so_dien_thoai = kh.soDienThoai.toLowerCase();
                const email = kh.email.toLowerCase();
                return (
                    ho_va_ten.includes(keyword) ||
                    so_dien_thoai.includes(keyword) ||
                    email.includes(keyword)
                );
            });
        },
    },
    methods: {
        layDataKhachHang() {
            axios
                .get('http://localhost:8080/api/admin/khach-hangs/get-data', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then(response => {
                    this.list_khach_hang = response.data.data;
                })
        },
        themNhanVien() {
            axios
                .post('http://127.0.0.1:8000/api/admin/khach-hang/add-data', this.create_khach_hang)
                .then(response => {
                    if (response.data.status) {
                        this.layDataKhachHang();
                        this.create_khach_hang = {};
                        this.$toast.success(response.data.message);
                    } else {
                        this.$toast.error(response.data.message);
                    }
                })
        },
        //Hàm kiểm tra dữ liệu khi cập nhật khách hàng
        validateEditKhachHang(nv) {
            // Kiểm tra xem có để trống trường nào không
            if (!nv.hoVaTen || !nv.email || !nv.soDienThoai || !nv.ngaySinh || !nv.cccd) {
                this.$toast.error("Vui lòng điền đầy đủ thông tin");
                return false;
            }

            // Kiểm tra định dạng email
            const regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!regexEmail.test(nv.email)) {
                this.$toast.error("Email không hợp lệ");
                return false;
            }

            // Kiểm tra định dạng số điện thoại (0 + 9-10 chữ số)
            const regexPhone = /^0\d{9,10}$/;
            if (!regexPhone.test(nv.soDienThoai)) {
                this.$toast.error("Số điện thoại không hợp lệ");
                return false;
            }

            // Kiểm tra định dạng CCCD (12 số)
            const regexCCCD = /^\d{12}$/;
            if (!regexCCCD.test(nv.cccd)) {
                this.$toast.error("CCCD phải gồm 12 số");
                return false;
            }

            // Kiểm tra ngày sinh không vượt quá ngày hiện tại
            const today = new Date();
            const birthday = new Date(nv.ngaySinh);

            if (isNaN(birthday.getTime())) {
                this.$toast.error("Ngày sinh không hợp lệ");
                return false;
            }

            // Tính tuổi
            const age = today.getFullYear() - birthday.getFullYear();
            const monthDiff = today.getMonth() - birthday.getMonth();
            const dayDiff = today.getDate() - birthday.getDate();

            // Điều chỉnh tuổi nếu chưa tới ngày sinh năm nay
            let realAge = age;
            if (monthDiff < 0 || (monthDiff === 0 && dayDiff < 0)) {
                realAge--;
            }

            // Không được nhỏ hơn 18 tuổi
            if (realAge < 18) {
                this.$toast.error("Khách hàng phải từ 18 tuổi trở lên");
                return false;
            }

            return true; // tất cả hợp lệ
        },
        capNhatKhachHang() {
            if (!this.validateEditKhachHang(this.edit_khach_hang)) {
                return; // Dừng nếu dữ liệu không hợp lệ
            }
            axios
                .put('http://localhost:8080/api/admin/khach-hangs/update', this.edit_khach_hang, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then(response => {
                    if (response.data.status) {
                        this.layDataKhachHang();
                        this.$toast.success(response.data.message);
                    } else {
                        this.$toast.error(response.data.message);
                    }
                })
        },
        xoaKhachHang() {
            axios
                .delete(`http://localhost:8080/api/admin/khach-hangs/delete/${this.del_khach_hang.id}`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then(response => {
                    if (response.data.status) {
                        this.layDataKhachHang();
                        this.$toast.success(response.data.message);
                    } else {
                        this.$toast.error(response.data.message);
                    }
                })
        },
        doiTrangThai(payload) {
            axios
                .post('http://localhost:8080/api/admin/khach-hangs/update-status', payload, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then(response => {
                    if (response.data.status) {
                        this.layDataKhachHang();
                        this.$toast.success(response.data.message);
                    } else {
                        this.$toast.error(response.data.message);
                    }
                })
        },
        doiKichHoat(payload) {
            axios
                .post('http://localhost:8080/api/admin/khach-hangs/update-kich-hoat', payload, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then(response => {
                    if (response.data.status) {
                        this.layDataKhachHang();
                        this.$toast.success(response.data.message);
                    } else {
                        this.$toast.error(response.data.message);
                    }
                })
        }
    },
};
</script>

<style></style>
