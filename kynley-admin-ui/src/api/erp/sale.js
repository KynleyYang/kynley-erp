import request from '@/utils/request'

// 查询销售列表
export function listSale(query) {
  return request({
    url: '/erp/sale/list',
    method: 'get',
    params: query
  })
}

// 查询销售详细
export function getSale(saleId) {
  return request({
    url: '/erp/sale/' + saleId,
    method: 'get'
  })
}

// 新增销售
export function addSale(data) {
  return request({
    url: '/erp/sale',
    method: 'post',
    data: data
  })
}

// 修改销售
export function updateSale(data) {
  return request({
    url: '/erp/sale',
    method: 'put',
    data: data
  })
}

// 删除销售
export function delSale(saleId) {
  return request({
    url: '/erp/sale/' + saleId,
    method: 'delete'
  })
}

// 发货
export function handleUps(data) {
  return request({
    url: '/erp/sale/handleUp',
    method: 'put',
    data: data
  })
}
