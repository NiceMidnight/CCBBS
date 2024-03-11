<script setup lang="ts">
// 是否折叠
import { isCollapse } from '../js/isCollapse.js'

</script>
<template>
  <el-aside >
    <el-scrollbar>
    <el-menu router unique-opened :collapse=isCollapse
    >
      <a  class="logo">
        <img src="@/assets/logo.jpg" alt="">
        <h1 style="margin-left: 10px">Tiamo</h1>
      </a>
<!--      资源管理-->
      <el-sub-menu index="资源管理">
        <template #title>
          <el-icon><IEpSetting/></el-icon> <span> 资源管理 </span>
        </template>
        <el-menu-item index="/dict">
          <el-icon><IEpSetting/></el-icon> <span> 资源字典 </span>
        </el-menu-item>
        <el-menu-item index="/article">
          <el-icon><IEpSetting/></el-icon> <span> 文章管理</span>
        </el-menu-item>
        <el-menu-item index="/images">
          <el-icon><IEpSetting/></el-icon> <span> 图片管理</span>
        </el-menu-item>
      </el-sub-menu>
<!--帖子管理-->
      <el-sub-menu index="2">
        <template #title>
          <el-icon><IEpSetting/></el-icon> <span> 帖子管理 </span>
        </template>
        <el-menu-item index="/posts">
          <el-icon><IEpSetting/></el-icon> <span> 帖子管理 </span>
        </el-menu-item>
        <el-menu-item index="/topicForPost">
          <el-icon><IEpSetting/></el-icon> <span> 帖子主题管理 </span>
        </el-menu-item>
        <el-menu-item index="/allPostComments">
          <el-icon><IEpSetting/></el-icon> <span> 帖子评论管理 </span>
        </el-menu-item>
      </el-sub-menu>
      <!--招聘信息管理-->
      <el-sub-menu index="3">
        <template #title>
          <el-icon><IEpSetting/></el-icon> <span> 招聘信息管理 </span>
        </template>
        <el-menu-item index="/jobTopicDict">
          <el-icon><IEpSetting/></el-icon> <span> 招聘主题 </span>
        </el-menu-item>
        <el-menu-item index="/applyRecruitMessage">
          <el-icon><IEpSetting/></el-icon> <span> 申请信息管理 </span>
        </el-menu-item>
        <el-menu-item index="/jobMessage">
          <el-icon><IEpSetting/></el-icon> <span> 招聘信息管理 </span>
        </el-menu-item>

      </el-sub-menu>

      <el-menu-item index="/users">
        <el-icon><IEpSetting/></el-icon> <span> 用户管理 </span>
      </el-menu-item>

      <el-sub-menu index="4">
        <template #title>
          <el-icon><IEpSetting/></el-icon> <span> 广告管理 </span>
        </template>
        <el-menu-item index="/advertisements">
          <el-icon><IEpSetting/></el-icon> <span> 广告列表</span>
        </el-menu-item>
        <el-menu-item>
          <el-icon><IEpSetting/></el-icon> <span> 广告位管理</span>
        </el-menu-item>
      </el-sub-menu>

    </el-menu>
    </el-scrollbar>
  </el-aside>
</template>

<style lang="scss" scoped>
.el-aside {
  background-color: #d9ecff;
  height: 100vh;
  width: auto;
}
.el-menu {
  background-color: #d9ecff;
  border-right: none;
  width: 200px;
  &.el-menu--collapse {
    width: 60px;
    & h1 {
      display: none;
    }
  }
}
.logo {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 60px;
  text-decoration: none;
  img {
    width: 32px;
    height: 32px;
  }
}

</style>