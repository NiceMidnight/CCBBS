import request from "../utils/request";

/**
 * 获取招聘主题
 */
export const getTopicForJobApi = () => {
    return request({
        method:'GET',
        url:'/manager/tfj/getTopicForJob'
    })
}

export const updateTopicColorApi = (row:any) => {
    return request({
        method:'POST',
        url:'/manager/tfj/updateTopicColor',
        data:row
    })
}