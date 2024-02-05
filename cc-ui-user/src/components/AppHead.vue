<template>
  <div class="head">
    <el-menu
        :default-active="activeIndex"
        mode="horizontal"
        @select="handleSelect"
        router
    >
      <el-menu-item index="/" style="margin-left: 20px">首页</el-menu-item>
      <el-menu-item index="/community">招聘信息</el-menu-item>
      <el-menu-item index="/post">帖子</el-menu-item>
      <el-menu-item index="personalInfo">个人信息</el-menu-item>
      <el-menu-item index="/reservation">联系我们</el-menu-item>
      <div><el-menu-item index="/forum">论坛</el-menu-item></div>

      <div class="search-container" v-if="isSearchVisible">
        <el-input placeholder="搜索" class="search-input" />
        <el-button type="primary" round @click="" class="search-button">
          搜索
        </el-button>
<!--        <el-switch v-model="isDarkMode" style="margin-left: 20px" @change="toggleBackground">-->
<!--          <template #active-action>-->
<!--            <span class="custom-active-action">T</span>-->
<!--          </template>-->
<!--          <template #inactive-action>-->
<!--            <span class="custom-inactive-action">F</span>-->
<!--          </template>-->
<!--        </el-switch>-->
      </div>
      <div class="right-model">
        <el-button type="success" round @click="onLogin" v-if="!getLocalStorage" >登录</el-button>
        <el-button type="primary" round v-if="!getLocalStorage">注册</el-button>
        <el-button type="danger" round v-if="getLocalStorage" @click="onLogout">退出登录</el-button>
      </div>

      <el-menu-item index="4" >设置</el-menu-item>
    </el-menu>
  </div>

</template>

<script setup lang="ts">

import {ref, onMounted, onUnmounted} from "vue";
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";
const getLocalStorage = ref<string | null>(localStorage.getItem("TokenInfo"));
const router = useRouter();
const activeIndex = ref("1");
const isSearchVisible = ref(true);
const isDarkMode = ref(true);
const toggleBackground = () => {
  console.log(isDarkMode)
}
const handleSelect = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};

const onLogin = () => {
  router.push("/login");
};

const onLogout = () => {
  localStorage.clear();
  router.push("/login");
};

// 根据屏幕宽度处理可见性
const handleResize = () => {
  isSearchVisible.value = window.innerWidth >= 768;
};

onMounted(() => {
  handleResize();
  window.addEventListener("resize", handleResize);
});

onUnmounted(() => {
  window.removeEventListener("resize", handleResize);
});
</script>

<style lang="scss" scoped>
.el-menu {
  background-color: white;
  border-bottom: 1mm solid beige;
  font-style: normal;
  font-family: "微软雅黑 Light", serif;
  font-weight: bolder;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.el-menu-item {
  //margin-left: 20px;
}

.search-container {
  display: flex;
  align-items: center;
}

.search-input {
  width: 150px; /* Adjust the width as needed */
}

.search-button {
  margin-left: 10px; /* Adjust the margin as needed */
  font-size: 14px; /* Adjust the font size as needed */
}

.right-model {
  display: flex;
  align-items: center;
}
@media screen and (max-width: 768px) {
  .el-menu {
    flex-direction: column;
    align-items: stretch;
  }

  .right-model {
    margin-top: 15px;
    margin-left: 0;
  }

  .search-container {
    display: none; /* 在较小屏幕上隐藏搜索容器 */
  }
}

</style>