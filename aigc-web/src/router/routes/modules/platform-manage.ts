import {DEFAULT_LAYOUT} from "@/router/routes/base";
import {AppRouteRecordRaw} from '../types';

const ConfigCenter: AppRouteRecordRaw = {
    component: DEFAULT_LAYOUT,
    path: "/platform-manage",
    name: "platformManage",
    meta: {
        locale: "menu.platform",
        requiresAuth: true,
        icon: "icon-list",
        order: 3
    },
    children: [
        {
            path: "/platforms",
            name: "platforms",
            component: () =>
                import('@/views/platform-manage/platform/index.vue'),
        },
        {
            path: "/platform/create",
            name: "createPlatform",
            component: () =>
                import('@/views/platform-manage/platform/create-platform/index.vue'),
        },
        {
            path: "/platform/edit",
            name: "EditPlatform",
            component: () =>
                import('@/views/platform-manage/platform/edit-task-config/index.vue'),
        },
        {
            path: "/users",
            name: "UserList",
            component: () =>
                import('@/views/platform-manage/user/index.vue'),
        },
        {
            path: "/user/create",
            name: "createUser",
            component: () =>
                import('@/views/platform-manage/user/create/index.vue'),
        },
        {
            path: "/user/edit",
            name: "EditUser",
            component: () =>
                import('@/views/platform-manage/user/edit/index.vue'),
        },
    ]
};

export default ConfigCenter;
