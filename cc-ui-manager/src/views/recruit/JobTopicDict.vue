<script setup lang="ts">
import {getTopicForJobApi, updateTopicColorApi} from "@/api/topicForJob";
import {ref} from "vue";
import {ElMessage} from "element-plus";
const topicForJob = ref(null)
const onLoad = async () => {
  await getTopicForJobApi().then((res) => {
    topicForJob.value = res.data
  })
}
onLoad()
const handleColorChange = async (row) => {
  await updateTopicColorApi(row).then((res) => {
    if (res["code"] === '200')
    {
      ElMessage.success(res["msg"])
      onLoad()
    }
    else ElMessage.error(res['msg'])
  })
};
</script>

<template>
  <el-card class="box-card">
    <el-table ref="multipleTableRef" :data="topicForJob" border style="width: 22%">
      <!-- 自动递增的行ID列 -->
      <el-table-column type="index" label="行id" width="70" align="center" fixed/>
      <el-table-column prop="topicId" label="主题id" width="70" align="center" fixed/>
      <el-table-column prop="topicName" label="主题名" width="100" align="center"/>
      <el-table-column  label="主题颜色" width="125" align="center" v-slot="{ row }">
        <el-color-picker v-model="row['topicColor']" size="large" @change="handleColorChange(row)"/>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<style scoped lang="scss">

</style>