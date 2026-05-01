<template>
    <div class="row">
        <div class="col">
            <div class="card bg-dark text-white">
                <img src="../../../assets/images/gallery/13.png" class="img-fluid" alt="..."
                    style="height: 120px; object-fit: cover;">
                <div class="card-img-overlay">
                    <h4 class="card-title text-white" style="margin-left: 610px; margin-top: 30px;">Danh Sách Bài Viết
                    </h4>
                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="row">

            <div class="col-lg-6">
                <div class="card">
                    <div class="row mt-4 mx-2">
                        <div class="col-lg-12" v-if="list_bai_viet.length > 0">
                            <div class="card mb-3">
                                <router-link :to="`/chi-tiet-bai-viet/${list_bai_viet[0].id}`">
                                    <img :src="list_bai_viet[0].hinhAnh" class="card-img-top" alt="">
                                </router-link>
                                <div class="card-body">
                                    <h5 class="card-title">{{ list_bai_viet[0].tieuDe }}</h5>
                                    <p class="card-text">{{ list_bai_viet[0].moTaNgan }}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <h5>Các Bài Viết Khác</h5>
                <hr>
                <div class="row">
                    <div class="col-lg-6 d-flex">
                        <div class="card flex-full">
                            <img src="https://www.galaxycine.vn/media/2025/2/27/1800x1200_1740651635343.jpg"
                                class="card-img-top" alt="...">
                            <div class="card-body">
                                <h6 class="card-title ">Phim Hay Tháng 03.2025: Bạch Tuyết pk Quỷ Nhập Tràng</h6>
                                <p class="card-text "><small>Some quick example text to build on the card title and make
                                        up the
                                        bulk of the card's content.</small></p>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6 d-flex">
                        <div class="card flex-full">
                            <img src="https://www.galaxycine.vn/media/2024/10/28/pht11-750_1730103049846.jpg"
                                class="card-img-top" alt="...">
                            <div class="card-body">
                                <h6 class="card-title ">Những Người Hùng Trở Lại</h6>
                                <p class="card-text "><small>Some quick example text to build on the card title and make
                                        up the
                                        bulk of the card's content.</small></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="card p-4">
                    <div class="row">
                        <template v-for="(value, index) in list_bai_viet.slice(1)" :key="index">
                            <div class="col-lg-12 d-flex justyfy-content-between">
                                <div class="card">
                                    <div class="row">
                                        <div class="col-lg-5">
                                            <router-link :to="`/chi-tiet-bai-viet/${value.id}`">
                                                <img :src="value.hinhAnh" class="img-fluid" alt=""
                                                    style="height: 150px;">
                                            </router-link>
                                        </div>
                                        <div class="col-lg-7">
                                            <div class="card-body">
                                                <h5 class="text-truncate">{{ value.tieuDe }}</h5>
                                                <p class="mb-0">{{ value.moTaNgan }}</p>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </template>
                        <hr>
                        <div class="col-lg-12 d-flex justyfy-content-between">
                            <div class="card">
                                <div class="row">
                                    <div class="col-lg-5">
                                        <img src="https://cdn.moveek.com/storage/media/cache/medium/6809f8356e715717366849.jpg"
                                            class="img-fluid" alt="" style="height: 150px;">
                                    </div>
                                    <div class="col-lg-7">
                                        <div class="card-body">
                                            <h5 class="text-truncate">Những review đầu tiên dành cho Thunderbolts: Biệt
                                                Đội Sấm Sét (2025)</h5>
                                            <p class="mb-0">Thunderbolts: Biệt Đội Sấm Sét là một cú hit đầy tiềm năng
                                                nhà Marvel.</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr>
                        <div class="col-lg-12 d-flex justyfy-content-between">
                            <div class="card">
                                <div class="row">
                                    <div class="col-lg-5">
                                        <img src="https://cdn.moveek.com/storage/media/cache/medium/681c2521bc7fc964269586.png"
                                            class="img-fluid" alt="" style="height: 150px;">
                                    </div>
                                    <div class="col-lg-7">
                                        <div class="card-body">
                                            <h5 class="text-truncate">Doraemon Movie 44 (2025) - Sẽ có phiên bản lồng
                                                tiếng của gia đình Pam Yêu Ơi</h5>
                                            <p class="mb-0">Xem lịch chiếu phim chiếu rạp Doraemon 2025: Nobita Và Cuộc
                                                Phiêu Lưu Vào Thế Giới Trong Tranh, sẽ có phiên bản lồng tiếng của gia
                                                đình Pam Yêu Ơi và combo Doraemon Movie 44 siêu hấp dẫn.</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr>
                        <div class="col-lg-12 d-flex justyfy-content-between">
                            <div class="card">
                                <div class="row">
                                    <div class="col-lg-5">
                                        <img src="https://cdn.moveek.com/storage/media/cache/medium/680f06392e121752384462.jpg"
                                            class="img-fluid" alt="" style="height: 150px;">
                                    </div>
                                    <div class="col-lg-7">
                                        <div class="card-body">
                                            <h5 class="text-truncate">Lật Mặt 8 - Phim của Lý Hải lập kỷ lục chưa từng
                                                có</h5>
                                            <p class="mb-0">131.000 vé xem phim Lật Mặt 8: Vòng Tay Nắng đã được bán ra
                                                ngay cả khi phim chưa chiếu. Bộ phim có gì mà hot như vậy?</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <hr>

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
            list_bai_viet: []
        }
    },
    mounted() {
        this.getBaiViet();
    },
    methods: {
        getBaiViet() {
            axios.get('http://localhost:8080/api/bai-viet/get-data')
                .then((res) => {
                    if (res.data.status) {
                        this.list_bai_viet = res.data.data;
                    }
                })
        },
    },
};
</script>
<style></style>