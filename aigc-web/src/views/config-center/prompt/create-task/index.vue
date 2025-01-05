<template>
  <div class="container">
    <Breadcrumb :items="['menu.config-center', 'menu.config-center.prompt']"/>

    <a-card class="general-card" :title="$t('menu.config-center.prompt')">
      <a-form ref="formRef" :model="form">
        <div class="custom-form-item">
          <label>备注</label>
          <a-input
              v-model="form.remark"
              class="custom-textarea"
              placeholder="请输入备注"
          />
        </div>
        <Prompt
            v-model="form.prompt"
            :placeholder="'GPT 提示词'"
            :prompt-label="'GPT 提示词'"
        ></Prompt>
      </a-form>
      <div class="submit-button-space"></div>
      <a-button type="primary" @click="onSubmitTask">提交</a-button>
    </a-card>
  </div>
</template>

<script lang="ts" setup>
import {computed, ref, reactive, watch, nextTick} from 'vue';
import {useI18n} from 'vue-i18n';
import useLoading from '@/hooks/loading';
import {PolicyRecord} from '@/api/list';
import type {TableColumnData} from '@arco-design/web-vue/es/table/interface';
import {fetchTeams, submitTask} from '@/api/task';
import {timestampToYMD} from '@/utils/date';
import {useRouter} from 'vue-router';
import {
  computePromptLabel,
  computePromptPlaceholder, createPromptConfig,
  createTaskConfig,
  getConfig,
  parseAccount, promptVMap,
  queryConfigList,
} from '@/api/config';
import {DefineTaskCategories} from "@/service/task-center/task";

type SizeProps = 'mini' | 'small' | 'medium' | 'large';
type Column = TableColumnData & { checked?: true };

const generateFormModel = () => {
  return {
    titles: '',
    prompt: '',
    publish_platform: '',
    task_category: '',
    comment_count: 3,
  };
};
const {loading, setLoading} = useLoading(true);
const {t} = useI18n();
const renderData = ref<PolicyRecord[]>([]);
const formModel = ref(generateFormModel());
const cloneColumns = ref<Column[]>([]);
const showColumns = ref<Column[]>([]);
const publishPlatforms = ref<[]>([]);
const sites = ref<[]>([]);
const taskCategories = ref<[]>([]);
const onSubmitTask = async () => {
  setLoading(true);
  try {
    const {data} = await createPromptConfig({
      prompt: form.prompt,
      remark: form.remark,
    });
    await router.push({
      name: 'promptConfig',
      query: {id: data.task_group_id},
    });
  } catch (err) {
    console.log(err);
    // you can report use errorHandler or other
  } finally {
    setLoading(false);
  }
};


const router = useRouter();

const form = reactive({
  task_category: '',
  remark: '',
  prompt: '',
});

const reset = () => {
  formModel.value = generateFormModel();
};
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
  background-color: white;
  display: flex;
  flex-direction: column;
  margin-bottom: 1rem;
}

.custom-form-item input {
  border: 1px solid #eee;
}

.custom-textarea {
  //resize: vertical; /* 允许垂直调整大小 */
  //line-height: 1.5;
  //max-height: 4.5em;
  border-radius: 0.25rem;
  border: 1px solid #eee;
}

.custom-form-item label {
  display: inline-block;
  margin-bottom: 0.5rem;
}

.submit-button-space {
  margin-bottom: 20px;
}
</style>
