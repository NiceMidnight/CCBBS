<script setup lang="ts">
import {ChatDotRound, View} from "@element-plus/icons-vue";
import {
  changeStatusForUserApi,
  getCommentStatusForComplianceApi,
  getReplyCommentsApi,
  getStatusForUserApi
} from "@/api/comment";
import {reactive, ref} from "vue";
import {QueryForm} from "@/api/comment";
import {ElMessage} from "element-plus";
import {baseUrl} from "@/utils/request";
import {increaseViewCountApi} from "@/api/post";
import {useRouter} from "vue-router";

const replyComments = ref(null);
const queryForm = reactive<QueryForm>({
  pageNum: 1,
  pageSize: 5,
  total: 1,
  data: {

  }
});
const statusForUserEnum = ref(null)
const statusForComplianceEnum = ref(null)
const onLoad = async () => {
  await getStatusForUserApi().then((res) => {
    statusForUserEnum.value = res.data
  })
  await getCommentStatusForComplianceApi().then((res) => {
    statusForComplianceEnum.value = res.data
  })
  await getReplyCommentsApi(queryForm).then((res) => {
    console.log(res)
    replyComments.value = res.data
    queryForm.total = res.data.total
    changeStatusForUser()
  })
}
onLoad()
/**
 * 改变消息状态为已读
 */
const changeStatusForUser = async () => {
  try {
    if (replyComments.value) {
      for (const comment of replyComments.value.data) {
        if (comment['statusForCompliance'] === statusForComplianceEnum.value[0])
        {
          if (comment['statusForUser'] === statusForUserEnum.value[0]) {
            await changeStatusForUserApi(comment['commentId']);
          }
        }
      }
    }
  } catch (e) {
    ElMessage.error('改变状态失败：' + e);
  }
}
/**
 * 改变大小
 * @param val
 */
const handleSizeChange = async (val: number) => {
  try {
    const queryParams = {
      pageNum:queryForm.pageNum,
      pageSize:val,
      data:queryForm.data
    }
    const res = await getReplyCommentsApi(queryParams);
    replyComments.value = res.data
    if (res['code'] === '200')
    {
      await changeStatusForUser()
    }
    queryForm.total = res.data.total
  } catch (e) {
    console.log(e)
  }
}
const handleCurrentChange = async (val: number) => {
  try {
    const queryParams = {
      pageNum:val,
      pageSize:queryForm.pageSize,
      data:queryForm.data
    }
    const res = await getReplyCommentsApi(queryParams);
    replyComments.value = res.data
    if (res['code'] === '200')
    {
      await changeStatusForUser()
    }
    queryForm.total = res.data.total
  } catch (e) {
    console.log(e)
  }
}
const circleUrl = 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png';//头像
/**
 * 链接跳转-帖子-个人主页
 */
const router = useRouter(); // 解析router
const redirectToPost = (postTitle,postId) => {
  increaseViewCountApi(postId);
  router.push({ name: 'Post', params: { title:postTitle,id: postId } });
};
const redirectToPersonalHomepage = (userId:number) => {
  router.push({ name: 'PersonalHomepage', params: { userId: userId } });
}
</script>

<template>
  <el-icon><ChatDotRound /></el-icon>我的消息
  <el-divider />
  <div v-if="replyComments" v-for="(comment, index) in replyComments['data']" :key="index">
    <div v-if="comment['statusForCompliance'] === statusForComplianceEnum[0]">
      <div v-if="comment['statusForUser'] === statusForUserEnum[0]">
        <el-badge is-dot class="item">
          <el-link :underline="false" @click="redirectToPost( comment['postTitle'], comment['postId'])">
            贴名：{{comment['postTitle']}}<el-icon class="el-icon--right"><el-icon><View /></el-icon></el-icon>
          </el-link>
        </el-badge>
      </div>
      <div v-else>
        <el-link :underline="false" @click="redirectToPost( comment['postTitle'], comment['postId'])">
          贴名：{{comment['postTitle']}}<el-icon class="el-icon--right"><el-icon><View /></el-icon></el-icon>
        </el-link>
      </div>
      <div style="display: flex;align-items: center;">
        <div v-if="comment['userHead']"><el-avatar :size="35" :src="comment['userHead']" /></div>
        <div v-else><el-avatar :size="35" :src="circleUrl" /></div>
        <div>
          <el-link :underline="false" @click="redirectToPersonalHomepage(comment['userId'])" style="margin-right: 0.5rem">
            {{comment['nickName']}}
            用户名：{{comment['userName']}}
          </el-link>回复了你
        </div>
        <div style="font-size: small;margin-left: 0.5rem">
          {{comment['commentContent']}}
        </div>
      </div>
    </div>

    <div v-else>
      该消息无法查看！
    </div>
    <el-divider />
  </div>
  <el-pagination
      v-model:current-page="queryForm.pageNum"
      v-model:page-size="queryForm.pageSize"
      background
      layout="prev, pager, next"
      :total="queryForm.total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"/>
</template>

<style scoped lang="scss">

</style>