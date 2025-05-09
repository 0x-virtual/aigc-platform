import axios from 'axios';
import type {AxiosRequestConfig, AxiosResponse} from 'axios';
import {Message, Modal} from '@arco-design/web-vue';
import {useUserStore} from '@/store';
import {getToken} from '@/utils/auth';
import {decrypt, encrypt} from "@/api/ai-client";
import debug from "@/utils/env";

export interface HttpResponse<T = unknown> {
    status: number;
    message: string;
    code: number;
    data: T;
}

const key = "yWnsPUaifnu8v98UEcx1TjDIXh5Cjvp3"
if (import.meta.env.VITE_API_BASE_URL) {
    axios.defaults.baseURL = import.meta.env.VITE_API_BASE_URL;
}

axios.interceptors.request.use(
    (config: AxiosRequestConfig) => {
        // let each request carry token
        // this example using the JWT token
        // Authorization is a custom headers key
        // please modify it according to the actual situation
        const token = getToken();
        if (token) {
            if (!config.headers) {
                config.headers = {};
            }
            config.headers.Authorization = `Bearer ${token}`;
        }
        if (!debug) {
            // 加密 URL 参数
            if (config.params) {
                config.params = {
                    e: encrypt(JSON.stringify(config.params), key)
                };
            }

            // 加密请求体
            if (config.data) {
                config.data = { e: encrypt(JSON.stringify(config.data), key)};
            }
        }


        return config;
    },
    (error) => {
        // do something
        return Promise.reject(error);
    }
);
// add response interceptors
axios.interceptors.response.use(
    (response: AxiosResponse<HttpResponse>) => {
        // TODO key from server
        console.log( response.data, " response.data")

        const decryptedData = decrypt(response.data, key);
        response.data = typeof decryptedData === "string" ? JSON.parse(decryptedData) : decryptedData;
        console.log( response.data, " response.data2")
        // if the custom code is not 20000, it is judged as an error.
        if (response.data.code !== 20000 && response.data.code !== 200) {
            Message.error({
                content: response.data.message || 'Error',
                duration: 5 * 1000,
            });
            // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
            if (
                [50008, 50012, 50014].includes(response.data.code) &&
                response.config.url !== '/api/user/info'
            ) {
                Modal.error({
                    title: 'Confirm logout',
                    content:
                        'You have been logged out, you can cancel to stay on this page, or log in again',
                    okText: 'Re-Login',
                    async onOk() {
                        const userStore = useUserStore();

                        await userStore.logout();
                        window.location.reload();
                    },
                });
            }
            return Promise.reject(new Error((response.data as any)?.msg || 'Error'));
        }
        return response.data;
    },
    (error) => {
        Message.error({
            content: error.msg || 'Request Error',
            duration: 5 * 1000,
        });
        return Promise.reject(error);
    }
);
