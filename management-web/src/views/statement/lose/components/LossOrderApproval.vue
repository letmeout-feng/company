<template>
  <el-dialog title="丢单审批" :visible.sync="dialogVisible" :before-close="handleCancel" :close-on-click-modal="false"
    width="70%" :destroy-on-close="true">
    <div class="table-style">
      <div class="modal-content"
        style="border: 1px solid #f0f0f0; background: #fafafa; padding: 20px 10px 10px;width:100%">
        <div class="modal-item">
          <span style="width: 20%;">项目名称：</span>
          <span style="width: 60%;">{{ opportunity.name }}</span>
        </div>
        <div class="modal-item">
          <span style="width: 20%;">销售人员：</span>
          <span style="width: 60%;">{{ opportunity.saleName }}</span>
        </div>
      </div>
      <el-table :data="tableData" :span-method="objectSpanMethod" border
        style="margin-top: 20px;table-layout: fixed;width: 100%;">
        <el-table-column prop="totalAmount" label="北光报价总金额" :min-width="80">
        </el-table-column>
        <el-table-column prop="quotedAmount" label="北光报价金额" :min-width="80">
        </el-table-column>

        <el-table-column prop="quotationItem" label="外部报价名称" :min-width="80">
        </el-table-column>
        <el-table-column prop="externalQuote" label="外部报价" :min-width="80">
        </el-table-column>

        <el-table-column prop="expectedIncome" label="预计税后收入" :min-width="80">
        </el-table-column>
        <el-table-column prop="totalExpectedIncome" label="预计税后总收入" :min-width="80">
        </el-table-column>

        <el-table-column prop="projectCostName" label="项目名称" :min-width="80">
        </el-table-column>
        <el-table-column prop="projectCost" label="项目成本" :min-width="80">
        </el-table-column>

        <el-table-column prop="expectedCost" label="预计成本" :min-width="80">
        </el-table-column>
        <el-table-column prop="totalProfit" label="预计利润" :min-width="80">
        </el-table-column>
        <el-table-column prop="profitMargin" label="成本利润率" :min-width="80">
        </el-table-column>
        <el-table-column prop="totalProfitMargin" label="总成本利润率" :min-width="80">
        </el-table-column>
      </el-table>

      <!-- 待签约审批 && 签约审批未通过 -->
      <h4 v-if="['5', '6'].includes(opportunity.loseType)" style="margin: 20px 10px 10px;">
        签约记录:
      </h4>
      <div v-if="['5', '6'].includes(opportunity.loseType)" class="follow-up">
        <div style="margin: 0 auto 20px;">
          <div>
            <span>合同类型：</span>
            <span>{{ opportunity.contractType === '1' ? '欣象代理' : (opportunity.contractType === '2' ? '北光直签' : '-')
            }}</span>
          </div>
        </div>
        <div style="margin: 0 auto 20px;">
          <span>申请签约理由：</span>
          <span>{{ opportunity.signDesc || '-' }}</span>
        </div>
        <div v-if="opportunity.loseType === '6'" style="margin: 0 auto 20px;">
          <span>申请签约是否批准：</span>
          <el-radio-group v-model="isApproval" disabled>
            <el-radio :label="true">批准</el-radio>
            <el-radio :label="false">拒绝</el-radio>
          </el-radio-group>
        </div>
        <div>
          <span>批准理由：</span>
          <span>{{ opportunity.signAuditDesc || '-' }}</span>
        </div>
      </div>

      <!-- 报价审批未通过 && 签约审批未通过 记录-->
      <h4 v-if="['3', '6'].includes(opportunity.loseType)" style="margin: 20px 10px 10px;">
        审批记录:
      </h4>
      <div v-if="['3', '6'].includes(opportunity.loseType)" class="modal-content">
        <div class="modal-item">
          <span style="width: 15%;">时间：</span>
          <span style="width: 85%;">
            {{ opportunity.loseType === '3' ? (opportunity.saleAuditTime || '-') : (opportunity.loseType === '6' ?
              (opportunity.signAuditTime || '-') : '-') }}
          </span>
        </div>
        <div class="modal-item">
          <span style="width: 15%;">审批人员：</span>
          <span style="width: 85%;">
            {{ opportunity.loseType === '3' ? (opportunity.saleAuditPersonName || '-') : (opportunity.loseType === '6' ?
              (opportunity.signAuditPersonName || '-') : '-') }}
          </span>
        </div>
        <div class="modal-item">
          <span style="width: 15%;">理由：</span>
          <span style="width: 85%;">
            {{ opportunity.loseType === '3' ? (opportunity.saleAuditDesc || '-') : (opportunity.loseType === '6' ?
              (opportunity.signAuditDesc || '-') : '-') }}
          </span>
        </div>
      </div>

      <!-- 丢单审批 -->
      <div style="margin: 30px 10px 10px;">
        <h4 style="margin-top: -5px;">项目概要介绍:</h4>
        <div style="margin-left: 20px;">
          {{ opportunity.introduce || '-' }}
        </div>
        <div style="margin:20px auto;">
          <h4>丢单原因:</h4>
          <div style="margin-left: 20px;">
            {{ opportunity.reasonDesc || '-' }}
          </div>
        </div>
        <div style="margin:20px auto;">
          <h4>是否批准：</h4>
          <el-radio-group v-model="isApproval" style="margin-left: 20px;">
            <el-radio :label="true">批准</el-radio>
            <el-radio :label="false">拒绝</el-radio>
          </el-radio-group>
        </div>
        <div>
          <h4 style="margin-bottom:10px"><span style="color:red">*</span>审批说明：</h4>
          <el-input type="textarea" v-model="auditDesc" placeholder="请输入审批说明" style="margin-left: 20px;" />
        </div>
      </div>
    </div>
    <span slot="footer">
      <el-button @click="handleCancel">取消</el-button>
      <el-button type="primary" @click="handleOk">确认</el-button>
    </span>
  </el-dialog>
