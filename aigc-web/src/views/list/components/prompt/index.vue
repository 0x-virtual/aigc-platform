<template>
  <div class="prompt-container">
    <div class="custom-form-item">
      <label>{{ promptLabel }}</label>
      <div class="input">
        <a-textarea
          v-model="innerPrompt"
          class="custom-textarea"
          :auto-size="{ minRows: 3, maxRows: 3 }"
          :placeholder="placeholder"
        />
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
  import { ref, watch } from 'vue';

  const props = defineProps({
    promptLabel: {
      type: String,
    },
    modelValue: {
      type: String,
    },
    placeholder: {
      type: String,
    },
  });
  const emit = defineEmits(['update:modelValue']);
  const innerPrompt = ref(props.modelValue);

  watch(innerPrompt, (newValue) => {
    emit('update:modelValue', newValue);
  });

  watch(
    () => props.modelValue,
    (newValue) => {
      innerPrompt.value = newValue;
    }
  );
</script>

<script lang="ts">
  export default {
    name: 'Prompt',
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
