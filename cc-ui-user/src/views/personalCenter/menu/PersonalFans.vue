<script setup lang="ts">
import {Lollipop, Star} from "@element-plus/icons-vue";
import { reactive, ref } from 'vue';
import {getFanUsersApi} from "@/api/follow";
import { timeHandlerSimplify } from "@/utils/timeHandler";
import { baseUrl } from "@/utils/request";
import {ElMessage} from "element-plus";
import {getOtherUserInfoByIdApi} from "@/api/login";
import {useRouter} from "vue-router";

const fanUserData = ref(null);//关注用户
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
    const res = await getFanUsersApi(queryForm);
    fanUserData.value = res.data;
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
    const res = await getFanUsersApi(queryParams);
    fanUserData.value = res.data;
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
    const res = await getFanUsersApi(queryParams);
    fanUserData.value = res.data;
    queryForm.total = res.data.total
  } catch (e) {
    ElMessage.error(e)
  }
}
const userInfo = ref(null) //用户信息
/**
 * 获取用户信息
 * @param userId
 */
const getUserInfo = async (userId:number) => {
  await getOtherUserInfoByIdApi(userId).then((res) => {
    userInfo.value = res.data
  })
}
/**
 * 链接跳转
 */
const router = useRouter(); // 解析router
const redirectToPersonalHomepage = (followerId:number) => {
  router.push({ name: 'PersonalHomepage', params: { userId: followerId } });
}
</script>

<template>
  <el-icon><Lollipop /></el-icon>我的粉丝
  <el-divider />
  <div v-if="fanUserData" v-for="(user, index) in fanUserData['data']" :key="index">
    <el-popover
        :width="300"
        placement="bottom-start"
        popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;"
    >
      <template #reference>
        <div class="user" @mouseenter="getUserInfo(user['followingId'])">
          <div><el-avatar :size="50" :src=" user['followerHead'] || circleUrl" /></div>
          <div class="user-message">
            <el-link :underline="false" @click="redirectToPersonalHomepage(user['followerId'])">
              {{user['followerNickName']}}
            </el-link>
            <font-awesome-icon :icon="['far', 'calendar-plus']" />
            {{timeHandlerSimplify(null,null,user['followDate'])}}关注了你
          </div>
        </div>
      </template>
      <template #default>
        <div v-if="userInfo" class="demo-rich-content" style="display: flex; gap: 16px; flex-direction: column">
          <el-avatar :size="60" :src="user['followingHead'] || circleUrl" style="margin-bottom: 8px"/>
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
  align-items: center;
  .user-message {
    margin-left: 1rem;
  }
}
.el-link {
  font-size: large;
}
</style>