</template>
<script>
import { fetchLoseInfo, fetchLoseApproval } from '@/api/statement'
export default {
  props: {
    opportunity: {
      type: Object,
      required: true,
    },
    dialogVisible: {
      type: Boolean,
      required: true,
    },
  },
  watch: {
    dialogVisible: {
      async handler(newVal) {
        if (newVal) {
          const params = {
            opportunitiesId: this.opportunity.id,
          };
          this.loading = true;

          try {
            let resData = null;

            if (['2', '3', '4'].includes(this.opportunity.loseType)) {
              const response = await fetchLoseInfo('presale', params);
              resData = response.data;
            } else if (['5', '6'].includes(this.opportunity.loseType)) {
              const response = await fetchLoseInfo('sign', params);
              resData = response.data;
            }

            if (resData) {
              this.tableData = [
                {
                  key: 1,
                  quotationItem: '软件',
                  totalAmount: parseFloat(resData.northAmount || 0).toFixed(2),
                  quotedAmount: parseFloat(resData.northQuoteAmount || 0).toFixed(2),
                  externalQuote: parseFloat(resData.softWareExtQuote || 0).toFixed(2),
                  expectedIncome: parseFloat(resData.softWareExtIncome || 0).toFixed(2),
                  totalExpectedIncome: parseFloat(resData.totalExtIncome || 0).toFixed(2),
                  projectCostName: '人工成本',
                  projectCost: parseFloat(resData.laborPrice || 0).toFixed(2),
                  expectedCost: parseFloat(resData.totalCost || 0).toFixed(2),
                  totalProfit: parseFloat(resData.totalProfit || 0).toFixed(2),
                  profitMargin: (resData.costProfitRate * 100).toFixed(2) + '%', // 格式化为百分比
                  totalProfitMargin: (resData.totalCostProfitRate * 100).toFixed(2) + '%', // 格式化为百分比
                },
                {
                  key: 2,
                  quotationItem: '产品',
                  totalAmount: parseFloat(resData.northAmount || 0).toFixed(2),
                  quotedAmount: parseFloat(resData.northQuoteAmount || 0).toFixed(2),
                  externalQuote: parseFloat(resData.prodExtQuote || 0).toFixed(2),
                  expectedIncome: parseFloat(resData.prodExtIncome || 0).toFixed(2),
                  totalExpectedIncome: parseFloat(resData.totalExtIncome || 0).toFixed(2),
                  projectCostName: '产品成本',
                  projectCost: parseFloat(resData.prodPrice || 0).toFixed(2),
                  expectedCost: parseFloat(resData.totalCost || 0).toFixed(2),
                  totalProfit: parseFloat(resData.totalProfit || 0).toFixed(2),
                  profitMargin: (resData.costProfitRate * 100).toFixed(2) + '%',
                  totalProfitMargin: (resData.totalCostProfitRate * 100).toFixed(2) + '%',
                },
                {
                  key: 3,
                  quotationItem: '硬件-自研',
                  totalAmount: parseFloat(resData.northAmount).toFixed(2),
                  quotedAmount: parseFloat(resData.northQuoteAmount).toFixed(2),
                  externalQuote: parseFloat(resData.selfExtQuote).toFixed(2),
                  expectedIncome: parseFloat(resData.selfExtIncome).toFixed(2),
                  totalExpectedIncome: parseFloat(resData.totalExtIncome).toFixed(2),
                  projectCostName: '硬件成本-自研',
                  projectCost: parseFloat(resData.selfPrice).toFixed(2),
                  expectedCost: parseFloat(resData.totalCost).toFixed(2),
                  totalProfit: parseFloat(resData.totalProfit).toFixed(2),
                  profitMargin: (resData.costProfitRate * 100).toFixed(2) + '%',
                  totalProfitMargin: (resData.totalCostProfitRate * 100).toFixed(2) + '%',
                },
                {
                  key: 4,
                  quotationItem: '实施',
                  totalAmount: parseFloat(resData.northAmount).toFixed(2),
                  quotedAmount: parseFloat(resData.northQuoteAmount).toFixed(2),
                  externalQuote: parseFloat(resData.impExtQuote).toFixed(2),
                  expectedIncome: parseFloat(resData.impExtIncome).toFixed(2),
                  totalExpectedIncome: parseFloat(resData.totalExtIncome).toFixed(2),
                  projectCostName: '实施成本',
                  projectCost: parseFloat(resData.impPrice).toFixed(2),
                  expectedCost: parseFloat(resData.totalCost).toFixed(2),
                  totalProfit: parseFloat(resData.totalProfit).toFixed(2),
                  profitMargin: (resData.costProfitRate * 100).toFixed(2) + '%',
                  totalProfitMargin: (resData.totalCostProfitRate * 100).toFixed(2) + '%',
                },
                {
                  key: 5,
                  quotationItem: '其他',
                  totalAmount: parseFloat(resData.northAmount).toFixed(2),
                  quotedAmount: parseFloat(resData.northQuoteAmount).toFixed(2),
                  externalQuote: parseFloat(resData.otherExtQuote).toFixed(2),
                  expectedIncome: parseFloat(resData.otherExtIncome).toFixed(2),
                  totalExpectedIncome: parseFloat(resData.totalExtIncome).toFixed(2),
                  projectCostName: '其他成本',
                  projectCost: parseFloat(resData.otherPrice).toFixed(2),
                  expectedCost: parseFloat(resData.totalCost).toFixed(2),
                  totalProfit: parseFloat(resData.totalProfit).toFixed(2),
                  profitMargin: (resData.costProfitRate * 100).toFixed(2) + '%',
                  totalProfitMargin: (resData.totalCostProfitRate * 100).toFixed(2) + '%',
                },
                {
                  key: 6,
                  quotationItem: '硬件采购',
                  totalAmount: parseFloat(resData.northAmount).toFixed(2),
                  quotedAmount: parseFloat(resData.extExtQuote).toFixed(2),
                  externalQuote: parseFloat(resData.extExtQuote).toFixed(2),
                  expectedIncome: parseFloat(resData.extExtIncome).toFixed(2),
                  totalExpectedIncome: parseFloat(resData.extExtIncome).toFixed(2),
                  projectCostName: '硬件-采购',
                  projectCost: parseFloat(resData.extPrice).toFixed(2),
                  expectedCost: parseFloat(resData.extPrice).toFixed(2),
                  totalProfit: parseFloat(resData.extProfit).toFixed(2),
                  profitMargin: (resData.costProfitRate * 100).toFixed(2) + '%',
                  totalProfitMargin: (resData.totalCostProfitRate * 100).toFixed(2) + '%',
                },
              ];
            }
          } catch (error) {
            this.$message.error('获取数据失败');
          } finally {
            this.loading = false;
          }
        }
      },
      immediate: true, // 立即触发一次
    },
  },
  data() {
    return {
      tableData: [{ key: 1, quotationItem: '软件', totalAmount: 0, quotedAmount: 0, externalQuote: 0, expectedIncome: 0, totalExpectedIncome: 0, projectCostName: '人工成本', projectCost: 0, expectedCost: 0, totalProfit: 0, profitMargin: 0, totalProfitMargin: 0 },
      { key: 2, quotationItem: '产品', totalAmount: 0, quotedAmount: 0, externalQuote: 0, expectedIncome: 0, totalExpectedIncome: 0, projectCostName: '产品成本', projectCost: 0, expectedCost: 0, totalProfit: 0, profitMargin: 0, totalProfitMargin: 0 },
      { key: 3, quotationItem: '硬件-自研', totalAmount: 0, quotedAmount: 0, externalQuote: 0, expectedIncome: 0, totalExpectedIncome: 0, projectCostName: '硬件成本-自研', projectCost: 0, expectedCost: 0, totalProfit: 0, profitMargin: 0, totalProfitMargin: 0 },
      { key: 4, quotationItem: '实施', totalAmount: 0, quotedAmount: 0, externalQuote: 0, expectedIncome: 0, totalExpectedIncome: 0, projectCostName: '实施成本', projectCost: 0, expectedCost: 0, totalProfit: 0, profitMargin: 0, totalProfitMargin: 0 },
      { key: 5, quotationItem: '其他', totalAmount: 0, quotedAmount: 0, externalQuote: 0, expectedIncome: 0, totalExpectedIncome: 0, projectCostName: '其他成本', projectCost: 0, expectedCost: 0, totalProfit: 0, profitMargin: 0, totalProfitMargin: 0 },
      { key: 6, quotationItem: '硬件采购', totalAmount: 0, quotedAmount: 0, externalQuote: 0, expectedIncome: 0, totalExpectedIncome: 0, projectCostName: '硬件-采购', projectCost: 0, expectedCost: 0, totalProfit: 0, profitMargin: 0, totalProfitMargin: 0 },],
      isApproval: true,
      auditDesc: '',
    }
  },
  methods: {
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      // 合并第一列为单个单元格
      if (columnIndex === 0 || columnIndex === 11) {
        if (rowIndex === 0) {
          return {
            rowspan: this.tableData.length, // 合并整个数据行
            colspan: 1
          };
        } else {
          return {
            rowspan: 0, // 不显示其他行的第一列
            colspan: 0
          };
        }
      }
      if (columnIndex === 1 || columnIndex === 5 || columnIndex === 8 || columnIndex === 9 || columnIndex === 10) { // 这里的 1 是指第二列
        if (rowIndex === 0) {
          return {
            rowspan: 5, // 合并前五行
            colspan: 1
          };
        } else if (rowIndex < 5) {
          return {
            rowspan: 0,
            colspan: 0 // 不显示这些行
          };
        }
      }
      return {
        rowspan: 1,
        colspan: 1
      };
    },
    // 合并外部报价的标题
    quoteSpanMethod({ row, column, rowIndex, columnIndex }) {
      // 如果是外部报价的标题（第一行）
      if (rowIndex === 0) {
        // 确定表头的列索引
        if (columnIndex === 2) {
          return { rowspan: 2, colspan: 2 }; // 合并外部报价为一列，两个子列
        }
        if (columnIndex === 3) {
          return { rowspan: 0, colspan: 0 }; // 隐藏quotationItem的下方单元格
        }
        if (columnIndex === 4) {
          return { rowspan: 0, colspan: 0 }; // 隐藏externalQuote的下方单元格
        }
      }
      return { rowspan: 1, colspan: 1 };
    },
    projectCostSpanMethod({ row, column }) {
      console.log('12345678', column)
      const index = column.label === "项目成本" ? 0 : -1;
      return [1, index < 0 ? 1 : (column.property === "projectCostName" ? 2 : 0)];
    },
    handleCancel() {
      this.$emit('update:dialogVisible', false);
    },

    async handleOk() {
      if (this.auditDesc === '') {
        this.$message.error('审批说明是必填项！');
        return; // 结束函数，确保不继续执行
      }
      try {
        const url = this.isApproval ? 'loseApproval' : 'loseReject';
        const params = {
          auditDesc: this.auditDesc,
          opportunitiesId: this.opportunity.id,
        };
        const response = await fetchLoseApproval(url, params);
        if (response && response.code === 200) {
          if (this.isApproval) {
            this.$message.success('丢单审批通过');
          } else {
            this.$message.success('丢单审批拒绝');
          }
        }
      } catch (error) {
        console.error('操作失败:', error);
      } finally {
        this.isApproval = true;
        this.auditDesc = '';
        this.$emit('update:dialogVisible', false);
      }
    },
  }
}
</script>
<style scoped>
.table-container {
  width: 100%;
  display: flex;
  flex-direction: column;
}

.el-table {
  flex: 1;
  background-color: #f0f5ff;
  overflow: auto;
}

.el-btn {
  margin-right: 10px;
  z-index: 999;
}

/* 防止弹框出现横向滚动条 */
.el-dialog__body {
  /* overflow-x: hidden; */
}

.table-style>>>.el-table_2_column_19 {
  text-align: center;
}

.table-style>>>.el-table_2_column_24 {
  text-align: center;
}

.modal-content {
  padding: 0 10px;
  display: flex;
  flex-wrap: wrap;
}

.modal-flex {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}

.modal-item {
  flex: 1 1 45%;
  box-sizing: border-box;
  margin-bottom: 10px;
  display: flex;
  align-items: flex-start;
  padding-inline: 30px;
}

.follow-up {
  margin-top: 20px;
  margin-left: 30px;
}
</style>
