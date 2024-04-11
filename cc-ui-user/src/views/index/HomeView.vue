<script setup lang="ts">
import {reactive, ref} from "vue";
import { baseUrl } from "@/utils/request";
import {
  getAllArticle,
  getHotArticleApi,
  getTopicForArticleOptionsApi
} from "@/api/article";
import {getIndexImage} from "@/api/image";
import {useRouter} from "vue-router";
import {getUserInfoApi} from "@/api/login";
import {timeHandler} from "@/utils/timeHandler";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {getTheNumOfLoginsApi, getVisitsApi} from "@/api/nonPermissionData";

const queryForm = reactive({
  pageNum:1,
  pageSize:5,
  total:1,
  data:{
    articleTitle:'',
    topicId:null,
  }
})
/**
 * 清除搜索框
 * @param value
 */
const handleTopicChange = (value) => {
  if (value === '') {
    queryForm.data.topicId = null;
    onQuery()
  }
  else onQuery()
}
const router = useRouter(); // 解析router
const articleTitle = ref('')  //  文章标题查询条件
const tableData = ref([]) // 文章数据
const hotArticleData = ref([]) //热门公告数据
const theNumOfLogins = ref()
const todayVisits = ref()
const arr = ref([]) // 图片地址数组
const squareUrl= 'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png'  //  默认头像
const userInfo = ref([])
const topicForArticleOptions = ref([])//公告类型选择器
/**
 * 重定向文章标题到文章信息
 * @param articleTitle
 * @param articleId
 */
const redirectToArticle = (articleTitle,articleId) => {
  router.push({ name: 'ArticleView', params: { title:articleTitle,id: articleId } });
};
/**
 * 加载页面数据--走马灯图片--公告--最热公告--已登录数量--今日访问单人数量--用户信息
 */
