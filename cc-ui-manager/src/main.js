
import { createApp } from 'vue'
import { createPinia } from "pinia";
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router/router'
import "@/style/main.scss"
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import '@fortawesome/fontawesome-free/css/all.css'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { library } from '@fortawesome/fontawesome-svg-core'
import { faThumbsUp } from '@fortawesome/free-solid-svg-icons'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { far } from '@fortawesome/free-regular-svg-icons'
import { fab } from '@fortawesome/free-brands-svg-icons'
library.add(faThumbsUp,fas,far,fab)

const app = createApp(App)
app.use(createPinia())
app.use(ElementPlus, {locale: zhCn,})
app.use(router)
app.component('font-awesome-icon', FontAwesomeIcon)
app.mount('#app')

