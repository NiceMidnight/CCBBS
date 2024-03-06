import request from "../utils/request";
import {UnwrapNestedRefs} from "@vue/runtime-core";

/**
 * 获取招聘信息
 * @param queryForm
 */
export const getJobMessageApi = (queryForm: UnwrapNestedRefs<{
    total: number;
    data: { topicId: number; nickName: string };
    pageSize: number;
    pageNum: number
}>) => {
    return request({
        method:'POST',
        url:'/user/job/getJobMessage',
        data:queryForm
    })
}
export type Job = {
    topicId:number,
    jobTitle:string,
    jobContent:string,
}
/**
 * 添加招聘信息
 * @param job
 */
export const addJobMessageApi = (job:Job) => {
    return request({
        method:'POST',
        url:'/user/job/addJobMessage',
        data:job
    })
}