<template>
  <div class="task-list-container">
    <a-table
      row-key="task_group_id"
      :loading="loading"
      :pagination="pagination"
      :columns="(cloneColumns as TableColumnData[])"
      :data="renderData"
      :bordered="false"
      :size="size"
      @row-click="onRowClick"
      @page-change="onPageChange"
    >
      <template #state="{ record }">
        {{ record.status }}
      </template>
      <template #progress="{ record }">
        {{ record.taskSuccessCount }} / {{ record.taskFailureCount }} /
        {{ record.taskTotal }}
      </template>
      <template #published_at="{ record }">
        {{ dateStrToYMD(record.createdAt) }}
      </template>
    </a-table>
  </div>
</template>

<script lang="ts" setup>
  import { computed, ref, reactive, watch, nextTick } from 'vue';
  import { useI18n } from 'vue-i18n';
  import useLoading from '@/hooks/loading';
  import { PolicyRecord } from '@/api/list';
  import { Pagination } from '@/types/global';
  import type { TableColumnData } from '@arco-design/web-vue/es/table/interface';
  import cloneDeep from 'lodash/cloneDeep';
  import { QueryTaskGroupsParams, queryTaskList } from '@/api/task';
  import { dateStrToYMD, timestampToYMD } from '@/utils/date';
  import { useRouter } from 'vue-router';
  import _ from 'lodash';

  type SizeProps = 'mini' | 'small' | 'medium' | 'large';
  type Column = TableColumnData & { checked?: true };

  const props = defineProps({
    taskCategory: {
      type: String,
    },
    columns: {
      type: Array,
    },
    dataMapping: {
      type: Function,
      default: (data: any) => data, // 默认是一个直接返回数据的函数
    },
  });
  const generateFormModel = () => {
    return {
      task_group_identifier: '',
      number: '',
      name: '',
      contentType: '',
      filterType: '',
      createdTime: [],
      state: '',
      showTaskList: true,
    };
  };
  const { loading, setLoading } = useLoading(true);
  const { t } = useI18n();
  const renderData = ref<PolicyRecord[]>([]);
  const formModel = ref(generateFormModel());
  const cloneColumns = ref<Column[]>([]);
  const showColumns = ref<Column[]>([]);

  const size = ref<SizeProps>('medium');

  const basePagination: Pagination = {
    current: 1,
    pageSize: 50,
    // pageSizeOptions: [20, 50, 100],
  };
  const pagination = reactive({
    ...basePagination,
  });
  const columns = computed<TableColumnData[]>(() => [
    {
      title: 'ID',
      dataIndex: 'id',
      slotName: 'id',
    },
    {
      title: '状态',
      dataIndex: 'status',
      slotName: 'status',
    },
    {
      title: '进度',
      dataIndex: 'progress',
      slotName: 'progress',
    },
    {
      title: '平台',
      dataIndex: 'siteName',
    },
    {
      title: '发布时间',
      dataIndex: 'published_at',
      slotName: 'published_at',
    },
    ...((props.columns || []) as any),
  ]);
  const fetchData = async (
    params: QueryTaskGroupsParams = { pi: 1, ps: 50 }
  ) => {
    setLoading(true);
    try {
      const { data, reference } = await queryTaskList(
        props.taskCategory!,
        params
      );
      const values = data.value.map((x) => props.dataMapping(x));
      console.log(values, reference);
      const siteById = _.keyBy(reference?.sites, 'id');
      // renderData.value = data.value as any;
      console.log(1, "siteById");
      console.log(siteById, "siteById");

      values.forEach((value) => {
        value.siteName = siteById[value.publishSiteId]?.siteName
      });
      console.log(values, "values");

      renderData.value = values;
      pagination.current = params.pi + 1;
      pagination.total = data.total;
    } catch (err) {
      console.error(err,"ee")
      // you can report use errorHandler or other
    } finally {
      setLoading(false);
    }
  };

  const router = useRouter();

  const onPageChange = (current: number) => {
    fetchData({ ps: basePagination.pageSize, pi: current - 1 });
  };
  const toCamelCase = (str: string) => {
    return str
      .toLowerCase()
      .replace(/[-_ ]+([a-z])/g, (match, letter) => letter.toUpperCase());
  };
  const onRowClick = (record: any) => {
    router.push({
      name: `${toCamelCase(props.taskCategory)}TaskDetail`,
      query: { id: record.id },
    });
  };

  fetchData();
  const reset = () => {
    formModel.value = generateFormModel();
  };

  watch(
    () => columns.value,
    (val) => {
      cloneColumns.value = cloneDeep(val);
      cloneColumns.value.forEach((item, index) => {
        item.checked = true;
      });
      showColumns.value = cloneDeep(cloneColumns.value);
    },
    { deep: true, immediate: true }
  );
  defineExpose({
    fetchData,
  });
</script>

<script lang="ts">
  export default {
    name: 'SearchTable',
  };
</script>

<style scoped lang="less">
  .task-list-container {
    padding-left: 10px;
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
    display: flex;
    flex-direction: column;
  }

  .custom-form-item label {
    margin-bottom: 8px; /* 标签与表单项之间的间距 */
  }

  .fixed-header {
    position: fixed;
    top: 0;
  }

  .fixed-footer {
    position: fixed;
    bottom: 0;
  }
</style>
