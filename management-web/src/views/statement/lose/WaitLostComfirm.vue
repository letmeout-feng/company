<template>
  <div class="app-container">
    <header-title :need-border="true" style="margin-bottom: 20px">
      <template slot="title">待丢单确认</template>
    </header-title>
    <el-form size="small" :inline="true">
      <el-form-item>
        <el-input v-model.trim="queryParams.model.query" placeholder="请输入" clearable size="small" style="width: 240px" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small" @click="handleQuery">搜索</el-button>
        <el-button size="small" @click="resetQuery">刷新</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8" style="margin-bottom: 10px">
      <el-col :span="1.5">
        <el-button v-hasPermi="['system:role:export']" type="warning" plain icon="el-icon-download" size="mini"
          :disabled="total < 1" @click="exportData">导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="data" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" width="50" />
      <el-table-column label="商机名称" prop="name" min-width="130" />
      <el-table-column label="客户名称" prop="customersName" />
      <el-table-column label="所属销售" prop="saleName" />
      <el-table-column label="产品类别" prop="category" />
      <el-table-column label="所属售前" prop="preSaleName" />
      <el-table-column label="成本报价" prop="amount" />
      <el-table-column label="销售对外报价" prop="projExtQuoteTotal">
        <template v-slot="scope">
          {{ scope.row.projExtQuoteTotal || '-' }}
        </template>
      </el-table-column>
      <el-table-column label="合同报价" prop="signQuoteTotal">
        <template v-slot="scope">
          {{ scope.row.signQuoteTotal || '-' }}
        </template>
      </el-table-column>
      <el-table-column label="整体利润率(不含外采)" min-width="100">
        <template v-slot="scope">
          {{ formatProfitRate(scope.row.projProfitRateExcl) }}
        </template>
      </el-table-column>
      <el-table-column label="合同类型" prop="contractType" min-width="100">
        <template v-slot="scope">
          {{ getContractTypeLabel(scope.row.contractType) }}
        </template>
      </el-table-column>
      <el-table-column label="申请丢单前状态" prop="statusBeforeLose" min-width="100">
        <template v-slot="scope">
          {{ getStatusBeforeLose(scope.row.statusBeforeLose) }}
        </template>
      </el-table-column>
      <el-table-column label="丢单理由" prop="reasonDesc">
        <template v-slot="scope">
          {{ scope.row.reasonDesc || '-' }}
        </template>
      </el-table-column>
      <el-table-column label="申请丢单时间" prop="loseTime">
        <template v-slot="scope">
          {{ scope.row.loseTime || '-' }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="100" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button size="middle" type="text" @click="handleView(scope.row)">商机详情</el-button>
          <el-button size="middle" type="text" style="margin-left: 0 !important;"
            @click="handleApprove(scope.row)">丢单审批</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :visible.sync="dialogDetailVisible" title="商机详情" width="600px">
      <div class="modal-content">
        <div class="modal-item">
          <span style="width: 15%;">商机名称：</span>
          <span style="width: 85%;font-weight: 700;">{{ opportunity.name }}</span>
        </div>
        <div class="modal-item">
          <span style="width: 15%;">客户名称：</span>
          <span style="width: 85%;">{{ opportunity.customersName }}</span>
        </div>
        <div class="modal-item">
          <span style="width: 15%;">所属销售：</span>
          <span style="width: 85%;">{{ opportunity.saleName }}</span>
        </div>
        <div class="modal-item">
          <span style="width: 15%;">产品类别：</span>
          <span style="width: 85%;">{{ opportunity.category }}</span>
        </div>
        <div class="modal-item">
          <span style="width: 15%;">所属售前：</span>
          <span style="width: 85%;">{{ opportunity.preSaleName }}</span>
        </div>
        <div class="modal-item" style="display: flex; align-items: flex-start;">
          <span style="width: 15%;">商机介绍：</span>
          <span style="width: 85%;">{{ opportunity.introduce }}</span>
        </div>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCancel" type="default">取消</el-button>
        <el-button v-if="opportunity.status === '1'" @click="handleOk" type="primary">确定</el-button>
      </div>
    </el-dialog>
    <isOrderLoss :opportunity="opportunity" :dialogVisible="OrderLossVisible" @update:dialogVisible="handleOrderLoss" />
    <isLossOrder :opportunity="opportunity" :dialogVisible="LossOrderVisible" @update:dialogVisible="handleLossOrder" />
    <div class="footer">
      <el-pagination :total="total" :page-sizes="[10, 20, 30]" :current-page="queryParams.current"
        :page-size="queryParams.size" layout="total, sizes, prev, pager, next, jumper"
        @current-change="handleCurrentChange" @size-change="handleSizeChange" />
    </div>
  </div>
</template>

<script>
import headerTitle from '@/components/Title/index.vue'
import { fetchOpportunities } from '@/api/statement'
import { statusMap } from '@/utils/commin'
import isOrderLoss from './components/OrderLossApproval.vue';
import isLossOrder from './components/LossOrderApproval.vue';

export default {
  components: { headerTitle, isOrderLoss, isLossOrder },
  data() {
    return {
      data: [], // 列表数据
      queryParams: {
        current: 1, // 当前页码
        size: 10, // 每页条数
        model: {
          query: '', // 查询参数
          parentStatusList: ['00001301'],
          statusList: ['14']
        }
      },
      total: 0, // 总条数
      loading: false, // 加载状态
      multipleSelection: [],// 用于存储选中的行数据
      OrderLossVisible: false, // 弹框显示/隐藏控制
      LossOrderVisible: false,
      opportunity: {}, // 当前查看的商机对象
      dialogDetailVisible: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getContractTypeLabel(contractType) {
      switch (contractType) {
        case '1':
          return '欣象代理'
        case '2':
          return '北光直签'
        default:
          return '-'
      }
    },

    getStatusBeforeLose(status) {
      return statusMap[Number(status)] || '-'
    },

    formatProfitRate(rate) {
      if (rate === null || rate === undefined) {
        return '-'
      }
      return `${(rate * 100).toFixed(2)}%`
    },

    // 封装请求逻辑
    async fetchOpportunitiesData(queryParams) {
      this.loading = true
      try {
        const response = await fetchOpportunities('pageWaitLose', queryParams)
        return response // 返回响应数据
      } catch (error) {
        this.loading = false
        this.$message.error(error.data.msg)
        return null
      }
    },

    async getList() {
      this.loading = true
      const response = await this.fetchOpportunitiesData(this.queryParams)
      if (response) {
        this.data = response.rows
        this.loading = false
        this.total = Number(response.total)
      }
    },

    handleCurrentChange(page) {
      this.queryParams.current = page
      this.getList()
    },

    handleSizeChange(size) {
      this.queryParams.size = size
      this.getList()
    },

    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },

    resetQuery() {
      // 重置查询条件
      this.queryParams.model.query = ''
      // 重置分页参数
      this.queryParams.current = 1
      this.queryParams.size = 10
      // 获取数据
      this.getList()
    },

    handleSelectionChange(val) {
      this.multipleSelection = val
    },

    // 导出 Excel
    async exportData() {
      const queryParams = JSON.parse(JSON.stringify(this.queryParams))
      queryParams.size = 9999
      const response = await this.fetchOpportunitiesData(queryParams)
      if (!response) return

      const data = this.multipleSelection.length > 0 ? this.multipleSelection : response.rows
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
        '申请丢单时间': item.loseTime || '-'
      }))
      this.exportToExcel(exportData, '丢单汇总_待丢单确认')
      this.loading = false
    },
    handleApprove(row) {
      this.opportunity = row;
      if (row.loseType === '1' || (['2', '3', '4'].includes(row.loseType) && row.type === 'INCAPABLE')) {
        this.OrderLossVisible = true;
      } else {
        this.LossOrderVisible = true;
      }
    },
    handleOrderLoss(v) {
      this.OrderLossVisible = v;
      this.getList()
    },
    handleLossOrder(v) {
      this.LossOrderVisible = v;
      this.getList()
    },
    // 查看商机详情
    handleView(row) {
      this.opportunity = row; // 设置当前商机数据
      this.dialogDetailVisible = true; // 显示弹框
    },
    handleCancel() {
      this.dialogDetailVisible = false; // 关闭弹框
    },
    handleOk() {
      this.dialogDetailVisible = false; // 关闭弹框
    },
  }
}
</script>
<style scoped>
.footer {
  float: right;
  margin: 30px 0;
}

.modal-content {
  padding: 0 20px;
}

.modal-item {
  margin-bottom: 10px;
  display: flex;
  align-items: center;
}
</style>
