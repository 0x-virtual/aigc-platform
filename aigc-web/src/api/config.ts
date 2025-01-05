import axios from 'axios';
import qs from 'query-string';
import type {DescData} from '@arco-design/web-vue/es/descriptions/interface';
import {Constant} from '@/api/constant';

export interface PolicyRecord {
    id: string;
    number: number;
    name: string;
    contentType: 'img' | 'horizontalVideo' | 'verticalVideo';
    filterType: 'artificial' | 'rules';
    count: number;
    status: 'online' | 'offline';
    createdTime: string;
}

export interface GetConfigListQuery extends Partial<PolicyRecord> {
    pi?: number;
    ps?: number;
    platform_id?: string;
    is_enabled?: string|number;
    site_id?: string;
    task_category?: string
}

export interface CreateTaskConfigRequest {
    platform_id: string;
    site_id: string;
    task_category: string;
    configuration: {
        post_creator_accounts: any[];
        comment_accounts: any[];
        prompt: string;
        faq_prompt: string;
    }
}

export interface CreatePromptConfigRequest {
    platform_id: string;
    site_id: string;
    task_category: string;
    remark: string;
    configuration: {
        prompt: string;
        faq_prompt: string;
    }
}

export interface CreateAiConfigRequest {
    platform_id: string;
    provider: string;
    base_url: string;
    gpt_type: string;
    api_key: string;
    model: string;
}

export interface CreateSiteConfigRequest {
    platform_id: string;
    base_url: string;
    site_name: string;
    admin_account: any;
    max_parallel_limit: number;
}

export interface PartialUpdateTaskConfigRequest {
    config_id: string;
    post_creator_accounts: any[];
    comment_accounts: any[];
}

export interface ConfigRequest {
    config_id: string;
}

export interface ToggleAiConfigIsEnableRequest {
    config_id: string;
    is_enabled: number;
}

export interface PartialUpdateAiConfigRequest {
    config_id: string;
    provider: string;
    base_url: string;
    api_key: string;
    model: string;
}

export interface PartialUpdatePromptConfigRequest {
    config_id: string;
    remark: string;
    configuration: any;
}

export interface PartialUpdateSiteConfigRequest {
    config_id: string;
    base_url: string;
    admin_account: any;
    max_parallel_limit: number;
}

export interface PolicyListRes {
    value: PolicyRecord[];
    total: number;
}

export function fetchPublishPlatform() {
    return axios.get<Array<any[]>>(
        `${Constant.API_PREFIX}/teams/publish-platforms`,
        {
            paramsSerializer: (obj) => {
                return qs.stringify(obj);
            },
        }
    );
}

export function fetchImageCategories() {
    return axios.get<Array<any[]>>(
        `${Constant.API_PREFIX}/article/image-categories`,
        {
            paramsSerializer: (obj) => {
                return qs.stringify(obj);
            },
        }
    );
}

export const intervalRefreshUnFinishedTask = (
    taskCategory: string,
    id: string,
    setLoading: (value: boolean) => void,
    onValue: (value: any) => void
) => {
    const intervalId = setInterval(async () => {
        try {
            setLoading(true);
            const {data: value} = await fetchTaskDetail(taskCategory, id);
            onValue(value);
            if (value && value.state === 'finish') {
                clearInterval(intervalId);
            }
        } finally {
            setLoading(false);
        }
    }, 3_000);
    return intervalId;
};

export function fetchTaskDetail(taskCategory: string, id: string) {
    return axios.get<Array<any[]>>(
        `${Constant.API_PREFIX}/${taskCategory}/task-groups/${id}`,
        {
            paramsSerializer: (obj) => {
                return qs.stringify(obj);
            },
        }
    );
}

export function retryTask(taskCategory: string, id: string) {
    return axios.post<Array<any[]>>(
        `${Constant.API_PREFIX}/${taskCategory}/task-groups/retry`,
        {
            task_group_id: id,
        }
    );
}

export function queryConfigList(route: string, params: GetConfigListQuery) {
    return axios.get<PolicyListRes>(
        `${Constant.API_PREFIX}/config/${route}/configs`,
        {
            params,
            paramsSerializer: (obj) => {
                return qs.stringify(obj);
            },
        }
    );
}

