<template>
  <div class="app-container">
    <header-title :need-border="true" style="margin-bottom: 20px">
      <template slot="title">本周丢单汇总</template>
    </header-title>
    <el-form size="small" :inline="true">
      <el-form-item>
        <el-input v-model.trim="queryParams.model.query" placeholder="请输入" clearable size="small" style="width: 240px; margin-right: 10px;" />
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="handleTimeChange"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small" @click="handleQuery">搜索</el-button>
        <el-button size="small" @click="resetQuery">刷新</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8" style="margin-bottom: 10px">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:role:export']"
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :disabled="total < 1 "
          @click="exportData"
        >导出</el-button>
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
      <el-table-column label="当前状态" prop="status">
        <template v-slot="scope">
          {{ getStatusBeforeLose(scope.row.status) }}
        </template>
      </el-table-column>
      <el-table-column label="丢单审批说明" prop="loseAuditDesc">
        <template v-slot="scope">
          {{ scope.row.loseAuditDesc || '-' }}
        </template>
      </el-table-column>
      <el-table-column label="更新时间" prop="updateTime">
        <template v-slot="scope">
          {{ scope.row.updateTime || '-' }}
        </template>
      </el-table-column>
    </el-table>

    <div class="footer">
      <el-pagination
        :total="total"
        :page-sizes="[10, 20, 30]"
        :current-page="queryParams.current"
        :page-size="queryParams.size"
        layout="total, sizes, prev, pager, next, jumper"
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"
      />
    </div>
  </div>
</template>

<script>
import headerTitle from '@/components/Title/index.vue'
import { fetchOpportunities } from '@/api/statement'
import { statusMap } from '@/utils/commin'
export default {
  components: { headerTitle },
  data() {
    return {
      data: [], // 列表数据
      dateRange: '',
      queryParams: {
        current: 1, // 当前页码
        size: 10, // 每页条数
        model: {
          query: '', // 查询参数
          statusList: ['14', '15', '7'],
          loseDateStart: '',
          loseDateEnd: ''
        }
      },
      total: 0, // 总条数
      loading: false, // 加载状态
      multipleSelection: [] // 用于存储选中的行数据
    }
  },
  created() {
    const today = new Date()
    const dayOfWeek = today.getDay()

    // 计算上周日的日期（上周日是当前日期减去今天的星期几+0，实际上就是减去 dayOfWeek + 1）
    const loseDateStart = new Date(today)
    loseDateStart.setDate(today.getDate() - dayOfWeek)

    // 计算本周日的日期（本周日是当前日期加上（7 - 今天的星期几）天）
    const loseDateEnd = new Date(today)
    loseDateEnd.setDate(today.getDate() + (7 - dayOfWeek))

    this.queryParams.model.loseDateStart = this.formatYMDDate(loseDateStart)
    this.queryParams.model.loseDateEnd = this.formatYMDDate(loseDateEnd)
    this.dateRange = [this.queryParams.model.loseDateStart, this.queryParams.model.loseDateEnd]
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

    formatProfitRate(rate) {
      if (rate === null || rate === undefined) {
        return '-' // 处理空值
      }
      return `${(rate * 100).toFixed(2)}%`
    },

    getStatusBeforeLose(status) {
      return statusMap[Number(status)] || '-'
    },

    // 封装请求逻辑
    async fetchOpportunitiesData(queryParams) {
      this.loading = true
      try {
        const response = await fetchOpportunities('pageWeekLose', queryParams)
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

    handleTimeChange(item) {
      if (item) {
        this.queryParams.model.loseDateStart = item[0]
        this.queryParams.model.loseDateEnd = item[1]
      } else {
        this.queryParams.model.loseDateStart = ''
        this.queryParams.model.loseDateEnd = ''
      }
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
        '所属售前': item.preSaleName,
        '成本报价': item.amount,
        '销售对外报价': item.projExtQuoteTotal,
        '合同报价': item.signQuoteTotal,
        '整体利润率(不含外采)': this.formatProfitRate(item.projProfitRateExcl),
        '丢单理由': item.reasonDesc || '-',
        '当前状态': this.getStatusBeforeLose(item.status),
        '丢单审批说明': item.loseAuditDesc || '-',
        '更新时间': item.loseAuditPersonName || '-'
      }))
      this.exportToExcel(exportData, '丢单汇总_本周丢单汇总')
      this.loading = false
    }
  }
}
</script>
<style scoped>
.footer {
  float: right;
  margin: 30px 0;
}
</style>
