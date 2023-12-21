import {dayjs} from "element-plus";

export const timeHandler = (row: any,column: any,value: string) => {
    if (value) {
        return dayjs(value).format("YYYY-MM-DD HH:mm:ss")
    }
    else return null
}