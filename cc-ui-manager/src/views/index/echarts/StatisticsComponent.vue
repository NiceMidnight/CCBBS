<template>
  <el-row :gutter="10">
    <el-col :span="4">
      <div class="statistic-card">
        <el-statistic :value="todayUserCount">
          <template #title>
            <div style="display: inline-flex; align-items: center">
              日活跃用户
              <el-tooltip
                  effect="dark"
                  content="一天内登录到论坛的用户数量"
                  placement="top"
              >
                <el-icon style="margin-left: 4px" :size="12">
                  <Warning />
                </el-icon>
              </el-tooltip>
            </div>
          </template>
        </el-statistic>
        <div class="statistic-footer">
          <div class="footer-item">
            <span>较昨日</span>
            <span class="green" v-if="todayUserCount >= yesterdayUserCount">
              {{ growthRate(todayUserCount,yesterdayUserCount) }}
              <el-icon>
                <CaretTop />
              </el-icon>
            </span>
            <span class="red" v-else>
              {{ growthRate(todayUserCount,yesterdayUserCount) }}
              <el-icon>
                <CaretBottom/>
              </el-icon>
            </span>
          </div>
          <div class="footer-item" @click="handleClick(1)">
            <el-icon :size="14">
              <ArrowRight />
            </el-icon>
          </div>
        </div>
      </div>
    </el-col>

    <el-col :span="4">
      <div class="statistic-card">
        <el-statistic :value="currentMonthUserCount">
          <template #title>
            <div style="display: inline-flex; align-items: center">
              月活跃用户
              <el-tooltip
                  effect="dark"
                  content="一个月内登录到论坛的用户数量"
                  placement="top"
              >
                <el-icon style="margin-left: 4px" :size="12">
                  <Warning />
                </el-icon>
              </el-tooltip>
            </div>
          </template>
        </el-statistic>
        <div class="statistic-footer">
          <div class="footer-item">
            <span>较上月</span>
            <span class="green" v-if="currentMonthUserCount >= lastMonthUserCount">
              {{ growthRate(currentMonthUserCount,lastMonthUserCount) }}
              <el-icon>
                <CaretTop />
              </el-icon>
            </span>
            <span class="red" v-else>
              {{ growthRate(currentMonthUserCount,lastMonthUserCount) }}
              <el-icon>
                <CaretBottom />
              </el-icon>
            </span>
          </div>
          <div class="footer-item" @click="handleClick(2)">
            <el-icon :size="14">
              <ArrowRight />
            </el-icon>
          </div>
        </div>
      </div>
    </el-col>

    <el-col :span="4">
      <div class="statistic-card">
        <el-statistic :value="currentMonthArticleCount" title="New transactions today">
          <template #title>
            <div style="display: inline-flex; align-items: center">
              月公告数量
              <el-tooltip
                  effect="dark"
                  content="当月发布公告的数量"
                  placement="top"
              >
                <el-icon style="margin-left: 4px" :size="12">
                  <Warning />
                </el-icon>
              </el-tooltip>
            </div>
          </template>
        </el-statistic>
        <div class="statistic-footer">
          <div class="footer-item">
            <span>较上月</span>
            <span class="green" v-if="currentMonthArticleCount >= lastMonthArticleCount">
              {{ growthRate(currentMonthArticleCount,lastMonthArticleCount) }}
              <el-icon>
                <CaretTop />
              </el-icon>
            </span>
            <span class="red" v-else>
              {{ growthRate(currentMonthArticleCount,lastMonthArticleCount) }}
              <el-icon>
                <CaretBottom />
              </el-icon>
            </span>
          </div>
          <div class="footer-item" @click="handleClick(3)">
            <el-icon :size="14">
              <ArrowRight />
            </el-icon>
          </div>
        </div>
      </div>
    </el-col>

    <el-col :span="4">
      <div class="statistic-card">
