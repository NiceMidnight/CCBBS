import request from "../utils/request";

export const getTopicForJobApi = () => {
    return request({
        method:'GET',
        url:'/user/tfj/getTopicForJob'
    })
}