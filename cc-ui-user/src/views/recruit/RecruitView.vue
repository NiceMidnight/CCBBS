<script setup lang="ts">
import {getTopicForJobApi} from "@/api/topicForJob";
import {reactive, ref} from "vue";
import {addJobMessageApi, getJobMessageApi} from "@/api/job";
import {ElMessage, ElMessageBox, ElNotification} from "element-plus";
import TextEditor from "@/views/addAPost/TextEditor.vue";
import {baseUrl} from "@/utils/request";
import {Document, Search} from "@element-plus/icons-vue";
import {timeHandler} from "@/utils/timeHandler";
import {truncateText} from "@/utils/textHandler";
import {useRouter} from "vue-router";
import {getUserRoleApi} from "@/api/login";
import {addAApplyRecruiterApi} from "@/api/applyRecruit";

const userRole = ref(0) //用户角色
const circleUrl = "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"; // 设置头像地址
const options = ref(null) //招聘主题
const jobData = ref(null) //招聘信息
const queryForm = reactive({  //查询信息
  pageNum:1,
  pageSize:10,
  total:1,
  data:{
    topicId:0,
    nickName:''
  }
})

const onLoad = async () => {
  try {
    await getTopicForJobApi().then((res) => {
      options.value = res.data
    })
    await getJobMessageApi(queryForm).then((res) => {
      jobData.value = res.data.data
      queryForm.total = res.data.total
      console.log(res)
    })
    if(localStorage.getItem("TokenInfo")!=null)
    {
      await getUserRoleApi().then((res) => {
        userRole.value = res.data
      })
    }

  }
  catch (e) {
    console.log(e)
  }
}
onLoad()
/**
 * 改变大小
 * @param val
 */
const handleSizeChange = async (val: number) => {
  try {
    const queryParams = {
      pageNum:queryForm.pageNum,
      pageSize:val,
      total:queryForm.total,
      data:queryForm.data
    }
    const res = await getJobMessageApi(queryParams);
    jobData.value = res.data.data
  } catch (e) {
    console.log(e)
  }
}
const handleCurrentChange = async (val: number) => {
  try {
    const queryParams = {
      pageNum:val,
      pageSize:queryForm.pageSize,
      total:queryForm.total,
      data:queryForm.data
    }
    const res = await getJobMessageApi(queryParams);
    jobData.value = res.data.data
  } catch (e) {
    console.log(e)
  }
}
/**
 * 添加招聘信息
 */
const addJobDialogVisible = ref(false)
const jobMessage = reactive({
  topicId:3,
  jobTitle:'',
  jobContent:'',
})
const addJobMessage = async () => {
  try {
    if (jobMessage.jobTitle != null && jobMessage.jobContent != null && jobMessage.jobTitle != '' && jobMessage.jobContent != '')
    {
      await addJobMessageApi(jobMessage).then((res) => {
        if (res['code'] === '200')
        {
          ElNotification({
            title: '提交招聘信息',
            message: res["msg"],
            type: 'success',
          })
          addJobDialogVisible.value = false
          jobMessage.jobTitle = ''
          jobMessage.jobContent = ''
        } else
          ElNotification({
            title: '提交招聘信息',
            message: res["msg"],
            type: 'error',
          })
      })
    }
    else ElNotification({
      title: '提交招聘信息',
      message: '招聘标题或内容不能为空',
      type: 'info',
    })
  } catch (e) {
    console.log(e)
  }
}
/**
 * 取消添加
 */
const handleClose = () => {
  ElMessageBox.confirm('是否取消添加招聘信息，数据将清空！')
      .then(() => {
        addJobDialogVisible.value = false
        Object.keys(jobMessage).forEach(key => {
          jobMessage[key] = ''
        })
      })
      .catch(() => {
        // catch error
      })
}

/**
 * 设置选中逻辑
 */
const activeIndex = ref("/recruit"); //页面加载时默认激活菜单的 index
const handleSelect = async (index: any) => {
  queryForm.data.topicId = index
  await getJobMessageApi(queryForm).then((res) => {
    jobData.value = res.data.data
    queryForm.total = res.data.total
    // console.log(res)
  })
};

/**
 * 文本截断
 * @param title
 */
const truncateTextFormatter = (title: string) => {
  return truncateText(title, 16);
};
/**
 * 解析路由
 */
const router = useRouter()

const createANewPage = () => {

}

const redirectToJob = (row: any) => {
  router.push({ name: 'JobMessage', params: { jobTitle:row['jobTitle'],jobId: row['jobId'] } });
}

const redirectToUser = (userId:any) => {
  router.push({ name: 'PersonalHomepage', params: { userId: userId } });
}

/**
 * 查询
 */
const searchJobMessage = async () => {
  await getJobMessageApi(queryForm).then((res) => {
    jobData.value = res.data.data
  })
}
/**
 * 申请招聘权限
 */
const applyForRecruitmentDialogVisible = ref(false)
const applyData = reactive({
  truthName:'',
  studentId:'',
  phone:'',
})
const handleCloseApplyForm = () => {
  ElMessageBox.confirm('是否取消申请，数据将清空！')
      .then(() => {
        applyForRecruitmentDialogVisible.value = false
        Object.keys(applyData).forEach(key => {
          applyData[key] = ''
        })
      })
      .catch(() => {
        // catch error
      })
}
const addAApplyRecruiter = async () => {
  try {
    await addAApplyRecruiterApi(applyData).then((res) => {
      if (res['code'] === '200')
      {
        ElNotification({
          title: '提交招聘权限申请',
          message: res["msg"],
          type: 'success',
        })
        applyForRecruitmentDialogVisible.value = false
      } else
        ElNotification({
          title: '提交招聘信息',
          message: res["msg"],
          type: 'error',
        })
    })
  } catch (e) {
    console.log(e)
  }
}
</script>

