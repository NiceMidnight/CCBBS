import request from "../utils/request";
import {UnwrapRef} from "vue";

export const getTFFOptionsApi = () => {
    return request({
        method:'GET',
        url:"/manager/tff/getTFFOptions"
    })
}

export const getTopicStatusApi = () => {
    return request({
        method:'GET',
        url:'/manager/tff/getTopicStatus'
    })
}

export const getTopicForFeedbackApi = (topicStatus: string) => {
    return request({
        method:'GET',
        url:'/manager/tff/getTopicForFeedback',
        params:{
            topicStatus:topicStatus
        }
    })
}

export const enableTopicApi = (topicId:number) => {
    return request({
        method:'GET',
        url:`/manager/tff/enableTopic?topicId=${topicId}`
    })
}

export const disableTopicApi = (topicId:number) => {
    return request({
        method:'GET',
        url:`/manager/tff/disableTopic?topicId=${topicId}`
    })
}

export const updateTFFColorApi = (topicId:number,topicColor:string) => {
    return request({
        method:"GET",
        url:'/manager/tff/updateTFFColor',
        params:{
            topicId:topicId,
            topicColor:topicColor
        }
    })
}