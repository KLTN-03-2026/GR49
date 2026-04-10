<template>
    <div class="section-authentication-signup d-flex align-items-center justify-content-center">
        <div class="container-fluid">
            <div class="row row-cols-1 row-cols-lg-2 row-cols-xl-3">
                <div class="col-lg-8 mx-auto">
                    <div class="my-4 text-center"></div>
                    <div class="card d-flex">
                        <div class="card-body flex-full">
                            <div class="p-4 rounded">
                                <div class="text-center">
                                    <div class="shadow-1-strong d-flex align-items-center justify-content-center mb-4 mx-auto"
                                        style="width: 150px; height: 150px; ">
                                        <img src="https://res.cloudinary.com/dvxkhsfyj/image/upload/v1756395686/popcorn-removebg-preview_mkbjoj.png"
                                            height="200" alt="" loading="lazy" />
                                    </div>
                                    <h4 class="mb-4">Đăng Ký Tài Khoản
                                    </h4>
                                    <!-- <p>Bạn đã có tài khoản?
                                        <router-link to="/client/dang-nhap">
                                            <a href="/client/dang-nhap">Đăng nhập tại đây</a>
                                        </router-link>
                                    </p> -->
                                </div>
                                <div class="form-body">
                                    <div class="row g-3 ">
                                        <div class="col-12">
                                            <label class="form-label">Họ và tên</label>
                                            <input v-model="user.hoVaTen" type="text" class="form-control"
                                                placeholder="Nhập họ và tên">
                                            <small v-if="errors.hoVaTen" class="text-danger">{{ errors.hoVaTen
                                                }}</small>
                                        </div>
                                        <div class="col-12">
                                            <label for="inputEmailAddress" class="form-label">Email</label>
                                            <input v-model="user.email" type="email" class="form-control"
                                                placeholder="Nhập email">
                                            <small v-if="errors.email" class="text-danger">{{ errors.email }}</small>
                                        </div>
                                        <div class="col-12">
                                            <label class="form-label">Số điện thoại</label>
                                            <input v-model="user.soDienThoai" type="text" class="form-control"
                                                placeholder="Nhập số điện thoại">
                                            <small v-if="errors.soDienThoai" class="text-danger">{{ errors.soDienThoai
                                                }}</small>
                                        </div>
                                        <div class="col-12">
                                            <label class="form-label">Số CCCD</label>
                                            <input v-model="user.cccd" type="text" class="form-control"
                                                placeholder="Nhập số CCCD">
                                            <small v-if="errors.cccd" class="text-danger">{{ errors.cccd }}</small>
                                        </div>
                                        <div class="col-12">
                                            <label class="form-label">Ngày Sinh</label>
                                            <input v-model="user.ngaySinh" type="date" class="form-control"
                                                min="1900-01-01" max="2025-12-31">
                                            <small v-if="errors.ngaySinh" class="text-danger">{{ errors.ngaySinh
                                                }}</small>
                                        </div>
                                        <div class="col-12">
                                            <label class="form-label">Mật khẩu</label>
                                            <div class="input-group" id="show_hide_password">
                                                <input v-model="user.password" type="password"
                                                    class="form-control border-end-0" placeholder="Nhập mật khẩu">
                                                <a href="javascript:;" class="input-group-text bg-transparent">
                                                    <i class="bx bx-hide"></i>
                                                </a>

                                            </div>
                                            <small v-if="errors.password" class="text-danger">{{ errors.password
                                                }}</small>
                                        </div>
                                        <div class="col-12">
                                            <label class="form-label">Nhập Lại Mật khẩu</label>
                                            <div class="input-group" id="show_hide_password">
                                                <input v-model="user.re_password" type="password"
                                                    class="form-control border-end-0" placeholder="Nhập lại mật khẩu">
                                                <a href="javascript:;" class="input-group-text bg-transparent">
                                                    <i class="bx bx-hide"></i>
                                                </a>

                                            </div>
                                            <small v-if="errors.re_password" class="text-danger">{{
                                                errors.re_password }}</small>
                                        </div>
                                        <div class="col-12">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value=""
                                                    id="flexCheckChecked" v-model="dongYDieuKhoan">
                                                <label class="form-check-label" for="flexSwitchCheckChecked">Bằng việc
                                                    đăng ký tài khoản, tôi đồng ý với Điều khoản dịch vụ &amp; Chính
                                                    sách bảo mật của <b>PoPCinema</b>.</label>
                                            </div>
                                        </div>
                                        <div class="col-12">
                                            <div class="d-grid">
                                                <button @click="dangKyTaiKhoan()" type="submit"
                                                    class="btn btn-primary btn-pill"
                                                    style="background-color: rgb(7, 7, 84);"
                                                    :disabled="!dongYDieuKhoan">
                                                    Đăng Ký</button>
                                            </div>
                                        </div>
                                        <div class="login-separater text-center mb-4">
                                            <hr>
                                            <div class="text-center mt-4">Bạn đã có tài khoản?

                                            </div>
                                            <div class="col-12 ">
                                                <div class="d-grid">
                                                    <a href="/client/dang-nhap" class="btn btn-custom btn-pill">Đăng
                                                        nhập</a>
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
    </div>

