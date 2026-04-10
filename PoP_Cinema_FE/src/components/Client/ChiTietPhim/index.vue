<template>
    <!-- Trailer Section -->
    <div class="">
        <div class="ratio ratio-16x9 shadow-lg overflow-hidden" style="height: 450px; object-fit: cover; ">
            <iframe :src="chi_tiet_phim.trailer" title="YouTube video player" allowfullscreen></iframe>
        </div>
    </div>

    <!-- Movie Details Container -->
    <div class="container py-5">
        <div class="row g-4">
            <!-- Poster Section -->
            <div class="col-md-4">
                <img :src="chi_tiet_phim.hinhAnh" alt="Movie Poster" class="img-fluid rounded shadow-lg mb-4">
                <div class="d-grid gap-3">
                    <button class="btn btn-warning py-3 fw-bold" data-bs-toggle="modal"
                        data-bs-target="#buyTicketModal">
                        <i class="fa-solid fa-ticket"></i> Đặt Vé
                    </button>
                </div>
            </div>

            <!-- Phần thông tin phim -->
            <div class="col-md-8">
                <div class="mb-4">
                    <h1 class="fw-bold text-dark mb-3">{{ chi_tiet_phim.tenPhim || 'Sample Movie' }}</h1>
                    <div class="d-flex flex-wrap gap-2 mb-3">
                        <span v-for="(value, index) in chi_tiet_phim.theLoai?.split(',') || []" :key="index"
                            class="badge bg-primary rounded-pill px-3 py-2">
                            {{ value || 'N/A' }}
                        </span>
                    </div>
                </div>

                <div class="row g-3">
                    <!-- Phần thông tin chi tiết  -->
                    <div class="col-lg-6">
                        <h4 class="fw-bold mb-3 border-bottom pb-2">Thông tin chi tiết</h4>
                        <div class="card shadow-sm">
                            <div class="card-body">
                                <div class="d-flex justify-content-between mb-2 border-bottom pb-2">
                                    <span class="fw-bold text-dark">Đạo diễn:</span>
                                    <span>{{ chi_tiet_phim.daoDien || 'N/A' }}</span>
                                </div>
                                <div class="d-flex justify-content-between mb-2 border-bottom pb-2">
                                    <span class="fw-bold text-dark">Quốc gia:</span>
                                    <span>{{ chi_tiet_phim.quocGia || 'N/A' }}</span>
                                </div>
                                <div class="d-flex justify-content-between mb-2 border-bottom pb-2">
                                    <span class="fw-bold text-dark">Ngày phát hành:</span>
                                    <span>{{ chi_tiet_phim.ngayPhatHanh || 'N/A' }}</span>
                                </div>
                                <div class="d-flex justify-content-between mb-2 border-bottom pb-2">
                                    <span class="fw-bold text-dark">Ngôn ngữ:</span>
                                    <span>{{ chi_tiet_phim.ngonNgu || 'N/A' }}</span>
                                </div>
                                <div class="d-flex justify-content-between">
                                    <span class="fw-bold text-dark">Thời lượng:</span>
                                    <span>{{ chi_tiet_phim.thoiLuong ? chi_tiet_phim.thoiLuong + ' phút' : 'N/A'
                                    }}</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Cast and Producer -->
                    <div class="col-lg-6">
                        <h4 class="fw-bold mb-3 border-bottom pb-2">Diễn viên chính</h4>
                        <div class="card shadow-sm mb-4">
                            <div class="card-body">
                                <p class="mb-0">{{ chi_tiet_phim.dienVien || 'N/A' }}</p>
                            </div>
                        </div>
                        <h4 class="fw-bold mb-3 border-bottom pb-2">Nhà sản xuất</h4>
                        <div class="card shadow-sm">
                            <div class="card-body">
                                <p class="mb-0">{{ chi_tiet_phim.nhaSanXuat || 'N/A' }}</p>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Synopsis -->
                <div class="mt-4">
                    <h4 class="fw-bold mb-3 border-bottom pb-2">Nội dung phim</h4>
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <p class="fs-7 lh-base text-dark text-justify">
                                {{ chi_tiet_phim.noiDung || 'Không có mô tả.' }}
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Comments and Ratings Section -->
        <div class="row g-4 mt-5">
            <!-- Comment Section -->
            <div class="col-lg-8">
                <div class="bg-white rounded shadow-sm p-4 h-100">
                    <h5 class="fs-5 fw-bold mb-4 border-bottom pb-2">Bình Luận</h5>
                    <div class="mb-4">
                        <div class="row g-3 align-items-center">
                            <div class="col-auto">
                                <img :src="profile.avatar  || 'https://res.cloudinary.com/dvxkhsfyj/image/upload/v1762926652/popcornclient_hditjq.png'" alt="User Avatar" class="rounded-circle"
                                    width="48" height="48">
                            </div>
                            <div class="col">
                                <textarea class="form-control" rows="3" placeholder="Viết bình luận của bạn..."
                                    v-model="create_binhLuan.noiDung" required></textarea>
                            </div>
                            <div class="col-auto">
                                <button type="submit" class="btn btn-dark px-4 py-2 fw-semibold"
                                    @click="themBinhLuan()">Gửi</button>
                            </div>
                        </div>
                    </div>
                    <div class="border-top pt-4">
                        <template v-for="(item, index) in listBinhLuan" :key="index">
                            <div class="mb-4">
                                <div class="d-flex align-items-start gap-3">
                                    <img :src=" item.avatar || 'https://res.cloudinary.com/dvxkhsfyj/image/upload/v1762926652/popcornclient_hditjq.png'" alt="User Avatar" class="rounded-circle"
                                        width="48" height="48">
                                    <div class="flex-grow-1">
                                        <div class="d-flex justify-content-between align-items-center mb-2">
                                            <h6 class="fw-bold mb-0">{{ item.tenKhach }}</h6>
                                            <small class="text-muted">{{ item.ngay }}</small>
                                        </div>
                                        <p class="mb-0 text-secondary">
                                            {{ item.noiDung }}
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </template>
                        <div class="mb-4">
                            <div class="d-flex align-items-start gap-3">
                                <img src="https://res.cloudinary.com/dvxkhsfyj/image/upload/v1762926652/popcornclient_hditjq.png" alt="User Avatar" class="rounded-circle"
                                    width="48" height="48">
                                <div class="flex-grow-1">
                                    <div class="d-flex justify-content-between align-items-center mb-2">
                                        <h6 class="fw-bold mb-0">Trần Thị B</h6>
                                        <small class="text-muted">06/05/2025</small>
                                    </div>
                                    <p class="mb-0 text-secondary">
                                        Rất thích chương trình này. Tiết kiệm được một khoản khi đi xem phim cùng gia
                                        đình. Mong DZ có nhiều ưu đãi như vậy.
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Ratings Section -->
            <div class="col-lg-4">
                <div class="bg-white rounded shadow-sm p-4 h-90">
                    <h5 class="fs-5 fw-bold mb-4 border-bottom pb-2">Đánh Giá Phim</h5>
                    <div class="row g-4">
                        <div class="col-12">

                            <div class="d-flex align-items-center gap-3" v-for="(item, index) in listDanhGia"
                                :key="index">
                                <div class="text-center">
                                    <h3 class="fw-bold text-success mb-0">{{ item.diemTrungBinh ? (item.diemTrungBinh *
                                        2).toFixed(1) : '0.0' }}</h3>
                                    <p class="text-muted small mb-0">/10 (PoPCinema)</p>
                                </div>
                                <div class="flex-grow-1">
                                    <div class="progress" style="height: 8px;">
                                        <div class="progress-bar bg-success" role="progressbar"
                                            :style="{ width: tinhPhanTram(item.diemTrungBinh) + '%' }"
                                            :aria-valuenow="tinhPhanTram(item.diemTrungBinh)" aria-valuemin="0"
                                            aria-valuemax="100">
                                        </div>
                                    </div>
                                    <p class="text-muted small mt-1">Dựa trên {{ item.tongDanhGia }} đánh giá</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="mt-4">
                        <div class="d-flex align-items-center gap-3">
                            <!-- Hàng sao chọn -->
                            <div class="rating-stars d-flex justify-content-start gap-2">
                                <i v-for="n in 5" :key="n" class="bi"
                                    :class="n <= selectedRating.soSao ? 'bi-star-fill text-warning fs-3' : 'bi-star text-secondary fs-3'"
                                    @click="selectedRating.soSao = n"
                                    style="cursor: pointer; transition: transform 0.2s;" @mouseover="hoverRating = n"
                                    @mouseleave="hoverRating = 0"></i>
                            </div>

                            <!-- Nút gửi đánh giá -->
                            <button type="button" class="btn btn-success px-4 py-2 fw-semibold" @click="themDanhGia()">
                                Gửi đánh giá
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Other Movies Section -->
        <div class="bg-white rounded shadow-sm p-3 p-md-4 mt-3">
            <h4>Các phim đang chiếu khác</h4>
            <div class="row">
                 <template v-for="(value, index) in list_phim_goi_y" :key="index">
                <template v-if="value.tinhTrang == 2">
                <div class="col-lg-3">
                    <div class="card">
                        <div class="card-img-top">
                             <img :src="value.hinhAnh"
                                class="img-fluid" alt=""  >
                        </div>
                        <div class="card-img-overlay text-center" style="margin-top: 200px;">
                            <div class="col">
                                <router-link :to="`/chi-tiet-phim/${value.id}`">
                                <button class="btn btn-warning p-2 " style="width: 170px;"><i
                                        class="fa-solid fa-ticket"></i>Đặt Vé</button>
                                    </router-link>
                                <br>
                                <button class="btn btn-outline-light p-2 mt-2" style="width: 170px;"><i
                                        class="fa-solid fa-circle-play"></i>Trailer</button>
                            </div>
                        </div>
                    </div>
                </div>
            
                </template>
                </template>
            </div>
        </div>
    </div>

    <!-- Modal Ticket (unchanged) -->
    <div class="modal fade" id="buyTicketModal" tabindex="-1" aria-labelledby="movieScheduleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog modal-xl modal-dialog-centered  ">
            <div class="modal-content">
                <div class="modal-header bg-light border-bottom">
                    <h4 class="modal-title fs-3 fw-bold text-dark" id="movieScheduleModalLabel">
                        Lịch chiếu: {{ chi_tiet_phim.tenPhim || 'Sample Movie' }}
                    </h4>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body p-4">
                    <div class="mb-4">
                        <h5 class="fw-semibold mb-3 text-dark">Chọn ngày xem</h5>
                    
                        <div class="d-flex flex-wrap gap-3 overflow-auto flex-nowrap pb-2">
                            <div v-for="(value, index) in ngayChieu" :key="index" class="ticket-card"
                                :class="{ active: selectedDate === value.ngayChieu }"
                                @click="selectedDate = value.ngayChieu; selectedSuatChieu = null">
                                <div class="ticket-date">
                                    <div class="fw-bold">{{ getThuTrongTuan(value.ngayChieu) }}</div>
                                    <div class="small">{{ formatDate(value.ngayChieu) }}</div>
                                </div>
                                <div class="ticket-perforation"></div>
                                <div class="ticket-icon">
                                    <i class="fa-solid fa-ticket-simple"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div>
                        <h5 class="fw-semibold mb-3 text-dark">Suất chiếu</h5>
                        <div class="row row-cols-2 row-cols-md-3 row-cols-lg-4 g-3">
                            <div class="col" v-for="(value, index) in suatChieuTheoNgay" :key="index">
                                <button class="btn btn-outline-primary w-100 py-2" @click="selectedSuatChieu = value">
                                    {{ formatTime(value.thoiGianBatDau) }} - {{ formatTime(value.thoiGianKetThuc) }}
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer border-top">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    <router-link v-if="selectedSuatChieu" :to="`/client/dat-ve/${selectedSuatChieu.id}`">
                        <button type="button" class="btn btn-primary">
                            Tiếp tục đặt vé
                        </button>
                    </router-link>
                </div>
            </div>
        </div>
    </div>
