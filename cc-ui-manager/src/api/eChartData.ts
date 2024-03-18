import request from "../utils/request";

export const getUserNumber = () => {
    return request({
        method:'GET',
        url:'/manager/user/getUserNumber'
    })
}

export const getTFPEChartDataApi = () => {
    return request({
        method:'GET',
        url:'/manager/echarts/getTFPEChartData'
    })
}

export const getUserRoleEChartDataApi = () => {
    return request({
        method:'GET',
        url:'/manager/echarts/getUserRoleEChartData'
    })
}

export const getTFJEChartDataApi = () => {
    return request({
        method:'GET',
        url:'/manager/echarts/getTFJEChartData'
    })
}

export const getTFAEChartDataApi = () => {
    return request({
        method:'GET',
        url:'/manager/echarts/getTFAEChartData'
    })
}

export const getCountOfTodayDailyUniqueVisitorsApi = () => {
    return request({
        method:'GET',
        url:'/manager/echarts/getCountOfTodayDailyUniqueVisitors'
    })
}
export const getCountOfYesterdayDailyUniqueVisitorsApi = () => {
    return request({
        method:'GET',
        url:'/manager/echarts/getCountOfYesterdayDailyUniqueVisitors'
    })
}

export const getCountOfCurrentMonthDailyUniqueVisitorsLogApi = () => {
    return request({
        method:'GET',
        url:'/manager/echarts/getCountOfCurrentMonthDailyUniqueVisitorsLog'
    })
}
export const getCountOfLastMonthDailyUniqueVisitorsApi = () => {
    return request({
        method:'GET',
        url:'/manager/echarts/getCountOfLastMonthDailyUniqueVisitors'
    })
}

export const getCountOfCurrentMonthArticleApi = () => {
    return request({
        method:'GET',
        url:'/manager/echarts/getCountOfCurrentMonthArticle'
    })
}
export const getCountOfLathMonthArticleApi = () => {
    return request({
        method:'GET',
        url:'/manager/echarts/getCountOfLathMonthArticle'
    })
}

export const getCountOfCurrentWeekPostApi = () => {
    return request({
        method:'GET',
        url:'/manager/echarts/getCountOfCurrentWeekPost'
    })
}
export const getCountOfLastWeekPostApi = () => {
    return request({
        method:'GET',
        url:'/manager/echarts/getCountOfLastWeekPost'
    })
}

export const getCountOfCurrentMonthJobApi = () => {
    return request({
        method:'GET',
        url:'/manager/echarts/getCountOfCurrentMonthJob'
    })
}
export const getCountOfLastMonthJobApi = () => {
    return request({
        method:'GET',
        url:'/manager/echarts/getCountOfLastMonthJob'
    })
}

export const getCountOfCurrentWeekFeedbackApi = () => {
    return request({
        method:'GET',
        url:'/manager/echarts/getCountOfCurrentWeekFeedback'
    })
}
export const getCountOfLastWeekFeedbackApi = () => {
    return request({
        method:'GET',
        url:'/manager/echarts/getCountOfLastWeekFeedback'
    })
}