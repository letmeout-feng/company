<template>
  <div>
    <a-card title="丢单审批未通过" :headStyle="{ 'height': '66px' }">
      <template #extra>
        <div style="display: flex; justify-content: flex-start; align-items: center; text-align: center;">
          <div style="display: flex; align-items: center; gap: 8px;">
            <a-input allowClear v-model:value="query" placeholder="请输入搜索内容" size="middle"
              @pressEnter="handleSearch"></a-input>
            <a-button @click="handleSearch" type="primary" size="middle">搜索</a-button>
            <a-button type="primary" size="middle" :disabled="pagination.total < 1" @click="exportData">导出</a-button>
          </div>
        </div>
      </template>
      <a-table :columns="visibleColumns" :data-source="data" :pagination="pagination" :loading="loading"
        :rowKey="record => record.id" :row-selection="rowSelection" @change="handleTableChange">
        <template #bodyCell="{ column, record, index }">
          <template v-if="column.dataIndex === 'index'">
            <span>{{ (pagination.current - 1) * pagination.pageSize + index + 1 }}</span>
          </template>
          <template v-else-if="column.dataIndex === 'amount'">
            <span>{{ record.amount !== null && record.amount !== undefined ? formatNumber(record.amount.toFixed(2)) : '-'
            }}</span>
          </template>
          <template v-else-if="column.dataIndex === 'projExtQuoteTotal'">
            <span>{{ record.projExtQuoteTotal !== null && record.projExtQuoteTotal !== undefined ?
              formatNumber(record.projExtQuoteTotal.toFixed(2)) : '-' }}</span>
          </template>
          <template v-else-if="column.dataIndex === 'signQuoteTotal'">
            <span>{{ record.signQuoteTotal !== null && record.signQuoteTotal !== undefined ?
              formatNumber(record.signQuoteTotal.toFixed(2)) : '-' }}</span>
          </template>
          <template v-else-if="column.dataIndex === 'signProjProfitRateExcl'">
            <span>{{ record.signProjProfitRateExcl !== null && record.signProjProfitRateExcl !== undefined ?
              (parseFloat(record.signProjProfitRateExcl) * 100).toFixed(2) + '%' : '-' }}</span>
          </template>
          <template v-else-if="column.dataIndex === 'contractType'">
            <span v-if="record.contractType === '1'">欣象代理</span>
            <span v-else-if="record.contractType === '2'">北光直签</span>
            <span v-else>-</span>
          </template>
          <template v-else-if="column.dataIndex === 'updateTime'">
            <span>{{ record.updateTime ? new Date(record.updateTime).toLocaleString() : '-' }}</span>
          </template>
          <template v-else-if="column.dataIndex === 'statusBeforeLose'">
            <span>{{ getStatusText(record.statusBeforeLose) }}</span>
          </template>
          <span v-else-if="column.dataIndex === 'actions'" style="text-align: left;">
            <a-button type="link" @click="handleDetail(record)" class="nomp">详情</a-button>
            <a-button v-hasPermi="['quote:order:reapply']" type="link" @click="handleUnqoute('discard', record)"
              class="nomp">丢单处理</a-button>
            <a-button v-hasPermi="['quote:order:handleLost']" type="link" @click="handleUnqoute('reapply', record)"
              class="nomp">重新申请报价</a-button>
            <a-button v-hasPermi="['quote:order:update']" type="link" @click="updateSalesOffer(record)" class="nomp"
              v-if="['2', '3', '4', '5', '6'].includes(record.loseType)">更新销售报价</a-button>
            <a-button v-hasPermi="['quote:order:contractRequest']" type="link" @click="handleUpdate(record)"
              class="nomp" v-if="['5', '6'].includes(record.loseType)">更新签约申请</a-button>
          </span>
        </template>
      </a-table>
    </a-card>
    <ApproveDetail :open="isModalVisible" :record="selectedRecord" @close="handleClose" @ok="handleOk"
      :isModalVisible="isModalVisible" :isWaitingSign="isWaitingSign" :isSignApprovalStatus="isSignApprovalStatus"
      :isLoss="isLoss">
    </ApproveDetail>
    <HandleUnable :open="isUnqouteModalVisible" :opportunity="selectedRecord" @close="handleCommonClose"
      :modalMode="modalMode" :isLostParams="isLostParams" />
    <QuotationApproval :open="isQuotationApprovalVisible" @approval-contractsigning-close="handleQuotationApprovalClose"
      :isApprovalOfContractSigning="isApprovalOfContractSigning" :isReContractApplication="isReContractApplication"
      @contract-apply-close="handleQuotationApprovalClose" :isSummarySheet="isSummarySheet"
      :opportunity="selectedRecord" :componentTitle="componentTitle" />
    <SalesQuotation :open="isSaleQuoteModelVisible" :opportunity="selectedRecord" @close="handleSaleQuoteCancel"
      :isSales="isSales" @handleReapply="handleReapply" @handleLose="handleLose"
      :isUpdateSalesOffer="isUpdateSalesOffer">
    </SalesQuotation>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { fetchLoseInfo } from '@/api/lose'
