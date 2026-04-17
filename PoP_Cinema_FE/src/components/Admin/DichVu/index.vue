<template>
    <div class="row">
        <div class="col-lg-4">
            <div class="card radius-10 border-top border-0 border-3 border-info">
                <div class="card-header">
                    <h4 class="card-title my-1">THÊM DỊCH VỤ</h4>
                </div>
                <div class="card-body">
                    <div class="mb-2">
                        <label class="mb-1">Tên dịch vụ</label>
                        <input v-model="create_dich_vu.tenDichVu" class="form-control" type="text"
                            placeholder="Nhập tên dịch vụ" />
                    </div>
                    <div class="mb-2">
                        <label class="mb-1">Hình ảnh</label>
                        <input v-model="create_dich_vu.hinhAnh" class="form-control" type="text"
                            placeholder="Nhập link hình ảnh" />
                    </div>
                    <div class="mb-2">
                        <label class="mb-1">Giá dịch vụ</label>
                        <input v-model="create_dich_vu.gia" class="form-control" type="number"
                            placeholder="Nhập giá dịch vụ" />
                    </div>
                    <div class="mb-2">
                        <label class="mb-1">Mô tả dịch vụ</label>
                        <textarea v-model="create_dich_vu.moTa" class="form-control" rows="3"
                            placeholder="Nhập mô tả dịch vụ"></textarea>
                    </div>
                    <div class="mb-2">
                        <label class="mb-1">Tình trạng</label>
                        <select v-model="create_dich_vu.tinhTrang" class="form-select"
                            aria-label="Default select example">
                            <option value="1">Hiển thị</option>
                            <option value="0">Tạm tắt</option>
                        </select>
                    </div>
                </div>
                <div class="card-footer d-flex justify-content-end">
                    <button type="button" class="btn btn-sm btn-primary" @click="themDichVu()">Thêm Mới</button>
                </div>
            </div>
        </div>
        <div class="col-lg-8">
            <div class="card radius-10 border-top border-0 border-3 border-info">
                <div class="card-header">
                    <h4 class="card-title my-1">DANH SÁCH DỊCH VỤ</h4>
                </div>
                <div class="card-body">
                    <div class="input-group mb-3">
                        <input type="text" class="form-control" placeholder="Tìm kiếm theo tên dịch vụ..."
                            v-model="searchText" />
                        <button class="btn btn-success input-group-text" style="width: 140px;">Tìm kiếm</button>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead class="text-center align-middle">
                                <tr class="bg-primary text-light">
                                    <th class="align-middle text-center">#</th>
                                    <th class="align-middle text-center text-nowrap">
                                        Tên dịch vụ
                                    </th>
                                    <th class="align-middle text-center">Hình ảnh</th>
                                    <th class="align-middle text-center">Giá dịch vụ</th>
                                    <th class="align-middle text-center">Mô tả dịch vụ</th>
                                    <th class="align-middle text-center">Tình trạng</th>
                                    <th class="align-middle text-center">Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <template v-for="(item, index) in filteredDichVu" :key="index">
                                    <tr>
                                        <td class="align-middle text-center">{{ index + 1 }}</td>
                                        <td class="align-middle">{{ item.tenDichVu }}</td>
                                        <td class="align-middle">
                                            <img :src="item.hinhAnh" alt="" class="img-fluid">
                                        </td>
                                        <td class="align-middle ">{{ formatVND(item.gia) }}</td>
                                        <td class="align-middle text-wrap">{{ item.moTa }}</td>
                                        <td @click="doiTrangThai(item)" class="text-center align-middle"
                                            style="width: 100px;">
                                            <button v-if="item.tinhTrang == 1" class="btn btn-sm btn-success"
                                                type="button">
                                                <i class="fa-solid fa-square-check"></i> Hiển thị
                                            </button>
                                            <button v-else class="btn btn-sm btn-warning" type="button">
                                                <i class="fa-solid fa-square-xmark"></i> Tạm tắt
                                            </button>
                                        </td>
                                        <td class="text-nowrap align-middle text-center" style="width: 150px;">
                                            <button type="button" class="btn btn-sm btn-info text-light"
                                                data-bs-toggle="modal" data-bs-target="#capNhatModal"
                                                v-on:click="Object.assign(edit_dich_vu, item)">
                                                Cập Nhật
                                            </button>
                                            <button type="button" class="ms-2 btn btn-sm btn-danger"
                                                data-bs-toggle="modal" data-bs-target="#xoaModal"
                                                v-on:click="Object.assign(del_dich_vu, item)">
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
    <!-- Modal cap nhat-->
    <div class="modal fade" id="capNhatModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Cập nhật dịch vụ</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <label class="mb-1">Tên dịch vụ</label>
                    <input v-model="edit_dich_vu.tenDichVu" class="form-control" type="text" />
                    <label class="mb-1">Hình ảnh</label>
                    <input v-model="edit_dich_vu.hinhAnh" class="form-control" type="text" />
                    <label class="mt-2">Giá dịch vụ</label>
                    <input v-model="edit_dich_vu.gia" class="form-control" type="number" />
                    <label class="mt-2">Mô tả dịch vụ</label>
                    <textarea v-model="edit_dich_vu.moTa" class="form-control" rows="3"></textarea>
                    <label class="mt-2">Tình trạng</label>
                    <select v-model="edit_dich_vu.tinhTrang" class="form-select" aria-label="Default select example">
                        <option selected>Chọn tình trạng</option>
                        <option value="1">Hiển thị</option>
                        <option value="0">Tạm tắt</option>
                    </select>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button v-on:click="capNhatDichVu()" type="button" class="btn btn-primary"
                        data-bs-dismiss="modal">Cập
                        nhật</button>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal xóa-->
    <div class="modal fade" id="xoaModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Xóa dịch vụ</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="alert alert-danger" role="alert">
                        <h6>
                            Bạn có chắc chắn muốn xóa dịch vụ
                            <b class="text-center text-danger"> {{ del_dich_vu.tenDichVu }}</b>
                            này không?
                        </h6>
                        <br />
                        <i class="fa-solid fa-circle-xmark me-2"></i><b>
                            Điều này không thể hoàn tác !!.
                        </b>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button v-on:click="xoaDichVu()" type="button" class="btn btn-danger"
                        data-bs-dismiss="modal">Xóa</button>
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
            list_dich_vu: [],
            create_dich_vu: {
                tinhTrang: 1
            },
            edit_dich_vu: {},
            del_dich_vu: {},
            searchText: ""
        };
    },
    mounted() {
        this.getListDichVu();
    },
    computed: {
        // Lọc dịch vụ theo tên (không phân biệt hoa thường)
        filteredDichVu() {
            const keyword = this.searchText.toLowerCase().trim();
            return this.list_dich_vu.filter(dich_vu =>
                dich_vu.tenDichVu.toLowerCase().includes(keyword)
            );
        },
    },
    methods: {
        formatVND(number) {
            return new Intl.NumberFormat("vi-VI", { style: "currency", currency: "VND" }).format(number,);
        },
        getListDichVu() {
            axios.get('http://localhost:8080/api/admin/dich-vu/get-data', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    this.list_dich_vu = res.data.data;
                });
        },

        // Hàm validate dữ liệu dịch vụ
        validateDichVu(dichVu) {
            const { tenDichVu, hinhAnh, gia, moTa, tinhTrang } = dichVu;

            if (!tenDichVu || !hinhAnh || !gia || !moTa || !tinhTrang) {
                this.$toast.error("Vui lòng nhập đầy đủ thông tin!");
                return false;
            }

            const giaNumber = parseFloat(gia);

            if (giaNumber <= 0) {
                this.$toast.error("Giá tiền phải lớn hơn 0!");
                return false;
            }

            return true; // Hợp lệ
        },
        themDichVu() {
            if (!this.validateDichVu(this.create_dich_vu)) return;

            axios.post('http://localhost:8080/api/admin/dich-vu/create', this.create_dich_vu, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.create_dich_vu = {};
                        this.getListDichVu();
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

        capNhatDichVu() {
            if (!this.validateDichVu(this.edit_dich_vu)) return;
            
            axios.put('http://localhost:8080/api/admin/dich-vu/update', this.edit_dich_vu, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.getListDichVu();
                    } else {
                        this.$toast.error(res.data.message);
                    }
                });
        },
        xoaDichVu() {
            axios.delete(`http://localhost:8080/api/admin/dich-vu/delete/${this.del_dich_vu.id}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.getListDichVu();
                    } else {
                        this.$toast.error(res.data.message);
                    }
                });
        },
        doiTrangThai(payload) {
            axios.post('http://localhost:8080/api/admin/dich-vu/update-status', payload, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.getListDichVu();
                    }
                    else {
                        this.$toast.error(res.data.message);
                    }
                });
        }
    },
};
</script>
<style></style>