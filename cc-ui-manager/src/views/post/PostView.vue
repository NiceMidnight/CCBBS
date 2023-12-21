<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header" style="margin-top: 10px;width: 1800px">
        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
          <el-form-item label="用户名称">
            <el-input v-model="queryForm.data.userName" placeholder="请输入上传用户名称" clearable @keyup.enter="onQuery"/>
          </el-form-item>
          <el-form-item label="标题">
            <el-input v-model="queryForm.data.postTitle" placeholder="请输入帖子标题" clearable @keyup.enter="onQuery"/>
          </el-form-item>
          <el-form-item label="内容">
            <el-input v-model="queryForm.data.postContent" placeholder="请输入帖子内容" clearable @keyup.enter="onQuery"/>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="onQuery">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
    </template>
    <el-table :data="tableData['data']" border style="width: 100%">
      <!-- 自动递增的行ID列 -->
      <el-table-column type="index" label="行id" width="80" align="center"/>
      <el-table-column prop="postId" label="文章id" width="80" align="center"/>
      <el-table-column prop="topicName" label="所属主题"  width="100" align="center"/>
      <el-table-column prop="postTitle" label="文章标题" width="160" align="center"/>
      <el-table-column prop="postContent" label="文章内容"  width="350" align="center"/>
      <el-table-column prop="userName" label="上传用户"  width="200" align="center"/>
      <el-table-column prop="createdAt" label="上传时间"  width="200" align="center" :formatter="timeHandler"/>
      <el-table-column  label="操作" width="150" align="center" v-slot="scope">
        <el-button type="default" @click="postView(scope.row['postId'])" plain>查看</el-button>
      </el-table-column>
      <el-table-column prop="postVisibility" label="状态"  width="100" align="center"/>
      <el-table-column label="是否合规" align="center"  width="230" v-slot="{ row }">
        <el-switch
            v-model="row['postStatus']"
            class="mb-2"
            active-value="COMPLIANCE"
            inactive-value="IRREGULARITY"
            active-text="启用"
            inactive-text="禁用"
            @change="handleChange($event,row['postId'])"
        />
      </el-table-column>
    </el-table>
    <el-pagination
        v-model:current-page="queryForm.pageNum"
        v-model:page-size="queryForm.pageSize"
        background layout="prev, pager, next" :total="tableData['total']"
        @current-change="(pageNum) => { onChange(pageNum)}"
    />

    <el-drawer v-model="drawer" :title="post['postTitle']" :direction="'ltr'">
      <span>{{ post['postContent'] }}</span>
    </el-drawer>

  </el-card>
</template>

<script setup lang="ts">

import {reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import {compliancePostApi, getAllPostApi, irregularityPostApi, postViewApi} from "@/api/post";
import {timeHandler} from "@/utils/timeHandler";
const drawer = ref(false)
/**
 * 表单数据---查询数据
 */
const tableData = ref([])
const queryForm = reactive({
  pageNum:1,
  pageSize:10,
  total:1,
  data:{
    userName:'',
    postTitle:'',
    postContent:''
  }
})
const onLoad = async() => {
  try {
    await getAllPostApi(queryForm).then((res) => {
      tableData.value = res.data
      console.log(res.data)
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
onLoad()
/**
 * 查找
 */
const onQuery = async() => {
  try {
    await getAllPostApi(queryForm).then((res) => {
      tableData.value = res.data
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
const onChange = async(pageNum:number) => {
  try {
    const queryParams = {
      pageNum:pageNum,
      pageSize:queryForm.pageSize,
      data:queryForm.data
    }
    await getAllPostApi(queryParams).then((res) => {
      tableData.value = res.data;
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
/**
 * 文章视图
 */
const post = ref([])
const postView = async (postId: number) => {
  try {
    await postViewApi(postId).then((res) => {
      drawer.value = true
      post.value = res.data
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
/**
 * 启用禁用
 */
const handleChange = async (act:"COMPLIANCE" | "IRREGULARITY",postId: number) => {
  let actions = {
    COMPLIANCE: {msg: '启用',fn: compliancePostApi},
    IRREGULARITY: {msg: '禁用',fn: irregularityPostApi}
  }
  const data = await actions[act].fn(postId)
  console.log(data)
  if (data["code"] === '200') {
    ElMessage.success( `${actions[act].msg}帖子`+postId+'成功')
  } else {
    ElMessage.error(`${actions[act].msg}帖子`+postId+'失败')
    throw new Error(`${actions[act].msg}帖子`+postId+'失败')
  }
}
</script>

<style lang="scss" scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>