<template>
  <div style="display: flex;flex-direction: column;align-items: center;">
    <div class="container">
      <div class="head">
        <el-menu
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"
            :ellipsis="false"
            @select="handleSelect"
            style="border-radius: 1rem"
        >
          <el-menu-item :index="`0`">不限</el-menu-item>
          <el-menu-item
              v-for="option in options"
              :key="`${option['topicId']}`"
              :index="`${option['topicId']}`"
          >
            {{ option['topicName'] }}
          </el-menu-item>
          <div class="flex-grow" />
          <div class="right-model">
            <el-input
                v-model="queryForm.data.nickName"
                style="width: 200px"
                placeholder="昵称、用户名"
                clearable
                @keyup.enter="searchJobMessage"
            />
            <el-button :icon="Search" circle @click="searchJobMessage" style="margin-left: 1rem"/>
            <el-button type="primary" round v-if="userRole === 0" @click="applyForRecruitmentDialogVisible = true">申请招聘权限</el-button>
            <el-button type="primary" round v-else @click="addJobDialogVisible = true">添加招聘信息</el-button>
          </div>
        </el-menu>
      </div>

      <div class="body">
        <el-table
            :data="jobData"
            style="width: 100%;border-radius: 1rem;margin-top: 20px"
        >
          <el-table-column label="" width="25" align="center" v-slot="{ row }">
            <el-link type="primary" :underline="false">
              <el-icon><Document /></el-icon>
            </el-link>
          </el-table-column>

          <el-table-column  label="标题" width="245"  @click="redirectToJob" v-slot="{ row }">
            <el-link type="primary" @click="redirectToJob(row)" >
              {{truncateTextFormatter(row['jobTitle'])}}
            </el-link>
          </el-table-column>

          <el-table-column prop="topicName" label="类型" align="center" width="100" />

          <el-table-column label="头像" width="80" align="center" v-slot="{ row }">
            <el-avatar :size="50" :src="row['userHead'] || circleUrl"/>
          </el-table-column>

          <el-table-column label="作者" align="center" width="100" v-slot="{ row }">
            <el-link type="primary" @click="redirectToUser(row['userId'])">{{row['nickName']}}</el-link>
          </el-table-column>

          <el-table-column prop="viewCount" label="点击量" align="center" width="70" />

          <el-table-column prop="createdAt" label="发布时间" align="center" width="180" :formatter="timeHandler" />
        </el-table>
        <el-pagination
            v-model:current-page="queryForm.pageNum"
            v-model:page-size="queryForm.pageSize"
            background
            layout="prev, pager, next"
            :total="queryForm.total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            style="padding: 1rem"/>
      </div>
    </div>
  </div>

  <!--添加招聘信息-->
  <el-dialog
      v-model="addJobDialogVisible"
      title="添加招聘信息"
      width="60%"
      draggable
  >
    <div style="margin: 10px" />
    <el-form
        :label-position="'right'"
        label-width="100px"
        :model="jobMessage"
        style="max-width: 1200px"
    >
      <el-form-item label="更改主题">
        <el-select v-model="jobMessage.topicId" :clearable="false" placeholder="NULL">
          <el-option
              v-for="option in options"
              :key="option['topicId']"
              :value="option['topicId']"
              :label="option['topicName']"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="标题">
        <el-input v-model="jobMessage.jobTitle" placeholder="请输入文章标题" maxlength="30" show-word-limit/>
      </el-form-item>
      <el-form-item label="内容">
        <div>
          <TextEditor
              v-model="jobMessage.jobContent"
          />
        </div>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="addJobMessage">提交</el-button>
      </span>
    </template>
  </el-dialog>
  <!--添加权限-->
  <el-dialog
      v-model="applyForRecruitmentDialogVisible"
      title="申请招聘权限表格"
      width="30%"
      draggable
  >
    <div style="margin: 10px" />
    <el-form
        :label-position="'right'"
        label-width="100px"
        :model="applyData"
        style="max-width: 500px"
    >

      <el-form-item label="真实姓名">
        <el-input v-model="applyData.truthName" placeholder="请输入你的名字" maxlength="30" show-word-limit/>
      </el-form-item>

      <el-form-item label="学号">
        <el-input v-model="applyData.studentId" placeholder="请输入学号" maxlength="12" show-word-limit/>
      </el-form-item>

      <el-form-item label="电话">
        <el-input v-model="applyData.phone" placeholder="请输入电话号码" maxlength="11" show-word-limit/>
      </el-form-item>

    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleCloseApplyForm">取消</el-button>
        <el-button type="primary" @click="addAApplyRecruiter">提交</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style lang="scss" scoped>
.container {
  width: 800px;
  //background-color: #c9e4fa;
  padding: 1rem;
  border-radius: 1rem;
  height: 750px;
  .right-model {
    margin-top: 15px;
    margin-right: 15px;
  }
}
.flex-grow {
  flex-grow: 1;
}

.head {

}
.el-menu-item {
  border-radius: 1rem;
}

.body {
  background-color: white;
  border-radius: 1rem;
}

</style>