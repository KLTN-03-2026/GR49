<template>
	<div class="container mt-5">
		<div class="row">
			<div class="col-lg-4">
				<div class="card">
					<div class="card-body ">
						<!-- Header: Avatar + Tên + Hạng -->
						<div class="d-flex align-items-center gap-3">
							<img :src="profile.avatar || 'https://cellphones.com.vn/sforum/wp-content/uploads/2024/02/anh-avatar-cute-58.jpg'"
								alt="Avatar" class="rounded-circle border p-1 bg-primary ms-4" width="80" height="80"
								data-bs-toggle="modal" data-bs-target="#avatarModal" style="cursor: pointer;">

							<div class="flex-grow-1">
								<div class="fw-bold" style="font-size: 18px;">
									{{ profile.hoVaTen }}
								</div>
								<span class="badge mt-1 d-inline-flex align-items-center gap-1 px-2 py-1"
									:style="badgeStyle">
									<i :class="iconClass"></i> Thành Viên {{ tenHangThanhVien }}
								</span>
							</div>
						</div>

						<hr>

						<!-- Điểm + Xếp hạng -->
						<div style=" padding-top: 14px; margin-bottom: 14px;">
							<div class="d-flex align-items-center justify-content-between">
								<span class="text-muted" style="font-size: 15px;">Điểm tích lũy</span>
								<div class="d-flex align-items-baseline gap-1">
									<span style="font-size: 28px; font-weight: 500; color: #EF9F27; line-height: 1;">
										{{ profile.diem?.toLocaleString() }}
									</span>
									<span class="text-muted" style="font-size: 15px;">điểm</span>
								</div>
							</div>

							<div class="progress mt-2" style="height: 6px; border-radius: 10px;">
								<div class="progress-bar" role="progressbar"
									:style="{ width: progress + '%', background: 'linear-gradient(90deg, #EF9F27, #D85A30)' }">
								</div>
							</div>

							<div class="d-flex justify-content-between mt-1">
								<small class="text-muted">
									Tiến tới thành viên <strong style="color: #185FA5;">{{ nextLevel?.ten || 'Tối đa'
									}}</strong>
								</small>
								<small class="text-muted">{{ profile.diem }} / {{ currentLevel.max }}</small>
							</div>
						</div>

					</div>
				</div>
			</div>
			<!-- Thông tin cá nhân -->
			<div class="col-lg-8">
				<div class="card">
					<div class="card-body">
						<ul class="nav nav-tabs nav-primary nav-fill text-center" role="tablist">
							<li class="nav-item " role="presentation">
								<a class="nav-link active" data-bs-toggle="tab" href="#profile" role="tab"
									aria-selected="true">
									<div class="d-flex align-items-center">
										<div class="tab-icon"><i class="bx bx-home font-18 me-1"></i>
										</div>
										<div class="tab-title">Thông Tin Cá Nhân</div>
									</div>
								</a>
							</li>
							<li class="nav-item" role="presentation">
								<a class="nav-link" data-bs-toggle="tab" href="#changepassword" role="tab"
									aria-selected="false" tabindex="-1">
									<div class="d-flex align-items-center">
										<div class="tab-icon"><i class="bx bx-user-pin font-18 me-1"></i>
										</div>
										<div class="tab-title">Đổi Mật Khẩu</div>
									</div>
								</a>
							</li>
							<li class="nav-item" role="presentation">
								<a class="nav-link" data-bs-toggle="tab" href="#history" role="tab"
									aria-selected="false" tabindex="-1">
									<div class="d-flex align-items-center">
										<div class="tab-icon"><i class="fa-solid fa-clock-rotate-left me-1"></i>
										</div>
										<div class="tab-title">Lịch Sử hoạt động</div>
									</div>
								</a>
							</li>
						</ul>
						<div class="tab-content py-3">
							<!-- THÔNG TIN CÁ NHÂN -->
							<div class="tab-pane fade active show" id="profile" role="tabpanel">
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
											<input type="text" class="form-control" v-model="profile.email" disabled>
										</div>
									</div>
									<div class="row mb-3">
										<div class="col-sm-3">
											<h6 class="mb-0">Số Điện Thoại</h6>
										</div>
										<div class="col-sm-9 text-secondary">
											<input type="text" class="form-control" v-model="profile.soDienThoai">
										</div>
									</div>
									<div class="row mb-3">
										<div class="col-sm-3">
											<h6 class="mb-0">CCCD</h6>
										</div>
										<div class="col-sm-9 text-secondary">
											<input type="text" class="form-control" v-model="profile.cccd">
										</div>
									</div>
									<div class="row mb-3">
										<div class="col-sm-3">
											<h6 class="mb-0">Ngày Sinh</h6>
										</div>
										<div class="col-sm-9 text-secondary">
											<input type="date" class="form-control" v-model="profile.ngaySinh">
										</div>
									</div>
									<div class="row">
										<div class="col-sm-3"></div>
										<div class="col-sm-9 text-secondary">
											<!-- <input type="button" class="btn btn-primary px-4" value="Save Changes"
												@click="capNhatThongTin()"> -->
											<button type="button" class="btn btn-primary px-4"
												@click="capNhatThongTin()">Lưu Thay Đổi</button>
										</div>
									</div>
								</div>
							</div>
							<!-- ĐỔI MẬT KHẨU -->
							<div class="tab-pane fade" id="changepassword" role="tabpanel">
								<div class="card">
									<div class="row g-0">
										<div class="col-lg-6 border-end">
											<div class="card-body">
												<div class="">
													<div class="mb-3 mt-2">
														<label class="form-label">Mật Khẩu Hiện Tại</label>
														<input type="password" class="form-control"
															placeholder="Nhập mật khẩu cũ"
															v-model="update_pass.matKhauCu">
													</div>
													<div class="mb-3 mt-2">
														<label class="form-label">Mật Khẩu Mới</label>
														<input type="password" class="form-control"
															placeholder="Nhập mật khẩu mới"
															v-model="update_pass.matkhauMoi">
													</div>
													<div class="mb-3">
														<label class="form-label">Xác Nhận Mật Khẩu</label>
														<input type="password" class="form-control"
															placeholder="Xác Nhận Mật Khẩu"
															v-model="update_pass.xacNhanMatKhau">
													</div>
													<div class="d-grid gap-2">
														<button type="button" class="btn btn-primary"
															@click="capNhatMatKhau()">Đổi Mật
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
							<!-- LỊCH SỬ TÍCH ĐIỂM -->
							<div class="tab-pane fade" id="history" role="tabpanel">

								<!-- FILTER -->
								<div class="d-flex gap-2 mb-3">
									<input type="date" class="form-control" v-model="ngayTruoc">
									<input type="date" class="form-control" v-model="ngaySau">
									<select class="form-control" v-model="trangThai">
										<option value="">Tất cả</option>
										<option value="1">Đã tích điểm</option>
										<option value="2">Đã tiêu điểm</option>
									</select>
								</div>

								<!-- LIST -->
								<div v-for="item in list_lich_su" :key="item.id" class="mb-2">

									<div
										class="d-flex justify-content-between align-items-center p-3 bg-light rounded shadow-sm">

										<!-- LEFT -->
										<div>
											<div class="fw-bold">
												{{ item.moTa }}
											</div>

											<small class="text-muted">
												<i :class="[
													'fas fa-tag me-1',
													item.tinhTrang == 1 ? 'fas fa-tag me-1 text-success' : '',
													item.tinhTrang == 2 ? 'fas fa-tag me-1 text-danger' : '',
													item.tinhTrang == 0 ? 'fas fa-tag me-1 text-secondary' : ''
												]"></i>
												{{ item.tinhTrang == 1 ? "Đã tích điểm" : "Đã sử dụng điểm" }}
											</small>

											<!-- 👇 thêm thời gian xuống dưới -->
											<div class="text-muted small mt-1">
												{{ new Date(item.ngayTao).toLocaleString('vi-VN') }}
											</div>
										</div>

										<!-- RIGHT -->
										<div class="text-end">
											<div
												:class="item.loai == 1 ? 'text-success fw-bold' : 'text-danger fw-bold'">
												{{ item.loai == 1 ? '+' : '-' }}{{ item.diem }}
											</div>

											<small class="text-warning">
												<i class="fas fa-coins"></i>
											</small>
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
	<!-- Avatar Modal -->
	<!-- Modal Chọn Avatar -->
	<div class="modal fade" id="avatarModal" tabindex="-1">
		<div class="modal-dialog modal-dialog-centered modal-lg">
			<div class="modal-content shadow-lg" style="border-radius: 20px;">

				<div class="modal-header border-0">
					<h5 class="modal-title fw-bold fs-4">Chọn Avatar</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>

				<div class="modal-body">
					<div class="row g-4 justify-content-center">

						<!-- Avatar 1 -->
						<div class="col-4 col-md-3 text-center avatar-container">
							<div class="avatar-wrapper"
								:class="{ selected: selectedAvatar === 'https://res.cloudinary.com/dvxkhsfyj/image/upload/v1762926668/popcornne_ziiohd.png' }"
								@click="selectedAvatar = 'https://res.cloudinary.com/dvxkhsfyj/image/upload/v1762926668/popcornne_ziiohd.png'">

								<img src="https://res.cloudinary.com/dvxkhsfyj/image/upload/v1762926668/popcornne_ziiohd.png"
									class="avatar-option">

								<div class="avatar-tick"><i class="bi bi-check-lg"></i></div>
							</div>
						</div>

						<!-- Avatar 2 -->
						<div class="col-4 col-md-3 text-center avatar-container">
							<div class="avatar-wrapper"
								:class="{ selected: selectedAvatar === 'https://res.cloudinary.com/dvxkhsfyj/image/upload/v1762926652/popcornclient_hditjq.png' }"
								@click="selectedAvatar = 'https://res.cloudinary.com/dvxkhsfyj/image/upload/v1762926652/popcornclient_hditjq.png'">

								<img src="https://res.cloudinary.com/dvxkhsfyj/image/upload/v1762926652/popcornclient_hditjq.png"
									class="avatar-option"
									data-avatar="https://res.cloudinary.com/dvxkhsfyj/image/upload/v1762926652/popcornclient_hditjq.png">
								<div class="avatar-tick"><i class="bi bi-check-lg"></i></div>
							</div>
						</div>

						<!-- Avatar 3 -->
						<div class="col-4 col-md-3 text-center avatar-container">
							<div class="avatar-wrapper"
								:class="{ selected: selectedAvatar === 'https://res.cloudinary.com/dvxkhsfyj/image/upload/v1756310316/popcorn_ry5fwe.png' }"
								@click="selectedAvatar = 'https://res.cloudinary.com/dvxkhsfyj/image/upload/v1756310316/popcorn_ry5fwe.png'">

								<img src="https://res.cloudinary.com/dvxkhsfyj/image/upload/v1756310316/popcorn_ry5fwe.png"
									class="avatar-option"
									data-avatar="https://res.cloudinary.com/dvxkhsfyj/image/upload/v1756310316/popcorn_ry5fwe.png">
								<div class="avatar-tick"><i class="bi bi-check-lg"></i></div>
							</div>
						</div>

					</div>
				</div>

				<div class="modal-footer border-0 justify-content-center">
					<button class="btn btn-secondary px-4" data-bs-dismiss="modal">Hủy</button>
					<button class="btn btn-primary px-4" :disabled="!selectedAvatar" @click="capNhatAvatar()">
						Lưu Avatar
					</button>
				</div>

			</div>
		</div>
	</div>
