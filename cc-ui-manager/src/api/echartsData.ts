import request from "../utils/request";

export const getIndexDataApi = () => {
    return request({
        method:'GET',
        url:'/manager/index/getIndexData'
    })
}