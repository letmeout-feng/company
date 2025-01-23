<template>
    <a-modal title="丢单审批" :open="isQuotationApprovalVisible" @cancel="handleCancel" @ok="handleOk" :destroyOnClose="true"
        width="70%">
        <a-spin v-if="loading" />
        <div class="ant-body" v-else>
            <div>
                <a-table :columns="columns" :data-source="data" bordered :pagination="false" size="small"
                    style="width: 100%;">
                    <template #title>
                        <div class="modal-content"
                            style="border: 1px solid #f0f0f0; background: #fafafa; padding: 20px 10px 10px;">
                            <div class="modal-item">
                                <span style="width: 20%;">项目名称：</span>
                                <span style="width: 60%;">{{ opportunity.name }}</span>
                            </div>
                            <div class="modal-item">
                                <span style="width: 20%;">销售人员：</span>
                                <span style="width: 60%; ">{{ opportunity.saleName }}</span>
                            </div>
                        </div>
                    </template>
                    <template v-slot:bodyCell="{ column, record, index }">
                        <span v-if="column.dataIndex === 'profitMargin'" :class="{
                            'highlight': isProfitRateLow && record.quotationItem !== '硬件采购'
                        }">
                            {{ record.profitMargin ? (parseFloat(record.profitMargin) * 100).toFixed(2) + '%' : '-' }}
                        </span>
                        <span v-else-if="column.dataIndex === 'totalProfitMargin'">
                            {{ record.totalProfitMargin ? (parseFloat(record.totalProfitMargin) * 100).toFixed(2) + '%' :
                                '-' }}
                        </span>
                        <span v-else-if="column.dataIndex === 'externalQuote'">
                            <span v-if="props.isPriceSheet || props.isReContractApplication || props.isSignDetail">
                                <a-input-number v-model:value="record[column.dataIndex]" :placeholder="'请输入' + column.title"
                                    @input="updateExternalQuote(index, record.externalQuote)" :step="0.01" :controls="false"
                                    :disabled="props.isSignDetail" />
                            </span>
                            <span v-else>{{ record[column.dataIndex] || '-' }}</span>
                        </span>
                        <span v-else>
                            {{ record[column.dataIndex] }}
                        </span>
                    </template>
                </a-table>
            </div>
            <!-- 待签约审批 && 签约审批未通过 -->
            <h4 style="margin: 20px 10px 10px; display: block;" v-if="['5', '6'].includes(opportunity.loseType)">签约记录:</h4>
            <div class="follow-up" v-if="['5', '6'].includes(opportunity.loseType)">
                <div style="margin: 0 auto 20px;">
                    <div>
                        <span>合同类型：</span>
                        <span>{{ opportunity.contractType === '1' ? '欣象代理' : (opportunity.contractType === '2' ? '北光直签' :
                            '-') }}</span>
                    </div>
                </div>
                <div style="margin: 0 auto 20px;">
                    <span style="display: inline-block">申请签约理由：</span>
                    <span>{{ opportunity.signDesc || '-' }}</span>
                </div>
                <!-- 签约审批未通过 -->
                <div v-if="opportunity.loseType === '6'">
                    <div style="margin: 0 auto 20px;">
                        <span>申请签约是否批准：</span>
                        <a-radio-group disabled :value="false">
                            <a-radio :value="true">批准</a-radio>
                            <a-radio :value="false" checked>拒绝</a-radio>
                        </a-radio-group>
                    </div>
                    <div>
                        <span>批准理由：</span>
                        <span> {{ opportunity.signAuditDesc || '-' }}</span>

                    </div>
                </div>
            </div>
            <!-- 报价审批未通过 && 签约审批未通过 记录-->
            <h4 style="margin: 20px 10px 10px; display: block;" v-if="['3', '6'].includes(opportunity.loseType)">审批记录:</h4>
            <div class="modal-content" v-if="['3', '6'].includes(opportunity.loseType)">
                <div class="modal-item">
                    <span style="width: 15%;">时间：</span>
                    <span style="width: 85%;">
                        {{ opportunity.loseType === '3' ? (opportunity.saleAuditTime || '-') :
                            (opportunity.loseType === '6' ? (opportunity.signAuditTime || '-') : '-') }}
                    </span>
                </div>
                <div class="modal-item">
                    <span style="width: 15%;">审批人员：</span>
                    <span style="width: 85%;">
                        {{ opportunity.loseType === '3' ? (opportunity.saleAuditPersonName || '-') :
                            (opportunity.loseType === '6' ? (opportunity.signAuditPersonName || '-') : '-') }}
                    </span>
                </div>
                <div class="modal-item">
                    <span style="width: 15%;">理由：</span>
                    <span style="width: 85%;">
                        {{ opportunity.loseType === '3' ? (opportunity.saleAuditDesc || '-') :
                            (opportunity.loseType === '6' ? (opportunity.signAuditDesc || '-') : '-') }}
                    </span>
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
                <div>
                    <h4 style="">丢单原因:</h4>
                    <div style="margin-left: 20px;">
                        {{ opportunity.reasonDesc || '-' }}
                    </div>
                </div>
                <div style="margin:20px auto;">
                    <h4>是否批准：</h4>
                    <a-radio-group v-model:value="isApproval" style="margin-left: 20px;">
                        <a-radio :value="true">批准</a-radio>
                        <a-radio :value="false">拒绝</a-radio>
                    </a-radio-group>
                </div>
                <div>
                    <h4 style="margin-bottom:10px"><span style="color:red">*</span>审批说明：</h4>
                    <a-textarea v-model:value="auditDesc" placeholder="请输入审批说明" style="margin-left: 20px;" />
                </div>
            </div>
        </div>
        <template #footer>
            <a-button @click="handleCancel">取消</a-button>
            <a-button type="primary" @click="handleOk">确认</a-button>
        </template>
    </a-modal>
