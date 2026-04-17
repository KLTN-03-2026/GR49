<template>
    <div class="row">
        <div class="col-lg-8">
            <div class="row">
                <div class="col-lg-12">
                    <div class="card radius-10 border-top border-0 border-3 border-info">
                        <div class="card-header d-flex justify-content-between align-items-center">
                            <h5 class="mb-0">Danh Sách Chức Vụ</h5>
                            <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#themMoiModal">Thêm
                                Mới</button>
                        </div>
                        <div class="card-body">
                            <div class="input-group mb-3">
                                <input type="text" class="form-control" placeholder="Tìm kiếm theo tên..." v-model="search">
                                <button class="btn btn-success input-group-text" style="width: 100px;">Tìm kiếm</button>
                            </div>
                            <div class="table-responsive">
                                <table class="table table-bordered">
                                    <thead class="text-center align-middle">
                                        <tr class="bg-primary text-light">
                                            <th>#</th>
                                            <th>Tên Chức Vụ</th>
                                            <th>Slug Chức Vụ</th>
                                            <th>Trạng Thái</th>
                                            <th>Cấp Quyền</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <template v-for="(value, index) in filteredChucVu" :key="index">
                                            <tr class="align-middle">
                                                <th class="text-center">{{ index + 1 }}</th>
                                                <td>{{ value.tenChucVu }}</td>
                                                <td>{{ value.slugChucVu }}</td>
                                                <td @click="doiTrangThai(value)" class="text-center">
                                                    <button v-if="value.tinhTrang == 1"
                                                        class="btn btn-success text-white">Hoạt
                                                        Động</button>
                                                    <button v-else class="btn btn-warning text-white">Tạm Dừng</button>
                                                </td>
                                                <td class="text-center">
                                                    <button class="btn btn-info text-white"
                                                        @click="Object.assign(create_phan_quyen, value), layDataPhanQuyen()">Phân
                                                        Quyền</button>
                                                </td>
                                                <td class="text-center">
                                                    <i class="fa-solid fa-square-pen fa-3x text-primary me-2"
                                                        data-bs-toggle="modal" data-bs-target="#updateModal"
                                                        @click="Object.assign(update_chuc_vu, value)"></i>
                                                    <i class="fa-solid fa-trash fa-3x text-danger"
                                                        data-bs-toggle="modal" data-bs-target="#delModal"
                                                        @click="Object.assign(delete_chuc_vu, value)"></i>
                                                </td>
                                            </tr>
                                        </template>
                                    </tbody>
                                </table>
                            </div>
                        </div>


                    </div>
                </div>
                <div class="col-lg-12">
                    <div class="card radius-10 border-top border-0 border-3 border-info">
                        <div class="card-header">
                            <h5 class="mb-0">Đang Phân Quyền Cho <b class="text-danger">{{ create_phan_quyen.tenChucVu
                                || "......"
                                    }}</b></h5>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered">
                                    <thead>
                                        <tr class="text-center text-nowrap align-middle bg-primary text-light">
                                            <th>#</th>
                                            <th>Tên Chức Năng</th>
                                            <th>Tên Quyền</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                         <template v-for="(value, index) in list_phan_quyen" :key="index">
                                        <tr class="align-middle">
                                            <td class="text-wrap">{{ index + 1 }}</td>
                                            <td class="text-wrap">{{ value.tenChucNang }}</td>
                                            <td>{{ value.tenChucVu }}</td>
                                            <td class="text-center">
                                                <button class="btn btn-danger"   data-bs-toggle="modal" data-bs-target="#delquyenModal" @click="Object.assign(delete_quyen, value)">Xóa</button>
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
        </div>

        <div class="col-lg-4">
            <div class="card radius-10 border-top border-0 border-3 border-info">
                <div class="card-header">
                    <h5 class="mb-0">Danh Sách Chức Năng</h5>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="table-responsive">
                            <table class="table table-bordered">
                                <thead>
                                    <tr class="text-center text-nowrap align-middle bg-primary text-light">
                                        <th>#</th>
                                        <th>Tên Chức Năng</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <template v-for="(value, index) in list_chuc_nang" :key="index">
                                        <tr class="align-middle">
                                            <th class="text-center">{{ index + 1 }}</th>
                                            <td class="text-wrap">{{ value.tenChucNang }}</td>
                                            <td class="text-center">
                                                <button class="btn btn-success" @click="themPhanQuyen(value)">Cấp
                                                    Quyền</button>
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
    </div>


    <!-- Modal Thêm Mới Chức Vụ -->
    <div class="modal fade" id="themMoiModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Thêm Mới Chức Vụ</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-2">
                        <label>Tên Chức Vụ</label>
                        <input type="text" class="form-control mt-1" v-model="create_chuc_vu.tenChucVu"
                            placeholder="Nhập tên chức vụ">
                    </div>
                    <div class="mb-2">
                        <label>Slug Chức Vụ</label>
                        <input type="text" class="form-control mt-1" v-model="create_chuc_vu.slugChucVu"
                            placeholder="Nhập slug chức vụ">
                    </div>
                    <div class="mb-2">
                        <label>Trạng Thái</label>
                        <select class="form-select mt-1" v-model="create_chuc_vu.tinhTrang">
                            <option value="1">Hoạt Động</option>
                            <option value="0">Tạm Dừng</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    <button @click="taoChucVu()" type="button" class="btn btn-primary">Tạo
                        Mới</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Cập Nhật Chức Vụ -->
    <div class="modal fade" id="updateModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Cập Nhật Chức Vụ</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-2">
                        <label>Tên Chức Vụ</label>
                        <input type="text" class="form-control mt-1" v-model="update_chuc_vu.tenChucVu"
                            placeholder="Nhập tên chức vụ">
                    </div>
                    <div class="mb-2">
                        <label>Slug Chức Vụ</label>
                        <input type="text" class="form-control mt-1" v-model="update_chuc_vu.slugChucVu"
                            placeholder="Nhập slug chức vụ">
                    </div>
                    <div class="mb-2">
                        <label>Trạng Thái</label>
                        <select class="form-select mt-1" v-model="update_chuc_vu.tinhTrang">
                            <option value="1">Hoạt Động</option>
                            <option value="0">Tạm Dừng</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    <button @click="capNhatChucVu()" type="button" class="btn btn-primary" data-bs-dismiss="modal">Cập
                        Nhật</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Xóa Chức Vụ -->
    <div class="modal fade" id="delModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Xóa Chức Vụ</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="alert alert-warning border-0 bg-warning alert-dismissible fade show py-2">
                        <div class="alert alert-warning border-0 bg-warning alert-dismissible fade show py-2">
                            <div class="d-flex align-items-center">
                                <div class="font-35 text-dark"><i class="bx bx-info-circle"></i>
                                </div>
                                <div class="ms-3">
                                    <h6 class="mb-0 text-dark">Bạn có chắc chắn muốn xóa chức vụ
                                        <b> {{ delete_chuc_vu.ten_chuc_vu }} </b>
                                        này không?
                                    </h6>
                                    <div class="text-dark"><b>Lưu ý: </b>Điều này không thể hoàn tác khi nhấn xác nhận
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    <button @click="xoaChucVu()" type="button" class="btn btn-danger"
                        data-bs-dismiss="modal">Xóa</button>
                </div>
            </div>
        </div>
    </div>

     <!-- Modal Xóa quyền -->
    <div class="modal fade" id="delquyenModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Xóa Quyền</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="alert alert-warning border-0 bg-warning alert-dismissible fade show py-2">
                        <div class="alert alert-warning border-0 bg-warning alert-dismissible fade show py-2">
                            <div class="d-flex align-items-center">
                                <div class="font-35 text-dark"><i class="bx bx-info-circle"></i>
                                </div>
                                <div class="ms-3">
                                    <h6 class="mb-0 text-dark">Bạn có chắc chắn muốn xóa quyền
                                        <b> {{ delete_quyen.id }} </b>
                                        <!-- này không? -->
                                    </h6>
                                    <!-- <div class="text-dark"><b>Lưu ý: </b>Điều này không thể hoàn tác khi nhấn xác nhận
                                    </div> -->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    <button @click="xoaPhanQuyen()" type="button" class="btn btn-danger"
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
            list_chuc_nang: [],
            list_chuc_vu: [],
            create_chuc_vu: {
                'tinh_trang': 1,
            },
            update_chuc_vu: {},
            delete_chuc_vu: {},
            delete_quyen: {},
            create_phan_quyen: {
                idChucVu: null,
            },
            list_phan_quyen: [],
            search: '',


        }
    },
    mounted() {
        this.layDataChucVu();
        this.layDataChucNang();
        // this.layDataPhanQuyen();
    },
      computed: {
        // Lọc chức vụ theo tên (không phân biệt hoa thường)
        filteredChucVu() {
            const keyword = this.search.toLowerCase().trim();
            return this.list_chuc_vu.filter(chucVu =>
                chucVu.tenChucVu.toLowerCase().includes(keyword)
            );
        },
    },
    methods: {
        layDataChucVu() {
            axios.get('http://localhost:8080/api/admin/chuc-vus/get-data', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then(response => {
                    this.list_chuc_vu = response.data.data;
                });
        },
        layDataChucNang() {
            axios.get('http://localhost:8080/api/admin/phan-quyens/get-chucNang', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then(response => {
                    this.list_chuc_nang = response.data.data;
                });
        },


        layDataPhanQuyen() {
            axios
                .post('http://localhost:8080/api/admin/phan-quyens/get-data/' + this.create_phan_quyen.id,null, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then(response => {
                    if (response.data.status){
                        // this.$toast.success(response.data.message);
                        this.list_phan_quyen = response.data.data;
                    }else{
                        this.$toast.error(response.data.message);
                    }
                    
                })
        },
        taoChucVu() {
            if (!this.create_chuc_vu.tenChucVu || !this.create_chuc_vu.slugChucVu) {
                this.$toast.error('Vui lòng nhập đầy đủ thông tin chức vụ');
                return;
            }
            axios.post('http://localhost:8080/api/admin/chuc-vus/create', this.create_chuc_vu, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.create_chuc_vu = {};
                        this.layDataChucVu();
                    }else{
                        this.$toast.error(res.data.message);
                    }
                });
        },
        capNhatChucVu() {
            axios.put('http://localhost:8080/api/admin/chuc-vus/update', this.update_chuc_vu, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.layDataChucVu();
                    }else{
                        this.$toast.error(res.data.message);
                    }
                });
        },
        xoaChucVu() {
            axios.delete('http://localhost:8080/api/admin/chuc-vus/delete/' + this.delete_chuc_vu.id, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.layDataChucVu();
                    }else{
                        this.$toast.error(res.data.message);
                    }
                });
        },
        doiTrangThai(payload) {
            axios.post('http://localhost:8080/api/admin/chuc-vus/update-status', payload, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.layDataChucVu();
                    }else{
                        this.$toast.error(res.data.message);
                    }
                });
        },

         themPhanQuyen(value) {
            if (!this.create_phan_quyen.id) {
                this.$toast.error('Vui lòng chọn chức vụ để phân quyền');
                return;
            }
            var payload = {
                idChucNang: value.id,
                idChucVu: this.create_phan_quyen.id,
            }
            axios
                .post('http://localhost:8080/api/admin/phan-quyens/create', payload, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then(response => {
                    if (response.data.status) {
                        this.$toast.success(response.data.message);
                        this.layDataChucNang();
                        this.layDataPhanQuyen();
                    } else {
                        this.$toast.error(response.data.message);
                    }
                })
                 .catch(res => {
                    const list = Object.values(res.response.data.errors);
                    list.forEach((v, i) => {
                        this.$toast.error(v[0]);
                    });
                });
        },

         xoaPhanQuyen() {
            axios.delete(`http://localhost:8080/api/admin/phan-quyens/delete/${this.delete_quyen.id}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.layDataPhanQuyen();
                    }
                });
        },
    }
}
</script>

<style></style>