import axios from 'axios';
const token = localStorage.getItem("key_client");
export default {
    props: ["id_phim"],
    data() {
        return {

            selectedRating: { soSao: 0 }, // đánh giá đã chọn
            hoverRating: 0, // đánh giá khi hover

            id_phim: this.$route.params.id_phim,// Lấy id_phim từ route
            chi_tiet_phim: {},// Chi tiết phim
            ngayChieuList: [], // Danh sách ngày chiếu
            suat_chieu_phim: [],// Suất chiếu của phim
            list_phim_goi_y: [],// Danh sách phim gợi ý

            selectedDate: false, // Ngày chiếu đã chọn
            selectedSuatChieu: null, // Suất chiếu đã chọn
            selectedRating: { // Đánh giá được chọn
                soSao: null
            },
            create_binhLuan: {
                noiDung: null
            },
            listDanhGia: [], // Danh sách đánh giá
            listBinhLuan: [], // Danh sách bình luận
            profile: {}, // Thông tin người dùng hiện tại
        }
    },
    computed: {

        //Hiển thị 7 ngày tiếp theo
        ngayChieu() {
            const today = new Date();
            let days = [];

            for (let i = 0; i < 7; i++) {
                const d = new Date(today);
                d.setDate(today.getDate() + i);

                days.push({
                    ngayChieu: d.toISOString().split('T')[0] // Giữ dạng YYYY-MM-DD
                });
            }

            return days;
        },

        // Lọc suất chiếu theo ngày đã chọn
        suatChieuTheoNgay() {
            return this.selectedDate ? this.suat_chieu_phim.filter(item => item.ngayChieu === this.selectedDate)
                : [];
        }
    },
    mounted() {
        this.loadChiTietPhim();

        // Lấy 7 ngày tiếp theo
        this.selectedDate = this.get7NgayTiepTheo();
        this.getDanhGia();
        this.getBinhLuan();
        this.getPhimGoiY();
        this.loadProfile();

    },
    watch: {
    '$route.params.id_phim': {
        immediate: true, // chạy ngay lần đầu mounted
        handler(newId) {
            this.id_phim = newId;
            
            // Reload dữ liệu phim mới
            this.loadChiTietPhim();
            this.getDanhGia();
            this.getBinhLuan();
            
            // Reset lựa chọn ngày và suất chiếu
            this.selectedDate = this.get7NgayTiepTheo();
            this.selectedSuatChieu = null;
        }
    }
},
    methods: {

        
        //tính phần trănm đánh giá
        tinhPhanTram(diem) {
            if (!diem || diem <= 0) return 0;
            if (diem > 5) diem = 5;
            return (diem / 5) * 100;
        },
        formatTime(time) {
            return time.slice(0, 5); // Convert HH:MM:SS to HH:MM
        },
        formatDate(date) {
            const d = new Date(date);
            return `${d.getDate()}/${d.getMonth() + 1}/${d.getFullYear()}`;
        },

        // Load chi tiết phim
        loadChiTietPhim() {
            // var payload = {
            //     id: this.id_phim
            // }
            axios.get(`http://localhost:8080/api/chi-tiet-phim/${this.id_phim}`)
                .then((res) => {
                    if (res.data.status) {
                        this.chi_tiet_phim = res.data.data_phim;
                        console.log(this.chi_tiet_phim);
                        this.suat_chieu_phim = res.data.data_suat_chieu;
                        console.log(this.suat_chieu_phim);

                    } else {
                        this.$toast.error(res.data.message);
                        this.$router.push('/');
                    }
                });
        },

        // Thêm đánh giá
        themDanhGia() {
            //  Kiểm tra đăng nhập trước khi gửi
            if (!token) {
                this.$toast.error("Bạn cần đăng nhập để đánh giá phim.");
                return;
            }
            var payload = {
                soSao: this.selectedRating.soSao,
                idPhim: this.chi_tiet_phim.id,
            }
            console.log(payload);
            axios
                .post('http://localhost:8080/api/client/danh-gia/create-danh-gia', payload, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then(response => {
                    if (response.data.status) {
                        this.getDanhGia();
                        this.$toast.success(response.data.message);

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

        // Hiển thị đánh giá
        getDanhGia() {
            axios.get(`http://localhost:8080/api/chi-tiet-phim/get-danh-gia?idPhim=${this.id_phim}`)
                .then((res) => {
                    if (res.data.status) {

                        this.listDanhGia = res.data.data;

                    } else {
                        this.$toast.error(res.data.message);

                    }
                });
        },

        // Thêm bình luận
        themBinhLuan() {

            //  Kiểm tra đăng nhập trước khi gửi
            if (!token) {
                this.$toast.error("Bạn cần đăng nhập để bình luận phim.");
                return;
            }
            var payload = {
                noiDung: this.create_binhLuan.noiDung,
                idPhim: this.chi_tiet_phim.id,

            }
            console.log(payload);
            axios
                .post('http://localhost:8080/api/client/danh-gia/create-binh-luan', payload, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then(response => {
                    if (response.data.status) {
                        this.create_binhLuan.noiDung = null;
                        this.getBinhLuan();
                        this.$toast.success(response.data.message);

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

        //Hiển thị bình luận
        getBinhLuan() {
            axios.get(`http://localhost:8080/api/chi-tiet-phim/get-binh-luan?idPhim=${this.id_phim}`)
                .then((res) => {
                    if (res.data.status) {

                        this.listBinhLuan = res.data.data;

                    } else {
                        this.$toast.error(res.data.message);

                    }
                });
        },


        //Lấy 7 ngày tiếp theo trong tuần tới
        get7NgayTiepTheo() {
            const result = [];
            const today = new Date();

            for (let i = 0; i < 7; i++) {
                const d = new Date(today);
                d.setDate(today.getDate() + i);
                const iso = d.toISOString().split('T')[0]; // yyyy-mm-dd
                result.push({ ngayChieu: iso });
            }

            return result;
        },

        //lấy thứ trong tuần -> vd: thứ 2, thứ 3,...
        getThuTrongTuan(dateStr) {
            const days = ['Chủ Nhật', 'Thứ Hai', 'Thứ Ba', 'Thứ Tư', 'Thứ Năm', 'Thứ Sáu', 'Thứ Bảy'];
            const date = new Date(dateStr);
            return days[date.getDay()];



        },

         getPhimGoiY() {
            axios
                .get('http://localhost:8080/api/chi-tiet-phim/get-goi-y')
                .then((res) => {
                    if (res.data.status) {
                        this.list_phim_goi_y = res.data.data;
                    }
                })
        },


        loadProfile() {

			axios.post("http://localhost:8080/api/client/profile", {}, {

				headers: {
					'Authorization': `Bearer ${token}`
				}
			})
				.then((res) => {
					this.profile = res.data;  // vì res.data là object user

				})
				.catch((err) => {
					console.error("Lỗi khi tải hồ sơ:", err);
				});
		},
    
    },
};