<script setup lang="ts">
import {
  complianceApplyApi,
  getApplyRecruiterMessageApi,
  getApplyStatusOptionsApi, getOneApplyMessageApi,
  irregularityApplyApi, updateApplyNotesApi
} from "@/api/applyRecruit";
import {ref,reactive,watch} from "vue";
import {ElMessage, ElMessageBox, ElTable} from "element-plus";
import {timeHandler} from "@/utils/timeHandler";
import {truncateText} from "@/utils/textHandler";
/**
 * 表单数据---查询数据
 */
const tableData = ref([]) //数据
const applyStatusOptions = ref([]) //帖子状态选择器
const queryForm = reactive({
  pageNum:1,
  pageSize:10,
  total:1,
  data:{
    userName:'',
    nickName:'',
    studentId:'',
    truthName:'',
    applyStatus:null
  }
})
const onQuery = async () => {
  try {
    await getApplyRecruiterMessageApi(queryForm,startTime.value,endTime.value).then((res) => {
      tableData.value = res.data
    })
  } catch (e) {
    ElMessage.error("查询错误："+e)
  }
}
const onLoad = async () => {
  await getApplyRecruiterMessageApi(queryForm,null,null).then((res) => {
    tableData.value = res.data
    console.log(res)
  })
  await getApplyStatusOptionsApi().then((res) => {
    applyStatusOptions.value = res.data.map(option => {
      return {
        label: translateStatus(option),
        value: option
      }
    })
  })
}
onLoad()
const handleStatusChange = (value) => {
  // 如果选项的值为空字符串，则将其设置为 null
  if (value === '') {
    queryForm.data.applyStatus = null;
  }
}
/**
 * 转译
 * @param status
 */
