<template>
  <div class="container">
    <Breadcrumb :items="['menu.task-center', '投放任务']" />
    <a-card class="general-card">
      <a-divider style="margin-top: 0" />
      <a-row style="margin-bottom: 16px; margin-left: 10px">
        <a-col :span="12">
          <a-space>
            <a-button type="primary" @click="redirectToOtherPage">
              <template #icon>
                <icon-plus />
              </template>
              {{ '新建投放任务' }}
            </a-button>
          </a-space>
        </a-col>
        <a-col
          :span="12"
          style="display: flex; align-items: center; justify-content: end"
        >
          <a-tooltip :content="$t('task.actions.refresh')">
            <div class="action-icon" @click="search"
              ><icon-refresh size="18"
            /></div>
          </a-tooltip>
        </a-col>

        <a-col
          :span="12"
          style="display: flex; align-items: center; justify-content: end"
        >
        </a-col>
      </a-row>
      <TaskList ref="taskListRef" task-category="article"></TaskList>
    </a-card>
  </div>
</template>

<script lang="ts" setup>
  import { useRouter } from 'vue-router';

  const router = useRouter();
  const redirectToOtherPage = () => {
    router.push({ name: 'articleCreateTask' });
  };
</script>

<script lang="ts">
  import { QueryTaskGroupsParams } from '@/api/task';
  import TaskList from '@/views/list/components/task-list/index.vue';
  import { ref } from 'vue';
  const taskListRef = ref<InstanceType<typeof TaskList> | null>(null);

  export default {
    name: 'QaTask',
  };
  const search = () => {
    taskListRef.value.fetchData({
      ps: 50,
      pi: 1,
      // ...formModel.value,
    } as unknown as QueryTaskGroupsParams);
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
</style>
