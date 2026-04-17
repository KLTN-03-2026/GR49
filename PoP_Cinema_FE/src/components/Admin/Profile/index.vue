<template>
    <div class="row">
        <div class="col-lg-12">
            <div class="card">
                <div class="card-body">
                    <ul class="nav nav-tabs nav-fill nav-primary" role="tablist">
                        <li class="nav-item" role="presentation" style="width: max-content;">
                            <a class="nav-link active" data-bs-toggle="tab" href="#profile" role="tab"
                                aria-selected="false" tabindex="-1">
                                <div class="d-flex align-items-center">
                                    <div class="tab-icon"><i class="bx bx-home font-18 me-1"></i>
                                    </div>
                                    <div class="tab-title">Thông Tin Cá Nhân</div>
                                </div>
                            </a>
                        </li>
                        <li class="nav-item" role="presentation">
                            <a class="nav-link" data-bs-toggle="tab" href="#doimatkhau" role="tab" aria-selected="false"
                                tabindex="-1">
                                <div class="d-flex align-items-center">
                                    <div class="tab-icon"><i class="bx bx-user-pin font-18 me-1"></i>
                                    </div>
                                    <div class="tab-title">Đổi Mật Khẩu</div>
                                </div>
                            </a>
                        </li>
                    </ul>
                    <div class="tab-content py-3">
                        <div class="tab-pane fade" id="profile" role="tabpanel">
                            <div class="container">
                                <div class="main-body">
                                    <div class="row">
                                        <div class="col-lg-4">
                                            <div class="card">
                                                <div class="card-body">
                                                    <div class="d-flex flex-column align-items-center text-center">
                                                        <img src="https://i.pravatar.cc/48?img=12"
                                                            alt="Admin" class="rounded-circle p-1 bg-primary"
                                                            width="155">
                                                        <div class="mt-3">
                                                            <h4>{{ profile.hoVaTen }}</h4>
                                                            <p class="text-secondary mb-1">Full Stack Developer</p>
                                                            <p class="text-muted font-size-sm">Bay Area, San Francisco,
                                                                CA</p>
                                                            <button class="btn btn-primary me-2">Follow</button>
                                                            <button class="btn btn-outline-primary">Message</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-8">
                                            <div class="card">
                                                <div class="card-body">
                                                    <div class="row mb-3">
                                                        <div class="col-sm-3">
                                                            <h6 class="mb-0">Họ Và Tên</h6>
                                                        </div>
                                                        <div class="col-sm-9 text-secondary">
                                                            <input type="text" class="form-control" v-model="profile.hoVaTen">
                                                        </div>
                                                    </div>
                                                    <div class="row mb-3">
                                                        <div class="col-sm-3">
                                                            <h6 class="mb-0">Email</h6>
                                                        </div>
                                                        <div class="col-sm-9 text-secondary">
                                                            <input type="text" class="form-control"
                                                                v-model="profile.email" disabled>
                                                        </div>
                                                    </div>
                                                    <div class="row mb-3">
                                                        <div class="col-sm-3">
                                                            <h6 class="mb-0">Số Điện Thoại</h6>
                                                        </div>
                                                        <div class="col-sm-9 text-secondary">
                                                            <input type="text" class="form-control"
                                                                v-model="profile.soDienThoai">
                                                        </div>
                                                    </div>
                                                    <div class="row mb-3">
                                                        <div class="col-sm-3">
                                                            <h6 class="mb-0">Ngày Sinh</h6>
                                                        </div>
                                                        <div class="col-sm-9 text-secondary">
                                                            <input type="date" class="form-control"
                                                                v-model="profile.ngaySinh">
                                                        </div>
                                                    </div>
                                                    <div class="row mb-3">
                                                        <div class="col-sm-3">
                                                            <h6 class="mb-0">Địa Chỉ</h6>
                                                        </div>
                                                        <div class="col-sm-9 text-secondary">
                                                            <input type="text" class="form-control"
                                                                v-model="profile.diaChi">
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm-3"></div>
                                                        <div class="col-sm-9 text-secondary">
                                                            <input type="button" class="btn btn-primary px-4" @click="capNhatThongTin()"
                                                                value="Save Changes">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="doimatkhau" role="tabpanel">
                            <div class="row">
                                <div class="col">
                                    <div class="card">
                                        <div class="row g-0">
                                            <div class="col-lg-6 border-end">
                                                <div class="card-body">
                                                    <div class="">
                                                        <div class="mb-3 mt-2">
                                                            <label class="form-label">Mật Khẩu Hiện Tại</label>
                                                            <input type="text" class="form-control"
                                                                placeholder="Nhập mật khẩu cũ" v-model="update_pass.matKhauCu">
                                                        </div>
                                                        <div class="mb-3 mt-2">
                                                            <label class="form-label">Mật Khẩu Mới</label>
                                                            <input type="text" class="form-control"
                                                                placeholder="Nhập mật khẩu mới" v-model="update_pass.matkhauMoi">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label class="form-label">Xác Nhận Mật Khẩu</label>
                                                            <input type="text" class="form-control"
                                                                placeholder="Xác Nhận Mật Khẩu" v-model="update_pass.xacNhanMatKhau">
                                                        </div>
                                                        <div class="d-grid gap-2">
                                                            <button type="button" class="btn btn-primary" @click="capNhatMatKhau()">Đổi Mật
                                                                Khẩu</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-6">
                                                <img src="https://cdn.tgdd.vn/Files/2019/12/07/1225665/huong-dan-thay-doi-mat-khau-tai-khoan-google-don-gian-tren-dien-thoai-may-tinh-7.jpg"
                                                    class="card-img login-img" alt="..." style="height: 325px;">
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
    </div>