</template>  
<script setup>
import { ref, defineProps, defineEmits, watch } from 'vue';
import { fetchLoseInfo } from '@/api/lose'
import { message } from 'ant-design-vue'

const data = ref([
    { key: 1, quotationItem: '软件', totalAmount: 0, quotedAmount: 0, externalQuote: 0, expectedIncome: 0, totalExpectedIncome: 0, projectCostName: '人工成本', projectCost: 0, expectedCost: 0, totalProfit: 0, profitMargin: 0, totalProfitMargin: 0 },
    { key: 2, quotationItem: '产品', totalAmount: 0, quotedAmount: 0, externalQuote: 0, expectedIncome: 0, totalExpectedIncome: 0, projectCostName: '产品成本', projectCost: 0, expectedCost: 0, totalProfit: 0, profitMargin: 0, totalProfitMargin: 0 },
    { key: 3, quotationItem: '硬件-自研', totalAmount: 0, quotedAmount: 0, externalQuote: 0, expectedIncome: 0, totalExpectedIncome: 0, projectCostName: '硬件成本-自研', projectCost: 0, expectedCost: 0, totalProfit: 0, profitMargin: 0, totalProfitMargin: 0 },
    { key: 4, quotationItem: '实施', totalAmount: 0, quotedAmount: 0, externalQuote: 0, expectedIncome: 0, totalExpectedIncome: 0, projectCostName: '实施成本', projectCost: 0, expectedCost: 0, totalProfit: 0, profitMargin: 0, totalProfitMargin: 0 },
    { key: 5, quotationItem: '其他', totalAmount: 0, quotedAmount: 0, externalQuote: 0, expectedIncome: 0, totalExpectedIncome: 0, projectCostName: '其他成本', projectCost: 0, expectedCost: 0, totalProfit: 0, profitMargin: 0, totalProfitMargin: 0 },
    { key: 6, quotationItem: '硬件采购', totalAmount: 0, quotedAmount: 0, externalQuote: 0, expectedIncome: 0, totalExpectedIncome: 0, projectCostName: '硬件-采购', projectCost: 0, expectedCost: 0, totalProfit: 0, profitMargin: 0, totalProfitMargin: 0 },
],);
const columns = ref([
    {
        title: '北光报价总金额',
        dataIndex: 'totalAmount',
        key: 'totalAmount',
        customCell: (_, index) => {
            if (index === 0) {
                return { rowSpan: 6 };
            }
            return { colSpan: 0 };
        },
        render: (text) => text ? parseFloat(text).toFixed(2) : '-'
    },
    {
        title: '北光报价金额',
        dataIndex: 'quotedAmount',
        key: 'quotedAmount',
        customCell: (_, index) => {
            if (index === 0) {
                return { rowSpan: 5 };
            }
            return index === 5 ? { rowSpan: 1 } : { colSpan: 0 }
        },
        render: (text) => text ? parseFloat(text).toFixed(2) : '-'
    },
    {
        title: '对外报价',
        dataIndex: 'quotationItem',
        colSpan: 2,
    },
    {
        title: '外部报价',
        colSpan: 0,
        dataIndex: 'externalQuote',
        key: 'externalQuote',
        width: '10%'
    },
    { title: '预计税后收入', dataIndex: 'expectedIncome', key: 'expectedIncome' },
    {
        title: '预计税后总收入', dataIndex: 'totalExpectedIncome', key: 'totalExpectedIncome',
        customCell: (_, index) => {
            if (index === 0) {
                return { rowSpan: 5 };  // 合并三行  
            }
            return index === 5 ? { rowSpan: 1 } : { colSpan: 0 }
        },
        render: (text) => text ? parseFloat(text).toFixed(2) : '-'
    },
    {
        title: '项目成本',
        dataIndex: 'projectCostName',
        key: 'projectCostName',
        colSpan: 2,
    },
    {
        title: '项目成本',
        dataIndex: 'projectCost',
        key: 'projectCost',
        colSpan: 0,
        width: '10%',
        render: (text) => text ? parseFloat(text).toFixed(2) : '-'

    },
    {
        title: '预计成本', dataIndex: 'expectedCost', key: 'expectedCost', customCell: (_, index) => {
            if (index === 0) {
                return { rowSpan: 5 };  // 合并三行  
            }
            return index === 5 ? { rowSpan: 1 } : { colSpan: 0 }
        },
    },
    {
        title: '预计利润',
        dataIndex: 'totalProfit',
        key: 'totalProfit',
        customCell: (_, index) => {
            if (index === 0) {
                return { rowSpan: 5 };
            }
            return index === 5 ? { rowSpan: 1 } : { colSpan: 0 }
        },
        render: (text) => text ? parseFloat(text).toFixed(2) : '-'
    },
    {
        title: '成本利润率', dataIndex: 'profitMargin', key: 'profitMargin', customCell: (_, index) => {
            if (index === 0) {
                return { rowSpan: 5 };  // 合并三行  
            }
            return index === 5 ? { rowSpan: 1 } : { colSpan: 0 }
        },
    },
    {
        title: '总成本利润率', dataIndex: 'totalProfitMargin', key: 'totalProfitMargin', customCell: (_, index) => {
            if (index === 0) {
                return { rowSpan: 6 };
            }
            return { colSpan: 0 };
        },
    },
])
const props = defineProps({
    opportunity: {
        type: Object,
        required: true,
    },
    isLossOrder: {
        type: Boolean,
        required: false,
    },
});
const emit = defineEmits([]);

