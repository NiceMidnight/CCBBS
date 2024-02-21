<script setup lang="ts">

import {reactive, ref, watch} from "vue";
import {ElMessage} from "element-plus";
import {
  compliancePostApi,
  getAllPostApi,
  getPostStatusForComplianceOptionApi,
  irregularityPostApi,
  postViewApi
} from "@/api/post";
import {timeHandler} from "@/utils/timeHandler";
import {truncateText} from "@/utils/textHandler";
import {useRouter} from "vue-router";

const drawer = ref(false)
/**
 * 文本截断
 * @param row
 */
const truncateTextFormatter = (row: any) => {
  return truncateText(row.postContent, 20);
};

/**
 * 表单数据---查询数据
 */
const tableData = ref([])
const postStatusForComplianceOptions = ref([]) //帖子状态选择器
const queryForm = reactive({
  pageNum:1,
  pageSize:10,
  total:1,
  data:{
    userName:'',
    postTitle:'',
    postContent:'',
    nickName:'',
    postStatus:null
  }
})
const onLoad = async() => {
  try {
    await getAllPostApi(queryForm,null,null).then((res) => {
      tableData.value = res.data
      console.log(res.data)
    })
    await getPostStatusForComplianceOptionApi().then((res) => {
      postStatusForComplianceOptions.value = res.data.map(option => {
        return {
          label: translateStatus(option),
          value: option
        }
      })
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
const translateStatus = (status) => {
  switch (status) {
    case 'COMPLIANCE':
      return '合规';
    case 'IRREGULARITY':
      return '不合规';
    default:
      return status;
  }
}
onLoad()
const handleStatusChange = (value) => {
  // 如果选项的值为空字符串，则将其设置为 null
  if (value === '') {
    queryForm.data.postStatus = null;
  }
}
/**
 * 查找
 */
const onQuery = async() => {
  try {
    await getAllPostApi(queryForm,startTime.value,endTime.value).then((res) => {
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
      data:queryForm.data
    }
    await getAllPostApi(queryParams,startTime.value,endTime.value).then((res) => {
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
      data:queryForm.data
    }
    await getAllPostApi(queryParams,startTime.value,endTime.value).then((res) => {
      tableData.value = res.data;
    })
  } catch (e) {
    ElMessage.error("改变页码错误："+e)
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
const router = useRouter(); // 解析router
/**
 * 重定向到对于帖子的评论管理
 * @param postId
 * @param postTitle
 */
const redirectToPostComment = (postId,postTitle) => {
  router.push({ name: 'signalPostComments', params: { title:postTitle,id: postId } });
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
</script>

<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header" style="margin-top: 10px;width: 1800px">
        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
          <el-form-item label="用户名">
            <el-input v-model="queryForm.data.userName" placeholder="请输入上传用户名称" clearable @keyup.enter="onQuery"/>
          </el-form-item>
          <el-form-item label="昵称">
            <el-input v-model="queryForm.data.nickName" placeholder="请输入上传用户昵称" clearable @keyup.enter="onQuery"/>
          </el-form-item>
          <el-form-item label="标题">
            <el-input v-model="queryForm.data.postTitle" placeholder="请输入帖子标题" clearable @keyup.enter="onQuery"/>
          </el-form-item>
          <el-form-item label="内容">
            <el-input v-model="queryForm.data.postContent" placeholder="请输入帖子内容" clearable @keyup.enter="onQuery"/>
          </el-form-item>
          <div style="display: flex">
            <el-form-item label="帖子状态" @keyup.enter="onQuery">
              <el-select
                  v-model="queryForm.data.postStatus"
                  placeholder="NULL"
                  clearable
                  @change="handleStatusChange"
              >
                <el-option
                    v-for="option in postStatusForComplianceOptions"
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
    <el-table :data="tableData['data']" border style="width: 100%">
      <!-- 自动递增的行ID列 -->
      <el-table-column type="index" label="行id" width="80" align="center"/>
      <el-table-column prop="postId" label="文章id" width="80" align="center"/>
      <el-table-column prop="topicName" label="所属主题"  width="120" align="center"/>
      <el-table-column prop="postTitle" label="文章标题" width="160" align="center"/>
      <el-table-column prop="postContent" label="文章内容"  width="250" align="center" :formatter="truncateTextFormatter"/>
      <el-table-column prop="userName" label="上传用户"  width="120" align="center"/>
      <el-table-column prop="nickName" label="用户昵称"  width="120" align="center"/>
      <el-table-column prop="createdAt" label="上传时间"  width="180" align="center" :formatter="timeHandler"/>
      <el-table-column  label="操作" width="240" align="center" v-slot="scope">
        <el-button type="default" @click="postView(scope.row['postId'])" plain>查看</el-button>
        <el-button type="default" @click="redirectToPostComment(scope.row['postId'],scope.row['postTitle'])" plain>查看评论</el-button>
      </el-table-column>
      <el-table-column prop="postVisibility" label="状态"  width="100" align="center"/>
      <el-table-column label="是否合规" align="center"  width="205" v-slot="{ row }">
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

    <el-drawer v-model="drawer" :title="post['postTitle']" :direction="'ltr'">
      <span v-html="post['postContent']"></span>
    </el-drawer>

  </el-card>
</template>

<style lang="scss" scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>