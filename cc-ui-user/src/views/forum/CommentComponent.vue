<template>
  <!-- 显示子评论的用户信息和内容 -->
    <div v-if="commentData" :key="commentData.commentId" class="child-comment">
      <div style="display: flex;align-items: center;">
        <el-avatar
            :size="50"
            :src="commentData.userHead ? `${baseUrl}/${commentData.userHead}` : 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'"
        />
        <div style="margin-left: 0.2rem">
          <div style="display: flex;align-items: center; ">
            <div class="comment-nickName">{{ commentData.nickName }} 回复给 {{ nickName }}</div>
            <div style="margin-left: 1rem">{{ timeHandlerSimplify(null, null, commentData.createdAt) }}</div>
          </div>
          <div style="display: flex;align-items: center;margin-top: 0.2rem;margin-bottom: 0.2rem">
            <div class="comment-content">{{ commentData.commentContent }}</div>
            <div style="margin-left: 1rem"><el-button round @click="handleReply(commentData)">回复</el-button></div>
            <div style="margin-left: 1rem" v-if="commentData.userId === userId ">
              <el-button type="danger" :icon="Delete" @click="handleDelete(commentData.commentId)" circle/>
            </div>
          </div>
        </div>
      </div>
      <Comment
          v-for="childComment in commentData.childCommentsList"
          :key="childComment.commentId"
          :commentData="childComment"
          :nickName="commentData.nickName"
          @reply="handleReply"
          @delete="handleDelete"
      />
    </div>

</template>

<script setup>
import {defineProps,defineEmits, onMounted, ref} from 'vue';
import Comment from './CommentComponent.vue';
import { timeHandlerSimplify} from "../../utils/timeHandler"
import {Check, Delete} from "@element-plus/icons-vue";
import {baseUrl} from "../../utils/request";
import {getUserIdApi} from "../../api/login"; // 导入递归组件
const userId = ref()
// 定义自定义事件
const emits = defineEmits(['reply','delete']);
const props = defineProps({
  commentData: {
    type: Object,
    required: true,
  },
  nickName: {
    type: String, // 根据您的需要，可以修改类型
    required: true,
  },
  handleReply: {
    type: Function,
    required: false,
  },
  handleDelete: {
    type: Function,
    required: false,
  },
});
onMounted( async () => {
   await getUserIdApi().then((res) => {
    userId.value = res.data
  })
})

const handleReply = (childCommentList) => {
  // 触发自定义事件，并传递 childCommentList 数据
  emits('reply', childCommentList);
};
const handleDelete = (commentId) => {
  emits('delete',commentId)
}
</script>

<style scoped>
.reply-comment {
  display: flex;
  position: fixed; /* 设置为固定定位 */
  bottom: 1rem; /* 与右侧容器相同的垂直位置 */
  align-items: center;
  width: 30%; /* 可以根据需要调整宽度 */
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
</style>
