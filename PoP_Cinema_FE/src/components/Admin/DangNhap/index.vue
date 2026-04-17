<template>
    <div class="section-authentication-signin d-flex align-items-center justify-content-center my-5 my-lg-0">
        <div class="container-fluid">
            <div class="row row-cols-1 row-cols-lg-2 row-cols-xl-3">
                <div class="col mx-auto">
                    <div class="card radius-10">
                        <div class="card-body">
                            <div class=" p-4 rounded">
                                <div class="text-center mb-4">
                                    <div class="shadow-1-strong d-flex align-items-center justify-content-center mb-4 mx-auto"
                                        style="width: 150px; height: 150px; ">
                                        <img src="https://res.cloudinary.com/dvxkhsfyj/image/upload/v1756446509/popcorn_admin-removebg-preview_maxjqu.png"
                                            height="200" alt="" loading="lazy" />
                                    </div>
                                    <h4 class="mt-3 font-weight-bold  text-nowrap">Đăng Nhập</h4>
                                </div>
                                <div class="form-body">
                                    <div class="row g-3">
                                        <div class="col-12">
                                            <label class="form-label">Email</label>
                                            <div class="input-group">
                                            
                                                <input v-model="thong_tin_dang_nhap.email" type="email"
                                                    class="form-control " placeholder="Nhập Email">
                                            </div>
                                        </div>
                                        <div class="col-12">
                                            <label class="form-label">Mật khẩu</label>
                                            <div class="input-group">
                                                
                                                <input v-model="thong_tin_dang_nhap.password" type="password"
                                                    class="form-control " placeholder="Nhập Mật khẩu">
                                            </div>
                                        </div>
                                        <div class="col-12">
                                            <div class="d-grid">
                                                <button @click="dangNhap()" class="btn btn-primary"  style="background-color:  #410441 ;">
                                                 
                                                    Đăng Nhập
                                                </button>
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
            axios.post('http://localhost:8080/auth/nhan-viens/dang-nhap', this.thong_tin_dang_nhap)
                .then((res) => {
                    if (res.data.status) {
                        console.log(res.data.data);

                        localStorage.setItem('key_admin', res.data.data.jwt)
                        const role = res.data.role;
                        if (role === 'ROLE_ADMIN' || role === 'ROLE_STAFF') {

                            this.$toast.success(res.data.message);
                            this.$router.push('/admin')
                        } else {
                            this.$toast.error('Bạn không có quyền truy cập!');
                        }

                    }else {     
                        this.$toast.error(res.data.message);
                    }
                    
                })
                .catch(() => {
                    this.$toast.error('Đăng nhập thất bại!');
                });
        }
    },
}
</script>
