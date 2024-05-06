import request from "../utils/request";
import {UnwrapNestedRefs} from "@vue/runtime-core";

export const getFavoritePostApi = (queryForm: UnwrapNestedRefs<{
    total: number;
    data: {};
    pageSize: number;
    pageNum: number
}>) => {
    return request({
        method:'POST',
        url:'/user/favoriteForPost/getFavoritePost',
        data:queryForm
    })
}