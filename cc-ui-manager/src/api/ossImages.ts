import request from "../utils/request";
export type QueryCondition = Partial<{
    pageNum: number
    pageSize: number
    data:{
        imgName:string,
    }
}>
export const getOssImagesApi = (queryForm:QueryCondition,startTime:any,endTime:any) => {
    return request({
        method:'POST',
        url:'/manager/ossImages/getOssImages',
        data:queryForm,
        params:{
            startTime:startTime,
            endTime:endTime
        }
    })
}

export const deleteOssImageByIdApi = (imageId:number) => {
    return request({
        method:'GET',
        url:'/manager/ossImages/deleteOssImageById',
        params:{
            imgId:imageId
        }
    })
}