<template>
    <div class="row">
        <div class="col-lg-12">
            <div class="card radius-10 border-top border-0 border-3 border-info">
                <div class="card-header d-flex justify-content-between">
                    <h4 class="mt-2"><b>DANH SÁCH NHÂN VIÊN</b></h4>
                    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addModal">
                        Thêm nhân viên
                    </button>
                </div>
                <div class="card-body table-responsive">
                    <div class="input-group mb-3">
                        <input type="text" class="form-control"
                            placeholder="Tìm kiếm theo tên, email, số điện thoại...." v-model="searchText">
                        <button class="btn btn-success input-group-text" style="width: 140px;">Tìm kiếm</button>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead>
                                <tr class="bg-primary text-light text-nowrap">
                                    <th class="text-center">#</th>
                                    <th class="text-center">Họ Và Tên</th>
                                    <th class="text-center">Email</th>
                                    <th class="text-center">Số Điện Thoại</th>
                                    <th class="text-center">Địa Chỉ</th>
                                    <th class="text-center">Ngày Sinh</th>
                                    <th class="text-center">Chức Vụ</th>
                                    <th class="text-center">Tình Trạng</th>
                                    <th class="text-center">Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <template v-for="(item, index) in filteredNhanVien" :key="index">
                                    <tr class="text-nowrap">
                                        <th class="align-middle text-center">{{ index + 1 }}</th>
                                        <td class="align-middle">{{ item.hoVaTen }}</td>
                                        <td class="align-middle">{{ item.email }}</td>
                                        <td class="align-middle text-center">{{ item.soDienThoai }}</td>
                                        <td class="align-middle">{{ item.diaChi }}</td>
                                        <td class="align-middle text-center">{{ item.ngaySinh }}</td>
                                        <td class="align-middle">{{ item.tenChucVu }}</td>
                                        <td @click="doiTrangThai(item)" class="align-middle text-center">
                                            <button v-if="item.tinhTrang == 1" class="btn btn-info w-100"
                                                style="color: white;">
                                                Hoạt động
                                            </button>
                                            <button v-else class="btn btn-warning w-100" style="color: white;">
                                                Tạm tắt
                                            </button>
                                        </td>
                                        <td class="align-middle text-center">
                                            <button class="btn btn-success me-2" data-bs-toggle="modal"
                                                data-bs-target="#updateModal"
                                                v-on:click="Object.assign(edit_nhan_vien, item)">
                                                Cập nhật
                                            </button>
                                            <button class="btn btn-danger" data-bs-toggle="modal"
                                                data-bs-target="#deleteModal"
                                                v-on:click="Object.assign(del_nhan_vien, item)">
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
                    <h5 class="modal-title">Thêm Nhân Viên Mới</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Họ Và Tên</label>
                            <input v-model="create_nhan_vien.hoVaTen" type="text" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Email</label>
                            <input v-model="create_nhan_vien.email" type="email" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Mật Khẩu</label>
                            <input v-model="create_nhan_vien.passWord" type="password" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Xác Nhận Mật Khẩu</label>
                            <input v-model="create_nhan_vien.confirmPassWord" type="password" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Số Điện Thoại</label>
                            <input v-model="create_nhan_vien.soDienThoai" type="text" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Ngày Sinh</label>
                            <input v-model="create_nhan_vien.ngaySinh" type="date" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Địa Chỉ</label>
                            <input v-model="create_nhan_vien.diaChi" type="text" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Tình Trạng</label>
                            <select v-model="create_nhan_vien.tinhTrang" class="form-select">
                                <option value="1">Hoạt động</option>
                                <option value="0">Tạm tắt</option>
                            </select>
                        </div>
                        <div class="col-md-12 mb-3">
                            <label class="form-label">Chức Vụ</label>
                            <select v-model="create_nhan_vien.idChucVu" class="form-select">
                                <template v-for="(item, index) in list_chuc_vu" :key="index">
                                    <option :value="item.id">{{ item.tenChucVu }}</option>
                                </template>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        Đóng
                    </button>
                    <button type="button" class="btn btn-primary" v-on:click="themNhanVien()">
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
                    <h5 class="modal-title">Cập Nhật Thông Tin Nhân Viên</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Họ Và Tên</label>
                            <input v-model="edit_nhan_vien.hoVaTen" type="text" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Email</label>
                            <input v-model="edit_nhan_vien.email" type="email" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Số Điện Thoại</label>
                            <input v-model="edit_nhan_vien.soDienThoai" type="text" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Ngày Sinh</label>
                            <input v-model="edit_nhan_vien.ngaySinh" type="date" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Địa Chỉ</label>
                            <input v-model="edit_nhan_vien.diaChi" type="text" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Tình Trạng</label>
                            <select v-model="edit_nhan_vien.tinhTrang" class="form-select">
                                <option value="1">Hoạt động</option>
                                <option value="0">Tạm tắt</option>
                            </select>
                        </div>
                        <div class="col-md-12 mb-3">
                            <label class="form-label">Chức Vụ</label>
                            <select v-model="edit_nhan_vien.idChucVu" class="form-select">
                                <template v-for="(item, index) in list_chuc_vu" :key="index">
                                    <option :value="item.id">{{ item.tenChucVu }}</option>
                                </template>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        Đóng
                    </button>
                    <button type="button" class="btn btn-success" data-bs-dismiss="modal"
                        v-on:click="capNhatNhanVien()">
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
                    <h5 class="modal-title">Xóa Nhân Viên</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="alert alert-danger" role="alert">
                        Bạn có chắc chắn muốn xóa nhân viên
                        <strong>{{ del_nhan_vien.ho_va_ten }}</strong>?
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        Đóng
                    </button>
                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal" v-on:click="xoaNhanVien()">
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
            list_nhan_vien: [],
            list_chuc_vu: [],
            create_nhan_vien: {
                hoVaTen: "",
                email: "",
                password: "",
                confirmPassWord: "",
                soDienThoai: "",
                diaChi: "",
                ngaySinh: "",
                tinhTrang: 1,
                idChucVu: "",
            },
            edit_nhan_vien: {},
            del_nhan_vien: {},
            searchText: ''
        };
    },
    mounted() {
        this.getNhanVien();
        this.getChucVu();
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
    methods: {
        getChucVu() {
            axios.get('http://localhost:8080/api/admin/chuc-vus/get-data', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    this.list_chuc_vu = res.data.data;
                })
        },
        getNhanVien() {
            axios
                .get('http://localhost:8080/api/admin/nhan-viens/get-data', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then((res) => {
                    this.list_nhan_vien = res.data.data;
                })
        },

        validateNhanVien(nv) {
            // Kiểm tra xem có để trống trường nào không
            if (!nv.hoVaTen || !nv.email || !nv.soDienThoai || !nv.passWord || !nv.confirmPassWord || !nv.diaChi || !nv.ngaySinh || !nv.idChucVu) {
                this.$toast.error("Vui lòng điền đầy đủ thông tin");
                return false;
            }

            // Kiểm tra định dạng email
            const regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!regexEmail.test(nv.email)) {
                this.$toast.error("Email không hợp lệ");
                return false;
            }


            // Regex mật khẩu giống backend
            const regexPass = /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
            if (!regexPass.test(nv.passWord)) {
                this.$toast.error("Mật khẩu phải có ít nhất 8 ký tự, gồm chữ hoa, số và ký tự đặc biệt");
                return false;
            }

            // Kiểm tra mật khẩu xác nhận
            if (nv.passWord !== nv.confirmPassWord) {
                this.$toast.error("Mật khẩu xác nhận không khớp");
                return false;
            }

            // Kiểm tra định dạng số điện thoại (0 + 9-10 chữ số)
            const regexPhone = /^0\d{9,10}$/;
            if (!regexPhone.test(nv.soDienThoai)) {
                this.$toast.error("Số điện thoại không hợp lệ");
                return false;
            }

        

           // Kiểm tra ngày sinh thực tế
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
                this.$toast.error("Nhân viên phải từ 18 tuổi trở lên");
                return false;
            }

            // Không được lớn hơn 65 tuổi (tuổi lao động)
            if (realAge > 65) {
                this.$toast.error("Tuổi vượt quá giới hạn cho phép (tối đa 65)");
                return false;
            }


            return true; // tất cả hợp lệ
        },

        //Hàm thêm nhân viên
        themNhanVien() {
            const nv = this.create_nhan_vien;

            if (!this.validateNhanVien(nv)) return; // kiểm tra validate trước

            axios.post('http://localhost:8080/api/admin/nhan-viens/create', this.create_nhan_vien, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.create_nhan_vien = {};
                        this.getNhanVien();
                    } else {
                        this.$toast.error(res.data.message);
                    }
                })
                .catch((error) => {
                    console.error("Lỗi khi gọi API:", error);
                    if (error.response) {
                        // Trường hợp server trả lỗi (4xx, 5xx)
                        this.$toast.error(error.response.data.message || "Lỗi từ server");
                    } else if (error.request) {
                        // Trường hợp request gửi đi nhưng không nhận response
                        this.$toast.error("Không nhận được phản hồi từ server");
                    } else {
                        // Trường hợp lỗi khác (lỗi cấu hình axios chẳng hạn)
                        this.$toast.error("Có lỗi xảy ra khi gửi request");
                    }
                });
        },

        //Hàm kiểm tra dữ liệu khi cập nhật nhân viên
        validateEditNhanVien(nv) {
            // Kiểm tra xem có để trống trường nào không
            if (!nv.hoVaTen || !nv.email || !nv.soDienThoai || !nv.diaChi || !nv.ngaySinh || !nv.idChucVu) {
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

            // Kiểm tra ngày sinh thực tế
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
                this.$toast.error("Nhân viên phải từ 18 tuổi trở lên");
                return false;
            }

            // Không được lớn hơn 65 tuổi (tuổi lao động)
            if (realAge > 65) {
                this.$toast.error("Tuổi vượt quá giới hạn cho phép (tối đa 65)");
                return false;
            }


            return true; // tất cả hợp lệ
        },

        capNhatNhanVien() {
            if (!this.validateEditNhanVien(this.edit_nhan_vien)) return; // kiểm tra validate trước

            axios.put('http://localhost:8080/api/admin/nhan-viens/update', this.edit_nhan_vien, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.getNhanVien();
                    } else {
                        this.$toast.error('Cập nhật thông tin thất bại');
                    }
                }).catch((error) => {
                    console.error("Lỗi khi gọi API:", error);
                    if (error.response) {
                        // Trường hợp server trả lỗi (4xx, 5xx)
                        this.$toast.error(error.response.data.message || "Lỗi từ server");
                    } else if (error.request) {
                        // Trường hợp request gửi đi nhưng không nhận response
                        this.$toast.error("Không nhận được phản hồi từ server");
                    } else {
                        // Trường hợp lỗi khác (lỗi cấu hình axios chẳng hạn)
                        this.$toast.error("Có lỗi xảy ra khi gửi request");
                    }
                });
        },
        xoaNhanVien() {
            axios.delete(`http://localhost:8080/api/admin/nhan-viens/delete/${this.del_nhan_vien.id}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.getNhanVien();
                    }
                    else {
                        this.$toast.error(res.data.message);
                    }
                });
        },
        doiTrangThai(payload) {
            axios.post('http://localhost:8080/api/admin/nhan-viens/update-status', payload, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.getNhanVien();
                    } else {
                        this.$toast.error(res.data.message);
                    }
                });
        }

    },
};
</script>

<style></style>
