import request from "../utils/request";

export const getUserNumber = () => {
    return request({
        method:'GET',
        url:'/manager/user/getUserNumber'
    })
}