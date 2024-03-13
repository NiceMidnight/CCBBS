import request from "../utils/request";
import {UnwrapNestedRefs} from "vue";

export const getArticleTopicApi = () => {
    return request({
        method:'GET',
        url:'manager/tfa/getArticleTopic'
    })
}

export const enableTopicForArticleApi = (topicId:number) => {
    return request({
        method:'GET',
        url:`manager/tfa/enableTopicForArticle?topicId=${topicId}`
    })
}

export const disableTopicForArticleApi = (topicId:number) => {
    return request({
        method:'GET',
        url:`manager/tfa/disableTopicForArticle?topicId=${topicId}`
    })
}

export const updateTopicColorApi = (topicId:number,topicColor:string) => {
    return request({
        method:'GET',
        url:'/manager/tfa/updateTopicColor',
        params:{
            topicId:topicId,
            topicColor:topicColor
        }
    })
}

export const addTopicApi = (topicData: UnwrapNestedRefs<{ topicName: string; topicColor: string }>) => {
    return request({
        method:'POST',
        url:'/manager/tfa/addTopic',
        data:topicData
    })
}