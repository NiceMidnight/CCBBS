import request from "../utils/request";

export const getUserNumber = () => {
    return request({
        method:'GET',
        url:'/manager/user/getUserNumber'
    })
}

export const getTFPEChartDataApi = () => {
    return request({
        method:'GET',
        url:'/manager/echarts/getTFPEChartData'
    })
}

export const getUserRoleEChartDataApi = () => {
    return request({
        method:'GET',
        url:'/manager/echarts/getUserRoleEChartData'
    })
}

export const getTFJEChartDataApi = () => {
    return request({
        method:'GET',
        url:'/manager/echarts/getTFJEChartData'
    })
}

export const getTFAEChartDataApi = () => {
    return request({
        method:'GET',
        url:'/manager/echarts/getTFAEChartData'
    })
}