<script setup lang="ts">
import { isCollapse } from '../js/isCollapse.js'
import {useTokenStore} from "../../stores/mytoken";
import {getInfo} from "../../api/login";
import {ref} from "vue";
import {logout} from "../../api/logout";
import {ElMessage, ElMessageBox} from "element-plus";
import { useRouter} from "vue-router";

const router = useRouter()

const username = ref()

getInfo().then((res) => {
  username.value = window.localStorage.getItem("userName") || res.data
})

//  退出
const onLogout = async () => {
  // 1、询问
  await ElMessageBox.confirm("确认退出？","退出询问",{
    confirmButtonText:"确认",
    cancelButtonText:"取消",
    type:'warning'
  }).catch(()=>{
    ElMessage.info("取消退出操作")
    return new Promise(()=>{})
  })
  //  2、执行退出
  await logout().then((res) => {
    if (res["code"] === "200") {
      router.push('/')
      ElMessage.success(res["msg"])
    } else ElMessage.error("登出失败...")
  })
  //  3、清空token
  useTokenStore().saveToken("")
}
</script>

<template>
  <el-header>
<!--    图标-->
    <el-icon @click="isCollapse = !isCollapse">
      <IEpExpand v-show="isCollapse"/>
      <IEpFold v-show="!isCollapse"/>
    </el-icon>
<!--面包屑-->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item
          v-for="(item,index) in $route.matched"
          :key="index"
          :to="{ path: item.path }"
      >{{ item.meta.title }}</el-breadcrumb-item>
    </el-breadcrumb>

<!--  下拉菜单-->
    <el-dropdown>
      <span class="el-dropdown-link">
        <el-avatar :size="32" :src="'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" />
        <el-icon class="el-icon--right">
          <IEpArrow-down />
        </el-icon>
      </span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item>{{ username }}</el-dropdown-item>
          <el-dropdown-item divided @click="onLogout">退出</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </el-header>
</template>


<style lang="scss" scoped>
.el-header {
  display: flex;
  align-items: center;
  background-color: #c6e2ff;
  .el-icon {
    margin-right: 20px;
  }
}
.el-dropdown {
  margin-left: auto;
  .el-dropdown-link {
    display: flex;
    justify-content: center;
    align-items: center;
  }
}

</style>