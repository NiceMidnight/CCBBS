import request from "../utils/request";
import {UnwrapNestedRefs} from "vue";

export const getAllFeedbackApi = (queryForm: UnwrapNestedRefs<{
    total: number;
    data: { topicId: null; reminderStatus: null; userName: string; feedbackStatus: null };
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