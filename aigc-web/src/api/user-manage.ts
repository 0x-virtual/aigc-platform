import axios from 'axios';
import qs from 'query-string';
import type {DescData} from '@arco-design/web-vue/es/descriptions/interface';
import {Constant} from '@/api/constant';
import {ToggleAiConfigIsEnableRequest} from "@/api/config";

export interface ToggleEnableStateRequest {
    id: string;
    is_enabled: number;
}

export interface IdRequest {
    id: string;
}


export function createUser(request: any) {
    return axios.post<any>(
        `${Constant.API_PREFIX}/user/users`,
        request
    );
}

export function createAiConfig(request: any) {
    return axios.post<any>(
        `${Constant.API_PREFIX}/config/ai/configs`,
        request
    );
}
