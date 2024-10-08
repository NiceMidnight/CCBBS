import request from "../utils/request";
import {UnwrapNestedRefs} from "@vue/runtime-core";
/**
 * 查询数据
 */
export type QueryCondition = Partial<{
    pageNum: number
    pageSize: number
    total: number
    data:{
        topicId:number
    }
}>
export type Post = {
    postId: number,
    postTitle: string,
    postContent: string
}
/**
 * 获取帖子数据
 * @param queryCondition
 * @param postMsg
 */
export const getAllPostApi = (queryCondition:QueryCondition,postMsg:string) => {
    return request({
        method:"POST",
        url:"/user/post/getAllPost",
        data:queryCondition,
        params:{
            postMsg:postMsg
        }
    })
}
/**
 * 获取帖子主题
 */
export const getPostTopicApi = () => {
    return request({
        method:"GET",
        url:"/user/tfp/getPostTopic",
    })
}
/**
 * 获取头像
 * @param userId
 */
export const getUserHeadApi = (userId:number) => {
    return request({
        method:'GET',
        url:'/user/getUserHead',
        params:{
            userId:userId
        }
    })
}
/**
 * 通过帖子id获取内容
 * @param postId
 */
export const getPostByPostIdApi = (postId:number) => {
    return request({
        method:"GET",
        url:"/user/post/getPostByPostId",
        params:{
            postId:postId
        }
    })
}
/**
 * 增加阅读量（点击量）
 * @param postId
 */
export const increaseViewCountApi = (postId:number) => {
    return request({
        method:'GET',
        url:'/user/post/increaseViewCount',
        params:{
            postId:postId
        }
    })
}
/**
 * 获取热点帖子
 */
export const getTopPostApi = () => {
    return request({
        method:'GET',
        url:'/user/post/getTopPost'
    })
}

export type postData = Partial<{
    topicId:number,
    postTitle:string,
    postContent:string,
}>
/**
 * 发帖
 * @param postData
 */
export const postAPostApi = (postData:postData) => {
    return request({
        method:'POST',
        url:'user/post/postAPost',
        data:postData
    })
}

export const getPostByUserIdApi = (queryForm: UnwrapNestedRefs<{
    total: number;
    pageSize: number;
    pageNum: number;
    data: {
        postTitle: string,
        topicId:number,
    };
}>, userId: number) => {
    return request({
        method:'POST',
        url:'user/post/getPostByUserId',
        data:queryForm,
        params:{
            userId:userId
        }
    })
}

