import request from "../utils/request";

export const getTopicForJobApi = () => {
    return request({
        method:'GET',
        url:'/manager/tfj/getTopicForJob'
    })
}