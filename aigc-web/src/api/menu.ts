import axios from 'axios';
import qs from 'query-string';
import { Constant } from '@/api/constant';


export function fetchMenus() {
  return [
    {
      path: "/task",
      name: "list",
      meta: {
        locale: "投放任务",
        icon: "icon-list",
        order: 1
      },
      children: [
        {
          path: "/article",
          name: "article",
          meta: {
            locale: "halo站点",
          }
        },
        {
          path: "/task/article/create", // The midline path complies with SEO specifications
          name: "articleCreateTask",
          hidden: true,
          meta: {
            locale: "menu.list.searchTable",
            requiresAuth: true,
            roles: ["*"],
            hidden: true // 添加 hidden 属性，标记该路由在侧边栏中隐藏
          }
        },
        {
          path: "/task/article/detail", // The midline path complies with SEO specifications
          name: "articleTaskDetail",
          hidden: true,
          meta: {
            locale: "menu.list.searchTable",
            requiresAuth: true,
            roles: ["*"],
            hidden: true // 添加 hidden 属性，标记该路由在侧边栏中隐藏
          }
        },

      ]
    },
    {
      server_custom_roles: ["wmvm", "admin"],
      path: "/config-center",
      name: "configCenter",
      meta: {
        locale: "menu.config-center",
        requiresAuth: true,
        icon: "icon-list",
        order: 2
      },
      children: [
        {
          server_custom_roles: ["wmvm", "admin"],
          path: "/site", // The midline path complies with SEO specifications
          name: "siteConfig",
          meta: {
            locale: "menu.config-center.site",
            requiresAuth: true,
            roles: ["*"]
          }
        },
        {
          server_custom_roles: ["wmvm", "admin"],
          path: "/createSiteConfig", // The midline path complies with SEO specifications
          name: "createSiteConfig",
          hidden: true,
          meta: {
            locale: "menu.config-center.task",
            requiresAuth: true,
            roles: ["*"]
          }
        },
        {
          server_custom_roles: ["wmvm", "admin"],
          path: "/editSiteConfig",
          name: "editSiteConfig",
          hidden: true,
          meta: {
            locale: "menu.config-center.task",
            requiresAuth: true,
            roles: ["*"]
          }
        },
        {
          server_custom_roles: ["wmvm", "admin"],
          path: "/ai", // The midline path complies with SEO specifications
          name: "aiConfig",
          meta: {
            locale: "menu.config-center.ai",
            requiresAuth: true,
            roles: ["*"]
          }
        },
        {
          server_custom_roles: ["wmvm", "admin"],
          path: "/createAiConfig", // The midline path complies with SEO specifications
          name: "createAiConfig",
          hidden: true,
          meta: {
            locale: "menu.config-center.task",
            requiresAuth: true,
            roles: ["*"]
          }
        },
        {
          server_custom_roles: ["wmvm", "admin"],
          path: "/editAiConfig",
          name: "editAiConfig",
          hidden: true,
          meta: {
            locale: "menu.config-center.task",
            requiresAuth: true,
            roles: ["*"]
          }
        },
        {
          server_custom_roles: ["wmvm", "admin"],
          path: "/prompt", // The midline path complies with SEO specifications
          name: "promptConfig",
          meta: {
            locale: "menu.config-center.prompt",
            requiresAuth: true,
            roles: ["*"]
          }
        },
        {
          server_custom_roles: ["wmvm", "admin"],
          path: "/createPromptConfig", // The midline path complies with SEO specifications
          name: "createPromptConfig",
          hidden: true,
          meta: {
            locale: "menu.config-center.prompt",
            requiresAuth: true,
            roles: ["*"]
          }
        },
        {
          server_custom_roles: ["wmvm", "admin"],
          path: "/edit", // The midline path complies with SEO specifications
          name: "editPromptConfig",
          hidden: true,
          meta: {
            locale: "menu.config-center.prompt",
            requiresAuth: true,
            roles: ["*"]
          }
        }
      ]
    },
    // {
    //   server_custom_roles: ["wmvm"],
    //   path: "/data-analysis",
    //   name: "dataAnalysis",
    //   meta: {
    //     locale: "menu.data-analysis",
    //     requiresAuth: true,
    //     icon: "icon-list",
    //     order: 2
    //   },
    //   children: [
    //     {
    //       server_custom_roles: ["wmvm"],
    //       path: "/taskAnalysis",
    //       name: "taskAnalysis",
    //       meta: {
    //         locale: "menu.data-analysis.task",
    //         requiresAuth: true,
    //         roles: ["*"]
    //       }
    //     }
    //   ]
    // }
  ];
}
