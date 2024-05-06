<template>
  <div class="login">
  <el-form ref="formRef" :model="form" :rules="rules" label-width="120px" label-position="top">
    <h2>Login</h2>
    <el-form-item label="账号" prop="userName">
      <el-input v-model="form.userName" />
    </el-form-item>

    <el-form-item label="密码" prop="password">
      <el-input v-model="form.password" />
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="onLogin" :loading="isLoading">登录</el-button>
      <el-button type="primary" @click="onCancel">Cancel</el-button>
    </el-form-item>
  </el-form>
  </div>
</template>

<script setup lang="ts">
import {reactive, ref} from "vue";
import {ElMessage, FormInstance, FormRules} from "element-plus";
import {login} from "../../api/login";
import {useRoute, useRouter} from "vue-router";
import {useTokenStore} from "../../stores/mytoken";
import {getUserName} from "@/api/users";
/**
 * 解构出push方法
 */
const { push } = useRouter();
const route = useRoute()
/**
 * 响应数据
 */
const form = reactive({
  userName:'',
  password:'',
})
// 返回{token,saveToken}数据
const store = useTokenStore()
/**
 * 登录按钮监听
 */
const onLogin = async () => {
  isLoading.value = true  // 点击登录设置不可点击为true
  //  先校验
  await formRef.value?.validate().catch(err => {
    ElMessage.error('登录失败...')
    isLoading.value = false
    throw err
  })
  //  再发送请求
  try {
    await login(form).then(async (res) => {
      if (res["code"] === '200') {
        store.saveToken(res["token"])
        ElMessage.success(res["msg"])
        isLoading.value = false
        await getUserName().then((res) => {
          window.localStorage.setItem("userName",res.data)
          console.log(res.data)
        })
        await push(route.query.redirect as string || '/index')
      } else {
        ElMessage.error(res["msg"])
      }
      isLoading.value = false
    })
  }catch (e) {
    ElMessage.error("error+"+e)
    isLoading.value = false
  }

}

/**
 * 定义是否登录加载中防止多次点击
 */
const isLoading = ref(false)

/**
 * 定义表单校验（TS提供）
 */
const rules = reactive<FormRules>({
  userName: [
    { required: true, message: "用户名不能为空",trigger: "blur"},
    // {pattern:}
  ],
  password: [
    { required: true, message: "密码不能为空", trigger: "blur"},
    // { min: 4, max: 18, message: "密码在4-18位之间", trigger: "blur"}
  ]
})

const formRef = ref<FormInstance | null>(null)
//取消登录
const onCancel = () => {
  ElMessage.error("NO")
}
</script>

<style lang="scss" scoped>
.login {
  background-color: #eee;
  height: 100vh;
  display: flex;
  -webkit-justify-content: center;
  align-items: center;
  .el-form {
    width: 300px;
    background-color: #ccc;
    padding: 50px;
    border-radius: 10px;
    .el-form-item {
      margin-top: 20px;
    }
    .el-button {
      //width: 100%;
      margin-top: 30px;
    }
  }
}
</style>