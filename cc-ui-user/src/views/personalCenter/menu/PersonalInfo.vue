<script setup>
import {baseUrl} from "@/utils/request";
import {computed, ref} from "vue";
import {onMounted} from "@vue/runtime-core";
import {getUserInfoApi, updateUserInfoApi} from "@/api/login";
import {ElMessage, ElMessageBox, ElNotification} from "element-plus";
import {provinces} from "@/utils/provincesData";
import {timeHandlerSimplify} from "../../../utils/timeHandler";
const token = localStorage.getItem("TokenInfo")
const personalInfo = ref()
const squareUrl= 'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png'
const selectedProvince = ref('');
const selectedCity = ref('');
const file = 'file';  //设置文件格式
//  个人信息
onMounted(async () => {
  if (token) {
    await getUserInfoApi().then((res) => {
      personalInfo.value = res.data
      const addressParts = res.data.userAddress.split('、');
      selectedProvince.value = addressParts[0];
      selectedCity.value = addressParts[1];
      // console.log(personalInfo.value.userAddress)
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
        if (!/^\d{11}$/.test(personalInfo.value.userPhone)) {
          ElNotification({
            title: '个人信息',
            message: '电话号码必须是11位数字',
            type: 'error',
          })
          return
        }
        personalInfo.value.userAddress = selectedProvince.value + "、" + selectedCity.value
        personalInfo.value.userHead = avatarUrl
        updateUserInfoApi(personalInfo.value).then((res) => {
          if (res["code"] === "200") {
            ElNotification({
              title: '个人信息',
              message: res["msg"],
              type: 'success',
            })
          } else ElNotification({
            title: '个人信息',
            message: "修改失败",
            type: 'info',
          })
        })
      })
      .catch(() => {
        ElMessage.info("提交失败")
      })
}

// 根据选中的省份获取对应的城市列表--当选择的省份发生改变时触发
const selectedProvinceCities = computed(() => {
  const selectedProvinceData = provinces.value.find(province => province.name === selectedProvince.value);
  return selectedProvinceData ? selectedProvinceData.cities : [];
});
const provinceChanged = () => {
  selectedCity.value = '';
};
// 令牌
const tokenInfo = localStorage.getItem("TokenInfo")
let avatarUrl = ''; // 在这里定义avatarUrl变量
const handleUploadSuccess = (response, file, fileList) => {
  // 处理上传成功后的逻辑
  // 更新 avatarUrl
  console.log(response)
  avatarUrl = response.data;
}

</script>

<template>
  <div v-if="personalInfo" >
    <el-descriptions title="个人信息show" :column="3" border >
      <el-descriptions-item
          label="用户名"
          label-align="right"
          align="center"
          label-class-name="my-label"
          class-name="custom-border"
      >
        {{ personalInfo["userName"] }}
      </el-descriptions-item>

      <el-descriptions-item label="昵称" label-align="right" align="center">
        <el-input v-model="personalInfo.nickName" />
      </el-descriptions-item>

      <el-descriptions-item label="性别" label-align="right" align="center">
        <el-radio-group v-model="personalInfo.userSex" class="ml-4">
          <el-radio :label="1">男</el-radio>
          <el-radio :label="0">女</el-radio>
        </el-radio-group>
      </el-descriptions-item>

      <el-descriptions-item label="头像" label-align="right" align="center">
        <el-upload
            ref="upload"
            class="upload-demo"
            :limit="1"
            :action="baseUrl+'/user/avatar/uploadAvatar'"
            :name="file"
            :headers="{ Authorization: `Bearer ${tokenInfo}` }"
            :on-success="handleUploadSuccess"
            :show-file-list="false"
        >
          <el-tooltip
              class="box-item"
              effect="dark"
              content="更换头像"
              placement="right-start"
          >
            <el-avatar shape="square" :size="100" :src="avatarUrl  || personalInfo.userHead || squareUrl" />
          </el-tooltip>
        </el-upload>
      </el-descriptions-item>

      <el-descriptions-item label="电话" label-align="right" align="center">
        <el-input v-model="personalInfo.userPhone" />
      </el-descriptions-item>

      <el-descriptions-item label="地址" label-align="right" align="center" v-model="personalInfo.userAddress">
        <div style="display: flex;flex: 1;">
          <el-select v-model="selectedProvince" placeholder="请选择省份" @change="provinceChanged" style="margin-right: 20px;">
            <el-option v-for="province in provinces" :key="province.name" :label="province.name" :value="province.name"></el-option>
          </el-select>
          <el-select v-model="selectedCity" placeholder="请选择城市">
            <el-option v-for="city in selectedProvinceCities" :key="city" :label="city" :value="city"></el-option>
          </el-select>
        </div>
      </el-descriptions-item>

      <el-descriptions-item
          label="创建时间"
          label-align="right"
          align="center"
          label-class-name="my-label"
          class-name="custom-border"
      >
        {{ timeHandlerSimplify(null,null,personalInfo["userDate"]) }}
      </el-descriptions-item>

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