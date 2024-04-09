<script setup lang="ts">
import {
  complianceJobApi,
  getJobMessageApi,
  getJobStatusApi,
  getJobVisibilityApi,
  irregularityJobApi, jobViewApi
} from "@/api/jobMessage";
import {reactive, ref, watch} from "vue";
import {getTopicForJobApi} from "@/api/topicForJob";
import {timeHandler} from "@/utils/timeHandler";
import {truncateText} from "@/utils/textHandler";
import {ElMessage, ElTable} from "element-plus";
import {getApplyRecruiterMessageApi} from "@/api/applyRecruit";
import {baseUrl} from "@/utils/request";
const jobStatusOptions = ref([])  //招聘信息状态选择器
const jobVisibilityOptions = ref([])  //招聘信息可见性状态选择器
const topicForJobOptions = ref([])  //招聘主题选择器
const tableData = ref([]) //表单数据
const queryForm = reactive({
  pageNum:1,
  pageSize:10,
  total:1,
  data:{
    userName:null,
    jobTitle:null,
    jobStatus:null,
    jobVisibility:null,
    topicId:null,
  }
})
const onLoad = async () => {
  await getJobStatusApi().then((res) => {
    jobStatusOptions.value = res.data.map(option => {
      return {
        label: translateJobStatus(option),
        value: option
      }
    })
    console.log(res)
  })
  await getJobVisibilityApi().then((res) => {
    jobVisibilityOptions.value = res.data.map(option => {
      return {
        label: translateJobVisibility(option),
        value: option
      }
    })
    console.log(res)
  })
  await getTopicForJobApi().then((res) => {
    topicForJobOptions.value = res.data
    console.log(res)
  })
  await getJobMessageApi(queryForm,null,null).then((res) => {
    tableData.value = res.data
    console.log(res)
  })
}
onLoad()
/**
 * 转译
 */
