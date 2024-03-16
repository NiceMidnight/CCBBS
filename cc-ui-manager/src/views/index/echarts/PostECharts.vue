<template>
  <h1>帖子数据</h1>
  <div ref="chart" style="width: 300px; height: 380px;"></div>
</template>

<script setup lang="ts">
import {ref, onMounted, reactive} from 'vue';
import * as echarts from 'echarts';
import {getTFPEChartDataApi} from "@/api/eChartData";

const chart = ref<HTMLElement | null>(null);  // 声明一个 ref 变量用于存储图表的 DOM 元素
onMounted(async () => {
  await getTFPEChartDataApi().then((res) => {
    postData = res.data
    console.log(res)
    initPostChart()
  })
});
let postData = reactive([])
const initPostChart = () => {
  // 格式化聚合好的数据为 echarts 饼图数据格式
  const pieData = postData.map(item => ({
    value: item['postCount'],
    name: item['topicName']
  }));
  if (!chart.value) return; // 如果图表元素不存在，直接返回
  const myChart = echarts.init(chart.value); // 初始化 echarts 实例
  const option = { // 定义 echarts 图表的配置选项
    toolbox: {
      show: true,
      bottom:'10px',
      feature: {
        mark: { show: true },
        dataView: { show: true, readOnly: false, title: '数据视图', lang: ['数据视图', '关闭', '刷新'] },
        restore: { show: true ,title: '刷新', lang: ['刷新']},
        saveAsImage: { show: true, title: '保存为图片', lang: ['点击保存'] }
      }
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      top: '5%',
      left: 'center'
    },
    series: [
      {
        name: '帖子数量',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: 20,
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: pieData
      }
    ]
  };
  myChart.setOption(option);
}
</script>
