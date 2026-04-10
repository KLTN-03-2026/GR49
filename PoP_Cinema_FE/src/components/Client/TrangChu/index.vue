<template>
    <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-indicators">
            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active"
                aria-current="true" aria-label="Slide 1"></button>
            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1"
                aria-label="Slide 2"></button>
            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2"
                aria-label="Slide 3"></button>
        </div>
        <div class="carousel-inner">
            <div class="carousel-item active">

                <img src="https://metiz.vn/media/slide_home/bo-tu-sieu-dang.jpg" class="d-block w-100" alt="Banner 1">
                <div class="carousel-caption d-none d-md-block">
                </div>
            </div>

            <div class="carousel-item">
                <img src="https://metiz.vn/media/slide_home/PHIMHOT_T8Artboard_1_copy3x.png" class="d-block w-100"
                    alt="Banner 2">
                <div class="carousel-caption d-none d-md-block">
                </div>
            </div>
            <div class="carousel-item">
                <img src="https://cdn.galaxycine.vn/media/2025/7/21/mang-me-di-bo-2048_1753070307369.jpg"
                    class="d-block w-100" alt="Banner 3">
                <div class="carousel-caption d-none d-md-block">
                </div>
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators"
            data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators"
            data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>

    <div class="container my-4">

        <!-- THẾ GIỚI ĐIỆN ẢNH -->
        <section class="cinema-world-section py-5 mx-3 rounded-5 shadow-lg">
            <div class="container">
                <!-- Tiêu đề -->
                <div class="text-center mb-4">
                    <h3 class="text-uppercase fw-bold text-light" style="letter-spacing: 2px;">Thế Giới Điện Ảnh</h3>
                    <p class="text-secondary fs-5">Khám phá những bom tấn, kiệt tác điện ảnh và câu chuyện chưa kể!</p>
                </div>

                <!-- Carousel -->
                <div id="cinemaCarousel" class="carousel slide" data-bs-ride="carousel">
                    <div class="carousel-inner">

                        <!-- Slide 1 -->
                        <div class="carousel-item active">
                            <div class="row g-4">
                                <div class="col-md-3 col-sm-6">
                                    <div
                                        class="movie-card-slider rounded-4 overflow-hidden shadow-lg position-relative">
                                        <img src="https://cdn.galaxycine.vn/media/2025/10/21/predator-500_1761029949058.jpg"
                                            class="img-fluid" alt="">
                                        <div class="movie-overlay d-flex flex-column justify-content-end p-3">
                                            <h6 class="fw-bold text-white">Deadpool & Wolverine</h6>
                                            <small class="text-light">Hành động • 2025</small>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-3 col-sm-6">
                                    <div
                                        class="movie-card-slider rounded-4 overflow-hidden shadow-lg position-relative">
                                        <img src="https://cdn.galaxycine.vn/media/2025/9/19/au-be-ca-heo-500_1758256459385.jpg"
                                            class="img-fluid" alt="">
                                        <div class="movie-overlay d-flex flex-column justify-content-end p-3">
                                            <h6 class="fw-bold text-white">Deadpool & Wolverine</h6>
                                            <small class="text-light">Hành động • 2025</small>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-3 col-sm-6">
                                    <div
                                        class="movie-card-slider rounded-4 overflow-hidden shadow-lg position-relative">
                                        <img src="https://cdn.galaxycine.vn/media/2025/10/29/godzilla-500_1761720005919.jpg"
                                            class="img-fluid" alt="">
                                        <div class="movie-overlay d-flex flex-column justify-content-end p-3">
                                            <h6 class="fw-bold text-white">Dune: Part Two</h6>
                                            <small class="text-light">Khoa học viễn tưởng • 2024</small>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-3 col-sm-6">
                                    <div
                                        class="movie-card-slider rounded-4 overflow-hidden shadow-lg position-relative">
                                        <img src="https://cdn.galaxycine.vn/media/2025/7/30/avatar-3-500_1753848148849.jpg"
                                            class="img-fluid" alt="">
                                        <div class="movie-overlay d-flex flex-column justify-content-end p-3">
                                            <h6 class="fw-bold text-white">Avatar: The Way of Water</h6>
                                            <small class="text-light">Phiêu lưu • 2022</small>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Slide 2 -->
                        <div class="carousel-item">
                            <div class="row g-4">
                                <div class="col-md-3 col-sm-6">
                                    <div
                                        class="movie-card-slider rounded-4 overflow-hidden shadow-lg position-relative">
                                        <img src="https://cdn.galaxycine.vn/media/2025/11/5/nam-dem-kinh-hoang-500_1762335963489.jpg"
                                            class="img-fluid" alt="">
                                        <div class="movie-overlay d-flex flex-column justify-content-end p-3">
                                            <h6 class="fw-bold text-white">Venom 3: The Last Dance</h6>
                                            <small class="text-light">Siêu anh hùng • 2025</small>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-3 col-sm-6">
                                    <div
                                        class="movie-card-slider rounded-4 overflow-hidden shadow-lg position-relative">
                                        <img src="https://cdn.galaxycine.vn/media/2025/10/28/bau-vat-troi-cho-500_1761625726896.jpg"
                                            class="img-fluid" alt="">
                                        <div class="movie-overlay d-flex flex-column justify-content-end p-3">
                                            <h6 class="fw-bold text-white">Godzilla x Kong: The New Empire</h6>
                                            <small class="text-light">Hành động • 2024</small>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-3 col-sm-6">
                                    <div
                                        class="movie-card-slider rounded-4 overflow-hidden shadow-lg position-relative">
                                        <img src="https://cdn.galaxycine.vn/media/2025/10/10/tron-ares-500_1760067381479.jpg"
                                            class="img-fluid" alt="">
                                        <div class="movie-overlay d-flex flex-column justify-content-end p-3">
                                            <h6 class="fw-bold text-white">Kung Fu Panda 4</h6>
                                            <small class="text-light">Hoạt hình • 2024</small>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-3 col-sm-6">
                                    <div
                                        class="movie-card-slider rounded-4 overflow-hidden shadow-lg position-relative">
                                        <img src="https://cdn.galaxycine.vn/media/2025/11/3/zootopia-500_1762159348935.jpg"
                                            class="img-fluid" alt="">
                                        <div class="movie-overlay d-flex flex-column justify-content-end p-3">
                                            <h6 class="fw-bold text-white">Kung Fu Panda 4</h6>
                                            <small class="text-light">Hoạt hình • 2024</small>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Nút điều hướng -->
                    <button class="carousel-control-prev" type="button" data-bs-target="#cinemaCarousel"
                        data-bs-slide="prev">
                        <span class="carousel-control-prev-icon bg-dark rounded-circle p-3" aria-hidden="true"></span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#cinemaCarousel"
                        data-bs-slide="next">
                        <span class="carousel-control-next-icon bg-dark rounded-circle p-3" aria-hidden="true"></span>
                    </button>
                </div>
            </div>
        </section>


        <!-- DỊCH VỤ OIZOIOI CINEMA -->
        <section class="services-section py-5 mt-5 mx-3">
            <h3 class="text-center fw-bold mb-5 text-dark">Trải Nghiệm Cùng PoP</h3>
            <div class="row justify-content-center text-center">
                <div class="col-md-3 col-sm-6 mb-4" v-for="item in [
                    { icon: 'fa-ticket', color: '#0d6efd', title: 'Đặt vé nhanh chóng', text: 'Chọn ghế trực tuyến, không cần chờ đợi, chỉ với vài thao tác đơn giản.' },
                    { icon: 'fa-film', color: '#6610f2', title: 'Kho phim đa dạng', text: 'Từ bom tấn Hollywood đến phim Việt và hoạt hình cho trẻ em.' },
                    { icon: 'fa-clock', color: '#ffc107', title: 'Lịch chiếu linh hoạt', text: 'Suất chiếu trải dài cả ngày, phù hợp mọi khung giờ rảnh rỗi.' },
                    { icon: 'fa-shopping-cart', color: '#198754', title: 'Combo bắp nước ưu đãi', text: 'Nhiều lựa chọn hấp dẫn, ưu đãi đặc biệt cho thành viên thân thiết.' }
                ]">
                    <div class="service-card p-4 h-100">
                        <div class="icon-wrapper mx-auto mb-3"
                            :style="`background: ${item.color}1A; color: ${item.color}`">
                            <i :class="`fa ${item.icon} fa-2x`"></i>
                        </div>
                        <h5 class="fw-bold mb-2">{{ item.title }}</h5>
                        <p class="text-muted small">{{ item.text }}</p>
                    </div>
                </div>
            </div>
        </section>


        <!-- PHIM ĐANG CHIẾU -->
        <h3 class="text-center mb-4 mt-5 text-dark">PHIM ĐANG CHIẾU</h3>
        <div class="row g-4 ">
            <template v-for="(value, index) in list_phim.filter(x => x.tinhTrang === 2).slice(0, 4)" :key="index">
                <div class="col-lg-3 col-md-6 col-sm-12">
                    <div class="card movie-card border-0 shadow-sm h-100">
                        <div class="position-relative">
                            <img :src="value.hinhAnh" class="card-img-top rounded-3" alt=""
                                style="height: 450px; object-fit: cover;" />
                            <!-- Điểm số -->
                            <div
                                class="rating-badge position-absolute bottom-0 start-0 m-2 px-2 py-1 bg-dark text-warning rounded-2 fw-bold">
                                <i class="fa-solid fa-star me-1"></i>{{ ((value.diemTrungBinh || 0) * 2).toFixed(1) ||
                                    8.0 }}
                            </div>
                            <!-- Độ tuổi -->
                            <div
                                class="age-badge position-absolute bottom-0 end-0 m-2 px-2 py-1 bg-warning text-dark rounded-2 fw-bold">
                                {{ value.doTuoi || 'T16' }}
                            </div>
                        </div>

                        <div class="card-body text-center">
                            <h6 class="fw-bold text-dark mb-1 text-truncate">{{ value.tenPhim }}</h6>
                            <p class="text-muted small mb-2">{{ value.theLoai }}</p>
                            <p class="text-muted small mb-3">
                                <i class="fa-regular fa-calendar me-1"></i>{{ value.ngayPhatHanh || '31.10.2025' }}
                            </p>
                            <router-link :to="`/chi-tiet-phim/${value.id}`">
                                <button class="btn btn-outline-darkblue rounded-pill px-4">
                                    Đặt vé
                                </button>
                            </router-link>
                        </div>
                    </div>
                </div>
            </template>
        </div>

        <div class="text-center mt-5 mb-4">
            <router-link to="/phim/dang-chieu">
                <button class="btn btn-outline-darkblue-square px-4 py-2">
                    Xem thêm <i class="fa-solid fa-chevron-right ms-1"></i>
                </button>
            </router-link>
        </div>

        <!-- Về chúng tôi -->
        <section class="about-section py-5 rounded-4 shadow-lg mx-3"
            style="background: linear-gradient(135deg, #0d1b2a, #1b263b); color: #f8f9fa;">
            <div class="container">
                <div class="text-center mb-5">
                    <h3 class="text-uppercase fw-bold" style="letter-spacing: 2px; color: #ffc107;">Về Chúng Tôi</h3>
                    <p class="text-light mt-2 fs-5">
                        Trải nghiệm điện ảnh đẳng cấp – nhanh chóng, tiện lợi và đầy cảm xúc cùng <span
                            class="fw-bold text-warning">PoP Cinema</span>.
                    </p>
                </div>

                <div class="row align-items-center g-4">
                    <!-- Hình minh họa -->
                    <div class="col-md-5 text-center">
                        <div class="position-relative d-inline-block">
                            <img src="https://res.cloudinary.com/dvxkhsfyj/image/upload/v1762926832/popcornne-removebg-preview_nl4pv6.png"
                                alt="OIZOIOI Cinema" class="img-fluid rounded-4 shadow-lg"
                                style="max-height: 360px; object-fit: cover;">
                            <div
                                class="position-absolute bottom-0 start-50 translate-middle-x bg-dark bg-opacity-75 text-light px-3 py-2 rounded-top">
                                <i class="fa-solid fa-film me-1 text-warning"></i> Hơn 200+ suất chiếu mỗi ngày
                            </div>
                        </div>
                    </div>

                    <!-- Nội dung giới thiệu -->
                    <div class="col-md-7">
                        <h4 class="fw-bold mb-3">
                            <span class="text-warning">Trải nghiệm điện ảnh </span>
                            <span class="text-primary">sống động </span>
                            <span class="text-light">với PoP Cinema</span>
                        </h4>

                        <p class="fs-5 mb-4 text-light-50">
                            Chúng tôi mang đến nền tảng đặt vé xem phim thông minh – dễ dùng, nhanh chóng, cùng nhiều ưu
                            đãi độc
                            quyền.
                            Hãy để PoP đồng hành cùng bạn trong từng khoảnh khắc điện ảnh đáng nhớ.
                        </p>

                        <div class="row">
                            <div class="col-6 mb-3">
                                <div class="d-flex align-items-center">
                                    <div class="icon-circle bg-success bg-opacity-25 text-success me-3">
                                        <i class="bi bi-ticket-perforated fs-4"></i>
                                    </div>
                                    <span>Mua vé trực tuyến 24/7</span>
                                </div>
                            </div>

                            <div class="col-6 mb-3">
                                <div class="d-flex align-items-center">
                                    <div class="icon-circle bg-danger bg-opacity-25 text-danger me-3">
                                        <i class="fa-solid fa-chair fs-4"></i>
                                    </div>
                                    <span>Tự do chọn chỗ ngồi yêu thích</span>
                                </div>
                            </div>

                            <div class="col-6 mb-3">
                                <div class="d-flex align-items-center">
                                    <div class="icon-circle bg-warning bg-opacity-25 text-warning me-3">
                                        <i class="fa-solid fa-popcorn fs-4"></i>
                                    </div>
                                    <span>Combo bắp nước siêu ưu đãi</span>
                                </div>
                            </div>

                            <div class="col-6 mb-3">
                                <div class="d-flex align-items-center">
                                    <div class="icon-circle bg-info bg-opacity-25 text-info me-3">
                                        <i class="fa-solid fa-clock fs-4"></i>
                                    </div>
                                    <span>Lưu lịch sử & nhắc lịch chiếu</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- PHIM SẮP CHIẾU -->
        <h3 class="text-center mb-4 mt-5 text-dark">PHIM SẮP CHIẾU</h3>
        <div class="row">
            <template v-for="(value, index) in list_phim.filter(x => x.tinhTrang === 1).slice(0, 4)" :key="index">
                <div class="col-md-3 mb-4 d-flex">
                    <div class="card flex-full w-100" style="background-color: #c6dcfe;">
                        <img :src="value.hinhAnh" class="card-img-top" style="height: 350px;">
                        <div class="card-body">
                            <span class="badge bg-info mb-2">{{ value.ngayPhatHanh }}</span>
                            <h5 class="card-title text-truncate text-dark">{{ value.tenPhim }}</h5>
                            <p class="card-text">{{ value.theLoai }}</p>
                            <button class="btn btn-outline-darkblue btn-sm mt-2">Nhắc tôi</button>
                        </div>
                    </div>
                </div>
            </template>
        </div>
        <div class="text-center mt-5 mb-4">
            <router-link to="/phim/sap-chieu">
                <button class="btn btn-outline-darkblue-square px-4 py-2">
                    Xem thêm phim <i class="fa-solid fa-chevron-right ms-1"></i>
                </button>
            </router-link>
        </div>

        <!-- BÀI VIẾT -->
        <h3 class="text-center mb-4 mt-5 text-dark">BÀI VIẾT</h3>
        <div class="row mb-5">

            <!-- BÀI VIẾT LỚN BÊN TRÁI -->
            <div class="col-lg-8 mb-4">
                <!-- <router-link :to="`/chi-tiet-bai-viet/${bai_noi_bat.id}`" class="text-decoration-none"> -->
                <div class="featured-card hover-zoom rounded overflow-hidden shadow-sm">
                    <img src="https://www.galaxycine.vn/media/2025/10/7/750_1759821428226.jpg"
                        class="img-fluid w-100 featured-img" style="height: 420px; object-fit: cover;">
                </div>
                <h3 class="mt-5 text-dark fw-bold hover-title">Điều Gì Xảy Ra Trong Predator:Badlands?</h3>
                <!-- </router-link> -->
            </div>

            <!-- DANH SÁCH BÊN PHẢI -->
            <div class="col-lg-4">

                <template v-for="(item, index) in list_bai_viet.slice(0, 3)" :key="index">
                    <div class="d-flex mb-4 review-item hover-box rounded">

                        <!-- ẢNH NHỎ -->
                        <router-link :to="`/chi-tiet-bai-viet/${item.id}`">
                            <div class="thumb-wrapper rounded overflow-hidden me-3">
                                <img :src="item.hinhAnh" class="thumb-img">
                            </div>
                        </router-link>

                        <!-- NỘI DUNG -->
                        <div>
                            <router-link :to="`/chi-tiet-bai-viet/${item.id}`" class="text-decoration-none">
                                <h6 class="fw-semibold text-dark hover-title line-clamp-2">{{ item.tieuDe }}</h6>
                            </router-link>

                            <div class="d-flex align-items-center text-muted small mt-1">
                                <i class="bi bi-hand-thumbs-up-fill me-1"></i>
                            </div>
                        </div>

                    </div>
                </template>

            </div>

        </div>


        <!-- TIN KHUYẾN MÃI -->
        <section class="promo-section py-5 mx-3 ">
            <div class="container">
                <!-- Tiêu đề -->
                <div class="text-center mb-4 ">
                    <h3 class="text-uppercase fw-bold text-dark" style="letter-spacing: 2px;">Tin Khuyến Mãi</h3>
                    <p class="text-secondary fs-5">Cập nhật ưu đãi cực hot – xem phim thả ga, tiết kiệm tối đa!</p>
                </div>

                <!-- Carousel -->
                <div id="promoCarousel" class="carousel slide" data-bs-ride="carousel">
                    <div class="carousel-inner">

                        <!-- Slide 1 -->
                        <div class="carousel-item active">
                            <div class="row g-4">
                                <div class="col-md-3 col-sm-6">
                                    <div class="promo-card rounded-4 shadow-sm overflow-hidden">
                                        <img src="https://cdn.galaxycine.vn/media/2025/9/26/lio-t9-galaxy-banner-app-1800x1200_1758894324797.jpg"
                                            class="img-fluid promo-img" alt="">
                                        <div class="promo-overlay p-3">
                                            <h6 class="fw-bold text-white">Tải App Liobank Giảm Ngay 60K Khi Mua Vé</h6>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-3 col-sm-6">
                                    <div class="promo-card rounded-4 shadow-sm overflow-hidden">
                                        <img src="https://cdn.galaxycine.vn/media/2025/9/24/imax-treasure-hunt-3_1758703263482.jpg"
                                            class="img-fluid promo-img" alt="">
                                        <div class="promo-overlay p-3">
                                            <h6 class="fw-bold text-white">Voucher ShopeePay Giảm Đến 50K</h6>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-3 col-sm-6">
                                    <div class="promo-card rounded-4 shadow-sm overflow-hidden">
                                        <img src="https://cdn.galaxycine.vn/media/2025/9/4/momo-galaxy-1_1756958473463.jpg"
                                            class="img-fluid promo-img" alt="">
                                        <div class="promo-overlay p-3">
                                            <h6 class="fw-bold text-white">Happy Day - Vé Chỉ Từ 45K</h6>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-3 col-sm-6">
                                    <div class="promo-card rounded-4 shadow-sm overflow-hidden">
                                        <img src="https://metiz.vn/media/new_offer/Deadpool-Wolverine.jpg"
                                            class="img-fluid promo-img" alt="">
                                        <div class="promo-overlay p-3">
                                            <h6 class="fw-bold text-white">Ưu Đãi Thành Viên Galaxy Cinema 2025</h6>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Slide 2 -->
                        <div class="carousel-item">
                            <div class="row g-4">
                                <div class="col-md-3 col-sm-6">
                                    <div class="promo-card rounded-4 shadow-sm overflow-hidden">
                                        <img src="https://cdn.galaxycine.vn/media/2025/10/29/750_1761720681370.jpg"
                                            class="img-fluid promo-img" alt="">
                                        <div class="promo-overlay p-3">
                                            <h6 class="fw-bold text-white">Giảm 30K Khi Thanh Toán Qua MoMo</h6>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-3 col-sm-6">
                                    <div class="promo-card rounded-4 shadow-sm overflow-hidden">
                                        <img src="https://cdn.galaxycine.vn/media/2023/11/23/giaveu22-digital-1800x1200_1700731546949.jpg"
                                            class="img-fluid promo-img" alt="">
                                        <div class="promo-overlay p-3">
                                            <h6 class="fw-bold text-white">Giảm 20K Khi Dùng ZaloPay</h6>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-3 col-sm-6">
                                    <div class="promo-card rounded-4 shadow-sm overflow-hidden">
                                        <img src="https://metiz.vn/media/new_offer/hang-muc-thanh-vien-metiz-2023.jpg"
                                            class="img-fluid promo-img" alt="">
                                        <div class="promo-overlay p-3">
                                            <h6 class="fw-bold text-white">FPT Play Combo Xem Phim Chất</h6>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-3 col-sm-6">
                                    <div class="promo-card rounded-4 shadow-sm overflow-hidden">
                                        <img src="https://cdn.galaxycine.vn/media/2025/8/25/zalopay-1_1756131251308.jpg"
                                            class="img-fluid promo-img" alt="">
                                        <div class="promo-overlay p-3">
                                            <h6 class="fw-bold text-white">Techcombank Ưu Đãi Độc Quyền</h6>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>

                    <!-- Nút điều hướng -->
                    <button class="carousel-control-prev" type="button" data-bs-target="#promoCarousel"
                        data-bs-slide="prev">
                        <span class="carousel-control-prev-icon bg-dark rounded-circle p-3" aria-hidden="true"></span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#promoCarousel"
                        data-bs-slide="next">
                        <span class="carousel-control-next-icon bg-dark rounded-circle p-3" aria-hidden="true"></span>
                    </button>
                </div>
            </div>
        </section>

        <!-- KHÁCH HÀNG NÓI GÌ VỀ OIZOIOI CINEMA -->

        <section class="testimonials py-5">
            <h3 class="text-center fw-bold mb-5 text-light">Khách Hàng Nói Gì Về <span class="text-primary">PoP
                    Cinema</span>?</h3>
            <div class="container">
                <div class="row justify-content-center">
                    <!-- Card 1 -->
                    <div class="col-md-4 mb-4">
                        <div class="testimonial-card p-4 text-light">
                            <div class="d-flex align-items-center mb-3">
                                <img src="https://randomuser.me/api/portraits/men/32.jpg" alt="avatar"
                                    class="rounded-circle me-3" width="60" height="60">
                                <div>
                                    <h5 class="mb-0 fw-bold">Nguyễn Văn A</h5>
                                    <small class="text-secondary">CEO Công ty ABC</small>
                                </div>
                            </div>
                            <div class="mb-2 text-warning">
                                &#9733;&#9733;&#9733;&#9733;&#9733;
                            </div>
                            <p class="fst-italic mb-0">“Không gian hiện đại, âm thanh sống động và dịch vụ tuyệt vời.
                                Tôi và gia
                                đình luôn chọn OIZOIOI Cinema mỗi cuối tuần để thư giãn.”</p>
                        </div>
                    </div>

                    <!-- Card 2 -->
                    <div class="col-md-4 mb-4">
                        <div class="testimonial-card p-4 text-light">
                            <div class="d-flex align-items-center mb-3">
                                <img src="https://randomuser.me/api/portraits/women/44.jpg" alt="avatar"
                                    class="rounded-circle me-3" width="60" height="60">
                                <div>
                                    <h5 class="mb-0 fw-bold">Trần Thị B</h5>
                                    <small class="text-secondary">Giám đốc XYZ Shop</small>
                                </div>
                            </div>
                            <div class="mb-2 text-warning">
                                &#9733;&#9733;&#9733;&#9733;&#9733;
                            </div>
                            <p class="fst-italic mb-0">“Đặt vé online nhanh chóng, giao diện trực quan, chọn được chỗ
                                ngồi yêu
                                thích — rất tiện lợi và hiện đại.”</p>
                        </div>
                    </div>

                    <!-- Card 3 -->
                    <div class="col-md-4 mb-4">
                        <div class="testimonial-card p-4 text-light">
                            <div class="d-flex align-items-center mb-3">
                                <img src="https://randomuser.me/api/portraits/men/65.jpg" alt="avatar"
                                    class="rounded-circle me-3" width="60" height="60">
                                <div>
                                    <h5 class="mb-0 fw-bold">Lê Văn C</h5>
                                    <small class="text-secondary">Chủ cửa hàng DEF</small>
                                </div>
                            </div>
                            <div class="mb-2 text-warning">
                                &#9733;&#9733;&#9733;&#9733;&#9733;
                            </div>
                            <p class="fst-italic mb-0">“Phim mới cập nhật liên tục, combo giá hợp lý, ghế ngồi êm ái —
                                OIZOIOI
                                Cinema là lựa chọn hàng đầu của mình.”</p>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>


