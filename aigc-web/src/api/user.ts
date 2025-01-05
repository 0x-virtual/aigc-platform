import axios from 'axios';
import type { RouteRecordNormalized } from 'vue-router';
import { UserState } from '@/store/modules/user/types';
import { Constant } from '@/api/constant';

export interface LoginData {
  username: string;
  password: string;
}

export interface LoginRes {
  token: string;
}

export function login(data: LoginData) {
  return axios.post<LoginRes>(`${Constant.API_PREFIX}/users/login`, data);
}

export function logout() {
  return axios.post<LoginRes>(`${Constant.API_PREFIX}/users/logout`);
}

export function getUserInfo() {
  return axios.get<{ user: UserState }>(`${Constant.API_PREFIX}/users/me`);
}

export function getMenuList() {
  return axios.post<RouteRecordNormalized[]>(
    `${Constant.API_PREFIX}/user/menu`
  );
}
