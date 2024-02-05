<template>
  <div>
    <div class="content-container">
      <div id="left" class="side-container">
        <el-carousel indicator-position="outside" class="carousel">
          <el-carousel-item v-for="(item, index) in arr" :key="index">
            <img :src="item" alt="Image" style="width: 100%; height: 100%;" />
          </el-carousel-item>
        </el-carousel>

        <div style="display: flex" class="container-for-search">
          <div >
            <el-input
                v-model="queryForm.data.articleTitle"
                placeholder="输入标题名"
                @keyup.enter="onQuery"
                clearable
                class="search-input"
                size="large"
                maxlength="50"
                style="width: 300px;margin-right: 20px"
            />
            <el-button type="primary" @click="onQuery" plain >查询</el-button>
          </div>
        </div>

        <div v-for="(item, index) in tableData" :key="index" class="container">
          <el-link :underline="false" @click="redirectToArticle(item.articleTitle, item.articleId)">
            {{ item["articleTitle"] }}
          </el-link>
          <div class="content-left">
            {{ truncateText(item["articleContent"], 100) }}
          </div>
          <div class="content-bottom">
            <el-avatar :size="25" :src="getUserHeader() || squareUrl" style="margin-right: 10px"/>
            {{ item["createdBy"]}}&nbsp;/&nbsp;{{ item["topicName"] }}&nbsp;/&nbsp;
            {{timeHandler(null,null,item["createdTime"])}}&nbsp;/&nbsp;{{ item["viewCount"] }}&nbsp;阅读量
          </div>
        </div>

        <div class="demo-pagination-block">
          <el-pagination
              v-model:current-page="queryForm.pageNum"
              v-model:page-size="queryForm.pageSize"
              :page-sizes="[5, 10, 15]"
              :small="small"
              :disabled="disabled"
              :background="background"
              layout="total, sizes, prev, pager, next, jumper"
              :total="queryForm.total"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
          >
          </el-pagination>
        </div>

      </div>

      <div id="right" class="side-right-content" style="margin-left: 10px">
        <div class="container">
          <div class="content-right">
            <div class="avatar-container">
              <el-avatar shape="square" :size="150" :src="getUserHeader() || squareUrl" />
            </div>
            <div class="user-info">
              {{ userInfo["userName"] }}
            </div>
            <div class="personality">
              {{ userInfo["personality"] || "这个人很懒，什么都还没有写呢..." }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {reactive, ref} from "vue";
import { baseUrl } from "../../utils/request";
import {getAllArticle} from "../../api/article";
import {ElMessage} from "element-plus";
import {getIndexImage} from "../../api/image";
import {useRouter} from "vue-router";
import {getUserInfoApi} from "../../api/login";
import {timeHandler} from "../../utils/timeHandler";

const small = ref(false)
const background = ref(false)
const disabled = ref(false)

const queryForm = reactive({
  pageNum:1,
  pageSize:5,
  total:1,
  data:{
    articleTitle:''
  }
})

const handleSizeChange = async (val: number) => {
  try {
    const queryParams = {
      pageNum:queryForm.pageNum,
      pageSize:val,
      data:queryForm.data
    }
    await getAllArticle(queryParams).then((res) => {
      tableData.value = res.data.data;
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
const handleCurrentChange = async (val: number) => {
  try {
    const queryParams = {
      pageNum:val,
      pageSize:queryForm.pageSize,
      data:queryForm.data
    }
    await getAllArticle(queryParams).then((res) => {
      tableData.value = res.data.data;
    })
  } catch (e) {
    ElMessage.error(e)
  }
}


const router = useRouter(); // 解析router
const tableData = ref([]) // 文章数据
const articleTitle = ref('')  //  文章标题查询条件
const arr = ref([]) // 图片地址数组
const squareUrl= 'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png'  //  默认头像
const userInfo = ref([])
/**
 * 重定向文章标题到文章信息
 * @param articleTitle
 * @param articleId
 */
const redirectToArticle = (articleTitle,articleId) => {
  router.push({ name: 'ArticleView', params: { title:articleTitle,id: articleId } });
};
const getUserHeader =  () => {
  return localStorage.getItem("userHead")
}
/**
 * 加载页面数据
 */
const onLoad = async () => {
  try {
    //  走马灯图片
    await getIndexImage().then((res) => {
      const imageData = res.data
      for (let i = 0;i < imageData.length; i++) {
        imageData[i] = `${baseUrl}/`+imageData[i]
      }
      arr.value = imageData

    })
    //  文章
    await getAllArticle(queryForm).then((res) => {
      queryForm.total = res.data.total
      tableData.value = res.data.data

    })
    //  用户信息
    if (localStorage.getItem("TokenInfo")) {
      await getUserInfoApi().then((res) => {
        userInfo.value = res.data

      })
    }
  } catch (e) {
    ElMessage.error(e)
  }
}
onLoad()
const onQuery = async () => {
  try {
    await getAllArticle(queryForm).then((res) => {
      queryForm.total = res.data.total
      tableData.value = res.data.data
    })
  } catch (e) {
    ElMessage.error(e)
  }
}
/**
 * 字段长度限制
 * @param text
 * @param maxLength
 */
const truncateText = (text, maxLength) => {
  if (text.length > maxLength) {
    return text.slice(0, maxLength) + '...'; // 如果文本长度超过最大长度，截断文本并添加省略号
  }
  return text; // 否则，返回原始文本
}
</script>

<style lang="scss" scoped>
.el-carousel {
  width: 100%;
  max-width: 800px;
  margin: 0 auto;
}

.content-container {
  display: flex;
  margin: 0 20px;
  flex-wrap: wrap; /* 添加 flex-wrap 以实现换行 */
}

.side-container {
  flex: 2;
  max-width: calc(70% - 10px); /* 让左侧容器宽度为右侧容器的两倍，减去 margin-left 的负边距 */
  margin-top: 20px;
  padding: 20px;
  box-sizing: border-box;
  border-radius: 20px;
  background-color: rgba(255, 255, 255, 0.2);

  .search-container {
    display: flex; /* 使用 Flex 布局 */
    align-items: center; /* 垂直居中对齐 */
    margin-bottom: 20px; /* 为整体设置下边距 */
  }

  .container {
    margin-top: 20px; /* 设置文章列表的上边距 */
  }
}

.side-right-content {  //右侧样式
  flex: 1;
  max-width: calc(30% - 10px); /* 让右侧容器宽度为左侧容器的一半，减去 margin-left 的负边距 */
  margin-top: 20px;
  padding: 20px;
  box-sizing: border-box;
  border-radius: 20px;
  background-color: rgba(255, 255, 255, 0.2);
}

.container-for-search {   //搜索框样式
  width: 100%;
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  box-sizing: border-box;
  background-color: rgba(255, 255, 255, 0);
  border-radius: 20px;
}

.container {    //文章列表样式
  width: 100%;
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  box-sizing: border-box;
  background-color: rgba(255, 255, 255, 0.8);
  border-radius: 20px;

  .el-link {
    min-width: 100px;
    height: 20px;
    border-radius: 10px;
    font-family: STXingkai, serif;
    font-size: 25px;
  }

  .content-right {
    width: 100%;
    height: 100%;
    padding: 20px;
    border-radius: 10px;
    font-size: 20px;
    font-weight: lighter;
  }
  .content-bottom {
    margin-top: 15px;
    font-size: 13px;
    display: flex;
    align-items: center; /* 垂直居中 */

  }

  .content-left {
    margin-top: 10px;
    font-size: 15px;
  }
}

@media screen and (max-width: 768px) {
  .side-container,
  .side-right-content,
  .container {
    width: 100%;
    max-width: 100%; /* 在小屏幕下撑满整个宽度 */
    margin-right: 0;
    margin-left: 0;
  }
}

.demo-pagination-block {
  display: flex;
  align-items: center; /* 垂直方向上居中对齐 */
  justify-content: center; /* 水平方向上居中对齐 */
  flex-direction: column; /* 确保分页组件位于内容上方 */
}

.el-pagination {
  margin-top: 10px; /* 如果需要添加分页与上方内容的间距，可以设置这个值 */
}

</style>