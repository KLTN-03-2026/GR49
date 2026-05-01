<template>

  <!-- Nút bật/tắt chatbot -->
  <div class="chatbot-container">
    <button class="chatbot-toggle" @click="toggleChat">
      <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
        stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
        class="lucide lucide-bot w-7 h-7" aria-hidden="true">
        <path d="M12 8V4H8"></path>
        <rect width="16" height="12" x="4" y="8" rx="2"></rect>
        <path d="M2 14h2"></path>
        <path d="M20 14h2">
        </path>
        <path d="M15 13v2"></path>
        <path d="M9 13v2"></path>
      </svg>
    </button>

    <!-- Khung popup chatbot -->
    <div class="chatbot-popup" v-show="isOpen">
      <div class="chatbot-header">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
          stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
          class="lucide lucide-bot w-7 h-7" aria-hidden="true">
          <path d="M12 8V4H8"></path>
          <rect width="16" height="12" x="4" y="8" rx="2"></rect>
          <path d="M2 14h2"></path>
          <path d="M20 14h2">
          </path>
          <path d="M15 13v2"></path>
          <path d="M9 13v2"></path>
        </svg> Trợp lý PoP AI
        <button class="close-btn" @click="toggleChat">×</button>
      </div>


      <!-- Thân chat -->
      <div class="chatbot-body" ref="chatBody">

        <!-- Tin nhắn chào ban đầu -->
        <div class="message bot" v-if="messages.length === 0">
          <div class="avatar">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
              stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
              class="lucide lucide-bot w-7 h-7" aria-hidden="true">
              <path d="M12 8V4H8"></path>
              <rect width="16" height="12" x="4" y="8" rx="2"></rect>
              <path d="M2 14h2"></path>
              <path d="M20 14h2">
              </path>
              <path d="M15 13v2"></path>
              <path d="M9 13v2"></path>
            </svg>
          </div>
          <!-- Nội dung chào -->
          <div class="text">
            PoP AI Xin chào 👋
            <p>Tôi có thể giúp gì cho bạn hôm nay?</p>
          </div>
        </div>

        <!-- Vòng lặp hiển thị tin nhắn -->
        <div v-for="(msg, index) in messages" :key="index" :class="['message', msg.sender]">

          <!-- Nếu là bot thì hiện avatar -->
          <div v-if="msg.sender === 'bot'" class="avatar">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
              stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
              class="lucide lucide-bot w-7 h-7" aria-hidden="true">
              <path d="M12 8V4H8"></path>
              <rect width="16" height="12" x="4" y="8" rx="2"></rect>
              <path d="M2 14h2"></path>
              <path d="M20 14h2">
              </path>
              <path d="M15 13v2"></path>
              <path d="M9 13v2"></path>
            </svg>
          </div>

          <!-- Nội dung tin nhắn -->
          <div class="text">
            <span v-if="msg.loading">Đang trả lời...</span>
            <!-- Nếu là button -->
            <!-- Tin nhắn bot -->
            <span v-if="!msg.options">{{ msg.text }}</span>

            <!-- Nếu có option thì hiển thị nhiều nút trong cùng bubble -->
            <div v-if="msg.options" class="bot-options">
              <span>{{ msg.text }}</span>

              <!-- Suất chiếu dạng grid giờ -->
              <div v-if="msg.component === 'time_grid'" class="time-grid">
                <button v-for="(opt, i) in msg.options" :key="i" class="time-btn" :disabled="opt.disabled"
                  @click="!opt.disabled && opt.action()">
                  <span class="time-hour">{{ opt.label }}</span>
                  <span class="time-date">{{ opt.ngay }}</span>
                </button>
              </div>

              <!-- Phim / options thường dạng list -->
              <div v-else class="options-grid">
                <button v-for="(opt, i) in msg.options" :key="i" class="chat-option-btn"
                  :class="{ 'has-img': opt.hinhAnh }" :disabled="opt.disabled" @click="!opt.disabled && opt.action()">

                  <!-- ảnh nếu có -->
                  <img v-if="opt.hinhAnh" :src="opt.hinhAnh"
                    style="width:40px;height:56px;object-fit:cover;border-radius:5px;flex-shrink:0" />
                  <!-- tên phim -->
                  <span>{{ opt.label }}</span>
                </button>
              </div>
            </div>

            <!-- Nếu có seatMatrix thì hiển thị sơ đồ ghế -->
            <div v-if="msg.seatMatrix" class="seat-matrix">
              <!-- Thêm màn hình -->
              <div class="cinema-screen">
                <div class="screen-bar"></div>
                <div class="screen-label">MÀN HÌNH</div>
              </div>
              <div v-for="(row, rIndex) in msg.seatMatrix" :key="rIndex" class="seat-row">

                <span class="seat-row-label">{{ row.row }}</span>

                <button v-for="(seat, sIndex) in row.seats" :key="sIndex" class="seat-btn"
                  :class="{ disabled: seat.disabled }" :disabled="seat.disabled"
                  @click="!seat.disabled && chonGhe(seat.value, seat.label)">
                  {{ seat.label }}
                </button>

              </div>
            </div>
            <!-- Tin nhắn bình thường
            <span v-else>{{ msg.text }}</span> -->
          </div>
        </div>
      </div>

      <!-- Gợi ý FAQ -->
      <div class="chatbot-faq" v-if="showFaq">
        <!-- <span class="faq-title">💡 Gợi ý câu hỏi:</span> -->
        <div class="faq-items">
          <button v-for="(faq, idx) in faqList" :key="idx" class="faq-btn" @click="selectFaq(faq)">
            {{ faq }}
          </button>
        </div>
      </div>

      <!-- Footer nhập tin -->
      <div class="chatbot-footer">
        <input type="text" placeholder="Nhập tin nhắn..." v-model="input" @keyup.enter="sendMessage" />
        <button class="send-btn" :disabled="!input.trim()" @click="sendMessage">
          <!-- <i class="fa-solid fa-paper-plane"></i> -->
          <!-- Icon máy bay (Lucide Send) -->
          <svg xmlns="http://www.w3.org/2000/svg" width="15" height="20" viewBox="0 0 24 24" fill="none"
            stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
            class="lucide lucide-send">
            <path d="M14.536 21.686a.5.5 0 0 0 .937-.024l6.5-19a.496.496 0 0 0-.635-.635l-19 
            6.5a.5.5 0 0 0-.024.937l7.93 3.18a2 2 0 0 1 1.112 1.11z" />
            <path d="m21.854 2.147-10.94 10.939" />
          </svg>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      isOpen: false, // trạng thái mở/đóng chatbot
      input: "",    // nội dung người dùng nhập
      messages: [], // danh sách tin nhắn
      showFaq: true,
      faqList: [
        "Làm thế nào để đặt vé?",
        "Giá vé bao nhiêu?",
        "Xem lịch chiếu phim nào đang có?",
        "Có khuyến mãi nào không?",
        "Có phim nào hot?"
      ],
      selectedSeats: []   // <- danh sách ghế đã chọn
    };
  },
  methods: {

    //===== Gọi API lấy danh sách phim =====
    async fetchMovies() {
      const res = await fetch("http://localhost:8080/chat/flow/phim");
      const data = await res.json();

      // Nhét action vào từng option
      const options = data.options.map(opt => ({
        label: opt.label,
        value: opt.value,
        hinhAnh: opt.hinhAnh,
        action: () => {
          // ✅ hiện tin nhắn user trước
          this.messages.push({ sender: "user", text: opt.label });
          this.fetchShowTimes(opt.value);
        }
      }));

      // Push 1 bubble bot chứa list buttons
      this.messages.push({
        sender: "bot",
        text: data.message,
        options: options
      });

      await this.$nextTick();
      this.scrollToBottom();
    },

    //==== Xử lý khi chọn phim → gọi API suất chiếu ======
    async fetchShowTimes(idPhim) {
      const res = await fetch(`http://localhost:8080/chat/flow/suatchieu/${idPhim}`);
      const data = await res.json();

      const options = data.options.map(opt => ({
        label: opt.label,
        ngay: opt.ngay,
        value: opt.value,
        action: () => {
          //  hiện tin nhắn user trước
          this.messages.push({ sender: "user", text: opt.label });
          this.fetchSeats(opt.value);
        }
      }));

      this.messages.push({
        sender: "bot",
        text: data.message,
        component: data.component,
        options: options
      });

      await this.$nextTick();
      this.scrollToBottom();
    },

    //===== Khi chọn suất chiếu → gọi API ghế  ======
    async fetchSeats(idSuatChieu) {
      const token = localStorage.getItem("key_client");

      try {
        const res = await fetch(
          `http://localhost:8080/chat/flow/ghe/${idSuatChieu}`,
          { headers: { Authorization: token ? `Bearer ${token}` : "" } }
        );

        // ❌ Server báo lỗi
        if (!res.ok) {
          this.messages.push({ sender: "bot", text: "⚠️ Bạn cần đăng nhập để chọn ghế!" });
          return;
        }

        // ✔ Thành công
        const data = await res.json();
        this.handleBotReply(data);

      } catch (err) {
        // ❌ Không kết nối được server
        this.messages.push({ sender: "bot", text: "⚠️ Không thể kết nối đến server!" });
      }
    },

    //===== Xử lý luồng ghế ngồi (nếu data trả về là seat matrix) =====
    handleSeatFlow(data) {
      // Không phải UI seat matrix → bỏ qua
      if (data.type !== "ui" || data.component !== "seat_matrix") {
        return false;
      }

      // Đẩy tin nhắn dạng sơ đồ ghế
      this.messages.push({
        sender: "bot",
        text: data.message,
        seatMatrix: data.matrix
      });

      this.$nextTick(() => this.scrollToBottom());
      return true;
    },

    //==== Xử lý phản hồi từ bot (có thể là text, options hoặc seat matrix) =====
    handleBotReply(data) {

      // Xử lý UI ghế → return để không chạy tiếp
      if (this.handleSeatFlow(data)) return;

      // Tin nhắn text hoặc có options
      const msg = {
        sender: "bot",
        text: data.message
      };

      // if (data.options) {
      //   msg.options = data.options;
      // }
      // === Nếu có options từ backend ===
      if (data.options) {
        msg.options = data.options.map(opt => ({
          ...opt,
          action: () => this.handleBotOption(opt)
        }));
      }

      this.messages.push(msg);
      this.$nextTick(() => this.scrollToBottom());
    },

    // ==== hàm chọn ghế ====
    chonGhe(idGhe, tenGhe) {

      // Thêm ghế vào danh sách
      if (!this.selectedSeats.includes(idGhe)) {
        this.selectedSeats.push(idGhe);
      }

      // Hiển thị lại tin nhắn người dùng
      this.messages.push({
        sender: "user",
        text: "Chọn ghế: " + tenGhe
      });

      // Gửi nút xác nhận
      this.messages.push({
        sender: "bot",
        text: "Bạn muốn đặt các ghế này?",
        options: [
          {
            label: "✅ Xác nhận đặt vé",
            value: "confirm_booking",
            action: () => this.confirmBooking()
          },
          {
            label: "❌ Huỷ",
            value: "cancel_booking",
            action: () => this.cancelBooking()
          }
        ]
      });

      this.$nextTick(() => this.scrollToBottom());
    },

    // ==== hàm xác nhận đặt vé (gọi API đặt vé với danh sách ghế đã chọn) ====
    async confirmBooking() {

      if (this.selectedSeats.length === 0) {
        this.messages.push({
          sender: "bot",
          text: "⚠️ Bạn chưa chọn ghế nào!"
        });
        return;
      }

      const token = localStorage.getItem("key_client");

      try {
        const res = await fetch("http://localhost:8080/chat/flow/dat-ve", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Authorization": token ? `Bearer ${token}` : ""
          },
          body: JSON.stringify({
            listVeIds: this.selectedSeats
          })
        });

        const text = await res.text();
        const lower = text.toLowerCase();

        if (res.status === 401 || res.status === 403 ||
          lower.includes("invalid") || lower.includes("token")) {

          this.messages.push({
            sender: "bot",
            text: "⚠️ Phiên đăng nhập hết hạn — vui lòng đăng nhập lại!"
          });
          return;
        }

        const data = JSON.parse(text);
        this.handleBotReply(data);

      } catch (e) {
        this.messages.push({
          sender: "bot",
          text: "⚠️ Không thể kết nối server!"
        });
      }

      this.selectedSeats = []; // reset sau khi đặt
    },


    //===== Hàm xủ lý thanh toán (khi click vào nút thanh toán trong option) =====
    handleBotOption(opt) {
      // if (opt.value === "pay") {
      //   this.handlePayment(opt);
      // }
      if (!opt || !opt.value) return;

      const map = {
        pay_vnpay: "vnpay",
        pay_momo: "momo",
        pay_payos: "payos"
      };

      const type = map[opt.value];

      if (!type) {
        this.messages.push({
          sender: "bot",
          text: "⚠️ Không xác định được phương thức thanh toán"
        });
        return;
      }

      this.thanhToan(type, opt);

    },

    async thanhToan(type, opt) {
      const token = localStorage.getItem("key_client");

      const urlMap = {
        vnpay: "/api/client/payment-methods/vnpay/create",
        momo: "/api/client/payment-methods/momo/create",
        payos: "/api/client/payment-methods/payos/create"
      };

      const body = {
        maDonHang: opt.maDonHang,
        tienThucNhan: opt.tienThucNhan
      };

      try {
        this.loading = true;

        const res = await axios.post(
          `http://localhost:8080${urlMap[type]}`,
          body,
          {
            headers: {
              Authorization: token ? `Bearer ${token}` : ""
            }
          }
        );

        // 👇 xử lý cả 2 loại response
        const paymentUrl =
          res.data.data.paymentUrl || // VNPay
          res.data.data.payUrl ||       // MoMo
          res.data.data.checkoutUrl;    //payOS

        if (!paymentUrl) {
          this.messages.push({
            sender: "bot",
            text: res.data.message || "❌ Không tạo được link thanh toán"
          });
          return;
        }

        this.messages.push({
          sender: "bot",
          text: "🔄 Đang chuyển đến trang thanh toán..."
        });

        setTimeout(() => {
          window.location.href = paymentUrl;
        }, 500);

      } catch (e) {
        console.error(e);

        this.messages.push({
          sender: "bot",
          text: e?.response?.data?.message || "❌ Thanh toán thất bại"
        });

      } finally {
        this.loading = false;
      }
    },
    
    //===== Khi click vào FAQ → tự động điền input và gửi tin nhắn =====
    selectFaq(faq) {
      // Ẩn gợi ý
      this.showFaq = false;

      // Cập nhật input với câu hỏi đã chọn
      this.input = faq;

      this.sendMessage(); // gửi tin nhắn ngay khi click FAQ
    },



    // -----Mở hoặc đóng chatbot-------
    toggleChat() {
      this.isOpen = !this.isOpen;
      document.body.classList.toggle("show-chatbot", this.isOpen);
      // Khi mở, focus vào ô input
      if (this.isOpen) {
        this.$nextTick(() => this.$refs.input?.focus());
      }
    },

    //===== Hàm Gửi tin nhắn =====
    async sendMessage() {
      if (!this.input.trim()) return; // không gửi khi input trống

      const userMsg = this.input;
      // Thêm tin nhắn người dùng vào danh sách
      this.messages.push({ sender: "user", text: userMsg });
      this.input = "";


      // Hiển thị tin nhắn bot đang load "..."
      this.messages.push({ sender: "bot", text: "", loading: true });
      await this.$nextTick();
      this.scrollToBottom();


      // Nếu user hỏi đặt vé → gọi API phim
      if (
        userMsg.toLowerCase().includes("đặt vé") ||
        userMsg.toLowerCase().includes("mua vé") ||
        userMsg.toLowerCase().includes("xem phim")
      ) {
        // this.messages.push({ sender: "bot", text: "Đang lấy danh sách phim..." });
        this.fetchMovies();

        // Dừng xử lý, không gọi API chat stream
        return;
      }

      try {
        const token = localStorage.getItem("key_client");
        // Cấu hình headers động
        const headers = {
          'Content-Type': 'application/json'
        };

        if (token) {
          // Nếu có token → thêm Authorization
          headers['Authorization'] = `Bearer ${token}`;
        }
        const response = await fetch(`http://localhost:8080/chat/stream?message=${encodeURIComponent(userMsg)}`, {
          method: 'POST',
          headers: headers
        });
        const reader = response.body.getReader();
        const decoder = new TextDecoder("utf-8");
        let botMsg = "";
        let lastUpdate = Date.now();

        // Đọc dữ liệu dạng stream
        // const readChunk = async () => {
        //   const { value, done } = await reader.read();
        //   if (done) {
        //     // Kết thúc stream, bỏ loading
        //     this.messages[this.messages.length - 1].loading = false;
        //     return;
        //   }

        //   botMsg += decoder.decode(value, { stream: true });
        //   this.messages[this.messages.length - 1].text = botMsg;
        //   await this.$nextTick();
        //   this.scrollToBottom();

        //   readChunk(); // đọc chunk tiếp
        // };

        // readChunk();


        // while (true) {
        //   const { value, done } = await reader.read();
        //   if (done) break;

        //   botMsg += decoder.decode(value, { stream: true });

        //   // Cập nhật mỗi 200ms hoặc khi stream xong
        //   if (Date.now() - lastUpdate > 200) {
        //     this.messages[this.messages.length - 1].text = botMsg;
        //     await this.$nextTick();
        //     this.scrollToBottom();
        //     lastUpdate = Date.now();
        //   }
        // }
        while (true) {
          const { value, done } = await reader.read();
          if (done) break;

          const chunk = decoder.decode(value, { stream: true });
          botMsg += chunk;

          // ❗ thử parse JSON mỗi lần update
          try {
            const json = JSON.parse(botMsg);

            // 👉 nếu là UI ghế
            if (json.type === "ui" && json.component === "seat_matrix") {

              this.messages.pop(); // bỏ loading

              this.messages.push({
                sender: "bot",
                text: json.message,
                seatMatrix: json.matrix
              });

              return; // ❌ dừng stream luôn
            }
          } catch (e) {
            // không phải JSON thì bỏ qua
          }

          if (Date.now() - lastUpdate > 200) {
            this.messages[this.messages.length - 1].text = botMsg;
            await this.$nextTick();
            this.scrollToBottom();
            lastUpdate = Date.now();
          }
        }


        // Update cuối cùng và remove loading
        this.messages[this.messages.length - 1].text = botMsg;
        this.messages[this.messages.length - 1].loading = false;
        await this.$nextTick();
        this.scrollToBottom();

        // Giới hạn message max 50
        if (this.messages.length > 50) this.messages.splice(0, this.messages.length - 50);


      } catch (err) {
        console.error(err);
        this.messages[this.messages.length - 1] = {
          sender: "bot", text: "Đã có lỗi xảy ra 😢"
        };
      }
    },



    // Cuộn xuống cuối vùng chat
    scrollToBottom() {
      const container = this.$refs.chatBody;
      // setTimeout để tránh giật khi stream liên tục
      setTimeout(() => {
        container.scrollTop = container.scrollHeight;
      }, 50);
    }
  }
};
</script>

