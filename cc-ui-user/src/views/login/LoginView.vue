<script setup lang="ts">
import {reactive, ref,onMounted} from "vue";
import {ElMessage, FormInstance, FormRules} from "element-plus";
import { loginApi} from "@/api/login";
import { useRoute, useRouter} from "vue-router";
import {useTokenStore} from "@/stores/mytoken";
import {registerApi} from "@/api/register";
import {baseUrl} from "@/utils/request";
// 解构出push方法
const { push } = useRouter();
const route = useRoute()

//  左右切换
const isRegister = ref<boolean>(); // 用于切换登录和注册界面
// 监听路由变化，更新参数
const registerLogin = () => {
  isRegister.value = !isRegister.value
}
onMounted( () => {
  isRegister.value = localStorage.getItem("loginOrRegister") === null ? true : localStorage.getItem("loginOrRegister") === 'true';

})
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
/**
 * 注册
 */
const registerData = reactive({
  userName:'',
  password:'',
  passwords:'',
  userPhone:'',
  nickName:''
})
const registerRules = reactive<FormRules>({
  userName: [
    { required: true, message: "用户名不能为空",trigger: "blur"},
    { min: 8, max: 18, message: "用户名在8-18位之间", trigger: "blur"},
    { validator: (rule, value, callback) => {
        if (/\s/.test(value)) {
          callback(new Error("用户名不能包含空格"));
        } else {
          callback();
        }
      }
    }
  ],
  password: [
    { required: true, message: "密码不能为空", trigger: "blur"},
    { min: 8, max: 18, message: "密码在8-18位之间", trigger: "blur"},
    { validator: (rule, value, callback) => {
        if (/\s/.test(value)) {
          callback(new Error("密码不能包含空格"));
        } else {
          callback();
        }
      }
    }
  ],
  passwords: [
    { required: true, message: "请再次输入密码", trigger: "blur" },
    { validator: (rule, value, callback) => {
        if (value !== registerData.password) {
          callback(new Error("两次输入的密码不一致"));
        } else {
          callback();
        }
      }
    }
  ],
  userPhone: [
    { required: true, message: "电话号码不能为空", trigger: "blur" },
    { pattern: /^\d{11}$/, message: "电话号码必须是11位数字", trigger: "blur" }
  ],
})
const registerRef = ref<FormInstance | null>(null)
const isRegistering = ref(false)  // 点击登录设置不可点击为true
const onRegister = async () => {
  isRegistering.value = true
  await registerRef.value?.validate().catch(err => {
    ElMessage.error('注册失败...')
    isRegistering.value = false
    throw err
  })
  try {
    await registerApi(registerData).then((res) => {
      if (res["code"] === '200')
      {
        ElMessage.success(res["msg"])
        setTimeout(() => {
          isRegister.value = true
        }, 2000); // 3000 毫秒 = 3 秒
      } else ElMessage.info(res["msg"])
    })
  }catch (e) {
    ElMessage.error(e)
  }
}
</script>

