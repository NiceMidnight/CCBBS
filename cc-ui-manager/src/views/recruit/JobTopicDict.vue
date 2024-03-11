<script setup lang="ts">
import {getTopicForJobApi} from "@/api/topicForJob";
import {ref} from "vue";
const topicForJob = ref(null)
const onLoad = async () => {
  await getTopicForJobApi().then((res) => {
    topicForJob.value = res.data
    topicColor.value = res.data['topicColor']
  })
}
onLoad()
const topicColor = ref()
const colors = [
  {
    value: '#E63415',
    label: 'red',
  },
  {
    value: '#FF6600',
    label: 'orange',
  },
  {
    value: '#FFDE0A',
    label: 'yellow',
  },
  {
    value: '#1EC79D',
    label: 'green',
  },
  {
    value: '#14CCCC',
    label: 'cyan',
  },
  {
    value: '#4167F0',
    label: 'blue',
  },
  {
    value: '#6222C9',
    label: 'purple',
  },
]
</script>

<template>
  <el-card class="box-card">
    <el-table ref="multipleTableRef" :data="topicForJob" border style="width: 22%">
      <!-- 自动递增的行ID列 -->
      <el-table-column type="index" label="行id" width="70" align="center" fixed/>
      <el-table-column prop="topicId" label="主题id" width="70" align="center" fixed/>
      <el-table-column prop="topicName" label="主题名" width="70" align="center"/>
      <el-table-column  label="主题颜色" width="100" align="center" v-slot="{ row }">
        <el-select v-model="topicColor" multiple style="width: 90px">
          <el-option
              v-for="item in colors"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          >
            <div class="flex items-center">
              <el-tag :color="item.value" style="margin-right: 8px" size="small" />
              <span :style="{ color: item.value }">{{ item.label }}</span>
            </div>
          </el-option>
          <template #tag>
            <el-tag :key="row['topicColor']" :color="row['topicColor']" />
          </template>
        </el-select>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<style scoped lang="scss">

</style>