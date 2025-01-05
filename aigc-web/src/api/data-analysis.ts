import axios from 'axios';
import {Constant} from "@/api/constant";
import qs from "query-string";

export function getTaskDataAnalysis(params?: any) {
    return axios.get<any>(
        `${Constant.API_PREFIX}/analysis/task`,
        {
            params,
            paramsSerializer: (obj) => {
                return qs.stringify(obj);
            },
        }
    );
}

export function getTaskDataAnalysisToday(params?: any) {
    return axios.get<any>(
        `${Constant.API_PREFIX}/analysis/task/today`,
        {
            params,
            paramsSerializer: (obj) => {
                return qs.stringify(obj);
            },
        }
    );
}

export function queryMyProjectList1() {
    return axios.post('/api/user/my-project/list');
}
