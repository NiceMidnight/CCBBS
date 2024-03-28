<script setup lang="ts">
import {reactive, ref} from "vue";
import {getFeedbackApi, getTFFOptionsApi, remindApi, submitFeedbackApi} from "@/api/feedback";
import TextEditor from "@/views/addAPost/TextEditor.vue";
import {Opportunity, Timer} from "@element-plus/icons-vue";
import {timeHandler, timeHandlerSimplify} from "../../../utils/timeHandler";
import {truncateText} from "@/utils/textHandler";
import {ElMessageBox, ElNotification} from "element-plus";

const tffOptions = ref([])
const tableData = ref([])
const onLoad = async () => {
  await getTFFOptionsApi().then((res) => {
    tffOptions.value = res.data
  })
  await getFeedbackApi().then((res) => {
    tableData.value = res.data
    console.log(res.data)
  })
}
onLoad()
/**
 * 转译
 * @param status
 */
const translateFeedbackStatus = (status) => {
  switch (status) {
    case 'Pending':
      return '未处理';
    case 'InProgress':
      return '处理中';
    case 'Processed':
      return '已处理';
    case 'Closed':
      return '已完成';
    default:
      return status;
  }
}
/**
 * 进度条
 * @param status
 */
const getPercentage = (status)  => {
  switch (status) {
    case 'Pending':
      return 0;
    case 'InProgress':
      return 50;
    case 'Processed':
      return 90;
    case 'Closed':
      return 100;
    default:
      return 0; // 如果状态不匹配，则默认为 0
  }
}
const getProgressColor = (status) => {
  switch (status) {
    case 'Pending':
      return '#f56c6c'; // 自定义颜色
    case 'InProgress':
      return '#85c6ef'; // 自定义颜色
    case 'Processed':
      return '#6f7ad3'; // 自定义颜色
    case 'Closed':
      return '#77dea0'; // 自定义颜色
    default:
      return 'blue'; // 如果状态不匹配，则默认为蓝色
  }
}

const dialogFormVisible = ref(false)
const feedbackForm = reactive({
  topicId:null,
  feedbackContent:'',
})
const submitFeedback = async () => {
  await submitFeedbackApi(feedbackForm).then((res) => {
    if (res['code'] === '200')
    {
      dialogFormVisible.value = false
      ElNotification({
        title: '反馈信息',
        message: res["msg"],
        type: 'success',
      })
      onLoad()
    } else ElNotification({
      title: '反馈信息',
      message: res["msg"],
      type: 'info',
    })
  })
}
/**
 * 催促
 * @param feedbackId
 */
const remind = async (feedbackId:number) => {
  await remindApi(feedbackId).then((res) => {
    onLoad()
  })
}

/**
 * 文本截断
 * @param feedbackContent
 */
const truncateTextFormatter = (feedbackContent: string) => {
  return truncateText(feedbackContent, 20);
};

/**
 * 取消添加图片
 */
const handleClose = () => {
  ElMessageBox.confirm('是否取消添加图片，数据将清空！')
      .then(() => {
        dialogFormVisible.value = false
        Object.keys(feedbackForm).forEach(key => {
          feedbackForm[key] = ''
        })
      })
      .catch(() => {
        // catch error
      })
}
</script>

<template>
  <div style="display: flex;padding: 10px"><el-icon><Opportunity /></el-icon>反馈中心</div>
  <el-button @click="dialogFormVisible = true">反馈</el-button>

  <el-table :data="tableData" height="250" style="width: 100%">
    <el-table-column prop="createdTime" label="时间" width="180" :formatter="timeHandlerSimplify"/>
    <el-table-column label="类型"  width="120" align="center">
      <template #default="{ row }">
        <el-tag size="large" :style="{ backgroundColor: row['topicColor'] }">{{ row['topicName'] }}</el-tag>
      </template>
    </el-table-column>

    <el-table-column label="反馈内容" width="350" align="center" v-slot="{ row }">
      <el-tooltip
          class="box-item"
          effect="dark"
          placement="bottom"
      >
        <template #content>
          <div v-html="row['feedbackContent']"></div>
        </template>
        {{truncateTextFormatter(row['feedbackContent'])}}
      </el-tooltip>
    </el-table-column>

    <el-table-column label="状态" v-slot="scope">
      <el-button color="#626aef" plain v-if="scope.row['reminderStatus'] === 'NotReminded'" @click="remind(scope.row['feedbackId'])">催促</el-button>
      <el-button color="#626aef" disabled plain v-else>已催促</el-button>
    </el-table-column>

    <el-table-column label="处理状态" align="center" width="150" v-slot="{ row }" >
      <el-progress :percentage="getPercentage(row['feedbackStatus'])" :color="getProgressColor(row['feedbackStatus'])" >
        <template #default="{ percentage }">
          <span class="percentage-label">{{translateFeedbackStatus(row['feedbackStatus'])}}</span>
        </template>
      </el-progress>
    </el-table-column>

  </el-table>

  <el-dialog v-model="dialogFormVisible" title="反馈表" width="1000" draggable>
    <el-form :model="feedbackForm">
      <el-form-item label="类型">
        <el-select v-model="feedbackForm.topicId" placeholder="选择类型" style="width: 300px">
          <el-option
              v-for="item in tffOptions"
              :key="item['topicId']"
              :label="item['topicName']"
              :value="item['topicId']"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="内容">
        <TextEditor
            v-model="feedbackForm.feedbackContent"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="submitFeedback">提交</el-button>
      </div>
    </template>
  </el-dialog>
</template>


<style>

</style>
