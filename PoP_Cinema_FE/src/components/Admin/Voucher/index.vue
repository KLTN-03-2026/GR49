<template>
    <div class="row">
        <div class="col-lg-12">
            <div class="card radius-10 border-top border-0 border-3 border-warning">
                <div class="card-header d-flex align-items-center justify-content-between">
                    <h4 class="mb-0">DANH SÁCH VOUCHER</h4>
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#themMoiModal">
                        Thêm mới
                    </button>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead class="text-center align-middle">
                                <tr class="bg-primary text-light">
                                    <th class="align-middle text-center">#</th>
                                    <th class="align-middle text-center text-nowrap">
                                        Mã Voucher
                                    </th>
                                    <th class="align-middle text-center">Tên Voucher</th>
                                    <th class="align-middle text-center">Hình ảnh</th>
                                    <th class="align-middle text-center">Mô tả</th>
                                    <th class="align-middle text-center">Thời gian bắt đầu</th>
                                    <th class="align-middle text-center">Thời gian kết thúc</th>
                                    <th class="align-middle text-center">Số giảm giá</th>
                                    <th class="align-middle text-center">Số tiền tối đa</th>
                                    <th class="align-middle text-center">Số tiền giảm giá</th>
                                    <th class="align-middle text-center">Tình trạng</th>
                                    <th class="align-middle text-center">Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="(item, index) in list_voucher" :key="index">
                                    <td class="align-middle text-center">{{ index + 1 }}</td>
                                    <td class="align-middle ">{{ item.maCode }}</td>
                                    <td class="text-center align-middle">{{ item.tenVoucher }}</td>
                                    <td class="align-middle">
                                        <img :src="item.hinhAnh" alt="" class="img-fluid">
                                    </td>
                                    <td class="text-center align-middle">{{ item.moTa }}</td>
                                    <td class="align-middle text-center">{{ convertDate(item.thoiGianBatDau) }}</td>
                                    <td class="align-middle text-center">{{ convertDate(item.thoiGianKetThuc) }}</td>
                                    <td class="text-center align-middle">{{ item.soGiamGia }}%</td>
                                    <td class="text-center align-middle">{{ formatVND(item.soTienToiDa) }}</td>
                                    <td class="text-center align-middle">{{ formatVND(item.soTienGiamGia) }}</td>
                                    <td @click="doiTrangThai(item)" class="text-center align-middle"
                                        style="width: 100px;">
                                        <button v-if="item.tinhTrang == 1" type="button" class="btn btn-sm btn-success">
                                            Hoạt Động
                                        </button>
                                        <button v-else type="button" class="btn btn-sm btn-warning">
                                            Tạm Dừng
                                        </button>
                                    </td>
                                    <td class="text-nowrap align-middle text-center" style="width: 150px;">
                                        <button type="button" class="btn btn-sm btn-info text-light"
                                            data-bs-toggle="modal" data-bs-target="#capNhatModal"
                                            v-on:click="Object.assign(edit_voucher, item)">
                                            Cập Nhật
                                        </button>
                                        <button type="button" class="ms-2 btn btn-sm btn-danger" data-bs-toggle="modal"
                                            data-bs-target="#xoaModal" v-on:click="Object.assign(del_voucher, item)">
                                            Xóa
                                        </button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal thêm mới -->
    <div class="modal fade" id="themMoiModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Thêm mới voucher</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <label class="mt-2">Tên Voucher</label>
                            <input v-model="create_voucher.tenVoucher" class="form-control" type="text" />
                        </div>
                        <div class="col-lg-12">
                            <label class="mt-2">Mã Voucher</label>
                            <input v-model="create_voucher.maCode" class="form-control" type="text" />
                        </div>
                        <div class="col-lg-12">
                            <label class="mt-2">Hình Ảnh</label>
                            <input v-model="create_voucher.hinhAnh" class="form-control" type="text" />
                        </div>
                        <div class="col-lg-12">
                            <label class="mt-2">Mô Tả</label>
                            <input v-model="create_voucher.moTa" class="form-control" type="text" />
                        </div>
                        <div class="col-lg-6">
                            <label class="mt-2">Thời gian bắt đầu</label>
                            <input v-model="create_voucher.thoiGianBatDau" class="form-control" type="date" />
                        </div>
                        <div class="col-lg-6">
                            <label class="mt-2">Thời gian kết thúc</label>
                            <input v-model="create_voucher.thoiGianKetThuc" class="form-control" type="date" />
                        </div>
                        <div class="col-lg-6">
                            <label class="mt-2">Số giảm giá</label>
                            <input v-model="create_voucher.soGiamGia" class="form-control" type="number" />
                        </div>
                        <div class="col-lg-6">
                            <label class="mt-2">Số tiền tối đa</label>
                            <input v-model="create_voucher.soTienToiDa" class="form-control" type="number" />
                        </div>
                        <div class="col-lg-6">
                            <label class="mt-2">Số tiền giảm giá</label>
                            <input v-model="create_voucher.soTienGiamGia" class="form-control" type="number" />
                        </div>
                        <div class="col-lg-6">
                            <label class="mt-2">Tình trạng</label>
                            <select v-model="create_voucher.tinhTrang" class="form-select"
                                aria-label="Default select example">
                                <option value="1">Hiển thị</option>
                                <option value="0">Tạm tắt</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button @click="themVoucher()" type="button" class="btn btn-primary">Thêm
                        Mới</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal cap nhat -->
    <div class="modal fade" id="capNhatModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Cập nhật voucher</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <label class="mt-2">Tên Voucher</label>
                            <input v-model="edit_voucher.tenVoucher" class="form-control" type="text" />
                        </div>
                        <div class="col-lg-12">
                            <label class="mt-2">Mã Voucher</label>
                            <input v-model="edit_voucher.maCode" class="form-control" type="text" />
                        </div>
                        <div class="col-lg-12">
                            <label class="mt-2">Hình Ảnh</label>
                            <input v-model="edit_voucher.hinhAnh" class="form-control" type="text" />
                        </div>
                        <div class="col-lg-12">
                            <label class="mt-2">Mô Tả</label>
                            <input v-model="edit_voucher.moTa" class="form-control" type="text" />
                        </div>
                        <div class="col-lg-6">
                            <label class="mt-2">Thời gian bắt đầu</label>
                            <input v-model="edit_voucher.thoiGianBatDau" class="form-control" type="date" />
                        </div>
                        <div class="col-lg-6">
                            <label class="mt-2">Thời gian kết thúc</label>
                            <input v-model="edit_voucher.thoiGianKetThuc" class="form-control" type="date" />
                        </div>
                        <div class="col-lg-6">
                            <label class="mt-2">Số giảm giá</label>
                            <input v-model="edit_voucher.soGiamGia" class="form-control" type="text" />
                        </div>
                        <div class="col-lg-6">
                            <label class="mt-2">Số tiền tối đa</label>
                            <input v-model="edit_voucher.soTienToiDa" class="form-control" type="text" />
                        </div>
                        <div class="col-lg-6">
                            <label class="mt-2">Số tiền giảm giá</label>
                            <input v-model="edit_voucher.soTienGiamGia" class="form-control" type="text" />
                        </div>
                        <div class="col-lg-6">
                            <label class="mt-2">Tình trạng</label>
                            <select v-model="edit_voucher.tinhTrang" class="form-select"
                                aria-label="Default select example">
                                <option value="1">Hiển thị</option>
                                <option value="0">Tạm tắt</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button @click="capNhatVoucher()" type="button" class="btn btn-primary" data-bs-dismiss="modal">Cập
                        nhật</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal xóa -->
    <div class="modal fade" id="xoaModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Xóa voucher</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="alert alert-warning border-0 bg-warning alert-dismissible fade show py-2">
                        <div class="d-flex align-items-center">
                            <div class="font-35 text-dark"><i class="bx bx-info-circle"></i>
                            </div>
                            <div class="ms-3">
                                <h6 class="mb-0 text-dark">Bạn có chắc chắn muốn xóa voucher
                                    <b> {{ del_voucher.tenVoucher }} </b>
                                    này không?
                                </h6>
                                <div class="text-dark"><b>Lưu ý: </b>Điều này không thể hoàn tác khi nhấn xác nhận</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy Bỏ</button>
                    <button @click="xoaVoucher()" type="button" class="btn btn-danger" data-bs-dismiss="modal">Xác
                        Nhận</button>
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
            list_voucher: [],
            create_voucher: {
                tinhTrang: 1
            },
            edit_voucher: {},
            del_voucher: {}
        }
    },
    mounted() {
        this.getVoucher();
    },
    methods: {
        formatVND(number) {
            return new Intl.NumberFormat("vi-VI", { style: "currency", currency: "VND" }).format(number,);
        },
        getVoucher() {
            axios.get('http://localhost:8080/api/admin/voucher/get-data', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    this.list_voucher = res.data.data;
                });
        },

        // Hàm kiểm tra hợp lệ của voucher
        validateVoucher(voucher) {
            // Kiểm tra trường bắt buộc
            if (!voucher.tenVoucher || !voucher.maCode || !voucher.thoiGianBatDau || !voucher.thoiGianKetThuc || !voucher.soGiamGia || !voucher.soTienToiDa || !voucher.soTienGiamGia) {
                this.$toast.error("Vui lòng nhập đầy đủ thông tin!");
                return false;
            }

            // Kiểm tra ngày bắt đầu < ngày kết thúc
            if (new Date(voucher.thoiGianKetThuc) <= new Date(voucher.thoiGianBatDau)) {
                this.$toast.error("Ngày kết thúc phải sau ngày bắt đầu!");
                return false;
            }

            // Kiểm tra số giảm giá (phần trăm)
            if (voucher.soGiamGia < 0 || voucher.soGiamGia > 100) {
                this.$toast.error("Số giảm giá phải từ 0 đến 100%");
                return false;
            }

            // Kiểm tra số tiền giảm giá
            if (voucher.soTienGiamGia < 0) {
                this.$toast.error("Số tiền giảm giá không được âm");
                return false;
            }

            // Kiểm tra số tiền tối đa
            if (voucher.soTienToiDa < 0) {
                this.$toast.error("Số tiền tối đa không được âm");
                return false;
            }

            // Kiểm tra số tiền giảm giá
            if (voucher.soTienGiamGia < 0) {
                this.$toast.error("Số tiền giảm giá không được âm");
                return false;
            }

            if (voucher.soTienGiamGia > voucher.soTienToiDa) {
                this.$toast.error("Số tiền giảm giá không được lớn hơn số tiền tối đa áp dụng");
                return false;
            }

            return true;
        },

        themVoucher() {
            if (!this.validateVoucher(this.create_voucher)) return;

            axios.post('http://localhost:8080/api/admin/voucher/create', this.create_voucher, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.create_voucher = {};
                        this.getVoucher();
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
        convertDate(date) {
            return new Date(date).toLocaleDateString('vi-VN');
        },

        capNhatVoucher() {
            if (!this.validateVoucher(this.edit_voucher)) return;

            axios.put('http://localhost:8080/api/admin/voucher/update', this.edit_voucher, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.getVoucher();
                    } else {
                        this.$toast.error(res.data.message);
                    }
                });
        },
        xoaVoucher() {
            axios.delete(`http://localhost:8080/api/admin/voucher/delete/${this.del_voucher.id}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.getVoucher();
                    } else {
                        this.$toast.error(res.data.message);
                    }
                });
        },
        doiTrangThai(payload) {
            axios.post('http://localhost:8080/api/admin/voucher/update-status', payload, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.getVoucher();
                    } else {
                        this.$toast.error(res.data.message);
                    }
                });
        }


    }
} 
</script>
<style></style>