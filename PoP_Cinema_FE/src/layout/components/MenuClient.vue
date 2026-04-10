<template>
    <header>
        <nav class="navbar navbar-expand-lg navbar-dark py-0" style="background: radial-gradient(circle at top, #020b3a, #000);">
            <div class="container"> <a class="navbar-brand fs-4 fw-bold" href="#"><span class="text-warning">PoP</span>Cinema</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent1" aria-controls="navbarSupportedContent1"
                    aria-expanded="false" aria-label="Toggle navigation"> <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent1">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    </ul>
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0 ">
                        <router-link to="/">
                            <li class="nav-item"> <a class="nav-link  text-light fs-6" aria-current="page" href="#">TRANG CHỦ</a>
                            </li>
                        </router-link>

                        <li class="nav-item dropdown"> <a class="nav-link dropdown-toggle text-light fs-6" href="#" role="button"
                                data-bs-toggle="dropdown" aria-expanded="false" >
                                PHIM
                            </a>
                            <ul class="dropdown-menu dropdown-menu-frontend">
                                <router-link to="/phim/dang-chieu">
                                    <li><a class="dropdown-item dropdown-item-frontend" href="/phim/dang-chieu">Phim Đang Chiếu</a>
                                    </li>
                                </router-link>
                                <router-link to="/phim/sap-chieu" >
                                    <li><a class="dropdown-item dropdown-item-frontend" href="/phim/sap-chieu">Phim Sắp Chiếu</a>
                                    </li>
                                </router-link>
                            </ul>
                        </li>
                        <router-link to="/bai-viet">
                            <li class="nav-item"> <a class="nav-link text-light fs-6" href="/bai-viet">BÀI VIẾT</a>
                            </li>
                        </router-link>
                        <router-link to="/about">
                            <li class="nav-item"> <a class="nav-link text-light fs-6" href="/about">VỀ CHÚNG TÔI</a>
                            </li>
                        </router-link>
                    </ul>

                   
                    <!-- Đã đăng nhập -->
                    <div class="d-flex nav-search" v-if="user_login">
                        <div class="my-2 border radius-30 ">
                            <div class="dropdown">
                                <a class="d-flex align-items-center nav-link dropdown-toggle dropdown-toggle-nocaret"
                                    href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    <img :src=" user_login.avatar || 'https://cellphones.com.vn/sforum/wp-content/uploads/2024/02/anh-avatar-cute-58.jpg'"
                                        class="user-img" style="height: 35px; object-fit: cover;">
                                    <div class="user-info ps-3 pe-3">
                                        <p class="user-name mb-0 text-light">{{ user_login.hoVaTen }}</p>
                                        <p class="designattion mb-0">{{ user_login.email }}</p>
                                    </div>
                                </a>
                                <ul class="dropdown-menu dropdown-menu-frontend ">
                                    <router-link :to="`/client/profile`">
                                        <li><a class="dropdown-item dropdown-item-frontend" href="javascript:;"><i
                                                    class="bx bx-user"></i><span>Thông Tin Cá Nhân</span></a>
                                        </li>
                                    </router-link>
                                    <router-link :to="`/client/danh-sach-don-hang`">
                                        <li><a class="dropdown-item dropdown-item-frontend" href="javascript:;"><i class="fa-solid fa-clock-rotate-left me-1"></i><span>Lịch Sử Giao Dịch</span></a>
                                        </li>
                                    </router-link>
                                    <li><a class="dropdown-item dropdown-item-frontend" href="javascript:;" @click="logout"><i
                                                class="bx bx-log-out-circle"></i><span>Logout</span></a>
                                    </li>
                                </ul>
                            </div>
                        </div>

                    </div>



                </div>
                <!-- Chưa đăng nhập -->
                <ul class="navbar-nav mb-2 mb-lg-0" v-if="!user_login">

                    <router-link to="/client/dang-nhap">
                        <li class="nav-item">
                            <a class="nav-link active fs-6" aria-current="page" href="/client/dang-nhap"> ĐĂNG NHẬP</a>

                        </li>

                    </router-link>
                    <router-link to="/client/dang-ky">
                        <li class="nav-item">
                            <a class="nav-link active fs-6" aria-current="page" href="/client/dang-ky">ĐĂNG KÝ</a>
                        </li>
                    </router-link>
                    <!-- <router-link to="/admin/">
                        <li class="nav-item">
                            <a class="ms-2  nav-link link-secondary fs-6" href="/admin/"> Admin</a>

                        </li>

                    </router-link> -->


                </ul>

            </div>
        </nav>
    </header>
</template>
<script>
import axios from 'axios';

export default {
data() {
        return {
            user_login: null,
        };
    },
    mounted() {
       const token = localStorage.getItem("key_client");
       console.log("Token:", token);

        if (token) {
            axios.get('http://localhost:8080/api/client/me', {
                headers: {
                     Authorization: `Bearer ${token}`
                }
            }).then(res => {
                    if (res.data.status) {
                this.user_login = res.data.data;
                // console.log("User Login:", this.user_login);

            } else {
                this.$toast.error(res.data.message);
                localStorage.removeItem("key_client");
                this.$router.push("/dang-nhap");
            }
            }).catch(() => {
                // Token không hợp lệ hoặc hết hạn
                localStorage.removeItem("key_client");
                this.$router.push('/dang-nhap');
            });
        }
    },
    methods: {
        logout() {
            localStorage.removeItem("key_client");
            this.user_login = null;
            this.$router.push("/");
        }
    }

}
</script>
<style>
/* Dropdown menu styling */
.dropdown-menu-frontend {
    background-color: #ffffff !important;
    border: none !important;
    min-width: 220px;
    padding: 0.5rem 0;
}

/* Dropdown item styling */
.dropdown-item-frontend {
    color: #333;
    font-weight: 400;
    padding: 0.5rem 1.5rem;
    border-left: 4px solid transparent; /* Mặc định trong suốt */
    transition: all 0.2s ease;
}

/* Item đầu tiên màu cam */
.dropdown-item-frontend:first-child {
    color: #0a0a0a;
    font-weight: 500;
}

/* Hiện thanh dọc cam khi hover */
.dropdown-item-frontend:hover {
    background-color: #c8ceef !important;
    color: #110457 !important;  
    border-left: 4px solid #110457 !important; /* Hiện thanh cam */
}
</style>