import { DEFAULT_LAYOUT } from '../base';
import { AppRouteRecordRaw } from '../types';

const VISUALIZATION: AppRouteRecordRaw = {
  path: '/visualization',
  name: 'visualization',
  component: DEFAULT_LAYOUT,
  meta: {
    locale: 'menu.visualization',
    requiresAuth: true,
    icon: 'icon-apps',
    order: 1,
  },
  children: [],
};

export default VISUALIZATION;
