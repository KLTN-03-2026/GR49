<template>
  <div class="flex flex-col items-center justify-center min-h-screen bg-green-50">
    <div class="bg-white shadow-lg rounded-xl p-8 text-center">
      <div class="shadow-1-strong d-flex align-items-center justify-content-center mb-4 mx-auto"
        style="width: 150px; height: 150px; ">
        <img src="https://res.cloudinary.com/dvxkhsfyj/image/upload/v1756445854/3-removebg-preview_svmz5i.png"
          height="200" alt="" loading="lazy" />
      </div>
      <h2 class="text-2xl font-bold text-green-600">Thanh toán thành công 🎉</h2>
      <p class="mt-3 text-gray-600">
        Đơn hàng của bạn đã được thanh toán, vui lòng kiểm tra đơn hàng trong email của bạn. Cảm ơn bạn đã sử dụng dịch
        vụ!
      </p>


      <!-- ✅ Thông tin đơn hàng -->
      <div class="row justify-content-center">
        <div class="col-12 col-md-10 mt-4">
          <h3 class="text-center mb-4">Chi Tiết Đơn Hàng</h3>
          <div class="card">
            <div class="card-body">
              <table class="table table-borderless">
                <thead style="background-color: #f1f1f1;">
                  <tr class="text-center">

                    <th>Mã Đơn Hàng</th>
                    <th>Mã Giao Dịch</th>
                    <th>Ngày Đặt</th>
                    <th>Tổng Tiền</th>
                    <th>Phim</th>
                    <th>Ghế</th>


                  </tr>
                </thead>
                <tbody>

                  <tr class="text-nowrap">

                    <td>{{ ma_hoa_don }}</td>
                    <td>{{ ma_giao_dich }}</td>
                    <td class="text-center align-middle">{{ formatDate(don_hang.ngayDat) }}
                    </td>
                    <td class="text-center align-middle">{{ formatVND(don_hang.tienThucNhan) }}</td>
                    <td class="text-center align-middle">{{ don_hang.tenPhim }}</td>
                    <td class="text-center align-middle"><span v-for="(item, index) in ds_ve" :key="index"
                        class="badge bg-light text-danger border border-danger mx-1 px-3 py-2">
                        {{ item.tenGhe || 'N/A' }}
                      </span></td>


                  </tr>

                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>

      <router-link to="/">
        Quay lại trang chủ
      </router-link>
    </div>
  </div>
</template>
<script>
import axios from 'axios';
const token = localStorage.getItem("key_client");
export default {
  data() {
    return {
      ma_hoa_don: this.$route.query.madonhang, // ✅ dùng query
      ma_giao_dich: this.$route.query.magiaodich, // ✅ dùng query
      don_hang: {},
      ds_ve: []
    };
  },
  mounted() {
    this.getDonHang();
  },
  methods: {
    formatVND(number) {
      return new Intl.NumberFormat("vi-VI", { style: "currency", currency: "VND" }).format(number,);
    },
    formatTime(time) {
      return time.slice(0, 5); // Convert HH:MM:SS to HH:MM
    },
    formatDate(date) {
      const d = new Date(date);
      return `${d.getDate()}/${d.getMonth() + 1}/${d.getFullYear()}`;
    },
    getDonHang() {
      // var payload = {
      //     id: this.id_phim
      // }
      console.log(this.ma_hoa_don);

      axios.get(`http://localhost:8080/api/client/don-hangs/${this.ma_hoa_don}`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      })
        .then((res) => {
          if (res.data.status) {
            this.don_hang = res.data.data;
            // console.log(this.data);
            this.ds_ve = res.data.ds_ve;
            // this.ds_dv = res.data.ds_dichVu;

          } else {
            this.$toast.error("Không tìm thấy đơn hàng");
            this.$router.push('/');
          }
        });
    },
  }

}

</script>