<template>
    <div class="scan-container">
        <h2>Quét mã QR vé 🎟️</h2>

        <!-- Camera -->
        <div id="reader"></div>

        <!-- Kết quả -->
        <div v-if="message" :class="['result-box', isSuccess ? 'success' : 'error']">
            <p>{{ message }}</p>

            <!-- 🎯 Hiển thị vé -->
            <div v-if="isSuccess && data">
                <p><b>Mã đơn:</b> {{ data.donHang?.maDonHang }}</p>
                <p><b>Tổng tiền:</b> {{ data.donHang?.tongTien }}</p>

                <p><b>Ghế:</b>
                    <span v-for="ve in data.dsVe" :key="ve.id">
                        {{ ve.tenGhe }}
                    </span>
                </p>

                <p><b>Phim:</b> {{ data.dsVe?.[0]?.tenPhim }}</p>
                <p><b>Ngày chiếu:</b> {{ formatNgayChieu(data.dsVe?.[0]?.ngayChieu) }}</p>

                <p><b>Giờ chiếu:</b>
                    {{ formatTime(data.dsVe?.[0]?.thoiGianBatDau) }}
                    -
                    {{ formatTime(data.dsVe?.[0]?.thoiGianKetThuc) }}
                </p>
            </div>
        </div>


        <button @click="startScan" :disabled="isScanning">
            {{ isScanning ? "Đang quét..." : "Quét lại" }}
        </button>
    </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import { Html5Qrcode } from "html5-qrcode";
import axios from "axios";

const message = ref("");
const isSuccess = ref(false);
const isScanning = ref(false);
const data = ref(null);
let scanner = null;
const token = localStorage.getItem("key_admin");

// 🔊 Âm thanh
const successSound = new Audio("/sounds/succes1.wav");
const errorSound = new Audio("/sounds/error.wav");

// 🎯 Định dạng ngày chiếu
const formatNgayChieu = (date) => {
    if (!date) return "";
    const [year, month, day] = date.split("-");
    return `${day}/${month}/${year}`;
};

// 🎯 Định dạng giờ chiếu
const formatTime = (time) => {
    if (!time) return "";
    return time.substring(0, 5); // lấy HH:mm
};

// 🎯 API check-in
const checkInApi = async (qr) => {
    try {
        const res = await axios.get("http://localhost:8080/api/admin/check-in", {
            params: { qr },
            headers: {
                Authorization: `Bearer ${token}`
            }
        });

        isSuccess.value = res.data.status;
        message.value = res.data.message;
        data.value = res.data.data;

        // 🔊 sound
        if (res.data.status) {
            successSound.currentTime = 0;
            successSound.play();
        } else {
            errorSound.currentTime = 0;
            errorSound.play();
        }

    } catch (err) {
        isSuccess.value = false;
        message.value = err.response?.data?.message || "❌ Lỗi server";
        data.value = null;

        errorSound.currentTime = 0;
        errorSound.play();
    }

};


// 👉 Bắt đầu quét QR
const startScan = async () => {
    // Reset trạng thái
    message.value = "";
    isSuccess.value = false;

    // Dừng scanner cũ nếu đang chạy
    if (scanner) {
        try {
            await scanner.stop();
        } catch (_) { }
    }

    scanner = new Html5Qrcode("reader");
    isScanning.value = true;

    try {
        await scanner.start(
            { facingMode: "environment" }, // Camera sau
            {
                fps: 10,
                qrbox: { width: 250, height: 250 },
            },
            async (decodedText) => {
                // Dừng quét ngay khi đọc được mã
                await scanner.stop();
                isScanning.value = false;

                // Gọi API với mã QR vừa quét
                await checkInApi(decodedText);
            },
            () => { } // Bỏ qua lỗi frame không tìm thấy QR
        );
    } catch (err) {
        isScanning.value = false;
        message.value = "❌ Không thể mở camera: " + err;
    }
};

// 👉 Khi load trang
onMounted(() => {
    startScan();
});

// 👉 Khi rời trang
onBeforeUnmount(async () => {
    if (scanner) {
        try {
            await scanner.stop();
        } catch (_) { }
    }
});
</script>

<style scoped>
.scan-container {
    text-align: center;
    padding: 20px;
}

#reader {
    width: 500px;
    /* tăng từ 300 lên 500 */
    margin: 20px auto;
}

.result-box {
    margin: 12px auto;
    padding: 16px 24px;
    border-radius: 10px;
    max-width: 500px;

    font-size: 20px;        /* 🔥 tăng chữ */
    font-weight: 600;       /* 🔥 đậm hơn */
    text-align: center;     /* căn giữa */
}

.result-box.success {
    background: #d4edda;
    color: #155724;
    font-size: 22px;
}

.result-box.error {
    background: #f8d7da;
    color: #721c24;
    font-size: 22px;
}

button {
    margin-top: 10px;
    padding: 8px 16px;
    cursor: pointer;
}

button:disabled {
    opacity: 0.6;
    cursor: not-allowed;
}
</style>