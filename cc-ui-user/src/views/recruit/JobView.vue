<script setup lang="ts">
import {getTopicForJobApi} from "@/api/topicForJob";
import {reactive, ref} from "vue";
import {addJobMessageApi, getJobMessageApi} from "@/api/recruit";
import {ElMessage, ElMessageBox, ElNotification} from "element-plus";
import TextEditor from "@/views/addAPost/TextEditor.vue";
import {baseUrl} from "@/utils/request";
import {View} from "@element-plus/icons-vue";
const circleUrl = "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"; // 设置头像地址
const options = ref(null) //招聘主题
const jobData = ref(null) //招聘信息
const queryForm = reactive({  //查询信息
  pageNum:1,
  pageSize:10,
  total:1,
  data:{
    topicId:0,
    user_id:0,
  }
})

const onLoad = async () => {
  try {
    await getTopicForJobApi().then((res) => {
      options.value = res.data
    })
    await getJobMessageApi(queryForm).then((res) => {
      jobData.value = res.data.data
      queryForm.total = res.data.total
      console.log(res)
    })
  }
  catch (e) {
    ElMessage.error(e)
  }
}
onLoad()
/**
 * 添加招聘信息
 */
const addJobDialogVisible = ref(false)
const jobMessage = reactive({
  topicId:3,
  jobTitle:'',
  jobContent:'',
})
const addJobMessage = async () => {
  try {
    await addJobMessageApi(jobMessage).then((res) => {
      if (res['code'] === '200')
      {
        ElNotification({
          title: '提交招聘信息',
          message: res["msg"],
          type: 'success',
        })
      } else
        ElNotification({
          title: '提交招聘信息',
          message: res["msg"],
          type: 'error',
        })
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
/**
 * 取消添加
 */
const handleClose = () => {
  ElMessageBox.confirm('是否取消添加招聘信息，数据将清空！')
      .then(() => {
        addJobDialogVisible.value = false
        Object.keys(jobMessage).forEach(key => {
          jobMessage[key] = ''
        })
      })
      .catch(() => {
        // catch error
      })
}
</script>

<template>


  <div style="display: flex;flex-direction: column;align-items: center;">
    <div class="container">
      <div class="head">
        <h3><el-button type="primary" round @click="addJobDialogVisible = true">添加招聘信息</el-button></h3>
      </div>

      <div class="body">
        <div v-if="jobData" v-for="(job, index) in jobData" :key="index" >
          <div class="title">{{job['jobTitle']}}</div>
          <div class="view-count">{{job['viewCount']}}<el-icon><View /></el-icon></div>
          <div class="left">
            <el-avatar :size="'large'" :src="baseUrl + '/' + job['userHead']" v-if="job['userHead']"/>
            <el-avatar :size="'large'" :src="circleUrl" v-else/>
          </div>
          <div>
            {{job['nickName']}}
          </div>
          <div v-html="job['jobContent']" class="content"></div>
        </div>
      </div>
    </div>

  </div>

  <!--添加招聘信息-->
  <el-dialog
      v-model="addJobDialogVisible"
      title="添加招聘信息"
      width="60%"
      draggable
  >
    <div style="margin: 10px" />
    <el-form
        :label-position="'right'"
        label-width="100px"
        :model="jobMessage"
        style="max-width: 1200px"
    >
      <el-form-item label="更改主题">
        <el-select v-model="jobMessage.topicId" :clearable="false" placeholder="NULL">
          <el-option
              v-for="option in options"
              :key="option['topicId']"
              :value="option['topicId']"
              :label="option['topicName']"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="题目">
        <el-input v-model="jobMessage.jobTitle" placeholder="请输入文章标题"/>
      </el-form-item>
      <el-form-item label="内容">
        <div>
          <TextEditor
              v-model="jobMessage.jobContent"
          />
        </div>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="addJobMessage">提交</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style lang="scss" scoped>
.container {
  width: 700px;
  background-color: #c9e4fa;
  padding: 1rem;
  border-radius: 1rem;
  height: 800px;
}

.head {
  flex-direction: column;
  align-items: center; /* 水平居中 */
}

.body {

  .title {
    font-weight: bolder;
    font-size: larger;
    display: flex;
    flex-direction: column;
    align-items: center; /* 水平居中 */
  }
  .view-count {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 10px;
  }
  .content {
    margin-top: 20px;
  }
}

</style>