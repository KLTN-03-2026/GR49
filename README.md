
# 🎬 WEBSITE POPCINEMA – HỆ THỐNG QUẢN LÝ ĐẶT VÉ XEM PHIM TRỰC TUYẾN ỨNG DỤNG CHATBOT AI VÀ MÔ PHỎNG PHÒNG CHIẾU 360°

PoP Cinema là hệ thống đặt vé xem phim trực tuyến, cung cấp trải nghiệm hoàn chỉnh từ người dùng đến quản trị, bao gồm đặt vé, thanh toán online và quản lý hệ thống.
Hệ thống được xây dựng theo kiến trúc **RESTful API + SPA**, đảm bảo hiệu năng cao, dễ mở rộng và phù hợp với mô hình thực tế.

---

## 🎯 Mục Tiêu
- Xây dựng nền tảng đặt vé phim **nhanh chóng, tiện lợi, an toàn**  
- Hỗ trợ người dùng **tìm kiếm phim, chọn suất chiếu và đặt ghế dễ dàng**  
- Tích hợp **thanh toán online** (VNPAY, MoMo)  
- Ứng dụng **AI Chatbot** để tư vấn và hỗ trợ người dùng  
- Mô phỏng **phòng chiếu 360°** giúp chọn ghế trực quan  
- Cung cấp hệ thống **quản trị rạp phim đầy đủ**  
- Thiết kế hệ thống **ổn định, scalable, dễ maintain**

---

## 🚀 Tổng Quan

- 🔧 Backend: Spring Boot (RESTful API)  
- 🎨 Frontend: Vue.js (SPA)  
- 🔐 Xác thực: JWT + Spring Security  
- 💳 Thanh toán: VNPAY, MoMo 
- 🤖 Chatbot AI: OpenAI / Gemini  
- 🗄️ Cơ sở dữ liệu: MySQL  

---

## ⚙️ Công Nghệ Sử Dụng

| Thành phần        | Công nghệ / Công cụ | Phiên bản |
|:------------------|:--------------------:|----------:|
| Ngôn ngữ          | Java (OpenJDK)      | 21 LTS    |
| Backend           | Spring Boot         | 3.5.x     |
| Frontend          | Vue.js              | 3.x       |
| Build (FE)        | Vite                | 5.x       |
| Cơ sở dữ liệu     | MySQL               | 8.0.x     |
| Build (BE)        | Maven               | 3.9.x     |
| Thanh toán        | VNPAY, MoMO         | Latest    |
| AI                | OpenAI, Gemini      | Latest    |
| Quản lý mã nguồn  | Git / GitHub        | Latest    |
| Container         | Docker              | Latest    |

---

## 📚 Thư Viện Backend

| Nhóm chức năng | Thư viện | Mô tả |
|:--------------|:---------|:------|
| Bảo mật | Spring Security + JWT | Xác thực và phân quyền |
| ORM | Spring Data JPA (Hibernate) | Làm việc với cơ sở dữ liệu |
| Validation | Spring Validation | Kiểm tra dữ liệu đầu vào |
| API | OpenFeign | Gọi API giữa các service |
| Email | Spring Mail | Gửi email hệ thống |
| AI | Spring AI (Gemeni AI) | Tích hợp chatbot |
| Boilerplate | Lombok | Giảm code lặp |
| Build | Maven | Quản lý dependency |

---

## 🎨 Thư Viện Frontend

| Nhóm chức năng | Thư viện / Công nghệ | Mô tả |
|:--------------|:--------------------|:------|
| Framework | Vue.js | Xây dựng SPA |
| Build Tool | Vite | Build nhanh |
| UI | Bootstrap | Giao diện responsive |
| HTTP | Axios | Gọi API |
| Routing | Vue Router | Điều hướng |
| State | Pinia / Vuex | Quản lý trạng thái |
| Biểu đồ | Chart.js | Trực quan dữ liệu |
| Wrapper | vue-chartjs | Dùng Chart.js trong Vue |
| 3D | Three.js | Đồ họa 3D |
| 360° | Panolens | Hiển thị panorama |

---

## ✨ Chức Năng Chính

### 👤 Người dùng
- Đăng ký / Đăng nhập  
- Xem phim, lịch chiếu  
- Chọn ghế, đặt vé, tham quan phòng chiếu VR360°
- Thanh toán online  
- Quản lý lịch sử đơn hàng 
- Tích điểm đổi quà 
- Bình luận, đánh giá
- Chatbot hỗ trợ  

