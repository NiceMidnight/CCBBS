<!-- RightContent.vue -->
<template>
  <el-container>
    <el-header>
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item>{{ topicId }}</el-breadcrumb-item>
      </el-breadcrumb>
    </el-header>
    <el-main>
      <div v-if="topicId === 1">主页内容</div>
      <div >
        {{ getContentByMenu(topicId) }}123
      </div>
    </el-main>
  </el-container>
</template>

<script setup lang="ts">
import {reactive, ref} from 'vue';
import {onMounted} from "@vue/runtime-core";
import {getAllPostApi, getPostTopicApi} from "../../../api/post";
import {ElMessage} from "element-plus";


const topicId = ref();
const getContentByMenu = (index: number) => {
  console.log(index)
  topicId.value = index
  return '';
};
const queryForm = reactive({
  pageNum:1,
  pageSize:10,
  total:1,
  data:{

  }
})
const postData = ref([])
onMounted( async () => {
  try {
    // await getAllPostApi(queryForm).then((res) => {
    //   postData.value = res.data
    //   console.log(postData.value)
    //   console.log(res)
    // })
  } catch (e) {
    ElMessage.error(e)
  }
})

</script>