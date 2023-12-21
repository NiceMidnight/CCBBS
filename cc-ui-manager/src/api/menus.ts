import request from "../utils/request";

// 定义返回类型
type Common<T> = {
    code: string
    data: T
    msg: string
    time: string
}
export type MenuItem = {
    createdBy: string
    createdTime: string
    description: string
    href: string
    icon: string
    id: number
    level: number
    name: string
    operatorId: number | null
    orderNum: number
    parentId: number
    shown: boolean
    updateBy: string
    updatedTime: string
}

/**
 * 获取所有菜单项
 */
export const getAll = () => {
    return request<Common<MenuItem[]>>({
        method: 'GET',
        url:'/manager/menu/getAllMenu'
    })
}