</template>

<script>
import axios from 'axios'
export default {
    data() {
        return {
            list_phim: [],
            list_bai_viet: [],
            listDanhGia: [],
        }
    },
    mounted() {
        this.loadData();
        this.getBaiViet();

    },
    methods: {
        loadData() {
            axios
                .get('http://localhost:8080/api/phim/get-data')
                .then((res) => {
                    this.list_phim = res.data.data_phim; // lưu danh sách phim vào biến list_phim

                    // Sau đó lặp qua từng phim để gọi API điểm đánh giá
                    this.list_phim.forEach((phim, index) => {
                        axios
                            .get(`http://localhost:8080/api/chi-tiet-phim/get-danh-gia?idPhim=${phim.id}`)
                            .then((resDanhGia) => {
                                if (resDanhGia.data.status && resDanhGia.data.data.length > 0) {
                                    const diem = resDanhGia.data.data[0].diemTrungBinh || 0;

                                    // Gắn thêm thuộc tính 'diemTrungBinh' vào từng phim
                                    this.list_phim[index].diemTrungBinh = diem;
                                } else {
                                    this.list_phim[index].diemTrungBinh = 0;
                                }
                            })
                            .catch(() => {
                                this.list_phim[index].diemTrungBinh = 0;
                            });
                    });
                });

        },
        getBaiViet() {
            axios.get('http://localhost:8080/api/bai-viet/get-data')
                .then((res) => {
                    if (res.data.status) {
                        this.list_bai_viet = res.data.data;
                    }
                })
        },

    }
}
</script>

<style src="./index.css"></style>