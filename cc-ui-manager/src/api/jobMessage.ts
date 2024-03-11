import request from "../utils/request";
import {UnwrapNestedRefs} from "vue";

export const getJobStatusApi = () => {
    return request({
        method:'GET',
        url:'/manager/job/getJobStatus'
    })
}
export const getJobVisibilityApi = () => {
    return request({
        method:'GET',
        url:'/manager/job/getJobVisibility'
    })
}
export const getJobMessageApi = (searchData: UnwrapNestedRefs<{
    total: number;
    data: {};
    pageSize: number;
    pageNum: number
}>, startTime:any, endTime: any) => {
    return request({
        method:'POST',
        url:'/manager/job/getJobMessage',
        data:searchData,
        params:{
            startTime:startTime,
            endTime:endTime
        }
    })
}

export const complianceJobApi = (jobId:number) => {
    return request ({
        method: "GET",
        url: "/manager/job/complianceJob",
        params: {
            jobId:jobId,
        }
    })
}
export const irregularityJobApi = (jobId:number) => {
    return request ({
        method: "GET",
        url: "/manager/job/irregularityJob",
        params: {
            jobId:jobId,
        }
    })
}

export const jobViewApi = (jobId:number) => {
    return request({
        method:'GET',
        url: "/manager/job/jobView",
        params: {
            jobId:jobId,
        }
    })
}

