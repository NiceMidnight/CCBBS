import axios from "axios";
import {ElMessage} from "element-plus";

// export const baseUrl = 'http://localhost:8081';
export const baseUrl = 'http://8.138.1.83:8081';
// 创建一个取消请求的 CancelToken
// @ts-ignore
const request = axios.create({
    //访问后端基本地址VITE_API_URL
    baseURL:baseUrl,
    timeout: 30000,      //超时
})

//HTTP请求头拦截处理并处理为json格式
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8'
    const token = localStorage.getItem("TokenInfo");
    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config
},error => {
    console.log('请求错误: ' + error) //for error debug
    // @ts-ignore
    return Promise.reject(error)
})

//返回结果拦截处理
request.interceptors.response.use(response => {
    // res.code
    return response.data
}, error => {
    console.log('请求错误: ' + error); // for error debug

    // 如果是401错误（未授权），显示自定义错误提示
    if (error.response && error.response.status === 401 && !axios.isCancel(error)) {
        const errorMessage = error.response.data && error.response.data.error
            ? error.response.data.error
            : '身份令牌丢失或验证无效，请登陆后查看...'; // 使用默认错误消息

        ElMessage.error(errorMessage); // 在实际应用中，你可以使用更合适的方式来显示错误提示
    }

    // @ts-ignore
    return Promise.reject(error);
})

export default request