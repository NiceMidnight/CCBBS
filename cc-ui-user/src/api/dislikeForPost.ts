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
/**
 * 收藏状态
 * @param postId
 */
export const getFavoriteForPostStatusApi = (postId:number) => {
    return request({
        method:'GET',
        url:'/user/favoriteForPost/getFavoriteForPostStatus',
        params:{
            postId:postId
        }
    })
}
/**
 * 取消收藏
 * @param postId
 */
export const cancelAFavoriteApi = (postId:number) => {
    return request({
        method:'GET',
        url:'/user/favoriteForPost/cancelAFavorite',
        params:{
            postId:postId
        }
    })
}
/**
 * 收藏
 * @param postId
 */
export const giveAFavoriteApi = (postId:number) => {
    return request({
        method:'GET',
        url:'/user/favoriteForPost/giveAFavorite',
        params:{
            postId:postId
        }
    })
}