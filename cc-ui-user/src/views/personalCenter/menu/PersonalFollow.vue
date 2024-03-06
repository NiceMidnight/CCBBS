<script setup lang="ts">
import { Star } from "@element-plus/icons-vue";
import { reactive, ref } from 'vue';
import {cancelFollowApi, getFollowUsersApi} from "@/api/follow";
import { timeHandlerSimplify } from "@/utils/timeHandler";
import { baseUrl } from "@/utils/request";
import {ElMessage, ElNotification} from "element-plus";
import {getOtherUserInfoByIdApi} from "@/api/login";
import {useRouter} from "vue-router";

const followUserData = ref(null);//关注用户
const queryForm = reactive({
  pageNum: 1,
  pageSize: 5,
  total: 1,
  data: {}
});

const circleUrl = 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png';
/**
 * 加载数据
 */
const onLoad = async () => {
  try {
    const res = await getFollowUsersApi(queryForm);
    followUserData.value = res.data;
    queryForm.total = res.data.total
  } catch (error) {
    console.error("错误信息:", error);
  }
};
onLoad();
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
    const res = await getFollowUsersApi(queryParams);
    followUserData.value = res.data;
    queryForm.total = res.data.total
  } catch (e) {
    ElMessage.error(e)
  }
}
/**
 * 改变大小
 * @param val
 */
const handleCurrentChange = async (val: number) => {
  try {
    const queryParams = {
      pageNum:val,
      pageSize:queryForm.pageSize,
      data:queryForm.data
    }
    const res = await getFollowUsersApi(queryParams);
    followUserData.value = res.data;
    queryForm.total = res.data.total
  } catch (e) {
    ElMessage.error(e)
  }
}


/**
 * 获取用户信息
 * @param userId
 */
const userInfo = ref(null) //定义用于存储用户悬浮时获取的标题和内容的响应式对象
const getUserInfo = async (userId:number) => {
  try {
    const userData = await getOtherUserInfoByIdApi(userId);
    userInfo.value = userData.data
    // userPopoverContent.value = {
    //   nickName: userData.data.nickName,
    //   userAddress: userData.data.userAddress
    // };
  } catch (error) {
    console.error("获取用户信息失败:", error);
    userInfo.value = null
    // userPopoverContent.value = { nickName: '', userAddress: '' }; // 清空标题和内容
  }
}
// 定义用于存储用户悬浮时获取的标题和内容的响应式对象
const userPopoverContent = ref({ nickName: '', userAddress: '' });

const cancelFollow = async (followingId:number) => {
  await cancelFollowApi(followingId).then((res) => {
    if (res["code"] === "200") {
      ElNotification({
        title: '关注状态',
        message: "用户"+followingId+ res["msg"],
        type: 'success',
      })
      onLoad()
    } else ElNotification({
      title: '关注状态',
      message: "用户"+followingId+res["msg"],
      type: 'info',
    })
  })
}
/**
 * 链接跳转
 */
const router = useRouter(); // 解析router
const redirectToPersonalHomepage = (followingId:number) => {
  router.push({ name: 'PersonalHomepage', params: { userId: followingId } });
}
</script>

<template>
  <el-icon><Star /></el-icon>我的关注
  <el-divider />
  <div v-if="followUserData" v-for="(user, index) in followUserData['data']" :key="index">
    <el-popover
        :width="300"
        placement="bottom-start"
        popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;"
    >
      <template #reference>
        <div class="user" @mouseenter="getUserInfo(user['followingId'])">
          <div><el-avatar :size="50" :src="baseUrl + '/' + user['followingHead'] || circleUrl" /></div>
          <div class="user-message">
            <div>
              <el-link :underline="false" @click="redirectToPersonalHomepage(user['followingId'])">
                {{user['followingNickName']}}
              </el-link>
              <font-awesome-icon :icon="['far', 'calendar-plus']" />
              {{timeHandlerSimplify(null,null,user['followDate'])}}
            </div>
            <div>
              <el-button type="success" plain @click="cancelFollow(user['followingId'])">
                {{ user['followStatus'] === 'Follow' ? '已关注' : user['followStatus'] }}
              </el-button>
            </div>
          </div>
        </div>
      </template>
      <template #default>
        <div v-if="userInfo" class="demo-rich-content" style="display: flex; gap: 16px; flex-direction: column">
          <el-avatar :size="60" :src="baseUrl + '/' + user['followingHead'] || circleUrl" style="margin-bottom: 8px"/>
          <div>
            <p class="demo-rich-content__name" style="margin: 0; font-weight: 500;font-size: larger">
              {{userInfo['nickName']}}
            </p>
            <p class="demo-rich-content__name" style="margin: 0; font-weight: 500">
              用户名：{{userInfo['userName']}}
            </p>
            <p class="demo-rich-content__name" style="margin: 0; font-weight: 500">
              年龄：{{userInfo['userAge']}}
            </p>
            <p class="demo-rich-content__mention" style="margin: 0; font-size: 14px; color: var(--el-color-info)">
              地址：{{userInfo['userAddress']}}
            </p>
          </div>
          <p class="demo-rich-content__desc" style="margin: 0">
            个性签名：{{userInfo['userPersonality']}}
          </p>
        </div>
      </template>
    </el-popover>
    <el-divider />
  </div>
  <div v-else class="post-null">
    您还没有关注任何用户呢~
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
.user {
  display: flex;
  .user-message {
    margin-left: 1rem;
  }
}
</style>
