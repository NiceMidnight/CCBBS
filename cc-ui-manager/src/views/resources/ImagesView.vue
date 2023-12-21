<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header" style="margin-top: 10px">
        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
          <el-form-item label="管理员名称">
            <el-input v-model="queryForm.data.userName" placeholder="请输入上传管理员名称" clearable @keyup.enter="onQuery"/>
          </el-form-item>
          <el-form-item label="图片类别" @keyup.enter="onQuery">
            <el-select
                v-model="queryForm.data.type"
                placeholder="NULL"
                clearable
            >
              <el-option
                v-for="option in options"
                :key="option['dictId']"
                :value="option['dictTypeId']"
                :label="option['dictItemName']"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="onQuery">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="addSysImages">添加</el-button>
          </el-form-item>
        </el-form>
      </div>
    </template>
    <el-table :data="tableData['data']" border style="width: 100%">
      <!-- 自动递增的行ID列 -->
      <el-table-column type="index" label="行id"  width="130" align="center"/>
      <el-table-column prop="id" label="图片Id" width="150" align="center"/>
      <el-table-column label="图片"  width="300" align="center" v-slot="{ row }">
        <el-image
            style="width: 70%; height: 70%"
            :zoom-rate="1.2"
            :src="getImage(row['imgPath']) || circleUrl"
            :initial-index="4"
            fit="cover"
        />
      </el-table-column>
      <el-table-column prop="userName" label="上传管理员"  width="150" align="center"/>
      <el-table-column prop="uploadTime" label="上传时间" align="center" width="200" :formatter="timeHandler"/>
      <el-table-column prop="typeName" label="类型" width="150" align="center"/>
      <el-table-column label="可见性" align="center"  width="200" v-slot="{ row }">
        <el-switch
            v-model="row['visible']"
            class="mb-2"
            active-value="VISIBLE"
            inactive-value="INVISIBLE"
            active-text="可见"
            inactive-text="不可见"
            @change="handleChange($event,row.id)"
        />
      </el-table-column>
      <el-table-column  label="操作" width="200" align="center">
        <el-button type="primary" plain>编辑</el-button>
        <el-button type="danger" plain>删除</el-button>
      </el-table-column>
    </el-table>
    <el-pagination
        v-model:current-page="queryForm.pageNum"
        v-model:page-size="queryForm.pageSize"
        background layout="prev, pager, next" :total="tableData['total']"

        @current-change="(pageNum) => { onChange(pageNum)}"
    />
  </el-card>
<!--添加系统管理图片-->
  <el-dialog
      v-model="dialogVisible"
      title="添加系统管理图片"
      width="30%"
      draggable
  >
    <div style="margin: 10px" />
    <el-form
        :label-position="'right'"
        label-width="100px"
        :model="formData"
        style="max-width: 460px"
    >
      <el-form-item label="图片">
        <el-upload
            ref="upload"
            class="upload-demo"
            :action="baseUrl+'/manager/images/uploadSysImg'"
            :limit="1"
            :on-exceed="handleExceed"
            :auto-upload="false"
            @change="handleFileChange"
            :on-success="handleUploadSuccess"
            :on-error="handleUploadError"
        >
<!--          :before-upload="submitSysImg"-->
          <template #trigger>
            <el-button type="primary" >选择文件</el-button>
          </template>
          <template #tip>
            <div class="el-upload__tip text-red">
              限制一个文件，新文件将覆盖旧文件
            </div>
          </template>
        </el-upload>
      </el-form-item>
      <el-form-item label="管理员">
        <el-text class="mx-1" type="success">{{ formData.userName }}</el-text>
      </el-form-item>
      <el-form-item label="图片名称">
        <el-input v-model="formData.imgName" />
      </el-form-item>
      <el-form-item label="图片类别">
        <el-select v-model="formData.type" clearable placeholder="NULL">
          <el-option
              v-for="option in options"
              :key="option['dictId']"
              :value="option['dictTypeId']"
              :label="option['dictItemName']"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="onSubmitAddSysImages">提交</el-button>
      </span>
    </template>
  </el-dialog>

</template>

<script setup lang="ts">
import { reactive, ref} from "vue";
import {inVisible, getAllImages, visible, getSysImgOptionApi, submitSysImgApi} from "../../api/images";
import {ElMessage, ElMessageBox, genFileId, UploadInstance, UploadProps, UploadRawFile} from "element-plus";
import {timeHandler} from "../../utils/timeHandler";
import {baseUrl} from "../../utils/request";
import {getUserName} from "@/api/users";

//  表格可见/不可见
const dialogVisible = ref(false)
//  添加图片数据
const formData = reactive({
  userName:'',
  type: '',
  imgName:'',
  imgPath:''
})
//  上传
const upload = ref<UploadInstance | null>(null)
/**
 * 添加图片按钮
 */