<style scoped>
/* ==== CSS cho chatbot */
.chatbot-container {
  position: fixed;
  bottom: 20px;
  right: 25px;
  z-index: 9999;
}

/* Nút mở */
.chatbot-toggle {
  width: 55px;
  height: 55px;
  border-radius: 50%;
  border: none;
  /* background: #0e0e62; */
  /* background: radial-gradient(circle at top, #021679, #000); */
  background: radial-gradient(circle at top, #02115a, #000);
  color: white;
  font-size: 24px;
  box-shadow: 0 3px 8px rgba(0, 0, 0, 0.25);
  cursor: pointer;
  transition: 0.3s;
}

.chatbot-toggle:hover {
  transform: scale(1.1);
  /* background: #005fe0; */
  box-shadow: 0 12px 30px rgba(0, 80, 255, 0.3);
  background: rgba(15, 35, 90, 0.95);
}

/* Popup chat */
.chatbot-popup {
  position: fixed;
  bottom: 85px;
  right: 25px;
  width: 430px;
  height: 550px;
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 5px 25px rgba(0, 0, 0, 0.15);
  display: flex;
  flex-direction: column;
  overflow: hidden;
  animation: slideUp 0.3s ease forwards;
}

@keyframes slideUp {
  from {
    transform: translateY(20px);
    opacity: 0;
  }

  to {
    transform: translateY(0);
    opacity: 1;
  }
}

.chatbot-header {
  /* background: #0e0e62; */
  background: radial-gradient(circle at top, #02115a, #000);
  color: #fff;
  padding: 12px 15px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.chatbot-header i {
  margin-right: 6px;
}

.chatbot-header h4 {
  margin: 0;
  font-size: 16px;
}

.chatbot-header .close-btn {
  background: none;
  border: none;
  color: white;
  font-size: 20px;
  cursor: pointer;
}

/* Nội dung */
.chatbot-body {
  flex: 1;
  overflow-y: auto;
  padding: 10px;
  background: #f6f7fb;
}

.message {
  display: flex;
  margin-bottom: 10px;
}

.message.user {
  justify-content: flex-end;
}

.message .text {
  max-width: 70%;
  padding: 8px 12px;
  border-radius: 14px;
  line-height: 1.4;
  font-size: 14px;
}

.message.user .text {
  background: #0e0e62;
  color: white;
  border-bottom-right-radius: 4px;
}

.message.bot .text {
  background: #eef3ff;
  color: #333;
  border-bottom-left-radius: 4px;
  white-space: pre-line;
  /* cho phép xuống dòng theo \n */
}

.avatar {
  width: 32px;
  height: 32px;
  /* background: #0e0e62; */
  background: radial-gradient(circle at top, #02115a, #000);
  color: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 8px;
  font-size: 16px;
}

/* Footer */
.chatbot-footer {
  display: flex;
  padding: 10px;
  border-top: 1px solid #ddd;
  background: #fff;
}

.chatbot-footer input {
  flex: 1;
  padding: 8px 10px;
  border-radius: 20px;
  border: 1px solid #ccc;
  outline: none;
}

.send-btn {
  border: none;
  /* background: #0e0e62; */
  background: radial-gradient(circle at top, #02115a, #000);
  color: white;
  border-radius: 50%;
  width: 38px;
  height: 38px;
  margin-left: 8px;
  cursor: pointer;
  transition: 0.2s;
}

/* Khi nút bị disable */
.send-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.send-btn:hover {
  outline: none;
  /* tắt outline khi nhấn */
  box-shadow: none;
  /* tắt shadow mặc định khi active */
  background: radial-gradient(circle at top, #02115a, #000);
}


/* =====CSS cho FAQ==== */
.chatbot-faq {
  padding: 8px 10px;
  border-top: 1px solid #ddd;
  margin-bottom: 4px;
}

.faq-title {
  font-weight: bold;
  display: block;
  margin-bottom: 4px;
  font-size: 0.9rem;
}

.faq-items {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
}

.faq-btn {
  background: #eef3ff;
  border: none;
  border-radius: 14px;
  padding: 4px 10px;
  font-size: 0.85rem;
  cursor: pointer;
  color: #0e0e62;
  transition: 0.2s;
}

.faq-btn:hover {
  background: #d0e0ff;
}

/* ===== BUTTON CHỌN PHIM / SUẤT CHIẾU TRONG CHAT ===== */
.chat-option-btn {
  background: #eef3ff;
  color: #0e0e62;
  border: none;
  border-radius: 12px;
  padding: 6px 12px;
  cursor: pointer;
  margin-top: 4px;
  font-size: 14px;
  transition: 0.2s;
}

.chat-option-btn:hover {
  background: #d6e2ff;
  box-shadow: 0 2px 6px rgba(0, 50, 255, 0.2);
}

/* Button có hình ảnh */
.chat-option-btn.has-img {
  display: flex;
  align-items: center;
  gap: 10px;
  text-align: left;
  padding: 6px 10px;
}

.bot-options {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.options-grid {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.time-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 6px;
  margin-top: 6px;
}

.time-btn {
  background: #fff;
  border: 1px solid #3f51b5;
  border-radius: 10px;
  padding: 8px 4px;
  cursor: pointer;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 3px;
  transition: 0.15s;
}

.time-btn:hover {
  background: #eef3ff;
  box-shadow: 0 2px 6px rgba(0, 50, 255, 0.15);
}

.time-btn:disabled {
  background: #ddd;
  border-color: #ccc;
  color: #999;
  cursor: not-allowed;
}

.time-hour {
  font-size: 14px;
  font-weight: 600;
  color: #02115a;
}

.time-date {
  font-size: 10px;
  color: #666;
  font-weight: 400;
}


/* ===== CSS cho sơ đồ ghế ===== */
/* Bubble chứa sơ đồ ghế — nới rộng ra */
.message.bot .text:has(.seat-matrix) {
  max-width: 95%;
  padding: 8px;
}

/* Container tổng */
.seat-matrix {
  margin-top: 6px;
}

/* Mỗi hàng ghế */
.seat-row {
  display: grid;
  grid-template-columns: 20px repeat(10, 1fr);
  /* cột label + 10 ghế */
  align-items: center;
  gap: 3px;
  margin-bottom: 3px;
}

/* Label hàng (A, B, C...) */
.seat-row-label {
  font-size: 11px;
  font-weight: 500;
  color: #666;
  text-align: center;
}

/* Nút ghế */
.seat-btn {
  margin: 0;
  padding: 0;
  height: 28px;
  width: 100%;
  font-size: 11px;
  border-radius: 5px;
  border: 1px solid #3f51b5;
  background: #fff;
  cursor: pointer;
  transition: background 0.15s;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.seat-btn:hover:not(:disabled) {
  background: #e8eaff;
}

.seat-btn.disabled {
  background: #ddd;
  color: #999;
  border-color: #ccc;
  cursor: not-allowed;
}

.seat-btn:hover {
  background: #e8eaff;
}

.seat-btn.disabled {
  background: #ddd !important;
  color: #666;
  border-color: #bbb;
  cursor: not-allowed;
}


/* ==== */
.cinema-screen {
  text-align: center;
  margin: 4px 0 12px;
}

.cinema-screen .screen-bar {
  display: inline-block;
  width: 75%;
  height: 6px;
  background: linear-gradient(to bottom, #b0bec5, #eceff1);
  border-radius: 3px 3px 0 0;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

.cinema-screen .screen-label {
  font-size: 10px;
  color: #aaa;
  margin-top: 3px;
  letter-spacing: 2px;
}
</style>
