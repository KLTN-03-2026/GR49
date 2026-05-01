<template>
    <div class="container mt-3 mb-5">
        <!-- TIÊU ĐỀ CHÍNH -->
        <div class="main-header">
            <!-- <span class="main-icon">🎁</span> -->
            <span class="main-title">Đổi điểm lấy quà ngay</span>
        </div>

        <!-- TIÊU ĐỀ PHỤ -->
        <div class="section-header">
            <span class="section-title">Quà tặng phổ biến</span>
            <router-link to="/client/danh-sach-doi-qua">
                <span class="section-link">Quà của tôi</span>
            </router-link>
        </div>

        <div class="row">
            <div class="col-4 mb-3" v-for="(item, index) in list_qua_voucher" :key="index">

                <div class="voucher-wrap">
                    <div class="voucher-ticket">

                        <div class="voucher-top">
                            <img :src="item.hinhAnh"
                                class="img-fluid promo-img" alt="">
                            <span class="ma-giam-label">Mã giảm giá</span>
                            <div class="voucher-amount">{{ item.soTienGiamGia }}</div>
                        </div>

                        <div class="voucher-divider">
                            <div class="circle-left"></div>
                            <div class="dashed-line"></div>
                            <div class="circle-right"></div>
                        </div>

                        <div class="voucher-bottom">
                            <p class="voucher-name">{{ item.tenVoucher }} <span @click="moModal(item)">
                                   <i class="fa-solid fa-circle-exclamation"></i>
                                </span></p>
                            <div class="voucher-footer">
                                <span class="points">⭐ {{ item.soDiem }}</span>
                                <button class="btn-doi-ngay" @click="doiVoucher(item)">Đổi ngay</button>
                            </div>
                        </div>

                    </div>

                    <svg class="wavy-bottom" viewBox="0 0 160 10" preserveAspectRatio="none">
                        <path
                            d="M0,0 Q5,10 10,5 Q15,0 20,5 Q25,10 30,5 Q35,0 40,5 Q45,10 50,5 Q55,0 60,5 Q65,10 70,5 Q75,0 80,5 Q85,10 90,5 Q95,0 100,5 Q105,10 110,5 Q115,0 120,5 Q125,10 130,5 Q135,0 140,5 Q145,10 150,5 Q155,0 160,5 L160,0 Z"
                            fill="white" />
                    </svg>
                </div>

            </div>
        </div>
        <!-- MODAL (giữ nguyên) -->
        <div class="modal-overlay" v-if="showModal" @click.self="dongModal">
            <div class="modal-box">
                <button class="modal-close" @click="dongModal">✕</button>

                <h4 class="modal-title">{{ selectedItem.tenVoucher }}</h4>

                <div class="modal-content">
                    <p><strong>Mô tả:</strong> {{ selectedItem.moTa || 'Không có mô tả' }}</p>
                    <p><strong>Số tiền giảm:</strong> {{ selectedItem.soTienGiamGia }}</p>
                    <p><strong>Tối đa:</strong> {{ selectedItem.soTienToiDa || 'Không giới hạn' }}</p>
                    <p><strong>Bắt đầu:</strong> {{ selectedItem.thoiGianBatDau || '---' }}</p>
                    <p><strong>Kết thúc:</strong> {{ selectedItem.thoiGianKetThuc || '---' }}</p>
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
            showModal: false,
            copied: false,
            selectedItem: {},
            list_qua_voucher: []   
        };
    },
     mounted() {
        this.getQuaVoucher();
    },
    methods: {
        moModal(item) {
            this.selectedItem = item;
            this.showModal = true;
        },
        dongModal() {
            this.showModal = false;
        },

         getQuaVoucher(id) {

            axios.get(`http://localhost:8080/api/get-qua-voucher`)
                .then((res) => {

                    this.list_qua_voucher = res.data.data;
                });
        },

        doiVoucher(item){
            axios.post('http://localhost:8080/api/client/qua-voucher/doi-qua?voucherId=' + item.id, null,{

                headers: {
                    Authorization: "Bearer " + localStorage.getItem('key_client')
                }
            })
                .then((res) => {
                    if (res.data.status) {
                        this.$toast.success(res.data.message);
                    } else {
                        this.$toast.error(res.data.message);
                    } 
                });
        }
    }
};
</script>

