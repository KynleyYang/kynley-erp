import request from '@/utils/request'

// 查询采购列表
export function listBuy(query) {
  return request({
    url: '/erp/buy/list',
    method: 'get',
    params: query
  })
}

// 查询采购详细
export function getBuy(buyId) {
  return request({
    url: '/erp/buy/' + buyId,
    method: 'get'
  })
}

// 新增采购
export function addBuy(data) {
  return request({
    url: '/erp/buy',
    method: 'post',
    data: data
  })
}

// 修改采购
export function updateBuy(data) {
  return request({
    url: '/erp/buy',
    method: 'put',
    data: data
  })
}

// 删除采购
export function delBuy(buyId) {
  return request({
    url: '/erp/buy/' + buyId,
    method: 'delete'
  })
}

// 修改采购
export function reach(data) {
  return request({
    url: '/erp/buy/reach',
    method: 'put',
    data: data
  })
}
