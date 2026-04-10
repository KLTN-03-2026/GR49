<template>
    <div class="flex flex-col items-center justify-center min-h-screen bg-green-50">
    <div class="bg-white shadow-lg rounded-xl p-8 text-center">
       <div class="shadow-1-strong d-flex align-items-center justify-content-center mb-4 mx-auto"
        style="width: 150px; height: 150px; ">
        <img src="https://res.cloudinary.com/dvxkhsfyj/image/upload/v1756445854/3-removebg-preview_svmz5i.png"
          height="200" alt="" loading="lazy" />
      </div>
      <h2 class="text-2xl font-bold text-green-600">Xin chúc mừng! Tài khoản của bạn đã được kích hoạt thành công</h2>
      <p class="mt-3 text-gray-600">
        
       Hãy đi đến trang đăng nhập để bắt đầu sử dụng dịch vụ của chúng tôi. <a href="/client/dang-nhap" class="text-blue-500 underline">Đăng nhập ngay</a>
      </p>
      <router-link to="/" >
        Quay lại trang chủ
      </router-link>
    </div>
  </div>
</template>
<script>
import axios from 'axios';
export default {
    props: ["hash_active"],
    data() {
        return {
            hash_active: this.$route.params.hash_active,
        }
    },
    mounted() {
        this.kichHoat();
    },
    methods: {
        kichHoat() {
           
            axios.post('http://localhost:8080/api/kich-hoat?maKichHoat=' + this.hash_active)
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                        // this.$router.push('/client/dang-nhap');
                    } else {
                        this.$toast.error(res.data.message);
                    }
                });

        }
    },
}
</script>
<style></style>