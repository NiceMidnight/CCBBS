<script setup>
import {useRoute, useRouter} from "vue-router";
import { ref, onMounted,computed,reactive,watch } from "vue";
import {timeHandler, timeHandlerSimplify} from "../../utils/timeHandler";
import {Check, Delete, Plus} from "@element-plus/icons-vue";
import {getPostByPostIdApi, getTopPostApi, increaseViewCountApi} from "../../api/post";
import {baseUrl} from "../../utils/request";
import {getOtherUserInfoByIdApi, getUserIdApi} from "../../api/login";
import {cancelFollowApi, followApi, getFollowStatusApi} from "../../api/follow";
import {ElMessage, ElMessageBox, ElNotification} from "element-plus";
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import {cancelALikeApi, getLikesForPostStatusApi, giveALikeApi} from "../../api/likeForPost";
import {cancelADislikeApi, getDislikesForPostStatusApi, giveADislikeApi} from "../../api/dislikeForPost";
import {
  deletePersonalCommentApi,
  getLatestCommentsApi,
  getTopCommentsApi,
  postACommentApi,
  submitAReplyApi
} from "../../api/comment";
import Comment from './CommentComponent.vue';

const route = useRoute();
let postId = Number(route.params.id);//帖子id
const post = ref(); //帖子信息
const userHeadUrls = ref({}); // 存储用户头像的对象
const userInfo = ref()  //用户信息
const isFollow = ref(false) //是否关注
const showUnfollow = ref(false) //鼠标停留状态
const commentData = ref([]) //评论数据
const userId = ref() //本人用户id
const topPost = ref()
const router = useRouter(); // 解析router
const redirectToPost =  (postTitle,postId) => {
  // console.log(postId)
  // console.log(postTitle)
  increaseViewCountApi(postId);
  router.push({ name: 'Post', params: { title:postTitle,id: postId } });
}
watch(() => route.params.id, (newId, oldId) => {
  postId = Number(newId); // 更新 postId 的值
  console.log(postId)
  loadData(postId.value); // 调用加载数据的方法
});
const loadData = async () => {
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
  /**
   * 获取最热评论
   */
  await getTopCommentsApi(postId).then((res) => {
    commentData.value = res.data
  })
  /**
   * 获取本人用户id
   */
  await getUserIdApi().then((res) => {
    userId.value = res.data
  })
  /**
   * 获取热点帖子
   */
  await getTopPostApi().then((res) => {
    topPost.value = res.data
  })
}
onMounted( () => {
  try
  {
    loadData()
  } catch (error) {
    console.error("钩子错误信息:", error);
  }
});

/**
 * 重新获取最热评论
 * @returns {Promise<void>}
 */
const reloadTopComments = async () => {
  await getTopCommentsApi(postId).then((res) => {
    commentData.value = res.data;
  });
};
/**
 * 重新获取最新评论
 * @returns {Promise<void>}
 */
const reloadLatestComments = async () => {
  await getLatestCommentsApi(postId).then((res) => {
    commentData.value = res.data;
  });
};

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
const cancelADislike = async () => {
  await cancelADislikeApi(postId).then((res) => {
    if (res["code"] === '200')
    {
      isDislike.value = false
      post.value.dislikeCount = res.data
    }
  })
}
/**
 * 未踩踩
 */
const giveADislike = async () => {
  await giveADislikeApi(postId).then((res) => {
    if (res["code"] === '200')
    {
      isDislike.value = true
      post.value.dislikeCount = res.data
    }
  })
}
/**
 * 发布评论
 */
const comment = ref()  //评论内容
const postACommentData = reactive({
  postId: 0,
  commentContent:'',
  parentCommentId:''
})
const postAComment = async () => {
  postACommentData.postId = postId
  if (postACommentData.commentContent != null && postACommentData.commentContent.trim())
  {
    await postACommentApi(postACommentData).then((res) => {
      if (res["code"] === '200')
      {
        if (isTopComments)
        {
          reloadTopComments();
        }
        else reloadLatestComments()
        postACommentData.commentContent = ''
        postACommentData.parentCommentId = ''
        ElMessage.success(res["msg"])
      } else ElMessage.error(res["msg"])
    })
  }
  else ElMessage.info("请输入评论内容再提交")
}
computed(() => {
  if (!comment.value) return 2; // 如果 comment 未定义或为空，则默认为1行
  const maxLengthPerRow = 50; // 每行最大字符数
  const extraRowCount = Math.ceil((comment.value.length - maxLengthPerRow) / maxLengthPerRow); // 计算超出部分的行数
  return Math.min(5, 2 + extraRowCount); // 最多显示5行，但超出部分每50个字符增加1行
});
const defaultComments = ['发表你的看法吧！', '你渴望power吗，评论获取power吧。', '写得很好，继续加油！','你猜我的评论在等待谁？']; // 默认评论数组
/**
 * 设置评论框占位符
 * @type {ComputedRef<string>}
 */
