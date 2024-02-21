import request from "../utils/request";

/**
 * 获取帖子状态选择器
 */
export const getCommentStatusForComplianceOptionApi = () => {
    return request({
        method:'GET',
        url:'/manager/comment/getCommentStatusForComplianceOption'
    })
}

export type QueryCondition = Partial<{
    pageNum: number
    pageSize: number
    total: number
    data:{
        postId:number,
        userName:string,
        postTitle:string,
        postContent:string
    }
}>

/**
 * 获取单个帖子评论
 * @param queryCondition
 * @param startTime
 * @param endTime
 */
export const getOnePostCommentsApi = (queryCondition:QueryCondition,startTime:any,endTime:any) => {
    return request({
        method:'POST',
        url:'/manager/comment/getOnePostComments',
        data: queryCondition,
        params:{
            startTime:startTime,
            endTime:endTime
        }
    })
}

/**
 * 获取帖子评论
 * @param queryCondition
 * @param startTime
 * @param endTime
 */
export const getAllPostCommentsApi = (queryCondition:QueryCondition,startTime:any,endTime:any) => {
    return request({
        method:'POST',
        url:'/manager/comment/getAllPostComments',
        data: queryCondition,
        params:{
            startTime:startTime,
            endTime:endTime
        }
    })
}
/**
 * 启用评论（合规）
 * @param commentId
 */
export const complianceCommentApi = (commentId:number) => {
    return request({
        method:'POST',
        url:'/manager/comment/complianceComment',
        params: {
            commentId:commentId
        }
    })
}
/**
 * 禁用评论（不合规）
 * @param commentId
 */
export const irregularityCommentApi = (commentId:number) => {
    return request({
        method:'POST',
        url:'/manager/comment/irregularityComment',
        params: {
            commentId:commentId
        }
    })
}

/**
 * 查看帖子
 */
export const commentViewApi = (commentId:number) => {
    return request({
        method:"GET",
        url:`/manager/comment/commentView?commentId=${commentId}`
    })
}