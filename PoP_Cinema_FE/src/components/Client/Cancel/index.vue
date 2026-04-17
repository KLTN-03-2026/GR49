<template>
  <div class="flex flex-col items-center justify-center min-h-screen bg-red-50">
    <div class="bg-white shadow-lg rounded-xl p-8 text-center">
      <div class="shadow-1-strong d-flex align-items-center justify-content-center mb-4 mx-auto"
        style="width: 150px; height: 150px; ">
        <img src="https://res.cloudinary.com/dvxkhsfyj/image/upload/v1756445843/2-removebg-preview_mlpkoa.png"
          height="200" alt="" loading="lazy" />
      </div>
      <h2 class="text-2xl font-bold text-red-600">Thanh toán đã bị hủy</h2>
      <p class="mt-3 text-gray-600">
        Đơn hàng của bạn chưa được thanh toán. Vui lòng thử lại hoặc chọn phương thức khác.
      </p>
      <router-link to="/" >
        Quay lại trang chủ
      </router-link>
    </div>
  </div>
</template>