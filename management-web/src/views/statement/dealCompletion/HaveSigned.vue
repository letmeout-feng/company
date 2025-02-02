<template>
  <div class="app-container">
    <header-title :need-border="true" style="margin-bottom: 20px;">
      <template slot="title">已签约</template>
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

    <el-row :gutter="10" class="mb8" style="margin-bottom: 10px;">
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
      <el-table-column label="商机名称" prop="name" min-width="150" />
      <el-table-column label="客户名称" prop="customersName" min-width="100" />
      <el-table-column label="所属销售" prop="saleName" min-width="100" />
      <el-table-column label="产品类别" prop="category" min-width="100" />
      <el-table-column label="报价版本" prop="currentVersion" min-width="100" />
      <el-table-column label="所属售前" prop="preSaleName" min-width="100" />
      <el-table-column label="成本报价" prop="amount" min-width="150" />
      <el-table-column label="合同报价" prop="signQuoteTotal" min-width="150" />
      <el-table-column label="整体利润率" min-width="100">
        <template v-slot="scope">
          {{ formatProfitRate(scope.row.projProfitRateExcl) }}
        </template>
      </el-table-column>
      <el-table-column label="合同类型" prop="contractType" min-width="100">
        <template v-slot="scope">
          {{ getContractTypeLabel(scope.row.contractType) }}
        </template>
      </el-table-column>
    </el-table>

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
export default {
  components: { headerTitle },
  data() {
    return {
      data: [],  // 列表数据
      queryParams: {
        current: 1, // 当前页码
        size: 10, // 每页条数
        model: {
          query: '', // 查询参数
          parentStatusList: ["00001302"],
          statusList: [6],
        }
      },
      total: 0, // 总条数
      loading: false, // 加载状态
      multipleSelection: [] // 用于存储选中的行数据
    };
  },
  created() {
    this.getList()
  },
  methods: {
    getContractTypeLabel(contractType) {
      switch (contractType) {
        case '1':
          return '欣象代理';
        case '2':
          return '北光直签';
        default:
          return '-'; // Optional: handle unexpected values
      }
    },
    formatProfitRate(rate) {
      if (rate === null || rate === undefined) {
        return '-'; // 处理空值
      }
      return `${(rate * 100).toFixed(2)}%`; // 将利润率转换为百分比格式，保留两位小数
    },
    // 封装请求逻辑
    async fetchOpportunitiesData(queryParams) {
      this.loading = true
      try {
        const response = await fetchOpportunities('listWithSigned', queryParams)
        return response // 返回响应数据
      } catch (error) {
        this.loading = false
        this.$message.error(error.data.msg)
        return null
      }
    },
    async getList() {
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
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    resetQuery() {
      // 重置查询条件
      this.queryParams.model.query = '';
      // 重置分页参数
      this.queryParams.current = 1;
      this.queryParams.size = 10;
      // 获取数据
      this.getList();
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
        '报价版本': item.currentVersion,
        '所属售前': item.preSaleName,
        '成本报价': item.amount,
        '合同报价': item.signQuoteTotal,
        '整体利润率': this.formatProfitRate(item.projProfitRateExcl),
        '合同类型': this.getContractTypeLabel(item.contractType)
      }))
      this.exportToExcel(exportData, '成单_已签约')
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
