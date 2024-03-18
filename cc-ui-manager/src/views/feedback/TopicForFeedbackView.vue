<script setup lang="ts">
import {getTopicForJobApi, updateTopicColorApi} from "@/api/topicForJob";
import {reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import {
  disableTopicApi,
  enableTopicApi,
  getTopicForFeedbackApi,
  getTopicStatusApi,
  updateTFFColorApi
} from "@/api/topicForFeedback";
import {timeHandler} from "@/utils/timeHandler";
const topicForFeedback = ref([])
const topicStatusOptions = ref([])
const onLoad = async () => {
  await getTopicStatusApi().then((res) => {
    topicStatusOptions.value = res.data.map(option => {
      return {
        label:translateTopicStatus(option),
        value:option
      }
    })
  })
  await getTopicForFeedbackApi(topicStatus.value).then((res) => {
    topicForFeedback.value = res.data
  })
}
onLoad()
/**
 * 转译
 * @param status
 */
const translateTopicStatus = (status) => {
  switch (status) {
    case 'ENABLE':
      return '启用';
    case 'DISABLE':
      return '禁用';
    default:
      return status;
  }
}
const handleColorChange = async (row) => {
  if (row['topicColor'] === '' || row['topicColor'] === null)
  {
    ElMessage.error("颜色不能为空")
    return
  }
  await updateTFFColorApi(row['topicId'],row['topicColor']).then((res) => {
    if (res['code'] === '200')
    {
      ElMessage.success(res['msg'])
    }
    else ElMessage.error(res['msg'])
  })
};
const topicStatus = ref(null)
const onQuery = async () => {
  await getTopicForFeedbackApi(topicStatus.value).then((res) => {
    topicForFeedback.value = res.data
  })
}
/**
 * 启用禁用
 */
const handleChange = async (act:"ENABLE" | "DISABLE",topicId: number) => {
  let actions = {
    ENABLE: {msg: '启用',fn: enableTopicApi},
    DISABLE: {msg: '禁用',fn: disableTopicApi}
  }
  const data = await actions[act].fn(topicId)
  // console.log(data)
  if (data["code"] === '200') {
    ElMessage.success(data['msg'])
  } else {
    ElMessage.error(data['msg'])
    throw new Error(data['msg'])
  }
}
</script>

<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header" style="margin-top: 10px;width: 1800px">
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item label="状态" @keyup.enter="onQuery"  style="width: 250px">
            <el-select
                v-model="topicStatus"
                placeholder="NULL"
                clearable
            >
              <el-option
                  v-for="option in topicStatusOptions"
                  :key="option.value"
                  :value="option.value"
                  :label="option.label"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="onQuery">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
    </template>
    <el-table ref="multipleTableRef" :data="topicForFeedback" border style="width: 100%">
      <!-- 自动递增的行ID列 -->
      <el-table-column type="index" label="行id" width="70" align="center" fixed/>
      <el-table-column prop="topicId" label="主题id" width="70" align="center" fixed/>
      <el-table-column prop="topicName" label="主题名" width="100" align="center"/>
      <el-table-column prop="userName" label="创建者" width="100" align="center"/>
      <el-table-column prop="createdTime" label="创建时间" width="180" align="center" :formatter="timeHandler"/>
      <el-table-column label="类型颜色" width="125" align="center" v-slot="{ row }">
        <el-color-picker v-model="row['topicColor']" size="large" @change="handleColorChange(row)"/>
      </el-table-column>
      <el-table-column label="状态" align="center"  width="205" v-slot="{ row }">
        <el-switch
            v-model="row['topicStatus']"
            class="mb-2"
            active-value="ENABLE"
            inactive-value="DISABLE"
            active-text="启用"
            inactive-text="禁用"
            @change="handleChange($event,row['topicId'])"
        />
      </el-table-column>
    </el-table>
  </el-card>
</template>

<style scoped lang="scss">

</style>