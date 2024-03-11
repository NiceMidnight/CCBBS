import request from "../utils/request";
import {UnwrapNestedRefs} from "@vue/runtime-core";

/**
 * 提交权限申请表
 * @param applyData
 */
export const addAApplyRecruiterApi = (applyData: UnwrapNestedRefs<{ studentId: string; phone: string; truthName: string }>) => {
    return request({
        method:'POST',
        url:'/user/applyRecruiter/addAApplyRecruiter',
        data:applyData
    })
}