import { DEFAULT_LAYOUT } from '../base';
import { AppRouteRecordRaw } from '../types';

const DataAnalysis: AppRouteRecordRaw = {
    path: '/data-analysis',
    name: 'dataAnalysis',
    component: DEFAULT_LAYOUT,
    meta: {
        locale: 'menu.data-analysis',
        requiresAuth: true,
        icon: 'icon-list',
        order: 3,
    },
    children: [
        {
            path: '/taskAnalysis', // The midline path complies with SEO specifications
            name: 'taskAnalysis',
            component: () => import('@/views/data-analysis/task/home/index.vue'),
            meta: {
                locale: 'menu.data-analysis.task',
                requiresAuth: true,
                roles: ['*'],
            },
        },
    ],
};

export default DataAnalysis;
