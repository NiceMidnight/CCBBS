<template>
  <div class="not-found">
    <div class="background-image">
      <img src="@/assets/404.jpg" alt="Background" />
      <div class="content">
        <h1>404 - 页面未找到</h1>
        <p>抱歉，您所访问的页面不存在。</p>
        <router-link to="/index">返回首页</router-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">

</script>

<style lang="scss" scoped>
.not-found {
  position: relative;
  text-align: center;
  min-height: 100vh;
  color: #00ffb3;
}

.background-image {
  position: relative; /* 使用相对定位 */
  width: 100%;
  height: 100%;
}

.background-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.content {
  position: absolute;
  top: 50%; /* 将内容垂直居中 */
  left: 50%; /* 将内容水平居中 */
  transform: translate(-50%, -50%); /* 居中方式 */
  z-index: 1;
  padding: 20px;
  text-align: center;
}

h1 {
  font-size: 48px;
  margin-bottom: 20px;
}

p {
  font-size: 24px;
  margin-bottom: 40px;
}

router-link {
  font-size: 18px;
}
</style>