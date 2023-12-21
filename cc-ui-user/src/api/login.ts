
// 用户登录请求
// @ts-ignore
import request from "../utils/request";
import {UnwrapRef} from "vue";

//用户登录-参数类型
type LoginInfo = {
    user:{
        userName: string,
        password: string,
    }
    code:{
        code:string
    }
}

//用户登录-返回类型
type LoginResult = {
    code: string,
    msg: string,
    token: string,
    data: string
}
type userInfo = {
    user: {
        userName: string,
        userPhone: string,
        userAddress: string,
        userPersonality: string,
        userHead:string,
        userAge:string,
        userSex:number
    }
}
/**
 * 登录请求api
 * @param user
 * @param code
 */
export const loginApi = (user: UnwrapRef<{ password: string; userName: string }>, code: UnwrapRef<string>) => {
    return request<LoginResult>({
        method:"POST",
        url:"/user/login",
        data: user,
        params:{
            code:code
        }
    })
}

/**
 * 获取信息api
 * @param token
 */
export const getUserInfo = (loginToken:string) => {
    return request({
        method:'GET',
        url:'/user/getUserInfo',
        params: { loginToken }
    })
}

/**
 * 更新个人信息
 */
export const updateUserInfoApi = (user:userInfo) => {
    return request({
        method:'POST',
        url:'/user/updateUserInfo',
        data:user
    })
}




