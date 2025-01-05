import axios from 'axios';
import qs from 'query-string';
import type {DescData} from '@arco-design/web-vue/es/descriptions/interface';
import {Constant} from '@/api/constant';

export interface ToggleEnableStateRequest {
    id: string;
    is_enabled: number;
}

export interface IdRequest {
    id: string;
}

export function fetchPlatforms(params?: any) {
    return axios.get<any>(
        `${Constant.API_PREFIX}/platform/platforms`,
        {
            params,
            paramsSerializer: (obj) => {
                return qs.stringify(obj);
            },
        }
    );
}

export function toggleEnableState(route: string, request: ToggleEnableStateRequest) {
    return axios.post<any>(
        `${Constant.API_PREFIX}/${route}/toggle-enable`,
        request
    );
}

export function getDetail(route: string, request: IdRequest) {
    return axios.get<any>(
        `${Constant.API_PREFIX}/${route}/detail`,
        {
            params: request,
            paramsSerializer: (obj) => {
                return qs.stringify(obj);
            },
        }
    );
}

export function createPlatform(request: any) {
    return axios.post<any>(
        `${Constant.API_PREFIX}/platform/platforms`,
        request
    );
}

export function getConfig(routes: string, id: string) {
    return axios.get<any>(
        `${Constant.API_PREFIX}/config/${routes}/configs/${id}`
    );
}

export function updatePlatform(request: any) {
    return axios.put<any>(
        `${Constant.API_PREFIX}/platform/platforms`,
        request
    );
}


export function createAiConfig(request: any) {
    return axios.post<any>(
        `${Constant.API_PREFIX}/config/ai/configs`,
        request
    );
}
