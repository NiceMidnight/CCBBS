<script setup lang="ts">
import {ref} from "vue";
import {getOneJobMessageApi} from "@/api/job";
import {ElMessage} from "element-plus";
import {baseUrl} from "@/utils/request";
import {Flag, View} from "@element-plus/icons-vue";
import {useRoute, useRouter} from "vue-router";
import {timeHandlerSimplify} from "../../utils/timeHandler";

const circleUrl = "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"; // 设置头像地址
const route = useRoute();
const router = useRouter()
let jobId = Number(route.params.jobId);//帖子id
const jobData = ref(null) //招聘信息
const onLoad = async () => {
  try {
    await getOneJobMessageApi(jobId).then((res) => {
      jobData.value = res.data
      console.log(res)
    })
  }
  catch (e) {
    ElMessage.error("获取招聘信息错误："+e)
  }
}
onLoad()
const redirectToUser = (userId:any) => {
  router.push({ name: 'PersonalHomepage', params: { userId: userId } });
}
</script>

<template>

  <div class="container" v-if="jobData">
    <div class="title">{{jobData['jobTitle']}}</div>
    <div style="display:flex;">
      <div class="view-count">

      </div>
      <div>
        <el-icon><View /></el-icon>&nbsp;{{jobData['viewCount']}}
        <font-awesome-icon :icon="['far', 'clock']" />&nbsp;{{timeHandlerSimplify(null,null,jobData['createdAt'])}}
        <el-icon><Flag /></el-icon>&nbsp;{{jobData['topicName']}}
      </div>
    </div>

    <div style="display: flex;align-items: center;">
      <el-avatar :size="50" :src="jobData['userHead']" v-if="jobData['userHead']"/>
      <el-avatar :size="'large'" :src="circleUrl" v-else/>
      <el-link type="primary" :underline="false" @click="redirectToUser(jobData['userId'])">{{jobData['nickName']}}</el-link>
    </div>

    <div v-html="jobData['jobContent']" style="padding: 2rem"></div>
  </div>

  <div v-else>
    404
  </div>
</template>

<style lang="scss" scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center; /* 水平居中 */
  justify-content: center; /* 垂直居中 */
  width: 100%;
  max-width: 1100px;
  margin: 0 auto;
  background-color: white;
  border-radius: 20px;
}

.title {
  padding: 2rem;
  font-family: STXingkai, serif;
  font-size: 2rem;
  text-align: center;
}

.el-link {
  margin-left: 8px;
  font-size: large;
}

@media screen and (max-width: 800px) {
  .container {
    width: 90vw;
    background-color: #c9e4fa;
    padding: 1rem;
    border-radius: 1rem;
    height: 800px;
  }
}

</style>