<template>
  <div class="common-layout">
    <el-container>
<!--      侧边栏-->
      <AppAside/>

      <el-container class="header-and-main">
<!--        头部-->
        <AppHead/>
        <!--          主体-->
        <el-main>
          <el-scrollbar>
            <RouterView/>
          </el-scrollbar>
        </el-main>
      </el-container>

    </el-container>
  </div>
</template>

<script setup lang="ts">
import AppAside from "./AppAside.vue";
import AppHead from "./AppHead.vue";
</script>

<style lang="scss" scoped>
.header-and-main {
  flex-direction: column;
  height: 100vh;
}
.el-main {
  background-color: #ecf5ff;
  padding-right: 0;
}
</style>