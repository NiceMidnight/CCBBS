import { defineStore } from "pinia";
import {computed, ref} from "vue";

// pinia配置token
export const useCounterStore = defineStore('counter', () => {
    const count = ref(0)
    const doubleCount = computed(() => count.value * 2)
    function  increment() {
        count.value++
    }
    return { count ,doubleCount, increment}
    // 其他配置...
})