const placeholderText = computed(() => {
  // 在页面加载时随机选择默认评论作为占位符内容
  const randomIndex = Math.floor(Math.random() * defaultComments.length);
  return defaultComments[randomIndex];
})
/**
 * 最热评论
 */
const isTopComments = ref(true) //显示最热
const getTopComments = async () => {
  await getTopCommentsApi(postId).then((res) => {
    isTopComments.value = true
    isLatestComments.value = false
    commentData.value = res.data
  })
  // console.log('点击最热')
}
/**
 * 最新评论
 */
const isLatestComments = ref(false) //显示最新
const getLatestComments = async () => {
  await getLatestCommentsApi(postId).then((res) => {
    isLatestComments.value = true
    isTopComments.value = false
    commentData.value = res.data
  })
  // console.log('点击最新')
}

const showReply = ref(false);  // 控制回复框显示状态的变量
const replyTextPlaceholder = ref('回复给@')  //回复框占位符
const replyData = reactive({  //回复评论数据
  postId: '',
  commentContent: '',
  parentCommentId: ''
})
/**
 * 回复框逻辑
 * @param comment
 */
const showReplyBox = (comment) => {
  replyData.parentCommentId = comment.commentId;
  replyData.postId = comment.postId;
  replyTextPlaceholder.value = '回复给@' + comment.nickName;
  showReply.value = true;
};

/**
 * 提交回复评论
 * @type {Ref<UnwrapRef<boolean>>}
 */
const postAReply = async () => {
  if (replyData.commentContent != null && replyData.commentContent.trim())
  {
    await submitAReplyApi(replyData).then((res) => {
      // 提交后重置回复框状态
      if (res["code"] === '200') {
        if (isTopComments)
        {
          reloadTopComments();
        }
        else reloadLatestComments()
        showReply.value = false;
        replyData.postId = ''
        replyData.commentContent =''
        replyData.parentCommentId = ''
        ElMessage.success(res["msg"])
      } else ElMessage.info(res["msg"])
    })
  } else ElMessage.info("请输入内容再提交回复")
};
/**
 * 取消回复按钮
 * @returns {Promise<void>}
 */
const cancelReply = async () => {
  showReply.value = false;
  replyData.postId = ''
  replyData.commentContent =''
  replyData.parentCommentId = ''
}
/**
 * 删除个人评论
 * @param commentId
 * @returns {Promise<void>}
 */