<template>
  <div class="login-register">
    <div class="background-image">
      <img src="@/assets/img/mainBgImg.jpg" alt="Background"/>
      <div class="form-container" id="login-register" >
        <!-- 登录图片 -->
        <div v-if="isRegister" class="component-background">
          <div class="image-container">
            <!-- 图片容器 -->
            <img src="@/assets/img/login.jpg" alt="Left Image" />
          </div>
          <div class="text-container">
            <!-- 添加文本内容 -->
            <p>你正在登录哦。。。</p>
          </div>
        </div>
        <!-- 登录组件 -->
        <el-form v-if="isRegister"
                 ref="formRef"
                 :model="form"
                 :rules="rules"
                 label-width="120px"
                 label-position="top" style="padding: 140px">
          <h2>Login</h2>
          <el-form-item label="账号" prop="userName" @keyup.enter="onLogin">
            <el-input v-model="form.userName" placeholder="请输入用户名"  />
          </el-form-item>
          <el-form-item label="密码" prop="password" @keyup.enter="onLogin" >
            <el-input v-model="form.password"  placeholder="请输入密码" show-password/>
          </el-form-item>
          <el-form-item  label="验证码"  prop="code" @keyup.enter="onLogin">
            <el-input v-model="form.code" style="flex: 1" placeholder="请输入验证码"/>
            <el-image style="width: 110px;height: 36px;margin-left: 10px;border-radius: 5px" :src="verifyApi" alt="图片无法加载" @click="getNewVerify()" />
          </el-form-item>
          <el-form-item style="margin-top: 30px">
            <el-button type="primary" @click="onLogin" :loading="isLoading">登录</el-button>
            <el-button type="success" @click="registerLogin">去注册</el-button>
            <el-button type="danger" @click="onCancel" >取消登录</el-button>
          </el-form-item>
        </el-form>

        <!-- 注册组件 -->
        <el-form v-if="!isRegister"
                 ref="registerRef"
                 :model="registerData"
                 :rules="registerRules"
                 label-width="120px"
                 label-position="top"
                 style="margin-left: 120px;margin-top: 80px;margin-bottom: 80px">
          <h2>Register</h2>
          <el-form-item label="账号" prop="userName" @keyup.enter="onRegister">
            <el-input v-model="registerData.userName" placeholder="请输入用户名"  />
          </el-form-item>
          <el-form-item label="密码" prop="password" @keyup.enter="onRegister">
            <el-input v-model="registerData.password" placeholder="请设置密码"  show-password/>
          </el-form-item>
          <el-form-item label="再次输入密码" prop="passwords" @keyup.enter="onRegister">
            <el-input v-model="registerData.passwords" placeholder="请再次输入密码"  show-password/>
          </el-form-item>
          <el-form-item label="电话" prop="userPhone" @keyup.enter="onRegister">
            <el-input v-model="registerData.userPhone" placeholder="请输入电话"  />
          </el-form-item>
          <el-form-item label="昵称" prop="email" @keyup.enter="onRegister">
            <el-input v-model="registerData.nickName" placeholder="请输入昵称选填"  />
          </el-form-item>
          <el-button type="primary" @click="onRegister" >注册</el-button>
          <el-button type="success" @click="registerLogin">去登录</el-button>
          <el-button type="danger" @click="onCancel" >取消注册</el-button>
        </el-form>
        <!-- 注册图片 -->
        <div v-if="!isRegister" class="component-background" style="margin-left: 148px">
          <div class="image-container">
            <!-- 图片容器 -->
            <img src="@/assets/img/register.jpeg" alt="Left Image" />
          </div>
          <div class="text-container">
            <!-- 添加文本内容 -->
            <p>你正在注册哦。。。</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.login-register {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  .background-image {
    position: relative;
    width: 100%;
    height: 99%;
  }
  .background-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  .form-container {
    position: absolute;
    top: 20%; /* 将内容垂直居中 */
    left: 20%; /* 将内容水平居中 */
    //transform: translate(-50%, -50%); /* 居中方式 */
    z-index: 2;
    width: 1200px;
    background-color: rgba(250, 250, 250, 0.8);
    border-radius: 30px;
    display: flex;
    text-align: center;
    .component-background {
      width: 650px; /* 左边容器宽度为400px */
      background:   /* 左边容器宽度为400px */
          radial-gradient(
                  circle at 50% 0,
                  rgba(255, 0, 0, 0.5),
                  rgba(255, 0, 0, 0) 70.71%
          ),
          radial-gradient(
                  circle at 6.7% 75%,
                  rgba(0, 0, 255, 0.5),
                  rgba(0, 0, 255, 0) 70.71%
          ),
          radial-gradient(
                  circle at 93.3% 75%,
                  rgba(0, 255, 0, 0.5),
                  rgba(0, 255, 0, 0) 70.71%
          ) beige;
      border-radius: 30px;
      display: flex;
      justify-content: center;  /* 水平居中 */
      align-items: center; /* 垂直居中 */
      flex-direction: column; /* 垂直排列子元素 */
      .image-container img {
        border-radius: 50%;
        width: 30%;
        height: auto;
        justify-content: center;
        align-items: center;
      }
      .text-container {
        text-align: center; /* 文本水平居中 */
        margin-top: 30px; /* 控制文本与图片之间的间距 */
      }
      .text-container p {
        font-size: 16px; /* 文本字体大小 */
        font-weight: bold; /* 文本粗体 */
      }
    }
  }
  .el-button {
    margin-left: 20px;
  }
}
</style>