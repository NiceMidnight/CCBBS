<script setup lang="ts">
import {onMounted} from "@vue/runtime-core";
import {getUserInfoApi} from "@/api/login";
import {ref,computed } from "vue";
import { useRouter } from "vue-router";
import {ChatDotRound, Lollipop, Memo, Star, User} from "@element-plus/icons-vue";
import PersonalInfo from "@/views/personalCenter/menu/PersonalInfo.vue";
import PersonalMessage from "@/views/personalCenter/menu/PersonalMessage.vue";
import PersonalFans from "@/views/personalCenter/menu/PersonalFans.vue";
import PersonalFollow from "@/views/personalCenter/menu/PersonalFollow.vue";
import PersonalDevelopment from "@/views/personalCenter/menu/PersonalDevelopment.vue";
import {getReplyCommentsCountApi} from "@/api/comment";
const route = useRouter()
const token = localStorage.getItem("TokenInfo")
const personalInfo = ref()  //个人信息
const replyCommentsReply = ref(0)
//  个人信息
onMounted(async () => {
  if (token) {
    await getUserInfoApi().then((res) => {
      personalInfo.value = res.data
      console.log(personalInfo.value)
    })
    await getReplyCommentsCountApi().then((res) => {
      replyCommentsReply.value = res.data
    })
  }
})

const selectedMenuItem = ref("1"); // 默认选中的菜单项索引为 "1"
/**
 * 选择菜单索引
 * @param key
 * @param keyPath
 */
const selectOneFromMenu = (key: string, keyPath: string[]) => {
  selectedMenuItem.value = key
}
/**
 * 根据索引显示不同组件
 */
const selectedComponent = computed(() => {
  switch (selectedMenuItem.value) {
    case "1":
      return PersonalInfo;
    case "2":
      return PersonalFollow;
    case "3":
      return PersonalFans;
    case "4":
      return PersonalMessage;
    case "5":
      return PersonalDevelopment;
      // 添加更多菜单项对应的组件
    default:
      return null; // 没有匹配的菜单项
  }
});
</script>
<template>
  <div class="container">
    <div class="left-container">
      <el-aside class="left-menu">
        <div class="menu-bar"><font-awesome-icon :icon="['far', 'user']" />个人中心</div>
        <el-menu
            default-active="1"
            class="el-menu-vertical-demo"
            :collapse="false"
            @select="selectOneFromMenu"
        >
          <el-menu-item index="1">
            <el-icon><User /></el-icon>
            <template #title>我的资料</template>
          </el-menu-item>
          <el-menu-item index="2">
            <el-icon><Star /></el-icon>
            <template #title>我的关注</template>
          </el-menu-item>
          <el-menu-item index="3">
            <el-icon><Lollipop /></el-icon>
            <template #title>我的粉丝</template>
          </el-menu-item>
          <el-menu-item index="4">
            <el-badge :value="replyCommentsReply" :max="99" class="item">
              <el-icon><ChatDotRound /></el-icon>
              我的消息
            </el-badge>
          </el-menu-item>
          <el-menu-item index="5">
            <el-icon><Memo /></el-icon>
            <template #title>成就值排行榜</template>
          </el-menu-item>
        </el-menu>
      </el-aside>
    </div>

    <div class="right-container">
      <component :is="selectedComponent"></component> <!-- 动态渲染组件 -->
    </div>

    <template >
      <p style="font-size: larger">您还没有登录呢....</p>
    </template>
  </div>
</template>



<style lang="scss" scoped>
.container {
  margin: 0 auto;
  width: 1200px;
  height: 600px;
  //background-color: rgba(166, 59, 59, 0.8); /* 为了可视化，添加一个背景色   0.65*/
  border-radius: 20px;
  padding:20px;
  display: flex;
}

.left-container {
  width: 180px;
  height: 700px;
  border-radius: 20px;
  padding:20px;
  background-color: rgba(256, 256, 256, 1); /* 为了可视化，添加一个背景色   0.65*/
  border: 1px solid #000;

  .left-menu {
    //padding:10px;
    width: 200px;
  }
  .el-menu {
    margin-top: 10px;
  }
}

.right-container {
  margin: 0 auto;
  justify-content: center;
  align-items: center;
  width: 900px;
  height: 680px;
  border-radius: 20px;
  border: 1px solid #000;
  background-color: white;
  padding: 30px; /* 可选，添加一些内边距以改善外观 */

}
/* 响应式调整 */
@media screen and (max-width: 768px) {
  .left-container {
    flex-basis: 100%; /* 左侧容器占据整行 */
    margin-bottom: 20px; /* 可选，添加一些下边距 */
  }
  .right-container {
    flex-basis: 100%; /* 右侧容器占据整行 */
  }
}
</style>