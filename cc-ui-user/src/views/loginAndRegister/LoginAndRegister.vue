<script setup lang="ts">
import {ref} from 'vue'
import LoginView from "@/views/loginAndRegister/login/LoginView.vue";
import RegisterView from "@/views/loginAndRegister/register/RegisterView.vue";
const onClick = () => {
  vari.value = !vari.value
}
let vari = ref<boolean>(false)
</script>

<template>
  <div :class="{container:true,'sign-up-model':vari}">
    <div class="inner-left-container">
      <div class="login-content">
        <h1>广航校园论坛</h1>
        <el-button @click="onClick" round>去注册</el-button>
      </div>
      <img src="../../assets/img/register.jpg" class="image">
    </div>

    <div class="inner-right-container">
      <div class="register-content">
        <h1>广航校园论坛</h1>
        <el-button @click="onClick" round>去登陆</el-button>
      </div>
      <img src="../../assets/img/loginAndRegisterBGImg.jpg" class="image">
    </div>
    <div class="inner-sign-up-container">
      <LoginView :class="{'sign-up-model':vari}"/>
      <RegisterView :class="{'sign-up-model':vari}"/>
    </div>
  </div>
</template>

<style scoped lang="scss">
.container {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  position: relative;
  display: flex;
  flex-direction: row;
  background-image: url('../../assets/img/loginAndRegisterBGImg.jpg');
  background-size: cover;
  background-position: center;
}
.container::before {
  content: "";
  width: 2000px;
  height: 2000px;
  background-color: rgb(158, 212, 222,0.7);
  position: absolute;
  border-radius: 50%;
  transform: translateY(-50%);
  right: 48%;
  top: -10%;
  transition: 1.8s ease-in-out;
}
.inner-left-container {
  width: 0;
  flex: 1;
  z-index: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: flex-end;
  padding: 3rem 10% 2rem 10%;
  pointer-events: all;
}

.inner-right-container {
  width: 0;
  flex: 1;
  z-index: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: flex-start;
  padding: 3rem 10% 2rem 10%;
  pointer-events: none;
}

.container .inner-left-container .login-content,
.container .inner-left-container .image {
  transform: translateX(0px);
  transition: 1s ease-in-out;
  transition-delay: 0.5s;
}
.container .inner-right-container .register-content,
.container .inner-right-container .image {
  transform: translateX(1000px);
  transition: 1s ease-in-out;
  transition-delay: 0.5s;
}

.image {
  width: 100%;
  filter: blur(1px);
}
.inner-sign-up-container {
  width: 50%;
  height: 50%;
  position: absolute;
  right: 0;
  top: 20%;
  transition: 1s ease-in-out;
  transition-delay: 0.5s;
  display: grid;
  grid-template-columns: 1fr;
}
//动画
.container.sign-up-model::before {
  transform: translate(100%,-50%);
  transition: 1.8s ease-in-out;
  right: 52%;
}
.container.sign-up-model .inner-right-container .register-content,
.container.sign-up-model .inner-right-container .image {
  transform: translateX(0px);
  transition: 1s ease-in-out;
  transition-delay: 0.5s;
}
.container.sign-up-model .inner-left-container .login-content,
.container.sign-up-model .inner-left-container .image {
  transform: translateX(-1000px);
  transition: 1s ease-in-out;
  transition-delay: 0.5s;
}
.container.sign-up-model .inner-sign-up-container {
  width: 50%;
  height: 50%;
  top: 20%;
  right: 50%;
  transition: 1s ease-in-out;
  transition-delay: 0.5s;
}

.container.sign-up-model .inner-left-container {
  pointer-events: none;
}
.container.sign-up-model .inner-right-container {
  pointer-events: all;
}

</style>