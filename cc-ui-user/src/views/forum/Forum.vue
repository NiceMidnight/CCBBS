<template>
  <div>
    <el-container class="post-container">
      <el-aside class="left-menu">
        <div class="menu-bar"><el-icon><ChatDotRound /></el-icon>讨论区</div>
        <el-menu :default-active="postTopicData['topicId']" class="el-menu-vertical-demo" @select="handleMenuSelect">
          <el-menu-item :index="`0`">首页</el-menu-item>
          <el-menu-item
              v-for="postTopicData in postTopicData"
              :key="`${postTopicData['topicId']}`"
              :index="`${postTopicData['topicId']}`"
          >
            {{ postTopicData['topicName'] }}
          </el-menu-item>
        </el-menu>
      </el-aside>

      <!-- 右侧部分 -->
      <el-main class="right-content">
        <div>
          {{ selectedData }}
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';
import { onMounted } from "@vue/runtime-core";
import { getAllPostApi, getPostTopicApi } from "../../api/post";
import { ElMessage } from "element-plus";
import {ChatDotRound} from "@element-plus/icons-vue";

const selectedData = ref(0);
const selectOption = (option: number) => {
  selectedData.value = option;
  console.log(selectedData.value);
};

const handleMenuSelect = async (index: string) => {
  queryForm.data.topicId = Number(index)
  await getAllPostApi(queryForm, postMsg.value).then((res) => {
    postData.value = res.data;
    console.log(res);
  });
  selectOption(Number(index));
};

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
onMounted(async () => {
  try {
    await getPostTopicApi().then((res) => {
      postTopicData.value = res.data;
      console.log(res);
    });
    await getAllPostApi(queryForm, postMsg.value).then((res) => {
      postData.value = res.data;
      console.log(res);
    });
  } catch (e) {
    ElMessage.error(e);
  }
});
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
.left-menu {
  width: 15%;
  overflow: hidden; /* 去掉 el-aside 的滚动条样式 */
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
  background-color: rgba(203, 238, 255, 0.3); /* 设置背景色为蓝色 */
  padding: 20px; /* 设置内边距，以便内容不贴边 */
  flex: 1; /* 让右侧部分占据剩余的所有空间 */
  border-radius: 20px;
}

.el-main {
  padding: 20px; /* 设置内边距，以便内容不贴边 */
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
}
</style>