</template>
<script>
import axios from 'axios';

const token = localStorage.getItem("key_client");
export default {

	data() {
		return {
			selectedAvatar: null,
			profile: {},
			update_pass: {},
			hangLevels: [
				{ ten: "Đồng", min: 0, max: 20 },
				{ ten: "Bạc", min: 20, max: 30 },
				{ ten: "Vàng", min: 30, max: 50 },
				{ ten: "Bạch Kim", min: 50, max: 100 }
			],
			list_lich_su: [],
			ngayTruoc: '',
			ngaySau: '',
			trangThai: '',
		}
	},
	computed: {
		tenHangThanhVien() {
			return this.profile?.hangThanhVien?.tenHang || "Bạc";
		},
		diem() {
			return this.profile?.diem || 0;
		},

		currentLevel() {
			if (this.diem < 20) return { ten: "Đồng", min: 0, max: 20 };
			if (this.diem < 30) return { ten: "Bạc", min: 20, max: 40 };
			if (this.diem < 50) return { ten: "Vàng", min: 40, max: 60 };
			return { ten: "Bạch Kim", min: 60, max: 100 };
		},

		nextLevel() {
			if (this.diem < 20) return { ten: "Bạc", max: 20 };
			if (this.diem < 40) return { ten: "Vàng", max: 40 };
			if (this.diem < 60) return { ten: "Bạch Kim", max: 100 };
			return null;
		},

		progress() {
			const level = this.currentLevel;
			if (!level) return 0;

			return ((this.diem - level.min) / (level.max - level.min)) * 100;
		},
		iconClass() {
			const hang = this?.tenHangThanhVien;

			if (hang === "Đồng") return "fas fa-star";
			if (hang === "Bạc") return "fas fa-shield-alt";
			if (hang === "Vàng") return "fas fa-crown";
			if (hang === "Bạch Kim") return "fas fa-gem";

			return "fas fa-star";
		},
		badgeStyle() {
			const hang = this.tenHangThanhVien;

			if (hang === "Đồng") {
				return "background: linear-gradient(135deg, #E6CFC3, #C89F94); color: #5A3E36;";
			}

			if (hang === "Bạc") {
				return "background: linear-gradient(135deg, #F1F1F1, #D6D6D6); color: #555;";
			}

			if (hang === "Vàng") {
				return "background: linear-gradient(135deg, #FFF3CD, #FFE08A); color: #B8860B;";
			}

			if (hang === "Bạch Kim") {
				return "background: linear-gradient(135deg, #E0F7FA, #B2EBF2); color: #006064;";
			}

			return "background:#eee; color:#333;";
		}
	},
	mounted() {


		this.loadProfile();
		this.getLichSuTichDiem();

	},
	watch: {
		ngayTruoc() {
			this.getLichSuTichDiem();
		},
		ngaySau() {
			this.getLichSuTichDiem();
		},
		trangThai() {
			this.getLichSuTichDiem();
		}
	},
	methods: {

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


		//Hàm kiểm tra dữ liệu khi cập nhật thông tin cá nhân
		validateEditKhachHang(nv) {
			// Kiểm tra xem có để trống trường nào không
			if (!nv.hoVaTen || !nv.email || !nv.soDienThoai || !nv.ngaySinh || !nv.cccd) {
				this.$toast.error("Vui lòng điền đầy đủ thông tin");
				return false;
			}


			// Kiểm tra định dạng số điện thoại (0 + 9-10 chữ số)
			const regexPhone = /^0\d{9,10}$/;
			if (!regexPhone.test(nv.soDienThoai)) {
				this.$toast.error("Số điện thoại không hợp lệ");
				return false;
			}

			// Kiểm tra định dạng CCCD (12 số)
			const regexCCCD = /^\d{12}$/;
			if (!regexCCCD.test(nv.cccd)) {
				this.$toast.error("CCCD phải gồm 12 số");
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


			axios.put('http://localhost:8080/api/client/update-thong-tin', this.profile, {
				headers: {
					Authorization: `Bearer ${token}`
				}
			})
				.then((res) => {
					if (res.data.status) {
						this.$toast.success(res.data.message);
						this.profile = {};
						this.loadProfile();
					} else {
						this.$toast.error("Cập nhật thất bại");
					}
				}).catch((err) => {
					console.error("Error:", err.response);
				});
		},

		capNhatMatKhau() {

			// 1. Kiểm tra trống
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

			axios.post('http://localhost:8080/api/client/change-pass', this.update_pass, {
				headers: {
					Authorization: `Bearer ${token}`
				}
			})
				.then((res) => {
					if (res.data.status) {

						this.$toast.success(res.data.message);
						this.update_pass = {};

					} else {
						this.$toast.error(res.data.message);
					}
				}).catch((error) => {

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
		capNhatAvatar() {
			axios.post("http://localhost:8080/api/client/update-avatar",
				{
					avatarUrl: this.selectedAvatar
				},
				{
					headers: {
						Authorization: `Bearer ${token}`
					}
				}
			)

				.then(res => {
					if (res.data.status) {
						this.$toast.success(res.data.message);
						this.profile.avatar = this.selectedAvatar;

						// đóng modal
						const modal = bootstrap.Modal.getInstance(document.getElementById("avatarModal"));
						modal.hide();
					} else {
						this.$toast.error(res.data.message || "Cập nhật thất bại!");
					}
				})
				.catch(err => {
					this.$toast.error("Lỗi server!");
					console.error(err);
				});
		},

		// hàm lấy lịch sử tích điểm với filter
		getLichSuTichDiem() {
			axios.get('http://localhost:8080/api/client/get-lich-su-diem', {
				params: {
					ngayTruoc: this.ngayTruoc || null,
					ngaySau: this.ngaySau || null,
					tinhTrang: this.trangThai || null
				},
				headers: {
					Authorization: `Bearer ${token}`
				}
			})
				.then((res) => {
					this.list_lich_su = res.data.data;
				});
		},


	}
}
</script>
<style>
.avatar-wrapper {
	position: relative;
	display: inline-block;
}

.avatar-option {
	width: 120px;
	height: 120px;
	object-fit: cover;
	border-radius: 50%;
	border: 4px solid transparent;
	cursor: pointer;
	transition: 0.25s;
}

.avatar-option:hover {
	transform: scale(1.1);
	box-shadow: 0px 0px 12px rgba(13, 110, 253, 0.4);
}

/* Tick dấu ✔ */
.avatar-tick {
	position: absolute;
	bottom: 5px;
	right: 8px;
	background: #0d6efd;
	color: #fff;
	border-radius: 50%;
	width: 30px;
	height: 30px;
	display: none;
	align-items: center;
	justify-content: center;
	font-size: 18px;
}

.avatar-wrapper.selected .avatar-option {
	border-color: #0d6efd !important;
	transform: scale(1.1);
	box-shadow: 0px 0px 15px rgba(13, 110, 253, 0.6);
}

.avatar-wrapper.selected .avatar-tick {
	display: flex;
}
</style>