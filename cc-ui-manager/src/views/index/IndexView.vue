<template>
  <div>
    <StatisticsComponent/>
    <!-- 将 EChartsContainer 组件放入模板中 -->
    <div style="display: flex;margin-top: 50px">
      <UserECharts/>
      <ArticleECharts/>
      <PostECharts/>
      <JobECharts/>
    </div>
    <EChartsContainer/>
  </div>
</template>

<script setup lang="ts">
import PostECharts from "@/views/index/echarts/PostECharts.vue";
import UserECharts from "@/views/index/echarts/UserECharts.vue";
import JobECharts from "@/views/index/echarts/JobECharts.vue";
import ArticleECharts from "@/views/index/echarts/ArticleECharts.vue";
import EChartsContainer from "@/views/index/echarts/EChartsContainer.vue";
import StatisticsComponent from "@/views/index/echarts/StatisticsComponent.vue";
</script>

<style lang="scss" scoped>

</style>
