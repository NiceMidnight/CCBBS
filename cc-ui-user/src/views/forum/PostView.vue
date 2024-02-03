<template>
  <div>
    <el-container class="post-container">
      <el-aside class="left-menu">
        <div class="menu-bar"><el-icon><ChatDotRound /></el-icon>讨论区</div>
        <el-menu :default-active="postTopicData['topicId']" class="el-menu-vertical-demo" @select="handleMenuSelect">
          <el-menu-item :index="`0`"><el-icon><Flag /></el-icon>首页</el-menu-item>
          <el-menu-item
              v-for="postTopicData in postTopicData"
              :key="`${postTopicData['topicId']}`"
              :index="`${postTopicData['topicId']}`"
          >
            <el-icon><Flag /></el-icon>{{ postTopicData['topicName'] }}
          </el-menu-item>
        </el-menu>
      </el-aside>
      <!-- 右侧部分 -->
      <el-main class="right-content">
        <div class="post-title" >
          <el-icon><Position /></el-icon>标题：{{ selectedData }}
        </div>
        <div v-for="(post, index) in postData['data']" :key="index">
          <div class="content-block">
          <div>
            <el-avatar :size="50" :src="getUserHead(post['userId']) || circleUrl" />
          </div>
          <div style="margin-left: 20px">
            <el-link class="post-link" :underline="false" @click="redirectToPost( post.postTitle, post.postId,)">
              {{ post["postTitle"] }}
            </el-link>
            <div style="margin-top: 5px">{{ post['postContent'] }}</div>

            <div style="display: flex;align-items: center;">
              <el-icon><UserFilled /></el-icon>{{ post['nickName'] }}
              <div style="margin-left: 20px">
                <template v-if="post.followStatus">
                  <el-button class="follow-button" type="success" size="small" plain
                             @mouseover="showUnfollow = true"
                             @mouseleave="showUnfollow = false"
                             @click="cancelFollow(post.userId,index)"
                  >
                    {{ showUnfollow ? '取消关注' : '已关注' }}
                  </el-button>
                </template>
                <template v-else>
                  <el-button class="follow-button" type="primary" size="small" plain @click="onFollowClick(post.userId,index)">
                    关注<el-icon style="margin-left: 4px"><CirclePlus /></el-icon>
                  </el-button>
                </template>
              </div>
              <div style="margin-left: 20px">
                发布时间：{{ timeHandler(null,null,post['createdAt']) }}
              </div>
              <div style="margin-left: 20px">
                浏览量：{{ post['viewCount'] }}
              </div>
              <div style="margin-left: 20px" >
                评论数：{{ getCommentCount(post['postId']) }}
              </div>
              <div style="margin-left: 20px">
                赞：{{ post['viewCount'] }}
              </div>
            </div>

          </div>
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';
import { onMounted  } from "@vue/runtime-core";
import {getAllPostApi, getPostTopicApi, getUserHeadApi} from "../../api/post";
import {ElMessage, ElNotification} from "element-plus";
import {ChatDotRound, CirclePlus, Flag, Position, UserFilled } from "@element-plus/icons-vue";
import {timeHandler} from "../../utils/timeHandler";
import {baseUrl} from "../../utils/request";
import {getCommentCountApi} from "../../api/comment";
import {useRouter} from "vue-router";
import {cancelFollowApi, followApi, getFollowStatusApi} from "../../api/follow";

const showUnfollow = ref(false);
/**
 * 链接跳转
 */
const router = useRouter(); // 解析router
const redirectToPost = (postTitle,postId) => {
  router.push({ name: 'Post', params: { title:postTitle,id: postId } });
};
const selectedData = ref('首页'); //首页菜单
const userHeadUrls = ref({}); // 存储用户头像的对象
/**
 * 菜单选择
 * @param option
 */
const selectOption = (option: number) => {
  const selectedTopic = postTopicData.value.find(topic => topic.topicId === option);
  selectedData.value = selectedTopic ? selectedTopic["topicName"] : '首页'; // 如果找到对应的 topic，就设置为 topicName，否则设置为空字符串

};
const handleMenuSelect = async (index: string) => {
  queryForm.data.topicId = Number(index)
  await getAllPostApi(queryForm, postMsg.value).then(async (res) => {
    postData.value = res.data;
  });
  await Promise.all(postData.value["data"].map(async (post) => {
    // 获取关注状态
    post.followStatus = await getFollowStatus(post.userId);
    // ... 省略其他逻辑
    console.log(postData)
  }));
  selectOption(Number(index));
};
/**
 * 查询表格
 */
const postMsg = ref('');
const queryForm = reactive({
  pageNum: 1,
  pageSize: 10,
  total: 1,
  data: {
    topicId: 0
  },
});
// 表格数据
const postData = ref([]);
const postTopicData = ref([]);
const commentCount = ref({})
onMounted(async () => {
  try {
    await getPostTopicApi().then((res) => {
      postTopicData.value = res.data;
    });
    await getAllPostApi(queryForm, postMsg.value).then((res) => {
      postData.value = res.data;
    });
    await Promise.all(postData.value["data"].map(async (post) => {
      if (!userHeadUrls.value[post.userId]) {
        const userHead = await getUserHeadApi(post.userId);
        userHeadUrls.value[post.userId] = `${baseUrl}/${userHead.data}`;
      }
    }));
    await Promise.all(postData.value["data"].map(async (post) => {
      if (!commentCount.value[post.postId]) {
        const count = await getCommentCountApi(post.postId);
        commentCount.value[post.postId] = count.data
      }
    }));
    await Promise.all(postData.value["data"].map(async (post) => {
      // 获取关注状态
      post.followStatus = await getFollowStatus(post.userId);
      // ... 省略其他逻辑
      console.log(postData)
    }));
  } catch (e) {
    ElMessage.error(e);
  }
});
/**
 * 获取关注状态
 * @param followingId
 */
