<script setup>
import { useRoute } from "vue-router";
import { ref, onMounted } from "vue";
import { timeHandler } from "../../utils/timeHandler";
import {Plus, View} from "@element-plus/icons-vue";
import {getPostByPostIdApi} from "../../api/post";
import {baseUrl} from "../../utils/request";
import {getOtherUserInfoByIdApi} from "../../api/login";
import {cancelFollowApi, followApi, getFollowStatusApi} from "../../api/follow";
import {ElNotification} from "element-plus";
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import {cancelALikeApi, getLikesForPostStatusApi, giveALikeApi} from "../../api/likeForPost";
import {getDislikesForPostStatusApi} from "../../api/dislikeForPost";
const route = useRoute();
const postId = Number(route.params.id);//帖子id
const post = ref(); //帖子信息
const userHeadUrls = ref({}); // 存储用户头像的对象
const userInfo = ref()  //用户信息
const isFollow = ref(false) //是否关注
const showUnfollow = ref(false) //鼠标停留状态

onMounted(async () => {
  try
  {
    /**
     * 获取帖子数据
     * @type {axios.AxiosResponse<any>}
     */
    const response = await getPostByPostIdApi(postId);
    post.value = response.data;
    // console.log(response.data);
    /**
     * 获取对当前用户基本信息
     */
    await getOtherUserInfoByIdApi(post.value["userId"]).then((res) =>
    {
      // console.log(res)
      userInfo.value = res.data
      userHeadUrls.value =  `${baseUrl}/${res.data.userHead}`
    })
    /**
     * 获取用户关注状态
     */
    await getFollowStatusApi(post.value["userId"]).then((res) =>
    {
      isFollow.value = res.data
    })
    /**
     * 获取帖子点赞状态
     */
    await getLikesForPostStatusApi(postId).then((res) => {
      isLike.value = res.data
    })
    /**
     * 获取帖子踩状态
     */
    await getDislikesForPostStatusApi(postId).then((res) => {
      isDislike.value = res.data
    })
  } catch (error) {
    console.error("钩子错误信息:", error);
  }
});
/**
 * 关注用户
 * @param followingId
 * @returns {Promise<void>}
 */
const follow = async (followingId) => {
  try {
    await followApi(followingId).then((res) => {
      if (res["code"] === "200") {
        ElNotification({
          title: '关注状态',
          message: "对用户"+followingId+ res["msg"],
          type: 'success',
        })
        isFollow.value = true
      } else ElNotification({
        title: '关注状态',
        message: "对用户"+followingId+res["msg"],
        type: 'info',
      })
    })
  } catch (e) {
    ElNotification({
      title: '关注状态',
      message: "对用户"+followingId+"关注操作错误",
      type: 'error',
    })
  }
}
/**
 * 取消关注用户
 * @param followingId
 * @returns {Promise<void>}
 */
const cancelFollow = async (followingId) => {
  try {
    await cancelFollowApi(followingId).then((res) => {
      if (res["code"] === "200") {
        ElNotification({
          title: '关注状态',
          message: "对用户"+followingId+ res["msg"],
          type: 'success',
        })
        isFollow.value = false
      } else ElNotification({
        title: '关注状态',
        message: "对用户"+followingId+res["msg"],
        type: 'info',
      })
    })
  } catch (e) {
    ElNotification({
      title: '关注状态',
      message: "对用户"+followingId+"关注操作错误",
      type: 'error',
    })
  }
}

const isLike = ref(false) //点赞true/false
const isDislike = ref(false) //踩true/false

/**
 * 已关注取消关注
 * @param postId
 */
const cancelALike = async () => {
  await cancelALikeApi(postId).then((res) => {
    if (res["code"] === '200') {
      isLike.value = true
      post.value.likeCount = res.data
    }
  })
  isLike.value = false
}
/**
 * 未关注关注
 * @param postId
 */
const giveALike = async () => {
  await giveALikeApi(postId).then((res) => {
    if (res["code"] === '200') {
      isLike.value = true
      post.value.likeCount = res.data
    }
  })
}
/**
 * 已踩取消踩
 */
const cancelAUnlike = () => {
  isDislike.value = false
}
/**
 * 未踩踩
 */
const giveAUnlike = () => {
  isDislike.value = true
}
</script>


<template>
  <div class="outer-container">
    <div class="left-container" v-if="post">
      <!-- 帖子信息 -->
      <div id="postMessage" class="postMessage">
        <div class="topic">{{ post.topicName }}</div>
