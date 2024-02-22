<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header" style="margin-top: 10px">
        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
          <el-form-item label="用户名">
            <el-input v-model="queryForm.data.userName" placeholder="请输入用户名" clearable @keyup.enter="onQuery"/>
          </el-form-item>
          <el-form-item label="性别" @keyup.enter="onQuery"  style="width: 150px">
            <el-select
                v-model="queryForm.data.userSex"
                placeholder="NULL"
                clearable
            >
              <el-option label="男" value="1" />
              <el-option label="女" value="0" />
            </el-select>
          </el-form-item>
<!--          <el-form-item label="注册时间">-->
<!--            <el-date-picker-->
<!--                v-model="timeRange"-->
<!--                type="datetimerange"-->
<!--                start-placeholder="Start Date"-->
<!--                end-placeholder="End Date"-->
<!--            />-->
<!--          </el-form-item>-->
          <el-form-item>
            <el-button type="primary" @click="onQuery">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
    </template>
    <el-table :data="tableData['data']" border style="width: 100%">
      <!-- 自动递增的行ID列 -->
      <el-table-column type="index" label="行id"  width="70" align="center"/>
      <el-table-column prop="id" label="用户Id" width="70" align="center"/>
      <el-table-column label="头像"  width="130" align="center" v-slot="{ row }">
        <el-avatar :size="50" :src="getImage(row['userHead']) || circleUrl" />
      </el-table-column>
      <el-table-column prop="userName" label="用户名"  width="100" align="center"/>
      <el-table-column prop="nickName" label="昵称"  width="100" align="center"/>
      <el-table-column prop="userSex" label="性别" width="100" align="center" :formatter="formatUserSex"/>
      <el-table-column prop="userAge" label="年龄" width="100" align="center"/>
      <el-table-column prop="userPhone" label="手机号" width="150" align="center"/>
      <el-table-column prop="userAddress" label="住址" align="center"/>
      <el-table-column prop="fans" label="粉丝" width="70" align="center"/>
      <el-table-column prop="userDate" label="注册时间" align="center" width="180" :formatter="timeHandler"/>
      <el-table-column label="状态" align="center"  width="200" v-slot="{ row }">
        <el-switch
            v-model="row['userStatus']"
            class="mb-2"
            active-value="ENABLE"
            inactive-value="DISABLE"
            active-text="启用"
            inactive-text="禁用"
            @change="handleChange($event,row.id)"
        />
      </el-table-column>
      <el-table-column prop="userDeleted" label="是否注销" width="120" align="center" :formatter="formatUserDeleted"/>
    </el-table>
    <el-pagination
        v-model:current-page="queryForm.pageNum"
        v-model:page-size="queryForm.pageSize"
        :page-sizes="[5,10]"
        :small="small"
        :disabled="disabled"
        :background="background"
        layout="total, sizes, prev, pager, next, jumper"
        :total="tableData['total']"
        @current-change="(pageNum) => { onChange(pageNum)}"
        style="margin-top: 20px"
    />
  </el-card>
</template>

<script setup lang="ts">

import {enableUser, forbidUser, getAllUsers} from "../../api/users";
import {ElMessage} from "element-plus";
import {reactive, ref} from "vue";
import {timeHandler} from "../../utils/timeHandler";
import {baseUrl} from "../../utils/request";
const small = ref(false)
const background = ref(false)
const disabled = ref(false)

/**
 * 表格数据---查询条件
 */
const tableData = ref([])
const queryForm = reactive({
  pageNum:1,
  pageSize:10,
  total:1,
  data: {
    userName:'',
    userSex:''
  }
})
/**
 * 请求数据 加载到表格内
 */
const onLoad = async() => {
  try {
    await getAllUsers(queryForm).then((res) => {
      tableData.value = res.data;
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
onLoad()
/**
 * 查询数据 加载到表格
 */
const onQuery = async() => {
  try {
    await getAllUsers(queryForm).then((res) => {
      tableData.value = res.data;
    })
  } catch (e) {
    ElMessage.error(e)
  }
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
    await getAllUsers(queryParams).then((res) => {
      tableData.value = res.data;
    })
  } catch (e) {
    ElMessage.error(e)
  }
}

//  时间选取器
const timeRange = ref(null)
//  时间查询监听
// watch(timeRange,(newTime) => {
//   if (Array.isArray(newTime)) {
//     queryCondition.value.startCreateTime = newTime[0].toISOString
//     queryCondition.value.endCreateTime = newTime[1].toISOString
//   } else {
//     queryCondition.value.startCreateTime = ''
//     queryCondition.value.endCreateTime = ''
//   }
// })

// 男/女
const formatUserSex = (row: any) => {
  return row.userSex === 1 ? '男' : row.userSex === 0 ? '女' : '';
};
//  是否注销
const formatUserDeleted = (row: any) => {
  return row.userDeleted === 1 ? '否' : row.userDeleted === 0 ? '是' : '';
};
// 默认用户头像
const circleUrl = 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
//  获取图片
const getImage = (userHead) => {
  return `${baseUrl}/${userHead}`
}
/**
 * 切换用户状态时间处理函数
 * @param act
 * @param userId
 */
const handleChange = async (act: "ENABLE" | "DISABLE",userId: number) => {
  let actions = {
    ENABLE: {msg: '启用', fn: enableUser},
    DISABLE:{msg: '禁用', fn: forbidUser}
  }
  const data = await actions[act].fn(userId)
  console.log(data)
  if (data["code"] === '200') {
    ElMessage.success( `${actions[act].msg}用户`+userId+'成功')
  } else {
    ElMessage.error(`${actions[act].msg}用户`+userId+'失败')
    throw new Error(`${actions[act].msg}用户`+userId+'失败')
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
</style>