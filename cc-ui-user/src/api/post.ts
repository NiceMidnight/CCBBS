import request from "../utils/request";
/**
 * 查询数据
 */
export type QueryCondition = Partial<{
    pageNum: number
    pageSize: number
    total: number
}>
export type Post = {
    postId: number,
    postTitle: string,
    postContent: string
}
export const getAllPostApi = (queryCondition:QueryCondition,postMsg:string) => {
    return request({
        method:"POST",
        url:"/user/resources/getPost",
        data:queryCondition,
        params:{
            postMsg:postMsg
        }
    })
}