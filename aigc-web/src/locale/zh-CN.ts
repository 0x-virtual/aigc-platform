import localeMessageBox from '@/components/message-box/locale/zh-CN';
import localeLogin from '@/views/login/locale/zh-CN';


import taskArticle from '@/views/list/article-task/locale/zh-CN';

import localeStepForm from '@/views/form/step/locale/zh-CN';
import localeGroupForm from '@/views/form/group/locale/zh-CN';

import localeBasicProfile from '@/views/profile/basic/locale/zh-CN';

import localeDataAnalysis from '@/views/visualization/data-analysis/locale/zh-CN';
import localeMultiDAnalysis from '@/views/visualization/multi-dimension-data-analysis/locale/zh-CN';

import localeSuccess from '@/views/result/success/locale/zh-CN';
import localeError from '@/views/result/error/locale/zh-CN';

import locale403 from '@/views/exception/403/locale/zh-CN';
import locale404 from '@/views/exception/404/locale/zh-CN';
import locale500 from '@/views/exception/500/locale/zh-CN';

import localeUserInfo from '@/views/user/info/locale/zh-CN';
import localeUserSetting from '@/views/user/setting/locale/zh-CN';
/** simple end */
import localeSettings from './zh-CN/settings';

export default {
    'menu.dashboard': '仪表盘',
    'menu.server.dashboard': '仪表盘',
    'menu.server.workplace': '工作台',
    'menu.server.monitor': '实时监控-服务端',
    'menu.task-center': '任务中心',
    'menu.task-center.tag': '标签任务',
    'menu.task-center.tag.create': '创建标签',
    'menu.config-center': '配置中心',
    'menu.config-center.site': '站点配置',
    'menu.config-center.ai': 'GPT配置',
    'menu.config-center.task': '任务配置',
    'menu.config-center.prompt': '提示词配置',
    'menu.data-analysis': '数据分析',
    'menu.data-analysis.task': '任务执行',
    'menu.platform.platforms': '平台管理',
    'menu.platform.users': '用户管理',
    'menu.platform': '系统管理',
    'menu.platform.list': '平台管理',
    'menu.system.user.edit': '用户配置',
    'menu.result': '结果页',
    'menu.exception': '异常页',
    'menu.form': '表单页',
    'menu.profile': '详情页',
    'menu.visualization': '任务中心',
    'menu.user': '个人中心',
    'menu.arcoWebsite': 'Arco Design',
    'menu.faq': '常见问题',
    'navbar.docs': '文档中心',
    'navbar.action.locale': '切换为中文',
    ...localeSettings,
    ...localeMessageBox,
    ...localeLogin,
    ...taskArticle,
    /** simple */
    ...localeStepForm,
    ...localeGroupForm,
    ...localeBasicProfile,
    ...localeDataAnalysis,
    ...localeMultiDAnalysis,
    ...localeSuccess,
    ...localeError,
    ...locale403,
    ...locale404,
    ...locale500,
    ...localeUserInfo,
    ...localeUserSetting,
    /** simple end */
};
