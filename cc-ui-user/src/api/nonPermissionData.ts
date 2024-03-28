import request from "../utils/request";

export const getTheNumOfLoginsApi = () => {
    return request({
        method:'GET',
        url:'/user/resources/getTheNumOfLogins'
    })
}

export const getVisitsApi = () => {
    return request({
        method:'GET',
        url:'/user/resources/getVisits'
    })
}