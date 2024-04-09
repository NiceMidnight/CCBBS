<script setup lang="ts">

import {reactive, ref, watch} from "vue";
import {ElMessage} from "element-plus";
import {
  compliancePostApi,
  irregularityPostApi,
  postViewApi
} from "@/api/post";
import {timeHandler} from "@/utils/timeHandler";
import {truncateText} from "@/utils/textHandler";
import {useRouter} from "vue-router";
import {
  downProgressApi,
  getAllFeedbackApi,
  getFeedbackStatusOptionApi,
  getReminderStatusOptionApi,
  upProgressApi
} from "@/api/feedback";
import {getTFFOptionsApi} from "@/api/topicForFeedback";
import {baseUrl} from "@/utils/request";
import {Minus, Plus} from "@element-plus/icons-vue";

const router = useRouter(); // 解析router
/**
 * 文本截断
 * @param row
 */
const truncateTextFormatter = (feedbackContent: string) => {
  return truncateText(feedbackContent, 20);
};

/**
 * 表单数据---查询数据
 */
const tableData = ref([])
const feedbackStatusOptions = ref([]) //反馈处理状态选择器
const reminderStatusOptions = ref([]) //反馈信息状态
const topicForFeedbackOptions = ref([]) //类型选择器
const queryForm = reactive({
  pageNum:1,
  pageSize:10,
  total:1,
  data:{
    creatorName:'',
    topicId:null,
    reminderStatus:null,
    feedbackStatus:null,
  }
})
const onLoad = async() => {
  try {
    await getAllFeedbackApi(queryForm,null,null).then((res) => {
      tableData.value = res.data
      console.log(res.data)
    })
    await getFeedbackStatusOptionApi().then((res) => {
      feedbackStatusOptions.value = res.data.map(option => {
        return {
          label: translateFeedbackStatus(option),
          value: option
        }
      })
    })
    await getReminderStatusOptionApi().then((res) => {
      reminderStatusOptions.value = res.data.map(option => {
        return {
          label:translateReminderStatus(option),
          value:option
        }
      })
    })
    await getTFFOptionsApi().then((res) => {
      topicForFeedbackOptions.value = res.data
      console.log(res)
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
// 用户头像
const circleUrl = 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
/**
 * 转译
 * @param status
 */
const translateReminderStatus = (status) => {
  switch (status) {
    case 'Reminder':
      return '催促中';
    case 'NotReminded':
      return '未催促';
    default:
      return status;
  }
}
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
onLoad()
const handleHandlerStatusChange = (value) => {
  // 如果选项的值为空字符串，则将其设置为 null
  if (value === '') {
    queryForm.data.feedbackStatus = null;
  }
}
const handleReminderStatusChange = (value) => {
  // 如果选项的值为空字符串，则将其设置为 null
  if (value === '') {
    queryForm.data.reminderStatus = null;
  }
}

/**
 * 查找
 */
const onQuery = async() => {
  try {
    await getAllFeedbackApi(queryForm,startTime.value,endTime.value).then((res) => {
      tableData.value = res.data
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
// 分页
const small = ref(false)
const background = ref(false)
const disabled = ref(false)
const handleSizeChange = async (size:number) => {
  try {
    const queryParams = {
      pageNum:queryForm.pageNum,
      pageSize:size,
      total:queryForm.total,
      data:queryForm.data
    }
    await getAllFeedbackApi(queryParams,startTime.value,endTime.value).then((res) => {
      tableData.value = res.data
    })
  } catch (e) {
    ElMessage.error("改变每页数量错误："+e)
  }
}
const handleCurrentChange = async(num:number) => {
  try {
    const queryParams = {
      pageNum:num,
      pageSize:queryForm.pageSize,
      total:queryForm.total,
      data:queryForm.data
    }
    await getAllFeedbackApi(queryParams,startTime.value,endTime.value).then((res) => {
      tableData.value = res.data;
    })
  } catch (e) {
    ElMessage.error("改变页码错误："+e)
  }
}

/**
 * 时间范围
 */
const timeRange = ref("") //时间
const startTime =  ref() ;
const endTime =  ref();
watch(timeRange,(newTime) => {
  if (Array.isArray(newTime))
  {
    const start = new Date(newTime[0]);
    start.setHours(start.getHours() + 8);
    startTime.value = start.toISOString();
    const end = new Date(newTime[1]);
    end.setHours(end.getHours() + 8);
    endTime.value = end.toISOString();
  }
  else {
    startTime.value = ""
    endTime.value = ""
  }
})
/**
 * 快速时间选择器
 * @type {[{text: string, value: (function(): [Date,Date])},{text: string, value: (function(): [Date,Date])},{text: string, value: (function(): [Date,Date])}]}
 */
const shortcuts = [
  {
    text: '上周',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
      return [start, end]
    },
  },
  {
    text: '上个月',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
      return [start, end]
    },
  },
  {
    text: '过去三个月',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
      return [start, end]
    },
  },
]

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
const upProgress = async (feedbackId:number,feedbackStatus:string) => {
  await upProgressApi(feedbackId,feedbackStatus).then((res) => {
    if (res['code'] === '200')
    {
      ElMessage.success(res['msg'])
      onQuery()
    } else ElMessage.error(res['msg'])
  })
}
const downProgress = async (feedbackId:number,feedbackStatus:string) => {
  await downProgressApi(feedbackId,feedbackStatus).then((res) => {
    if (res['code'] === '200')
    {
      ElMessage.success(res['msg'])
      onQuery()
    } else ElMessage.error(res['msg'])
  })
}
</script>

<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header" style="margin-top: 10px;width: 1800px">
        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
          <el-form-item label="用户名">
            <el-input v-model="queryForm.data.creatorName" placeholder="请输入上传用户名称" clearable @keyup.enter="onQuery"/>
          </el-form-item>
          <el-form-item label="类型" @keyup.enter="onQuery"  style="width: 250px">
            <el-select
                v-model="queryForm.data.topicId"
                placeholder="NULL"
                clearable
            >
              <el-option
                  v-for="option in topicForFeedbackOptions"
                  :key="option['topicId']"
                  :value="option['topicId']"
                  :label="option['topicName']"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="处理状态" @keyup.enter="onQuery"  style="width: 250px">
            <el-select
                v-model="queryForm.data.feedbackStatus"
                placeholder="NULL"
                clearable
                @change="handleHandlerStatusChange"
            >
              <el-option
                  v-for="option in feedbackStatusOptions"
                  :key="option.value"
                  :value="option.value"
                  :label="option.label"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="催促状态" @keyup.enter="onQuery"  style="width: 250px">
            <el-select
                v-model="queryForm.data.reminderStatus"
                placeholder="NULL"
                clearable
                @change="handleReminderStatusChange"
            >
              <el-option
                  v-for="option in reminderStatusOptions"
                  :key="option.value"
                  :value="option.value"
                  :label="option.label"
              />
            </el-select>
          </el-form-item>
          <div style="display: flex">
            <el-form-item>
              <div >
                <span >时间</span>
                <el-date-picker
                    v-model="timeRange"
                    type="datetimerange"
                    :shortcuts="shortcuts"
                    range-separator="至"
                    start-placeholder="起始时间"
                    end-placeholder="结束时间"
                    style="margin-left: 1rem"
                />
              </div>
            </el-form-item>
            <el-form-item>
              <el-button type="success" @click="onQuery">查询</el-button>
            </el-form-item>
          </div>
        </el-form>
      </div>
    </template>

    <el-table :data="tableData['data']" border style="width: 100%">
      <!-- 自动递增的行ID列 -->
      <el-table-column type="index" label="行id" width="80" align="center"/>
      <el-table-column prop="feedbackId" label="反馈id" width="80" align="center"/>
      <el-table-column label="反馈内容" width="160" align="center" v-slot="{ row }">
        <el-tooltip
            class="box-item"
            effect="dark"
            :content="row['feedbackContent']"
            placement="bottom"
        >
          {{truncateTextFormatter(row['feedbackContent'])}}
        </el-tooltip>
      </el-table-column>
      <el-table-column prop="creatorHead" label="头像"  width="120" align="center" v-slot="{ row }">
        <el-avatar :size="50" :src="row['creatorHead'] || circleUrl" />
      </el-table-column>
      <el-table-column prop="creatorName" label="反馈用户"  width="120" align="center" />
      <el-table-column prop="createdTime" label="反馈时间"  width="180" align="center" :formatter="timeHandler"/>
      <el-table-column label="类型"  width="120" align="center">
        <template #default="{ row }">
          <el-tag size="large" :style="{ backgroundColor: row['topicColor'] }">{{ row['topicName'] }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="handlerName" label="处理员"  width="120" align="center"/>
      <el-table-column prop="createdAt" label="处理时间"  width="180" align="center" :formatter="timeHandler"/>
      <el-table-column label="催促状态"  width="100" align="center" v-slot="{ row }">
        {{translateReminderStatus(row['reminderStatus'])}}
      </el-table-column>>
      <el-table-column label="处理状态" align="center" width="300" v-slot="{ row }" >
        <el-progress :percentage="getPercentage(row.feedbackStatus)" :color="getProgressColor(row.feedbackStatus)" >
          <template #default="{ percentage }">
            <span class="percentage-label">{{translateFeedbackStatus(row['feedbackStatus'])}}</span>
          </template>
        </el-progress>
        <el-button-group>
          <el-button :icon="Minus" @click="downProgress(row['feedbackId'],row['feedbackStatus'])" />
          <el-button :icon="Plus" @click="upProgress(row['feedbackId'],row['feedbackStatus'])" />
        </el-button-group>
      </el-table-column>
    </el-table>
    <el-pagination
        v-model:current-page="queryForm.pageNum"
        v-model:page-size="queryForm.pageSize"
        :page-sizes="[1,10, 20, 30]"
        :small="small"
        :background="background"
        :disabled="disabled"
        layout="total, sizes, prev, pager, next, jumper"
        :total="tableData['total']"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        style="margin-top: 20px"
    />

  </el-card>
</template>

<style lang="scss" scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>