<!--        <el-statistic title="Total Transactions" :value="outputValue" />-->
        <el-statistic :value="currentWeekPostCount" title="New transactions today">
          <template #title>
            <div style="display: inline-flex; align-items: center">
              周帖子数量
              <el-tooltip
                  effect="dark"
                  content="当周发布帖子的数量"
                  placement="top"
              >
                <el-icon style="margin-left: 4px" :size="12">
                  <Warning />
                </el-icon>
              </el-tooltip>
            </div>
          </template>
        </el-statistic>
        <div class="statistic-footer">
          <div class="footer-item">
            <span>较上周</span>
            <span class="green" v-if="currentWeekPostCount >= lastWeekPostCount">
              {{ growthRate(currentWeekPostCount,lastWeekPostCount) }}
              <el-icon>
                <CaretTop />
              </el-icon>
            </span>
            <span class="red" v-else>
              {{ growthRate(currentWeekPostCount,lastWeekPostCount) }}
              <el-icon>
                <CaretBottom />
              </el-icon>
            </span>
          </div>
          <div class="footer-item" @click="handleClick(4)">
            <el-icon :size="14">
              <ArrowRight />
            </el-icon>
          </div>
        </div>
      </div>
    </el-col>

    <el-col :span="4">
      <div class="statistic-card">
        <el-statistic :value="currentMonthJobCount" title="New transactions today">
          <template #title>
            <div style="display: inline-flex; align-items: center">
              月招聘信息数量
              <el-tooltip
                  effect="dark"
                  content="当月发布招聘信息的数量"
                  placement="top"
              >
                <el-icon style="margin-left: 4px" :size="12">
                  <Warning />
                </el-icon>
              </el-tooltip>
            </div>
          </template>
        </el-statistic>
        <div class="statistic-footer">
          <div class="footer-item">
            <span>较上月</span>
            <span class="green" v-if="currentMonthJobCount >= lastMonthJobCount">
              {{ growthRate(currentMonthJobCount,lastMonthJobCount) }}
              <el-icon>
                <CaretTop />
              </el-icon>
            </span>
            <span class="red" v-else>
              {{ growthRate(currentMonthJobCount,lastMonthJobCount) }}
              <el-icon>
                <CaretBottom />
              </el-icon>
            </span>
          </div>
          <div class="footer-item" @click="handleClick(5)">
            <el-icon :size="14">
              <ArrowRight />
            </el-icon>
          </div>
        </div>
      </div>
    </el-col>

    <el-col :span="4" >
      <div class="statistic-card">
        <el-statistic :value="currentWeekFeedbackCount" title="New transactions today">
          <template #title>
            <div style="display: inline-flex; align-items: center">
              周反馈数量
              <el-tooltip
                  effect="dark"
                  content="当周对论坛网站提供反馈信息的数量"
                  placement="top"
              >
                <el-icon style="margin-left: 4px" :size="12">
                  <Warning />
                </el-icon>
              </el-tooltip>
            </div>
          </template>
        </el-statistic>
        <div class="statistic-footer">
          <div class="footer-item">
            <span>较昨日</span>
            <span class="green" v-if="currentWeekFeedbackCount >= lastWeekFeedbackCount">
              {{ growthRate(currentWeekFeedbackCount,lastWeekFeedbackCount) }}
              <el-icon>
                <CaretTop />
              </el-icon>
            </span>
            <span class="red" v-else>
              {{ growthRate(currentWeekFeedbackCount,lastWeekFeedbackCount) }}
              <el-icon>
                <CaretBottom />
              </el-icon>
            </span>
          </div>
          <div class="footer-item" @click="handleClick(6)">
            <el-icon :size="14">
              <ArrowRight />
            </el-icon>
          </div>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script lang="ts" setup>
