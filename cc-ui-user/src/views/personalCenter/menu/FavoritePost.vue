<script setup lang="ts">
import {timeHandlerSimplify} from "@/utils/timeHandler";
import {Sunny} from "@element-plus/icons-vue";
import {reactive, ref} from "vue";
import {useRouter} from "vue-router";
import {getFavoritePostApi} from "@/api/favoriteForPost";
import {getFanUsersApi} from "@/api/follow";
import {getPostByPostIdApi, increaseViewCountApi} from "@/api/post";
const postData = ref(null);//关注用户
const queryForm = reactive({
  pageNum: 1,
  pageSize: 5,
  total: 1,
  data: {}
});
const onLoad = async () => {
  try {
    await getFavoritePostApi(queryForm).then((res) => {
      postData.value = res.data
      queryForm.total = res.data.total
      console.log(res)
    })
  } catch (e) {
    console.log(e)
  }
}
onLoad()
/**
 * 改变大小
 * @param val
 */
const handleSizeChange = async (val: number) => {
  try {
    const queryParams = {
      pageNum:queryForm.pageNum,
      pageSize:val,
      total:queryForm.total,
      data:queryForm.data
    }
    const res = await getFavoritePostApi(queryParams);
    postData.value = res.data;
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
      total:queryForm.total,
      data:queryForm.data
    }
    const res = await getFavoritePostApi(queryParams);
    postData.value = res.data;
    queryForm.total = res.data.total
  } catch (e) {
    console.log(e)
  }
}
const postInfo = ref(null) //用户信息
const getPostInfo = async (postId:number) => {
  await getPostByPostIdApi(postId).then((res) => {
    postInfo.value = res.data
  })
}
/**
 * 链接跳转
 */
const router = useRouter(); // 解析router
const redirectToPostDetail = (postTitle:string,postId:number) => {
  increaseViewCountApi(postId);
  router.push({ name: 'Post', params: { title:postTitle,id: postId } });
}
</script>

<template>
  <el-icon><Sunny /></el-icon>我的收藏
  <el-divider />
  <div v-if="postData" v-for="(post, index) in postData['data']" :key="index">
    <el-popover
        :width="300"
        placement="bottom-start"
        popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;"
    >
      <template #reference>
        <div class="user" @mouseenter="getPostInfo(post['postId'])">
          <div class="user-message">
            <el-link :underline="false" @click="redirectToPostDetail(post['postTitle'],post['postId'])" style="font-size: larger">
              {{post['postTitle']}}
            </el-link>
            收藏于{{timeHandlerSimplify(null,null,post['timestamp'])}}
          </div>
        </div>
      </template>

      <template #default>
        <div v-if="postInfo" class="demo-rich-content" style="display: flex; gap: 16px; flex-direction: column">
<!--          <el-avatar :size="60" :src="user['followingHead'] || circleUrl" style="margin-bottom: 8px"/>-->
          <div>
            <p class="demo-rich-content__name" style="margin: 0; font-weight: 500;font-size: larger">
              {{post['postTitle']}}
            </p>
            <p class="demo-rich-content__name" style="margin: 0; font-size: 14px; color: var(--el-color-info)">
              发布者：{{postInfo['nickName']}}
            </p>
            <p class="demo-rich-content__name" style="margin: 0; font-size: 14px; color: var(--el-color-info)">
              点击量：{{postInfo['viewCount']}}
            </p>
            <p class="demo-rich-content__name" style="margin: 0; font-size: 14px; color: var(--el-color-info)">
              点赞数：{{postInfo['likeCount']}}
            </p>
            <p class="demo-rich-content__mention" style="margin: 0; font-size: 14px; color: var(--el-color-info)">
              收藏数：{{postInfo['favoriteCount']}}
            </p>
          </div>
          <p class="demo-rich-content__desc" style="margin: 0">
            发布时间：{{timeHandlerSimplify(null,null,post['createdAt'])}}
          </p>
        </div>
      </template>
    </el-popover>
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