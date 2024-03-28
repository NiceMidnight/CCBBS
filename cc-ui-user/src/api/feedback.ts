import request from "../utils/request";
import {UnwrapNestedRefs} from "@vue/runtime-core";

export const getTFFOptionsApi = () => {
    return request({
        method:'GET',
        url:'/user/tff/getTFFOptions'
    })
}

export const getFeedbackApi = () => {
    return request({
        method:'GET',
        url:'/user/feedback/getFeedback'
    })
}

export const remindApi = (feedbackId:number) => {
    return request({
        method:'GET',
        url:`/user/feedback/remind?feedbackId=${feedbackId}`
    })
}

export const submitFeedbackApi = (feedbackForm: UnwrapNestedRefs<{ topicId: null; feedbackContent: string }>) => {
    return request({
        method:'POST',
        url:'/user/feedback/submitFeedback',
        data:feedbackForm
    })
}