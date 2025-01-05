<template>
  <div class="container">
    <Breadcrumb :items="['menu.config-center', 'menu.config-center.task']"/>
    <a-card class="general-card">
      <a-divider style="margin-top: 0"/>
      <a-row style="margin-bottom: 16px; margin-left: 10px">
        <a-col :span="12">
          <a-space>
            <a-button type="primary" @click="redirectToOtherPage">
              <template #icon>
                <icon-plus/>
              </template>
              {{ $t('config-center.task.create') }}
            </a-button>
          </a-space>
        </a-col>
        <a-col
            :span="12"
            style="display: flex; align-items: center; justify-content: end"
        >
          <a-tooltip :content="$t('task.actions.refresh')">
            <div class="action-icon" @click="search">
              <icon-refresh size="18"/>
            </div>
          </a-tooltip>
        </a-col>

        <a-col
            :span="12"
            style="display: flex; align-items: center; justify-content: end"
        >
        </a-col>
      </a-row>
      <div class="task-list-container">
        <a-table
            row-key="config_id"
            :loading="loading"
            :pagination="pagination"
            :columns="(cloneColumns as TableColumnData[])"
            :data="renderData"
            :bordered="false"
            :size="size"
            @row-click="onRowClick"
            @page-change="onPageChange"
        >
          <template #task_category="{ record }">
            {{ $t(`config-center.task.tackCategory.${record.task_category}`) }}
          </template>
          <template #post_creator_accounts="{ record }">
            {{ record.post_creator_accounts ? '***' : '' }}
          </template
          >
          <template #comment_accounts="{ record }">
            {{ record.comment_accounts ? '***' : '' }}
          </template
          >
          <template #updated_at="{ record }">
            {{ timestampToYMD(record.updated_at) }}
          </template>
          <template #created_at="{ record }">
            {{ timestampToYMD(record.created_at) }}
          </template>
          <template #is_enabled="{ record }">
            <a-switch
                v-model="record.is_enabled"
                :checked-value="1"
                :unchecked-value="0"
                @change="(checked) => handlerIsEnableChange(checked, record)"
            />
          </template>
          <template #operations="{ record }">
            <ConfigCenterOperation
                @onDeleted="fetchData"
                config="task" :config-id="record.config_id" edit-route="editTaskConfig"></ConfigCenterOperation>
          </template>
        </a-table>
      </div>
    </a-card>
  </div>
</template>

<script lang="ts" setup>
import {useRouter} from 'vue-router';
import {TableColumnData} from '@arco-design/web-vue/es/table/interface';
import useLoading from '@/hooks/loading';
import {useI18n} from 'vue-i18n';
import {computed, reactive, ref, watch} from 'vue';
import {PolicyRecord} from '@/api/list';
import {Pagination} from '@/types/global';
import {QueryTaskGroupsParams, queryTaskList} from '@/api/task';
import cloneDeep from 'lodash/cloneDeep';
import {queryConfigList, toggleConfigIsEnable} from '@/api/config';
import {timestampToYMD} from "@/utils/date";

type SizeProps = 'mini' | 'small' | 'medium' | 'large';
type Column = TableColumnData & { checked?: true };

const props = defineProps({
  taskCategory: {
    type: String,
  },
});
const generateFormModel = () => {
  return {
    task_group_identifier: '',
    number: '',
    name: '',
    task_category: '',
    filterType: '',
    createdTime: [],
    state: '',
    showTaskList: true,
  };
};
const {loading, setLoading} = useLoading(true);
const {t} = useI18n();
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
  // {
  //   title: t('config-center.task.columns.config_id'),
  //   dataIndex: 'config_id',
  //   slotName: 'config_id',
  // },
  {
    title: t('config-center.task.platform_display_name'),
    dataIndex: 'platform_display_name',
    slotName: 'platform_display_name',
  },
  {
    title: t('config-center.task.site_name'),
    dataIndex: 'site_name',
    slotName: 'site_name',
  },
  {
    title: t('config-center.task.tackCategory'),
    dataIndex: 'task_category',
    slotName: 'task_category',
  },
  // {
  //   title: t('config-center.task.postCreatorAccount'),
  //   dataIndex: 'post_creator_accounts',
  //   slotName: 'post_creator_accounts',
  // },
  // {
  //   title: t('config-center.task.commentAccount'),
  //   dataIndex: 'comment_accounts',
  //   slotName: 'comment_accounts',
  // },
  {
    title: t('config-center.ai.is_enabled'),
    dataIndex: 'is_enabled',
    slotName: 'is_enabled',
  },
  // {
  //   title: t('config-center.created_at'),
  //   dataIndex: 'created_at',
  //   slotName: 'created_at',
  // },
  {
    title: t('config-center.updated_at'),
    dataIndex: 'updated_at',
    slotName: 'updated_at',
  },
  {
    title: t('config-center.task.operations'),
    // dataIndex: 'operations',
    slotName: 'operations',
  },
]);
const fetchData = async (
    params: QueryTaskGroupsParams = {pi: 1, ps: 50}
) => {
  setLoading(true);
  try {
    const {data} = await queryConfigList('task', params);
    renderData.value = data.items as any;
    pagination.current = params.pi + 1;
    pagination.total = data.total;
    console.log(renderData.value);
  } catch (err) {
    // you can report use errorHandler or other
  } finally {
    setLoading(false);
  }
};

const router = useRouter();


const handlerIsEnableChange = async (checked: number, record: any) => {
  console.log(record);
  await toggleConfigIsEnable("task", {
    id: record.config_id,
    enabled: checked,
  });
};

const redirectToOtherPage = () => {
  router.push({name: 'createTaskConfig'});
};

const search = () => {
  fetchData({
    ps: basePagination.pageSize,
    pi: basePagination.current - 1,
    // ...formModel.value,
  } as unknown as QueryTaskGroupsParams);
};
const onPageChange = (current: number) => {
  fetchData({ps: basePagination.pageSize, pi: current - 1});
};

const onRowClick = (record: any) => {
  router.push({
    name: `${props.taskCategory}TaskDetail`,
    query: {id: record.task_group_id},
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
    {deep: true, immediate: true}
);
</script>

<script lang="ts">
export default {
  name: 'QaTask',
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

.operation-btn {
  padding-left: 0px;
}
</style>
