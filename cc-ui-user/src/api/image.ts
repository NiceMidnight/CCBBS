import request from "../utils/request";

/**
 * 获取首页走马灯图片
 */
export const getIndexImage = () => {
    return request({
        method:"GET",
        url:"user/resources/getIndexImage",
    })
}