<template>
    <div class="container mt-4">

        <!-- Sơ đồ ghế -->
        <div class="row">
            <div class="col-lg-12">
                <div class="card">
                    <div class="card-body">
                        <table class="table table-borderless table-hover ">
                            <tbody>
                                <tr>
                                    <th colspan="100%" class="bg-dark text-light fs-4 p-2 text-center radius-15">MÀN
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
                                            <th @click="chonGhe(value)" class="text-center">

                                                <!-- Ghế đã bán -->
                                                <template v-if="value.donHang > 0">
                                                    <div class="border rounded shadow-sm p-2 bg-light"
                                                        style="color: #d6d6d6;">
                                                        <div class="fw-bold fs-6 ">{{ value.tenGhe }} - {{
                                                            value.giaVe }}</div>
                                                        <i class="fa-solid fa-couch  m-2" style="color: #d6d6d6;"></i>
                                                    </div>
                                                </template>


                                                <!-- Ghế trống -->
                                                <template v-else-if="value.donHang == null">
                                                    <div class="border border-dark rounded shadow-sm p-2 seat" :style="{
                                                        background: value.loaiVe === 'VIP' ? 'linear-gradient(135deg, #ff4fa3, #ff9fd6)' : '#f8f9fa',
                                                        color: value.loaiVe === 'VIP' ? 'white' : 'black'
                                                    }">
                                                        <div class="fw-bold fs-6 ">{{
                                                            value.tenGhe }} - {{ value.giaVe }}
                                                        </div>
                                                        <i class="fa-solid fa-couch  m-2 "
                                                            :class="value.loaiVe === 'VIP' ? 'text-white' : 'text-dark'"></i>
                                                    </div>
                                                </template>

                                                <!-- Chọn ghế -->
                                                <template v-else>
                                                    <div class="border border-warning rounded shadow-sm p-2 bg-light">
                                                        <div class="fw-bold fs-6 " style="color: #f97316;">{{
                                                            value.tenGhe }} - {{ value.giaVe }}</div>
                                                        <i class="fa-solid fa-couch  m-2" style="color: #f97316;"></i>
                                                    </div>
                                                </template>
                                            </th>
                                        </template>
                                    </tr>
                                </template>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!-- Thông tin loại ghế -->
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
                            <div class="d-flex align-items-center">
                                <div class="rounded me-2" style="width: 20px; height: 20px; background-color: #d6d6d6;">
                                </div>
                                <span>Ghế đã bán</span>
                            </div>
                            <div class="d-flex align-items-center">
                                <div class="rounded me-2" style="width: 20px; height: 20px; background-color: #f97316;">
                                </div>
                                <span>Ghế đang chọn</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-auto mb-2 ms-auto">
                        <button class="btn  btn-dark text-light me-2" @click="openModal = true">Tham quan phòng
                            VR360°</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Danh sách dịch vụ và thông tin phim + suất chiếu + ghế -->
        <div class="row">
            <div class="col-lg-6">
                <div class="card radius-10 border-top border-0 border-5 border-success">
                    <div class="card-body">
                        <div class="mb-3">
                            <h4>Danh Sách Dịch Vụ</h4>
                        </div>
                        <div v-for="(value, index) in list_ben_trai" :key="index" class="card">
                            <div class="row g-0">
                                <div class="col-md-4">
                                    <img :src="value.hinhAnh" alt="..." class="card-img "
                                        style="height: 180px; object-fit: cover;">
                                </div>
                                <div class="col-md-8">
                                    <div class="card-body">
                                        <h5 class="card-title">{{ value.tenDichVu }}</h5>
                                        <p class="card-text">{{ value.moTa }}</p>
                                        <div class="d-flex justify-content-between align-items-center">
                                            <p class="card-text fw-bold mb-0">Giá: {{ formatVND(value.gia) }}</p>
                                            <button @click="themDichvu(value)" class="btn btn-outline-primary">Thêm dịch
                                                vụ</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Thông tin phim và suất chiếu  -->
            <div class="col-lg-6">
                <div class="card radius-10 border-top border-0 border-5 border-warning">
                    <div class="card-body">
                        <div class="d-flex mb-2">
                            <img :src="thong_tin_phim.hinhAnh" alt="Poster phim" class="img-fluid rounded me-3"
                                style="width: 150px; height: 270px; object-fit: cover;">
                            <div>
                                <h6 class="mb-1 fw-bold">{{ thong_tin_phim.tenPhim }}</h6>
                                <p class="mb-1 text-muted small">{{ thong_tin_phim.ngonNgu }}</p>
                            </div>
                        </div>
                        <p class="mb-3">Suất chiếu: <strong>{{ thong_tin_phim.thoiGianBatDau }}- {{
                            thong_tin_phim.thoiGianKetThuc }}</strong> -
                            <strong>{{ formatDate(thong_tin_phim.ngayChieu) }}</strong>
                        </p>
                        <hr class="my-2" style="border: 1px dashed;">

                        <!-- v-for ghế  -->
                        <template v-for="(value, index) in list_ben_phai" :key="index">
                            <template v-if="value.type == 1">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>Ghế {{ value.tenGhe }}</div>
                                    <div class="d-flex align-items-center">
                                        <div class="me-2">{{ formatVND(value.giaVe) }}</div>
                                        <i @click="xoaBo(value)"
                                            class="fa-solid fa-rectangle-xmark fa-2x text-danger"></i>
                                    </div>
                                </div>
                                <hr class="my-2" style="border: 1px dashed;">
                            </template>
                        </template>

                        <!-- v-for dịch vụ -->
                        <template v-for="(value, index) in list_ben_phai" :key="index">
                            <template v-if="value.type == 2">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>{{ value.tenGhe }}</div>
                                    <div class="d-flex align-items-center">
                                        <div class="me-2">{{ formatVND(value.giaVe) }}</div>
                                        <i @click="xoaBo(value)"
                                            class="fa-solid fa-rectangle-xmark fa-2x text-danger"></i>
                                    </div>
                                </div>
                                <hr class="my-2" style="border: 1px dashed;">
                            </template>
                        </template>
                        <div class="d-flex justify-content-start mt-1">
                            <input v-model="ma_voucher" type="text" class="form-control me-4"
                                placeholder="Nhập vào mã voucher">
                            <button v-on:click="thongTinVoucher()" class="btn btn-info text-light text-nowrap">Áp
                                Dụng</button>
                        </div>
                        <hr class="my-2" style="border: 1px dashed;">
                        <div class="d-flex justify-content-between">
                            <strong>Tổng cộng</strong>
                            <strong class="text-danger">{{ formatVND(tongCong) }}</strong>
                        </div>
                    </div>
                    <div class="card-footer">
                        <div class="row">
                            <div class="col-lg-6">
                                <button class="btn btn-outline-dark w-100 me-2"> Quay Lại</button>
                            </div>
                            <div class="col-lg-6">
                                <button class="btn btn-warning w-100" data-bs-toggle="modal"
                                    data-bs-target="#exampleModal">Thanh Toán </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <!-- Modal Thanh Toán-->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Thông Tin Đặt Vé</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row mb-2">
                        <div class="col-5 text-muted">Tên Phim:</div>
                        <div class="col-7 text-end fw-semibold">{{ thong_tin_phim.tenPhim ||
                            "Khôngcó"
                            }}
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-5 text-muted">Suất Chiếu:</div>
                        <div class="col-7 text-end fw-semibold">
                            {{ thong_tin_phim.thoiGianBatDau }} - {{
                                thong_tin_phim.thoiGianKetThuc }} -
                            {{ thong_tin_phim.ngayChieu }}
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-5 text-muted">Rạp:</div>
                        <div class="col-7 text-end">OIZOIOI Cinema</div>
                    </div>

                    <div class="row mb-2">
                        <!-- Số lượng vé -->
                        <div class="col-5 text-muted">Số lượng vé: {{list_ben_phai.filter(item => item.type ===
                            1).length
                        }}</div>
                        <!-- Danh sách ghế -->
                        <div class="col-7 text-end">Ghế: <strong>{{list_ben_phai.filter(item => item.type
                            === 1).map(item=>
                                item.tenGhe).join(', ') }}</strong></div>

                    </div>

                    <div class="row mb-2">
                        <div class="col-5 text-muted">Dịch vụ:</div>
                        <!-- Danh sách dịch vụ -->
                        <div class="col-7 text-end"><strong>{{list_ben_phai.filter(item => item.type === 2).map(item =>
                            item.tenGhe).join(', ') || "Không có dịch vụ đi kèm"}}</strong></div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" v-on:click="thanhToan()" class="btn btn-primary">Xác nhận đặt vé</button>
                </div>
            </div>
        </div>
    </div>

    <!-- ✅ Modal Tour 3D -->
    <div v-if="openModal" class="modal-3d-overlay" @click.self="closeModal">
        <div class="modal-3d-content">
            <div
                class="modal-3d-header bg-black text-white position-relative d-flex align-items-center justify-content-center">
                <h5 class="m-0 text-center w-100 text-warning fw-bold">360° TOUR PHÒNG CHIẾU</h5>
                <button class="btn btn-sm btn-light position-absolute end-0 me-3 px-3 py-1 fw-semibold"
                    @click="closeModal">
                    Đóng
                </button>
            </div>

            <div class="image-container" ref="panoContainer"></div>
        </div>
    </div>


