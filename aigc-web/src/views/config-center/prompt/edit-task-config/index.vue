<template>
  <div class="container">
    <Breadcrumb :items="['menu.config-center', 'menu.config-center.prompt']"/>

    <a-card class="general-card" :title="$t('menu.config-center.prompt')">
      <a-form ref="formRef" :model="form">
        <div class="custom-form-item">
          <label>平台</label>
          <a-select v-model="form.platform_id" placeholder="请选择" disabled>
            <a-option
                v-for="option in publishPlatforms"
                :key="option.team_id"
                :value="option.team_id"
            >{{ option.display_name }}
            </a-option>
          </a-select>
        </div>
        <div class="custom-form-item">
          <label>任务类别</label>
          <a-select v-model="form.task_category" placeholder="请选择" disabled>
            <a-option
                v-for="option in taskCategories"
                :key="option.value"
                :value="option.value"
            >{{ option.name }}
            </a-option>
          </a-select>
        </div>
        <div class="custom-form-item">
          <label>提示词名称</label>
          <a-input v-model="form.remark" >
          </a-input>
        </div>
        <Prompt
            v-model="form.configuration.prompt"
            :placeholder="promptVMap[form.task_category]?.placeholder"
            :prompt-label="'GPT 提示词'"
        ></Prompt>
        <Prompt
            v-if="form.task_category === 'article'"
            v-model="form.configuration.faq_prompt"
            :placeholder="'输入FAQ 提示词'"
            :prompt-label="'GPT FAQ 提示词'"
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
import {
  fetchTeams,
  intervalRefreshUnFinishedTask,
  submitTask,
} from '@/api/task';
import {timestampToYMD} from '@/utils/date';
import {useRoute, useRouter} from 'vue-router';
import {
  createTaskConfig,
  getConfig,
  joinAccount,
  parseAccount, promptVMap, updatePromptConfig,
  updateTaskConfig,
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
const taskCategories = ref<[]>([]);
const onSubmitTask = async () => {
  setLoading(true);
  try {
    const {data} = await updatePromptConfig({
      configuration: {
        ...form.configuration,
      },
      config_id: form.config_id,
      remark: form.remark
    });
    await router.push({
      name: 'promptConfig',
    });
  } catch (err) {
    console.log(err);
    // you can report use errorHandler or other
  } finally {
    setLoading(false);
  }
};

const setPublishPlatform = async () => {
  const {data: value} = await fetchTeams();
  publishPlatforms.value = value.items;
};

const setTaskCategories = async () => {
  taskCategories.value = DefineTaskCategories
};

const router = useRouter();
const route = useRoute();

const form = reactive({
  platform_id: '',
  config_id: '',
  task_category: '',
  remark: '',
  configuration: {
    prompt: '',
    faq_prompt: '',
  },
});

const refreshTaskDetail = async (id: string) => {
  const {data: value} = await getConfig('prompt', id);
  form.platform_id = value.platform_id;
  form.config_id = id;
  form.task_category = value.task_category;
  form.remark = value.remark;
  form.configuration = value.configuration || {}
  form.configuration.prompt =  form.configuration.prompt || ""
};
refreshTaskDetail(route.query?.id);

setPublishPlatform();
setTaskCategories();
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
