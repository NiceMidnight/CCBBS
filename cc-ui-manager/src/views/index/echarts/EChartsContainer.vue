<template>
  <div>
    <div class="echarts-container" ref="chartContainer"></div>
    <div>{{ userNumber }}</div>
  </div>
</template>

<script setup lang="ts">
import * as echarts from 'echarts';
import { ref, onMounted, reactive } from 'vue';
import { getUserNumber } from "@/api/eChartData";
import { getIndexDataApi } from "@/api/echartsData";

const chartContainer = ref<HTMLElement | null>(null);
const data = reactive(['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子']);
const userNumber = ref(null);

onMounted(async () => {
  initChart("a", data);
  await getUserNumber().then((res) => {
    // console.log(res);
    userNumber.value = res.data;
  });
  await getIndexDataApi().then((res) => {
    userNumber.value = res.data;
    // console.log(res);
  });
});

const initChart = (da: string, data: Array<string>) => {
  // 获取图表容器元素
  const container = chartContainer.value;

  if (!container) {
    return;
  }

  // 初始化图表实例
  const myChart = echarts.init(container);

  // 配置图表选项...
  const options = {
    title: {
      text: 'ECharts 入门示例'
    },
    tooltip: {},
    legend: {
      data: [da]
    },
    xAxis: {
      data: data
    },
    yAxis: {},
    series: [
      {
        name: da,
        type: 'bar',
        data: [5, 20, 36, 10, 10, 20]
      }
    ]
  };

  myChart.setOption(options);
};
</script>

<style lang="scss" scoped>
.echarts-container {
  width: 100%; /* 根据需要调整 */
  height: 400px; /* 根据需要调整 */
}
</style>
