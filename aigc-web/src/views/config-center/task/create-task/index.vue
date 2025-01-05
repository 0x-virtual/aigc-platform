<template>
  <div class="container">
    <Breadcrumb :items="['menu.config-center', 'menu.config-center.task']"/>

    <a-card class="general-card" :title="$t('menu.config-center.task')">
      <a-form ref="formRef" :model="form">
        <div class="custom-form-item">
          <label>平台</label>
          <a-select
              v-model="form.platform_id"
              placeholder="请选择"
              allow-clear
              @change="onPlatformChange"
          >
            <a-option
                v-for="option in publishPlatforms"
                :key="option.team_id"
                :value="option.team_id"
            >{{ option.display_name }}
            </a-option>
          </a-select>
        </div>
        <div class="custom-form-item">
          <label>站点</label>
          <a-select v-model="form.site_id" placeholder="请选择" allow-clear   @change="onSiteChange">
            <a-option
                v-for="option in sites"
                :key="option.config_id"
                :value="option.config_id"
            >{{ option.site_name }}
            </a-option>
          </a-select>
        </div>
        <div class="custom-form-item">
          <label>任务类别</label>
          <a-select
              v-model="form.task_category"
              placeholder="请选择"
              allow-clear
          >
            <a-option
                v-for="option in taskCategories"
                :key="option.value"
                :value="option.value"
            >{{ option.name }}
            </a-option>
          </a-select>
        </div>
        <div class="custom-form-item" v-if="form.task_category === 'qa' || form.task_category === 'article'">
          <label>发文账号</label>
          <div class="input">
            <a-textarea
                v-model="form.configuration.postCreatorAccounts"
                class="custom-textarea"
                :auto-size="{ minRows: 3, maxRows: 3 }"
                :placeholder="'按格式输入\n账号,密码\n账号2,密码2'"
            />
          </div>
        </div>
        <div v-if="['qa', 'qa_comment'].includes(form.task_category)" class="custom-form-item">
          <label>评论账号</label>
          <div class="input">
            <a-textarea
                v-model="form.configuration.commentAccounts"
                class="custom-textarea"
                :auto-size="{ minRows: 3, maxRows: 3 }"
                :placeholder="'按格式输入\n账号,密码\n账号2,密码2'"
            />
          </div>
        </div>
<!--        <Prompt-->
<!--            v-model="form.configuration.prompt"-->
<!--            :placeholder="computePromptPlaceholder(form.task_category)"-->
<!--            :prompt-label="computePromptLabel(form.task_category)"-->
<!--        ></Prompt>-->
<!--        <Prompt-->
<!--            v-if="form.task_category === 'article'"-->
<!--            v-model="form.configuration.faq_prompt"-->
<!--            :placeholder="'输入FAQ 提示词'"-->
<!--            :prompt-label="'GPT FAQ 提示词'"-->
<!--        ></Prompt>-->
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
  computePromptPlaceholder,
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
const prompts = ref<[]>([]);
const taskCategories = ref<[]>([]);
const onSubmitTask = async () => {
  setLoading(true);
  try {
    const {data} = await createTaskConfig({
      configuration: {
        ...form.configuration,
        post_creator_accounts: parseAccount(form.configuration.postCreatorAccounts),
        comment_accounts: parseAccount(form.configuration.commentAccounts),
      },
      platform_id: form.platform_id,
      task_category: form.task_category,
      site_id: form.site_id,
    });
    await router.push({
      name: 'taskConfig',
      query: {id: data.task_group_id},
    });
  } catch (err) {
    console.log(err);
    // you can report use errorHandler or other
  } finally {
    setLoading(false);
  }
};

const onPlatformChange = async (id: string) => {
  const {data: value} = await queryConfigList('site', {platform_id: id});
  sites.value = value.items;
  form.site_id = '';
};

const onSiteChange = async (id: string) => {
  // const {data: value} = await queryConfigList('prompt', {
  //   site_id: id
  // });
  // prompts.value = value.items;
  // form.prompt_id = '';
};

const setPublishPlatform = async () => {
  const {data: value} = await fetchTeams();
  publishPlatforms.value = value.items;
};

const setTaskCategories = async () => {
  taskCategories.value = DefineTaskCategories
};

const router = useRouter();

const form = reactive({
  platform_id: '',
  site_id: '',
  prompt_id: '',
  task_category: '',
  configuration: {
    postCreatorAccounts: '',
    commentAccounts: '',
    prompt: '',
    faq_prompt: '',
  },
});

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
