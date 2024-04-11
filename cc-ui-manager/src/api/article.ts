import request from "../utils/request";
import {UnwrapNestedRefs} from "vue";

export type QueryCondition = Partial<{
    pageNum: number
    pageSize: number
    data: {

    }
}>

export type articleData = {
    articleTitle:string,
    articleContent:string
}
/**
 * 获得所有文章
 * @param queryCondition
 */
export const getAllArticle = (queryCondition : QueryCondition,startTime:any,endTime:any) => {
    return request({
        method:"POST",
        url:"/manager/article/getAllArticle",
        data:queryCondition,
        params:{
            startTime:startTime,
            endTime:endTime
        }
    })
}
/**
 * 添加文章
 * @param articleData
 * @param loginToken
 */
export const addArticleApi = (articleData:articleData) => {
    return request({
        method:'POST',
        url:'/manager/article/addArticle',
        data: articleData,
    })
}
/**
 * 删除某篇文章
 */
export const deleteArticleApi = (id:number) => {
    return request({
        method:"POST",
        url:"/manager/article/deleteArticle",
        params:{
            id:id
        }
    })
}
/**
 * 编辑文章
 */
export type EditArticleData = {
    createdBy:string
    updatedBy:string
    articleId: string
    articleTitle: string
    articleContent: string
}
export const editArticleApi = (editArticleData:EditArticleData) => {
    return request({
        method:"POST",
        url:"/manager/article/editArticle",
        data:editArticleData,
    })
}
/**
 * 查看文章
 */
export const articleViewApi = (articleId:number) => {
    return request({
        method:'GET',
        url:`/manager/article/articleView?articleId=${articleId}`
    })
}
/**
 * 获取单个文章所有数据
 */
export const getOneArticleApi = (articleId:number) => {
    return request({
        method:'GET',
        url:`/manager/article/getOneByArticleId?articleId=${articleId}`
    })
}
/**
 * 使公告公开
 * @param articleId
 */
export const makeAnArticlePublicApi = (articleId:number) => {
    return request({
        method:'GET',
        url:`/manager/article/makeAnArticlePublic?articleId=${articleId}`
    })
}
/**
 * 使公告私有
 * @param articleId
 */
export const makeAnArticlePrivateApi = (articleId:number) => {
    return request({
        method:'GET',
        url:`/manager/article/makeAnArticlePrivate?articleId=${articleId}`
    })
}