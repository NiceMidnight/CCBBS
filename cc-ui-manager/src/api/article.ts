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
export const getAllArticle = (queryCondition : QueryCondition) => {
    return request({
        method:"POST",
        url:"/manager/article/getAllArticle",
        data:queryCondition
    })
}
/**
 * 添加文章
 * @param articleData
 * @param loginToken
 */
export const addArticleApi = (articleData:articleData,loginToken:string) => {
    return request({
        method:'POST',
        url:'/manager/article/addArticle',
        data: articleData,
        params:{
            loginToken:loginToken
        }
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
export const editArticleApi = (editArticleData:EditArticleData,loginToken:string) => {
    return request({
        method:"POST",
        url:"/manager/article/editArticle",
        data:editArticleData,
        params:{
            loginToken:loginToken
        }
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