</template>
<script>
import axios from 'axios'
export default {
    props: ['id_suat_chieu'],
    data() {
        return {
            id_suat_chieu: this.$route.params.id_suat_chieu, // Lấy id suất chiếu từ route
            list_ve: [], // Mảng vé
            thong_tin_phim: {}, // Thông tin phim
            hang_ngang_phong: null, // Số ghế hàng ngang phòng
            list_ben_phai: [], // Mảng ghế và dịch vụ bên phải
            list_ben_trai: [], // Mảng dịch vụ bên trái
            ma_voucher: '',   // Mã voucher
            kq_voucher: {},  // Kết quả voucher
            openModal: false, // ✅ thêm biến mở modal 3D
            hinhAnhPhong: '', // Hình ảnh phòng chiếu 3D
        }
    },
    mounted() {
        this.getVe();
        this.getDichVu();

        //De tat modal khi load trang
        document.querySelectorAll('.modal-backdrop').forEach(el => el.remove());
        document.body.classList.remove('modal-open');
        document.body.style = '';
    },
    computed: {

        // Tạo mảng ghế theo hàng ngang của phòng
        taoGheChoPhong() {
            let a = [];
            for (let i = 0; i < this.list_ve.length; i += this.hang_ngang_phong) {
                a.push(this.list_ve.slice(i, i + this.hang_ngang_phong));
            }
            return a;
        },

        // Tính tổng tiền vé và dịch vụ
        tongCong() {
            let tong = 0;
            let tien_giam_gia = 0;
            for (let i = 0; i < this.list_ben_phai.length; i++) {
                tong += this.list_ben_phai[i].giaVe;
            }

            if (tong >= this.kq_voucher.so_tien_toi_da) {
                tien_giam_gia = tong * this.kq_voucher.so_giam_gia;
                if (tien_giam_gia > this.kq_voucher.so_tien_giam_gia) {
                    tien_giam_gia = this.kq_voucher.so_tien_giam_gia;
                }
            }

            return tong - tien_giam_gia;
        }
    },
    watch: {

        // Theo dõi sự thay đổi của openModal
        openModal(newVal) {
            if (newVal) {
                this.loadPanorama()
            }
        }


    },

    methods: {

        //=====Thanh toán vé=======
        thanhToan() {
            var payload = {
                list_ben_phai: this.list_ben_phai.map(item => ({
                    idVe: item.id,
                    type: item.type
                })),
                maCode: this.ma_voucher
            }

            if (this.list_ben_phai.filter(item => item.type === 1).length == 0) {
                this.$toast.error("Vui lòng chọn ghế và dịch vụ trước khi thanh toán.");
                return;
            }
            console.log("Thanh toán vé: ", payload),
                axios.post("http://localhost:8080/api/client/dat-ve/thanh-toan", payload, {

                    headers: {
                        Authorization: "Bearer " + localStorage.getItem('key_client')
                    }
                })
                    .then((res) => {
                        if (res.data.status) {
                            this.$toast.success(res.data.message);
                            this.list_ben_phai = [];
                            this.ma_voucher = '';
                            this.$router.push('/client/don-hang/' + res.data.ma_hoa_don);
                        } else {
                            this.$toast.error(res.data.message);
                        }
                    })
        },
        //=====Lấy thông tin voucher=====
        thongTinVoucher() {

            if (this.ma_voucher.trim() === '') {
                this.$toast.error("Vui lòng nhập mã voucher.");
                return;
            }
            var payload = {
                maCode: this.ma_voucher,
            }
            axios.post("http://localhost:8080/api/client/dat-ve/ap-voucher", payload, {

                headers: {
                    Authorization: "Bearer " + localStorage.getItem('key_client')
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.kq_voucher = res.data.data;
                    } else {
                        this.$toast.error(res.data.message);
                    }
                })

        },

        //=====Xóa bỏ ghế hoặc dịch vụ khi chọn=====
        xoaBo(value) {
            value.donHang = null;
            this.list_ben_phai = this.list_ben_phai.filter(item => !(item.id === value.id && item.type === value.type));
        },

        //=====Thêm dịch vụ=====
        themDichvu(value) {
            value.tenGhe = value.tenDichVu;
            value.giaVe = value.gia;
            value.type = 2;
            this.list_ben_phai.push(value);
        },

        //=====Lấy dịch vụ=====
        getDichVu() {
            axios.get("http://localhost:8080/api/client/dat-ve/get-data", {

                headers: {
                    Authorization: "Bearer " + localStorage.getItem('key_client')
                }
            })
                .then((res) => {
                    this.list_ben_trai = res.data.data;
                })
        },

        //=====Chọn ghế=====
        chonGhe(value) {
            
            // ghế đã có đơn hàng
            if (value.donHang > 0) return;

            // ghế chưa có đơn hàng
            else if (value.donHang == null) {
                console.log("Chọn ghế: ", value);
                value.donHang = -1;
                value.type = 1;
                this.list_ben_phai.push(value);
                return;
            }

            // ghế người dùng đang chọn tạm thời trên giao diện
            else if (value.donHang == -1) {
                value.donHang = null;

                this.list_ben_phai = this.list_ben_phai.reduce((acc, item) => {
                    if (!(item.id === value.id && item.type === 1)) {
                        acc.push(item);
                    }
                    return acc;
                }, []);
            };
        },

        //=====Lấy vé theo id suất chiếu=====
        getVe() {

            console.log("id suất chiếu: ", this.id_suat_chieu);
            const token = localStorage.getItem("key_client");
            axios.get(`http://localhost:8080/api/client/dat-ve/${this.id_suat_chieu}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })

                .then((res) => {
                    if (res.data.status) {
                        this.list_ve = res.data.data_ve;

                        this.thong_tin_phim = res.data.data_phim;
                        console.log("Thông tin phim: ", this.thong_tin_phim);
                        this.hang_ngang_phong = res.data.hang_ngang;
                        this.hinhAnhPhong = res.data.hinh_anh; // Lấy hình ảnh phòng chiếu 3D

                    } else {
                        this.$toast.error('Không có dữ liệu vé nào cho suất chiếu này. Vui lòng chọn suất chiếu khác hoặc liên hệ quản trị viên để biết thêm thông tin.');
                        this.$router.push('/');
                    }
                })
        },
        formatVND(number) {
            return new Intl.NumberFormat("vi-VI", { style: "currency", currency: "VND" }).format(number,);
        },
        formatDate(date) {
            const thuVN = ['Chủ Nhật', 'Thứ Hai', 'Thứ Ba', 'Thứ Tư', 'Thứ Năm', 'Thứ Sáu', 'Thứ Bảy'];
            const d = new Date(date);
            return `${thuVN[d.getDay()]}, ngày ${d.getDate()}/${d.getMonth() + 1}/${d.getFullYear()}`;
        },

        // === TOUR 3D ===



        // Đóng modal
        closeModal() {
            this.openModal = false
        },
        // Tải thư viện và khởi tạo panorama
        loadPanorama() {
            const threeScript = document.createElement('script')
            threeScript.src = 'https://cdnjs.cloudflare.com/ajax/libs/three.js/105/three.min.js'
            threeScript.onload = () => {
                const panoScript = document.createElement('script')
                panoScript.src = '/js/panolens.min.js'
                panoScript.onload = () => this.initPanorama()
                document.body.appendChild(panoScript)
            }
            document.body.appendChild(threeScript)
        },



        // Khởi tạo panorama
        initPanorama() {
            const panorama = new PANOLENS.ImagePanorama(`/images/${this.hinhAnhPhong}`)

            // Viewer là khung hiển thị panorama
            const viewer = new PANOLENS.Viewer({
                container: this.$refs.panoContainer,
                autoRotate: true,
                autoRotateSpeed: 0.3,
                controlBar: true
            })
            viewer.add(panorama)

            // const video = document.createElement('video')
            // video.src = '/videos/muado.mp4'
            // video.loop = true
            // video.muted = true
            // video.autoplay = true
            // video.playsInline = true
            // video.crossOrigin = 'anonymous'
            // video.addEventListener('canplay', () => video.play())

            // const texture = new THREE.VideoTexture(video)
            texture.minFilter = THREE.LinearFilter
            texture.magFilter = THREE.LinearFilter
            texture.format = THREE.RGBFormat

            const geometry = new THREE.PlaneGeometry(2400, 1000)
            const material = new THREE.MeshBasicMaterial({ map: texture })
            const screen = new THREE.Mesh(geometry, material)
            screen.position.set(150, 200, -2400)
            panorama.add(screen)
        },





    },

}
</script>
<style>
.bg-warning {
    background-color: #ff8cec !important;
}


/* ✅ Style modal 3D */
.modal-3d-overlay {
    position: fixed;
    inset: 0;
    background: rgba(0, 0, 0, 0.85);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 999;
    /* thấp hơn Bootstrap modal (1055) để không đè */
}

.modal-3d-content {
    background: #000a0e;
    border: 2px solid #ebb47a;
    border-radius: 12px;
    width: 80%;
    max-width: 1000px;
    height: 80vh;
    display: flex;
    flex-direction: column;
    overflow: hidden;
}

.modal-3d-header {
    padding: 12px 16px;
    border-bottom: 2px solid #ebb47a;
    background: #000;
    color: #fff;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.modal-3d-header h5 {
    font-size: 1.2rem;
    letter-spacing: 1px;
    margin: 0;
}


.image-container {
    flex: 1;
    border-top: 2px solid #ebb47a;
    background: #000;
    border-radius: 0 0 10px 10px;
    overflow: hidden;
}
</style>