import ApproveDetail from '../../sales/CompanyApproval/components/ApproveDetail.vue';
import HandleUnable from '../../sales/SalesToQuotation/components/handleUnCost.vue' //丢单 重新申请报价
import QuotationApproval from '../../sales/CompanyApproval/components/QuotationApproval.vue'
import SalesQuotation from '@/views/sales/SalesToQuotation/components/SalesQuotation.vue'; //销售报价
import { formatNumber } from '@/utils/format'
import { statusMap } from '@/enums/LoseType'
import { exportToExcel } from '@/utils/common'


const columns = ref([
  { title: '序号', dataIndex: 'index', key: 'index', render: (_, __, index) => index + 1, width: '6%' },
  { title: 'id', dataIndex: 'id', visible: false },
  { title: '商机名称', dataIndex: 'name', width: '10%', sorter: (a, b) => a.name.localeCompare(b.name) },
  { title: '客户名称', dataIndex: 'customersName', sorter: (a, b) => a.customersName.localeCompare(b.customersName) },
  { title: '所属销售id', dataIndex: 'saleId', visible: false },
  { title: '所属销售', dataIndex: 'saleName', sorter: (a, b) => a.saleName.localeCompare(b.saleName) },
  { title: '产品类别', dataIndex: 'category', key: 'category', sorter: (a, b) => a.category.localeCompare(b.category) },
  { title: '所属售前', dataIndex: 'preSaleId', visible: false },
  { title: '所属售前', dataIndex: 'preSaleName', width: '9%', sorter: (a, b) => a.preSaleName.localeCompare(b.preSaleName) },
  { title: '成本报价', dataIndex: 'amount', key: 'amount', sorter: (a, b) => parseFloat(a.amount) - parseFloat(b.amount) },
  { title: '销售对外报价', dataIndex: 'projExtQuoteTotal', key: 'projExtQuoteTotal', sorter: (a, b) => parseFloat(a.projExtQuoteTotal) - parseFloat(b.projExtQuoteTotal) },
  { title: '合同报价', dataIndex: 'signQuoteTotal', key: 'signQuoteTotal', sorter: (a, b) => parseFloat(a.signQuoteTotal) - parseFloat(b.signQuoteTotal) },
  { title: '整体利润率', dataIndex: 'signProjProfitRateExcl', key: 'signProjProfitRateExcl', },
  { title: '合同类型', dataIndex: 'contractType', key: 'contractType', sorter: (a, b) => a.contractType.localeCompare(b.contractType) },
  { title: '申请丢单前状态', dataIndex: 'statusBeforeLose', key: 'statusBeforeLose', width: '8%' },
  { title: '丢单理由', dataIndex: 'reasonDesc', key: 'reasonDesc', width: '8%' },
  { title: '申请丢单时间', dataIndex: 'loseTime', key: 'loseTime', width: '10%', sorter: (a, b) => new Date(a.updateTime) - new Date(b.updateTime) },
  { title: '操作', dataIndex: 'actions', key: 'actions', width: '8%' },
]);
const visibleColumns = computed(() => {
  return columns.value.filter(column => column.visible !== false);
});
const selectedRowKeys = ref([]); // 当前选中的行
const selectedRowRecords = ref([]);
const rowSelection = computed(() => ({
  selectedRowKeys: selectedRowKeys.value,
  onChange: (selectedKeys) => {
    selectedRowKeys.value = selectedKeys;
    selectedRowRecords.value = data.value.filter(record => selectedKeys.includes(record.id));
  },
}));
const data = ref([]);
const loading = ref(true); // 加载状态
const selectedRecord = ref(null)
const isWaitingSign = ref(false) //是否显示签约申请标识
const isModalVisible = ref(false) //报价详情
const isSignApprovalStatus = ref(false)
const query = ref('');
const isLoss = ref(false)

const modalMode = ref('');
const isUnqouteModalVisible = ref(false)

const isQuotationApprovalVisible = ref(false)
const isApprovalOfContractSigning = ref(false)
const isReContractApplication = ref(false)

const isSaleQuoteModelVisible = ref(false) //销售报价
const isSales = ref(false)
const isUpdateSalesOffer = ref(false)

const isSummarySheet = ref(false) //通用标识
const componentTitle = ref('')

const isLostParams = ref('') //丢单传参相关