</template>
<script src="./index.js">
// import axios from 'axios';
// const token = localStorage.getItem("key_client");
// export default {
//     props: ["id_phim"],
//     data() {
//         return {

//             selectedRating: { soSao: 0 },
//             hoverRating: 0,

//             id_phim: this.$route.params.id_phim,
//             chi_tiet_phim: {},
//             ngayChieu: [],
//             suat_chieu_phim: [],

//             selectedDate: false,
//             selectedSuatChieu: null,
//             selectedRating: {
//                 soSao: null
//             },
//             create_binhLuan: {
//                 noiDung: null
//             },
//             listDanhGia: [],
//             listBinhLuan: [],
//         }
//     },
//     computed: {
//         ngayChieu() {
//             const today = new Date();
//             let days = [];

//             for (let i = 0; i < 7; i++) {
//                 const d = new Date(today);
//                 d.setDate(today.getDate() + i);

//                 days.push({
//                     ngayChieu: d.toISOString().split('T')[0] // Giữ dạng YYYY-MM-DD
//                 });
//             }

//             return days;
//         },

//         suatChieuTheoNgay() {
//             return this.selectedDate ? this.suat_chieu_phim.filter(item => item.ngayChieu === this.selectedDate)
//                 : [];
//         }
//     },
//     mounted() {
//         this.loadChiTietPhim();
//         this.selectedDate = this.get7NgayTiepTheo();
//         this.getDanhGia();
//         this.getBinhLuan();

