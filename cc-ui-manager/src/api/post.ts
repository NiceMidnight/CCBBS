import request from "../utils/request";

/**
 * 查询数据
 */
export type QueryCondition = Partial<{
    pageNum: number
    pageSize: number
    data: {

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
export const getAllPostApi = (queryCondition:QueryCondition) => {
    return request({
        method:"POST",
        url:"/manager/post/getAllPosts",
        data:queryCondition
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