const translateStatus = (status) => {
  switch (status) {
    case 'COMPLIANCE':
      return '合规';
    case 'IRREGULARITY':
      return '不合规';
    default:
      return status;
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
const handleSizeChange = async (size:number) => {
  try {
    const queryParams = {
      pageNum:queryForm.pageNum,
      pageSize:size,
      total:queryForm.total,
      data:queryForm.data
    }
    await getApplyRecruiterMessageApi(queryParams,startTime.value,endTime.value).then((res) => {
      tableData.value = res.data
    })
  } catch (e) {
    ElMessage.error("改变每页数量错误："+e)
  }
}
const handleCurrentChange = async(num:number) => {
  try {
    const queryParams = {
      pageNum:num,
      pageSize:queryForm.pageSize,
      total:queryForm.total,
      data:queryForm.data
    }
    await getApplyRecruiterMessageApi(queryParams,startTime.value,endTime.value).then((res) => {
      tableData.value = res.data
    })
  } catch (e) {
    ElMessage.error("改变页码错误："+e)
  }
}
/**
 * 启用禁用
 */
const handleChange = async (act:"COMPLIANCE" | "IRREGULARITY",postId: number,userId:number) => {
  let actions = {
    COMPLIANCE: {msg: '合规',fn: complianceApplyApi},
    IRREGULARITY: {msg: '违规',fn: irregularityApplyApi}
  }
  const data = await actions[act].fn(postId,userId)
  console.log(data)
  if (data["code"] === '200') {
    ElMessage.success( `${actions[act].msg}申请`+postId+'操作成功')
  } else {
    ElMessage.error(`${actions[act].msg}申请`+postId+'操作失败')
    throw new Error(`${actions[act].msg}申请`+postId+'操作失败')
  }
}
/**
 * 文本截断
 * @param row
 */
const truncateTextFormatter = (notes: string) => {
  return truncateText(notes, 10);
};
/**
 * 编辑备注
 */
const editApplyNotesDialogVisible = ref(false)
const editApplyNotesData = reactive({
  applyId:null,
  userName:'',
  nickName:'',
  truthName:'',
  studentId:'',
  phone:'',
  createdAt:'',
  notes:'',
})
const editApplyNotes = async (row:any) => {
  editApplyNotesDialogVisible.value = true
  editApplyNotesData.applyId = row['applyId']
  editApplyNotesData.userName = row['userName']
  editApplyNotesData.nickName = row['nickName']
  editApplyNotesData.truthName = row['truthName']
  editApplyNotesData.studentId = row['studentId']
  editApplyNotesData.phone = row['phone']
  editApplyNotesData.notes = row['notes']
  editApplyNotesData.createdAt = row['createdAt']
}
const editHandleClose = () => {
  ElMessageBox.confirm('是否取消编辑文章，数据将清空！')
      .then(() => {
        editApplyNotesDialogVisible.value = false
        Object.keys(editApplyNotesData).forEach(key => {
          editApplyNotesData[key] = ''
        })
      })
      .catch(() => {
        // catch error
      })
}
const onSubmitEditApplyNotes = async () => {
  try {
    await updateApplyNotesApi(editApplyNotesData.applyId,editApplyNotesData.notes).then((res) => {
      editApplyNotesDialogVisible.value = false
      ElMessage.success(res["msg"])
      onLoad()
    })
  }
  catch (e) {
    console.log(e)
  }
}
/**
 * 多选
 */
const multipleTableRef = ref<InstanceType<typeof ElTable>>()
const multipleSelection = ref([])
const handleSelectionChange = (val: any) => {
  multipleSelection.value = val
  console.log(multipleSelection)
}
</script>

<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header" style="margin-top: 10px;width: 1800px">
        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
          <el-form-item label="用户名">
            <el-input v-model="queryForm.data.userName" placeholder="请输入上传用户名称" clearable @keyup.enter="onQuery"/>
          </el-form-item>
          <el-form-item label="昵称">
            <el-input v-model="queryForm.data.nickName" placeholder="请输入上传用户昵称" clearable @keyup.enter="onQuery"/>
          </el-form-item>
          <el-form-item label="学号">
            <el-input v-model="queryForm.data.studentId" placeholder="请输入学号" clearable @keyup.enter="onQuery"/>
          </el-form-item>
          <el-form-item label="真实姓名">
            <el-input v-model="queryForm.data.truthName" placeholder="请输入真实姓名" clearable @keyup.enter="onQuery"/>
          </el-form-item>
          <div style="display: flex">
            <el-form-item label="申请状态" @keyup.enter="onQuery"  style="width: 250px">
              <el-select
                  v-model="queryForm.data.applyStatus"
                  placeholder="NULL"
                  clearable
                  @change="handleStatusChange"
              >
                <el-option
                    v-for="option in applyStatusOptions"
                    :key="option.value"
                    :value="option.value"
                    :label="option.label"
                />
              </el-select>
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
          </div>
        </el-form>
      </div>
    </template>
    <el-table ref="multipleTableRef" :data="tableData['data']" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <!-- 自动递增的行ID列 -->
      <el-table-column type="index" label="行id" width="60" align="center" fixed/>
      <el-table-column prop="applyId" label="申请id" width="70" align="center" fixed/>
      <el-table-column prop="userId" label="用户id" width="70" align="center"/>
      <el-table-column prop="userName" label="用户名" width="100" align="center"/>
      <el-table-column prop="nickName" label="昵称" width="100" align="center"/>
      <el-table-column prop="studentId" label="学号" width="150" align="center"/>
      <el-table-column prop="truthName" label="真实姓名" width="150" align="center"/>
      <el-table-column prop="phone" label="电话" width="150" align="center"/>
      <el-table-column prop="createdAt" label="提交时间"  width="170" align="center" :formatter="timeHandler"/>
      <el-table-column prop="handlingUserName" label="处理员" width="100" align="center"/>
<!--      <el-table-column prop="handlingNickName" label="管理昵称" width="100" align="center"/>-->
      <el-table-column prop="handlingAt" label="处理时间"  width="170" align="center" :formatter="timeHandler"/>
      <el-table-column label="备注"  width="170" align="center" v-slot="{ row }">
        <el-tooltip
            class="box-item"
            effect="dark"
            :content="row['notes']"
            placement="bottom"
        >
          {{truncateTextFormatter(row['notes'])}}
        </el-tooltip>
      </el-table-column>
      <el-table-column label="可见性" align="center"  width="200" v-slot="{ row }">
        <el-switch
            v-model="row['applyStatus']"
            class="mb-2"
            active-value="COMPLIANCE"
            inactive-value="IRREGULARITY"
            active-text="合规"
            inactive-text="违规"
            @change="handleChange($event,row['applyId'],row['userId'])"
        />
      </el-table-column>
      <el-table-column fixed="right" label="操作选项" align="center" width="150" >
        <template #default="{ row }">
          <el-button type="primary" @click="editApplyNotes(row)" plain>编辑备注</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        v-model:current-page="queryForm.pageNum"
        v-model:page-size="queryForm.pageSize"
        :page-sizes="[ 5, 10, 20, 30]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="tableData['total']"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        style="margin-top: 20px"
    />
  </el-card>
  <!--编辑文章-->
  <el-dialog
      v-model="editApplyNotesDialogVisible"
      title="编辑备注"
      width="30%"
      draggable
  >
    <div style="margin: 10px" />
    <el-form
        :label-position="'right'"
        label-width="70px"
        :model="editApplyNotesData"
        style="max-width: 500px"
    >
      <el-form-item label="用户名">
        <el-text class="mx-1" type="success">{{editApplyNotesData.userName}}</el-text>
      </el-form-item>
      <el-form-item label="昵称">
        <el-text class="mx-1" type="success">{{editApplyNotesData.nickName}}</el-text>
      </el-form-item>
      <el-form-item label="真名">
        <el-text class="mx-1" type="success">{{editApplyNotesData.truthName}}</el-text>
      </el-form-item>
      <el-form-item label="学号">
        <el-text class="mx-1" type="success">{{editApplyNotesData.studentId}}</el-text>
      </el-form-item>
      <el-form-item label="学号">
        <el-text class="mx-1" type="success">{{editApplyNotesData.phone}}</el-text>
      </el-form-item>
      <el-form-item label="提交时间">
        <el-text class="mx-1" type="success">{{timeHandler(null,null,editApplyNotesData.createdAt)}}</el-text>
      </el-form-item>
      <el-form-item label="备注">
        <el-input
            v-model="editApplyNotesData.notes"
            maxlength="30"
            style="width: 400px"
            placeholder="请输入备注"
            show-word-limit
            type="textarea"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="editHandleClose">取消</el-button>
        <el-button type="primary" @click="onSubmitEditApplyNotes">提交</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style lang="scss" scoped>

</style>