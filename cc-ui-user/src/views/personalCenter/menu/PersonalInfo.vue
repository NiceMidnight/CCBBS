<script setup>
import {baseUrl} from "@/utils/request";
import {useRouter} from "vue-router";
import {ref} from "vue";
import {onMounted} from "@vue/runtime-core";
import {getUserInfoApi, updateUserInfoApi} from "@/api/login";
import {ElMessage, ElMessageBox} from "element-plus";
const route = useRouter()
const token = localStorage.getItem("TokenInfo")
const personalInfo = ref()
const squareUrl= 'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png'
//  个人信息
onMounted(async () => {
  if (token) {
    await getUserInfoApi().then((res) => {
      personalInfo.value = res.data
      console.log(personalInfo.value)
    })
  }
})
const onConfirm = async () => {
  await ElMessageBox.confirm(
      '确认修改提交嘛？',
      'Warning',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(() => {
        ElMessage.success("提交成功")
        updateUserInfoApi(personalInfo.value).then((res) => {
          ElMessage.success("修改成功")
        })
      })
      .catch(() => {
        ElMessage.info("提交失败")
      })
}

const onBack = () => {
  route.push('/')
}
</script>

<template>
  <div v-if="personalInfo" >
    <el-descriptions title="个人信息show" :column="3" border >
      <el-descriptions-item
          label="用户"
          label-align="right"
          align="center"
          label-class-name="my-label"
          class-name="custom-border"
      >{{ personalInfo["userName"] }}</el-descriptions-item>
      <el-descriptions-item label="性别" label-align="right" align="center">
        <el-radio-group v-model="personalInfo.userSex" class="ml-4">
          <el-radio :label="1">男</el-radio>
          <el-radio :label="0">女</el-radio>
        </el-radio-group>
      </el-descriptions-item>
      <el-descriptions-item label="年龄" label-align="right" align="center"><el-input v-model="personalInfo.userAge" /></el-descriptions-item>
      <el-descriptions-item label="头像" label-align="right" align="center"><el-avatar shape="square" :size="100" :src="`${baseUrl}/${personalInfo.userHead}` || squareUrl" /></el-descriptions-item>
      <el-descriptions-item label="电话" label-align="right" align="center"><el-input v-model="personalInfo.userPhone" /></el-descriptions-item>
      <el-descriptions-item label="住址" label-align="right" align="center"><el-input v-model="personalInfo.userAddress" /></el-descriptions-item>
      <el-descriptions-item label="个性签名" label-align="right" align="center">
        <el-input
            v-model="personalInfo.userPersonality"
            maxlength="20"
            placeholder="输入个性签名...(20字)"
            show-word-limit
            type="textarea"
        />
      </el-descriptions-item>
    </el-descriptions>
    <!-- 提交按钮在下方 -->
    <div class="button-container">
      <el-button type="success" round size="large"  @click="onConfirm">提交修改</el-button>
      <el-button type="primary" round size="large" style="margin-left: 50px" @click="onBack">返回首页</el-button>
    </div>
  </div>
</template>

<style scoped lang="scss">
.el-descriptions {
  margin: 0 auto;
  justify-content: center;
  align-items: center;
  width: 900px;
  border-radius: 20px;
  //border: 2px solid #000;
  //padding: 30px; /* 可选，添加一些内边距以改善外观 */
}
.button-container {
  text-align: center; /* 让按钮在容器中居中对齐 */
  margin-top: 40px; /* 调整按钮与描述信息之间的间距 */
}
@media screen and (max-width: 1300px) {

}
</style>