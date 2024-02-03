<script setup>
import {useRoute} from "vue-router";
import {ref} from "vue";
import {timeHandler} from "../../utils/timeHandler";
import { onMounted } from "@vue/runtime-core";
import { User, View } from "@element-plus/icons-vue";
import {getPostByPostIdApi} from "../../api/post";
const route = useRoute();
const postId = Number(route.params.id);
const post = ref();

onMounted(async() => {
  await getPostByPostIdApi(postId).then((res) => {
    post.value = res.data
    console.log(res.data);
  })
})

</script>



<template>
  <div class="container" v-if="post">
    <div class="title">{{ post["postTitle"] }}</div>
    <div class="topic">
      主题：{{ post["topicName"] }}
    </div>
    <div class="content">

      <el-icon class="icon"><User /></el-icon>
      发布用户：{{ post["nickName"] }}&nbsp;&nbsp;/&nbsp;&nbsp;
      发布时间：{{ timeHandler(null, null, post["createdTime"]) }}
    </div>

    <div v-if="post['updatedId'] != null" class="content">
      所属主题：{{ post["topicName"] }}
      <el-icon class="icon"><User /></el-icon>
      更新用户：{{ post["updatedBy"] }}&nbsp;&nbsp;/&nbsp;&nbsp;
      更新时间：{{ timeHandler(null, null, post["updatedTime"]) }}
    </div>

    <div class="content">
      <el-icon class="icon"><View /></el-icon>{{ post["viewCount"] }} 阅读量
    </div>

    <div class="content">{{ post["postContent"] }}</div>
  </div>
</template>

<style lang="scss" scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center; /* 水平居中 */
  justify-content: center; /* 垂直居中 */
  width: 100%;
  max-width: 1100px;
  margin: 0 auto;
  background-color: rgba(209, 255, 247, 0.7);
  border-radius: 20px;
}

.title {
  padding: 2rem;
  font-family: STXingkai, serif;
  font-size: 2rem;
  text-align: center;
}

.content {
  display: flex;
  align-items: center; /* 垂直居中 */
  height: 100%;
  overflow-y: auto;
  padding: 1rem;
  border-radius: 10px;
  font-size: 1.2rem;
  font-weight: lighter;
  overflow-wrap: break-word;
  word-wrap: break-word;
  hyphens: auto;
  width: 98%;
  white-space: pre-line;
}

.icon {
  margin-right: 10px; /* 调整图标和文本之间的间距 */
}

@media screen and (max-width: 768px) {
  .title {
    font-size: 1.5rem;
  }
  .content {
    font-size: 1.2rem;
  }
}
</style>