const deleteComment = async (commentId) => {
  ElMessageBox.confirm(
      '是否删除个人评论?',
      '删除评论',
      {
        confirmButtonText: '是',
        cancelButtonText: '否',
        type: 'warning',
      }
  )
      .then(async () => {
        await deletePersonalCommentApi(commentId,postId).then((res) => {
          if (res["code"] === '200') {
            ElMessage.success('删除评论成功')
            if (isTopComments)
            {
              getTopComments()
            } else getLatestComments()
          } else ElMessage.info("删除个人评论失败")
        })
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '取消删除个人评论',
        })
      })
}
/**
 * 优化文本显示
 * @param postContent
 * @returns {*|string}
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
              <el-button type="success" plain round
                         @mouseover="showUnfollow = true"
                         @mouseleave="showUnfollow = false"
                         @click="cancelFollow(post.userId)"
              >
                {{ showUnfollow ? '取消关注' : '已关注' }}</el-button>
            </template>

          </div>

        </div>
        <el-divider style="margin: 0.6rem"/>
        <div class="title">{{ post.postTitle }}</div>
        <div class="content" v-html="post.postContent"></div>

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
          <el-button type="primary" plain class="like-button" @click="cancelADislike" v-if="isDislike">
            <span class="like-icon-container">
              <font-awesome-icon :icon="['fas', 'thumbs-down']" />
            </span>
            {{ post.dislikeCount }}
          </el-button>
          <el-button type="primary" plain class="like-button" @click="giveADislike"  v-else>
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
      <el-divider content-position="left" style="margin-top: 0.6rem;margin-bottom: 0.6rem">欢迎评论留言</el-divider>
      <!-- 评论区 -->
      <div class="postComments">
        <div class="flex justify-space-between mb-4 flex-wrap gap-4">
          <el-button
              key='primary'
              type='primary'
              text
              v-if="isTopComments"
          >最热</el-button>
          <el-button
              key='info'
              type='info'
              text
              v-else
              @click="getTopComments"
          >最热</el-button>

          <el-divider direction="vertical" />

          <el-button
              key='primary'
              type='primary'
              text
              v-if="isLatestComments"
          >最新</el-button>
          <el-button
              key='info'
              type='info'
              text
              v-else
              @click="getLatestComments"
          >最新</el-button>
        </div>
        <el-form style="margin-top: 1rem">
          <el-form-item label="发表评论">
            <el-input
                v-model="postACommentData.commentContent"
                autosize
                type="textarea"
                :placeholder="placeholderText"
                :rows="3"
            />
          </el-form-item>
          <el-form-item style="margin-left: 4rem">
            <el-button round><font-awesome-icon :icon="['far', 'image']" /></el-button>
            <el-button @click="postAComment" type="primary" class="publish-button">发布</el-button>
          </el-form-item>
        </el-form>

        <div v-if="commentData.length > 0">
          <div v-for="comment in commentData" :key="comment.commentId">
            <div style="border-radius: 1rem;padding: 0.5rem;">
              <div style="display: flex;align-items: center;">
                <el-avatar
                    :size="50"
                    :src="comment.userHead ? `${baseUrl}/${comment.userHead}` : 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'"
                />
                <div style="margin-left: 0.2rem">
                  <div style="display: flex;align-items: center; ">
                    <div class="comment-nickName" > {{ comment.nickName }}</div>
                    <div style="margin-left: 1rem">{{timeHandlerSimplify(null,null,comment.createdAt)}}</div>
                  </div>
                  <div style="display: flex;align-items: center;margin-top: 0.2rem;margin-bottom: 0.2rem">
                    <div class="comment-content" >{{ comment.commentContent }}</div>
                    <div style="margin-left: 1rem" ><el-button round @click="showReplyBox(comment)">回复</el-button></div>
                    <div style="margin-left: 1rem" v-if="comment.userId === userId ">
                      <el-button type="danger" :icon="Delete" @click="deleteComment(comment.commentId)" circle/>
                    </div>
                  </div>
                </div>
              </div>
              <!-- 渲染子评论 -->
              <div v-if="comment.childCommentsList" style="margin-left: 3rem">
                <Comment
                    v-for="childComment in comment.childCommentsList"
                    :key="childComment.commentId"
                    :commentData="childComment"
                    :nickName="comment.nickName"
                    @reply="showReplyBox"
                    @delete="deleteComment"
                />
              </div>
            </div>
            <el-divider />
          </div>
          <div style="margin:5rem"></div>
          <!-- 条件渲染回复框 -->
          <div v-if="showReply" class="reply-comment">
            <el-input
                v-model="replyData.commentContent"
                autosize
                type="textarea"
                :placeholder="replyTextPlaceholder"
            />
            <el-button type="success" :icon="Check" circle @click="postAReply" style="margin-left: 2rem"/>
            <el-button type="danger" :icon="Delete" circle @click="cancelReply" style="margin-left: 1rem"/>
          </div>
        </div>
                <div v-else style="
                justify-content: center;
                display: flex;-webkit-text-stroke: 1px #2f8bc9;
                -webkit-text-fill-color : transparent;
                font-size: 1.5rem;
                color:#c03232;">
                  还没有评论呢，快来一起畅所欲言吧......
                </div>
      </div>
    </div>
    <!-- 右侧容器 -->
    <!-- 右侧容器容器 -->
    <div class="right-container-container">
      <!-- 右侧容器 -->
      <div class="right-container">
        <div class="title"><font-awesome-icon :icon="['fas', 'fire']" style="margin-right: 1rem;justify-content: center;"/>实时热点</div>
        <div v-for="post in topPost" :key="post.postId" @click="redirectToPost( post.postTitle, post.postId)" class="top-post" >
          <div class="font3">{{post.postTitle}}</div>
          <div class="font1" v-html="formattedPostContent(post.postContent)"></div>
          <div >{{post.viewCount}}次浏览</div>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.top-post {
  border-radius: 0.5rem;
  padding: 0.5rem;

  &:hover {
    background-color: #cfe9ff;
    cursor: pointer; // 让鼠标在悬停时显示为手型
  }
}
.font1{
  text-shadow: 1px 1px black, -1px -1px black, 1px -1px black, -1px 1px black;
  font-size: 1rem;
  color:#fff;
  padding: 0.3rem;
}
.font2 {
  font-size: 1rem;
  color:#fefefe;
  text-shadow:0px 1px 0px #c0c0c0,
  0px 2px 0px #b0b0b0,
  0px 3px 0px #a0a0a0,
  0px 4px 0px #909090,
  0px 5px 10px rgba(0, 0, 0, .9);
}
.font3{
  font-size: 1.2rem;
  background: linear-gradient(to right, red, blue);
  -webkit-background-clip: text;
  color: transparent;
}
.reply-comment {
  display: flex;
  position: fixed; /* 设置为固定定位 */
  bottom: 1rem; /* 与右侧容器相同的垂直位置 */
  align-items: center;
  width: 38%; /* 可以根据需要调整宽度 */
  background-color: white; /* 设置背景颜色为白色 */
  padding: 20px; /* 可以根据需要调整内边距 */
  border-radius: 20px; /* 添加圆角 */
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* 添加阴影效果，增加对比度 */
}
.comment-nickName {
  padding: 0.2rem;
  color: #FB7299;
  font-size: large
}
.comment-content {
  padding: 0.2rem;
  font-size: 1.1rem;
  font-family: "微软雅黑 Light", serif;
}
.postComments {
  padding: 10px;
  border-radius: 10px;
  margin: 10px;
}
.publish-button {
  margin-left: auto; /* 将按钮置于右侧 */
  display: block; /* 使按钮独占一行 */
}

.new-comment {
  margin-top: 20px;
}
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

