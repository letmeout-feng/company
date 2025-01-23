import request from '@/utils/request'

// page接口
export function fetchOpportunities(path, params) {
  return request({
    url: `/manager/report/${path}`,
    method: 'post',
    data: params
  })
}
// 获取商机所有版本
export function getVersion (params) {
  return request({
    url: '/quote/opportunities/version',
    method: 'post',
    data: params
  });
};
//  粗略成本报价详情
export function getRoughInfo (params) {
  return request({
    url: '/quote/rough/getRoughInfo',
    method: 'post',
    data: params
  });
};

// 丢单审批相关
export function fetchLoseInfo(path, params) {
  return request({
    url: `/manager/report/lose/${path}`,
    method: 'post',
    data: params
  })
}
// 丢单审批
export function fetchLoseApproval(path, params) {
  return request({
    url: `/manager/report/${path}`,
    method: 'post',
    data: params
  })
}
