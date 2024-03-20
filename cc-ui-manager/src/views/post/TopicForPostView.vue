<script setup lang="ts">

import {reactive, ref} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {
  addTopicApi,
  deleteTopicApi,
  disableTopicApi, editTopicApi,
  enableTopicApi,
  getTFPApi,
  getTFPStatusApi, getTopicByIdApi, updateTopicColorApi
} from "@/api/topicForPost";
import {timeHandler} from "@/utils/timeHandler";
import {getUserInfo} from "@/api/users";

/**
 * 字典类型选择器
 */
const options = ref([])
/**
 * 添加主题
 */
const addTopicDialogVisible = ref(false)
const addTopicData = reactive({
  topicName:'',
  topicStatus:'',
})
const addTopic = async () => {
  try {
    addTopicDialogVisible.value = true
    await getTFPStatusApi().then((res) => {
      options.value = res.data
      console.log(res)
    })
  } catch (e) {

  }
}
const addHandleClose = () => {
  ElMessageBox.confirm('是否取消添加帖子主题，数据将清空！')
      .then(() => {
        addTopicDialogVisible.value = false
        Object.keys(addTopicData).forEach(key => {
          addTopicData[key] = ''
        })
      })
      .catch(() => {
        // catch error
      })
}
const onSubmitAddTopic = async () => {
  try {
    await addTopicApi(addTopicData).then((res) => {
      if (res["code"] === '200') {
        addTopicDialogVisible.value = false
        ElMessage.success(res["msg"])
        Object.keys(addTopicData).forEach(key => {
          addTopicData[key] = ''
        })
        onLoad()
      } else if (res["code"] === '500') {
        ElMessage.error(res["msg"])
      }
    })
  } catch (e) {

  }
}
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
/**
 * 编辑主题视图
 */
const editTopicDialogVisible = ref(false)
const editTopicData = reactive({
  createdUserName:'',
  createdUserNickName:'',
  updatedUserName:'',
  updatedUserNickName:'',
  data:{
    createdTime:'',
    createdId:0,
    updatedTime:'',
    updatedId:0,
    topicId:0,
    topicName:'',
  }
})
const initEditTopicData = () => {
  editTopicData.createdUserName = ''
  editTopicData.createdUserNickName = ''
  editTopicData.updatedUserName = ''
  editTopicData.updatedUserNickName = ''
  editTopicData.data.updatedTime = ''
  editTopicData.data.updatedId = 0
}

/**
 * 点击编辑主题按钮
 * @param topicId
 */
const onEditTopicButton = async (topicId:number) => {
  try{
    initEditTopicData()
    editTopicDialogVisible.value = true
    editTopicData.data.topicId = topicId
    await getTopicByIdApi(topicId).then(async (res) => {
      if (res["code"] === '200') {
        editTopicData.data.createdTime = res.data.createdTime
        editTopicData.data.createdId = res.data.createdId
        getUserInfo(editTopicData.data.createdId).then((res) => {
          console.log(editTopicData.data.createdId)
          if (res["code"] === '200') {
            editTopicData.createdUserName = res.data["userName"]
            editTopicData.createdUserNickName = res.data["nickName"]
          } else console.log(res["msg"])
        })

        if (res.data.updatedId) {
          editTopicData.data.updatedTime = res.data.updatedTime
          editTopicData.data.updatedId = res.data.updatedId
          getUserInfo(editTopicData.data.updatedId).then((res) => {
            console.log(editTopicData.data.updatedId)
            if (res["code"] === '200') {
              editTopicData.updatedUserName = res.data["userName"]
              editTopicData.updatedUserNickName = res.data["nickName"]
            } else console.log(res["msg"])
          })
        }
        editTopicData.data.topicName = res.data.topicName

      } else console.log(res["msg"])
    })
  }catch (e) {
    console.log(e)
  }
}
/**
 * 提交编辑主题
 */
const editTopic = async () => {
  await editTopicApi(editTopicData.data.topicName,editTopicData.data.topicId).then((res) => {
    if (res["code"] === '200')
    {
      ElMessage.success(res["msg"])
      editTopicDialogVisible.value = false
      onLoad()
    } else ElMessage.error(res["msg"])
  })
}
/**
 * 关闭编辑窗口
 */
const editHandleClose = () => {
  editTopicDialogVisible.value = false
}
/**
 * 更改主题颜色
 * @param row
 */
