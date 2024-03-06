import request from "../utils/request";
import {UnwrapNestedRefs} from "@vue/runtime-core";

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
export type QueryCondition = Partial<{
    pageNum: number
    pageSize: number
    total: number
    data: {
    }
}>
/**
 * 获取关注用户
 * @param queryForm
 */
export const getFollowUsersApi = (queryForm: QueryCondition) => {
    return request({
        method:'POST',
        url:'/user/follow/getFollowUsers',
        data:queryForm
    })
}
/**
 * 获取粉丝用户
 * @param queryForm
 */
export const getFanUsersApi = (queryForm: QueryCondition) => {
    return request({
        method:'POST',
        url:'/user/follow/getFanUsers',
        data:queryForm
    })
}
