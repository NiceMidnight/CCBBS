<template>
  <div style="width: 100%; height: 100%; overflow: auto;">
    <RouterView/>
    <div class="foot">
      <div class="footer-content">
        <p>&copy; 2023 Your Company Name. All rights reserved. --2023贵公司名称。版权所有</p>
        <p>Contact us: contact@example.com --联系我们:contact@example.com</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.foot {
  background-color: #333;
  color: #fff;
  padding: 20px;
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-wrap: wrap; /* 新增：允许内容在小屏幕时换行 */
  justify-content: space-between;
  align-items: center;
}

.footer-content p {
  margin: 0;
  font-size: 14px;
}

/* 媒体查询：在屏幕宽度小于等于600px时，修改样式 */
@media screen and (max-width: 600px) {
  .footer-content {
    flex-direction: column; /* 纵向排列 */
    text-align: center; /* 文字居中 */
  }
}
</style>
