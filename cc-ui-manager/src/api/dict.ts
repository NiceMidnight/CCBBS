import request from "../utils/request";
import {UnwrapNestedRefs} from "vue";

export type QueryCondition = Partial<{
    pageNum: number
    pageSize: number
    data: {
        dictTypeName: string
    }
}>
export type DictData = {
    dictTypeId:string,
    dictTypeName:string,
    dictItemName:string
}
/**
 * 查询数据字典
 * @param queryCondition
 */
export const getAllDict = (queryCondition: QueryCondition) => {
    return request({
        method:'POST',
        url:'/manager/dict/getAllDict',
        data:queryCondition
    })
}
/**
 * 获取字典选择器
 */
export const getDictTypeName = () => {
    return request({
        method:'GET',
        url:'/manager/dict/getDictTypeName'
    })
}
/**
 * 添加字典内容
 */
export const addDictApi = (Dict:DictData) => {
    return request({
        method:'POST',
        url:'/manager/dict/addDict',
        data:Dict
    })
}
