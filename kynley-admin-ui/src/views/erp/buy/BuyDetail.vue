<template>
  <vxe-modal height="70vh" title="采购详情" v-model="open" width="60%" show-maximize showFooter resize>
    <div class="custom-title">
      <span>基本信息</span>
    </div>
    <el-descriptions
        :column="2"
        border
    >
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            订单总金额(元)
          </div>
        </template>
        {{ buyItem.totalAmount }}元
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            供应商
          </div>
        </template>
        {{ buyItem.supplierName }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            采购状态
          </div>
        </template>
        <dict-tag :options="buy_status" :value="buyItem.status"/>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            备注
          </div>
        </template>
        {{ buyItem.remark }}
      </el-descriptions-item>
    </el-descriptions>

    <div class="custom-title">
      <span>采购明细</span>
    </div>
    <el-table border :data="buyItemsList">
      <el-table-column label="序号" align="center" type="index" width="50"/>
      <el-table-column label="商品名称" align="center" prop="goodsName"/>
      <el-table-column label="采购数量" align="center" prop="total"/>
      <el-table-column label="采购单价" align="center" prop="unitPrice"/>
    </el-table>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="open = false">关 闭</el-button>
      </div>
    </template>
  </vxe-modal>
</template>

<script setup>
import {getBuy} from "@/api/erp/buy.js";

const {proxy} = getCurrentInstance()
const {buy_status} = proxy.useDict('buy_status')

//是否打开弹窗
const open = ref(false)

//采购信息数据
const buyItem = ref(null)
//采购明细信息
const buyItemsList = ref(null)

//父组件调用方法
const handleOpen = (buyId) => {
  //根据采购ID查询采购信息
  getBuy(buyId).then(res => {
    buyItem.value = res.data
    buyItemsList.value = res.data.buyItemsList
    //打开弹窗
    open.value = true
  })
}

//暴露方法给父组件使用
defineExpose({
  handleOpen
})
</script>

<style scoped>

/*自定义标题容器样式*/
.custom-title {
  margin: 5px 0;
  font-size: 18px;
  font-weight: bold;
  color: #333;
  position: relative;
  padding-left: 12px;
}

/*装饰条*/
.custom-title::before {
  content: "";
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  height: 18px;
  width: 4px;
  background-color: #00afff;
  border-radius: 2px;
}
</style>
