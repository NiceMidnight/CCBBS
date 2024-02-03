import request from "../utils/request";


export const getFollowStatusApi = (followingId:number) => {
    return request({
        method:'GET',
        url:'/user/follow/getFollowStatus',
        params:{
            followingId:followingId
        }
    })
}
export const cancelFollowApi = (followingId:number) => {
    return request({
        method:'GET',
        url:'/user/follow/cancelFollow',
        params:{
            followingId:followingId
        }
    })
}
export const followApi = ( followingId:number ) => {
    return request({
        method:'GET',
        url:'/user/follow/following',
        params:{
            followingId:followingId
        }
    })
}
