import request from '@/utils/request'

// 保存工作流信息
export const saveWorkflow = (data) => {
  return request({
    url: '/manager/setting/batch',
    method: 'post',
    data: data
  })
}

// 获取工作流信息
export const getWorkflow = (data) => {
  return request({
    url: '/manager/setting/page',
    method: 'post',
    data: data
  })
}

// 修改工作流信息
export const putWorkflow = (data) => {
  return request({
    url: '/manager/setting',
    method: 'put',
    data: data
  })
}

// 获取分类信息
export const getCategory = () => {
  return request({
    url: '/manager/setting/getEmailTemplateCategory',
    method: 'get'
  })
}