const translateJobStatus = (status) => {
  switch (status) {
    case 'COMPLIANCE':
      return '合规';
    case 'IRREGULARITY':
      return '不合规';
    default:
      return status;
  }
}
const translateJobVisibility = (visibility) => {
  switch (visibility) {
    case 'PUBLIC':
      return '公开';
    case 'PRIVATE':
      return '私有';
    default:
      return visibility;
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
 * 查询
 */
const onQuery = async () => {
  await getJobMessageApi(queryForm,startTime.value,endTime.value).then((res) => {
    tableData.value = res.data
  })
}
const handleSizeChange = async (size:number) => {
  try {
    const queryParams = {
      pageNum:queryForm.pageNum,
      pageSize:size,
      total:queryForm.total,
      data:queryForm.data
    }
    await getJobMessageApi(queryParams,startTime.value,endTime.value).then((res) => {
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
    await getJobMessageApi(queryParams,startTime.value,endTime.value).then((res) => {
      tableData.value = res.data
    })
  } catch (e) {
    ElMessage.error("改变页码错误："+e)
  }
}
/**
 * 清除搜索框
 * @param value
 */
const handleTopicChange = (value) => {
  if (value === '') {
    queryForm.data.topicId = null;
  }
}
const handleJobStatusChange = (value) => {
  if (value === '') {
    queryForm.data.jobStatus = null;
  }
}
const handleJobVisibilityChange = (value) => {
  if (value === '') {
    queryForm.data.jobVisibility = null;
  }
}
/**
 * 文本截断
 * @param jobContent
 */
const truncateTextFormatter = (jobContent: string) => {
  return truncateText(jobContent, 10);
};
/**
 * 启用禁用
 */
const handleChange = async (act:"COMPLIANCE" | "IRREGULARITY",jobId: number) => {
  let actions = {
    COMPLIANCE: {msg: '合规',fn: complianceJobApi},
    IRREGULARITY: {msg: '违规',fn: irregularityJobApi}
  }
  const data = await actions[act].fn(jobId)
  console.log(data)
  if (data["code"] === '200') {
    ElMessage.success( data['msg'])
  } else {
    ElMessage.error(data['msg'])
    throw new Error(data['msg'])
  }
}
/**
 * 多选
 */
const multipleTableRef = ref<InstanceType<typeof ElTable>>()
const multipleSelection = ref([])
const handleSelectionChange = (val: any) => {
  multipleSelection.value = val
  console.log(multipleSelection)
}
// 用户头像
const circleUrl = 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
/**
 * 内容
 */
const drawer = ref(false)
const jobMessage = ref([])
const jobView = async (job) => {
  drawer.value = true
  await jobViewApi(job['jobId']).then((res) => {
    jobMessage.value = res.data
  })
}
</script>

<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header" style="margin-top: 10px;width: 1800px">
        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
          <el-form-item label="用户名">
            <el-input v-model="queryForm.data.userName" placeholder="请输入上传用户名称" clearable @keyup.enter="onQuery"/>
          </el-form-item>
          <el-form-item label="招聘标题">
            <el-input v-model="queryForm.data.jobTitle" placeholder="请输入标题信息" clearable @keyup.enter="onQuery"/>
          </el-form-item>
          <div style="display: flex">
            <el-form-item label="主题" @keyup.enter="onQuery"  style="width: 250px">
              <el-select
                  v-model="queryForm.data.topicId"
                  placeholder="NULL"
                  clearable
                  @change="handleTopicChange"
                  v-if="topicForJobOptions && topicForJobOptions.length > 0"
              >
                <el-option
                    v-for="option in topicForJobOptions"
                    :key="option.topicId"
                    :value="option.topicId"
                    :label="option['topicName']"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="帖子状态" @keyup.enter="onQuery"  style="width: 250px">
              <el-select
                  v-model="queryForm.data.jobStatus"
                  placeholder="NULL"
                  clearable
                  @change="handleJobStatusChange"
                  v-if="jobStatusOptions && jobStatusOptions.length > 0"
              >
                <el-option
                    v-for="option in jobStatusOptions"
                    :key="option.value"
                    :value="option.value"
                    :label="option.label"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="可见性" @keyup.enter="onQuery"  style="width: 250px">
              <el-select
                  v-model="queryForm.data.jobVisibility"
                  placeholder="NULL"
                  clearable
                  @change="handleJobVisibilityChange"
                  v-if="jobVisibilityOptions && jobVisibilityOptions.length > 0"
              >
                <el-option
                    v-for="option in jobVisibilityOptions"
                    :key="option.value"
                    :value="option.value"
                    :label="option.label"
                />
              </el-select>
            </el-form-item>
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
    <el-table ref="multipleTableRef" :data="tableData['data']" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <!-- 自动递增的行ID列 -->
      <el-table-column type="index" label="行id" width="60" align="center" fixed/>
      <el-table-column prop="jobId" label="招聘id" width="70" align="center" fixed/>
      <el-table-column label="招聘标题" width="150" align="center" v-slot="{ row }">
      {{truncateTextFormatter(row['jobTitle'])}}
      </el-table-column>
      <el-table-column label="招聘内容"  width="170" align="center" v-slot="{ row }">
        {{truncateTextFormatter(row['jobContent'])}}
      </el-table-column>
      <el-table-column prop="userId" label="用户id"  width="70" align="center"/>
      <el-table-column label="头像" width="100" align="center" v-slot="{ row }">
        <el-avatar :size="50" :src="row['userHead'] || circleUrl" />
      </el-table-column>
      <el-table-column prop="userName" label="用户名" width="100" align="center"/>
      <el-table-column prop="nickName" label="昵称" width="100" align="center"/>
      <el-table-column label="主题" width="100" align="center">
        <template #default="{ row }">
          <el-tag size="large" :style="{ backgroundColor: row['topicColor'] }">{{ row['topicName'] }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createdAt" label="提交时间"  width="170" align="center" :formatter="timeHandler"/>
      <el-table-column prop="viewCount" label="点击量" width="100" align="center"/>
      <el-table-column prop="jobVisibility" label="可见性" width="100" align="center"/>
      <el-table-column label="状态" align="center"  width="150" v-slot="{ row }">
        <el-switch
            v-model="row['jobStatus']"
            class="mb-2"
            active-value="COMPLIANCE"
            inactive-value="IRREGULARITY"
            active-text="合规"
            inactive-text="违规"
            @change="handleChange($event,row['jobId'])"
        />
      </el-table-column>
      <el-table-column fixed="right" label="操作选项" align="center" width="150">
        <template #default="{ row }">
          <el-button type="primary" @click="jobView(row)" plain>查看内容</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        v-model:current-page="queryForm.pageNum"
        v-model:page-size="queryForm.pageSize"
        :page-sizes="[ 5, 10, 20, 30]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="tableData['total']"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        style="margin-top: 20px"
    />
    <el-drawer v-model="drawer" :title="jobMessage['jobTitle']" :direction="'btt'">
      <span v-html="jobMessage['jobContent'] "></span>
    </el-drawer>
  </el-card>
</template>

<style scoped lang="scss">

</style>