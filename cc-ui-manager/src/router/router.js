// router.js
import { createRouter, createWebHistory } from 'vue-router';
import IndexView from "@/views/index/IndexView.vue";
import {useTokenStore} from "@/stores/mytoken";
const routes = [
    {
        path:'/',
        name:'login',
        component: () => import("@/views/login/LoginView.vue")
    },
    {
        path: '/index',
        component: () =>import('@/components/layout/AppLayout.vue'),
        meta: { requiresAuth: true,title:"首页" },  //   是否需要验证
        children:[
            //默认页面
            {
                path: '',
                redirect: { name: 'default' } // 重定向到默认页面
            },
            {
                path: '',
                name:'default',
                component: IndexView,
            },
            {
                path: '/dict',
                name:'dict',
                component: () => import("@/views/dict/DictView.vue"),
                meta: {title:"资源字典"}
            },
            {
                path: '/images',
                name: 'images',
                component: () =>import("@/views/resources/SysImagesView.vue"),
                meta: {title:"本地图片管理"}
            },
            {
                path: '/ossImages',
                name: 'ossImages',
                component: () =>import("@/views/resources/OssImagesView.vue"),
                meta: {title:"云端图片管理"}
            },
            {
                path: '/users',
                name: 'users',
                component: () =>import("@/views/users/UsersIndex.vue"),
                meta: {title:"用户管理"}
            },
            {
                path: '/posts',
                name: 'posts',
                component: () =>import("@/views/post/PostView.vue"),
                meta: {title:"帖子管理"}
            },
            {
                path:'/posts/comments/:id/:title',
                name:'signalPostComments',
                component: () => import("@/views/post/SignalPostCommentsView.vue"),
                meta: {title:"单帖子评论管理"}
            },
            {
                path: '/topicForPost',
                name:'topicForPost',
                component: () => import("@/views/post/TopicForPostView.vue"),
                meta: { title: "帖子主题管理" }
            },
            {
                path:'/allPostComments',
                name:'allPostComments',
                component: () => import("@/views/post/AllPostCommentsView.vue"),
                meta: {title:"帖子评论管理"}
            },
            {
                path: '/personalInformation',
                name: 'personalInformation',
                component: () =>import("@/views/personalInformation/PersonalInformationView.vue"),
                meta: {title:"个人信息"}
            },
            {
                path:'/article',
                name:'article',
                component: () => import("@/views/article/ArticleView.vue"),
                meta: {title:"公告文章管理"}
            },
            {
                path:'/topicForArticle',
                name:'TopicForArticle',
                component: () => import("@/views/article/ArticleTopicDict.vue"),
                meta: {title:"公告主题管理"}
            },
            {
                path:'/applyRecruitMessage',
                name:'ApplyRecruitMessage',
                component: () => import("@/views/recruit/ApplyRecruitMessage.vue"),
                meta: {title:"招聘权限申请"}
            },
            {
                path:'/jobMessage',
                name:'JobMessage',
                component: () => import("@/views/recruit/JobMessage.vue"),
                meta: {title:"招聘信息"}
            },
            {
                path:'/jobTopicDict',
                name:'JobTopicDict',
                component: () => import("@/views/recruit/JobTopicDict.vue"),
                meta: {title:"招聘信息"}
            },
            {
                path:'/feedback',
                name:'Feedback',
                component: () => import("@/views/feedback/FeedbackView.vue"),
                meta: {title:"反馈信息"}
            },
            {
                path:'/topicForFeedback',
                name:'TopicForFeedback',
                component: () => import("@/views/feedback/TopicForFeedbackView.vue"),
                meta: {title:"反馈主题"}
            },
            {
                path:'/:xxx(.*)*',
                name:'ErrorPage',
                component: () => import("@/views/404/ErrorPage.vue")
            },
        ]
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach((to, from, next) => {
    if (to.matched.some(r=>r.meta?.requiresAuth)) {
        const store = useTokenStore()
        if (!store.token) {
            //不存在token，跳转login页面，原要跳转的页面以query形式传递
            next({name:'login',query:{redirect: to.fullPath}})
        } else next()
        return
    }
    //不要求权限拦截，原计划跳转
    next()
})

export default router;
