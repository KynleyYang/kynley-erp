<template>
  <div class="mains-container">
    <!-- 标题和搜索区域 -->
    <div class="header-section">
      <h2 class="page-title">商品库存预警</h2>
      <div class="search-area">
        <!-- 搜索输入框 -->
        <el-input
            v-model="query.goodsName"
            placeholder="搜索商品名称"
            clearable
            @clear="resetQuery"
            @keyup.enter="handleQuery"
            style="width: 300px"
        >
          <!-- 搜索图标 -->
          <template #prefix>
            <el-icon>
              <Search/>
            </el-icon>
          </template>
        </el-input>
        <!-- 搜索按钮 -->
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
      </div>
    </div>

    <!-- 统计卡片区域 -->
    <div class="stat-cards">
      <!-- 总商品数卡片 -->
      <el-card shadow="hover" class="stat-card">
        <div class="card-content">
          <div class="card-icon bg-blue">
            <el-icon>
              <Box/>
            </el-icon>
          </div>
          <div class="card-info">
            <div class="card-title">总商品数</div>
            <div class="card-value">{{ goodsToCount.goodsCount }}</div>
          </div>
        </div>
      </el-card>

      <!-- 库存正常数卡片 -->
      <el-card shadow="hover" class="stat-card">
        <div class="card-content">
          <div class="card-icon bg-green">
            <el-icon>
              <Box/>
            </el-icon>
          </div>
          <div class="card-info">
            <div class="card-title">库存正常</div>
            <div class="card-value">{{ goodsToCount.normalCount }}</div>
          </div>
        </div>
      </el-card>

      <!-- 总商品数卡片 -->
      <el-card shadow="hover" class="stat-card">
        <div class="card-content">
          <div class="card-icon bg-red">
            <el-icon>
              <Box/>
            </el-icon>
          </div>
          <div class="card-info">
            <div class="card-title">库存预警</div>
            <div class="card-value">{{ goodsToCount.warningCount }}</div>
          </div>
        </div>
      </el-card>
    </div>

    <!-- 商品表格区域 -->
    <el-card shadow="never" class="table-card">
      <template #header>
        <div class="table-header">
          <span>以下为库存预警的商品</span>
        </div>
      </template>

      <!-- 商品表格 -->
      <el-table ref="table" highlight-current-row
                border :data="warningGoodsList">
        <el-table-column label="序号" align="center" type="index" :index="indexMethod"/>
        <el-table-column label="商品名称" align="center" prop="goodsName"/>
        <el-table-column label="商品分类" align="center" prop="classifyName"/>
        <el-table-column label="单位" align="center" prop="unit"/>
        <el-table-column label="规格" align="center" prop="specs"/>
        <el-table-column label="库存" align="center" prop="stock"/>
        <el-table-column label="销售价" align="center" prop="salesPrice"/>
        <el-table-column label="库存阈值" align="center" prop="threshold"/>
      </el-table>

      <!-- 分页组件 -->
      <div class="page-cont">
        <pagination
            v-show="total>0"
            :total="total"
            v-model:page="query.pageNum"
            v-model:limit="query.pageSize"
            @pagination="getList"
        />
      </div>
    </el-card>

  </div>
</template>

<script setup>
import {Box, Search} from "@element-plus/icons-vue";
import {selectGoodsOrNormalOrWarningToCount, selectWarningGoodsList} from "@/api/erp/goods.js";

const query = ref({
  pageNum: 1,
  pageSize: 10,
  goodsName: null,
  sortId: null,
  specs: null,
  classifyName: null
})

//商品统计数据
const goodsToCount = ref([])

//库存预警商品列表数据
const warningGoodsList = ref([])

//预警商品列表总数
const total = ref(0)

//获取数据
const getList = () => {
  //查询总商品数,库存正常数,库存预警数
  selectGoodsOrNormalOrWarningToCount().then(res => {
    goodsToCount.value = res.data
  })

  //查询库存预警列表
  selectWarningGoodsList(query.value).then(res => {
    warningGoodsList.value = res.rows
    total.value = res.total
  })
}

/** 自定义序号 */
const indexMethod = (index) => {
  let pageNum = query.value.pageNum - 1;
  if ((pageNum !== -1 && pageNum !== 0)) {
    return (index + 1) + (pageNum * query.value.pageSize);
  } else {
    return (index + 1)
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  query.value.pageNum = 1
  getList()
}

//处理搜索框清空操作 清空后调用查询方法
const resetQuery = () => {
  query.value.goodsName = ''
  getList()
}

//组件挂载时调用
getList()
</script>

<style scoped>
/*主容器样式*/
.mains-container {
  padding: 20px; /*内边距*/
  background-color: #f5f7fa; /*背景颜色*/
}

/*头部区域样式*/
.header-section {
  display: flex; /*弹性布局*/
  justify-content: space-between; /*两段对齐*/
  align-items: center; /*垂直居中*/
  margin-bottom: 20px;
}

/*页面标题样式*/
.page-title {
  color: #303133; /*文字颜色*/
  margin: 0; /*外边距清零*/
}

/*搜索区域样式*/
.search-area {
  display: flex; /*弹性布局*/
  align-items: center; /*垂直居中*/
  gap: 10px; /*子元素的间距*/
}

/*统计卡片容器样式*/
.stat-cards {
  display: grid; /*网格布局*/
  grid-template-columns: repeat(3, 1fr); /*3列等宽*/
  gap: 20px; /*网格间距*/
  margin-bottom: 20px; /*底部外边距*/
}

/*单个统计卡片样式*/
.stat-card {
  border-radius: 10px; /*圆角边框*/
}

/*卡片内容区域样式*/
.card-content {
  display: flex; /*弹性布局*/
  align-items: center; /*垂直居中*/
}

/*卡片图标容器样式*/
.card-icon {
  width: 50px;
  height: 50px;
  border-radius: 8px; /*圆角边框*/
  display: flex; /*弹性布局*/
  align-items: center; /*垂直居中*/
  justify-content: center; /*水平居中*/
  margin-right: 15px; /*右侧外边距*/
  font-size: 24px; /*图标大小*/
  color: white; /*图标颜色*/
}

/*蓝色背景样式*/
.bg-blue {
  background-color: #00afff;
}

/*绿色背景样式*/
.bg-green {
  background-color: #3db731;
}

/*红色背景样式*/
.bg-red {
  background-color: red;
}

/*卡片信息区域样式*/
.card-info {
  flex: 1; /*占据剩余空间*/
}

/*卡片标题样式*/
.card-title {
  font-size: 14px;
  color: #656769;
  margin-bottom: 5px; /*底部外边距*/
}

/*卡片数值样式*/
.card-value {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
}

/*表格卡片样式*/
.table-card {
  border-radius: 8px; /*圆角边框*/
  margin-bottom: 20px; /*底部外边距*/
}

/*表格头部样式*/
.table-header {
  display: flex; /*弹性布局*/
  justify-content: center; /*两端对齐*/
  align-items: center; /*垂直居中*/
}

/*分页容器样式*/
.page-cont {
  display: flex; /*弹性布局*/
  justify-content: flex-end; /*右对齐*/
  margin-top: 20px; /*顶部外边距*/
}
</style>
