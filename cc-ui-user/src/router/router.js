// router.js
import { createRouter, createWebHistory } from 'vue-router';
import ErrorPage from "@/views/ErrorPage.vue";
import {useTokenStore} from "@/stores/mytoken";
const routes = [

    {
        path:'/login',
        name:'login',
        component: () => import("@/views/loginAndRegister/LoginAndRegister.vue")
    },
    {
        path: '/',
        name: 'home',
        // component: AppLayout, // 设置根路径对应的组件
        component: () => import("@/components/AppLayout.vue"),
        meta: { requiresAuth: true,title:"首页" },  //   是否需要验证
        children:[
            //默认主页面
            {
                path: '/',
                name:'index',
                component: () => import("@/views/index/HomeView.vue"),
                meta: {title:"基本信息"}
            },

            //公告信息
            {
                path: '/articleMessage/:id/:title',
                name: 'ArticleView',
                component: () => import("@/views/index/ArticleView.vue"),
                props: routes => ({
                    id: parseInt(routes.params.id)
                }),
                meta:{title:"文章信息"}
            },

            //招聘
            {
                path: '/recruit',
                name:'Recruit',
                component: () => import("@/views/recruit/RecruitView.vue"),
                meta: {title:"所有招聘信息"}
            },
            {
                path: '/jobMessage/:jobId/:jobTitle',
                name:'JobMessage',
                component: () => import("@/views/recruit/JobView.vue"),
                props: routes => ({
                    id: parseInt(routes.params.id)
                }),
                meta: {title:"工作信息"}
            },

            //个人中心
            {
                path: '/personalInfo',
                name:'PersonalInfo',
                component: () => import("@/views/personalCenter/PersonalCenter.vue"),
                meta: {title:"个人信息"}
            },

            //反馈
            {
                path: '/reservation',
                name: 'Reservation',
                component: () => import("@/views/personalCenter/menu/ReservationView.vue"),
                meta:{title:"反馈信息"}
            },

            //论坛
            {
                path: '/forum',
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

            //个人主页
            {
                path: '/personalHomepage/:userId',
                name: 'PersonalHomepage',
                component: () => import("@/views/PersonalHomepage/PersonalHomepage.vue"),
                meta: { title: "个人主页", requiresAuth: true }  // 需要验证权限
            },

            // 懒加载错误页面
            {
                path:'/:xxx(.*)*',
                name:'ErrorPage',
                component:ErrorPage,
                meta: {title:""}
            },
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
