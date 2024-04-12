<script setup>
import { ref, reactive,onMounted } from 'vue';
import TextEditor from "./TextEditor.vue";
import {getPostTopicApi, postAPostApi,} from "../../api/post";
import {ElMessage, ElNotification} from "element-plus";
import {useRouter} from "vue-router";
const topics = ref([]); //主题内容
const router = useRouter()
onMounted( async () => {
  /**
   * 获取主题
   */
  await getPostTopicApi().then((res) => {
    console.log(res)
    topics.value = res.data
  })
})
/**
 * 帖子数据
 * @type {UnwrapNestedRefs<{topicId: number, postContent: string, postTitle: string}>}
 */
const postData = reactive({
  topicId:1,
  postTitle:'',
  postContent:'',
})
/**
 * 提交帖子
 * @returns {Promise<void>}
 */
const onPost = async () => {
  if (postData.postTitle !== null && postData.postTitle !== '')
  {
    await postAPostApi(postData).then((res) => {
      console.log(res)
      if (res["code"])
      {
        ElNotification({
          title: '发布帖子',
          message: res["msg"],
          type: 'success',
        })
      } else ElNotification({
        title: '发布帖子',
        message: res["msg"],
        type: 'error',
      })
    })
  } else ElNotification({
    title: '发布帖子',
    message: "帖子标题不能为空啊啊啊",
    type: 'info',
  })
}
const onCancel =  () => {
  router.push("/forum");
}
</script>

<template>
  <div>
    <el-container class="container">
      <div class="title">
        <div>
          <font-awesome-icon :icon="['fab', 'twitter']" />
        </div>
        <div style="padding: 0.3rem;font-size: 1.5rem" class="color-text">
          广州航海学院
        </div>
        <div style="font-size: 1rem;padding: 0.7rem;margin-left: 2rem">
          <font-awesome-icon :icon="['far', 'pen-to-square']" /> 写文章
        </div>
        <div class="buttons">
          <el-button type="primary" round @click="onPost">发布</el-button>
          <el-button type="info" round @click="onCancel">取消</el-button>
        </div>
      </div>
      <el-divider border-style="double" />
      <div class="post-title">
        <div>
          标题&nbsp;&nbsp;
          <el-input
              v-model="postData.postTitle"
              maxlength="20"
              placeholder="请输入帖子标题"
              show-word-limit
              type="text"
              style="width: 23rem;"
          />
        </div>
      </div>
      <el-divider border-style="double" />
      <div class="select">
        <div class="select-text">
          选择主题
        </div>
        <div style="margin-left: 3rem">
          <el-radio-group v-model="postData.topicId">
            <el-radio v-for="topic in topics" :key="topic.topicId" :label="topic.topicId">{{ topic.topicName }}</el-radio>
          </el-radio-group>
        </div>
      </div>
      <el-divider border-style="double" />
      <div>
        <TextEditor
            v-model="postData.postContent"
        />
      </div>
    </el-container>

  </div>
</template>

<style scoped lang="scss">
.buttons {
  margin-left: auto;
}
.el-button {
  margin-left: 1rem
}
.container {
  width: 100%;
  max-width: 90rem;
  height: 50rem;
  flex-direction: column;
  margin: auto; /* 居中 */
  background-color: white;
  padding: 20px;
  border-radius: 20px;
}
.title {
  font-size: 2rem;
  padding: 0.5rem;
  display: flex;
}
.color-text {
  font-size: 30px;
  background: linear-gradient(to right, #e11717, #1b1b52);
  -webkit-background-clip: text;
  color: transparent;
}
.post-title {
  padding: 0.5rem;
  font-size: 1.4rem;
  display: flex;
}
.select {
  display: flex;
  padding: 0.3rem;
}
.select-text{
  font-size: 1.4rem;
  color: #e77272;
  text-shadow:0px 1px 0px #e7b1b1,
  0px 2px 0px #b0b0b0,
  0px 3px 0px #a0a0a0,
  0px 4px 0px #909090,
  0px 5px 10px rgba(54, 53, 53, 0.9);
}

</style>