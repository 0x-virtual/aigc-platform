<template>
  <div class="container">
    <Breadcrumb :items="['menu.task-center', '投放任务']"/>

    <a-card class="general-card" :title="'新建投放任务1'">
      <a-form ref="formRef" :model="form">
        <div class="custom-form-item">
          <label>发布地址1</label>
          <a-select
              v-model="form.publish_site_id"
              placeholder="Please select ..."
              allow-clear
              @change="onSiteChange"
          >
            <a-option
                v-for="option in sites"
                :key="option.id"
                :value="option.id"
            >{{ option.siteName }}
            </a-option>
          </a-select>
        </div>
        <PromptSelect task-category="article" v-model="form.prompt_id" ref="promptSelectRef"></PromptSelect>
        <div class="custom-form-item">
          <label>文章标题</label>
          <div class="input">
            <a-textarea
                v-model="form.titles"
                class="custom-textarea"
                :auto-size="{ minRows: 3, maxRows: 3 }"
                :placeholder="'按格式输入\n标题1|分类1\n标题2|分类2'"
                allow-clear
            />
          </div>
        </div>
      </a-form>
      <div class="submit-button-space"></div>
      <a-button type="primary" @click="onSubmitTask" :disabled="loading"
      >提交
      </a-button
      >
    </a-card>
  </div>
</template>

<script lang="ts" setup>
import {computed, ref, reactive, watch, nextTick} from 'vue';
import {useI18n} from 'vue-i18n';
import useLoading from '@/hooks/loading';
import {queryPolicyList, PolicyRecord, PolicyParams} from '@/api/list';
import {
  fetchImageCategories,
  queryTaskList,
  submitArticleTask,
  submitTask,
} from '@/api/task';
import {useRouter} from 'vue-router';
import { queryConfigList, queryCurrentTeamSitesList } from '@/api/config';
import {i18nLanguages} from "@/service/task-center/translate";
import PromptSelect from "@/views/list/components/prompt-select/index.vue";

const generateFormModel = () => {
  return {
    titles: '',
    prompt: '',
    publish_platform: '',
    comment_count: 3,
  };
};
const {loading, setLoading} = useLoading(false);
const {t} = useI18n();
const renderData = ref<PolicyRecord[]>([]);
const formModel = ref(generateFormModel());
const sites = ref<[]>([]);

const onSubmitTask = async () => {
  setLoading(true);
  try {
    const {data} = await submitArticleTask({
      promptId: form.prompt_id,
      titles: form.titles,
      siteId: form.publish_site_id,
    });
    await router.push({
      name: 'articleTaskDetail',
      query: {id: data.id},
    });
  } catch (err) {
    // you can report use errorHandler or other
  } finally {
    setLoading(false);
  }
};

const setSites = async () => {
  console.log('sites');
  const {data} = await queryConfigList('site', {});
  sites.value = data.value ;
  if (sites.value && sites.value.length > 0) {
    form.publish_site_id = sites.value[0].id;
    onSiteChange(form.publish_site_id);
  }
};

const router = useRouter();

const form = reactive({
  prompt_id: '',
  publish_site_id: '',
  image_category_id: '',
  image_source: '',
  titles: '',
  content_prompt: '',
  faq_prompt: '',
  language: "no_translate",
});
const promptSelectRef = ref(null);
const onSiteChange = (id:string) => {
  promptSelectRef.value.onSiteChange(id);
};
setSites();
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