const addSysImages = async () => {
  dialogVisible.value = true
  await getUserName(localStorage.getItem("TokenInfo")).then((res) => {
    console.log(res)
    formData.userName = res.data
  })
}
/**
 * 更换图片覆盖
 * @param files
 */
const handleExceed: UploadProps['onExceed'] = (files) => {
  // 当文件数量超过限制时，执行以下逻辑
  // 清空已选择的文件列表
  upload.value!.clearFiles();
  // 获取超出限制的第一个文件
  const file = files[0] as UploadRawFile;
  // 生成一个新的文件 UID，以确保唯一性
  file.uid = genFileId();
  // 手动触发文件上传
  upload.value!.handleStart(file);
}
/**
 * 文件名获取
 * @param file
 */
const handleFileChange = (file) => {
  // file 是上传的文件对象
  // 可以通过 file.name 获取文件名
  formData.imgPath = file.name
  formData.imgName = file.name
};

/**
 * 绑定图片（未用）
 */
// const submitSysImg = async (file) => {
//   try {
//     const imgData = new FormData()
//     imgData.append('file',file)
//     await submitSysImgApi(imgData).then((res) => {
//       console.log(res)
//     })
//   } catch (e) {
//     console.log(e)
//   }
// }
/**
 * 图片及信息提交
 */
const onSubmitAddSysImages = async () => {
  console.log(formData)
  await upload.value!.submit()
  await submitSysImgApi(formData).then((res) => {
    if (res["code"] === '200') {
      ElMessage.success(res["msg"])
      dialogVisible.value = false
      onLoad()
    } else ElMessage.error(res["msg"])
  })
}
/**
 * 图片提交成功反馈
 * @param success
 */
const handleUploadSuccess = (success) => {
  if (success.code === '200') {
    ElMessage.success(success["msg"])
  } else ElMessage.error(success["msg"])
}
/**
 * 图片提交失败反馈
 * @param err
 */
const handleUploadError = (err) => {
  // 处理上传失败时的后端错误
  console.error('错误:', err);
  // 根据错误信息执行其他操作
  ElMessage.error("图片上传失败，错误信息："+err)
};
/**
 * 取消添加图片
 */
const handleClose = () => {
  ElMessageBox.confirm('是否取消添加图片，数据将清空！')
      .then(() => {
        dialogVisible.value = false
        Object.keys(formData).forEach(key => {
          formData[key] = ''
        })
      })
      .catch(() => {
        // catch error
      })
}

/**
 * 表格数据---查询条件
 */
const tableData = ref([])
const queryForm = reactive({
  pageNum:1,
  pageSize:10,
  total:1,
  data:{
    type:'',
    userName:''
  }
})

/**
 * 图片类别选择器
 */
const options = ref([])
/**
 * 加载数据
 */
const onLoad = async() => {
  try {
    await getSysImgOptionApi().then((res) => {
      options.value = res.data
      console.log(options.value)
    })
    await getAllImages(queryForm).then((res) => {
      tableData.value = res.data
      console.log(res.data)
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
/**
 * 获取图片
 * @param imgPath
 */
const getImage = (imgPath) => {
  return `${baseUrl}/${imgPath}`
}
const circleUrl = 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png' // 不能正常显示图片时显示该图片
onLoad()
/**
 * 设置图片状态可见/不可见
 * @param act
 * @param imageId
 */
const handleChange = async (act: "VISIBLE" | "INVISIBLE", imageId: number) => {
  let actions = {
    VISIBLE: {fn: visible},
    INVISIBLE: {fn: inVisible}
  }
  const data = await actions[act].fn(imageId)
  console.log(data)
  if (data["code"] === '200') {
    ElMessage.success("图片"+imageId+data["msg"])
  } else {
    ElMessage.error("图片"+imageId+data["msg"])
    throw new Error("图片"+imageId+data["msg"])
  }
}
/**
 * 查询数据
 */
const onQuery = async() => {
  try {
    await getAllImages(queryForm).then((res) => {
      tableData.value = res.data
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
//  添加
const addSysImage = async () => {

}
/**
 * 改变页码查询数据
 * @param pageNum
 */
const onChange = async(pageNum:number) => {
  try {
    const queryParams = {
      pageNum:pageNum,
      pageSize:queryForm.pageSize,
      data:queryForm.data
    }
    await getAllImages(queryParams).then((res) => {
      tableData.value = res.data;
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
</script>

<style lang="scss" scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.box-card {
  width: auto;
}
.demo-form-inline .el-input {
  --el-input-width: 220px;
}
.dialog-footer button:first-child {
  margin-right: 10px;
}

</style>