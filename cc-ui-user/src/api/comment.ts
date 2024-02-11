import request from "../utils/request";

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