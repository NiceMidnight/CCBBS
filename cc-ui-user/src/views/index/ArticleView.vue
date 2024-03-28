<template>
  <div class="container" v-if="article">
    <div class="title">{{ article["articleTitle"] }}</div>

    <div><el-icon class="icon">
      <View /></el-icon>{{ article["viewCount"] }}
      <el-icon><Flag /></el-icon> {{article['topicName']}}
    </div>

    <div>
      <el-icon class="icon"><User /></el-icon>
      发布：{{ article["createdBy"] }}&nbsp;&nbsp;/&nbsp;&nbsp;{{ timeHandler(null, null, article["createdTime"]) }}
    </div>

    <div v-if="article['updatedId'] != null" >
      <el-icon class="icon"><User /></el-icon>
      更新：{{ article["updatedBy"] }}&nbsp;&nbsp;/&nbsp;&nbsp;{{ timeHandler(null, null, article["updatedTime"]) }}
    </div>

    <div class="content" v-html="article['articleContent']"></div>

  </div>
</template>

<script setup lang="ts">
import { onMounted } from "@vue/runtime-core";
import { useRoute } from "vue-router";
import { getArticle } from "../../api/article";
import { ref } from "vue";
import { timeHandler } from "../../utils/timeHandler";
import {Flag, User, View} from "@element-plus/icons-vue";

const route = useRoute();
const articleId = Number(route.params.id);
const article = ref();

onMounted(async () => {
  await getArticle(articleId).then((res) => {
    article.value = res.data;
    console.log(res)
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
  background-color: white;
  border-radius: 20px;
}

.title {
  padding: 2rem;
  font-family: STXingkai, serif;
  font-size: 2rem;
  text-align: center;
}

.content {
  padding: 2rem;
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