const handleColorChange = async (row) => {
  if (row['topicColor'] === '' || row['topicColor'] === null)
  {
    ElMessage.error("颜色不能为空")
    return
  }
  await updateTopicColorApi(row['topicId'],row['topicColor']).then((res) => {
    if (res["code"] === '200')
    {
      ElMessage.success(res["msg"])
      onQuery()
    }
    else ElMessage.error(res['msg'])
  })
}
</script>

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
            <el-button type="primary" @click="addTopic">添加</el-button>
          </el-form-item>
        </el-form>
      </div>
    </template>
    <el-table :data="tableData['data']" border style="width: 100%">
      <el-table-column type="index" label="行id"  width="120" align="center"/>
      <el-table-column prop="topicId" label="主题ID" width="120" align="center"/>
      <el-table-column prop="topicName" label="类型名称" width="200" align="center"/>
      <el-table-column prop="createdBy" label="创建者"  width="155" align="center"/>
      <el-table-column prop="createdTime" label="创建时间"  width="200" align="center" :formatter="timeHandler"/>
      <el-table-column prop="updatedBy" label="更新者" width="155" align="center"/>
      <el-table-column prop="updatedTime" label="更新时间" width="200" align="center" :formatter="timeHandler"/>
      <el-table-column label="主题颜色" width="125" align="center" v-slot="{ row }">
        <el-color-picker v-model="row['topicColor']" size="large" @change="handleColorChange(row)"/>
      </el-table-column>
      <el-table-column label="状态" align="center"  width="180" v-slot="{ row }">
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
      <el-table-column  label="操作" width="200" align="center" v-slot="scope">
        <el-button type="primary" plain @click="onEditTopicButton(scope.row['topicId'])">编辑</el-button>
        <el-button type="danger" plain @click="deleteTopic(scope.row['topicId'])">删除</el-button>
      </el-table-column>
    </el-table>
    <el-pagination
        v-model:current-page="queryForm.pageNum"
        v-model:page-size="queryForm.pageSize"
        background layout="prev, pager, next"
        :total="tableData['total']"

        @size-change="(pageSize) => getTFPApi({
          pageSize:pageSize,
          pageNum: 1,
          data: {
            topicName:queryForm.data.topicName
          }}).then((res) => { tableData['value'] = res.data })"
        @current-change="(pageNum) => { onChange(pageNum)}"
        style="margin-top: 20px"
    />
  </el-card>
<!--添加主题-->
  <el-dialog
      v-model="addTopicDialogVisible"
      title="添加帖子主题"
      width="30%"
      draggable
  >
    <div style="margin: 10px" />
    <el-form
        :label-position="'right'"
        label-width="100px"
        :model="addTopicData"
        style="max-width: 500px"
    >
      <el-form-item label="主题名称" style="width: 350px;">
        <el-input v-model="addTopicData.topicName" placeholder="输入主题名称" />
      </el-form-item>
      <el-form-item label="主题状态">
        <el-select
            v-model="addTopicData.topicStatus"
            placeholder="请选择字典类型名称"
            clearable
        >
          <el-option
              v-for="option in options"
              :key="option"
              :value="option"
              :label="option"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addHandleClose">取消</el-button>
        <el-button type="primary" @click="onSubmitAddTopic">提交</el-button>
      </span>
    </template>
  </el-dialog>


  <!--编辑主题-->
  <el-dialog
      v-model="editTopicDialogVisible"
      :title="`编辑主题名称--${editTopicData.data.topicId}`"
      width="40%"
      draggable
  >
    <div style="margin: 10px" />
    <el-form
        :label-position="'right'"
        label-width="100px"
        :model="editTopicData"
        style="max-width: 700px"
    >
      <el-form-item label="上传用户名">
        <el-text class="mx-1" type="success">{{editTopicData.createdUserName}}</el-text>
      </el-form-item>
      <el-form-item label="昵称">
        <el-text class="mx-1" type="success">{{editTopicData.createdUserNickName}}</el-text>
      </el-form-item>
      <el-form-item label="上传时间">
        <el-text class="mx-1" type="success">{{timeHandler(null,null,editTopicData.data.createdTime)}}</el-text>
      </el-form-item>


      <el-form-item label="更新用户名">
        <el-text class="mx-1" type="success">{{editTopicData.updatedUserName}}</el-text>
      </el-form-item>
      <el-form-item label="昵称">
        <el-text class="mx-1" type="success">{{editTopicData.updatedUserNickName}}</el-text>
      </el-form-item>
      <el-form-item label="更新时间">
        <el-text class="mx-1" type="success">{{timeHandler(null,null,editTopicData.data.updatedTime)}}</el-text>
      </el-form-item>

      <el-form-item label="主题名称">
        <el-input
            v-model="editTopicData.data.topicName"
            maxlength="6"
            placeholder="请输入主题名称"
            show-word-limit
            type="text"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="editHandleClose">取消</el-button>
        <el-button type="primary" @click="editTopic">提交</el-button>
      </span>
    </template>
  </el-dialog>

</template>



<style lang="scss" scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>