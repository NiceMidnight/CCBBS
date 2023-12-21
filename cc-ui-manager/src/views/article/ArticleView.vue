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
      <!-- 自动递增的行ID列 -->
      <el-table-column type="index" label="行id" width="100" align="center"/>
      <el-table-column prop="articleId" label="文章id" width="100" align="center"/>
      <el-table-column prop="articleTitle" label="文章标题" width="200" align="center"/>
      <el-table-column prop="articleContent" label="文章内容"  width="330" align="center"/>
      <el-table-column prop="createdBy" label="创建者"  width="100" align="center"/>
      <el-table-column prop="createdTime" label="创建时间"  width="200" align="center" :formatter="timeHandler"/>
      <el-table-column prop="updatedBy" label="更新者"  width="100" align="center"/>
      <el-table-column prop="updatedTime" label="更新时间"  width="200" align="center" :formatter="timeHandler"/>
      <el-table-column  label="操作" width="327" align="center" v-slot="scope">
        <el-button type="default" @click="articleView(scope.row.articleId)" plain>查看</el-button>
        <el-button type="primary" @click="editArticle(scope.row.articleId)" plain>编辑</el-button>
        <el-button type="danger" @click="ifDeleteArticle(scope.row.articleId)" plain>删除</el-button>
      </el-table-column>
    </el-table>
    <el-pagination
        v-model:current-page="queryForm.pageNum"
        v-model:page-size="queryForm.pageSize"
        background layout="prev, pager, next" :total="tableData['total']"
        @current-change="(pageNum) => { onChange(pageNum)}"
    />
    <el-drawer v-model="drawer" :title="article['articleTitle']" :direction="'ltr'">
      <span>{{ article['articleContent'] }}</span>
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
      width="40%"
      draggable
  >
    <div style="margin: 10px" />
    <el-form
        :label-position="'right'"
        label-width="100px"
        :model="editArticleData"
        style="max-width: 700px"
    >
      <el-form-item label="上传管理员">
        <el-text class="mx-1" type="success">{{editArticleData.createdBy}}</el-text>
      </el-form-item>
      <el-form-item label="更新者">
        <el-text class="mx-1" type="success">{{editArticleData.updatedBy}}</el-text>
      </el-form-item>
      <el-form-item label="文章id">
        <el-text class="mx-1" type="success" > {{ editArticleData.articleId}}</el-text>
      </el-form-item>
      <el-form-item label="文章标题">
        <el-input v-model="editArticleData.articleTitle" placeholder="请输入文章标题"/>
      </el-form-item>
      <el-form-item label="文章内容">
        <el-input v-model="editArticleData.articleContent" type="textarea" :rows="12" placeholder="请输入文章内容"/>
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

<script setup lang="ts">
import {reactive, ref} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {
  addArticleApi,
  articleViewApi,
  deleteArticleApi,
  editArticleApi,
  getAllArticle,
  getOneArticleApi
} from "../../api/article";
import {getUserName} from "@/api/users";
import {timeHandler} from "@/utils/timeHandler";
const drawer = ref(false)
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
  await getUserName(localStorage.getItem("TokenInfo")).then((res) => {
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
    await addArticleApi(articleData,localStorage.getItem("TokenInfo")).then((res) => {
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
})
const editArticle = async(articleId:number) => {
  try {
    editArticleDialogVisible.value = true
    await getOneArticleApi(articleId).then((res) => {
      console.log(res)
      editArticleData.createdBy = res.data.createdBy
      if (res.data.updatedBy) {
        editArticleData.updatedBy = res.data.updatedBy
      } else editArticleData.updatedBy = "未曾更新"
      editArticleData.articleId = res.data.articleId
      editArticleData.articleTitle = res.data.articleTitle
      editArticleData.articleContent = res.data.articleContent
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
const editHandleClose = () => {
  ElMessageBox.confirm('是否取消编辑文章，数据将清空！')
      .then(() => {
        editArticleDialogVisible.value = false
        Object.keys(articleData).forEach(key => {
          articleData[key] = ''
        })
      })
      .catch(() => {
        // catch error
      })
}
const onSubmitEditArticle = async () => {
  try {
    await editArticleApi(editArticleData,localStorage.getItem("TokenInfo")).then((res) => {
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
  } catch (e) {
    ElMessage.error(e)
  }
}
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
const onChange = async(pageNum:number) => {
  try {
    const queryParams = {
      pageNum:pageNum,
      pageSize:queryForm.pageSize,
      data:queryForm.data
    }
    await getAllArticle(queryParams).then((res) => {
      tableData.value = res.data;
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
/**
 * 文章视图
 */
const article = ref([])
const articleView = async (articleId: number) => {
  try {
    await articleViewApi(articleId).then((res) => {
      drawer.value = true
      article.value = res.data
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
/**
 * 删除文章
 */
const ifDeleteArticle = async (articleId:number) => {
  ElMessageBox.confirm(
      '是否确认删除文章',
      '删除文章',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(()=> {
    deleteArticleApi(articleId).then((res) => {
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

<style lang="scss" scoped>

</style>