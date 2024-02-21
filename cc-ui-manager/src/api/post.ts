import request from "../utils/request";
import {getCommentStatusForComplianceOptionApi} from "./commen";

/**
 * 查询数据
 */
export type QueryCondition = Partial<{
    pageNum: number
    pageSize: number
    data:{
        userName:string,
        postTitle:string,
        postContent:string,
        nickName:string
    }
}>
export type postData = {
    postId:number,
    postTitle:string,
    postContent:string
}
/**
 * 获取所有文章
 */
export const getAllPostApi = (queryCondition:QueryCondition,startTime:any,endTime:any) => {
    return request({
        method:"POST",
        url:"/manager/post/getAllPosts",
        data:queryCondition,
        params:{
            startTime:startTime,
            endTime:endTime
        }
    })
}

export const getPostStatusForComplianceOptionApi = () => {
    return request({
        method:'GET',
        url:'/manager/post/getPostStatusForComplianceOption'
    })
}

/**
 * 启用帖子
 * @param postId
 */
export const compliancePostApi = (postId: number) => {
    return request ({
        method: "GET",
        url: "/manager/post/compliancePost",
        params: {
            postId:postId
        }
    })
}

/**
 * 禁用帖子
 * @param postId
 */
export const irregularityPostApi = (postId: number) => {
    return request ({
        method: "GET",
        url:"/manager/post/irregularityPost",
        params: {
            postId:postId
        }
    })
}
/**
 * 查看帖子
 */
export const postViewApi = (postId:number) => {
    return request({
        method:"GET",
        url:`/manager/post/postView?postId=${postId}`
    })
}

export const getPostTitleApi = (postId:number) => {
    return request({
        method:'GET',
        url:`/manager/post/getPostTitle?postId=${postId}`
    })
}