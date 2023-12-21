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
 */
export const getAllUsers = (queryCondition: QueryCondition = {}) => {
    return request({
        method: 'POST',
        url: '/manager/user/getAllUsers',
        data: queryCondition,
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
export const getUserName = (token:string) => {
    return request({
        method:'GET',
        url:`/manager/getUserName?token=${token}`
    })
}


