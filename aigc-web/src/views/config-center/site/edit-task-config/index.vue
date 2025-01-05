<template>
  <div class="container">
    <Breadcrumb :items="['menu.config-center', 'menu.config-center.site']" />

    <a-card class="general-card" :title="$t('menu.config-center.site')">
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
          <label>站点名称</label>
          <div class="input">
            <a-input
              class="custom-textarea"
              v-model="form.site_name"
              :placeholder="'站点名称'"
              disabled
            />
          </div>
        </div>
        <div class="custom-form-item">
          <label>Base Url</label>
          <div class="input">
            <a-input
              class="custom-textarea"
              v-model="form.site_base_url"
              :placeholder="'站点 接口地址'"
              allow-clear
            />
          </div>
        </div>
        <div class="custom-form-item">
          <label>最大并发数</label>
          <div class="input">
            <a-input
                class="custom-textarea"
                v-model="form.max_parallel_limit"
                :placeholder="' 站点最大并发数'"
                allow-clear
            />
          </div>
        </div>
        <div class="custom-form-item">
          <label>管理员账号</label>
          <div class="input">
            <a-textarea
              class="custom-textarea"
              :auto-size="{ minRows: 3, maxRows: 3 }"
              v-model="form.admin_account"
              :placeholder="'按格式输入\n账号,密码\n账号2,密码2'"
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
  import { useRoute, useRouter } from 'vue-router';
  import {
    createAiConfig,
    createSiteConfig,
    createTaskConfig,
    getConfig,
    joinAccount,
    parseAccount,
    updateSiteConfig,
  } from '@/api/config';

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
  const route = useRoute();

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
      const { data } = await updateSiteConfig({
        base_url: form.site_base_url,
        config_id: form.config_id,
        max_parallel_limit: Number(form.max_parallel_limit),
        admin_account: parseAccount(form.admin_account)[0],
      });
      await router.push({
        name: 'siteConfig',
      });
    } catch (err) {
      console.log(err);
      // you can report use errorHandler or other
    } finally {
      setLoading(false);
    }
  };

  const setPublishPlatform = async () => {
    const { data: value } = await fetchTeams();
    publishPlatforms.value = value.items;
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
      {
        name: '微软',
        value: 'azure',
      },
      {
        name: 'Replicate',
        value: 'replicate',
      },
    ];
  };

  const router = useRouter();
  const refreshTaskDetail = async (id: string) => {
    const { data: value } = await getConfig('site', id);
    form.siteName = value.siteName;
    form.baseUrl = value.baseUrl;
    form.adminUsername = value.adminUsername;
    form.adminPassword = value.adminPassword;
    form.id = value.id;
  };
  refreshTaskDetail(route.query?.id);
  const form = reactive({
    config_id: '',
    platform_id: '',
    admin_account: '',
    max_parallel_limit: '',
    site_name: '',
    site_base_url: '',
  });

  setPublishPlatform();
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
