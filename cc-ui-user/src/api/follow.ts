import request from "../utils/request";

/**
 * 获取关注状态
 * @param followingId
 */
export const getFollowStatusApi = (followingId:number) => {
    return request({
        method:'GET',
        url:'/user/follow/getFollowStatus',
        params:{
            followingId:followingId
        }
    })
}

/**
 * 关注用户
 * @param followingId
 */
export const followApi = ( followingId:number ) => {
    return request({
        method:'GET',
        url:'/user/follow/following',
        params:{
            followingId:followingId
        }
    })
}
/**
 * 取消关注
 * @param followingId
 */
export const cancelFollowApi = (followingId:number) => {
    return request({
        method:'GET',
        url:'/user/follow/cancelFollow',
        params:{
            followingId:followingId
        }
    })
}

