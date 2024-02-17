import request from "../utils/request";


type RegisterInfo = Partial<{
    userName:string,
    password:string,
    userPhone:string,
    nickName:string
}>

/**
 * 注册api
 * @param registerInfo
 */
export const registerApi = (registerInfo : RegisterInfo) => {
    return request({
        method:"POST",
        url:"/user/register",
        data:registerInfo
    })
}