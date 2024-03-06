import request from "../utils/request";
import {reactive} from "vue";

export const getCommentCountApi = (postId:number) => {
    return request({
        method:'GET',
        url:'/user/comment/getCommentCount',
        params:{
            postId:postId
        }
    })
}
/**
 * 提交对帖子评论
 */
export type postACommentData = Partial<{
    postId: number,
    commentContent:string,
    parentCommentId:string
}>
/**
 * 帖子评论
 */
export type commentData = Partial<{
    commentId: number
    postId: number
    userId: number
    nickName: string
    commentContent: string
    parentCommentId: number
    createdAt: string
    statusForUser: string
    statusForCompliance: string
    // childCommentsList: commentData
}>
/**
 * 提交帖子
 * @param comments
 */
export const postACommentApi = (comments:postACommentData) => {
    return request({
        method:'POST',
        url:'/user/comment/postAComment',
        data: comments
    })
}

/**
 * 获取最新评论
 * @param postId
 */
export const getLatestCommentsApi = (postId:number) => {
    return request({
        method:'GET',
        url:'/user/comment/getLatestComments',
        params:{
            postId:postId
        }
    })
}
/**
 * 获取最热评论
 * @param postId
 */
export const getTopCommentsApi = (postId:number) => {
    return request({
        method:'GET',
        url:'/user/comment/getTopComments',
        params:{
            postId:postId
        }
    })
}
/**
 * 回复
 */
export type ReplyCommentData = Partial<{
    postId: string
    commentContent: string
    parentCommentId: string
}>
/**
 * 提交回复评论
 * @param comments
 */
export const submitAReplyApi = (comments:ReplyCommentData) => {
    return request({
        method:'POST',
        url:'/user/comment/submitAReply',
        data:comments
    })
}
/**
 * 删除个人评论
 * @param commentId
 * @param postId
 */
export const deletePersonalCommentApi = (commentId:number,postId:number) => {
    return request({
        method:'GET',
        url:'/user/comment/deletePersonalComment',
        params:{
            commentId:commentId,
            postId:postId
        }
    })
}

export type QueryForm = Partial<{
    pageNum: number,
    pageSize: number,
    total: number,
    data: {

    },
}>
/**
 * 获取未读回复评论
 * @param queryForm
 */
export const getReplyCommentsApi = (queryForm:QueryForm) => {
    return request({
        method:'POST',
        url:'/user/comment/getReplyComments',
        data:queryForm
    })
}
/**
 * 获取未读回复评论数量
 */
export const getReplyCommentsCountApi = () => {
    return request({
        method:'GET',
        url:'/user/comment/getReplyCommentsCount',
    })
}
/**
 * 用户是否查看的枚举类
 */
export const getStatusForUserApi = () => {
    return request({
        method:'GET',
        url:'/user/comment/getStatusForUser'
    })
}
/**
 * 评论是否违规的枚举类
 */
export const getCommentStatusForComplianceApi = () => {
    return request({
        method:'GET',
        url:'/user/comment/getCommentStatusForCompliance'
    })
}
/**
 * 查看帖子更改状态已读
 * @param commentId
 */
export const changeStatusForUserApi = (commentId:number) => {
    return request({
        method:'GET',
        url:'/user/comment/changeStatusForUser',
        params:{
            commentId:commentId
        }
    })
}