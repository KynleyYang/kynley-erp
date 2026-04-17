<template>
    <vxe-modal height="70vh" title="选择商品" v-model="open" width="60%" show-maximize showFooter resize>
        <!-- 顶部搜索 -->
        <el-form :model="queryParams" ref="queryRef" :inline="true" label-width="68px">
            <el-form-item label="商品名称" prop="goodsName">
                <el-input
                        v-model="queryParams.goodsName"
                        placeholder="请输入商品名称"
                        clearable
                        @keyup.enter="handleQuery"
                />
            </el-form-item>
            <el-form-item label="商品分类" prop="classifyName">
                <el-input
                        v-model="queryParams.classifyName"
                        placeholder="请输入商品分类"
                        clearable
                        @keyup.enter="handleQuery"
                />
            </el-form-item>
            <el-form-item label="规格" prop="specs">
                <el-input
                        v-model="queryParams.specs"
                        placeholder="请输入规格"
                        clearable
                        @keyup.enter="handleQuery"
                />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
                <el-button icon="Refresh" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>

        <!-- 表格 -->
        <el-table @row-click="clickRow" ref="table" highlight-current-row
                  border v-loading="loading" :data="goodsList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center"/>
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
        <pagination
                v-show="total>0"
                :total="total"
                v-model:page="queryParams.pageNum"
                v-model:limit="queryParams.pageSize"
                @pagination="getList"
        />

        <template #footer>
            <div class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="open = false">取 消</el-button>
            </div>
        </template>


    </vxe-modal>
</template>

<script setup>
import {listGoods} from "@/api/erp/goods.js";

//弹窗是否被打开
const open = ref(false)
//加载状态
const loading = ref(false)
//商品列表信息
const goodsList = ref([])
//当前选中的行(单行或多行)
const selectedRows = ref([])
//当前组件的实例
const {proxy} = getCurrentInstance()
//数据总数
const total = ref(0)
//查询参数
const queryParams = ref({
    pageNum: 1,
    pageSize: 10,
    goodsName: null,
    sortId: null,
    specs: null,
    classifyName: null
})

const ids = ref([])
const single = ref(true)
const multiple = ref(true)

//新增一个ref来存储已有的商品
const existingGoods = ref([])

//父组件调用方法
const handleOpen = (existingItems = []) => {
    //保存已有的商品列表
    existingGoods.value = existingItems;
    getList()
    //打开弹窗
    open.value = true
}

/** 查询商品列表 */
const getList = () => {
    loading.value = true
    listGoods(queryParams.value).then(response => {
        goodsList.value = response.rows
        total.value = response.total
        loading.value = false

        //数据加载完之后, 自动勾选已有商品
        nextTick(() => {
            if (existingGoods.value.length > 0) {
                const table = proxy.$refs.table;
                goodsList.value.forEach(row => {
                    if (existingGoods.value.some(item => item.goodsId === row.goodsId)) {
                        table.toggleRowSelection(row, true);
                        //添加到selectRows数组中
                        if (selectedRows.value.some(item => item.goodsId === row.goodsId)) {
                            selectedRows.value.push(row)
                        }
                    }
                })
            }
        })

    })
}

//点击行 获取行
const clickRow = (row) => {
    // 获取表格组件的引用
    const table = proxy.$refs.table;
    // 查找当前点击的行是否已经在选中的数组中
    const index = selectedRows.value.findIndex(item => item === row)
    //如果行已经选中(在数组中找到)
    if (index >= 0) {
        //如果行已经选中, 则取消选中
        table.toggleRowSelection(row, false)
        //从数组中移除
        selectedRows.value.splice(index, 1)
    } else {
        //如果没有选中, 则选中改行
        table.toggleRowSelection(row, true)
        //将该行数据添加到selectedRows数组中
        selectedRows.value.push(row);
    }
}

//声明emit事件
const emit = defineEmits(['ok'])

/** 确定按钮 */
const submitForm = () => {
    //将选中的行数组信息传回给父组件
    emit('ok', selectedRows.value)
    selectedRows.value = []
    //关闭弹窗
    open.value = false
}

// 多选框选中数据
const handleSelectionChange = (selection) => {
    ids.value = selection.map(item => item.goodsId)
    single.value = selection.length != 1
    multiple.value = !selection.length
}

/** 自定义序号 */
const indexMethod = (index) => {
    let pageNum = queryParams.value.pageNum - 1;
    if ((pageNum !== -1 && pageNum !== 0)) {
        return (index + 1) + (pageNum * queryParams.value.pageSize);
    } else {
        return (index + 1)
    }
}

/** 搜索按钮操作 */
const handleQuery = () => {
    queryParams.value.pageNum = 1
    getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
    proxy.resetForm("queryRef")
    handleQuery()
}

//暴露方法给父组件使用
defineExpose({
    handleOpen
})
</script>

<style scoped>

</style>
