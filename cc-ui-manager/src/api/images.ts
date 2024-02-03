
import request from "../utils/request";

export type QueryCondition = Partial<{
    pageNum: number
    pageSize: number
    data: {
        type:string
    }
}>
/**
 * 获取所有图片资源
 * @param queryCondition
 */
export const getAllImages = (queryCondition: QueryCondition = {}) => {
    return request({
        method:'POST',
        url:'/manager/images/getAllImages',
        data:queryCondition
    })
}
/**
 * 设置图片可见
 * @param imageId
 */
export const visible = (imageId: number) => {
    return request({
        method:"GET",
        url:"/manager/images/visible",
        params:{
            id:imageId
        }
    })
}
/**
 * 设置图片不可见
 * @param imageId
 */
export const inVisible = (imageId:number) => {
    return request({
        method:"POST",
        url:"/manager/images/inVisible",
        params:{
            id:imageId
        }
    })
}
/**
 * 获取图片选择器内容
 */
export const getSysImgOptionApi = () => {
    return request({
        method:"GET",
        url:"/manager/images/getSysImgOption"
    })
}
/**
 * 图片提交数据
 */
export type AddSysImgData = {
    imgName: string;
    imgPath: string;
    userName: string;
    type: string
}