import {
  ArrowRight,
  CaretBottom,
  CaretTop,
  Warning,
} from '@element-plus/icons-vue'
import {onMounted, ref} from "vue";
import {
  getCountOfCurrentMonthArticleApi,
  getCountOfCurrentMonthDailyUniqueVisitorsLogApi,
  getCountOfCurrentMonthJobApi, getCountOfCurrentWeekFeedbackApi,
  getCountOfCurrentWeekPostApi,
  getCountOfLastMonthDailyUniqueVisitorsApi,
  getCountOfLastMonthJobApi, getCountOfLastWeekFeedbackApi,
  getCountOfLastWeekPostApi,
  getCountOfLathMonthArticleApi,
  getCountOfTodayDailyUniqueVisitorsApi,
  getCountOfYesterdayDailyUniqueVisitorsApi
} from "@/api/eChartData";
import { useTransition } from '@vueuse/core'
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";
const source = ref(172000)
const outputValue = useTransition(source, {duration: 1500,})
const router = useRouter(); // 解析router
const handleClick = (num:number) => {
  switch (num)
  {
    case 1:
      router.push({ name: 'users' });
      return;
    case 2:
      router.push({ name: 'users' });
      return;
    case 3:
      router.push({ name: 'article' });
      return;
    case 4:
      router.push({ name: 'posts' });
      return;
    case 5:
      router.push({ name: 'JobMessage' });
      return;
    case 6:
      router.push({ name: 'Feedback' });
      return;
  }
}
const todayUserCount = ref(null)  //当日活跃用户数量
const yesterdayUserCount = ref(null)  //昨日活跃用户数量
const currentMonthUserCount = ref(null) //当月活跃用户数量
const lastMonthUserCount = ref(null)  //上个月活跃用户数量
const currentMonthArticleCount = ref(null) //本月公告数
const lastMonthArticleCount = ref(null) //上月公告数
const currentWeekPostCount = ref(null) //本周帖子数
const lastWeekPostCount = ref(null) //上周帖子数
const currentMonthJobCount = ref(null) //本月招聘信息数量
const lastMonthJobCount = ref(null) //上月招聘信息数量
const currentWeekFeedbackCount = ref(null) //本周反馈数
const lastWeekFeedbackCount = ref(null) //上周反馈数
onMounted( async () => {
   await getCountOfTodayDailyUniqueVisitorsApi().then((res) => {
    todayUserCount.value = res.data
    // console.log(res)
  })
  await getCountOfYesterdayDailyUniqueVisitorsApi().then((res) => {
    yesterdayUserCount.value = res.data
    // console.log(res)
  })
  await getCountOfCurrentMonthDailyUniqueVisitorsLogApi().then((res) => {
    currentMonthUserCount.value = res.data
    // console.log(res)
  })
  await getCountOfLastMonthDailyUniqueVisitorsApi().then((res) => {
    lastMonthUserCount.value = res.data
    // console.log(res)
  })
  await getCountOfCurrentMonthArticleApi().then((res) => {
    currentMonthArticleCount.value = res.data
    // console.log(res)
  })
  await getCountOfLathMonthArticleApi().then((res) => {
    lastMonthArticleCount.value = res.data
    // console.log(res)
  })
  await getCountOfCurrentWeekPostApi().then((res) => {
    currentWeekPostCount.value = res.data
    // console.log(res)
  })
  await getCountOfLastWeekPostApi().then((res) => {
    lastWeekPostCount.value = res.data
    // console.log(res)
  })
  await getCountOfCurrentMonthJobApi().then((res) => {
    currentMonthJobCount.value = res.data
    // console.log(res)
  })
  await getCountOfLastMonthJobApi().then((res) => {
    lastMonthJobCount.value = res.data
    // console.log(res)
  })
  await getCountOfCurrentWeekFeedbackApi().then((res) => {
    currentWeekFeedbackCount.value = res.data
    console.log(res)
  })
  await getCountOfLastWeekFeedbackApi().then((res) => {
    lastWeekFeedbackCount.value = res.data
    console.log(res)
  })
});

/**
 * 计算增长率
 * @param now
 * @param before
 */
const growthRate = (now,before) => {
  if (before === 0)
  {
    return "N/A"; // 表示数据不适用
  }
  const rate = (now - before) / before * 100
  return Math.abs(rate).toFixed(2) + '%'
}
</script>

<style scoped>
:global(h2#card-usage ~ .example .example-showcase) {
  background-color: var(--el-fill-color) !important;
}

.el-statistic {
  --el-statistic-content-font-size: 28px;
}

.statistic-card {
  height: 100%;
  padding: 20px;
  border-radius: 4px;
  background-color: var(--el-bg-color-overlay);
}

.statistic-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  font-size: 12px;
  color: var(--el-text-color-regular);
  margin-top: 16px;
}

.statistic-footer .footer-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.statistic-footer .footer-item span:last-child {
  display: inline-flex;
  align-items: center;
  margin-left: 4px;
}

.green {
  color: var(--el-color-success);
}
.red {
  color: var(--el-color-error);
}
</style>
