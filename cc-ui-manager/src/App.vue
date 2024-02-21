<template>
  <RouterView/>
</template>

<script setup lang="ts">
import {RouterView} from "vue-router";
import {onMounted} from "vue";
import {ElMessage} from "element-plus";
import {useTokenStore} from "@/stores/mytoken";
import {tokenVerificationApi} from "@/api/login";
onMounted(()=> {
  const tokenStore = useTokenStore()
  const token = tokenStore.getToken()
  if (token) {
    try {
      tokenVerificationApi(token).then((res) => {
        if (res["code"] === '500')
        {
          tokenStore.clearToken()
          ElMessage.error(res["msg"]);
        }
      })
    } catch (e) {
      console.log(e)
    }
  }
})
</script>

<style scoped>

</style>
