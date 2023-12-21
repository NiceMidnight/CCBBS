<template>

  <el-container>
<!--    导航栏-->
    <AppHead class="appHead"/>
<!--    主题-->
      <el-main>
        <el-scrollbar>
          <RouterView/>
        </el-scrollbar>
      </el-main>

  </el-container>
</template>

<script setup lang="ts">


import AppHead from "./AppHead.vue";
</script>

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
  background-image: url("@/assets/img/backgroundImg.png");
  background-size: cover; /* 或其他值，如 contain */
  //background-repeat: no-repeat;
  //background-position: center center;
  //padding: 0;
}
</style>