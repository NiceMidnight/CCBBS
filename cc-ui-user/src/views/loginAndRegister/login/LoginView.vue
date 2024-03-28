<script setup lang="ts">
import {User,Lock} from "@element-plus/icons-vue";
import {useRoute, useRouter} from "vue-router";
import {reactive, ref} from "vue";
import {baseUrl} from "@/utils/request";
import {useTokenStore} from "@/stores/mytoken";
import {ElMessage, FormInstance, FormRules} from "element-plus";
import {enterDailyUniqueVisitorsLogApi, loginApi} from "@/api/login";
const route = useRoute()
const { push } = useRouter();
/**
 * 登录数据
 */
const form = reactive({
  userName:'zhangsan',
  password:'zhangsan',
  code:''
})
/**
 * 验证码
 */
const verifyApi=ref(`${baseUrl}/user/verify`);
function getNewVerify(){
  verifyApi.value=verifyApi.value+"?"+(new Date()).getTime()
}
const store = useTokenStore() //  返回{token,saveToken}数据
/**
 * 登录按钮
 */
const onLogin = async () => {
  isLoading.value = true  // 点击登录设置不可点击为true
  //  先校验
  await formRef["value"]?.validate().catch(err => {
    ElMessage.error('登录失败...')
    isLoading.value = false
    throw err
  })
  //  再发送请求error+TypeError: Cannot read properties of null (reading 'userName')
  try {
    const user = ({
      userName:form.userName,
      password:form.password,
    })
    await loginApi(user,form.code).then((res) => {
      console.log(res)
      if (res["code"] === '200') {
        store.saveToken(res["token"])
        ElMessage.success(res["msg"])
        isLoading.value = false
        enterDailyUniqueVisitorsLogApi()
        push(route.query.redirect as string || '/')
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

const isLoading = ref(false)// 定义是否登录加载中防止多次点击
/**
 * 定义登录的表单校验（TS提供）
 */
const rules = reactive<FormRules>({
  userName: [
    { required: true, message: "用户名不能为空",trigger: "blur"},
    { min: 8, max: 18, message: "用户名在8-18位之间", trigger: "blur"}
    // {pattern:}
  ],
  password: [
    { required: true, message: "密码不能为空", trigger: "blur"},
    { min: 8, max: 18, message: "密码在8-18位之间", trigger: "blur"}
  ],
  code: [
    { required:true, message: "验证码不能为空", trigger:"blur"},
    { min: 4, max: 4, message: "验证码为4位", trigger: "blur"}
  ]
})
const formRef = ref<FormInstance | null>(null)
/**
 * 取消
 */
const onCancel = () => {
  push('/')
}

</script>

<template>

  <el-form class="login-form"
           ref="formRef"
           :model="form"
           :rules="rules">
    <h1>登录</h1>
    <el-form-item @keyup.enter="onLogin" prop="userName">
      <el-input placeholder="账号" :prefix-icon="User"  v-model="form.userName">

      </el-input>
    </el-form-item>

    <el-form-item @keyup.enter="onLogin" prop="password">
      <el-input  placeholder="密码" :prefix-icon="Lock" v-model="form.password">

      </el-input>
    </el-form-item>

    <el-form-item @keyup.enter="onLogin" prop="code">
      <el-input  placeholder="验证码" v-model="form.code" style="flex: 1" >

      </el-input>
      <el-image style="width: 110px;height: 36px;margin-left: 10px;border-radius: 5px" :src="verifyApi" alt="图片无法加载" @click="getNewVerify()" />
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="onLogin" :loading="isLoading">登录</el-button>
      <el-button type="danger" @click="onCancel" >首页</el-button>
    </el-form-item>
  </el-form>
</template>

<style scoped lang="scss">
.login-form {
  grid-column: 1;
  grid-row: 1;
  opacity: 1;
  transition: 1s ease-in-out;
  transition-delay: 0.5s;
  padding: 1% 30%;
  pointer-events: all;
}
.login-form.sign-up-model {
  opacity: 0;
  transition: 1s ease-in-out;
  pointer-events: none;
}
</style>