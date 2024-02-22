<script setup lang="ts">
import {reactive, ref} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {
  addArticleApi,
  articleViewApi,
  deleteArticleApi,
  editArticleApi,
  getAllArticle,
  getOneArticleApi, makeAnArticlePrivateApi, makeAnArticlePublicApi
} from "../../api/article";
import {getUserName} from "@/api/users";
import {timeHandler} from "@/utils/timeHandler";
import {truncateText} from "@/utils/textHandler";
import {getArticleTopicApi} from "@/api/topicForArticle";
import TextEditor from "@/views/article/TextEditor.vue";
const drawer = ref(false)
/**
 * 文本截断
 * @param row
 */
const truncateTextFormatter = (row: any) => {
  return truncateText(row.articleContent, 42);
};
/**
 * 添加文章
 */
const addArticleDialogVisible = ref(false)
const articleData = reactive({
  createdBy:'',
  articleTitle:'',
  articleContent:'',
})
const addArticle = async () => {
  addArticleDialogVisible.value = true
  await getUserName().then((res) => {
    console.log(res)
    articleData.createdBy = res.data
  })
}
const handleClose = () => {
  ElMessageBox.confirm('是否取消添加文章，数据将清空！')
      .then(() => {
        addArticleDialogVisible.value = false
        Object.keys(articleData).forEach(key => {
          articleData[key] = ''
        })
      })
      .catch(() => {
        // catch error
      })
}
const onSubmitAddArticle = async () => {
  try {
    await addArticleApi(articleData).then((res) => {
      addArticleDialogVisible.value = false
      if (res["code"] === 500) {
        ElMessage.error(res["msg"])
      } else ElMessage.success(res["msg"])
      onLoad()
    })
  } catch (e) {
    ElMessage.error(e)
  }
}


/**
 * 编辑文章   数据---对话框数据---取消清空数据---提交编辑数据
 */
const editArticleDialogVisible = ref(false)
const editArticleData = reactive({
  createdBy:'',
  updatedBy:'',
  articleId:'',
  articleTitle:'',
  articleContent:'',
  topicId:''
})
const editArticle = async(article) => {
  try {
    editArticleDialogVisible.value = true
    editArticleData.createdBy = article.createdBy
    if (article.updatedBy)
    {
      editArticleData.updatedBy = article.createdBy
    } else editArticleData.updatedBy = "未曾更新"
    editArticleData.articleId = article.articleId
    editArticleData.articleTitle = article.articleTitle
    editArticleData.articleContent = article.articleContent
    editArticleData.topicId = article.topicId
  } catch (e) {
    ElMessage.error(e)
  }
}
const editHandleClose = () => {
  ElMessageBox.confirm('是否取消编辑文章，数据将清空！')
      .then(() => {
        editArticleDialogVisible.value = false
        Object.keys(editArticleData).forEach(key => {
          editArticleData[key] = ''
        })
      })
      .catch(() => {
        // catch error
      })
}
const onSubmitEditArticle = async () => {
  try {
    if (editArticleData.topicId != null)
    {
      await editArticleApi(editArticleData).then((res) => {
        console.log(res)
        if (res["code"] === 500) {
          ElMessage.error("文章"+editArticleData.articleId+res["msg"])
        }
        else {
          editArticleDialogVisible.value = false
          onLoad()
          ElMessage.success("文章"+editArticleData.articleId+res["msg"])
        }
      })
    }
    else ElMessage.error("主题不能为空")
  } catch (e) {
    ElMessage.error(e)
  }
}

/**
 * 获取下拉主题选择
 */
const options = ref([])
/**
 * 表单数据---查询数据
 */
