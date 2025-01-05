import { DEFAULT_LAYOUT } from '../base';
import { AppRouteRecordRaw } from '../types';

const LIST: AppRouteRecordRaw = {
  path: '/task',
  name: 'list',
  component: DEFAULT_LAYOUT,
  meta: {
    locale: 'menu.task-center',
    requiresAuth: true,
    icon: 'icon-list',
    order: 2,
  },
  children: [
    {
      path: '/qa', // The midline path complies with SEO specifications
      name: 'qaTask',
      component: () => import('@/views/list/search-table/index.vue'),
      meta: {
        locale: 'menu.task-center.qa',
        requiresAuth: true,
        roles: ['*'],
      },
    },
    {
      path: '/qa/create', // The midline path complies with SEO specifications
      name: 'qaCreateTask',
      hidden: true,
      component: () => import('@/views/list/create-task/index.vue'),
      meta: {
        locale: 'menu.list.searchTable',
        requiresAuth: true,
        roles: ['*'],
        hidden: true, // 添加 hidden 属性，标记该路由在侧边栏中隐藏
      },
    },
    {
      path: '/qa/detail', // The midline path complies with SEO specifications
      name: 'qaTaskDetail',
      hidden: true,
      component: () => import('@/views/list/task-detail/index.vue'),
      meta: {
        locale: 'menu.list.searchTable',
        requiresAuth: true,
        roles: ['*'],
        hidden: true, // 添加 hidden 属性，标记该路由在侧边栏中隐藏
      },
    },
    {
      path: '/qaComment', // The midline path complies with SEO specifications
      name: 'qaComment',
      component: () => import('@/views/task-center/qa-comment/list/index.vue'),
      meta: {
        locale: 'menu.task-center.qa-comment',
        requiresAuth: true,
        roles: ['*'],
      },
    },
    {
      path: '/qaComment/create', // The midline path complies with SEO specifications
      name: 'qaCommentCreateTask',
      hidden: true,
      component: () => import('@/views/task-center/qa-comment/create/index.vue'),
      meta: {
        requiresAuth: true,
        roles: ['*'],
        hidden: true, // 添加 hidden 属性，标记该路由在侧边栏中隐藏
      },
    },
    {
      path: '/qaComment/detail', // The midline path complies with SEO specifications
      name: 'qaCommentTaskDetail',
      hidden: true,
      component: () => import('@/views/task-center/qa-comment/detail/index.vue'),
      meta: {
        requiresAuth: true,
        roles: ['*'],
        hidden: true, // 添加 hidden 属性，标记该路由在侧边栏中隐藏
      },
    },
    {
      path: '/article',
      name: 'article',
      component: () => import('@/views/list/article-task/index.vue'),
      meta: {
        locale: 'menu.task.article',
        requiresAuth: true,
        roles: ['*'],
      },
    },
    {
      path: '/task/article/create', // The midline path complies with SEO specifications
      name: 'articleCreateTask',
      hidden: true,
      component: () => import('@/views/list/article-create-task/index.vue'),
      meta: {
        locale: 'menu.list.searchTable',
        requiresAuth: true,
        roles: ['*'],
        hidden: true, // 添加 hidden 属性，标记该路由在侧边栏中隐藏
      },
    },
    {
      path: '/task/article/detail', // The midline path complies with SEO specifications
      name: 'articleTaskDetail',
      hidden: true,
      component: () => import('@/views/list/article-task-detail/index.vue'),
      meta: {
        locale: 'menu.list.searchTable',
        requiresAuth: true,
        roles: ['*'],
        hidden: true, // 添加 hidden 属性，标记该路由在侧边栏中隐藏
      },
    },
    {
      path: '/tag',
      name: 'tag',
      component: () => import('@/views/task-center/tag/home/index.vue'),
      meta: {
        locale: 'menu.task.tag',
        requiresAuth: true,
        roles: ['*'],
      },
    },
    {
      path: '/task/tag/create', // The midline path complies with SEO specifications
      name: 'tagCreateTask',
      hidden: true,
      component: () => import('@/views/task-center/tag/create-task/index.vue'),
      meta: {
        locale: 'menu.list.searchTable',
        requiresAuth: true,
        roles: ['*'],
        hidden: true, // 添加 hidden 属性，标记该路由在侧边栏中隐藏
      },
    },
    {
      path: '/task/tag/detail', // The midline path complies with SEO specifications
      name: 'tagTaskDetail',
      hidden: true,
      component: () => import('@/views/task-center/tag/task-detail/index.vue'),
      meta: {
        locale: 'menu.list.searchTable',
        requiresAuth: true,
        roles: ['*'],
        hidden: true, // 添加 hidden 属性，标记该路由在侧边栏中隐藏
      },
    },
  ],
};

export default LIST;
