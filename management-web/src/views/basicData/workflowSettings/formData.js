const currentFormData = [
  {
    senderInfo: [
      { nickName: '{所属售前}', default: true, userId: '' }
    ],
    carbonCopyInfo: [
      { nickName: '{所属销售}', default: true, userId: '' },
      { nickName: '穆立源', default: true, userId: '110' },
      { nickName: '龚夏婷', default: true, userId: '113' },
      { nickName: '于子涵', default: true, userId: '101' },
      { nickName: '夏群', default: true, userId: '100' }
    ],
    emailSubject: '需进行成本报价 - {商机主题} - {日期}',
    emailTemplate: '<p><strong>{商机主题}</strong> 需进行成本报价，请尽快完成。<br><br>点击<strong style="color: rgb(0, 102, 204);">{报价系统链接}</strong>进行成本报价</p>',
    category: '报价相关模板'
  },
  {
    senderInfo: [
      { nickName: '{所属销售}', default: true, userId: '' }
    ],
    carbonCopyInfo: [
      { nickName: '穆立源', default: true, userId: '110' },
      { nickName: '龚夏婷', default: true, userId: '113' },
      { nickName: '于子涵', default: true, userId: '101' },
      { nickName: '夏群', default: true, userId: '100' }
    ],
    emailSubject: '已完成成本报价 - {商机主题} - {售前部门} – {报价类型}{版本号} – {日期}',
    emailTemplate: '<p><strong>{售前部门} </strong>已完成<strong>{商机主题}</strong>成本报价，总成本为：<strong>{成本总额}</strong>，请尽快完成销售报价<br><br>点击<strong style="color: rgb(0, 102, 204);">{报价系统链接}</strong>进行销售报价</p>',
    category: '报价相关模板'
  },
  {
    senderInfo: [
      { nickName: '{所属售前}', default: true, userId: '' }
    ],
    carbonCopyInfo: [
      { nickName: '{所属销售}', default: true, userId: '' },
      { nickName: '穆立源', default: true, userId: '110' },
      { nickName: '龚夏婷', default: true, userId: '113' },
      { nickName: '于子涵', default: true, userId: '101' },
      { nickName: '夏群', default: true, userId: '100' }
    ],
    emailSubject: '需更新成本报价 - {商机主题} - {日期}',
    emailTemplate: '<p><strong>{商机主题}</strong><br><br>所属销售：<strong>{所属销售}</strong>，由于<strong>{重新报价说明}</strong>，需重新进行成本报价，请尽快完成。<br><br>点击<strong style="color: rgb(0, 102, 204);">{报价系统链接} </strong>进行成本报价更新</p>',
    category: '报价相关模板'
  },
  {
    senderInfo: [
      { nickName: '王总', default: true, userId: '115' }
    ],
    carbonCopyInfo: [
      { nickName: '{所属销售}', default: true, userId: '' },
      { nickName: '穆立源', default: true, userId: '110' },
      { nickName: '龚夏婷', default: true, userId: '113' },
      { nickName: '于子涵', default: true, userId: '101' },
      { nickName: '夏群', default: true, userId: '100' }
    ],
    emailSubject: '对外销售报价审批申请 - {商机主题} – {日期}',
    emailTemplate: '<p><strong>{商机主题}</strong> ，所属销售：<strong>{所属销售}</strong>，所属售前：<strong>{所属售前}</strong><br><br>评估总成本为：<strong>{成本总额}</strong>，销售对外报价：<strong>{销售对外报价}</strong>，项目利润率：<strong>{项目报价利润率}</strong><br>继续跟进原因：<strong>{继续跟进原因}</strong><br><br>请点击<strong style="color: rgb(0, 102, 204);">{报价系统链接}</strong>进行审批</p>',
    category: '审批流相关模板'
  },
  {
    senderInfo: [
      { nickName: '王总', default: true, userId: '115' },
      { nickName: '{所属销售}', default: true, userId: '' }
    ],
    carbonCopyInfo: [
      { nickName: '穆立源', default: true, userId: '110' },
      { nickName: '龚夏婷', default: true, userId: '113' },
      { nickName: '于子涵', default: true, userId: '101' },
      { nickName: '夏群', default: true, userId: '100' }
    ],
    emailSubject: '{销售报价审批结果} - {商机主题} - 销售报价 – {日期}',
    emailTemplate: '<p><strong>{商机主题}</strong><br><br>评估总成本为：<strong>{成本总额}</strong>，销售对外报价：<strong>{销售对外报价}</strong>，项目利润率：<strong>{项目报价利润率}</strong><br>报价审批结果：<strong>{销售报价审批结果}</strong><br><br><br>点击<strong style="color: rgb(0, 102, 204);">{报价系统链接}</strong>查看详情</p>',
    category: '审批流相关模板'
  },
  {
    senderInfo: [
      { nickName: '王总', default: true, userId: '115' }
    ],
    carbonCopyInfo: [
      { nickName: '{所属销售}', default: true, userId: '' },
      { nickName: '穆立源', default: true, userId: '110' },
      { nickName: '龚夏婷', default: true, userId: '113' },
      { nickName: '于子涵', default: true, userId: '101' },
      { nickName: '夏群', default: true, userId: '100' }
    ],
    emailSubject: '签约申请 - {商机主题} – {日期}',
    // emailTemplate: '{商机主题}，所属销售：{所属销售}，所属售前：{所属售前}\n\n评估总成本为：{成本总额)\n北光合同总金额：{北光合同总金额}，北光合同金额(不含硬件)：{北光合同金额不含硬件部分}\n北光总成本利润率：{总成本利润率}，成本利润率(不含硬件)：{成本利润率}\n\n\n继续签约原因：{继续签约原因}\n\n请点击{报价系统链接}进行签约审批'
    emailTemplate: '<p>{签约申请详情图片}<br><br>请点击<strong style="color: rgb(0, 102, 204);">{报价系统链接}</strong>进行签约审批</p>',
    category: '审批流相关模板'
  },
  {
    senderInfo: [
      { nickName: '{所属销售}', default: true, userId: '' }
    ],
    carbonCopyInfo: [
      { nickName: '穆立源', default: true, userId: '110' },
      { nickName: '龚夏婷', default: true, userId: '113' },
      { nickName: '于子涵', default: true, userId: '101' },
      { nickName: '夏群', default: true, userId: '100' }
    ],
    emailSubject: '{签约申请审批结果} - {商机主题} 签约申请 – {日期}',
    emailTemplate: '<p><strong>{商机主题}</strong><br><br>评估总成本为：<strong>{成本总额}</strong>，销售对外报价：<strong>{销售对外报价}</strong>，项目利润率：<strong>{项目签约利润率}</strong><br>签约申请审批结果：<strong>{签约申请审批结果}<br></strong><br>点击<strong style="color: rgb(0, 102, 204);">{报价系统链接}</strong>查看详情</p>',
    category: '审批流相关模板'
  },
  {
    senderInfo: [
      { nickName: '穆立源', default: true, userId: '110' }
    ],
    carbonCopyInfo: [
      { nickName: '{所属售前}', default: true, userId: '' },
      { nickName: '{所属销售}', default: true, userId: '' },
      { nickName: '龚夏婷', default: true, userId: '113' },
      { nickName: '于子涵', default: true, userId: '101' },
      { nickName: '夏群', default: true, userId: '100' }
    ],
    emailSubject: '不再继续跟进 - {商机主题} - {日期}',
    emailTemplate: '<p><strong>{商机主题} </strong>，所属销售：<strong>{所属销售}</strong>，所属售前：<strong>{所属售前}</strong><br><br>成本评估类型：<strong>{成本评估类型}</strong><br>评估总成本为：<strong>{成本总额}</strong><br>计划销售对外报价：<strong>{销售对外报价}</strong>，项目利润率(不含外采)：<strong>{项目报价利润率}</strong><br><br>建议不再继续跟进<br>丢单理由：<strong>{丢单理由}</strong>，请审批确认<br><br>点击<strong style="color: rgb(0, 102, 204);">{报价系统链接}</strong>查看详情，进行审批</p>',
    category: '丢单相关模板'
  },
  {
    senderInfo: [
      { nickName: '穆立源', default: true, userId: '110' }
    ],
    carbonCopyInfo: [
      { nickName: '{所属售前}', default: true, userId: '' },
      { nickName: '{所属销售}', default: true, userId: '' },
      { nickName: '龚夏婷', default: true, userId: '113' },
      { nickName: '于子涵', default: true, userId: '101' },
      { nickName: '夏群', default: true, userId: '100' }
    ],
    emailSubject: '丢单 - {商机主题} – {日期}',
    emailTemplate: '<p><strong>{商机主题} </strong>，所属销售：<strong>{所属销售}</strong>，所属售前：<strong>{所属售前}</strong><br><br>成本评估类型：<strong>{成本评估类型}</strong><br>评估总成本为：<strong>{成本总额}</strong><br><br>销售对外报价：<strong>{销售对外报价}</strong>，项目利润率(不含外采)：<strong>{项目报价利润率}</strong>，报价审批结果：<strong>{报价申请结果}</strong><br>预计签约金额：<strong>{申请签约金额}</strong>，项目利润率(不含外采)：<strong>{项目签约利润率}</strong>，签约申请审批结果：<strong>{签约申请审批结果}</strong><br><br>申请进行丢单处理，请审批，以下为具体丢单原因：<br>丢单原因：<strong>{丢单原因}</strong><br><br>点击<strong style="color: rgb(0, 102, 204);">{报价系统链接}</strong>查看详情，进行审批</p>',
    category: '丢单相关模板'
  },
  {
    senderInfo: [
      { nickName: '穆立源', default: true, userId: '110' }
    ],
    carbonCopyInfo: [
      { nickName: '{所属售前}', default: true, userId: '' },
      { nickName: '{所属销售}', default: true, userId: '' },
      { nickName: '龚夏婷', default: true, userId: '113' },
      { nickName: '于子涵', default: true, userId: '101' },
      { nickName: '夏群', default: true, userId: '100' }
    ],
    emailSubject: '丢单信息汇总 - {时间周期}',
    emailTemplate: '<p>附件为 <strong>{时间周期} </strong>期间，预计不再继续跟进的丢单信息汇总，请您下载附件查看。<br><br>点击<strong style="color: rgb(0, 102, 204);">{管理系统链接}</strong>查看详情</p>',
    category: '丢单相关模板'
  },
  {
    senderInfo: [
      { nickName: '李响', default: true, userId: '1876195487503020036' },
      { nickName: '张永博', default: true, userId: '106' },
      { nickName: '周强', default: true, userId: '1866303062802300939' }
    ],
    carbonCopyInfo: [
      { nickName: '安建欣', default: true, userId: '1876195487503020040' }
    ],
    emailSubject: '涉及硬件采购的潜在商机 - {商机主题} - {日期}',
    emailTemplate: '<p><strong>{商机主题} </strong>，所属销售：<strong>{所属销售}</strong>，所属售前：<strong>{所属售前}</strong><br><br>成本评估类型：<strong>{成本评估类型}</strong><br><br>硬件-自研采购总金额：<strong>{成本价总计}</strong>，请您点击下载附件查看详情<br>硬件-外采采购总金额：<strong>{成本价总计}</strong>，请您点击下载附件查看详情</p>',
    category: '硬件采购相关模板'
  },
  {
    senderInfo: [
      { nickName: '李响', default: true, userId: '1876195487503020036' },
      { nickName: '张永博', default: true, userId: '106' },
      { nickName: '周强', default: true, userId: '1866303062802300939' }
    ],
    carbonCopyInfo: [
      { nickName: '安建欣', default: true, userId: '1876195487503020040' }
    ],
    emailSubject: '涉及硬件采购的预计签约项目 - {商机主题} - {日期}',
    emailTemplate: '<p><strong>{商机主题} </strong>，所属销售：<strong>{所属销售}</strong>，所属售前：<strong>{所属售前}</strong><br><br>成本评估类型：<strong>{成本评估类型}</strong><br><br>硬件-自研采购总金额：<strong>{成本价总计}</strong>，请您点击下载附件查看详情<br>硬件-外采采购总金额：<strong>{成本价总计}</strong>，请您点击下载附件查看详情</p>',
    category: '硬件采购相关模板'
  },
  {
    senderInfo: [
      { nickName: '李响', default: true, userId: '1876195487503020036' },
      { nickName: '张永博', default: true, userId: '106' },
      { nickName: '周强', default: true, userId: '1866303062802300939' }
    ],
    carbonCopyInfo: [
      { nickName: '安建欣', default: true, userId: '1876195487503020040' }
    ],
    emailSubject: '涉及硬件采购的已签约项目 - {商机主题} - {日期}',
    emailTemplate: '<p><strong>{商机主题}</strong> ，所属销售：<strong>{所属销售}</strong>，所属售前：<strong>{所属售前}</strong><br><br>成本评估类型：<strong>{成本评估类型}</strong><br><br>硬件-自研采购总金额：<strong>{成本价总计}</strong>，请您点击下载附件查看详情<br>硬件-外采采购总金额：<strong>{成本价总计}</strong>，请您点击下载附件查看详情<br><br>点击<strong style="color: rgb(0, 102, 204);">{报价系统链接}</strong>查看详情</p>',
    category: '硬件采购相关模板'
  }
]

export default currentFormData
