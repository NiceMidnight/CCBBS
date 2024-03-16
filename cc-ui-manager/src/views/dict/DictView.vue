<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header" style="margin-top: 10px">
        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
          <el-form-item label="字典类型名称" @keyup.enter="onQuery" style="width: 250px">
            <el-select
                v-model="queryForm.data.dictTypeName"
                placeholder="NULL"
                clearable
                width="200px"
            >
              <el-option
                  v-for="option in options"
                  :key="option.dictTypeName"
                  :value="option.dictTypeName"
                  :label="option.dictTypeName"
              />
            </el-select>
          </el-form-item>
          <el-form-item >
            <el-button type="success" @click="onQuery">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="addDictDialogVisible = true">添加</el-button>
          </el-form-item>
        </el-form>
      </div>
    </template>
    <el-table :data="tableData['data']" border style="width: 100%">
      <el-table-column type="index" label="行id"  width="100" align="center"/>
      <el-table-column prop="dictId" label="字典Id" width="220" align="center"/>
      <el-table-column prop="dictTypeId" label="类型Id" width="220" align="center"/>
      <el-table-column prop="dictTypeName" label="字典类型名称"  width="300" align="center"/>
      <el-table-column prop="dictItemName" label="类型属性名称" width="300" align="center"/>
      <el-table-column label="颜色" width="125" align="center" v-slot="{ row }">
        <el-color-picker v-model="row['dictColor']" size="large" @change="handleColorChange(row)"/>
      </el-table-column>
    </el-table>
    <el-pagination
        v-model:current-page="queryForm.pageNum"
        v-model:page-size="queryForm.pageSize"
        background layout="prev, pager, next" :total="tableData['total']"
        @size-change="(pageSize) => getAllDict({
          pageSize:pageSize,
          pageNum: 1,
          data: {
            dictTypeName:queryForm.data.dictTypeName
          }}).then((res) => { tableData['value'] = res.data })"
        @current-change="(pageNum) => { onChange(pageNum)}"
        style="margin-top: 20px"
    />
  </el-card>

  <el-dialog
      v-model="addDictDialogVisible"
      title="添加字典内容"
      width="30%"
      draggable
  >
    <div style="margin: 10px" />
    <el-form
        :label-position="'right'"
        label-width="100px"
        :model="addDictData"
        style="max-width: 500px"
    >
      <el-form-item label="类型Id" style="width: 350px;">
        <el-input v-model="addDictData.dictTypeId" @input="handleDictTypeIdInput" placeholder="请输入字典类型Id,不能与原有Id冲突" />
      </el-form-item>
      <el-form-item label="字典类型名称">
        <el-select
            v-model="addDictData.dictTypeName"
            placeholder="请输入字典类型名称"
            clearable
        >
          <el-option
              v-for="option in options"
              :key="option.dictTypeName"
              :value="option.dictTypeName"
              :label="option.dictTypeName"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="类型属性名称">
        <el-input v-model="addDictData.dictItemName" placeholder="请输入字典类型属性名称"/>
      </el-form-item>

    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addHandleClose">取消</el-button>
        <el-button type="primary" @click="onSubmitAddDict">提交</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import {reactive, ref} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {addDictApi, getAllDict, getDictTypeName, updateDictColorApi} from "../../api/dict";
/**
 * 字典类型选择器
 */
const options = ref([])
/**
 * 数据内容---加载数据(字典类型选择器内容数据+表格数据)
 */
const tableData = ref([])
const queryForm = reactive({
  pageNum:1,
  pageSize:10,
  total:1,
  data:{
    dictTypeName:'',
  }
})
const onLoad = async() => {
  try {
    await getDictTypeName().then((res) => {
      options.value = res.data
      console.log(res.data)
    })
    await getAllDict(queryForm).then((res) => {
      tableData.value = res.data
      console.log(res.data)
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
onLoad()
/**
 * 更改主题颜色
 * @param row
 */
const handleColorChange = async (row) => {
  await updateDictColorApi(row['dictId'],row['dictColor']).then((res) => {
    if (res["code"] === '200')
    {
      ElMessage.success(res["msg"])
      onQuery()
    }
    else ElMessage.error(res['msg'])
  })
}
/**
 * 查询数据---改变页码查询数据
 */
const onQuery = async() => {
  try {
    await getAllDict(queryForm).then((res) => {
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
    await getAllDict(queryParams).then((res) => {
      tableData.value = res.data;
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
/**
 * 添加字典
 */
const addDictDialogVisible = ref(false)
const addDictData = reactive({
  dictTypeId:'',
  dictTypeName:'',
  dictItemName:''
})
const onSubmitAddDict = async () => {
  await addDictApi(addDictData).then((res) => {
    if (res["code"] === '200') {
      addDictDialogVisible.value = false
      ElMessage.success(res["msg"])
      onLoad()
    } else ElMessage.error(res["msg"])
  })
}
const addHandleClose = () => {
  ElMessageBox.confirm('是否取消添加字典内容，已填数据将清空！')
      .then(() => {
        addDictDialogVisible.value = false
        Object.keys(addDictData).forEach(key => {
          addDictData[key] = ''
        })
      })
      .catch(() => {
        // catch error
      })
}
const handleDictTypeIdInput = (value) => {
  // 只允许输入数字
  addDictData.dictTypeId = value.replace(/\D/g, '');
};

</script>

<style lang="scss" scoped>

</style>