
// 用户登录请求
import request from "../utils/request";
import {useTokenStore} from "../stores/mytoken";

//用户登录-参数类型
type LoginInfo = {
    userName: string,
    password: string,
    code?: string
}

//用户登录-返回类型
type LoginResult = {
    code: string,
    msg: string,
    token: string,
    data: string
}
/**
 * 登录请求
 * @param loginInfo
 */
export const login = (loginInfo: LoginInfo) => {
    return request<LoginResult>({
        method:"POST",
        url:"/manager/login",
        data: loginInfo
    })
}

// const token = useTokenStore

/**
 * 获取管理员信息
 * @param token
 */
export const getInfo = (token) => {
    return request({
        method:'GET',
        url:'/manager/getInfo',
        params: { token }
    })
}




