<script setup lang="ts">

import {timeHandler} from "@/utils/timeHandler";
import {useRoute, useRouter} from "vue-router";
import {reactive, ref,watch} from "vue";
import {ElMessage} from "element-plus";
import {
  commentViewApi,
  complianceCommentApi, getAllPostCommentsApi,
  getCommentsApi,
  getCommentStatusForComplianceOptionApi,
  irregularityCommentApi
} from "@/api/commen";
import {baseUrl} from "@/utils/request";
import {truncateText} from "@/utils/textHandler";
import {getPostTitleApi} from "@/api/post";

const route = useRoute(); //解析路由
/**
 * 表单数据---查询数据
 */
const tableData = ref([])
/**
 * 评论状态选择器
 */
const commentStatusForComplianceOptions = ref([])
const queryForm = reactive({
  pageNum:1,
  pageSize:7,
  total:1,
  data:{
    postId:'',
    userName:'',
    nickName:'',
    commentContent:'',
    statusForCompliance:null,
  }
})
/**
 * 加载数据
 */
const onLoad = async() => {
  try {
    const res = await getCommentStatusForComplianceOptionApi();
    commentStatusForComplianceOptions.value = res.data.map(option => {
      return {
        label: translateStatus(option),
        value: option
      };
    });
    await getAllPostCommentsApi(queryForm).then((res) => {
      tableData.value = res.data
      console.log(res.data)
    })
  } catch (e) {
    ElMessage.error(e);
  }
}
onLoad()

/**
 * 获取图片
 */
const circleUrl = 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png' // 不能正常显示图片时显示该图片
/**
 * 改变页数
 * @param size
 */
const handleSizeChange = async (size:number) => {
  try {
    const queryParams = {
      pageNum:queryForm.pageNum,
      pageSize:size,
      data:queryForm.data
    }
    await getAllPostCommentsApi(queryParams,startTime.value,endTime.value).then((res) => {
      tableData.value = res.data
    })
  } catch (e) {
    ElMessage.error("改变每页数量错误："+e)
  }
}
/**
 * 改变大小
 * @param num
 */
const handleCurrentChange = async(num:number) => {
  try {
    const queryParams = {
      pageNum:num,
      pageSize:queryForm.pageSize,
      data:queryForm.data
    }
    await getAllPostCommentsApi(queryParams,startTime.value,endTime.value).then((res) => {
      tableData.value = res.data;
    })
  } catch (e) {
    ElMessage.error("改变页码错误："+e)
  }
}
/**
 * 将英文是否合规状态转换为中文状态的函数
 * @param status
 * @returns {*|string}
 */
const translateStatus = (status) => {
  switch (status) {
    case 'COMPLIANCE':
      return '合规';
    case 'IRREGULARITY':
      return '不合规';
    default:
      return status;
  }
};

/**
 * 查询该帖子评论
 * @returns {Promise<void>}
 */
const onQuery = async() => {
  await getAllPostCommentsApi(queryForm,startTime.value,endTime.value).then((res) => {
    tableData.value = res.data
    console.log(tableData.value)
  })
}
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
const router = useRouter(); //解析路由跳转
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
 * 帖子的合规不合规操作
 * @param act
 * @param commentId
 */
const handleChange = async (act: "COMPLIANCE" | "IRREGULARITY", commentId: number) => {
  const actions = {
    COMPLIANCE: { msg: '启用', fn: complianceCommentApi },
    IRREGULARITY: { msg: '禁用', fn: irregularityCommentApi },
  };
  const action = actions[act]
  if (action) {
    try {
      const data = await action.fn(commentId);
      if (data["code"] === '200') {
        ElMessage.success(`${action.msg}评论 ${commentId} 成功`);
      } else {
        ElMessage.error(`${action.msg}评论 ${commentId} 失败`);
        throw new Error(`${action.msg}评论 ${commentId} 失败`);
      }
    } catch (error) {
      console.error(error);
    }
  } else {
    console.error(`未知操作: ${act}`);
  }
};
/**
 * 文章视图
 */