const pagination = ref({
  current: 1,
  pageSize: 10,
  total: 1,
});
// 数据加载  
const loadOpportunities = async () => {
  loading.value = true;
  console.log("开始加载数据...");
  const filters = {
    id: '',
    name: '',
    category: '',
    statusList: ['15'],
    parentStatusList: ["00001301"],
    query: query.value.trim(),
  };
  const params = {
    current: pagination.value.current,
    model: filters,
    order: 'descending',
    size: pagination.value.pageSize,
    sort: 'id',
  }
  try {
    const response = await fetchLoseInfo('pageLoseReject', params);
    data.value = response.rows || []; // 确保响应结构正确 
    // 手动默认按升序排序, 避免高亮 
    data.value = [...data.value].sort(
      (a, b) => new Date(a.updateTime) - new Date(b.updateTime)
    );
    pagination.value.total = response.total;
  } catch (error) {
    console.error('Error loading opportunities:', error);
  } finally {
    loading.value = false;
  }
};
const handleTableChange = (paginationProps) => {
  if (paginationProps.current) {
    pagination.value.current = paginationProps.current;
    loadOpportunities();
  }
}
const getStatusText = (status) => {
  return statusMap[status] || '未知状态'; // 如果状态未定义，返回'未知状态'
};

const handleUnqoute = (mode, record) => {
  modalMode.value = mode;
  selectedRecord.value = record;
  if (mode === 'discard') {
    isLostParams.value = '丢单审批未通过'
  } else {
    isLostParams.value = ''
  }
  isUnqouteModalVisible.value = true
}
const handleCommonClose = () => {
  isUnqouteModalVisible.value = false
  loadOpportunities()
}


const updateSalesOffer = (record) => {
  selectedRecord.value = record;
  isSales.value = true
  isUpdateSalesOffer.value = true
  isSaleQuoteModelVisible.value = true
};
const handleSaleQuoteCancel = () => {
  isSaleQuoteModelVisible.value = false;
  isSales.value = false
  isUpdateSalesOffer.value = false
  loadOpportunities()
}
const handleReapply = (param) => {
  isSaleQuoteModelVisible.value = false;
  isSales.value = false
  isUpdateSalesOffer.value = false
  handleUnqoute('reapply', param)
}
const handleLose = (param) => {
  isSaleQuoteModelVisible.value = false;
  isSales.value = false
  isUpdateSalesOffer.value = false
  handleUnqoute('discard', param)
}


const handleUpdate = (record) => {
  selectedRecord.value = record;
  isSummarySheet.value = true
  componentTitle.value = '更新签约申请'
  isQuotationApprovalVisible.value = true
  isReContractApplication.value = true
};
const handleQuotationApprovalClose = () => {
  isApprovalOfContractSigning.value = false
  isQuotationApprovalVisible.value = false
  isReContractApplication.value = false
  isSummarySheet.value = false
  componentTitle.value = ''
  loadOpportunities();
}
const handleSearch = () => {
  loadOpportunities();
};

const handleDetail = (record) => {
  isLoss.value = true
  selectedRecord.value = record;
  isWaitingSign.value = true
  isSignApprovalStatus.value = true
  isModalVisible.value = true;
};
const handleClose = () => {
  isLoss.value = false
  isModalVisible.value = false
  isWaitingSign.value = false
  isSignApprovalStatus.value = false
}
onMounted(() => {
  console.log('组件已挂载，开始加载数据...');
  loadOpportunities();
});

// 导出 Excel
const exportData = async () => {
  loading.value = true;
  const filters = {
    id: '',
    name: '',
    category: '',
    statusList: ['15'],
    parentStatusList: ["00001301"],
    query: '',
  };
  const params = {
    current: pagination.value.current,
    model: filters,
    order: 'descending',
    size: 99999,
    sort: 'id',
  }
  try {
    const response = await fetchLoseInfo('pageLoseReject', params);
    let data = selectedRowKeys.value.length > 0 ? selectedRowRecords.value : response.rows
    data = data.sort(
      (a, b) => new Date(a.updateTime) - new Date(b.updateTime)
    );
    const exportData = data.map((item) => ({
      '商机名称': item.name,
      '客户名称': item.customersName,
      '所属销售': item.saleName,
      '产品类别': item.category,
      '所属售前': item.preSaleName,
      '成本报价': item.amount,
      '销售对外报价': item.projExtQuoteTotal,
      '合同报价': item.signQuoteTotal,
      '整体利润率(不含外采)': this.formatProfitRate(item.projProfitRateExcl),
      '合同类型': this.getContractTypeLabel(item.contractType),
      '申请丢单前状态': this.getStatusBeforeLose(item.statusBeforeLose),
      '丢单理由': item.reasonDesc || '-',
      '申请丢单时间': item.loseTime || '-',
      '丢单审批人': item.loseAuditPersonName || '-',
      '丢单审批理由': item.loseAuditDesc || '-'
    }))
    exportToExcel(exportData, '丢单汇总_丢单审批未通过')
  } catch (error) {
    console.error('Error loading opportunities:', error);
  } finally {
    loading.value = false;
  }
}
</script>

<style scoped>
.table-container {
  width: 100%;
  display: flex;
  flex-direction: column;
}

.ant-table {
  flex: 1;
  background-color: #f0f5ff;
  overflow: auto;
}

.ant-btn {
  margin-right: 10px;
  z-index: 999;
}
</style>