import '@/assets/main.css'      // 引入 main.css 样式文件
import 'animate.css';

import { createApp } from 'vue' // 引入 createApp 方法
import App from '@/App.vue'     // 引入 App.vue 组件
// 导入路由
import router from '@/router'
// 导入 Element Plus 图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)

// 应用路由
app.use(router)

// 引入图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.mount('#app')
