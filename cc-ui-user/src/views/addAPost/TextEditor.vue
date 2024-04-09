<script setup lang="ts">
import '@wangeditor/editor/dist/css/style.css' // 引入 css

import {onBeforeUnmount, ref, shallowRef, watchEffect, watch} from 'vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import {baseUrl} from "@/utils/request";
import {ElMessage} from "element-plus";
import { v4 as uuidV4 } from 'uuid';
//s属性
const props = defineProps({
  modelValue:{
    type:String,
    default:''
  }
})
//
const emits = defineEmits<{
  (e: "update:modelValue", value: string):void
}>()
// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef()

// 内容 HTML
const valueHtml = ref('<p>输入帖子内容</p>')
/**
 * 监听属性变化自动赋值给valueHtml
 */
watchEffect(() => {
  valueHtml.value = props.modelValue
})
/**
 * 监听valueHtml变化自动传值给父组件
 */
watch(valueHtml,(newHtml) => {
  emits("update:modelValue",newHtml)
})

const token = localStorage.getItem("TokenInfo");

const toolbarConfig = {}

const editorConfig = {
  placeholder: '请输入内容...',
  MENU_CONF: {
    uploadImage: {
      server: `${baseUrl}/user/images/upload`,
      fieldName:'file',
      name:`${uuidV4()}`,
      customInsert(res:any,insertFn:any) {
        console.log(res)
        if (res.code === '200') {
          insertFn(res.data)
        } else {
          ElMessage.error("富文本编辑器上传图片失败");
        }
      }
    }
  }
}

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor == null) return
  editor.destroy()
})

const handleCreated = (editor) => {
  editorRef.value = editor // 记录 editor 实例，重要！
}


</script>

<template>
  <div style="border: 1px solid #ccc;">
    <Toolbar
        style="border-bottom: 1px solid #ccc"
        :editor="editorRef"
        :defaultConfig="toolbarConfig"
    />
    <Editor
        style="height: 400px; overflow-y: hidden;"
        v-model="valueHtml"
        :defaultConfig="editorConfig"
        @onCreated="handleCreated"
    />
  </div>
</template>

<style scoped lang="scss">

</style>