<!--        <div class="topic"><el-tag type='success' size='large' style="font-size: 20px">{{ post.topicName }}</el-tag></div>-->
        <div class="user-message">
          <div >
            <el-avatar :size="60" :src="userHeadUrls && typeof userHeadUrls === 'string' ? userHeadUrls :  'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" />
          </div>
          <div style="margin-left: 10px">
            <div ><b >{{ post.nickName }}</b></div>
            <div v-if="userInfo && userInfo.userPersonality">{{ userInfo["userPersonality"] }}</div>
          </div>
          <div style="margin-left: 20px">
            <template v-if="!isFollow">
              <el-button type="primary" @click="follow(post.userId)" plain round>关注<el-icon><Plus /></el-icon></el-button>
            </template>
            <template v-else-if="isFollow">
              <el-button type="primary" round
                         @mouseover="showUnfollow = true"
                         @mouseleave="showUnfollow = false"
                         @click="cancelFollow(post.userId)"
                         plain
              >
                {{ showUnfollow ? '取消关注' : '已关注' }}</el-button>
            </template>

          </div>

        </div>
        <el-divider />
        <div class="title">{{ post.postTitle }}</div>
        <div class="content">{{ post.postContent }}</div>

        <div>
          <!-- 赞贴-->
          <el-button type="primary" plain class="like-button" @click="cancelALike" v-if="isLike">
            <span class="like-icon-container">
              <font-awesome-icon :icon="['fas', 'thumbs-up']" />
            </span>
            {{ post.likeCount }}
          </el-button>

          <el-button type="primary" plain class="like-button" @click="giveALike" v-else>
            <span class="like-icon-container">
              <font-awesome-icon :icon="['far', 'thumbs-up']"/>
            </span>
            {{ post.likeCount }}
          </el-button>

          <!-- 踩贴-->
          <el-button type="primary" plain class="like-button" @click="cancelAUnlike" v-if="isDislike">
            <span class="like-icon-container">
              <font-awesome-icon :icon="['fas', 'thumbs-down']" />
            </span>
            {{ post.dislikeCount }}
          </el-button>
          <el-button type="primary" plain class="like-button" @click="giveAUnlike"  v-else>
            <span class="like-icon-container">
              <font-awesome-icon :icon="['far', 'thumbs-down']" />
            </span>
            {{ post.dislikeCount }}
          </el-button>
        </div>

        <div class="post-message">
          <div><font-awesome-icon :icon="['far', 'pen-to-square']" style="margin-right: 5px"/>上传于{{ timeHandler(null, null, post["createdAt"]) }}</div>
          <div class="view">
            <font-awesome-icon :icon="['fas', 'book-open-reader']" style="margin-right: 5px"/>{{ post.viewCount }} 阅读量
            <el-divider direction="vertical" class="divider" />
            <font-awesome-icon :icon="['far', 'comment']" style="margin-right: 5px"/>{{ post.commentCount }} 评论数
          </div>
        </div>
      </div>
      <!-- 分隔线 -->
      <el-divider content-position="left" >欢迎评论留言</el-divider>
      <!-- 评论区 -->
      <div id="postComments">
        123
      </div>
    </div>

    <!-- 右侧容器 -->
    <!-- 右侧容器容器 -->
    <div class="right-container-container">
      <!-- 右侧容器 -->
      <div class="right-container">
        <div class="title">实时热点</div>
        <div>132</div>
        123122222222222222222222222222222
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.outer-container {
  display: flex;
  justify-content: center;
  width: 100%;
  max-width: 1200px;
  margin: 0 auto; /* 让outer-container居中 */
}

.left-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 100%;
  max-width: 800px;
  margin: 0 auto;
  background-color: white;
  border-radius: 20px;
  text-align: left;
}

.right-container-container {
  position: relative; /* 设置为相对定位，使 .right-container 参照此容器定位 */
  flex: 1; /* 让 .right-container-container 占满剩余空间 */
}

.right-container {
  position: sticky; /* 设置为粘性定位 */
  top: 50px; /* 根据需要设置距离页面顶部的距离 */
  max-width: 400px;
  background-color: white;
  border-radius: 20px;
  padding: 20px;
}

.postMessage {
  margin: 20px;
}

.topic {
  border-left: 6px solid #2196F3;
  background: white;
  padding: 14px;
  font-size: 2rem;
  letter-spacing: 1px;
  font-family: STXingkai, serif;
}

.user-message {
  display: flex;
  align-items: center;
  overflow-y: auto;
  padding: 1rem;
  border-radius: 10px;
  font-size: 1rem;
  font-weight: lighter;
  overflow-wrap: break-word;
  word-wrap: break-word;
  hyphens: auto;
  width: 98%;
  white-space: pre-line;
  margin: 5px;
  .el-avatar {
    border: 2px solid #2196F3; // 添加边框样式
  }
}
.user-message, .post-message {
  font-size: 1rem;
  color: #333; // 根据需要修改文字颜色
}
.post-message {
  margin-top: 10px;
  display: flex;
  align-items: center;
  overflow-y: auto;
  font-weight: lighter;
  overflow-wrap: break-word;
  word-wrap: break-word;
  hyphens: auto;
  width: 98%;
  white-space: pre-line;
}
.post-message div {
  margin-bottom: 8px;  /* 调整行间距 */
}
.el-icon {
  margin-left: 4px;
}
.like-button {

}
.like-icon-container {
  display: flex;
  justify-content: flex-end;
  margin-right: 10px;
}

.view {
  display: flex;
  align-items: center;
  margin-left: auto;  /* 将剩余空间推到右侧 */
}
.divider {
  margin-left: 10px;
}
.title {
  display: flex;
  align-items: center;
  overflow-y: auto;
  padding: 1rem;
  border-radius: 10px;
  font-size: 1.2rem;
  font-weight: bolder;
  overflow-wrap: break-word;
  word-wrap: break-word;
  hyphens: auto;
  width: 98%;
  white-space: pre-line;
}
.content {
  display: flex;
  align-items: center;
  overflow-y: auto;
  padding: 1rem;
  border-radius: 10px;
  font-weight: lighter;
  overflow-wrap: break-word;
  word-wrap: break-word;
  hyphens: auto;
  width: 98%;
  white-space: pre-line;
}

.icon {
  margin-right: 10px;
}

.right-container {
  flex: 1; /* 让右侧容器占满可用空间 */
  margin-left: 50px;
  max-width: 400px;
  background-color: white; /* 设置背景颜色为黄色 */
  border-radius: 20px;
}

#postComments {
  padding: 10px;
  background-color: #f5f5f5;
  border-radius: 10px;
  margin-top: 10px;
}

@media screen and (max-width: 768px) {
  .topic {
    font-size: 1.5rem;
  }
  .content {
    font-size: 1rem;
  }
  .user-message {
    font-size: 1rem;
  }
  .post-message {
    font-size: 1rem;
  }
  .right-container {
    display: none; /* 在小屏幕下隐藏右侧容器 */
  }
}
</style>