</template>
<script>
import axios from 'axios'
export default {
    data() {
        return {
            user: {
                hoVaTen: "",
                email: "",
                soDienThoai: "",
                cccd: "",
                ngaySinh: "",
                password: "",
                re_password: "",
            },
            dongYDieuKhoan: false,
            errors: {},
        }
    },
    methods: {

        // Kiểm tra hợp lệ form
        validateForm() {
            this.errors = {};

            // Họ và tên
            if (!this.user.hoVaTen.trim()) {
                this.errors.hoVaTen = "Vui lòng nhập họ và tên.";
            }

            // Email
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!this.user.email) {
                this.errors.email = "Vui lòng nhập email.";
            } else if (!emailRegex.test(this.user.email)) {
                this.errors.email = "Email không hợp lệ.";
            }

            // Số điện thoại
            const phoneRegex = /^(0[0-9]{9})$/;
            if (!this.user.soDienThoai) {
                this.errors.soDienThoai = "Vui lòng nhập số điện thoại.";
            } else if (!phoneRegex.test(this.user.soDienThoai)) {
                this.errors.soDienThoai = "Số điện thoại phải gồm 10 chữ số và bắt đầu bằng 0.";
            }

            // CCCD
            const cccdRegex = /^[0-9]{12}$/;
            if (!this.user.cccd) {
                this.errors.cccd = "Vui lòng nhập số CCCD.";
            } else if (!cccdRegex.test(this.user.cccd)) {
                this.errors.cccd = "Số CCCD phải gồm đúng 12 chữ số.";
            }

            // // Ngày sinh
            // if (!this.user.ngaySinh) {
            //     this.errors.ngaySinh = "Vui lòng chọn ngày sinh.";
            // }


            // Ngày sinh
            if (!this.user.ngaySinh) {
                this.errors.ngaySinh = "Vui lòng chọn ngày sinh.";
            } else {
                const today = new Date();
                const birthday = new Date(this.user.ngaySinh + "T00:00:00");

                // Kiểm tra ngày sinh có hợp lệ
                if (isNaN(birthday.getTime())) {
                    this.errors.ngaySinh = "Ngày sinh không hợp lệ.";
                } else {

                    // Tính tuổi
                    let age = today.getFullYear() - birthday.getFullYear();
                    const m = today.getMonth() - birthday.getMonth();

                    if (m < 0 || (m === 0 && today.getDate() < birthday.getDate())) {
                        age--;
                    }

                    if (age < 18) {
                        this.errors.ngaySinh = "Ngày sinh của bạn phải từ 18 tuổi trở lên.";
                    }

                }
            }


            // Mật khẩu
            const passRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
            if (!this.user.password) {
                this.errors.password = "Vui lòng nhập mật khẩu.";
            } else if (!passRegex.test(this.user.password)) {
                this.errors.password =
                    "Mật khẩu phải có ít nhất 8 ký tự, gồm chữ hoa, chữ thường, số và ký tự đặc biệt.";
            }

            if (!this.user.re_password) {
                this.errors.re_password = "Vui lòng nhập mật khẩu xác nhận.";
            }

            // Nhập lại mật khẩu
            else if (this.user.password !== this.user.re_password) {
                this.errors.re_password = "Mật khẩu nhập lại không khớp.";
            }

            // Đồng ý điều khoản
            if (!this.dongYDieuKhoan) {
                this.errors.dongYDieuKhoan = "Bạn phải đồng ý với điều khoản.";
            }

            // Nếu không có lỗi thì true
            return Object.keys(this.errors).length === 0;
        },
        dangKyTaiKhoan() {
            if (!this.validateForm()) {
                return;
            }
            axios.post('http://localhost:8080/auth/dang-ky', this.user)
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);

                        this.$router.push('/')
                    } else {
                        this.$toast.error('Đăng ký tài khoản thất bại');
                    }
                })
                .catch(() => {
                    this.$toast.error('Có lỗi xảy ra khi kết nối server');
                });
        }
    },
}
</script>
<style>
.section-authentication-signup {
    display: flex;
    align-items: center;
    justify-content: center;
    min-height: 100vh;
    overflow-y: auto;
    /* quan trọng */
}

.btn-custom {
    background-color: #fdfdff;
    color: #070754 !important;
    border: 2px solid #070754;
    /* màu viền */

}

.btn-custom:hover {
    background-color: #070754;
    /* màu khi hover */
    color: #ffffff !important;
}

.form-check-input:focus {
    box-shadow: 0 0 0 0.2rem rgba(7, 7, 84, 0.25);
}

.form-check-input:checked {
    background-color: #070754;
    border-color: #070754;
}
</style>