<style scoped>
/* ===== TIÊU ĐỀ CHÍNH ===== */
.main-header {
    background: linear-gradient(135deg, #e8000d, #ff6b35);
    border-radius: 16px;
    padding: 14px 16px;
    margin-bottom: 16px;
    display: flex;
    align-items: center;
    gap: 8px;
}

.main-icon {
    font-size: 22px;
}

.main-title {
    font-size: 18px;
    font-weight: 700;
    color: white;
    letter-spacing: 0.3px;
}

/* ===== TIÊU ĐỀ PHỤ ===== */
.section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 12px;
}

.section-title {
    font-size: 15px;
    font-weight: 700;
    color: #222;
}

.section-link {
    font-size: 13px;
    color: #ff4444;
    font-weight: 500;
    cursor: pointer;
}

.section-link:hover {
    text-decoration: underline;
}

/* ===== CARD ===== */
.voucher-wrap {
    filter: drop-shadow(0 3px 10px rgba(0, 0, 0, 0.15));
}

.voucher-ticket {
    background: white;
    border-radius: 12px 12px 0 0;
    position: relative;
}


.voucher-top {
    border-radius: 12px 12px 0 0;
    padding: 14px 10px 16px;
    text-align: center;
    position: relative;
    min-height: 90px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}

.promo-img {
    width: 100%;
    height: 100%;
    object-fit: contain;
    background: #000;
    /* cho đỡ trống */
}

.ma-giam-label {
    position: absolute;
    top: 8px;
    left: 50%;
    transform: translateX(-50%);
    background: rgba(255, 255, 255, 0.25);
    color: white;
    font-size: 9px;
    padding: 2px 8px;
    border-radius: 3px;
    white-space: nowrap;
}

.voucher-amount {
    font-size: 30px;
    font-weight: 900;
    color: white;
    letter-spacing: 1px;
    margin-top: 8px;
}

.voucher-divider {
    position: relative;
    height: 14px;
}

.circle-left {
    width: 14px;
    height: 14px;
    border-radius: 50%;
    background: #f5f5f5;
    position: absolute;
    left: -7px;
    z-index: 2;
}

.circle-right {
    width: 14px;
    height: 14px;
    border-radius: 50%;
    background: #f5f5f5;
    position: absolute;
    right: -7px;
    z-index: 2;
}

.dashed-line {
    position: absolute;
    top: 50%;
    left: 7px;
    right: 7px;
    transform: translateY(-50%);
    border-top: 1.5px dashed #e0e0e0;
}

.voucher-bottom {
    background: white;
    padding: 8px 10px 6px;
}

.voucher-name {
    font-size: 11px;
    font-weight: 500;
    color: #333;
    text-align: center;
    margin-bottom: 8px;
    line-height: 1.3;
}

.voucher-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.points {
    font-size: 11px;
    font-weight: 700;
    color: #ff9800;
}

.btn-doi-ngay {
    background: #ff4444;
    color: white;
    font-size: 10px;
    font-weight: 600;
    padding: 4px 10px;
    border-radius: 20px;
    border: none;
    cursor: pointer;
    white-space: nowrap;
}

.btn-doi-ngay:active {
    opacity: 0.85;
}

.wavy-bottom {
    display: block;
    width: 100%;
    height: 10px;
    overflow: hidden;
}

/* MODAL*/
.modal-overlay {
    position: fixed;
    inset: 0;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 999;
    padding: 20px;
}

.modal-box {
    background: white;
    border-radius: 20px;
    padding: 24px 20px 20px;
    width: 100%;
    max-width: 340px;
    text-align: center;
    position: relative;
}

.modal-close {
    position: absolute;
    top: 12px;
    right: 14px;
    background: #f0f0f0;
    border: none;
    border-radius: 50%;
    width: 28px;
    height: 28px;
    font-size: 12px;
    cursor: pointer;
    color: #555;
}

.modal-label {
    font-size: 12px;
    color: #999;
}

</style>