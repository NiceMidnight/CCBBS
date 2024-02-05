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
export const cancelALikeApi = (postId:number) => {
    return request({
        method:'GET',
        url:'/user/dislikesForPost/cancelADislike',
        params:{
            postId:postId
        }
    })
}
/**
 * 赞
 * @param postId
 */
export const giveADislikeApi = (postId:number) => {
    return request({
        method:'GET',
        url:'/user/likesForPost/giveADislike',
        params:{
            postId:postId
        }
    })
}