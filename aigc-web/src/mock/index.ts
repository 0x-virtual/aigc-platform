import Mock from 'mockjs';

import './user';
import './message-box';

/** simple */
import '@/views/visualization/multi-dimension-data-analysis/mock';

import '@/views/user/setting/mock';
/** simple end */

Mock.setup({
  timeout: '600-1000',
});