//     },
//     methods: {
//         //tính phần trănm đánh giá
//         tinhPhanTram(diem) {
//             if (!diem || diem <= 0) return 0;
//             if (diem > 5) diem = 5;
//             return (diem / 5) * 100;
//         },
//         formatTime(time) {
//             return time.slice(0, 5); // Convert HH:MM:SS to HH:MM
//         },
//         formatDate(date) {
//             const d = new Date(date);
//             return `${d.getDate()}/${d.getMonth() + 1}/${d.getFullYear()}`;
//         },
//         loadChiTietPhim() {
//             // var payload = {
//             //     id: this.id_phim
//             // }
//             axios.get(`http://localhost:8080/api/chi-tiet-phim/${this.id_phim}`)
//                 .then((res) => {
//                     if (res.data.status) {
//                         this.chi_tiet_phim = res.data.data_phim;
//                         console.log(this.chi_tiet_phim);
//                         this.suat_chieu_phim = res.data.data_suat_chieu;
//                         console.log(this.suat_chieu_phim);

//                     } else {
//                         this.$toast.error(res.data.message);
//                         this.$router.push('/');
//                     }
//                 });
//         },
//         themDanhGia() {
//             //  Kiểm tra đăng nhập trước khi gửi
//             if (!token) {
//                 this.$toast.error("Bạn cần đăng nhập để đánh giá phim.");
//                 return;
//             }
//             var payload = {
//                 soSao: this.selectedRating.soSao,
//                 idPhim: this.chi_tiet_phim.id,
//             }
//             console.log(payload);
//             axios
//                 .post('http://localhost:8080/api/client/danh-gia/create-danh-gia', payload, {
//                     headers: {
//                         Authorization: `Bearer ${token}`
//                     }
//                 })
//                 .then(response => {
//                     if (response.data.status) {
//                         this.getDanhGia();
//                         this.$toast.success(response.data.message);

