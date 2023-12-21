<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header" style="margin-top: 10px">
        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
          <el-form-item label="标题">
            <el-input v-model="queryForm.data.topicName" placeholder="请输入帖子标题" clearable @keyup.enter="onQuery"/>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="onQuery">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="">添加</el-button>
          </el-form-item>
        </el-form>
      </div>
    </template>
    <el-table :data="tableData['data']" border style="width: 100%">
      <el-table-column type="index" label="行id"  width="120" align="center"/>
      <el-table-column prop="topicId" label="主题ID" width="120" align="center"/>
      <el-table-column prop="topicName" label="类型名称" width="200" align="center"/>
      <el-table-column prop="createdBy" label="创建者"  width="180" align="center"/>
      <el-table-column prop="createdTime" label="创建时间"  width="200" align="center" :formatter="timeHandler"/>
      <el-table-column prop="updatedBy" label="更新者" width="180" align="center"/>
      <el-table-column prop="updatedTime" label="更新时间" width="200" align="center" :formatter="timeHandler"/>
      <el-table-column label="状态" align="center"  width="200" v-slot="{ row }">
        <el-switch
            v-model="row['topicStatus']"
            class="mb-2"
            active-value="ENABLE"
            inactive-value="DISABLE"
            active-text="启用"
            inactive-text="禁用"
            @change="handleChange($event,row['topicId'])"
        />
      </el-table-column>
      <el-table-column  label="操作" width="250" align="center" v-slot="scope">
        <el-button type="primary" plain>编辑</el-button>
        <el-button type="danger" plain @click="deleteTopic(scope.row['topicId'])">删除</el-button>
      </el-table-column>
    </el-table>
    <el-pagination
        v-model:current-page="queryForm.pageNum"
        v-model:page-size="queryForm.pageSize"
        background layout="prev, pager, next" :total="tableData['total']"
        @size-change="(pageSize) => getTFPApi({
          pageSize:pageSize,
          pageNum: 1,
          data: {
            topicName:queryForm.data.topicName
          }}).then((res) => { tableData['value'] = res.data })"
        @current-change="(pageNum) => { onChange(pageNum)}"
    />
  </el-card>

<!--  <el-dialog-->
<!--      v-model="addDictDialogVisible"-->
<!--      title="添加字典内容"-->
<!--      width="30%"-->
<!--      draggable-->
<!--  >-->
<!--    <div style="margin: 10px" />-->
<!--    <el-form-->
<!--        :label-position="'right'"-->
<!--        label-width="100px"-->
<!--        :model="addDictData"-->
<!--        style="max-width: 500px"-->
<!--    >-->
<!--      <el-form-item label="类型Id" style="width: 350px;">-->
<!--        <el-input v-model="addDictData.dictTypeId" @input="handleDictTypeIdInput" placeholder="请输入字典类型Id,不能与原有Id冲突" />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="字典类型名称">-->
<!--        <el-select-->
<!--            v-model="addDictData.dictTypeName"-->
<!--            placeholder="请输入字典类型名称"-->
<!--            clearable-->
<!--        >-->
<!--          <el-option-->
<!--              v-for="option in options"-->
<!--              :key="option.dictTypeName"-->
<!--              :value="option.dictTypeName"-->
<!--              :label="option.dictTypeName"-->
<!--          />-->
<!--        </el-select>-->
<!--      </el-form-item>-->

<!--      <el-form-item label="类型属性名称">-->
<!--        <el-input v-model="addDictData.dictItemName" placeholder="请输入字典类型属性名称"/>-->
<!--      </el-form-item>-->

<!--    </el-form>-->
<!--    <template #footer>-->
<!--      <span class="dialog-footer">-->
<!--        <el-button @click="addHandleClose">取消</el-button>-->
<!--        <el-button type="primary" @click="onSubmitAddDict">提交</el-button>-->
<!--      </span>-->
<!--    </template>-->
<!--  </el-dialog>-->


</template>

<script setup lang="ts">

import {reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import {deleteTopicApi, disableTopicApi, enableTopicApi, getTFPApi} from "@/api/topicForPost";
import {timeHandler} from "@/utils/timeHandler";
/**
 * 数据内容---加载数据(主题内容表格数据)
 */
const tableData = ref([])
const queryForm = reactive({
  pageNum:1,
  pageSize:10,
  total:1,
  data:{
    topicName:'',
  }
})
const onLoad = async() => {
  try {
    await getTFPApi(queryForm).then((res) => {
      tableData.value = res.data
      console.log(res.data)
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
onLoad()
/**
 * 查询数据---改变页码查询数据
 */
const onQuery = async() => {
  try {
    await getTFPApi(queryForm).then((res) => {
      tableData.value = res.data
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
const onChange = async(pageNum:number) => {
  try {
    const queryParams = {
      pageNum:pageNum,
      pageSize:queryForm.pageSize,
      data:queryForm.data
    }
    await getTFPApi(queryParams).then((res) => {
      tableData.value = res.data;
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
/**
 * 启用禁用主题
 * @param act
 * @param topicId
 */
const handleChange = async (act: "ENABLE" | "DISABLE",topicId: number) => {
  let actions = {
    ENABLE: {msg: '启用', fn: enableTopicApi},
    DISABLE: {msg: '禁用', fn: disableTopicApi}
  }
  const data = await actions[act].fn(topicId)
  console.log(data)
  if (data["code"] === '200') {
    ElMessage.success( `${actions[act].msg}主题`+topicId+'成功')
    await onLoad()
  } else {
    ElMessage.error(`${actions[act].msg}主题`+topicId+'失败')
    throw new Error(`${actions[act].msg}主题`+topicId+'失败')
  }
}

/**
 * 删除主题
 */
const deleteTopic = async (topicId: number) => {
  await deleteTopicApi(topicId).then((res) => {
    if (res["code"] === '200') {
      ElMessage.success(res["msg"])
      onLoad()
    } else ElMessage.error(res["msg"])
  })
}
</script>

<style lang="scss" scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>