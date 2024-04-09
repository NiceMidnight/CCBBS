<script setup lang="ts">
import {useRoute, useRouter} from "vue-router";
import {getOtherUserInfoByIdApi} from "@/api/login";
import {reactive, ref} from "vue";
import {getPostByUserIdApi, getPostTopicApi, increaseViewCountApi} from "@/api/post";
import {ElMessage} from "element-plus";
import {timeHandlerSimplify} from "../../utils/timeHandler";
import {baseUrl} from "@/utils/request";

const route = useRoute();
let userId = Number(route.params.userId);//用户id
const circleUrl = "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"; // 设置头像地址
const userInfo = ref(null)  //用户信息
const topics = ref([]); //主题内容
const posts = ref([])
const queryForm = reactive({
  pageNum:1,
  pageSize:5,
  total:1,
  data:{
    postTitle:'',
    topicId:0,
  }
})
const onLoad = async () => {
  await getOtherUserInfoByIdApi(userId).then((res) => {
    // console.log(res.data)
    userInfo.value = res.data
  })
  await getPostTopicApi().then((res) => {
    topics.value = res.data
    // console.log(res.data)
  })
  await getPostByUserIdApi(queryForm,userId).then((res) => {
    posts.value = res.data
    queryForm.total = res.data.total
    // console.log(res.data)
  })
}
onLoad()
/**
 * 选择主题获取对应帖子
 */
const selectTopic = async () => {
  await getPostByUserIdApi(queryForm,userId).then((res) => {
    posts.value = res.data
    queryForm.total = res.data.total
    // console.log(res.data)
  })
}
/**
 * 帖子显示处理
 * @param postContent
 */
const formattedPostContent = (postContent) =>{
  // 将获取到的数据中的图片标签的宽度设置为10%
  const truncatedContent = postContent.replace(/<img/g, '<img style="width: 5%;"');
  if (postContent.length > 20) {
    return truncatedContent.slice(0,20) + '...';
  } else {
    return truncatedContent;
  }
}
/**
 * 改变页码
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
    await getPostByUserIdApi(queryParams,userId).then((res) => {
      posts.value = res.data
      queryForm.total = res.data.total
    })
  } catch (e) {
    ElMessage.error(e)
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
    await getPostByUserIdApi(queryParams,userId).then((res) => {
      posts.value = res.data
      queryForm.total = res.data.total
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
/**
 * 链接跳转
 */
const router = useRouter(); // 解析router
const redirectToPost = (postTitle,postId) => {
  increaseViewCountApi(postId);
  router.push({ name: 'Post', params: { title:postTitle,id: postId } });
};
</script>

<template>
  <div class="page-container" v-if="userInfo">
    <div class="head-container">

      <div class="first-head" >
        <div class="left">
          <el-avatar :size="'large'" :src=" userInfo['userHead']" v-if="userInfo['userHead']"/>
          <el-avatar :size="'large'" :src="circleUrl" v-else/>
        </div>
        <div class="right">
          <div style="font-weight: bolder">{{userInfo['nickName']}}</div>
          <div>用户名：{{ userInfo['userName'] }}</div>
        </div>
      </div>

      <div class="second-head">
        <div v-if="userInfo['userAddress']">
          地址：{{userInfo['userAddress']}}
        </div>
      </div>

    </div>
    <div class="content-container">
      <div>
        主题
        <div>
          <el-radio-group v-model="queryForm.data.topicId" @change="selectTopic">
            <el-radio :label="0">不限</el-radio>
            <el-radio v-for="topic in topics" :key="topic.topicId" :label="topic.topicId" >{{ topic['topicName'] }}</el-radio>
          </el-radio-group>
        </div>
        <el-divider content-position="left">帖子</el-divider>
      </div>

      <div v-if="posts['data'].length != 0" v-for="post in posts['data']" class="post">
        <div class="post-first">
          <el-link class="post-link" :underline="false" @click="redirectToPost( post['postTitle'], post['postId'])">
            {{ post["postTitle"] }}
          </el-link>
          <div class="content" v-html="formattedPostContent(post['postContent'])">
          </div>
          <div class="message">
            <div class="number">{{timeHandlerSimplify(null,null,post['createdAt'])}}</div>
            <el-divider direction="vertical" />
            <div class="number">{{post['viewCount']}}</div>阅读量

            <el-divider direction="vertical" />
            <div class="number">{{post['commentCount']}}</div>评论
            <el-divider direction="vertical" />
            <div class="number">{{post['likeCount']}}</div>点赞
          </div>
        </div>
        <el-divider />
      </div>

      <div v-else class="post-null">
        该用户在该主题还没有帖子哦~
      </div>
      <el-pagination
        small
        v-model:current-page="queryForm.pageNum"
        v-model:page-size="queryForm.pageSize"
        background
        layout="prev, pager, next"
        :total="queryForm.total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"/>
    </div>
  </div>
</template>


<style scoped lang="scss">
.page-container {
  display: flex;
  flex-direction: column;
  align-items: center; /* 水平居中 */
  //justify-content: center;
}

.head-container {
  margin-bottom: 20px; /* 设置头部容器底部边距 */
  background-color: rgba(241, 250, 243, 0.9);
  height: 150px; /* 占据整个视口高度 */
  width: 45%;
  padding: 1rem;
  border-radius: 1rem;

  .first-head {
    display: flex;
    align-items: center; /* 水平居中 */
    .left {

    }
    .right {
      margin-left: 10px;
    }
  }
  .second-head {
    font-family: "微软雅黑 Light", serif;
  }
}

.avatar {
  position: absolute; /* 设置绝对定位 */
  top: 0; /* 与头部容器上边对齐 */
  left: 50%; /* 水平居中 */
  transform: translateX(-50%); /* 水平居中 */
}

.content-container {
  background-color: white;
  width: 45%;
  padding: 1rem;
  border-radius: 1rem;
  align-items: center; /* 水平居中 */
  .post {
    align-content: center;
    .post-first {
      border-radius: 1rem;
      padding: 0.6rem;
      &:hover {
        border-radius: 1rem;
        padding: 0.6rem;
        background-color: #e6f7ff; /* 鼠标悬停时的蓝色背景 */
        cursor: pointer; /* 鼠标指针样式为手型 */
      }
    }
    .post-link {
      font-size: 1.2rem;
      font-weight: 900;
      text-decoration: none; /* 去除下划线 */
      transition: color 0.3s ease; /* 添加颜色过渡效果 */
    }
    .content {

    }
    .message {
      font-size: small;
      font-family: "微软雅黑 Light", serif;
      display: flex;
      align-content: center;
      .number {
        font-weight: bold;
        margin-right: 0.5rem;
      }
    }
  }
  .post-null {
    font-size: 1.3rem;
    background: linear-gradient(to right, red, blue);
    -webkit-background-clip: text;
    color: transparent;
    padding: 1rem;
  }
}

@media screen and (max-width: 1000px) {
  /* 在小屏幕下修改样式 */
  .head-container {
    font-size: 10px; /* 缩小字体大小 */
    width: 90%;
  }

  .content-container {
    //font-size: 10px; /* 缩小字体大小 */
    width: 90%;
  }
}
</style>
