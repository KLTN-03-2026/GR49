<template>
    <div class="container mt-3 mb-5">

        <!-- HEADER -->
        <div class="main-header">
            <span class="main-icon">🎁</span>
            <span class="main-title">Quà của tôi</span>
        </div>

        <!-- LIST -->
        <div class="row">
            <div class="col-4 mb-3" v-for="(item, index) in danhSachQua" :key="index">
                <div class="qua-card">

                    <img :src="item.hinhAnh" class="qua-card-img" />

                    <div class="qua-card-body">
                        <p class="qua-card-title">{{ item.tenVoucher }}</p>

                        <p class="qua-card-date">
                            <span class="fw-bold">Ngày nhận:</span> {{ formatDate(item.ngayNhan) }}
                        </p>
                         <p class="qua-card-date">
                            <span class="fw-bold">Hiệu lực:</span> {{ formatDate(item.thoiGianBatDau) }} - {{ formatDate(item.thoiGianKetThuc) }}
                        </p>

                        <span class="qua-badge"
                            :class="item.tinhTrang === 0 ? 'badge-used' : 'badge-active'">
                            {{ item.tinhTrang === 1 ? 'Đã dùng' : 'Khả dụng' }}
                        </span>

                        <button class="btn-lay-ma" :class="{ disabled: item.tinhTrang === 1 }"
                            :disabled="item.tinhTrang === 1" @click="moModal(item)">
                            Lấy mã
                        </button>
                    </div>

                </div>
            </div>

            <div class="empty" v-if="danhSachQua.length === 0">
                <span class="empty-icon">🎁</span>
                <p>Bạn chưa có quà nào</p>
            </div>

        </div>

        <!-- MODAL (giữ nguyên) -->
        <div class="modal-overlay" v-if="showModal" @click.self="dongModal">
            <div class="modal-box">
                <button class="modal-close" @click="dongModal">✕</button>

                <img :src="selectedItem.hinhAnh" class="modal-img" alt="">

                <p class="modal-ten">{{ selectedItem.tenVoucher }}</p>
                <p class="modal-label">Mã giảm giá của bạn</p>

                <div class="modal-code-wrap">
                    <span class="modal-code">{{ selectedItem.maCode }}</span>
                    <button class="btn-copy" @click="copyMa(selectedItem.maCode)">
                        {{ copied ? '✓ Đã copy' : 'Sao chép' }}
                    </button>
                </div>

                <p class="modal-note">Dùng mã này khi thanh toán để được giảm giá</p>
            </div>
        </div>

    </div>
</template>

<script>
import axios from 'axios';

const token = localStorage.getItem("key_client");
export default {
    data() {
        return {
            showModal: false,
            copied: false,
            selectedItem: {},
            danhSachQua: []
        };
    },
     mounted() {
        this.getDanhSachQua();
    },
    methods: {
        formatDate(dateStr) {
            const d = new Date(dateStr);
            return `${d.getDate()}/${d.getMonth() + 1}/${d.getFullYear()}`;
        },
        moModal(item) {
            this.selectedItem = item;
            this.copied = false;
            this.showModal = true;
        },
        dongModal() {
            this.showModal = false;
        },
        async copyMa(ma) {
            try {
                await navigator.clipboard.writeText(ma);
                this.copied = true;
                setTimeout(() => { this.copied = false; }, 2000);
            } catch {
                this.copied = true;
            }
        },
        getDanhSachQua() {

			axios.get("http://localhost:8080/api/client/qua-voucher/get-lich-su-qua-voucher", {

				headers: {
					'Authorization': `Bearer ${token}`
				}
			})
				.then((res) => {
					this.danhSachQua = res.data.data;

				})
				.catch((err) => {
					console.error("Lỗi khi tải hồ sơ:", err);
				});
		},
    }
};
</script>

<style scoped>
/* HEADER */
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
}

/* GRID */
.qua-list {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 12px;
}

/* CARD */
.qua-card {
    background: white;
    border-radius: 16px;
    overflow: hidden;
    /* QUAN TRỌNG: chặn ảnh tràn */
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    display: flex;
    flex-direction: column;
}

.qua-card:hover {
    transform: translateY(-3px);
}

/* IMAGE */
.qua-card-img {
  width: 100%;
  height: 140px;   /* 👈 fix cứng chiều cao cho chắc */
  object-fit: cover;
  display: block;
}

/* BODY */
.qua-card-body {
    padding: 10px;
    display: flex;
    flex-direction: column;
    gap: 6px;
}

/* TITLE */
.qua-card-title {
    font-size: 13px;
    font-weight: 700;
    color: #222;
    min-height: 32px;
}

/* DATE */
.qua-card-date {
    font-size: 10px;
    color: #999;
}

/* BADGE */
.qua-badge {
    font-size: 9px;
    font-weight: 700;
    padding: 3px 8px;
    border-radius: 20px;
    width: fit-content;
}

.badge-active {
    background: #e8f5e9;
    color: #2e7d32;
}

.badge-used {
    background: #eee;
    color: #999;
}

/* BUTTON */
.btn-lay-ma {
    margin-top: auto;
    background: #ff4444;
    color: white;
    font-size: 12px;
    font-weight: 600;
    padding: 8px;
    border-radius: 10px;
    border: none;
    cursor: pointer;
    width: 100%;
}

.btn-lay-ma.disabled {
    background: #eee;
    color: #bbb;
    cursor: not-allowed;
}

/* EMPTY */
.empty {
    text-align: center;
    padding: 40px 0;
    color: #aaa;
}

.empty-icon {
    font-size: 40px;
    display: block;
    margin-bottom: 8px;
}

/* MODAL giữ nguyên */
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

.modal-img {
    width: 100%;
    height: 140px;
    object-fit: cover;
    border-radius: 12px;
    margin-bottom: 14px;
}

.modal-ten {
    font-size: 15px;
    font-weight: 700;
}

.modal-label {
    font-size: 12px;
    color: #999;
}

.modal-code-wrap {
    display: flex;
    justify-content: space-between;
    background: #fff5f5;
    border: 1.5px dashed #ff4444;
    border-radius: 10px;
    padding: 10px;
}

.modal-code {
    font-size: 18px;
    font-weight: 900;
    color: #ff4444;
}

.btn-copy {
    background: #ff4444;
    color: white;
    border-radius: 20px;
    border: none;
    padding: 5px 10px;
}

.modal-note {
    font-size: 11px;
    color: #aaa;
}
</style>