import request from '@/utils/request'; 
// 丢单
export const fetchLoseInfo = (path,params) => {  
    return request({  
      url: `/quote/lose/${path}`,  
      method: 'post',  
      data: params
    });  
  };