<template>
  <div class="container">
    <Breadcrumb :items="['menu.config-center', 'menu.config-center.ai']" />

    <a-card class="general-card" :title="$t('menu.config-center.ai')">
      <a-form ref="formRef" :model="form">
        <div class="custom-form-item">
          <label>GPT提供商</label>
          <a-select v-model="form.provider" placeholder="请选择" allow-clear>
            <a-option
              v-for="option in gptProviders"
              :key="option.value"
              :value="option.value"
              >{{ option.name }}
            </a-option>
          </a-select>
        </div>
        <div class="custom-form-item">
          <label>Base Url</label>
          <div class="input">
            <a-input
              class="custom-textarea"
              v-model="form.baseUrl"
              :placeholder="'GPT 接口地址'"
              allow-clear
            />
          </div>
        </div>
        <div class="custom-form-item">
          <label>Api Key</label>
          <div class="input">
            <a-textarea
              class="custom-textarea"
              :auto-size="{ minRows: 1, maxRows: 1 }"
              v-model="form.apiKey"
              :placeholder="'请输入 API KEY'"
              allow-clear
            />
          </div>
        </div>
        <div class="custom-form-item">
          <label>模型</label>
          <div class="input">
            <a-input
              class="custom-textarea"
              v-model="form.model"
              :placeholder="'请输入（模型只能输入一个）'"
              allow-clear
            />
          </div>
        </div>
      </a-form>
      <div class="submit-button-space"></div>
      <a-button type="primary" @click="onSubmitTask">提交</a-button>
    </a-card>
  </div>
</template>

<script lang="ts" setup>
  import { computed, ref, reactive, watch, nextTick } from 'vue';
  import { useI18n } from 'vue-i18n';
  import useLoading from '@/hooks/loading';
  import { PolicyRecord } from '@/api/list';
  import type { TableColumnData } from '@arco-design/web-vue/es/table/interface';
  import { fetchTeams, submitTask } from '@/api/task';
  import { timestampToYMD } from '@/utils/date';
  import { useRouter } from 'vue-router';
  import { createAiConfig, createTaskConfig, parseAccount } from '@/api/config';

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
  const { loading, setLoading } = useLoading(true);
  const { t } = useI18n();
  const renderData = ref<PolicyRecord[]>([]);
  const formModel = ref(generateFormModel());
  const cloneColumns = ref<Column[]>([]);
  const showColumns = ref<Column[]>([]);
  const publishPlatforms = ref<[]>([]);
  const gptTypes = ref<[]>([]);
  const gptProviders = ref<[]>([]);
  const onSubmitTask = async () => {
    setLoading(true);
    try {
      const { data } = await createAiConfig({
        baseUrl: form.baseUrl,
        provider: form.provider,
        apiKey: form.apiKey,
        model: form.model,
      });
      await router.push({
        name: 'aiConfig',
      });
    } catch (err) {
      console.log(err);
      // you can report use errorHandler or other
    } finally {
      setLoading(false);
    }
  };

  const setTaskCategories = async () => {
    gptTypes.value = [
      {
        name: 'GPT3.5',
        value: 'gpt3',
      },
      {
        name: 'GPT4.0',
        value: 'gpt4',
      },
      {
        name: 'GPT-Image',
        value: 'gpt_image',
      },
    ];
  };

  const setGptProviders = async () => {
    gptProviders.value = [
      {
        name: 'OpenAI',
        value: 'openai',
      },
    ];
  };

  const router = useRouter();

  const form = reactive({
    provider: 'openai',
    model: '',
    baseUrl: '',
    apiKey: '',
  });

  setTaskCategories();
  setGptProviders();
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