### 🛠️ Quản trị viên
- Dashboard 
- Quản lý phim, suất chiếu, phòng, ghế  
- Quản lý vé 
- Quản lý tài khoản khách hàng và nhân viên  
- Quản lý voucher, dịch vụ, bài viết 
- Phân quyền hệ thống
- Quản lý đơn hàng và thống kê  

---

## 📁 Cấu Trúc Dự Án

### 🔧 Backend
```
├── src/                                # Thư mục chính của mã nguồn
│   ├── main/
│   │   ├── java/com/quoc/Movie_Ticket_Booking/
│   │   │   ├── client/                 # API dành cho người dùng (Client)
│   │   │   ├── config/                 # Cấu hình bảo mật, CORS, JWT, Email,VNPAY,MoMo...
│   │   │   ├── controller/             # API endpoints xử lý request/response
│   │   │   │   ├── admin/              # Controller cho Admin (quản trị)
│   │   │   │   ├── client/             # Controller cho người dùng
│   │   │   ├── dto/                    # Data Transfer Objects
│   │   │   │   ├── request/            # DTO cho request yêu cầu
│   │   │   │   └── response/           # DTO cho response trả về
│   │   │   ├── exception/              # Xử lý ngoại lệ toàn cục
│   │   │   ├── model/                  # Entity ánh xạ database
│   │   │   ├── repository/             # JPA Repository (Tương tác dữ liệu)
│   │   │   ├── service/                # Business logic (xử lý nghiệp vụ, tách khỏi controller)
│   │   │   ├── type/                   # Enum và kiểu dữ liệu cố định
│   │   │   ├── util/                   # Tiện ích chung
│   │   │   ├── vnpaypayment/           # Xử lí nghiệp vụ thanh toán VNPay 
│   │   │   └── MovieTicketBookingApplication.java # File chạy chính
│   │   │
│   │   └── resources/
│   │       ├── application.properties  # Cấu hình hệ thống
│   │       └── static / templates      # (nếu có) HTML, tài nguyên tĩnh
│   │
│   └── test/                           # Unit test
│
├── pom.xml                             # File cấu hình Maven, các thư viện
```

### 🎨 Frontend
```
├── src/                # Thư mục chính của mã nguồn
│ ├── assets/           # Ảnh, biểu tượng, CSS, fonts,...
│ │
│ ├── components/       # Các component Vue tái sử dụng
│ │ ├── Admin/          # Component dành cho trang quản trị
│ │ ├── ChatBot/        # Giao diện và logic chatbot
│ │ ├── Client/         # Component cho giao diện khách hàng
│ │
│ ├── layout/           # Giao diện tổng thể (Header, Footer, Sidebar,...)
│ ├── router/           # Cấu hình định tuyến trang (Vue Router)
│ │
│ ├── App.vue           # Component gốc của ứng dụng
│ ├── main.js           # File khởi tạo ứng dụng Vue
│ └── style.css         # CSS chung của toàn dự án
│
├── .gitignore          # Cấu hình bỏ qua file/thư mục khi push Git
├── index.html          # Trang HTML gốc của ứng dụng
```

---

## 🚀 Cách Chạy Dự Án

### 🎨 Frontend (Vue.js)

#### 1. Clone (HTTPS)
```bash
git clone https://github.com/dangquocne/PoP_Cinema_FE.git
cd PoP_Cinema_FE
```

#### 2. Cài đặt
```bash
npm install
```

#### 4. Chạy dự án
```bash
npm run dev
```

👉 URL: http://localhost:5173

---

### 🔧 Backend (Spring Boot)

#### 1. Clone (HTTPS)
```bash
git clone https://github.com/dangquocne/PoP_Cinema_BE.git
cd PoP_Cinema_BE
```

#### 3. Cấu hình database
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/movie_booking_db
spring.datasource.username=root
spring.datasource.password=yourpassword
```

4. **Chạy dự án**  

   Chạy trực tiếp trong **IntelliJ IDEA** hoặc **Spring Tool Suite 4** qua file:  

   Mở file: 

   src/main/java/com/quoc/Movie_Ticket_Booking/MovieTicketBookingApplication.java

   Sau đó ấn chuột phải → **Run As → Java Application**	

👉 URL: http://localhost:8080

---

## 👨‍💻 Thành Viên Nhóm 49
- Huỳnh Đăng Quốc
- Nguyễn Thanh An
- Hồ Gia Huy
- Phan Quang Đính
- Phạm Đức Ngọc

---

## 📄 Giấy Phép
MIT License
