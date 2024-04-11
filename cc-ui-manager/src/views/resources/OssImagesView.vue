<script setup lang="ts">
import { reactive, ref, watch} from "vue";
import {ElMessage} from "element-plus";
import {timeHandler} from "@/utils/timeHandler";
import { deleteOssImageByIdApi, getOssImagesApi} from "@/api/ossImages";


/**
 * 放大图片
 */
const imageDialogVisible = ref(false);
const enlargedImagePath = ref('');
const enlargedImgName = ref('')
const showImageDialog = (imgPath,imgName) => {
  enlargedImagePath.value = imgPath
  enlargedImgName.value = imgName
  imageDialogVisible.value = true;
};
const closeImageDialog = () => {
  imageDialogVisible.value = false;
};

const deleteOssImageById = async (imageId) => {
  try {
    await deleteOssImageByIdApi(imageId).then((res) => {
      if (res["code"] === "200") {
        ElMessage.success(res["msg"])
        onLoad()
      } else ElMessage.error(res["msg"])
    })
  } catch (e) {
    console.log(e)
  }
}

/**
 * 表格数据---查询条件---加载数据
 */
const tableData = ref([])
const queryForm = reactive({
  pageNum:1,
  pageSize:5,
  total:1,
  data:{
    imgName:'',
  }
})
const onLoad = async() => {
  try {
    await getOssImagesApi(queryForm,null,null).then((res) => {
      tableData.value = res.data
      console.log(res)
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
onLoad()

/**
 * 查询数据
 */
const onQuery = async() => {
  try {
    await getOssImagesApi(queryForm,startTime.value,endTime.value).then((res) => {
      tableData.value = res.data
    })
  } catch (e) {
    ElMessage.error(e)
  }
}

/**
 * 改变页码查询数据
 * @param size
 */
const handleSizeChange = async(size:number) => {
  try {
    const queryParams = {
      pageNum:queryForm.pageNum,
      pageSize:size,
      data:queryForm.data
    }
    await getOssImagesApi(queryParams,startTime.value,endTime.value).then((res) => {
      tableData.value = res.data;
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
const onChange = async(num:number) => {
  try {
    const queryParams = {
      pageNum:num,
      pageSize:queryForm.pageSize,
      data:queryForm.data
    }
    await getOssImagesApi(queryParams,startTime.value,endTime.value).then((res) => {
      tableData.value = res.data;
    })
  } catch (e) {
    ElMessage.error(e)
  }
}

/**
 * 时间范围
 */
const timeRange = ref("") //时间
const startTime =  ref() ;
const endTime =  ref();
watch(timeRange,(newTime) => {
  if (Array.isArray(newTime))
  {
    const start = new Date(newTime[0]);
    start.setHours(start.getHours() + 8);
    startTime.value = start.toISOString();
    const end = new Date(newTime[1]);
    end.setHours(end.getHours() + 8);
    endTime.value = end.toISOString();
  }
  else {
    startTime.value = ""
    endTime.value = ""
  }
})
/**
 * 快速时间选择器
 * @type {[{text: string, value: (function(): [Date,Date])},{text: string, value: (function(): [Date,Date])},{text: string, value: (function(): [Date,Date])}]}
 */
const shortcuts = [
  {
    text: '上周',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
      return [start, end]
    },
  },
  {
    text: '上个月',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
      return [start, end]
    },
  },
  {
    text: '过去三个月',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
      return [start, end]
    },
  },
]
</script>

<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header" style="margin-top: 10px">
        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
          <el-form-item label="管理员名称">
            <el-input v-model="queryForm.data.imgName" placeholder="请输入图片名称" clearable @keyup.enter="onQuery"/>
          </el-form-item>
          <el-form-item>
            <div >
              <span >时间</span>
              <el-date-picker
                  v-model="timeRange"
                  type="datetimerange"
                  :shortcuts="shortcuts"
                  range-separator="至"
                  start-placeholder="起始时间"
                  end-placeholder="结束时间"
                  style="margin-left: 1rem"
              />
            </div>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="onQuery">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
    </template>
    <el-table :data="tableData['data']" border style="width: 100%">
      <el-table-column type="selection" width="55" />
      <!-- 自动递增的行ID列 -->
      <el-table-column type="index" label="行id"  width="60" align="center"/>
      <el-table-column prop="imgId" label="图片Id" width="90" align="center"/>
      <el-table-column label="图片"  width="100" align="center" v-slot="{ row }">
        <el-image
            style="width: 70%; height: 70%"
            :src="row['imgUrl']"
            fit="cover"
            @click="showImageDialog(row['imgUrl'],row.imgName)"
        />
      </el-table-column>
      <el-table-column prop="imgName" label="图片名"  width="230" align="center"/>
      <el-table-column prop="uploadTime" label="上传时间" align="center" width="200" :formatter="timeHandler"/>
      <el-table-column  label="操作" width="240" align="center" v-slot="scope">
        <el-button type="danger" plain @click="deleteOssImageById(scope.row['imgId'])">删除</el-button>
      </el-table-column>
    </el-table>
    <el-pagination
        v-model:current-page="queryForm.pageNum"
        v-model:page-size="queryForm.pageSize"
        :page-sizes="[4, 10, 15, 20]"
        background layout="total, sizes, prev, pager, next, jumper"
        :total="tableData['total']"
        @size-change="handleSizeChange"
        @current-change="(pageNum) => { onChange(pageNum)}"
        style="margin-top: 20px"
    />
  </el-card>
  <!--  图片放大-->
  <el-dialog
      v-model="imageDialogVisible"
      :title="`查看大图-${enlargedImgName}`"
      :visible.sync="imageDialogVisible"
      width="60%"
      :before-close="closeImageDialog"
  >
    <el-image
        :src="enlargedImagePath"
        style="width: 100%; height: 100%"
        fit="contain"
    />
  </el-dialog>
</template>

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