const loading = ref(true);
const auditDesc = ref('')
const isApproval = ref(true)

watch(() => props.isLossOrder, async (newVal) => {
    if (newVal) {
        const params = {
            'opportunitiesId': props.opportunity.id
        }
        try {
            const resData = ref(null)
            if (props.opportunity.loseType === '2' || props.opportunity.loseType === '3' || props.opportunity.loseType === '4') {
                const response = await fetchLoseInfo('presale', params);
                resData.value = response.data
            }
            if (props.opportunity.loseType === '5' || props.opportunity.loseType === '6') {
                const response = await fetchLoseInfo('sign', params);
                resData.value = response.data
            }
            data.value = [
                {
                    key: 1,
                    quotationItem: '软件',
                    totalAmount: parseFloat(resData.value.northAmount).toFixed(2),
                    quotedAmount: parseFloat(resData.value.northQuoteAmount).toFixed(2),
                    externalQuote: parseFloat(resData.value.softWareExtQuote).toFixed(2),
                    expectedIncome: parseFloat(resData.value.softWareExtIncome).toFixed(2),
                    totalExpectedIncome: parseFloat(resData.value.totalExtIncome).toFixed(2),
                    projectCostName: '人工成本',
                    projectCost: parseFloat(resData.value.laborPrice).toFixed(2),
                    expectedCost: parseFloat(resData.value.totalCost).toFixed(2),
                    totalProfit: parseFloat(resData.value.totalProfit).toFixed(2),
                    profitMargin: resData.value.costProfitRate,
                    totalProfitMargin: resData.value.totalCostProfitRate
                },
                {
                    key: 2,
                    quotationItem: '产品',
                    totalAmount: parseFloat(resData.value.northAmount).toFixed(2),
                    quotedAmount: parseFloat(resData.value.northQuoteAmount).toFixed(2),
                    externalQuote: parseFloat(resData.value.prodExtQuote).toFixed(2),
                    expectedIncome: parseFloat(resData.value.prodExtIncome).toFixed(2),
                    totalExpectedIncome: parseFloat(resData.value.totalExtIncome).toFixed(2),
                    projectCostName: '产品成本',
                    projectCost: parseFloat(resData.value.prodPrice).toFixed(2),
                    expectedCost: parseFloat(resData.value.totalCost).toFixed(2),
                    totalProfit: parseFloat(resData.value.totalProfit).toFixed(2),
                    profitMargin: resData.value.costProfitRate,
                    totalProfitMargin: resData.value.totalCostProfitRate
                },
                {
                    key: 3,
                    quotationItem: '硬件-自研',
                    totalAmount: parseFloat(resData.value.northAmount).toFixed(2),
                    quotedAmount: parseFloat(resData.value.northQuoteAmount).toFixed(2),
                    externalQuote: parseFloat(resData.value.selfExtQuote).toFixed(2),
                    expectedIncome: parseFloat(resData.value.selfExtIncome).toFixed(2),
                    totalExpectedIncome: parseFloat(resData.value.totalExtIncome).toFixed(2),
                    projectCostName: '硬件成本-自研',
                    projectCost: parseFloat(resData.value.selfPrice).toFixed(2),
                    expectedCost: parseFloat(resData.value.totalCost).toFixed(2),
                    totalProfit: parseFloat(resData.value.totalProfit).toFixed(2),
                    profitMargin: resData.value.costProfitRate,
                    totalProfitMargin: resData.value.totalCostProfitRate
                },
                {
                    key: 4,
                    quotationItem: '实施',
                    totalAmount: parseFloat(resData.value.northAmount).toFixed(2),
                    quotedAmount: parseFloat(resData.value.northQuoteAmount).toFixed(2),
                    externalQuote: parseFloat(resData.value.impExtQuote).toFixed(2),
                    expectedIncome: parseFloat(resData.value.impExtIncome).toFixed(2),
                    totalExpectedIncome: parseFloat(resData.value.totalExtIncome).toFixed(2),
                    projectCostName: '实施成本',
                    projectCost: parseFloat(resData.value.impPrice).toFixed(2),
                    expectedCost: parseFloat(resData.value.totalCost).toFixed(2),
                    totalProfit: parseFloat(resData.value.totalProfit).toFixed(2),
                    profitMargin: resData.value.costProfitRate,
                    totalProfitMargin: resData.value.totalCostProfitRate
                },
                {
                    key: 5,
                    quotationItem: '其他',
                    totalAmount: parseFloat(resData.value.northAmount).toFixed(2),
                    quotedAmount: parseFloat(resData.value.northQuoteAmount).toFixed(2),
                    externalQuote: parseFloat(resData.value.otherExtQuote).toFixed(2),
                    expectedIncome: parseFloat(resData.value.otherExtIncome).toFixed(2),
                    totalExpectedIncome: parseFloat(resData.value.totalExtIncome).toFixed(2),
                    projectCostName: '其他成本',
                    projectCost: parseFloat(resData.value.otherPrice).toFixed(2),
                    expectedCost: parseFloat(resData.value.totalCost).toFixed(2),
                    totalProfit: parseFloat(resData.value.totalProfit).toFixed(2),
                    profitMargin: resData.value.costProfitRate,
                    totalProfitMargin: resData.value.totalCostProfitRate
                },
                {
                    key: 6,
                    quotationItem: '硬件采购',
                    totalAmount: parseFloat(resData.value.northAmount).toFixed(2),
                    quotedAmount: parseFloat(resData.value.extExtQuote).toFixed(2),
                    externalQuote: parseFloat(resData.value.extExtQuote).toFixed(2),
                    expectedIncome: parseFloat(resData.value.extExtIncome).toFixed(2),
                    totalExpectedIncome: parseFloat(resData.value.extExtIncome).toFixed(2),
                    projectCostName: '硬件-采购',
                    projectCost: parseFloat(resData.value.extPrice).toFixed(2),
                    expectedCost: parseFloat(resData.value.extPrice).toFixed(2),
                    totalProfit: parseFloat(resData.value.extProfit).toFixed(2),
                    profitMargin: resData.value.extProfitRate,
                    totalProfitMargin: resData.value.totalCostProfitRate
                }
            ];
        } catch (error) {
            message.error("获取数据失败");
        } finally {
            loading.value = false;
        }
    }
});
const handleOk = async () => {
    if (auditDesc.value === '') {
        message.error('审批说明是必填项！');
    }
    try {
        const url = isApproval.value ? 'loseApproval' : 'loseReject';
        const params = {
            auditDesc: auditDesc.value,
            opportunitiesId: props.opportunity.id,
        };
        const response = await fetchLoseInfo(url, params);
        if (response && response.code === 200) {
            if (isApproval.value) {
                message.success('丢单审批通过')
            } else {
                message.success('丢单审批拒绝')
            }
        }

    } catch (error) {
        console.error('操作失败:', error);
    } finally {
        isApproval.value = true
        auditDesc.value = ''
        emit('close');
    }
}
const handleCancel = () => {
    isApproval.value = true
    auditDesc.value = ''
    emit('close');
}
</script>  
  
<style scoped>
.modal-content {
    padding: 20px;
    display: flex;
    flex-wrap: wrap;
}

.modal-item {
    margin-bottom: 10px;
    width: 50%;
    margin-bottom: 10px;
    box-sizing: border-box;
    display: flex;
    align-items: flex-start;
}

.ant-body>>>.ant-table-content {
    position: relative;
    z-index: 999;
    overflow-x: auto;
}

.ant-modal-body {
    height: 90%;
    overflow-y: auto;
}

.ant-row {
    display: flex;
    border: 1px solid #f0f0f0;
}

.ant-col {
    padding: 10px;
}

.ant-table-wrapper .ant-table.ant-table-bordered>.ant-table-title {
    border: 1px solid #f0f0f0;
    border-bottom: 0;
    padding: 0px;
}

.follow-up {
    margin-top: 20px;
    margin-left: 10px;
}

.contract-application {
    margin-top: 25px;
}

/* 预警 */
.highlight {
    color: red;
    font-weight: bold;
    background-color: #ffcccc;
}

@media (max-width: 768px) {
    .ant-body>>>.ant-table {
        font-size: 12px;
    }

    .ant-row {
        flex-direction: column;
    }

    .ant-col {
        padding: 5px;
        width: 100%;
    }
}
</style>