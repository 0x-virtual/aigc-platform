import { DEFAULT_LAYOUT } from '../base';
import { AppRouteRecordRaw } from '../types';

const ConfigCenter: AppRouteRecordRaw = {
  path: '/config-center',
  name: 'configCenter',
  component: DEFAULT_LAYOUT,
  meta: {
    locale: 'menu.config-center',
    requiresAuth: true,
    icon: 'icon-list',
    order: 3,
  },
  children: [
    {
      path: '/site', // The midline path complies with SEO specifications
      name: 'siteConfig',
      component: () => import('@/views/config-center/site/index.vue'),
      meta: {
        locale: 'menu.config-center.site',
        requiresAuth: true,
        roles: ['*'],
      },
    },
    {
      path: '/createSiteConfig', // The midline path complies with SEO specifications
      name: 'createSiteConfig',
      hidden: true,
      component: () =>
        import('@/views/config-center/site/create-task/index.vue'),
      meta: {
        locale: 'menu.config-center.task',
        requiresAuth: true,
        roles: ['*'],
      },
    },
    {
      path: '/editSiteConfig',
      name: 'editSiteConfig',
      hidden: true,
      component: () =>
        import('@/views/config-center/site/edit-task-config/index.vue'),
      meta: {
        locale: 'menu.config-center.task',
        requiresAuth: true,
        roles: ['*'],
      },
    },
    {
      path: '/ai', // The midline path complies with SEO specifications
      name: 'aiConfig',
      component: () => import('@/views/config-center/ai/index.vue'),
      meta: {
        locale: 'menu.config-center.ai',
        requiresAuth: true,
        roles: ['*'],
      },
    },
    {
      path: '/createAiConfig', // The midline path complies with SEO specifications
      name: 'createAiConfig',
      hidden: true,
      component: () => import('@/views/config-center/ai/create-task/index.vue'),
      meta: {
        locale: 'menu.config-center.task',
        requiresAuth: true,
        roles: ['*'],
      },
    },
    {
      path: '/editAiConfig',
      name: 'editAiConfig',
      hidden: true,
      component: () =>
        import('@/views/config-center/ai/edit-task-config/index.vue'),
      meta: {
        locale: 'menu.config-center.task',
        requiresAuth: true,
        roles: ['*'],
      },
    },
    {
      path: '/task', // The midline path complies with SEO specifications
      name: 'taskConfig',
      component: () => import('@/views/config-center/task/index.vue'),
      meta: {
        locale: 'menu.config-center.task',
        requiresAuth: true,
        roles: ['*'],
      },
    },
    {
      path: '/createTaskConfig', // The midline path complies with SEO specifications
      name: 'createTaskConfig',
      hidden: true,
      component: () =>
        import('@/views/config-center/task/create-task/index.vue'),
      meta: {
        locale: 'menu.config-center.task',
        requiresAuth: true,
        roles: ['*'],
      },
    },
    {
      path: '/edit', // The midline path complies with SEO specifications
      name: 'editTaskConfig',
      hidden: true,
      component: () =>
        import('@/views/config-center/task/edit-task-config/index.vue'),
      meta: {
        locale: 'menu.config-center.task',
        requiresAuth: true,
        roles: ['*'],
      },
    },
    {
      path: '/prompt', // The midline path complies with SEO specifications
      name: 'promptConfig',
      component: () => import('@/views/config-center/prompt/index.vue'),
      meta: {
        locale: 'menu.config-center.prompt',
        requiresAuth: true,
        roles: ['*'],
      },
    },
    {
      path: '/createPromptConfig', // The midline path complies with SEO specifications
      name: 'createPromptConfig',
      hidden: true,
      component: () =>
          import('@/views/config-center/prompt/create-task/index.vue'),
      meta: {
        locale: 'menu.config-center.prompt',
        requiresAuth: true,
        roles: ['*'],
      },
    },
    {
      path: '/editPrompt', // The midline path complies with SEO specifications
      name: 'editPromptConfig',
      hidden: true,
      component: () =>
          import('@/views/config-center/prompt/edit-task-config/index.vue'),
      meta: {
        locale: 'menu.config-center.prompt',
        requiresAuth: true,
        roles: ['*'],
      },
    },
  ],
};

export default ConfigCenter;
