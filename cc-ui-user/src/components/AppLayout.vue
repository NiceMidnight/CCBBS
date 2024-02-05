<script setup lang="ts">
import AppHead from "./AppHead.vue";
import {ref,watch} from "vue";

// const isDarkMode = ref(true);
// const backgroundStyle = ref<string>(
//     isDarkMode.value
//         ? 'url("src/assets/img/blackBgImg.jpg")'
//         : 'url("src/assets/img/backgroundImg.png")'
// );
// watch(isDarkMode, (newValue) => {
//   console.log("isDarkMode changed:", newValue);
//   backgroundStyle.value = newValue
//       ? 'url("@/assets/img/blackBgImg.jpg")'
//       : 'url("@/assets/img/backgroundImg.png")';
// });
</script>

<template>
  <el-container>
<!--    导航栏-->
    <AppHead class="appHead"/>
<!--    主题-->
      <el-main >
        <el-scrollbar>
          <RouterView/>
        </el-scrollbar>
      </el-main>
  </el-container>
</template>

<style lang="scss" scoped>
.appHead {
  width: 100%;
  background-color: #ffffff;
}
.el-container {
  flex-direction: column;
  height: 100vh;
}
.el-main {
  background-image: url('../assets/img/backgroundImg.png');
  background-size: cover; /* 或其他值，如 contain */
}
</style>