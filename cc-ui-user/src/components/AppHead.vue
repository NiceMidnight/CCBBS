<script setup lang="ts">

import {ref, onMounted, onUnmounted, reactive} from "vue";
import {useRouter} from "vue-router";
import {ElMessage, ElMessageBox} from "element-plus";
import {logoutApi} from "@/api/login";
import {getTFFOptionsApi} from "@/api/feedback";
import TextEditor from "@/views/addAPost/TextEditor.vue";
const getLocalStorage = ref<string | null>(localStorage.getItem("TokenInfo"));
const router = useRouter(); //析构
const activeIndex = ref("/"); //页面加载时默认激活菜单的 index
const menuVisible = ref(true);  //菜单的可视化
/**
 * 设置选中逻辑
 */
const handleSelect = (key: string, keyPath: string[]) => {

};
/**
 * 登录OR注册
 * @param loginOrRegister
 */
const onLoginOrRegister = (loginOrRegister:boolean) => {
  localStorage.setItem('loginOrRegister', JSON.stringify(loginOrRegister));
  router.push("/login");
};
/**
 * 登出
 */
const onLogout = async () => {

  await ElMessageBox.confirm('确认登出吗?')
      .then(() => {
        logoutApi().then((res) => {
          if (res["code"] === '200')
          {
            ElMessage.success(res["msg"])
            localStorage.clear();
            router.push("/login");
          } else ElMessage.error(res["msg"])
        })

      })
      .catch(() => {
        // catch error
      })
};

/**
 * 根据屏幕宽度处理可见性
 */
const handleResize = () => {
  menuVisible.value = window.innerWidth >= 1000;
};

onMounted(() => {
  handleResize();
  window.addEventListener("resize", handleResize);
});

onUnmounted(() => {
  window.removeEventListener("resize", handleResize);
});

const ltrMenuDrawer = ref(false)
const getMenu = () => {
  ltrMenuDrawer.value = true
}


</script>

<template>
  <div v-if="menuVisible">
    <el-menu
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        :ellipsis="false"
        @select="handleSelect"
        router
    >
      <el-menu-item index="/">
        <div class="title">
          <div>
            <font-awesome-icon :icon="['fab', 'twitter']" />
          </div>
          <div style="padding: 0.3rem;font-size: 1.5rem" class="color-text">
            广州航海学院
          </div>
        </div>
      </el-menu-item>
      <el-menu-item index="/recruit">招聘信息</el-menu-item>
      <el-menu-item index="/personalInfo">个人中心</el-menu-item>
<!--      <el-menu-item index="/reservation">联系我们</el-menu-item>-->
      <el-menu-item index="/forum">论坛</el-menu-item>
      <el-menu-item index="/postAMessage">发帖</el-menu-item>
      <div class="flex-grow" />

      <div class="right-model">
        <el-button type="success" round @click="onLoginOrRegister(true)" v-if="!getLocalStorage" >登录</el-button>
        <el-button type="primary" round @click="onLoginOrRegister(false)" v-if="!getLocalStorage">注册</el-button>
        <el-button type="danger" round v-if="getLocalStorage" @click="onLogout">退出登录</el-button>
      </div>
      <el-sub-menu index="4">
        <template #title>设置</template>
        <el-menu-item index="/2-2" >更改密码</el-menu-item>
      </el-sub-menu>
<!--      <el-menu-item index="4" style="margin-right: 2rem">设置</el-menu-item>-->
    </el-menu>
  </div>

  <div v-else >
    <el-menu
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        :ellipsis="false"
        @select="handleSelect"
        router
    >
      <el-menu-item  style="align-items: center;" @click="getMenu">
        <div class="title">
          <div style="display: flex; align-items: center; padding: 0.3rem;">
            <font-awesome-icon :icon="['fab', 'twitter']" style="font-size: 2vw; margin-right: 5px;" />
            <span style="font-size: 2vw;" >广航论坛</span>
            <font-awesome-icon :icon="['fas', 'bars']" style="font-size: 2vw; margin-left: 5px;" />
          </div>
        </div>
      </el-menu-item>

      <el-drawer
          v-model="ltrMenuDrawer"
          title="菜单栏"
          :direction="'ltr'"
      >
        <el-menu :default-active="activeIndex" mode="vertical" router>
          <!-- 这里放上面的菜单项 -->
          <el-menu-item index="/">首页</el-menu-item>
          <el-menu-item index="/recruit">招聘信息</el-menu-item>
          <el-menu-item index="/personalInfo">个人信息</el-menu-item>
          <el-menu-item index="/forum">论坛</el-menu-item>
          <el-menu-item index="/postAMessage">发帖</el-menu-item>
          <el-menu-item v-if="!getLocalStorage" @click="onLoginOrRegister(true)">登录</el-menu-item>
          <el-menu-item  v-if="!getLocalStorage" @click="onLoginOrRegister(false)">注册</el-menu-item>

          <el-menu-item index="4">设置</el-menu-item>
          <el-menu-item  v-if="getLocalStorage" @click="onLogout">退出登录</el-menu-item>
        </el-menu>
      </el-drawer>
    </el-menu>
  </div>


</template>

<style lang="scss" scoped>
.flex-grow {
  flex-grow: 1;
}
.head {
  display: flex;
}
.title {
  font-size: 2rem;
  padding: 0.5rem;
  display: flex;
  align-items: center;
}
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

.right-model {
  display: flex;
  align-items: center;

}
@media screen and (max-width: 768px) {
  .title {
    font-size: 2vw;

    display: flex;
    align-items: center;
  }
  .right-model {
    margin-top: 15px;
    margin-left: 0;
  }
  .search-container {
    display: none; /* 在较小屏幕上隐藏搜索容器 */
  }
}
@media screen and (max-width: 1000px) {
  .el-menu {
    flex-direction: column;
    align-items: stretch;
  }
}
</style>