import request from "../utils/request";


type RegisterInfo = {
    userName:string,
    password:string,
    userPhone:string,
    userEmail:string
}

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