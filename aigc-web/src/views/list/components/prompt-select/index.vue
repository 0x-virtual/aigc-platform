<template>
  <div class="prompt-select-container">
    <div class="custom-form-item">
      <label>提示词</label>
      <a-select
          v-model="promptId"
          placeholder="请选择"
      >
        <a-option
            v-for="option in prompts"
            :key="option.id"
            :value="option.id"
        >{{ option.remark }}
        </a-option>
      </a-select>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {onMounted, ref, watch} from 'vue';
import {queryConfigList} from "@/api/config";

const props = defineProps({
  modelValue: {
    type: String,
  },
  taskCategory: {
    type: String,
  },
});
const prompts = ref<[]>([]);

const onSiteChange = async (id: string) => {
  const {data} = await queryConfigList('prompt', {
    enabled: 1,
  });
  prompts.value = data.value;
  emit('update:modelValue', '');
};

const emit = defineEmits(['update:modelValue']);
const promptId = ref(props.modelValue);

watch(promptId, (newValue) => {
  emit('update:modelValue', newValue);
});

watch(
    () => props.modelValue,
    (newValue) => {
      promptId.value = newValue;
    }
);

// onMounted(() => {
//   if (props.modelValue) {
//     onSiteChange(props.modelValue)
//   }
// })
defineExpose({onSiteChange});
</script>

<script lang="ts">
export default {
  name: 'PromptSelect',
};
</script>

<style scoped lang="less">
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
</style>
