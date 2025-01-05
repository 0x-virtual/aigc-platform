<template>
  <div class="container">
    <Breadcrumb :items="['menu.task-center', 'menu.task-center.article']" />
    <a-spin class="task-spin" :loading="loading" tip="">
      <a-card class="general-card" :title="$t('menu.task.article')">
        <div class="custom-form-item">
          <label>状态</label>
          <span class="btn-wrapper">
            <span class="finish-state">{{
                taskDetail.status
            }}</span>
          </span>
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
            </span>
            <div
              >{{ taskDetail.taskSuccessCount }} /
              {{ taskDetail.taskFailureCount }} /
              {{ taskDetail.taskTotal }}
            </div>
          </div>
        </div>
        <div class="custom-form-item">
          <label>发布地址</label>
          {{ taskDetail.siteName }}
        </div>
        <div class="custom-form-item">
          <label>提示词</label>
          {{ taskDetail.prompt }}
        </div>
        <div class="custom-form-item">
          <label>文章标题</label>
          <div class="scrollable-text">
            {{ taskDetail.titles }}
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
    onMounted,
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
    stopTaskGroup,
    submitTask,
  } from '@/api/task';
  import { timestampToYMD } from '@/utils/date';
  import { onBeforeRouteLeave, useRoute, useRouter } from 'vue-router';
  import {convertLanguageDisplay} from "../../../service/task-center/translate";

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
  const router1 = useRouter();
  const { t } = useI18n();
  const taskDetail = ref({
    prompt_remark: '',
    titles: '',
    prompt: '',
    translate_language: 'no_translate',
    publish_platform: '',
    comment_count: 3,
    state: '',
  });

  const size = ref<SizeProps>('medium');
  const router = useRoute();
  const isHovering = ref(false);
  const basePagination: Pagination = {
    current: 1,
    pageSize: 50,
  };
  const pagination = reactive({
    ...basePagination,
  });
  const columns = computed<TableColumnData[]>(() => [
    {
      title: t('searchTable.columns.task_group_identifier'),
      dataIndex: 'task_group_identifier',
      slotName: 'task_group_identifier',
    },
    {
      title: t('searchTable.columns.task_group_id'),
      dataIndex: 'task_group_id',
    },
    {
      title: t('searchTable.columns.state'),
      dataIndex: 'state',
      slotName: 'state',
    },
    {
      title: t('searchTable.columns.progress'),
      dataIndex: 'progress',
      slotName: 'progress',
    },
    {
      title: t('searchTable.columns.publish_site_name'),
      dataIndex: 'publish_site_name',
    },
    {
      title: t('searchTable.columns.published_at'),
      dataIndex: 'published_at',
      slotName: 'published_at',
    },
    // {
    //   title: t('searchTable.columns.operations'),
    //   dataIndex: 'operations',
    //   slotName: 'operations',
    // },
  ]);

  const retry = async () => {
    const { data: value } = await retryTask('article', router.query?.id);
    await refreshTaskDetail(router.query?.id);
  };


  let intervalId: any;
  const refreshTaskDetail = async (id: string) => {
    const { data, reference } = await fetchTaskDetail('article', id);
    taskDetail.value = data.taskGroup;
    taskDetail.value.siteName = data.site?.siteName;
    if (data.state !== 'finish' && data.state !== 'termination') {
      intervalId = intervalRefreshUnFinishedTask(
        'article',
        router.query?.id,
        (value) => {
          setLoading(value);
        },
        (value) => {
          taskDetail.value = value.taskGroup;
          taskDetail.value.siteName = value.site?.siteName;
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

  .btn-wrapper {
    display: flex;
  }

  .stop-btn {
    padding-left: 16px;
    font-size: 0.8rem;
    margin-left: 30px;
    border-radius: 0px;
    color: rgb(var(--danger-6));
    background-color: var(--color-danger-light-1);
    border-color: transparent;
  }
</style>
