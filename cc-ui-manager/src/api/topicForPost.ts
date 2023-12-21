import request from "../utils/request";
export type QueryCondition = Partial<{
    pageNum: number
    pageSize: number
    data: {
        topicName: string
    }
}>
export const getTFPApi = (queryCondition:QueryCondition) => {
    return request({
        method:'POST',
        url:'/manager/tfp/getTFP',
        data:queryCondition
    })
}
export const enableTopicApi = ( topicId:number ) => {
    return request({
        method:'GET',
        url:'/manager/tfp/enableTFP',
        params: {
            topicId: topicId
        }
    })
}

export const disableTopicApi = ( topicId:number ) => {
    return request({
        method:'GET',
        url:'/manager/tfp/disableTFP',
        params: {
            topicId: topicId
        }
    })
}

export const deleteTopicApi = ( topicId:number ) => {
    return request({
        method:'GET',
        url:'/manager/tfp/deleteTopic',
        params: {
            topicId:topicId
        }
    })
}