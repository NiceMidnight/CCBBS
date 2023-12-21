<template>
  <div id="head" class="content">
    <div class="message">
      <p>过来发帖分享内容吧</p>
    </div>
    <div class="search">
      <el-input
          v-model="postMsg"
          size="large"
          maxlength="5"
          placeholder="搜索帖子" />
      <el-button type="success" plain style="margin-left: 30px;" @click="searchPostData">查询</el-button>
      <el-button type="primary" plain @click="postAMessage">发帖</el-button>
    </div>
  </div>

  <div class="container">
    <el-row :gutter="20">
      <el-col v-for="(item, index) in postData['data']" :key="index" :span="6">
        <el-card class="square-card">
          <div class="data-content">
            <!-- 左边图片 -->
            <div class="image-container">
              <img src='src/assets/img/login.jpg' alt="Image" />
            </div>
            <!-- 右边数据项 -->
            <div class="data-container">
              <div >发布用户：{{ item["userName"] }}</div>
              <div >标题：{{ item["postTitle"] }}</div>
              <div >内容：{{ item["postContent"] }}</div>
              <div >发布时间：{{ timeHandler(null,null,item["createdAt"])  }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
  <el-dialog
      v-model="postAMessageDialogVisible"
      title="发帖框"
      width="40%"
      draggable
  >
    <div style="margin: 10px" />
    <el-form
        :label-position="'right'"
        label-width="100px"
        :model="addPostData"
        style="max-width: 700px"
    >
      <el-form-item label="上传管理员">
        <el-text class="mx-1" type="success">{{addPostData.userName}}</el-text>
      </el-form-item>
      <el-form-item label="文章标题">
        <el-input v-model="addPostData.postTitle" placeholder="请输入帖子标题"/>
      </el-form-item>
      <el-form-item label="文章内容">
        <el-input v-model="addPostData.postContent" type="textarea" :rows="12" placeholder="请输入帖子内容"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addHandleClose">取消</el-button>
        <el-button type="primary" @click="onSubmitAddPost">提交</el-button>
      </span>
    </template>
  </el-dialog>

</template>
<script setup lang="ts">
import {reactive, ref} from 'vue'
import {onMounted} from "@vue/runtime-core";
import {ElMessage, ElMessageBox, ElNotification} from "element-plus";
import {getAllPostApi} from "../../api/post";
import {useTokenStore} from "../../stores/mytoken"
import {timeHandler} from "../../utils/timeHandler";

/**
 * 发帖
 */
const {loginToken,getLoginToken} = useTokenStore()

const postAMessageDialogVisible = ref(false)
const addPostData = ref({
  userName:'',
  postTitle:'',
  postContent:'',
})
const postAMessage = () => {
  getLoginToken()
  if (loginToken) {
    postAMessageDialogVisible.value = true
  } else {
    ElNotification({
      title: '信息',
      message: '您还没有登录，没有权限发布帖子！',
      type: 'info',
    })
  }
}
const addHandleClose = () => {
  ElMessageBox.confirm('是否取消发帖，数据将清空！').then(() => {
    postAMessageDialogVisible.value = true
    Object.keys(addPostData).forEach(key => {
      addPostData[key] = ''
    })
  }).catch((e)=>{
    console.log(e)
  })
}
//提交发帖数据
const onSubmitAddPost = () => {

}

/**
 * 查询全部帖子数据
 */
//查询信息
const postMsg = ref('')
const queryForm = reactive({
  pageNum:1,
  pageSize:10,
  total:1,
  data:{
  }
})
// 表格数据
const postData = ref([])
onMounted(async () => {
  try {
    await getAllPostApi(queryForm,postMsg.value).then((res) => {
      postData.value = res.data
      console.log(postData.value)
      console.log(res)
    })
  } catch (e) {
    ElMessage.error(e)
  }
})
// 查询帖子数据
const searchPostData= async () => {
  try {
    await getAllPostApi(queryForm,postMsg.value).then((res) => {
      postData.value = res.data
      console.log(res)
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
</script>

<style>
.content {
  padding: 20px;
  display: flex;
  align-items: center;
  flex-direction: column; /* 竖直方向布局 */

  .message {
    display: flex;
    align-items: center;
    justify-content: flex-start; /* 设置内容靠左对齐 */
    margin-bottom: 20px; /* 添加下边距 */
  }

  .message p {
    color: #0ea67b;
    font-size: 30px;
  }

  .search {
    display: flex;
    align-items: center;
    //background-color: rgba(255, 255, 255, 0.65); /* 为了可视化，添加一个背景色   0.65*/
    el-input {
      margin-right: 10px; /* 调整搜索框与按钮之间的间距 */
    }
  }
}

.container {
  text-align: center; /* 居中对齐 */

}

.square-card {
  overflow: hidden; /* 隐藏卡片内的溢出部分 */
  margin: 20px; /* 设置卡片之间的间距 */
  background-color: rgba(255, 255, 255, 0.7); /* 背景颜色透明度，可以调整最后一个参数 */
  border-radius: 8px; /* 圆角边框 */
  max-width: 400px; /* 卡片最大宽度，根据实际需要调整 */
}

.square-card::-webkit-scrollbar {
  display: none; /* 隐藏滚动条 */
}

.data-content {
  display: flex; /* 使用 Flexbox 布局 */
  flex-wrap: wrap; /* 当屏幕宽度较小时，内容换行 */
}
.data-container {
  flex: 2; /* 调整数据容器宽度，根据实际需要调整 */
  text-align: left;
}
.image-container {
  flex: 1; /* 图片容器占据剩余空间 */
  margin-right: 10px; /* 右边距，可根据需要调整 */
}

.image-container img {
  max-width: 100%; /* 图片宽度不超过容器 */
  height: auto; /* 自适应高度 */
}

.data-container {
  flex: 1; /* 数据容器占据剩余空间 */
  text-align: left; /* 左对齐 */
}
/* 媒体查询，调整样式以适应小屏幕设备 */
@media screen and (max-width: 768px) {
  .square-card {
    max-width: 100%; /* 卡片宽度充满屏幕 */
  }

  .data-content {
    flex-direction: column; /* 垂直方向布局 */
  }

  .image-container,
  .data-container {
    margin-right: 0; /* 移除右边距 */
  }
}
</style>