//                     } else {
//                         this.$toast.error(response.data.message);
//                     }
//                 })
//                 .catch(res => {
//                     const list = Object.values(res.response.data.errors);
//                     list.forEach((v, i) => {
//                         this.$toast.error(v[0]);
//                     });
//                 });
//         },
//         getDanhGia() {
//             axios.get(`http://localhost:8080/api/chi-tiet-phim/get-danh-gia?idPhim=${this.id_phim}`)
//                 .then((res) => {
//                     if (res.data.status) {

//                         this.listDanhGia = res.data.data;

//                     } else {
//                         this.$toast.error(res.data.message);

//                     }
//                 });
//         },

//         themBinhLuan() {

//             //  Kiểm tra đăng nhập trước khi gửi
//             if (!token) {
//                 this.$toast.error("Bạn cần đăng nhập để bình luận phim.");
//                 return;
//             }
//             var payload = {
//                 noiDung: this.create_binhLuan.noiDung,
//                 idPhim: this.chi_tiet_phim.id,

//             }
//             console.log(payload);
//             axios
//                 .post('http://localhost:8080/api/client/danh-gia/create-binh-luan', payload, {
//                     headers: {
//                         Authorization: `Bearer ${token}`
//                     }
//                 })
//                 .then(response => {
//                     if (response.data.status) {
//                         this.create_binhLuan.noiDung = null;
//                         this.getBinhLuan();
//                         this.$toast.success(response.data.message);

//                     } else {
//                         this.$toast.error(response.data.message);
//                     }
//                 })
//                 .catch(res => {
//                     const list = Object.values(res.response.data.errors);
//                     list.forEach((v, i) => {
//                         this.$toast.error(v[0]);
//                     });
//                 });
//         },

//         getBinhLuan() {
//             axios.get(`http://localhost:8080/api/chi-tiet-phim/get-binh-luan?idPhim=${this.id_phim}`)
//                 .then((res) => {
//                     if (res.data.status) {

//                         this.listBinhLuan = res.data.data;

//                     } else {
//                         this.$toast.error(res.data.message);

//                     }
//                 });
//         },


//         //Lấy 7 ngày tiếp theo trong tuần tới
//         get7NgayTiepTheo() {
//             const result = [];
//             const today = new Date();

//             for (let i = 0; i < 7; i++) {
//                 const d = new Date(today);
//                 d.setDate(today.getDate() + i);
//                 const iso = d.toISOString().split('T')[0]; // yyyy-mm-dd
//                 result.push({ ngayChieu: iso });
//             }

//             return result;
//         },

//         //lấy thứ trong tuần -> vd: thứ 2, thứ 3,...
//         getThuTrongTuan(dateStr) {
//             const days = ['Chủ Nhật', 'Thứ Hai', 'Thứ Ba', 'Thứ Tư', 'Thứ Năm', 'Thứ Sáu', 'Thứ Bảy'];
//             const date = new Date(dateStr);
//             return days[date.getDay()];



//         },
//     },
// }
</script>
<style src="./index.css">
</style>
