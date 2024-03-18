// router.js
import { createRouter, createWebHistory } from 'vue-router';
import ErrorPage from "@/views/ErrorPage.vue";
import {useTokenStore} from "@/stores/mytoken";
const routes = [
    {
        path:'/login',
        name:'login',
        component: () => import("@/views/login/LoginView.vue")
    },
    {
        path: '/',
        name: 'home',
        // component: AppLayout, // 设置根路径对应的组件
        component: () => import("@/components/AppLayout.vue"),
        meta: { requiresAuth: true,title:"首页" },  //   是否需要验证
        children:[
            //默认页面
            {
                path: '/',
                name:'index',
                component: () => import("@/views/index/HomeView.vue"),
                meta: {title:"基本信息"}
            },
            // 懒加载
            {
                path:'/:xxx(.*)*',
                name:'ErrorPage',
                component:ErrorPage,
                meta: {title:""}
            },
            {
                path: '/recruit',
                name:'Recruit',
                component: () => import("@/views/recruit/RecruitView.vue"),
                meta: {title:"招聘"}
            },
            {
                path: '/jobMessage/:jobId/:jobTitle',
                name:'JobMessage',
                component: () => import("@/views/recruit/JobView.vue"),
                props: routes => ({
                    id: parseInt(routes.params.id)
                }),
                meta: {title:"招聘"}
            },
            {
                path: '/personalInfo',
                name:'PersonalInfo',
                component: () => import("@/views/personalCenter/PersonalCenter.vue"),
                meta: {title:"个人信息"}
            },
            {
                path: '/articleMessage/:id/:title',   //定义带有参数的路由
                name: 'ArticleView',
                component: () => import("@/views/index/ArticleView.vue"),
                props: routes => ({
                    id: parseInt(routes.params.id)
                }),
                meta:{title:"文章信息"}
            },
            {
                path: '/reservation',   //定义带有参数的路由
                name: 'Reservation',
                component: () => import("@/views/reservation/ReservationView.vue"),
                meta:{title:"文章信息"}
            },
            {
                path: '/forum',   //定义带有参数的路由
                name: 'Forum',
                component: () => import("@/views/forum/PostView.vue"),
                meta:{title:"帖子"},

            },
            {
                path: '/forum/:id/:title',
                name: 'Post',
                component: () => import("@/views/forum/PostMessageView.vue"),
                meta: { title: "帖子信息", requiresAuth: true }  // 需要验证权限
            },
            {
                path: '/postAMessage',
                name: 'PostAMessage',
                component: () => import("@/views/addAPost/PostAMessage.vue"),
                meta: { title: "发帖", requiresAuth: true }  // 需要验证权限
            },
            {
                path: '/personalHomepage/:userId',
                name: 'PersonalHomepage',
                component: () => import("@/views/PersonalHomepage/PersonalHomepage.vue"),
                meta: { title: "个人主页", requiresAuth: true }  // 需要验证权限
            }
        ]
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

// router.beforeEach((to, from, next) => {
//     if (to.matched.some(r=>r.meta?.requiresAuth)) {
//         const store = useTokenStore()
//         if (!store.token) {
//             //不存在token，跳转login页面，原要跳转的页面以query形式传递
//             next({name:'login',query:{redirect: to.fullPath}})
//         } else next()
//         return
//     }
//     //不要求权限拦截，原计划跳转
//     next()
// })

export default router;