export function queryCurrentTeamSitesList(params?: GetConfigListQuery) {
    return axios.get<PolicyListRes>(
        `${Constant.API_PREFIX}/config/site/platform-sites`,
        {
            params,
            paramsSerializer: (obj) => {
                return qs.stringify(obj);
            },
        }
    );
}
export function parseAccount(accountString: string) {
    if (accountString) {
        return accountString
            .trim()
            .split('\n')
            .map((x) => {
                const split = x.trim().split(',');
                return {
                    username: split[0]?.trim(),
                    password: split[1]?.trim(),
                };
            });
    }
    return [];
}

export function joinAccount(accountString: any[]) {
    if (Array.isArray(accountString)) {
        return accountString.map((x) => `${x.username},${x.password}`).join('\n');
    }
    return '';
}

export const promptVMap: Record<string, any> = {
    qa: {
        placeholder: '按格式输入\n提示词1|提示词2',
        label: 'GPT 提示词',
    },
    article: {
        placeholder: '输入GPT提示词',
        label: 'GPT 内容提示词',
    },
    tag: {
        placeholder: '输入GPT提示词',
        label: 'GPT 提示词',
    }
}

export function computePromptPlaceholder(taskCategory: string) {
    return promptVMap[taskCategory]?.placeholder || "输入GPT提示词";
}

export function computePromptLabel(taskCategory: string) {
    return promptVMap[taskCategory]?.label || "GPT 提示词";
}

export function getConfig(routes: string, params: any) {
    return axios.get<any>(
        `${Constant.API_PREFIX}/config/${routes}/configs`, {
            params: params
      }
    );
}

export function getDetail(routes: string, params: any) {
    return axios.get<any>(
      `${Constant.API_PREFIX}/config/${routes}/detail`, {
          params: params
      }
    );
}
export function updateTaskConfig(request: PartialUpdateAiConfigRequest) {
    return axios.patch<PolicyListRes>(
        `${Constant.API_PREFIX}/config/task/configs/${request.config_id}`,
        request
    );
}

export function updatePromptConfig(request: PartialUpdatePromptConfigRequest) {
    return axios.patch<PolicyListRes>(
        `${Constant.API_PREFIX}/config/prompt/configs/${request.config_id}`,
        request
    );
}

export function createTaskConfig(request: CreateTaskConfigRequest) {
    return axios.post<PolicyListRes>(
        `${Constant.API_PREFIX}/config/task/configs`,
        request
    );
}

export function createPromptConfig(request: CreatePromptConfigRequest) {
    return axios.post<PolicyListRes>(
        `${Constant.API_PREFIX}/config/prompt/configs`,
        request
    );
}

export function createAiConfig(request: CreateAiConfigRequest) {
    return axios.post<PolicyListRes>(
        `${Constant.API_PREFIX}/config/ai/configs`,
        request
    );
}

export function createSiteConfig(request: CreateSiteConfigRequest) {
    return axios.post<PolicyListRes>(
        `${Constant.API_PREFIX}/config/site/configs`,
        request
    );
}

export function updateSiteConfig(request: PartialUpdateSiteConfigRequest) {
    return axios.patch<any>(
        `${Constant.API_PREFIX}/config/site/configs/${request.config_id}`,
        request
    );
}

export function updateAiConfig(request: PartialUpdateAiConfigRequest) {
    return axios.patch<any>(
        `${Constant.API_PREFIX}/config/ai/configs`,
        request
    );
}

export function toggleConfigIsEnable(type: "ai" | "task" | "site", request: ToggleAiConfigIsEnableRequest) {
    return axios.patch<any>(
        `${Constant.API_PREFIX}/config/${type}/configs`,
        request
    );
}

export function deleteConfig(type: "ai" | "task" | "site", id: number) {
    return axios.delete<any>(
        `${Constant.API_PREFIX}/config/${type}/configs`, {
            data: {
                id: id
            }
      }
    );
}

export function toggleAiConfigIsEnable(request: ToggleAiConfigIsEnableRequest) {
    return axios.post<any>(
        `${Constant.API_PREFIX}/config/ai/configs/${request.config_id}/toggle`,
        request
    );
}

export interface ServiceRecord {
    id: number;
    title: string;
    description: string;
    name?: string;
    actionType?: string;
    icon?: string;
    data?: DescData[];
    enable?: boolean;
    expires?: boolean;
}

export function queryInspectionList() {
    return axios.get('/api/list/quality-inspection');
}

export function queryTheServiceList() {
    return axios.get('/api/list/the-service');
}

export function queryRulesPresetList() {
    return axios.get('/api/list/rules-preset');
}
