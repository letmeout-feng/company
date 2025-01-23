<template>
  <el-dialog :title="'丢单审批'" :visible.sync="dialogVisible" :before-close="handleCancel" width="60%"
    :close-on-click-modal="false">
    <div class="ant-layout-content">
      <el-card>
        <!-- 详细报价 -->
        <div class="detail" v-if="opportunity.type === 'COST'">
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
          <el-table :data="data" :pagination="false" :border="true" class="full-width-table" show-summary>
            <template slot-scope="{ row }">
              <el-table-column prop="item" label="项目" width="30%" />
              <el-table-column prop="amount" label="金额（元）" width="30%" />
            </template>
            <template slot="footer">
              <el-table-column label="项目成本合计（元）" />
              <el-table-column :label="totalAmount" />
            </template>
          </el-table>
        </div>

        <!-- 粗略报价 -->
        <div class="rough" v-if="opportunity.type === 'ROUGH'">
          <div style="margin: 20px 0;">
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
            <el-table :data="roughdata" :pagination="false" :border="true" class="full-width-table" show-summary :summary-method="getSummary">
              <template slot-scope="{ row }">
                <el-table-column prop="item" label="类别" width="40%" />
                <el-table-column prop="amount" label="粗略报价" width="30%">
                </el-table-column>
                <el-table-column prop="valuationDesc" label="报价说明" width="30%">
                </el-table-column>
              </template>
              <template slot="footer">
                <el-table-column label="项目成本合计（元）" />
                <el-table-column :label="totalRoughAmount" />
              </template>
            </el-table>
          </div>
          <div class="modal-item" style="margin: 30px 10px 10px;" v-if="opportunity.type === 'ROUGH' || opportunity.type === 'COST'">
            <span style="margin-right: 15px;">报价说明：</span>
            <span style="width:70%">
              {{ opportunity.quoteDesc ? opportunity.quoteDesc : '-' }}
            </span>
          </div>
        </div>

        <!-- 占比 -->
        <div style="margin-top:10px" v-if="deptData.length > 0">
          <div style="width:100%">产品项目组占比：</div>
          <div class="modal-item"
            style="display: flex; align-items: flex-start; padding-inline:20px; padding-block: 10px;">
            <div style="width:100%">
              <div v-for="(dept, index) in deptData" :key="index"
                style="display: flex; align-items: center; margin-bottom: 10px;">
                <span style="flex: 1;">{{ dept.deptName }}</span>
                <span style="flex: 1;">{{ dept.userNames }}</span>
                <span style="flex: 1;">
                  <el-input-number v-model="percentages[index]" :controls="false" :step="0.01" :precision="2" disabled />
                  %
                </span>
              </div>
            </div>
          </div>
        </div>
        <!-- 无法报价 -->
        <div class="incapable" v-if="opportunity.type === 'INCAPABLE'">
          <div class="modal-content">
            <div class="modal-flex">
              <div class="modal-item">
                <span class="modal-item-son modal-item-son1">商机名称：</span>
                <span class="modal-item-son modal-item-son2">{{ opportunity.name }}</span>
              </div>
              <div class="modal-item">
                <span class="modal-item-son modal-item-son1">客户名称：</span>
                <span class="modal-item-son modal-item-son2">{{ opportunity.customersName }}</span>
              </div>
              <div class="modal-item">
                <span class="modal-item-son modal-item-son1">所属销售：</span>
                <span class="modal-item-son modal-item-son2">{{ opportunity.saleName }}</span>
              </div>
              <div class="modal-item">
                <span class="modal-item-son modal-item-son1">产品类别：</span>
                <span class="modal-item-son modal-item-son2">{{ opportunity.category }}</span>
              </div>
              <div class="modal-item">
                <span class="modal-item-son modal-item-son1">所属售前：</span>
                <span class="modal-item-son modal-item-son2">{{ opportunity.preSaleName }}</span>
              </div>
              <div class="modal-item">
                <span class="modal-item-son modal-item-son1">报价版本：</span>
                <span class="modal-item-son modal-item-son2">{{ opportunity.currentVersion }}</span>
              </div>
              <div class="modal-item"
                v-if="props.opportunity.type === 'INCAPABLE' && (props.opportunity.loseType === '2' || props.opportunity.loseType === '3' || props.opportunity.loseType === '4')">
                <span class="modal-item-son modal-item-son1">报价金额：</span>
                <span class="modal-item-son modal-item-son2">-</span>
              </div>
              <div class="modal-item"
                v-if="props.opportunity.type === 'INCAPABLE' && (props.opportunity.loseType === '2' || props.opportunity.loseType === '3' || props.opportunity.loseType === '4')">
                <span class="modal-item-son modal-item-son1">签约类型：</span>
                <span class="modal-item-son modal-item-son2">{{ signType == '1' ? '欣象代理' : signType == '2' ?
                  '北光直签' : ''
                }}</span>
              </div>
              <div class="modal-item"
                v-if="signType == '1' && (props.opportunity.loseType === '2' || props.opportunity.loseType === '3' || props.opportunity.loseType === '4')">
                <span class="modal-item-son modal-item-son1">欣象报价金额：</span>
                <span class="modal-item-son modal-item-son2">{{ xxUnableQuoteAmount }}</span>
              </div>
              <div class="modal-item"
                v-if="props.opportunity.type === 'INCAPABLE' && (props.opportunity.loseType === '2' || props.opportunity.loseType === '3' || props.opportunity.loseType === '4')">
                <span class="modal-item-son modal-item-son1">北光报价金额：</span>
                <span class="modal-item-son modal-item-son2">{{ unableQuoteAmount }}</span>
              </div>
              <div class="modal-item"
                v-if="props.opportunity.type === 'INCAPABLE' && (props.opportunity.loseType === '2' || props.opportunity.loseType === '3' || props.opportunity.loseType === '4')">
                <span class="modal-item-son modal-item-son1">整体利润率：</span>
                <span class="modal-item-son modal-item-son2">-</span>
              </div>
            </div>

            <div class="modal-item" style="display: flex; align-items: flex-start;"
              :style="props.updateQuote ? 'margin-top: 8px;' : ''">
              <span class="modal-item-son" style="width:13%">无法报价原因：</span>
              <span style="width:60%">
                <span>{{ opportunity.quoteDesc || '-' }}</span>
              </span>
            </div>
          </div>
        </div>
        <!-- 丢单审批 -->
        <div style="margin: 30px 10px 10px;">
          <div>
            <h4 style="margin-top: -5px;">项目概要介绍:</h4>
            <div style="margin-left: 20px;">
              {{ opportunity.introduce || '-' }}
            </div>
          </div>
          <div style="margin:20px auto;">
            <h4 style="">丢单原因:</h4>
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
      </el-card>
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
      required: false
    },
    isOrderLoss: {
      type: Boolean,
      required: false
    },
    dialogVisible: {
      type: Boolean,
      required: true
    }
  },
  computed: {
    totalAmount() {
      return this.data.reduce((sum, item) => sum + Number(item.amount), 0).toFixed(2);
    },
    totalRoughAmount() {
      return this.roughdata.reduce((sum, item) => sum + Number(item.amount), 0).toFixed(2);
    }
  },
  watch: {
    dialogVisible(newVal) {
      console.log(newVal)
      if (newVal) {
        const params = {
          'opportunitiesId': this.opportunity.id
        };
        if (this.opportunity.type === 'COST') {
          this.getQuoteDetail('detail', params);
        }
        if (this.opportunity.type === 'ROUGH') {
          this.getQuoteDetail('rough', params);
        }
        if (this.opportunity.type === 'INCAPABLE' && (this.opportunity.loseType === '2' || this.opportunity.loseType === '3' || this.opportunity.loseType === '4')) {
          this.getQuoteDetail('presale', params);
        }
        if (this.opportunity.type === 'INCAPABLE' && (this.opportunity.loseType === '1')) {
          this.loading = false;
        }
      }
    }
  },
  data() {
    return {
      loading: true,
      data: [
        { key: 1, item: '售前支持小计（元）', amount: 0.00 },
        { key: 2, item: '定制开发小计（元）', amount: 0.00 },
        { key: 3, item: '产品平台小计（元）', amount: 0.00, highlight: true },
        { key: 4, item: '硬件小计（元） - 自研', amount: 0.00 },
        { key: 5, item: '硬件小计（元） - 外采', amount: 0.00 },
        { key: 6, item: '实施小计（元）', amount: 0.00 },
        { key: 7, item: '其他小计（元）', amount: 0.00 }
      ],
      roughdata: [
        { key: 1, item: '售前支持小计（元）', amount: '', valuationDesc: '' },
        { key: 2, item: '定制开发小计（元）', amount: '', valuationDesc: '' },
        { key: 3, item: '产品平台小计（元）', amount: '', valuationDesc: '' },
        { key: 4, item: '硬件小计（元） - 自研', amount: '', valuationDesc: '' },
        { key: 5, item: '硬件小计（元） - 外采', amount: '', valuationDesc: '' },
        { key: 6, item: '实施小计（元）', amount: '', valuationDesc: '' },
        { key: 7, item: '其他小计（元）', amount: '', valuationDesc: '' },
      ],
      deptData: [],
      percentages: [],
      auditDesc: '',
      isApproval: true,
      signType: '',
      unableQuoteAmount: null,
      xxUnableQuoteAmount: null
    }
  },
  methods: {
    async getQuoteDetail(url, params) {
      try {
        const response = await fetchLoseInfo(url, params);
        if (response && response.code === 200) {
          const detailData = response.data;
          if (this.opportunity.type === 'COST') {
            this.data.forEach(item => {
              switch (item.key) {
                case 1:
                  item.amount = parseFloat(detailData.quoteOpportunitiesSupportVO?.totalCost || 0.00).toFixed(2);
                  break;
                case 2:
                  item.amount = parseFloat(detailData.quoteOpportunitiesCustomizableVo?.totalSoftwareCosts || 0.00).toFixed(2);
                  break;
                case 3:
                  item.amount = parseFloat(detailData.quoteOpportunitiesProductVO?.totalCost || 0.00).toFixed(2);
                  break;
                case 4:
                  item.amount = parseFloat(detailData.quoteOpportunitiesSelfVO?.totalCost || 0.00).toFixed(2);
                  break;
                case 5:
                  item.amount = parseFloat(detailData.quoteOpportunitiesExternalVO?.totalEstimatedlCost || 0.00).toFixed(2);
                  break;
                case 6:
                  item.amount = parseFloat(detailData.quoteOpportunitiesImplement?.totalCost || 0.00).toFixed(2);
                  break;
                case 7:
                  item.amount = parseFloat(detailData.quoteOpportunitiesOtherVO?.totalCost || 0.00).toFixed(2);
                  break;
                default:
                  item.amount = 0.00;
              }
            });
            this.deptData = detailData.radioList;
            this.percentages = this.deptData.map(dept => dept.radio);
          }
          if (this.opportunity.type === 'ROUGH') {
            this.roughdata = [
              { key: 1, item: '售前支持小计（元）', amount: detailData.quoteOpportunitiesRoughDetails[0].supportAmount, valuationDesc: detailData.quoteOpportunitiesRoughDetails[0].supportDesc },
              { key: 2, item: '定制开发小计（元）', amount: detailData.quoteOpportunitiesRoughDetails[0].customAmount, valuationDesc: detailData.quoteOpportunitiesRoughDetails[0].customDesc },
              { key: 3, item: '产品平台小计（元）', amount: detailData.quoteOpportunitiesRoughDetails[0].productAmount, valuationDesc: detailData.quoteOpportunitiesRoughDetails[0].productDesc },
              { key: 4, item: '硬件小计（元） - 自研', amount: detailData.quoteOpportunitiesRoughDetails[0].selfAmount, valuationDesc: detailData.quoteOpportunitiesRoughDetails[0].selfDesc },
              { key: 5, item: '硬件小计（元） - 外采', amount: detailData.quoteOpportunitiesRoughDetails[0].externalAmount, valuationDesc: detailData.quoteOpportunitiesRoughDetails[0].externalDesc },
              { key: 6, item: '实施小计（元）', amount: detailData.quoteOpportunitiesRoughDetails[0].implementAmount, valuationDesc: detailData.quoteOpportunitiesRoughDetails[0].implementDesc },
              { key: 7, item: '其他小计（元）', amount: detailData.quoteOpportunitiesRoughDetails[0].otherAmount, valuationDesc: detailData.quoteOpportunitiesRoughDetails[0].otherDesc }
            ];
            this.deptData = detailData.radioList;
            this.percentages = this.deptData.map(dept => dept.radio);
          }
          if (this.opportunity.type === 'INCAPABLE' && (this.opportunity.loseType === '2' || this.opportunity.loseType === '3' || this.opportunity.loseType === '4')) {
            this.signType = detailData.signType;
            this.unableQuoteAmount = detailData.unableQuoteAmount;
            this.xxUnableQuoteAmount = detailData.xxUnableQuoteAmount;
          }
        } else {
          console.error('获取报价详情失败:', response.error);
        }
      } catch (error) {
        console.error('Error fetching cost quote detail:', error);
      } finally {
        this.loading = false;
      }
    },
    getSummary(param) {
    const totals = { amount: 0 }; // 跟踪总和
    param.data.forEach(item => {
      totals.amount += Number(item.amount) || 0;
    });

    return [
      '合计', // 类别列没有值
      totals.amount, // 总计在'粗略报价'列
      '' // 报价说明列没有汇总
    ];
  }  ,
    handleCancel() {
      this.isApproval = true;
      this.auditDesc = '';
      this.deptData = [];
      this.percentages = [];
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
        this.deptData = [];
        this.percentages = [];
        this.$emit('update:dialogVisible', false);
      }
    },
  }
}
</script>
<style scoped>
.ant-layout-content {
  height: 100%;
  overflow: auto !important;
  margin-top: 20px;
}

.layout {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.full-width-table>>>.el-table__header {
  width: 100% !important;
}

.full-width-table>>>.el-table__body {
  width: 100% !important;
}

.full-width-table>>>.el-table__footer {
  width: 100% !important;
}

.total {
  margin-top: 30px;
  font-weight: bold;
}

table {
  width: 100%;
  border-collapse: collapse;
  border: 1px solid #000;
}

td,
th {
  border: 1px solid #000;
  text-align: left;
  height: 45px;
}

.label {
  width: 30%;
  text-align: center;
  line-height: 45px;
}

.input {
  width: 60%;
}

.input input,
.input textarea {
  width: 98%;
  border: none;
  outline: none;
}

.ant-layout-content>>>.ant-table-summary>.ant-table-cell {
  background: #f1f1f1;
  border: 1px solid #faf5f5;
  padding: 16px;
  font-weight: 600;
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
}

.modal-item-son {
  display: inline-block;
}

.modal-item-son1 {
  width: 25%;
}

.modal-item-son2 {
  width: 60%;
}


@media screen and (max-width: 768px) {
  .modal-item {
    flex: 1 1 100%;
  }
}
</style>

