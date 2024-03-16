import request from "../utils/request";


export type QueryCondition = Partial<{
    pageNum: number
    pageSize: number
    // tel: string
    // userId: string
    data:{
        userName:string
        userSex:string
    }

}>
/**
 * 获取用户列表信息
 * @param queryCondition
 * @param startTime
 * @param endTime
 */
export const getAllUsers = (queryCondition: QueryCondition = {},startTime:any,endTime:any) => {
    return request({
        method: 'POST',
        url: '/manager/user/getAllUsers',
        data: queryCondition,
        params:{
            startTime:startTime,
            endTime:endTime
        }
    })
}
export const getUserStatusOptionsApi = () => {
    return request({
        method: 'GET',
        url: '/manager/user/getUserStatusOptions'
    })
}
/**
 * 启用用户
 * @param userId
 */
export const enableUser = (userId: number) => {
    return request ({
        method: "GET",
        url: "/manager/user/enableUser",
        params: {
            // userId:userId
            id:userId
        }
    })
}

/**
 * 禁用用户
 * @param userId
 */
export const forbidUser = (userId: number) => {
    return request ({
        method: "POST",
        url:"/manager/user/forbidUser",
        params: {
            // userId:userId
            id:userId
        }
    })
}
/**
 * 获取单个用户名称
 */
export const getUserName = () => {
    return request({
        method:'GET',
        url:`/manager/getUserName`
    })
}

/**
 * 获取用户信息
 * @param id
 */
export const getUserInfo = (id:number) => {
    return request({
        method:"GET",
        url:'/manager/user/getUserInfo',
        params:{
            id:id
        }
    })
}



