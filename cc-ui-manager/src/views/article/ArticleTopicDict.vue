<script setup lang="ts">
import {timeHandler} from "@/utils/timeHandler";
import {reactive, ref} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {
  addTopicApi,
  disableTopicForArticleApi,
  enableTopicForArticleApi,
  getArticleTopicApi,
  updateTopicColorApi
} from "@/api/topicForArticle";
/**
 * 表单数据---查询数据
 */
const topicForArticle = ref([])

const onLoad = async () => {
  await getArticleTopicApi().then((res) => {
    console.log(res)
    topicForArticle.value = res.data
  })
}
onLoad()

/**
 * 设置图片状态可见/不可见
 * @param act
 * @param topicId
 */
const handleChange = async (act: "ENABLE" | "DISABLE", topicId: number) => {
  let actions = {
    ENABLE: {msg:'启用',fn: enableTopicForArticleApi},
    DISABLE: {msg:'禁用',fn: disableTopicForArticleApi}
  }
  const data = await actions[act].fn(topicId)
  console.log(data)
  if (data["code"] === '200') {
    // "公告主题启用成功（id："+topicId+")"
    ElMessage.success(data['msg'])
  } else {
    ElMessage.error(data['msg'])
    throw new Error(data['msg'])
  }
}
/**
 * 更新主题颜色
 * @param row
 */
const handleColorChange = async (row:any) => {
  if (row['topicColor'] === '' || row['topicColor'] === null)
  {
    ElMessage.error("颜色不能为空")
    return
  }
  await updateTopicColorApi(row['topicId'],row['topicColor']).then((res) => {
    if (res["code"] === '200')
    {
      ElMessage.success(res["msg"])
      onLoad()
    }
    else ElMessage.error(res['msg'])
  })
}
/**
 * 添加公告主题
 */
const addTopicDialogVisible = ref(false)
const topicData = reactive({
  topicName:'',
  topicColor:''
})
const handleClose = () => {
  ElMessageBox.confirm('是否取消添加公告主题，已填数据将清空！')
      .then(() => {
        addTopicDialogVisible.value = false
        Object.keys(topicData).forEach(key => {
          topicData[key] = ''
        })
      })
      .catch(() => {
        // catch error
      })
}
const onSubmitAddTopic = async () => {
  await addTopicApi(topicData).then((res) => {
    if (res['code'] === '200')
    {
      ElMessage.success(res['msg'])
      Object.keys(topicData).forEach(key => {
        topicData[key] = ''
      })
      addTopicDialogVisible.value = false
      onLoad()
    } else ElMessage.error(res['msg'])
  })
}
</script>

<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header" style="margin: 10px;">
        <el-form-item>
          <el-button type="primary" @click="addTopicDialogVisible = true">添加</el-button>
        </el-form-item>
      </div>
    </template>
    <el-table ref="multipleTableRef" :data="topicForArticle" border style="width: 100%">
      <!-- 自动递增的行ID列 -->
      <el-table-column type="index" label="行id" width="70" align="center" fixed/>
      <el-table-column prop="topicId" label="主题id" width="70" align="center" fixed/>
      <el-table-column prop="topicName" label="主题名" width="100" align="center"/>
      <el-table-column prop="createdId" label="创建者id" width="100" align="center"/>
      <el-table-column prop="createdBy" label="创建者" width="100" align="center"/>
      <el-table-column prop="createdTime" label="创建时间"  width="170" align="center" :formatter="timeHandler"/>
      <el-table-column  label="主题颜色" width="125" align="center" v-slot="{ row }">
        <el-color-picker v-model="row['topicColor']" size="large" @change="handleColorChange(row)"/>
      </el-table-column>
      <el-table-column label="可见性" align="center"  width="200" v-slot="{ row }">
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
    </el-table>
  </el-card>
  <!--添加公告主题-->
  <el-dialog
      v-model="addTopicDialogVisible"
      title="添加公告主题"
      width="25%"
      draggable
  >
    <div style="margin: 40px" />
    <el-form
        :label-position="'right'"
        label-width="100px"
        :model="topicData"
        style="max-width: 300px"
    >
      <el-form-item label="主题名">
        <el-input v-model="topicData.topicName" placeholder="请输入主题"/>
      </el-form-item>
      <el-form-item label="主题颜色">
        <el-color-picker v-model="topicData.topicColor" size="large"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="onSubmitAddTopic">提交</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped lang="scss">

</style>