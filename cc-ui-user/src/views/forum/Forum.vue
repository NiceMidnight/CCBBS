<template>
  <div class="app-container">
    <!-- 左侧组件列表 -->
    <div class="sidebar">
      <div
          v-for="(component, index) in components"
          :key="index"
          @click="selectComponent(index)"
          :class="{ 'selected': selectedComponentIndex === index }"
      >
        {{ component.name }}
      </div>
    </div>
123
    <!-- 右侧组件显示区域 -->
    <div class="main-content">
      <component :is="selectedComponent" />
    </div>
  </div>
</template>

<script setup lang="ts">
import Component1 from "./components/Component1.vue";
import Component2 from "./components/Component2.vue";

const components = [
  { name: "Component 1", component: Component1 },
  { name: "Component 2", component: Component2 },
  // Add more components as needed
];

let selectedComponentIndex = 0;

const selectComponent = (index) => {
  selectedComponentIndex = index;
};
</script>

<style scoped>
.app-container {
  display: flex;
  height: 100vh; /* 设置高度为视窗高度 */
}

.sidebar {
  width: 200px;
  background-color: #f0f0f0;
  padding: 20px;
  border-right: 1px solid #ccc;
  overflow-y: auto; /* 如果组件列表过多，启用纵向滚动条 */
}

.sidebar div {
  cursor: pointer;
  padding: 10px;
  margin-bottom: 10px;
  transition: background-color 0.3s;
}

.sidebar div:hover {
  background-color: #ddd;
}

.sidebar .selected {
  background-color: #ddd;
}

.main-content {
  flex: 1;
  padding: 20px;
}
</style>
