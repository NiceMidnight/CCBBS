import request from "../utils/request";

/**
 * 获取点赞状态
 * @param postId
 */
export const getLikesForPostStatusApi = (postId:number) => {
    return request({
        method:'GET',
        url:'/user/likesForPost/getLikesForPostStatus',
        params:{
            postId:postId
        }
    })
}
/**
 * 取消赞
 * @param postId
 */
export const cancelALikeApi = (postId:number) => {
    return request({
        method:'GET',
        url:'/user/likesForPost/cancelALike',
        params:{
            postId:postId
        }
    })
}
/**
 * 点赞
 * @param postId
 */
export const giveALikeApi = (postId:number) => {
    return request({
        method:'GET',
        url:'/user/likesForPost/giveALike',
        params:{
            postId:postId
        }
    })
}