const onLoad = async () => {
  try {
    await getIndexImage().then((res) => {
      const imageData = res.data
      for (let i = 0;i < imageData.length; i++) {
        imageData[i] = `${baseUrl}/`+imageData[i]
      }
      arr.value = imageData
    })
    await getAllArticle(queryForm).then((res) => {
      queryForm.total = res.data.total
      tableData.value = res.data.data
    })
    await getHotArticleApi().then((res) => {
      hotArticleData.value = res.data
      // console.log(res)
    })
    await getTheNumOfLoginsApi().then((res) => {
      theNumOfLogins.value = res.data
      // console.log(res)
    })
    await getVisitsApi().then((res) => {
      todayVisits.value = res.data
      // console.log(res)
    })
    await getTopicForArticleOptionsApi().then((res) => {
      topicForArticleOptions.value = res.data
    })
    if (localStorage.getItem("TokenInfo")) {
      await getUserInfoApi().then((res) => {
        userInfo.value = res.data
        console.log(res)
      })
    }
  } catch (e) {
    console.error(e)
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
    console.error(e)
  }
}
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
    console.error(e)
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
    console.error(e)
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
            <font-awesome-icon :icon="['fas', 'caret-up']" style="margin-right: 0.5rem;justify-content: center;color:blue"/>最新
            <el-input
                v-model="queryForm.data.articleTitle"
                placeholder="输入标题名"
                @keyup.enter="onQuery"
                clearable
                class="search-input"
                size="default"
                maxlength="50"
                style="width: 250px;margin-right: 10px"
            />
            <el-button type="primary" @click="onQuery" plain >查询</el-button>
          </div>
          <el-form-item label="主题" @keyup.enter="onQuery"  style="width: 200px;margin-left: 10px">
            <el-select
                v-model="queryForm.data.topicId"
                placeholder="NULL"
                clearable
                @change="handleTopicChange"
                v-if="topicForArticleOptions && topicForArticleOptions.length > 0"
            >
              <el-option
                  v-for="option in topicForArticleOptions"
                  :key="option.topicId"
                  :value="option.topicId"
                  :label="option['topicName']"
              />
            </el-select>
          </el-form-item>
        </div>

        <div v-for="(item, index) in tableData" :key="index" class="container">
          <el-link :underline="false" @click="redirectToArticle(item.articleTitle, item['articleId'])">
            {{ item["articleTitle"] }}
          </el-link>
          <div class="content-left">
            {{ truncateText(item["articleContent"], 100) }}
          </div>
          <div class="content-bottom">
            <el-avatar :size="25" :src="squareUrl" style="margin-right: 10px"/>
            {{ item["createdBy"]}}&nbsp;/&nbsp;{{ item["topicName"] }}&nbsp;/&nbsp;
            {{timeHandler(null,null,item["createdTime"])}}&nbsp;/&nbsp;{{ item["viewCount"] }}&nbsp;阅读量
          </div>
          <el-divider />
        </div>

        <div style="padding: 5px">
          <el-pagination
              v-model:current-page="queryForm.pageNum"
              v-model:page-size="queryForm.pageSize"
              :page-sizes="[5, 10, 15]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="queryForm.total"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
          >
          </el-pagination>
        </div>
      </div>

      <!--右侧容器-->
      <div id="right" class="side-right-content" style="margin-left: 10px">
        <div class="r-container" v-if="userInfo['userName']">
          <div>
            <el-avatar shape="square" :size="100" :src="userInfo['userHead']" />
          </div>
          <div>
            {{ userInfo["userName"] }}
          </div>
          <div>
            {{ userInfo["personality"] || "这个人很懒，什么都还没有写呢..." }}
          </div>
        </div>
        <div class="r-container" v-else>
          请登录后查看...
        </div>
        <div class="r-container">
          <div class="title">
            <font-awesome-icon :icon="['fas', 'fire']" style="margin-right: 0.5rem;justify-content: center;color:red"/>热门
          </div>
          <div class="content">
            <div class="top-article" v-for="article in hotArticleData" :key="article['articleId']"
                 @click="redirectToArticle(article['articleTitle'],article['articleId'])">
              <div class="font3">{{article['articleTitle']}}</div>
              <div >{{article['viewCount']}}次浏览</div>
            </div>
          </div>
        </div>

        <div class="r-container">
          <div class="title">
            论坛信息
          </div>
          <div style="display: flex;margin-top: 15px">
            <font-awesome-icon :icon="['fas', 'user-check']" style="margin-right: 0.5rem;justify-content: center;"/>
            <div style="color: red;margin-right: 5px;margin-left: 5px">{{theNumOfLogins}}</div>人已登录在线
          </div>
          <div style="display: flex">
            <font-awesome-icon :icon="['fas', 'wifi']" style="margin-right: 0.5rem;justify-content: center;"/>
            <div style="color: red;margin-right: 5px;margin-left: 5px">{{todayVisits}}</div>今日访问用户
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.carousel {
  margin: 0 auto;
  width: 550px;
  height: 250px;
  padding: 30px;
}
.content-container {
  display: flex;
  margin: 0 auto;
  flex-wrap: wrap; /* 添加 flex-wrap 以实现换行 */
  justify-content: center; /* 垂直居中 */
  width: 1000px;
}

.side-container {
  flex: 2.5;
  margin-top: 20px;
  //padding: 20px;
  box-sizing: border-box;
  border-radius: 20px;
  //background-color: rgba(255, 255, 255, 0.2);
  background-color: white;
}

.font3{
  background: linear-gradient(to right, red, blue);
  -webkit-background-clip: text;
  color: transparent;
}

.side-right-content {  //右侧样式
  flex: 1;
  margin-top: 20px;
  padding: 20px;
  box-sizing: border-box;
  border-radius: 20px;
  background-color: rgba(255, 255, 255, 0);
  .r-container {
    max-width: 800px;
    padding: 20px;
    background-color: white;
    border-radius: 20px;
    margin-bottom: 20px;
    .title {
      font-weight: bolder;
    }
    .content {
      .top-article {
        border-radius: 0.5rem;
        padding: 0.5rem;
        &:hover {
          background-color: #cfe9ff;
          cursor: pointer; // 让鼠标在悬停时显示为手型
        }
      }
    }
  }
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
  .el-carousel {
    width: 100%;
    max-width: 90%;
    margin: 0 auto;
  }
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
  margin-bottom: 10px;
}


</style>