</template>
<script>
import axios from 'axios';

const token = localStorage.getItem("key_admin");
export default {

	data() {
		return {

			profile: {},
			update_pass: {
			}
		}
	},
	mounted() {


		this.loadProfile();

	},
	methods: {

		loadProfile() {
             
			axios.get("http://localhost:8080/api/admin/profile", {
                 
				headers: {
					'Authorization': `Bearer ${token}`
				}
			})
            
				.then((res) => {
					this.profile = res.data;  // vì res.data là object user
                     console.log(this.profile);
					
				})
				.catch((err) => {
					console.error("Lỗi khi tải hồ sơ:", err);
				});
		},


        //Hàm kiểm tra dữ liệu khi cập nhật thông tin
		validateEditKhachHang(nv) {
			// Kiểm tra xem có để trống trường nào không
			if (!nv.hoVaTen || !nv.email || !nv.soDienThoai || !nv.ngaySinh || !nv.diaChi) {
				this.$toast.error("Vui lòng điền đầy đủ thông tin");
				return false;
			}


			// Kiểm tra định dạng số điện thoại (0 + 9-10 chữ số)
			const regexPhone = /^0\d{9,10}$/;
			if (!regexPhone.test(nv.soDienThoai)) {
				this.$toast.error("Số điện thoại không hợp lệ");
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
				this.$toast.error("Ngày sinh của bạn phải từ 18 tuổi trở lên");
				return false;
			}

			return true; // tất cả hợp lệ
		},

		capNhatThongTin() {

        if (!this.validateEditKhachHang(this.profile)) {
				return; // Dừng nếu dữ liệu không hợp lệ
			}

            axios.put('http://localhost:8080/api/admin/update-thong-tin', this.profile, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.profile = {};
                        this.loadProfile();
                    }else {
						this.$toast.error("Cập nhật thất bại");
					}
                }) .catch((err) => {
                    console.error("Error:", err.response);
                });
        },

		capNhatMatKhau() {
            // 1. Kiểm tra đầy đủ thông tin
            if (!this.update_pass.matKhauCu || !this.update_pass.matkhauMoi || !this.update_pass.xacNhanMatKhau) {
				this.$toast.error("Vui lòng điền đầy đủ thông tin.");
				return;
			}
            // 2. Kiểm tra mật khẩu mới khớp
			if (this.update_pass.matkhauMoi !== this.update_pass.xacNhanMatKhau) {
				this.$toast.error("Mật khẩu xác nhận không khớp.");
				return;
			}

			// 3. Kiểm tra regex mật khẩu mới (khớp backend)
			const regexPass = /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
			if (!regexPass.test(this.update_pass.matkhauMoi)) {
				this.$toast.error("Mật khẩu mới phải có ít nhất 8 ký tự, bao gồm chữ hoa, số và ký tự đặc biệt.");
				return;
			}
            axios.post('http://localhost:8080/api/admin/change-pass', this.update_pass,  {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.data.status) {

                        this.$toast.success(res.data.message);
                        this.update_pass = {};
                        
                    }else {
						this.$toast.error(res.data.message);
					}
                }) .catch((err) => {
                    console.error("Error:", err.response);
                });
        },


	}
}
</script>
<style></style>