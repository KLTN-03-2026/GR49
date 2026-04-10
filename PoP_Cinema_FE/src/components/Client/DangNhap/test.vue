<template>
  <div class="flex items-center justify-center h-screen bg-gray-100">
    <div class="relative w-[380px] bg-white rounded-xl shadow-lg p-6">
      <!-- Nút đóng -->
      <button
        class="absolute top-3 right-3 text-gray-400 hover:text-gray-600 text-xl"
        @click="closeForm"
      >
        ✕
      </button>

      <!-- Logo -->
      <div class="flex justify-center mb-4">
        <img src="/logo.png" alt="logo" class="w-28" />
      </div>

      <!-- Tiêu đề -->
      <h2 class="text-center text-lg font-semibold mb-6">Đăng Nhập Tài Khoản</h2>

      <!-- Input Email -->
      <div class="mb-4">
        <input
          type="email"
          v-model="email"
          placeholder="Nhập Email"
          class="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-orange-400"
        />
      </div>

      <!-- Input Mật khẩu -->
      <div class="mb-4 relative">
        <input
          :type="showPassword ? 'text' : 'password'"
          v-model="password"
          placeholder="Nhập Mật khẩu"
          class="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-orange-400"
        />
        <span
          class="absolute right-3 top-1/2 -translate-y-1/2 text-gray-500 cursor-pointer"
          @click="togglePassword"
        >
          {{ showPassword ? '🙈' : '👁️' }}
        </span>
      </div>

      <!-- Nút Đăng nhập -->
      <button
        class="w-full bg-orange-500 text-white py-2 rounded-lg text-base font-medium hover:bg-orange-600"
        @click="login"
      >
        ĐĂNG NHẬP
      </button>

      <!-- Quên mật khẩu -->
      <p class="text-center text-sm text-gray-500 mt-3 cursor-pointer hover:underline">
        Quên mật khẩu?
      </p>

      <!-- Đăng ký -->
      <div class="text-center mt-5 border-t pt-4">
        <p class="text-sm text-gray-500">Bạn chưa có tài khoản?</p>
        <button
          class="w-full mt-2 border border-orange-500 text-orange-500 py-2 rounded-lg hover:bg-orange-50"
          @click="register"
        >
          Đăng ký
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const email = ref("");
const password = ref("");
const showPassword = ref(false);

const togglePassword = () => {
  showPassword.value = !showPassword.value;
};

const login = () => {
  alert(`Email: ${email.value}\nMật khẩu: ${password.value}`);
};

const closeForm = () => {
  alert("Đóng form");
};

const register = () => {
  alert("Đi đến trang đăng ký");
};
</script>