const drawer = ref(false)
const comment = ref([])
const commentView = async (commentId: number) => {
  try {
    await commentViewApi(commentId).then((res) => {
      drawer.value = true
      comment.value = res.data
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
/**
 * 文本截断
 * @param row
 */
const truncateTextFormatter = (row: any) => {
  return truncateText(row.commentContent, 5);
};
const truncateTextFormatterForTitle = (row: any) => {
  return truncateText(row["postTitle"], 5);
};
const handleStatusChange = (value) => {
  // 如果选项的值为空字符串，则将其设置为 null
  if (value === '') {
    queryForm.data.statusForCompliance = null;
  }
}
</script>

<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header" style="margin-top: 10px;width: 1800px">
        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
          <div>
            <el-form-item label="用户名">
              <el-input v-model="queryForm.data.userName" placeholder="请输入用户名" clearable @keyup.enter="onQuery"/>
            </el-form-item>
            <el-form-item label="昵称">
              <el-input v-model="queryForm.data.nickName" placeholder="请输入用户昵称" clearable @keyup.enter="onQuery"/>
            </el-form-item>
            <el-form-item label="帖子ID">
              <el-input v-model="queryForm.data.postId" placeholder="请输入帖子Id" clearable @keyup.enter="onQuery"/>
            </el-form-item>
            <el-form-item label="内容">
              <el-input v-model="queryForm.data.commentContent" placeholder="请输入帖子内容" clearable @keyup.enter="onQuery"/>
            </el-form-item>
          </div>
          <div>
            <el-form-item label="帖子状态" @keyup.enter="onQuery"  style="width: 250px">
              <el-select
                  v-model="queryForm.data.statusForCompliance"
                  placeholder="NULL"
                  clearable
                  @change="handleStatusChange"
              >
                <el-option
                    v-for="option in commentStatusForComplianceOptions"
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

    <el-table :data="tableData['records']" border style="width: 100%">
      <!-- 自动递增的行ID列 -->
      <el-table-column type="index" label="行id"  width="100" align="center"/>
      <el-table-column prop="commentId" label="评论Id" width="100" align="center"/>
      <el-table-column label="头像"  width="100" align="center" v-slot="{ row }">
        <el-avatar :size="50" :src="row['userHead'] || circleUrl" />
      </el-table-column>
      <el-table-column prop="userId" label="用户Id"  width="100" align="center"/>
      <el-table-column prop="userName" label="用户名"  width="130" align="center" v-slot="{ row }"/>
      <el-table-column prop="nickName" label="用户昵称"  width="130" align="center"/>
      <el-table-column prop="createdAt" label="上传时间" align="center" width="200" :formatter="timeHandler"/>
      <el-table-column prop="commentContent" label="评论内容" width="150" align="center"  :formatter="truncateTextFormatter"/>
      <el-table-column prop="parentCommentId" label="父评论Id" width="100" align="center"/>
      <!--      status_for_user-->
      <el-table-column prop="postId" label="帖子Id" width="100" align="center"/>
      <el-table-column prop="postTitle" label="帖子标题" width="130" align="center" :formatter="truncateTextFormatterForTitle"/>
      <el-table-column  label="操作" width="115" align="center" v-slot="scope">
        <el-button type="default" @click="commentView(scope.row['commentId'])" plain>查看</el-button>
      </el-table-column>
      <el-table-column label="是否合规" align="center"  width="200" v-slot="{ row }">
        <el-switch
            v-model="row['statusForCompliance']"
            class="mb-2"
            active-value="COMPLIANCE"
            inactive-value="IRREGULARITY"
            active-text="合规"
            inactive-text="不合规"
            @change="handleChange($event,row['commentId'])"
        />
      </el-table-column>

    </el-table>

    <el-pagination
        v-model:current-page="queryForm.pageNum"
        v-model:page-size="queryForm.pageSize"
        :page-sizes="[5, 7, 10, 20]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="tableData['total']"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        style="margin-top: 20px"
    />

    <el-drawer v-model="drawer" :title="comment['commentId']" :direction="'ltr'">
      <span >{{comment['commentContent']}}</span>
    </el-drawer>

  </el-card>
</template>

<style scoped lang="scss">
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

</style>