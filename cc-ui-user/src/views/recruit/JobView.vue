<script setup lang="ts">
import {ref} from "vue";
import {getOneJobMessageApi} from "@/api/job";
import {ElMessage} from "element-plus";
import {baseUrl} from "@/utils/request";
import {View} from "@element-plus/icons-vue";
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

  <div style="display: flex;flex-direction: column;align-items: center;">
    <div class="container">
      <div class="body" v-if="jobData">

        <div class="title">{{jobData['jobTitle']}}</div>
        <div class="style-view-click">
          <div class="view-count">
            {{jobData['topicName']}}
          </div>
          <div class="view-count">
            {{jobData['viewCount']}}&nbsp;<el-icon><View /></el-icon>
          </div>
          <div class="view-count">
            {{timeHandlerSimplify(null,null,jobData['createdAt'])}}&nbsp;<font-awesome-icon :icon="['far', 'clock']" />
          </div>
        </div>

        <div class="user">
          <el-avatar :size="50" :src="baseUrl + '/' + jobData['userHead']" v-if="jobData['userHead']"/>
          <el-avatar :size="'large'" :src="circleUrl" v-else/>
          <el-link type="primary" :underline="false" @click="redirectToUser(jobData['userId'])">{{jobData['nickName']}}</el-link>
        </div>
        <div>

        </div>
        <div v-html="jobData['jobContent']" class="content"></div>
      </div>

      <div v-else>
        404
      </div>
    </div>

  </div>

</template>

<style lang="scss" scoped>
.container {
  width: 800px;
  background-color: #c9e4fa;
  padding: 1rem;
  border-radius: 1rem;
  height: 800px;
}
.el-link {
  margin-left: 8px;
  font-size: large;
}
.body {
  .title {
    font-weight: bolder;
    font-size: larger;
    display: flex;
    flex-direction: column;
    align-items: center; /* 水平居中 */
    padding: 1.5rem;
  }
  .style-view-click {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 10px;
    .view-count {
      display: flex;
      justify-content: center;
      align-items: center;
      margin-right: 0.7rem;
    }
  }
  .user {
    display: flex;
    align-items: center;
    margin: 10px;
  }
  .content {
    margin-top: 20px;
  }
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