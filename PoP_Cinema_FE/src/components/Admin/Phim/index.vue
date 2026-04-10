<template>
    <div class="row">
        <div class="col-lg-12">
            <div class="card radius-10 border-top border-0 border-3 border-info">
                <div class="card-header d-flex justify-content-between">
                    <h4 class="mt-2"><b>DANH SÁCH PHIM</b></h4>
                    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addModal">
                        Thêm phim
                    </button>
                </div>
                <div class="card-body table-responsive">
                    <div class="input-group mb-3">
                        <input type="text" class="form-control" placeholder="Tìm kiếm phim theo tên..." v-model="searchText">
                        <button class="btn btn-success input-group-text" style="width: 110px;">Tìm kiếm</button>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead>
                                <tr class="bg-primary text-light text-nowrap">
                                    <th class="text-center">#</th>
                                    <th class="text-center">Tên Phim</th>
                                    <th class="text-center">Hình Ảnh</th>
                                    <th class="text-center">Đạo Diễn</th>
                                    <th class="text-center">Ngày Phát Hành</th>
                                    <th class="text-center">Quốc Gia</th>
                                    <th class="text-center">Trạng Thái</th>
                                    <th class="text-center">Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <template v-for="(item, index) in filteredPhim" :key="index">
                                    <tr>
                                        <th class="align-middle text-center">{{ index + 1 }}</th>
                                        <td class="align-middle text-wrap">{{ item.tenPhim }}</td>
                                        <td class="align-middle">
                                            <img :src="item.hinhAnh" alt="Hình Ảnh" class="img-fluid"
                                                style="width: 100px; height: 100px; object-fit: cover;">
                                        </td>
                                        <td class="align-middle">{{ item.daoDien }}</td>
                                        <td class="align-middle text-center">{{ item.ngayPhatHanh }}</td>
                                        <td class="align-middle text-center">{{ item.quocGia }}</td>
                                        <td @click="doiTrangThai(item)" class="align-middle text-center text-nowrap"
                                            style="width: 140px;">
                                            <!-- Ví dụ: 1 = Sắp chiếu, 2 = Đang chiếu, 0 = Ngừng chiếu -->
                                            <button v-if="item.tinhTrang == 0" class="btn btn-danger w-100"
                                                style="color: white;">
                                                Ngừng Chiếu
                                            </button>
                                            <button v-if="item.tinhTrang == 1" class="btn btn-warning w-100"
                                                style="color: white;">
                                                Sắp Chiếu
                                            </button>
                                            <button v-if="item.tinhTrang == 2" class="btn btn-success w-100"
                                                style="color: white;">
                                                Đang Chiếu
                                            </button>

                                        </td>
                                        <td class="align-middle text-center text-nowrap" style="width: 200px;">
                                            <button class="btn btn-secondary me-2"
                                                @click="Object.assign(obj_phim_chi_tiet, item)" style="color: white;"
                                                data-bs-toggle="modal" data-bs-target="#chiTietModal">
                                                Chi Tiết
                                            </button>
                                            <button class="btn btn-info text-light me-2" data-bs-toggle="modal"
                                                data-bs-target="#updateModal"
                                                v-on:click="Object.assign(edit_phim, item)">
                                                Cập nhật
                                            </button>
                                            <button class="btn btn-danger" data-bs-toggle="modal"
                                                data-bs-target="#deleteModal"
                                                v-on:click="Object.assign(del_phim, item)">
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
        <div class="modal-dialog modal-xl">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Thêm Phim Mới</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Tên Phim</label>
                            <input v-model="create_phim.tenPhim" type="text" class="form-control" placeholder="Nhập tên phim" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Hình Ảnh</label>
                            <input v-model="create_phim.hinhAnh" type="text" class="form-control" placeholder="Nhập link hình ảnh"/>
                        </div>
                        <div class="col-md-3 mb-3">
                            <label class="form-label">Đạo Diễn</label>
                            <input v-model="create_phim.daoDien" type="text" class="form-control" placeholder="Nhập tên đạo diễn"/>
                        </div>
                        <div class="col-md-5 mb-3">
                            <label class="form-label">Diễn Viên</label>
                            <input v-model="create_phim.dienVien" type="text" class="form-control" placeholder="Nhập tên diễn viên"/>
                        </div>
                        <div class="col-md-4 mb-3">
                            <label class="form-label">Nhà Sản Xuất</label>
                            <input v-model="create_phim.nhaSanXuat" type="text" class="form-control" placeholder="Nhập tên nhà sản xuất"/>
                        </div>
                        <div class="col-md-4 mb-3">
                            <label class="form-label">Quốc Gia</label>
                            <input v-model="create_phim.quocGia" type="text" class="form-control" placeholder="Nhập quốc gia"/>
                        </div>
                        <div class="col-md-4 mb-3">
                            <label class="form-label">Ngôn Ngữ</label>
                            <input v-model="create_phim.ngonNgu" type="text" class="form-control" placeholder="Nhập ngôn ngữ"/>
                        </div>
                        <div class="col-md-4 mb-3">
                            <label class="form-label">Ngày Phát Hành</label>
                            <input v-model="create_phim.ngayPhatHanh" type="date" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Thể Loại</label>
                            <input v-model="create_phim.theLoai" type="text" class="form-control" placeholder="Nhập thể loại"/>
                        </div>
                        <div class="col-md-3 mb-3">
                            <label class="form-label">Thời Lượng</label>
                            <input v-model="create_phim.thoiLuong" type="number" class="form-control" />
                        </div>
                        <div class="col-md-3 mb-3">
                            <label class="form-label">Trạng Thái</label>
                            <select v-model="create_phim.tinhTrang" class="form-select">
                                <option value="0">Ngừng Chiếu</option>
                                <option value="1">Sắp Chiếu</option>
                                <option value="2">Đang Chiếu</option>
                            </select>
                        </div>
                        <div class="col-md-12 mb-3">
                            <label class="form-label">Mô Tả</label>
                            <textarea v-model="create_phim.noiDung" class="form-control" rows="3" placeholder="Nhập mô tả phim"></textarea>
                        </div>
                        <div class="col-md-12 mb-3">
                            <label class="form-label">Trailer</label>
                            <input v-model="create_phim.trailer" class="form-control" rows="3" placeholder="Nhập link trailer" />
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        Đóng
                    </button>
                    <button type="button" class="btn btn-primary"  v-on:click="themPhim()">
                        Thêm mới
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Cập Nhật -->
    <div class="modal fade" id="updateModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-xl">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Cập Nhật Thông Tin Phim</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Tên Phim</label>
                            <input v-model="edit_phim.tenPhim" type="text" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Hình Ảnh</label>
                            <input v-model="edit_phim.hinhAnh" type="text" class="form-control" />
                        </div>
                        <div class="col-md-3 mb-3">
                            <label class="form-label">Đạo Diễn</label>
                            <input v-model="edit_phim.daoDien" type="text" class="form-control" />
                        </div>
                        <div class="col-md-5 mb-3">
                            <label class="form-label">Diễn Viên</label>
                            <input v-model="edit_phim.dienVien" type="text" class="form-control" />
                        </div>
                        <div class="col-md-4 mb-3">
                            <label class="form-label">Nhà Sản Xuất</label>
                            <input v-model="edit_phim.nhaSanXuat" type="text" class="form-control" />
                        </div>
                        <div class="col-md-4 mb-3">
                            <label class="form-label">Quốc Gia</label>
                            <input v-model="edit_phim.quocGia" type="text" class="form-control">
                        </div>
                        <div class="col-md-4 mb-3">
                            <label class="form-label">Ngôn Ngữ</label>
                            <input v-model="edit_phim.ngonNgu" type="text" class="form-control">
                        </div>
                        <div class="col-md-4 mb-3">
                            <label class="form-label">Ngày Phát Hành</label>
                            <input v-model="edit_phim.ngayPhatHanh" type="date" class="form-control" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Thể Loại</label>
                            <input v-model="edit_phim.theLoai" type="text" class="form-control">
                        </div>
                        <div class="col-md-3 mb-3">
                            <label class="form-label">Thời Lượng</label>
                            <input v-model="edit_phim.thoiLuong" type="number" class="form-control" />
                        </div>
                        <div class="col-md-3 mb-3">
                            <label class="form-label">Trạng Thái</label>
                            <select v-model="edit_phim.tinhTrang" class="form-select">
                                <option value="0">Ngừng Chiếu</option>
                                <option value="1">Sắp Chiếu</option>
                                <option value="2">Đang Chiếu</option>
                            </select>
                        </div>
                        <div class="col-md-12 mb-3">
                            <label class="form-label">Mô Tả</label>
                            <textarea v-model="edit_phim.noiDung" class="form-control" rows="3"></textarea>
                        </div>
                        <div class="col-md-12 mb-3">
                            <label class="form-label">Trailer</label>
                            <input v-model="edit_phim.trailer" class="form-control" rows="3" />
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        Đóng
                    </button>
                    <button type="button" class="btn btn-success" data-bs-dismiss="modal" v-on:click="capNhatPhim()">
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
                    <h5 class="modal-title">Xóa Phim</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="alert alert-danger" role="alert">
                        Bạn có chắc chắn muốn xóa phim
                        <strong>{{ del_phim.tenPhim }}</strong>?
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        Đóng
                    </button>
                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal" v-on:click="xoaPhim()">
                        Xác nhận
                    </button>
                </div>
            </div>
        </div>
    </div>
    <!-- modal chi tiết -->
    <div class="modal fade" id="chiTietModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-xl">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Chi Tiết Phim</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="ratio ratio-16x9 shadow-lg overflow-hidden" style="height: 400px;">
                                <iframe :src="obj_phim_chi_tiet.trailer" title="YouTube video player"
                                    allowfullscreen></iframe>
                            </div>
                        </div>
                    </div>
                    <div class="row mt-3">
                        <div class="col-lg-4 text-center mb-3 shadow-lg overflow-hidden rounded">
                            <img :src="obj_phim_chi_tiet.hinhAnh" alt="Hình Ảnh" class="img-fluid rounded mt-3"
                                style="max-height: 300px;">
                        </div>
                        <div class="col-lg-8 shadow-sm overflow-hidden">
                            <table class="table table-bordered">
                                <tbody>
                                    <tr>
                                        <th style="width: 35%">Tên Phim</th>
                                        <td>{{ obj_phim_chi_tiet.tenPhim }}</td>
                                    </tr>
                                    <tr>
                                        <th>Đạo Diễn</th>
                                        <td>{{ obj_phim_chi_tiet.daoDien }}</td>
                                    </tr>
                                    <tr>
                                        <th>Diễn Viên</th>
                                        <td>{{ obj_phim_chi_tiet.dienVien }}</td>
                                    </tr>
                                    <tr>
                                        <th>Nhà Sản Xuất</th>
                                        <td>{{ obj_phim_chi_tiet.nhaSanXuat }}</td>
                                    </tr>
                                    <tr>
                                        <th>Ngày Phát Hành</th>
                                        <td>{{ obj_phim_chi_tiet.ngayPhatHanh }}</td>
                                    </tr>
                                    <tr>
                                        <th>Thể Loại</th>
                                        <td>{{ obj_phim_chi_tiet.theLoai }}</td>
                                    </tr>
                                    <tr>
                                        <th>Ngôn Ngữ</th>
                                        <td>{{ obj_phim_chi_tiet.ngonNgu }}</td>
                                    </tr>
                                    <tr>
                                        <th>Thời Lượng</th>
                                        <td>{{ obj_phim_chi_tiet.thoiLuong }} phút</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="col-lg-12 shadow-lg overflow-hidden rounded">
                            <h5 class="mt-2">Nội dung phim</h5>
                            <p>{{ obj_phim_chi_tiet.noiDung }}</p>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        Đóng
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
            list_phim: [],
            obj_phim_chi_tiet: {},
            create_phim: {},
            edit_phim: {},
            del_phim: {},
            searchText: '',
        };
    },
    mounted() {
        this.getPhim();
    },
    computed: {
        // Lọc phim theo tên (không phân biệt hoa thường)
        filteredPhim() {
            const keyword = this.searchText.toLowerCase().trim();
            return this.list_phim.filter(phim =>
                phim.tenPhim.toLowerCase().includes(keyword)
            );
        },
    },
    methods: {

        getPhim() {
            axios
                .get('http://localhost:8080/api/admin/phims/get-data', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then((res) => {
                    this.list_phim = res.data.data;
                })
        },
        themPhim() {

            // Kiểm tra các trường bắt buộc
            if (!this.create_phim.tenPhim || !this.create_phim.hinhAnh || !this.create_phim.daoDien || !this.create_phim.dienVien 
            || !this.create_phim.ngayPhatHanh || !this.create_phim.quocGia || !this.create_phim.tinhTrang
              || !this.create_phim.noiDung || !this.create_phim.trailer || !this.create_phim.theLoai || !this.create_phim.thoiLuong
            || !this.create_phim.nhaSanXuat || !this.create_phim.ngonNgu) {
                
                 this.$toast.error("Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            //  Kiểm tra tồn tại trong listPhim(đã load sẵn)
            const exists = this.list_phim.some(phim =>
                phim.tenPhim.trim().toLowerCase() === this.create_phim.tenPhim.trim().toLowerCase()
            );

            if (exists) {
                this.$toast.error("Phim này đã tồn tại!");
                return;
            }

            axios.post('http://localhost:8080/api/admin/phims/create', this.create_phim, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.create_phim = {};
                        this.getPhim();
                    }
                }) .catch((err) => {
                    console.error("Error:", err.response);
                });
        },
        capNhatPhim() {

            // Kiểm tra các trường bắt buộc
            if (!this.edit_phim.tenPhim || !this.edit_phim.hinhAnh || !this.edit_phim.daoDien || !this.edit_phim.dienVien 
            || !this.edit_phim.ngayPhatHanh || !this.edit_phim.quocGia || !this.edit_phim.tinhTrang
              || !this.edit_phim.noiDung || !this.edit_phim.trailer || !this.edit_phim.theLoai || !this.edit_phim.thoiLuong
            || !this.edit_phim.nhaSanXuat || !this.edit_phim.ngonNgu) {
                
                 this.$toast.error("Vui lòng nhập đầy đủ thông tin!");
                return;
            }
            axios.put('http://localhost:8080/api/admin/phims/update', this.edit_phim, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.getPhim();
                    }else{
                        this.$toast.error(res.data.message);
                    }
                });
        },
        xoaPhim() {
            axios.delete(`http://localhost:8080/api/admin/phims/delete/${this.del_phim.id}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }

            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.getPhim();
                    }else{
                        this.$toast.error(res.data.message);
                    }
                });
        },
        doiTrangThai(value) {
            axios.post('http://localhost:8080/api/admin/phims/update-status', value, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.getPhim();
                    }else{
                        this.$toast.error(res.data.message);
                    }
                });
        }
    },
};
</script>

<style></style>
