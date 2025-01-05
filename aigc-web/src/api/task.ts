import axios from 'axios';
import qs from 'query-string';
import type { DescData } from '@arco-design/web-vue/es/descriptions/interface';
import { Constant } from '@/api/constant';

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

export interface QueryTaskGroupsParams extends Partial<PolicyRecord> {
  pi: number;
  ps: number;
}

export interface SubmitTaskRequest {
  titles: string;
  prompt_id: string,
  content_prompt: string;
  translate_language?: string;
  faq_prompt: string;
  image_source: string;
  publish_site_id: string;
  image_category_id: string;
}

export interface PolicyListRes {
  value: PolicyRecord[];
  total: number;
}

export function fetchTeams() {
  return axios.get<Array<any[]>>(`${Constant.API_PREFIX}/teams/all-platforms`, {
    paramsSerializer: (obj) => {
      return qs.stringify(obj);
    },
  });
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
      const { data: value } = await fetchTaskDetail(taskCategory, id);
      onValue(value);
      if (
        (value && value.state === 'finish') ||
        (value && value.state === 'termination')
      ) {
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
    `${Constant.API_PREFIX}/task-groups/detail`,
    {
      params: {
        id
      }
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

export function stopTaskGroup(taskCategory: string, id: string) {
  return axios.post<Array<any[]>>(
    `${Constant.API_PREFIX}/${taskCategory}/task-groups/stop`,
    {
      task_group_id: id,
    }
  );
}

export function queryTaskList(
  taskCategory: string,
  params: QueryTaskGroupsParams
) {
  return axios.get<PolicyListRes>(
    `${Constant.API_PREFIX}/task-groups/list`,
    {
      params,
      paramsSerializer: (obj) => {
        return qs.stringify(obj);
      },
    }
  );
}

export function submitTask(request: SubmitTaskRequest) {
  return axios.post<PolicyListRes>(
    `${Constant.API_PREFIX}/qa/task-groups`,
    request
  );
}


export function submitTaskCommon(taskCategory: string, request: any) {
  return axios.post<PolicyListRes>(
      `${Constant.API_PREFIX}/${taskCategory}/task-groups`,
      request
  );
}

export function submitArticleTask(request: SubmitTaskRequest) {
  return axios.post<PolicyListRes>(
    `${Constant.API_PREFIX}/task-groups/create`,
    request
  );
}

export function submitTagTask(taskCategory: string, body: any) {
  return axios.post<PolicyListRes>(
    `${Constant.API_PREFIX}/${taskCategory}/task-groups`,
    body
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