const tableData = ref([])
const queryForm = reactive({
  pageNum:1,
  pageSize:10,
  total:1,
  data:{
    createdBy:'',
    articleTitle:'',
    articleContent:''
  }
})
const onLoad = async() => {
  try {
    await getArticleTopicApi().then((res) => {
      options.value = res.data
    })
    await getAllArticle(queryForm).then((res) => {
      tableData.value = res.data
      console.log(res.data)
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
onLoad()
/**
 * 设置图片状态可见/不可见
 * @param act
 * @param topicId
 */
const handleChange = async (act: "PUBLIC" | "PRIVATE", topicId: number) => {
  let actions = {
    PUBLIC: {msg:'公开',fn: makeAnArticlePublicApi},
    PRIVATE: {msg:'私有',fn: makeAnArticlePrivateApi}
  }
  const data = await actions[act].fn(topicId)
  console.log(data)
  if (data["code"] === '200') {
    ElMessage.success("公告"+topicId+data["msg"])
  } else {
    ElMessage.error("公告"+topicId+data["msg"])
    throw new Error("公告"+topicId+data["msg"])
  }
}
/**
 * 查找
 */
const onQuery = async() => {
  try {
    await getAllArticle(queryForm).then((res) => {
      tableData.value = res.data
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
/**
 * 改变页码
 */
const small = ref(false)
const background = ref(false)
const disabled = ref(false)
const handleSizeChange = async (size:number) => {
  try {
    const queryParams = {
      pageNum: queryForm.pageNum,
      pageSize: size,
      data:queryForm.data
    }
    await getAllArticle(queryParams).then((res) => {
      tableData.value = res.data
    })
  } catch (e) {
    ElMessage.error("改变每页大小错误，错误信息："+e)
  }
}
const handleCurrentChange = async (num:number) => {
  try {
    const queryParams = {
      pageNum: num,
      pageSize: queryForm.pageSize,
      data:queryForm.data
    }
    await getAllArticle(queryParams).then((res) => {
      tableData.value = res.data
    })
  } catch (e) {
    ElMessage.error("改变页数错误，错误信息："+e)
  }
}
/**
 * 文章视图
 */
const articleMessage = ref([])
const articleView = async (article) => {
  try {
    await articleViewApi(article.articleId).then((res) => {
      drawer.value = true
      articleMessage.value = res.data
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
/**
 * 删除文章
 */
const ifDeleteArticle = async (article) => {
  ElMessageBox.confirm(
      '是否确认删除文章',
      '删除文章',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(()=> {
    deleteArticleApi(article.articleId).then((res) => {
      if (res["code"] === 500) {
        ElMessage.error(res["msg"])
      } else ElMessage.success(res["msg"])
    })
    onLoad()
  }).catch(() => {
    ElMessage.info('取消删除')
  })
}
</script>

<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header" style="margin-top: 10px;width: 1800px">
        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
          <el-form-item label="创建者名称">
            <el-input v-model="queryForm.data.createdBy" placeholder="请输入创建者名称" clearable @keyup.enter="onQuery"/>
          </el-form-item>
          <el-form-item label="标题">
            <el-input v-model="queryForm.data.articleTitle" placeholder="请输入文章标题" clearable @keyup.enter="onQuery"/>
          </el-form-item>
          <el-form-item label="内容">
            <el-input v-model="queryForm.data.articleContent" placeholder="请输入文章内容" clearable @keyup.enter="onQuery"/>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="onQuery">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="addArticle">添加</el-button>
          </el-form-item>
        </el-form>
      </div>
    </template>
    <el-table :data="tableData['data']" border style="width: 100%">
      <el-table-column type="selection" width="55" />
      <!-- 自动递增的行ID列 -->
      <el-table-column type="index" label="行id" width="60" align="center" fixed/>
      <el-table-column prop="articleId" label="文章id" width="70" align="center" fixed/>
      <el-table-column prop="articleTitle" label="文章标题" width="150" align="center"/>
      <el-table-column prop="articleContent" label="文章内容"  width="200" align="center" :formatter="truncateTextFormatter"/>
      <el-table-column prop="topicId" label="主题id" width="70" align="center"/>
      <el-table-column label="主题" width="150" align="center">
        <template #default="{ row }">
          <el-tag size="large" :style="{ backgroundColor: row['topicColor'] }">{{ row['topicName'] }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createdBy" label="创建者"  width="100" align="center"/>
      <el-table-column prop="createdTime" label="创建时间"  width="170" align="center" :formatter="timeHandler"/>
      <el-table-column prop="updatedBy" label="更新者"  width="100" align="center"/>
      <el-table-column prop="updatedTime" label="更新时间"  width="170" align="center" :formatter="timeHandler"/>
      <el-table-column label="可见性" align="center"  width="200" v-slot="{ row }">
        <el-switch
            v-model="row['article_status']"
            class="mb-2"
            active-value="PUBLIC"
            inactive-value="PRIVATE"
            active-text="可见"
            inactive-text="不可见"
            @change="handleChange($event,row.articleId)"
        />
      </el-table-column>
      <el-table-column fixed="right" label="操作选项" align="center" width="230" >
        <template #default="{ row }">
          <el-button type="default" @click="articleView(row)" plain>查看</el-button>
          <el-button type="primary" @click="editArticle(row)" plain>编辑</el-button>
          <el-button type="danger" @click="ifDeleteArticle(row)" plain>删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        v-model:current-page="queryForm.pageNum"
        v-model:page-size="queryForm.pageSize"
        :page-sizes="[ 10, 20, 30]"
        :small="small"
        :disabled="disabled"
        :background="background"
        layout="total, sizes, prev, pager, next, jumper"
        :total="tableData['total']"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        style="margin-top: 20px"
    />
    <el-drawer v-model="drawer" :title="articleMessage['articleTitle']" :direction="'ltr'">
      <span v-html="articleMessage['articleContent'] "></span>
    </el-drawer>
  </el-card>

<!--添加文章-->
  <el-dialog
      v-model="addArticleDialogVisible"
      title="添加系统文章"
      width="40%"
      draggable
  >
    <div style="margin: 10px" />
    <el-form
        :label-position="'right'"
        label-width="100px"
        :model="articleData"
        style="max-width: 660px"
    >
      <el-form-item label="文章标题">
        <el-input v-model="articleData.articleTitle" placeholder="请输入文章标题"/>
      </el-form-item>
      <el-form-item label="文章内容">
        <el-input v-model="articleData.articleContent" type="textarea" :rows="12" placeholder="请输入文章内容"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="onSubmitAddArticle">提交</el-button>
      </span>
    </template>
  </el-dialog>

<!--编辑文章-->
  <el-dialog
      v-model="editArticleDialogVisible"
      title="编辑系统文章"
      width="60%"
      style="margin-top: 20px"
      draggable
  >
    <div style="margin: 10px" />
    <el-form
        :label-position="'right'"
        label-width="100px"
        :model="editArticleData"
        style="max-width: 1200px"
    >
      <el-form-item label="上传管理员">
        <el-text class="mx-1" type="success">{{editArticleData.createdBy}}</el-text>
      </el-form-item>
      <el-form-item label="更新者">
        <el-text class="mx-1" type="success">{{editArticleData.updatedBy}}</el-text>
      </el-form-item>
      <el-form-item label="更改主题">
        <el-select v-model="editArticleData.topicId" :clearable="false" placeholder="NULL">
          <el-option
              v-for="option in options"
              :key="option['topicId']"
              :value="option['topicId']"
              :label="option['topicName']"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="文章id">
        <el-text class="mx-1" type="success" > {{ editArticleData.articleId }}</el-text>
      </el-form-item>
      <el-form-item label="文章标题">
        <el-input v-model="editArticleData.articleTitle" placeholder="请输入文章标题"/>
      </el-form-item>
      <el-form-item label="文章内容" >
        <div>
          <TextEditor
              v-model="editArticleData.articleContent"
          />
        </div>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="editHandleClose">取消</el-button>
        <el-button type="primary" @click="onSubmitEditArticle">提交</el-button>
      </span>
    </template>
  </el-dialog>
</template>



<style lang="scss" scoped>

</style>