const getFollowStatus =  async (followingId) => {
  try {
    const res = await getFollowStatusApi(followingId);
    if (res["code"] === "200") return res.data;
    else return false;
  } catch (e) {
    return false;
  }
};
/**
 * 关注按钮
 * @param followingId
 */
const onFollowClick = async (followingId,postIndex) => {
  try {
    const res = await followApi(followingId);
    if (res["code"] === "200") {
      ElNotification({
        title: '关注状态',
        message: "用户" + followingId + res["msg"],
        type: 'success',
      });
      // 更新关注状态
      postData.value["data"][postIndex].followStatus = true;
    } else {
      ElNotification({
        title: '关注状态',
        message: "用户" + followingId + res["msg"],
        type: 'info',
      });
    }
  } catch (e) {
    ElNotification({
      title: '关注状态',
      message: "用户" + followingId + "关注操作错误",
      type: 'error',
    });
  }
};
/**
 * 取消关注
 * @param followingId
 */
const cancelFollow = async (followingId,postIndex) => {
  try {
    await cancelFollowApi(followingId).then((res) => {
      if (res["code"] === "200") {
        ElNotification({
          title: '关注状态',
          message: "用户"+followingId+ res["msg"],
          type: 'success',
        })
        postData.value["data"][postIndex].followStatus = false;
      } else ElNotification({
        title: '关注状态',
        message: "用户"+followingId+res["msg"],
        type: 'info',
      })
    })
  } catch (e) {
    ElNotification({
      title: '关注状态',
      message: "用户"+followingId+"关注操作错误",
      type: 'error',
    })
  }
}
/**
 * 用户头像
 */
const circleUrl = 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
const getUserHead = (userId: number) => {
  return userHeadUrls.value[userId] || circleUrl;
};
const getCommentCount = (postId:number) => {
  return commentCount.value[postId] || 0
}
</script>

<style scoped>
.post-container {
  width: 100%;
  max-width: 1400px;
  height: 800px;
  display: flex;
  margin: auto; /* 居中 */
  background-color: white;
  padding: 20px;
  border-radius: 20px;
}
.post-link {
  font-size: 20px;
  font-weight: 900;
  text-decoration: none; /* 去除下划线 */
  transition: color 0.3s ease; /* 添加颜色过渡效果 */
}
.follow-button {
  /* 添加或覆盖样式以确保按钮一致 */
  font-size: 14px; /* 根据需要调整字体大小 */
  height: 28px; /* 根据需要设置按钮高度 */
  line-height: 28px; /* 保持按钮内容垂直居中 */
  /* 添加其他共同的样式属性 */
}
.left-menu {
  width: 15%;
  overflow: hidden; /* 去掉 el-aside 的滚动条样式 */
  border-right: none;
  .menu-bar {
    display: flex;
    align-items: center; /* 垂直居中 */
    //justify-content: center; /* 水平居中 */
    height: 8%;
    font-size: 25px;
    font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
    color: #0ea67b;
  }
}
.right-content {
  margin-left: 10px;
  background-color: rgba(256, 256, 256, 0.3); /* 设置背景色为蓝色 */
  padding: 20px; /* 设置内边距，以便内容不贴边 */
  flex: 1; /* 让右侧部分占据剩余的所有空间 */
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  .post-title {
    display: flex;
    align-items: center;
    font-size: 25px;
    font-family: 微软雅黑,serif;
    color: #387e6a;
  }
}
.right-content .content-block {
  /* 可以根据需要自定义每个内容块的样式 */
  margin-top: 10px;
  align-items: center;
  padding: 10px;
  background-color: #fff;
  border-radius: 8px;
  border-bottom: 1px solid #ccc; /* 添加下划线 */
  display: flex;
  font-size: 15px;
}
.right-content .content-block:hover {
  background-color: #cfe9ff; /* 鼠标悬停时的浅蓝背景 */
  cursor: pointer;
}
.el-icon {
  margin-right: 5px;
}

/* 响应式字体大小 */
@media screen and (max-width: 768px) {
  .right-content {
    font-size: 15px; /* 设置较小的字体大小 */
  }
  .left-menu {
    width: 110px;
    .menu-bar {
      display: flex;
      align-items: center; /* 垂直居中 */
    //justify-content: center; /* 水平居中 */
      height: 8%;
      font-size: 25px;
      font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
      color: #0ea67b;
    }
  }
  .right-content .content-block {
    /* 可以根据需要自定义每个内容块的样式 */
    margin-top: 10px;
    align-items: center;
    padding: 10px;
    background-color: #fff;
    border-radius: 8px;
    border-bottom: 1px solid #ccc; /* 添加下划线 */
    display: flex;
    font-size: 10px;
  }
}
</style>
