import request from "../utils/request";

export const getArticleTopicApi = () => {
    return request({
        method:'GET',
        url:'manager/tfa/getArticleTopic'
    })
}