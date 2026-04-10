<template>
    <div class="row">
        <div class="col-lg-4">
            <div class="card radius-10 border-top border-0 border-3 border-info">
                <div class="card-header">
                    <h4 class="my-1">THÊM GHẾ MỚI</h4>
                </div>
                <div class="card-body">
                    <div class="col-md-12 mb-2">
                        <label>Tên Ghế</label>
                        <input type="text" class="form-control mt-1" v-model="create_ghe.tenGhe"
                            placeholder="Nhập tên ghế" />
                    </div>
                    <div class="col-md-12 mb-2">
                        <label>Giá Ghế</label>
                        <input type="text" class="form-control mt-1" v-model="create_ghe.giaGhe"
                            placeholder="Nhập giá ghế" />
                    </div>

                    <div class="col-md-12 mb-2">
                        <label>Loại Ghế</label>
                        <select class="form-select mt-1" v-model="create_ghe.loaiGhe">
                            <option value="THUONG">Thường</option>
                            <option value="VIP">VIP</option>
                        </select>
                    </div>
                    <div class="col-md-12 mb-2">
                        <label>Phòng Chiếu</label>
                        <select class="form-select mt-1" v-model="create_ghe.phongChieu">
                            <option v-for="phong in list_phong" :key="phong.id" :value="phong.id">{{ phong.tenPhong
                                }}
                            </option>
                        </select>
                    </div>
                    <div class="col-md-12 mb-2">
                        <label>Tình Trạng</label>
                        <select class="form-select mt-1" v-model="create_ghe.tinhTrang">
                            <option value="1">Hoạt Động</option>
                            <option value="0">Tạm Dừng</option>
                        </select>
                    </div>
                </div>
                <div class="card-footer text-end">
                    <button class="btn  btn-primary" v-on:click="themGhe()">Thêm ghế</button>
                </div>
            </div>
        </div>
        <div class="col-lg-8">
            <div class="card radius-10 border-top border-0 border-3 border-info">
                <div class="card-header d-flex justify-content-between">
                    <h4 class="my-1">DANH SÁCH GHẾ</h4>
                </div>
                <div class="card-body table-responsive">
                    <div class="row mb-3 align-items-end">
                        <div class="col-5 mb-2">
                            <label>Phòng Chiếu</label>
                            <select class="form-select mt-1" v-model="selectedPhongId">
                                <option v-for="phong in list_phong" :key="phong.id" :value="phong.id">{{ phong.tenPhong
                                    }}
                                </option>
                            </select>
                        </div>
                        <div class="col-auto mb-2">
                            <button class="btn btn-success w-100" @click="locGheTheoPhongChieu()">Lọc</button>
                        </div>
                        <div class="col-auto mb-2">
                            <button class="btn btn-info w-100 text-white" @click="reset">Làm mới</button>
                        </div>

                        <div class="col-auto mb-2 ms-auto">
                            <button class="btn  btn-dark text-light me-2" @click="moModalSoDoGhe(selectedPhongId)">
                                Sơ đồ ghế
                            </button>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead>
                                <tr class="bg-primary text-light text-nowrap">
                                    <th class="text-center">#</th>
                                    <th class="text-center">Tên Ghế</th>
                                    <th class="text-center">Giá Ghế</th>
                                    <th class="text-center">Loại Ghế</th>
                                    <th class="text-center">Phòng Chiếu</th>
                                    <th class="text-center">Tình Trạng</th>
                                    <th class="text-center">Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <template v-for="(item, index) in list_ghe" :key="index">
                                    <tr class="text-nowrap">
                                        <th class="align-middle text-center" style="width: 30px;">{{ index + 1 }}</th>
                                        <td class="align-middle text-center">{{ item.tenGhe }}</td>
                                        <td class="align-middle text-center">{{ item.giaGhe }}</td>
                                        <td class="align-middle text-center">{{ item.loaiGhe }}</td>
                                        <td class="align-middle" :value="item.phongChieu">{{ item.tenPhongChieu }}</td>
                                        <td @click="doiTrangThai(item)" class="align-middle text-center"
                                            style="width: 150px;">
                                            <button v-if="item.tinhTrang == 1" class="btn  btn-success w-100"
                                                style="color: white;">
                                                Hoạt Động
                                            </button>
                                            <button v-else class="btn  btn-warning w-100" style="color: white;">
                                                Tạm Dừng
                                            </button>
                                        </td>
                                        <td class="align-middle text-center" style="width: 150px;">
                                            <button class="btn  btn-info text-light me-2" data-bs-toggle="modal"
                                                data-bs-target="#updateModal" @click="edit_ghe = item">
                                                Cập nhật
                                            </button>
                                            <button class="btn  btn-danger" data-bs-toggle="modal"
                                                data-bs-target="#deleteModal" @click="del_ghe = item">
                                                Xóa
                                            </button>
                                        </td>
                                    </tr>
                                </template>
                            </tbody>
                        </table>
                    </div>
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


    <!-- Modal Cập Nhật -->
    <div class="modal fade" id="updateModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Cập Nhật Thông Tin Ghế</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="col-md-12 mb-2">
                        <label>Tên Ghế</label>
                        <input type="text" class="form-control mt-1" v-model="edit_ghe.tenGhe"
                            placeholder="Nhập tên ghế" />
                    </div>
                    <div class="col-md-12 mb-2">
                        <label>Giá Ghế</label>
                        <input type="text" class="form-control mt-1" v-model="edit_ghe.giaGhe"
                            placeholder="Nhập tên ghế" />
                    </div>
                    <div class="col-md-12 mb-2">
                        <label>Loại Ghế</label>
                        <select class="form-select mt-1" v-model="edit_ghe.loaiGhe">
                            <option value="THUONG">Thường</option>
                            <option value="VIP">VIP</option>
                        </select>
                    </div>
                    <div class="col-md-12 mb-2">
                        <label>Phòng Chiếu</label>
                        <select class="form-select mt-1" v-model="edit_ghe.phongChieu">
                            <option v-for="phong in list_phong" :key="phong.id" :value="phong.id">{{ phong.tenPhong
                                }}
                            </option>
                        </select>
                    </div>
                    <div class="col-md-12 mb-2">
                        <label>Tình Trạng</label>
                        <select class="form-select mt-1" v-model="edit_ghe.tinhTrang">
                            <option value="1">Hoạt Động</option>
                            <option value="0">Tạm Dừng</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn  btn-secondary" data-bs-dismiss="modal">
                        Đóng
                    </button>
                    <button type="button" class="btn  btn-primary" data-bs-dismiss="modal" v-on:click="capNhatGhe()">
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
                    <h5 class="modal-title">Xóa Ghế</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="alert alert-danger" role="alert">
                        Bạn có chắc chắn muốn xóa ghế
                        <strong>{{ del_ghe.ten_ghe }}</strong> tại phòng <strong>{{ del_ghe.ten_phong }}</strong>?
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn  btn-secondary" data-bs-dismiss="modal">
                        Đóng
                    </button>
                    <button type="button" class="btn  btn-danger" data-bs-dismiss="modal" v-on:click="xoaGhe()">
                        Xác nhận
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Sơ đồ ghế -->
    <div class="modal fade" id="mapGheModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-xl modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">
                        Sơ đồ ghế - {{ ten_phong }}
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <!-- Sơ đồ ghế -->
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="card">
                                <div class="card-body">
                                    <table class="table table-borderless table-hover ">
                                        <tbody>
                                            <tr>
                                                <th colspan="100%"
                                                    class="bg-dark text-light fs-4 p-2 text-center radius-15">MÀN
                                                    HÌNH</th>
                                            </tr>
                                            <tr>
                                                <th>
                                                    <p class="mt-5"></p>
                                                </th>
                                            </tr>
                                            <template v-for="(row, key) in taoGheChoPhong" :key="key">
                                                <tr>
                                                    <template v-for="(value, index) in row" :key="index">
                                                        <th class="text-center">

                                                            <!-- Ghế trống -->

                                                            <div class="border border-dark rounded shadow-sm p-2 seat"
                                                                :style="{
                                                                    background: value.loaiGhe === 'VIP' ? 'linear-gradient(135deg, #ff4fa3, #ff9fd6)' : '#f8f9fa',
                                                                    color: value.loaiGhe === 'VIP' ? 'white' : 'black'
                                                                }">
                                                                <div class="fw-bold fs-6 ">{{
                                                                    value.tenGhe }} - {{ value.giaGhe }}
                                                                </div>
                                                                <i class="fa-solid fa-couch  m-2 "
                                                                    :class="value.loaiGhe === 'VIP' ? 'text-white' : 'text-dark'"></i>
                                                            </div>

                                                        </th>
                                                    </template>
                                                </tr>
                                            </template>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="p-1 radius-15 " style="background-color: #f97316;"></div>
                            <div class="row my-3 d-flex justify-content-between">
                                <div class="col-lg-5">
                                    <div class="d-flex align-items-center gap-3 mt-3">
                                        <div class="d-flex align-items-center">
                                            <div class="rounded me-2 border border-dark"
                                                style="width: 20px; height: 20px; background-color: #f8f9fa;">
                                            </div>
                                            <span>Ghế thường</span>
                                        </div>
                                        <div class="d-flex align-items-center">
                                            <div class="rounded me-2"
                                                style="width: 20px; height: 20px; background: linear-gradient(135deg, #ff4fa3, #ff9fd6);">
                                            </div>
                                            <span>Ghế VIP</span>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
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
            selectedPhongId: "", // ID phòng chiếu được chọn để lọc ghế
            list_ghe: [], // Danh sách ghế
            list_map_ghe: [], // Danh sách ghế để hiển thị sơ đồ ghế
            hang_ngang_phong: null, // Số ghế theo hàng ngang của phòng 
            ten_phong: "", // Tên phòng chiếu
            phongModalId: null,        // ID phòng được truyền vào modal
            list_phong: [], // Danh sách phòng chiếu

            create_ghe: {   // Thông tin ghế mới
                tinh_trang: '1'
            },
            edit_ghe: {}, // Thông tin ghế cần chỉnh sửa
            del_ghe: {}, // Thông tin ghế cần xóa

            currentPage: 1,
            totalPages: 0,
            pageSize: 16,
        };
    },
    mounted() {
        this.layDataGhe();
        this.layDataPhong();


    },
    computed: {
        // Tạo mảng ghế theo hàng ngang của phòng
        taoGheChoPhong() {
            let a = [];
            for (let i = 0; i < this.list_map_ghe.length; i += this.hang_ngang_phong) {
                a.push(this.list_map_ghe.slice(i, i + this.hang_ngang_phong));
            }
            return a;
        },
    },
    methods: {

        // layDataGhe() {
        //     axios
        //         .get('http://localhost:8080/api/admin/ghe/get-data', {
        //             headers: {
        //                 Authorization: `Bearer ${token}`
        //             }
        //         })
        //         .then(response => {
        //             this.list_ghe = response.data.data;
        //         })
        // },
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


        // / Hàm validate ghế
        validateGhe(ghe) {
            // Kiểm tra trường bắt buộc
            if (!ghe.tenGhe || !ghe.giaGhe || !ghe.phongChieu || !ghe.loaiGhe || !ghe.tinhTrang) {
                this.$toast.error("Vui lòng nhập đầy đủ thông tin!");
                return false;
            }

            // Kiểm tra giá ghế là số và >= 0
            if (isNaN(ghe.giaGhe) || ghe.giaGhe < 0) {
                this.$toast.error("Giá ghế phải là số và không được âm!");
                return false;
            }



            return true;
        },
        themGhe() {
            if (!this.validateGhe(this.create_ghe)) return;
            axios
                .post('http://localhost:8080/api/admin/ghe/create', this.create_ghe, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then(response => {
                    if (response.data.status) {
                        this.layDataGhe();
                        this.create_ghe = {};
                        this.$toast.success(response.data.message);
                    } else {
                        this.$toast.error(response.data.message);
                    }
                })
        },
        capNhatGhe() {
             if (!this.validateGhe(this.edit_ghe)) return;
            axios
                .put('http://localhost:8080/api/admin/ghe/update', this.edit_ghe, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then(response => {
                    if (response.data.status) {
                        this.$toast.success(response.data.message);
                        // Đảm bảo currentPage tồn tại
                        const page = this.currentPage ?? 1;
                        //  Nếu đang lọc theo phòng chiếu, reload lại đúng danh sách phòng đó
                        if (this.selectedPhongId) {
                            this.locGheTheoPhongChieu(false);
                        } else {
                            this.layDataGhe(page);
                        }

                    }
                })
        },
        xoaGhe() {
            axios
                .delete(`http://localhost:8080/api/admin/ghe/delete/${this.del_ghe.id}`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then(response => {
                    if (response.data.status) {
                        // Đảm bảo currentPage tồn tại
                        const page = this.currentPage ?? 1;
                        this.layDataGhe(page);
                        this.$toast.success(response.data.message);
                    } else {
                        this.$toast.error(response.data.message);
                    }
                })
        },

        // Đổi trạng thái ghế
        doiTrangThai(payload) {
            axios.post('http://localhost:8080/api/admin/ghe/update-status', payload, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        // Đảm bảo currentPage tồn tại
                        const page = this.currentPage ?? 1;

                        //  Nếu đang lọc theo phòng chiếu, reload lại đúng danh sách phòng đó
                        if (this.selectedPhongId) {
                            this.locGheTheoPhongChieu(false);
                        } else {
                            this.layDataGhe(page);
                        }
                    }else{
                         this.$toast.error(res.data.message);
                    }
                });
        },

        // Lọc ghế theo phòng chiếu
        locGheTheoPhongChieu(showToast = true) {

            if (!this.selectedPhongId) {
                this.$toast.warning("Vui lòng chọn phòng chiếu để lọc ghế.");
                return;
            }

            axios.get(`http://localhost:8080/api/admin/ghe/get-ghe-by-phong`, {
                params: { phongChieuId: this.selectedPhongId },
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {

                    if (res.data) {
                        if (showToast) {
                            this.$toast.success("Lọc ghế thành công.");
                        }

                        // Reload đúng trang hiện tại
                        this.list_ghe = res.data.ghes;

                    }

                })
                .catch((err) => {
                    this.$toast.error("Error:", err.response);
                });
        },

        // Hàm lấy dữ liệu ghế với phân trang
        layDataGhe(page = 1) {
            axios
                .get('http://localhost:8080/api/admin/ghe/get-all-ghe', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    },
                    params: {
                        pageNo: page - 1,   // backend bắt đầu từ 0
                        pageSize: this.pageSize
                    }
                })
                .then(response => {

                    this.list_ghe = response.data.ghes;
                    this.currentPage = (response.data.pageNo ?? 0) + 1;
                    this.totalPages = response.data.totalPages ?? 1;
                })
                .catch(error => {
                    console.error("Lỗi khi tải dữ liệu ghế:", error);
                });
        },

        reset() {
            this.layDataGhe();
        },

        // Hàm mở modal sơ đồ ghế
        moModalSoDoGhe(selectedPhongId) {
            if (!selectedPhongId) {
                this.$toast.warning("Vui lòng chọn phòng chiếu trước khi xem sơ đồ.");
                return;
            }

            // Gán ID phòng chiếu vào modal
            axios
                .get(`http://localhost:8080/api/admin/ghe/get-map-ghe/${selectedPhongId}`, {

                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then((res) => {
                    if (res.data.status) {
                        this.list_map_ghe = res.data.data_ghe || [];
                        this.hang_ngang_phong = res.data.hang_ngang || 0;
                        this.ten_phong = res.data.ten_phong || "";
                        console.log("map", this.list_map_ghe);
                        console.log("hang", this.hang_ngang_phong);

                        // ✅ Hiển thị modal sau khi load dữ liệu thành công
                        const modal = new bootstrap.Modal(document.getElementById('mapGheModal'));
                        modal.show();
                    }
                })
                .catch((err) => {
                    console.error("Lỗi khi tải sơ đồ ghế:", err);
                });
        },

        // Hàm thay đổi trang
        changePage(page) {
            if (page >= 1 && page <= this.totalPages) {
                this.layDataGhe(page);
            }
        }

    },
};
</script>

<style src="./index.css"></style>