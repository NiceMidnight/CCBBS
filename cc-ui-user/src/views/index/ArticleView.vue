<template>
  <div class="container" v-if="article">
    <div class="title">{{ article["articleTitle"] }}</div>

    <div class="content">
      <el-icon class="icon"><User /></el-icon>
      发布用户：{{ article["createdBy"] }}&nbsp;&nbsp;/&nbsp;&nbsp;
      发布时间：{{ timeHandler(null, null, article["createdTime"]) }}
    </div>

    <div v-if="article['updatedId'] != null" class="content">
      <el-icon class="icon"><User /></el-icon>
      更新用户：{{ article["updatedBy"] }}&nbsp;&nbsp;/&nbsp;&nbsp;
      更新时间：{{ timeHandler(null, null, article["updatedTime"]) }}
    </div>

    <div class="content">
      <el-icon class="icon"><View /></el-icon>{{ article["viewCount"] }} 阅读量
    </div>

    <div class="content">{{ article["articleContent"] }}</div>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from "@vue/runtime-core";
import { useRoute } from "vue-router";
import { getArticle } from "../../api/article";
import { ref } from "vue";
import { timeHandler } from "../../utils/timeHandler";
import { User, View } from "@element-plus/icons-vue";

const route = useRoute();
const articleId = Number(route.params.id);
const article = ref();

onMounted(async () => {
  await getArticle(articleId).then((res) => {
    article.value = res.data;

  });
});
</script>

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
