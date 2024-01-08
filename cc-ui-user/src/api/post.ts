import request from "../utils/request";
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

export const getPostTopicApi = () => {
    return request({
        method:"GET",
        url:"/user/tfp/getPostTopic",

    })
}