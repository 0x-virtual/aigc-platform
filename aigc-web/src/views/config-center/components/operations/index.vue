<template>
  <div class="btn-group">
    <a-popconfirm content="数据删除后不可恢复，是否确认删除?" type="error" @ok="handlerDelete(config, configId)">
      <a-button
          status="danger"
          type="primary medium"
          size="small"
          class="operation-btn delete-btn"
      >
        删除
      </a-button>
    </a-popconfirm>

    <a-button
        type="primary medium"
        size="small"
        class="operation-btn edit-btn"
        @click="handlerEdit(editRoute, configId)"
    >
      编辑
    </a-button>
  </div>
</template>

<script lang="ts" setup>
import {computed, ref, reactive, watch, nextTick} from 'vue';
import {useRouter} from 'vue-router';
import {deleteConfig} from "@/api/config";

const props = defineProps({
  config: {
    type: String,
  },
  configId: {
    type: String,
  },
  editRoute: {
    type: String,
  },
});

const emit = defineEmits(['onDeleted'])

const router = useRouter();

const handlerDelete = async (type: any, configId: any) => {
  console.log(type,configId);
  await deleteConfig(type as any, configId);
  emit('onDeleted', configId); // Emit the delete event
};

const handlerEdit = (editRoute: any, configId: any) => {
  router.push({
    name: editRoute,
    query: {
      id: configId,
    },
  });
};
</script>

<script lang="ts">
export default {
  name: 'ConfigCenterOperations',
};
</script>

<style scoped lang="less">
.btn-group {
  display: flex;
  gap: 10px; /* Optional: Adds space between buttons */
}

.delete-btn {
  background-color: rgb(244 120 120);
}

.edit-btn {
  background-color: #2faff8;
}

.table-search {
  margin-bottom: 10px;
}
</style>
