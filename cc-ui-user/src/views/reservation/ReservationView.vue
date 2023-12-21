<template>
  <div class="contact-us">
    <el-card class="contact-card" :header="cardHeader">
      <el-form ref="contactFormRef" :model="formData" :rules="formRules" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="formData.name" placeholder="请输入您的姓名"></el-input>
        </el-form-item>
        <el-form-item label="电子邮件" prop="email">
          <el-input v-model="formData.email" placeholder="请输入您的电子邮件"></el-input>
        </el-form-item>
        <el-form-item label="消息" prop="message">
          <el-input v-model="formData.message" type="textarea" placeholder="请输入您的消息"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { ElForm, ElFormItem, ElInput, ElButton, ElMessage } from 'element-plus';

const formData = ref({
  name: '',
  email: '',
  message: '',
});

const formRules = {
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' },
  ],
  email: [
    { required: true, message: '请输入电子邮件', trigger: 'blur' },
    { type: 'email', message: '请输入有效的电子邮件地址', trigger: ['blur', 'change'] },
  ],
  message: [{ required: true, message: '请输入消息', trigger: 'blur' }],
};

const contactFormRef = ref(null); // 创建引用

const submitForm = () => {
  // 使用 .value 获取引用，并执行表单验证
  contactFormRef.value?.validate((valid) => {
    if (valid) {
      console.log('提交的信息：', formData.value);
      // 在这里你可以将信息发送到服务器或执行其他操作
      ElMessage.success('提交成功！');
    } else {
      console.log('请填写完整正确的信息');
      ElMessage.error('表单验证失败，请检查输入是否正确！');
    }
  });
};

const cardHeader = '联系我们';

</script>

<style>
.contact-us {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 80vh;
}

.contact-card {
  width: 400px;
}
</style>
