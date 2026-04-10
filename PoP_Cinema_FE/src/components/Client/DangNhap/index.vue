<template>
    <div class="section-authentication-signin d-flex align-items-center justify-content-center my-5 my-lg-0">
        <div class="container-fluid">
            <div class="row row-cols-1 row-cols-lg-2 row-cols-xl-3">
                <div class="col mx-auto">
                    <div class="card radius-10">
                        <div class="card-body">
                            <div class=" p-4 rounded">
                                <div class="text-center">
                                    <div class="shadow-1-strong d-flex align-items-center justify-content-center mb-4 mx-auto"
                                        style="width: 150px; height: 150px; ">
                                        <img src="https://res.cloudinary.com/dvxkhsfyj/image/upload/v1756395686/popcorn-removebg-preview_mkbjoj.png"
                                            height="200" alt="" loading="lazy" />
                                    </div>
                                    <h4 class="mb-4">Đăng Nhập Tài Khoản

                                    </h4>

                                </div>
                                <!-- <div class="d-grid">
                                    <a class="btn my-3 shadow-sm btn-white" href="javascript:;"> <span
                                            class="d-flex justify-content-center align-items-center">
                                            <img class="me-2" src="../../../assets/images/icons/search.svg" width="16"
                                                alt="Image Description">
                                            <span>Đăng nhập bằng Google</span>
                                        </span>
                                    </a>
                                </div> -->
                                <!-- <div class="login-separater text-center mb-4">
                                    <span>OR</span>
                                    <hr>
                                </div> -->
                                <div class="form-body">
                                    <div class="row g-3">
                                        <div class="col-12">
                                            <label class="form-label">Email</label>


                                            <input v-model="thong_tin_dang_nhap.email" type="email"
                                                class="form-control " placeholder="Nhập Email">

                                        </div>
                                        <div class="col-12">
                                            <label class="form-label">Mật khẩu</label>

                                            <input v-model="thong_tin_dang_nhap.password" type="password"
                                                class="form-control " placeholder="Mật khẩu">

                                        </div>
                                        <div class="col-md-6">
                                        </div>

                                        <div class="col-12">
                                            <div class="d-grid">
                                                <button v-on:click="dangNhap()" class="btn btn-primary btn-pill" style="background-color: rgb(7, 7, 84);">Đăng
                                                    Nhập</button>
                                            </div>
                                        </div>
                                        <div class="col-md-5"><a href="/client/quen-mat-khau" class="forgot-link">Quên mật khẩu?</a>
                                        </div>

                                    </div>
                                    <div class="login-separater text-center mb-4">
                                        <hr>
                                    </div>
                                    <div class="text-center">Bạn chưa có tài khoản? 
                                        <!-- <router-link
                                            to="/client/dang-ky">Đăng ký ngay</router-link> -->
                                    </div>
                                    <div class="col-12 ">
                                            <div class="d-grid">
                                                <a href="/client/dang-ky" class="btn btn-custom btn-pill" >Đăng ký</a>
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
    data() {
        return {
            thong_tin_dang_nhap: {}
        }
    },
    mounted() {

        //De tat modal khi load trang
        document.querySelectorAll('.modal-backdrop').forEach(el => el.remove());
        document.body.classList.remove('modal-open');
        document.body.style = '';
    },
    methods: {
        dangNhap() {
            if (!this.thong_tin_dang_nhap.email || !this.thong_tin_dang_nhap.password) {
                this.$toast.error('Vui lòng nhập đầy đủ thông tin đăng nhập!');
                return;
            }

            axios.post('http://localhost:8080/auth/dang-nhap', this.thong_tin_dang_nhap)
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        console.log(res.data.data);

                        localStorage.setItem('key_client', res.data.data.jwt)

                        this.$router.push('/')
                    } else {
                        this.$toast.error(res.data.message);
                    }
                })
                .catch(() => {
                    this.$toast.error('Email hoặc mật khẩu không đúng!');
                });
        }
    },
}
</script>
<style>
.forgot-link {
  color: rgb(46, 43, 43);              /* mặc định màu đen */
  text-decoration: none;     /* bỏ gạch chân */
  transition: color 0.3s;    /* hiệu ứng mượt khi đổi màu */
}

.forgot-link:hover {
  color:#070754;   /* khi hover thì đổi sang xanh đậm */
}
.btn-custom {
        background-color: #fdfdff; 
        color: #070754 !important;
        border: 2px solid #070754; /* màu viền */
    
    }
.btn-custom:hover {
        background-color: #070754;  /* màu khi hover */
         color: #ffffff !important;
    }
</style>