<template>
    <div class="section-authentication-signin d-flex align-items-center justify-content-center my-5 my-lg-0">
        <div class="container-fluid">
            <div class="row row-cols-1 row-cols-lg-2 row-cols-xl-3">
                <div class="col mx-auto">
                    <div class="card radius-10">
                        <div class="card-body">
                            <div class="p-4 rounded">
                                <div class="text-center">
                                    <div class="shadow-1-strong d-flex align-items-center justify-content-center mb-3 mx-auto"
                                        style="width: 150px; height: 150px; ">
                                        <img src="https://res.cloudinary.com/dvxkhsfyj/image/upload/v1756395686/popcorn-removebg-preview_mkbjoj.png"
                                            height="200" alt="" loading="lazy" />
                                    </div>
                                    <h4 class="mb-5">
                                        Đặt lại mật khẩu

                                    </h4>



                                </div>

                                <div class="form-body">
                                    <div class="row g-3">
                                        <div class="col-12">
                                            <label class="form-label">Mật khẩu mới</label>


                                            <input type="password" class="form-control " placeholder="Nhập mật khẩu mới"
                                                v-model="request.matKhauMoi">

                                        </div>

                                        <div class="col-12">
                                            <label class="form-label">Xác nhận mật khẩu mới</label>


                                            <input type="password" class="form-control "
                                                placeholder="Xác nhận lại mật khẩu mới"
                                                v-model="request.xacNhanMatKhauMoi">

                                        </div>

                                        <div class="col-md-6">
                                        </div>

                                        <div class="col-12">
                                            <div class="d-grid">
                                                <button v-on:click="resetPassword()" class="btn btn-primary btn-pill"
                                                    style="background-color: rgb(7, 7, 84);">
                                                    Xác nhận </button>
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

export default {
    props: ["hash_reset"],
    data() {
        return {
            hash_reset: this.$route.params.hash_reset,
            request: {},
        }
    },
    methods: {
        resetPassword() {

            if (!this.request.matKhauMoi || !this.request.xacNhanMatKhauMoi) {
                this.$toast.error("Vui lòng điền đầy đủ thông tin.");
                return;
            }
            if (this.request.matKhauMoi !== this.request.xacNhanMatKhauMoi) {
                this.$toast.error("Mật khẩu mới và xác nhận mật khẩu mới không khớp.");
                return;
            }

            // 3. Kiểm tra regex mật khẩu mới (khớp backend)
			const regexPass = /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
			if (!regexPass.test(this.request.matKhauMoi)) {
				this.$toast.error("Mật khẩu mới phải có ít nhất 8 ký tự, bao gồm chữ hoa, số và ký tự đặc biệt.");
				return;
			}

            axios.post('http://localhost:8080/api/dat-lai-mat-khau?maReset=' + this.hash_reset, this.request)
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.request = {};
                    } else {
                        this.$toast.error(res.data.message);
                    }
                });
        }
    },
}
</script>
<style></style>