import request from "../utils/request";
import {UnwrapNestedRefs} from "vue";

/**
 * 获取申请信息
 * @param queryForm
 * @param startTime
 * @param endTime
 */
export const getApplyRecruiterMessageApi = (queryForm: UnwrapNestedRefs<{
    total: number;
    data: { studentId: string; nickName: string; truthName: string; userName: string; applyStatus: null };
    pageSize: number;
    pageNum: number
}>,startTime:any,endTime:any) => {
    return request({
        method:'POST',
        url:'/manager/applyRecruiter/getApplyRecruiterMessage',
        data:queryForm,
        params:{
            startTime:startTime,
            endTime:endTime
        }
    })
}
/**
 * 获取申请状态选择器
 */
export const getApplyStatusOptionsApi = () => {
    return request({
        method:'GET',
        url:'/manager/applyRecruiter/getApplyStatusOptions'
    })
}
/**
 * 合规
 * @param applyId
 */
export const complianceApplyApi = (applyId: number,userId:number) => {
    return request ({
        method: "GET",
        url: "/manager/applyRecruiter/complianceApply",
        params: {
            applyId:applyId,
            userId:userId
        }
    })
}

/**
 * 不合规
 * @param applyId
 */
export const irregularityApplyApi = (applyId: number,userId:number) => {
    return request ({
        method: "GET",
        url:"/manager/applyRecruiter/irregularityApply",
        params: {
            applyId:applyId,
            userId:userId
        }
    })
}
/**
 * 获取单个申请信息
 * @param applyId
 */
export const getOneApplyMessageApi = (applyId:number) => {
    return request({
        method:'GET',
        url:`/manager/applyRecruiter/getOneApplyMessage?applyId=${applyId}`
    })
}

export const updateApplyNotesApi = (applyId:number,notes:string) => {
    return request({
        method:'',
        url:'/manager/applyRecruiter/updateApplyNotes',
        params:{
            applyId:applyId,
            notes:notes
        }
    })
}
