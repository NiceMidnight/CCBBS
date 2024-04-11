import request from "../utils/request";
export type QueryCondition = Partial<{
    pageNum: number
    pageSize: number
    total: number
    data: {
        articleTitle: string,
        topicId:number
    }
}>
/**
 * 获取首页文章信息
 * @param queryCondition
 */
export const getAllArticle = (queryCondition:QueryCondition) => {
    return request({
        method:"POST",
        url:'/user/resources/getIndexArticle',
        data: queryCondition,
    })
}
export const getHotArticleApi = () => {
    return request({
        method:'GET',
        url:'/user/resources/getHotArticle'
    })
}
/**
 * 文章标题链接查看对应文章全部信息
 */
export const getArticle = (queryCondition:number) => {
    return request({
        method:"POST",
        url:'/user/resources/getArticle',
        params:{
            articleId:queryCondition
        }
    })
}

export const getTopicForArticleOptionsApi = () => {
    return request({
        method:"GET",
        url:'/user/resources/getTopicForArticleOptions',
    })
}