import { defineStore } from "pinia";
import { ref } from "vue";

// 存储token
// 创建一个存储token的store
export const useTokenStore = defineStore('mytoken', () => {
    const token = ref<string | null>(window.localStorage.getItem("TokenInfo") || '');
    // const expirationTime = ref<number | null>(parseInt(window.localStorage.getItem("TokenExpiration") || '0'));
    //
    // const EXPIRATION_TIME_MINUTES = 1; // 固定的过期时间为30分钟

    // 用于保存传递的字符串
    function saveToken(data: string) {
        // const currentTime = Date.now();
        // @ts-ignore
        // const expirationTimeMillis = currentTime + EXPIRATION_TIME_MINUTES * 60 * 1000;

        token.value = data;
        // @ts-ignore
        // expirationTime.value = expirationTimeMillis

        updateLocalStorage(data); // 更新本地存储
    }

    // 用于更新本地存储的数据
    function updateLocalStorage(data: string) {
        window.localStorage.setItem("TokenInfo", data);
        // window.localStorage.setItem("TokenExpiration",expirationTimeMillis.toString())
    }

    // 用于获取已保存的字符串
    function getToken(): string | null {
        // const currentTime = Date.now()
        // const expirationTimeMillis = expirationTime.value || 0;

        // if (currentTime < expirationTimeMillis) {
        //     // Token 未过期，返回token值
        //     return token.value;
        // } else {
        //     // Token 已过期，清除token和过期时间，并返回null
        //     clearToken();
        //     return null;
        // }
        return token.value;
    }
    //  清空本地token
        function clearToken() {
        token.value = null;
        // expirationTime.value = null;
        window.localStorage.removeItem("TokenInfo");
        // window.localStorage.removeItem("TokenExpiration");
    }

    const loginToken = ref()
    function getLoginToken() {
        loginToken.value = localStorage.getItem("TokenInfo")
    }
    // 向外暴露
    return { token, saveToken, getToken,loginToken,getLoginToken,clearToken };
});

// export const useTokenStore = defineStore('mytoken', () => {
//     // ref ===  state
//     const tokenJson = ref("")
//     // computed  ===  getters
//     const token = computed(() => {
//         try {
//             return JSON.parse(tokenJson.value || "{}")
//         } catch (e) {
//             // 这里可以根据需要处理非 JSON 字符串的情况，并给予用户一个错误提示
//             ElMessage.error("tokenJson不是有效的JSON字符串")
//             return {} // 返回一个默认的空对象，以防止错误继续传播
//         }
//     })
//     // function  ===   actions
//     function saveToken(data: string | object) {
//         if (typeof data === 'string') {
//             tokenJson.value = data; // 对于字符串，直接保存
//         } else {
//             tokenJson.value = JSON.stringify(data); // 对于对象，转换为 JSON 字符串后保存
//         }
//     }
//
//     //向外暴露
//     return { token, saveToken }
// })