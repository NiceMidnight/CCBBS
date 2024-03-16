<template>
  <div>
    <!-- 将 EChartsContainer 组件放入模板中 -->
    <div style="display: flex">
      <UserECharts/>
      <ArticleECharts/>
      <PostECharts/>
      <JobECharts/>
    </div>
  </div>
</template>

<script setup lang="ts">
import PostECharts from "@/views/index/echarts/PostECharts.vue";
import UserECharts from "@/views/index/echarts/UserECharts.vue";
import JobECharts from "@/views/index/echarts/JobECharts.vue";
import ArticleECharts from "@/views/index/echarts/ArticleECharts.vue";
</script>

<style lang="scss" scoped>

</style>
