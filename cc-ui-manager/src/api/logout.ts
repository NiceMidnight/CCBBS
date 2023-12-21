import request from "../utils/request";

export type LogoutInfo = {
    code: string,
    msg: string

}
/**
 * 登出
 */
export const logout = () => {
    return request ({
        method:"GET",
        url:"/manager/logout"
    })
}