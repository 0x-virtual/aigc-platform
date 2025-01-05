<template>
  <div class="container">
    <Breadcrumb :items="['menu.list', 'menu.list.searchTable']" />
    <a-spin class="task-spin" :loading="loading" tip="">
      <a-card class="general-card" :title="$t('menu.list.searchTable')">
        <div class="custom-form-item">
          <label>状态</label>
          <span class="finish-state">{{
            $t(`task.state.${taskDetail.state}`)
          }}</span>
        </div>
        <div class="custom-form-item">
          <div>
            <span class="task-progress-detail">
              <label>进度</label>
            </span>
            <span
              v-if="
                (taskDetail.state === 'finish' &&
                  taskDetail.task_failure_count > 0) ||
                taskDetail.state === 'termination'
              "
              class="task-progress-detail task-progress-retry-icon product"
              @mouseover="isHovering = true"
              @mouseleave="isHovering = false"
              @click="retry"
            >
              <a-tooltip content="重试任务">
                <icon-refresh size="20" tip="重试任务"></icon-refresh>
              </a-tooltip>
            </span>
            <div
              >{{ taskDetail.task_success_count }} /
              {{ taskDetail.task_failure_count }} /
              {{ taskDetail.task_total }}</div
            >
          </div>
        </div>

        <div class="custom-form-item">
          <label>发布地址</label>
          {{ taskDetail.publish_platform }}
        </div>
        <div class="custom-form-item">
          <label>问答标题</label>
          <div class="scrollable-text">
            {{ taskDetail.titles }}
          </div>
        </div>
        <div class="custom-form-item">
          <label>回复数量</label>
          {{ taskDetail.task_comment_count }}
        </div>
        <div class="custom-form-item">
          <label>GPT提示词</label>
          <div class="scrollable-text">
            {{ taskDetail.task_prompt }}
          </div>
        </div>
      </a-card>
    </a-spin>
  </div>
</template>

<script lang="ts" setup>
  import {
    computed,
    ref,
    reactive,
    watch,
    nextTick,
    onBeforeUnmount,
  } from 'vue';
  import { useI18n } from 'vue-i18n';
  import useLoading from '@/hooks/loading';
  import { queryPolicyList, PolicyRecord, PolicyParams } from '@/api/list';
  import { Pagination, PaginationQuery } from '@/types/global';
  import type { SelectOptionData } from '@arco-design/web-vue/es/select/interface';
  import type { TableColumnData } from '@arco-design/web-vue/es/table/interface';
  import cloneDeep from 'lodash/cloneDeep';
  import Sortable from 'sortablejs';
  import {
    fetchTaskDetail,
    intervalRefreshUnFinishedTask,
    QueryTaskGroupsParams,
    queryTaskList,
    retryTask,
    submitTask,
  } from '@/api/task';
  import { timestampToYMD } from '@/utils/date';
  import { onBeforeRouteLeave, useRoute, useRouter } from 'vue-router';

  type SizeProps = 'mini' | 'small' | 'medium' | 'large';
  type Column = TableColumnData & { checked?: true };

  const generateFormModel = () => {
    return {
      titles: '',
      prompt: '',
      publish_platform: '',
      comment_count: 3,
    };
  };
  const { loading, setLoading } = useLoading(false);
  const { t } = useI18n();
  const taskDetail = ref({
    titles: '',
    prompt: '',
    publish_platform: '',
    comment_count: 3,
  });

  const size = ref<SizeProps>('medium');
  const router = useRoute();

  const retry = async () => {
    const { data: value } = await retryTask('qa', router.query?.id);
    await refreshTaskDetail(router.query?.id);
  };
  let intervalId: any;
  const refreshTaskDetail = async (id: string) => {
    const { data: value } = await fetchTaskDetail('qa', id);
    taskDetail.value = value;
    if (value.state !== 'finish' && value.state !== 'termination') {
      intervalId = intervalRefreshUnFinishedTask(
        'qa',
        router.query?.id,
        (value) => {
          setLoading(value);
        },
        (value) => {
          taskDetail.value = value;
        }
      );
    }
  };
  refreshTaskDetail(router.query?.id);

  onBeforeUnmount(() => {
    clearInterval(intervalId);
  });

  onBeforeRouteLeave((to, from, next) => {
    clearInterval(intervalId);
    next();
  });
</script>

<script lang="ts">
  export default {
    name: 'SearchTable',
  };
</script>

<style scoped lang="less">
  .container {
    padding: 0 20px 20px 20px;
  }
  :deep(.arco-table-th) {
    &:last-child {
      .arco-table-th-item-title {
        margin-left: 16px;
      }
    }
  }
  .action-icon {
    margin-left: 12px;
    cursor: pointer;
  }
  .active {
    color: #0960bd;
    background-color: #e3f4fc;
  }
  .setting {
    display: flex;
    align-items: center;
    width: 200px;
    .title {
      margin-left: 12px;
      cursor: pointer;
    }
  }
  .custom-form-item {
    white-space: pre-line;
    display: flex;
    flex-direction: column;
    margin-bottom: 2rem !important;
  }
  .custom-form-item label {
    margin-bottom: 1rem !important;
  }
  .finish-state {
    background-color: rgb(115, 216, 151);
    border-radius: 18px;
    height: 24px;
    line-height: 24px;
    padding-left: 12px;
    //padding-right: 12px;
    font-size: 0.75rem;
    display: inline;
    width: 62px;
  }
  .custom-form-item label {
    margin-bottom: 8px; /* 标签与表单项之间的间距 */
  }
  .submit-button-space {
    margin-bottom: 20px;
  }
  /* 样式可以根据实际需求自行调整 */
  .custom-form-item {
    margin-bottom: 16px;
  }

  .custom-form-item label {
    display: inline-block;
    //width: 100px;
  }

  .scrollable-text {
    //max-width: 200px; /* 文本框最大宽度 */
    max-height: 200px; /* 文本框最大高度 */
    overflow-x: auto; /* 水平方向自动滚动 */
    overflow-y: auto; /* 垂直方向自动滚动 */
    //border: 1px solid #ccc;
    padding: 8px;
    //font-family: -apple-system, BlinkMacSystemFont, Segoe UI, Roboto, PingFang SC, Helvetica Neue, Noto Sans, Noto Sans CJK SC, Microsoft Yahei, Arial, Hiragino Sans GB, sans-serif;
    font-size: 1rem;
    font-weight: 400;
    line-height: 1.5715;
  }

  .task-spin {
    display: block;
  }
  .task-progress-detail {
    padding-right: 20px;
    display: inline;
    width: 200px;

    height: 100px;
    font-size: 15px;

    //border: 1px solid red;

    white-space: nowrap;
  }
  .task-progress-retry-icon {
    padding-top: 10px;
  }
  .retry-btn {
    background: white;
  }
  .product {
    //border: 1px solid #ccc;
    //padding: 10px;
    cursor: pointer;
  }

  .product:hover {
    background-color: white;
  }

  .task-spin {
    display: block;
  }
</style>
