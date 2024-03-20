import request from "../utils/request";
import {UnwrapNestedRefs} from "vue";

export const getAllFeedbackApi = (queryForm: UnwrapNestedRefs<{
    total: number;
    data: { topicId: null; reminderStatus: null; creatorName: string; feedbackStatus: null };
    pageSize: number;
    pageNum: number
}>, startTime:any, endTime: any) => {
    return request({
        method:'POST',
        url:'/manager/feedback/getAllFeedback',
        data:queryForm,
        params:{
            startTime:startTime,
            endTime:endTime
        }
    })
}

export const getFeedbackStatusOptionApi = () => {
    return request({
        method:'GET',
        url:'/manager/feedback/getFeedbackStatusOption'
    })
}
export const getReminderStatusOptionApi = () => {
    return request({
        method:'GET',
        url:'/manager/feedback/getReminderStatusOption'
    })
}

export const getFeedbackViewApi = (feedbackId:number) => {
    return request({
        method:'GET',
        url:`/manager/feedback/getFeedbackView?feedbackId=${feedbackId}`
    })
}

export const upProgressApi = (feedbackId: number, feedbackStatus: string) => {
    return request({
        method:'GET',
        url:'/manager/feedback/upProgress',
        params:{
            feedbackId:feedbackId,
            feedbackStatus:feedbackStatus
        }
    })
}
export const downProgressApi = (feedbackId: number, feedbackStatus: string) => {
    return request({
        method:'GET',
        url:'/manager/feedback/downProgress',
        params:{
            feedbackId:feedbackId,
            feedbackStatus:feedbackStatus
        }
    })
}