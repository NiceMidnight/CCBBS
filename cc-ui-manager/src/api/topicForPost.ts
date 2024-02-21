import request from "../utils/request";
export type QueryCondition = Partial<{
    pageNum: number
    pageSize: number
    data: {
        topicName: string
    }
}>
export type AddTFPData = {
    topicName:string,
    topicStatus:string,
}
/**
 * 获取所有主题
 * @param queryCondition
 */
export const getTFPApi = (queryCondition:QueryCondition) => {
    return request({
        method:'POST',
        url:'/manager/tfp/getTFP',
        data:queryCondition
    })
}
export const getTFPStatusApi = () => {
    return request({
        method:'GET',
        url:'/manager/tfp/getTFPStatus'
    })
}
/**
 * 添加主题
 * @param addTFPData
 */
export const addTopicApi = (addTFPData:AddTFPData) => {
    return request({
        method:'POST',
        url:'/manager/tfp/addTFP',
        data: addTFPData
    })
}

/**
 * 启用主题
 * @param topicId
 */
export const enableTopicApi = ( topicId:number ) => {
    return request({
        method:'GET',
        url:'/manager/tfp/enableTFP',
        params: {
            topicId: topicId
        }
    })
}
/**
 * 禁用主题
 * @param topicId
 */
export const disableTopicApi = ( topicId:number ) => {
    return request({
        method:'GET',
        url:'/manager/tfp/disableTFP',
        params: {
            topicId: topicId
        }
    })
}
/**
 * 删除主题
 * @param topicId
 */
export const deleteTopicApi = ( topicId:number ) => {
    return request({
        method:'GET',
        url:'/manager/tfp/deleteTopic',
        params: {
            topicId:topicId
        }
    })
}
/**
 * 获取主题
 * @param topicId
 */
export const getTopicByIdApi = (topicId:number) => {
    return request({
        method:'GET',
        url:`/manager/tfp/getTopicById`,
        params:{
            topicId:topicId
        }
    })
}
/**
 * 提交编辑主题
 * @param topicName
 */
export const editTopicApi = (topicName:string,topicId:number) => {
    return request({
        method:'GET',
        url:'/manager/tfp/editTopic',
        params:{
            topicName:topicName,
            topicId:topicId
        }
    })
}