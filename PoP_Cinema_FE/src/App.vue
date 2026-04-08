<template>
  <component :is="layout">
      <router-view></router-view>
      
  </component>
  <Chatbot v-if="layout === 'client-layout'" />  
</template>

<script>
import Chatbot from "@/components/ChatBot/index.vue";
const default_layout = "default";
export default {
  components: { Chatbot },
  computed: {
    layout() {
      return (this.$route.meta.layout || default_layout) + "-layout";
    },
  },
};
</script>
<style>
</style>