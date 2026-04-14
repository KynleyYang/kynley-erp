import request from '@/utils/request'

// 查询商品分类列表
export function listSort(query) {
  return request({
    url: '/erp/sort/list',
    method: 'get',
    params: query
  })
}

// 查询商品分类详细
export function getSort(sortId) {
  return request({
    url: '/erp/sort/' + sortId,
    method: 'get'
  })
}

// 新增商品分类
export function addSort(data) {
  return request({
    url: '/erp/sort',
    method: 'post',
    data: data
  })
}

// 修改商品分类
export function updateSort(data) {
  return request({
    url: '/erp/sort',
    method: 'put',
    data: data
  })
}

// 删除商品分类
export function delSort(sortId) {
  return request({
    url: '/erp/sort/' + sortId,
    method: 'delete'
  })
}
