import request from "../utils/request";

export const getCommentCountApi = (postId:number) => {
    return request({
        method:'GET',
        url:'/user/comment/getCommentCount',
        params:{
            postId:postId
        }
    })
}