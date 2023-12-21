import {dayjs} from "element-plus";

export const timeHandler = (row: any,colunm: any,value: string) => {
    return dayjs(value).format("YYYY-MM-DD HH:mm:ss")
}