<script setup lang="ts">

import {User, Lock, PhoneFilled} from "@element-plus/icons-vue";
import {reactive, ref,computed} from "vue";
import {ElMessage, FormInstance, FormRules} from "element-plus";
import {registerApi} from "@/api/register";
import {useRouter} from "vue-router";
import {provinces} from "@/utils/provincesData";
/**
 * 注册
 */
const registerData = reactive({
  userName:'',
  password:'',
  passwords:'',
  userPhone:'',
  nickName:'',
  userAddress:'',
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
    ElMessage.error('注册失败，请输入完整信息进行注册！')
    isRegistering.value = false
    throw err
  })
  try {
    registerData.userAddress = selectedProvince.value + selectedCity.value
    console.log(registerData)
    await registerApi(registerData).then((res) => {
      if (res["code"] === '200')
      {
        ElMessage.success(res["msg"])
        setTimeout(() => {
          push('/login')
        }, 2000); // 3000 毫秒 = 3 秒
      } else ElMessage.info(res["msg"])
    })
  }catch (e) {
    console.log(e)
  }
}
const { push } = useRouter();
/**
 * 取消
 */
const onCancel = () => {
  push('/')
}

const selectedProvince = ref<string>('');
const selectedCity = ref<string>('');

// 根据选中的省份获取对应的城市列表
const selectedProvinceCities = computed(() => {
  const selectedProvinceData = provinces.value.find(province => province.name === selectedProvince.value);
  return selectedProvinceData ? selectedProvinceData.cities : [];
});

// 当选择的省份发生改变时触发
const provinceChanged = () => {
  // 重置选中的城市
  selectedCity.value = '';
};
</script>

<template>

  <el-form class="register-form"
           ref="registerRef"
           :model="registerData"
           :rules="registerRules">
    <h1>注册</h1>
    <el-form-item prop="userName" @keyup.enter="onRegister">
      <el-input v-model="registerData.userName" placeholder="账号" :prefix-icon="User"></el-input>
    </el-form-item>
    <el-form-item prop="password" @keyup.enter="onRegister">
      <el-input v-model="registerData.password" placeholder="密码" :prefix-icon="Lock" show-password></el-input>
    </el-form-item>
    <el-form-item prop="passwords" @keyup.enter="onRegister">
      <el-input v-model="registerData.passwords" placeholder="请再次输入密码" :prefix-icon="Lock" show-password/>
    </el-form-item>
    <el-form-item prop="userPhone" @keyup.enter="onRegister">
      <el-input v-model="registerData.userPhone" placeholder="请输入电话" :prefix-icon="PhoneFilled" />
    </el-form-item>

    <el-form-item style="display: flex;align-items: center">
      <div style="display: flex;flex: 1;">
        <el-select v-model="selectedProvince" placeholder="请选择省份" @change="provinceChanged" style="margin-right: 20px;">
          <el-option v-for="province in provinces" :key="province.name" :label="province.name" :value="province.name"></el-option>
        </el-select>
        <el-select v-model="selectedCity" placeholder="请选择城市">
          <el-option v-for="city in selectedProvinceCities" :key="city" :label="city" :value="city"></el-option>
        </el-select>
      </div>
    </el-form-item>

    <el-form-item prop="email" @keyup.enter="onRegister">
      <el-input v-model="registerData.nickName" placeholder="请输入昵称选填" />
    </el-form-item>
    <el-button type="primary" @click="onRegister" >注册</el-button>
    <el-button type="danger" @click="onCancel" >首页</el-button>
  </el-form>
</template>

<style scoped lang="scss">
.register-form {
  grid-column: 1;
  grid-row: 1;
  opacity: 0;
  transition: 1s ease-in-out;
  padding: 1% 30%;
  pointer-events: none;
}
.register-form.sign-up-model {
  opacity: 1;
  transition: 1s ease-in-out;
  transition-delay: 0.5s;
  pointer-events: all;
}

</style>