import request from "../utils/request";

/**
 * 获取踩状态
 * @param postId
 */
export const getDislikesForPostStatusApi = (postId:number) => {
    return request({
        method:'GET',
        url:'/user/dislikesForPost/getDislikesForPostStatus',
        params:{
            postId:postId
        }
    })
}
/**
 * 取消踩
 * @param postId
 */
export const cancelADislikeApi = (postId:number) => {
    return request({
        method:'GET',
        url:'/user/dislikesForPost/cancelADislike',
        params:{
            postId:postId
        }
    })
}
/**
 * 踩
 * @param postId
 */
export const giveADislikeApi = (postId:number) => {
    return request({
        method:'GET',
        url:'/user/dislikesForPost/giveADislike',
        params:{
            postId:postId
        }
    })
}