<template>
    <div class="section-authentication-signin d-flex align-items-center justify-content-center my-5 my-lg-0">
        <div class="container-fluid">
            <div class="row row-cols-1 row-cols-lg-2 row-cols-xl-3">
                <div class="col mx-auto">
                    <div class="card radius-10">
                        <div class="card-body">
                            <div class="">
                                <div class="text-center">
                                    <div class="shadow-1-strong d-flex align-items-center justify-content-center mb-3 mx-auto"
                                        style="width: 150px; height: 150px; ">
                                        <img src="https://res.cloudinary.com/dvxkhsfyj/image/upload/v1756395686/popcorn-removebg-preview_mkbjoj.png"
                                            height="200" alt="" loading="lazy" />
                                    </div>
                                    <h4 class="mb-5">
                                        Bạn quên mật khẩu?

                                    </h4>
                                     
                                    <p>Vui lòng cung cấp email đăng nhập,chúng tôi sẽ gửi link kích hoạt qua email của bạn</p>
                                    
                                    
                                </div>
                            
                                <div class="form-body">
                                    <div class="row g-3">
                                        <div class="col-12">
                                            <label class="form-label">Email</label>


                                            <input  type="email"
                                                class="form-control " placeholder="Nhập Email" v-model="email">

                                        </div>
                                        
                                        <div class="col-md-6">
                                        </div>

                                        <div class="col-12">
                                            <div class="d-grid">
                                                <button v-on:click="resetPassword()" class="btn btn-primary btn-pill" style="background-color: rgb(7, 7, 84);">
                                                    Cấp lại mật khẩu</button>
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
            email: ''
        }
    },
    methods: {
        resetPassword(){

             
            if (!this.email) {
                this.$toast.error("Vui lòng nhập email.");
                return;
            }
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(this.email)) {
                this.$toast.error("Vui lòng nhập địa chỉ email hợp lệ.");
                return;
            }

            axios.post('http://localhost:8080/api/quen-mat-khau?email=' + this.email)
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        this.email = '';
                    } else {
                        this.$toast.error(res.data.message);
                    } 
                });
        }
    },
}
</script>
<style></style>