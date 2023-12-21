import { createApp } from 'vue'
import { createPinia } from "pinia";
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router/router'
import "@/style/main.scss"
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'

const app = createApp(App)
app.use(createPinia())
app.use(ElementPlus, {locale: zhCn,})
app.use(router)
app.mount('#app')
