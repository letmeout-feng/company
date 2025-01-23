<template>
    <a-modal title="丢单审批" :open="isCostVisible" @cancel="handleCancel" width="60%" @ok="handleOk" :mask-closable="true">
        <a-spin v-if="loading" />
        <div class="ant-layout-content" v-else>
            <a-card>
                <div class="detail" v-if="opportunity.type === 'COST'">
                    <a-table :dataSource="data" :pagination="false" :columns="columns" row-key="key" bordered size="middle">
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
                        <template #bodyCell="{ column, record }">
                            <template v-if="column.dataIndex === 'amount'">
                                {{ record.amount }}
                            </template>
                        </template>
                        <template #summary>
                            <a-table-summary-cell>项目成本合计（元）：</a-table-summary-cell>
                            <a-table-summary-cell>{{ totalAmount }}</a-table-summary-cell>
                        </template>
                    </a-table>
                </div>
                <!-- 粗略报价 -->
                <div class="rough" v-if="opportunity.type === 'ROUGH'">
                    <div style="margin: 20px 0;">
                        <a-table size="small" :dataSource="roughdata" :pagination="false" :columns="roughcolumns"
                            row-key="key" bordered>
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
                            <template #bodyCell="{ column, record }">
                                <template v-if="column.dataIndex === 'amount'">
                                    <a-input-number v-model:value="record[column.dataIndex]" :step="0.01" :precision="2"
                                        style="width: 160px" :controls="false" :bordered="false" disabled></a-input-number>
                                </template>
                                <template v-if="column.dataIndex === 'valuationDesc'">
                                    <a-input v-model:value="record[column.dataIndex]" :bordered="false" disabled></a-input>
                                </template>
                            </template>
                            <template #summary>
                                <a-table-summary-cell>项目成本合计（元）：</a-table-summary-cell>
                                <a-table-summary-cell>{{ totalRoughAmount }}</a-table-summary-cell>
                                <a-table-summary-cell></a-table-summary-cell>
                            </template>
                        </a-table>
                    </div>

                </div>
                <div style="margin: 30px 10px 10px;" v-if="opportunity.type === 'ROUGH' || opportunity.type === 'COST'">
                    <h4 style="margin-right: 15px;">报价说明：</h4>
                    <span style="margin-left: 20px;">
                        {{ opportunity.quoteDesc ? opportunity.quoteDesc : '-' }}
                    </span>
                </div>
                <!-- 占比 -->
                <div style="margin-top:10px" v-if="deptData.length > 0">
                    <div style="width:100%">产品项目组占比：</div>
                    <div class="modal-item"
                        style="display: flex; align-items: flex-start; padding-inline:20px; padding-block: 10px;">
                        <div style="width:100%">
                            <template v-for="(dept, index) in deptData" :key="index">
                                <div style="display: flex; align-items: center; margin-bottom: 10px;">
                                    <span style="flex: 1;">{{ dept.deptName }}</span>
                                    <span style="flex: 1;">{{ dept.userNames }}</span>
                                    <span style="flex: 1;">
                                        <a-input-number v-model:value="percentages[index]" :controls="false" :step='0.01'
                                            :precision="2" disabled />
                                        %
                                    </span>
                                </div>
                            </template>
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
            </a-card>
        </div>
        <template #footer>
            <a-button @click="handleCancel">取消</a-button>
            <a-button type="primary" @click="handleOk">确认</a-button>
        </template>
    </a-modal>
</template>

<script setup>
import { ref, defineEmits, defineProps, computed, watch } from 'vue';
import { fetchLoseInfo } from '@/api/lose'
import { message } from 'ant-design-vue'

const props = defineProps({
    opportunity: {
        type: Object,
        required: false
    },
    isOrderLoss: {
        type: Boolean,
        required: false,
    },
});
const emit = defineEmits([]);

// 粗略报价详情
const roughcolumns = [
    {
        title: '类别',
        dataIndex: 'item',
        key: 'item',
    },
    {
        title: '粗略报价',
        dataIndex: 'amount',
        key: 'amount',
    },
    {
        title: '报价说明',
        dataIndex: 'valuationDesc',
        key: 'valuationDesc',
    }
]
const roughdata = ref([
    { key: 1, item: '售前支持小计（元）', amount: '', valuationDesc: '' },
    { key: 2, item: '定制开发小计（元）', amount: '', valuationDesc: '' },
    { key: 3, item: '产品平台小计（元）', amount: '', highlight: true, valuationDesc: '' },
    { key: 4, item: '硬件小计（元） - 自研', amount: '', valuationDesc: '' },
    { key: 5, item: '硬件小计（元） - 外采', amount: '', valuationDesc: '' },
    { key: 6, item: '实施小计（元）', amount: '', valuationDesc: '' },
    { key: 7, item: '其他小计（元）', amount: '', valuationDesc: '' },
]);
const columns = [
    {
        title: '项目',
        dataIndex: 'item',
        key: 'item',
        width: '30%'
    },
    {
        title: '金额（元）',
        dataIndex: 'amount',
        key: 'amount',
        width: '30%'

    },
]
let data = ref([
    { key: 1, item: '售前支持小计（元）', amount: 0.00, tab: 'persale' },
    { key: 2, item: '定制开发小计（元）', amount: 0.00, tab: 'software' },
    { key: 3, item: '产品平台小计（元）', amount: 0.00, highlight: true, tab: 'product' },
    { key: 4, item: '硬件小计（元） - 自研', amount: 0.00, tab: 'self' },
    { key: 5, item: '硬件小计（元） - 外采', amount: 0.00, tab: 'purchasing' },
    { key: 6, item: '实施小计（元）', amount: 0.00, tab: 'implementation' },
    { key: 7, item: '其他小计（元）', amount: 0.00, tab: 'other' },
]);
const loading = ref(true);
const deptData = ref([]);  // 部门数据
const percentages = ref([]);
const auditDesc = ref('')
const isApproval = ref(true)

const signType = ref('')
const unableQuoteAmount = ref(null)
const xxUnableQuoteAmount = ref(null)

const totalAmount = computed(() => {
    return data.value.reduce((sum, item) => sum + Number(item.amount), 0).toFixed(2);
});
const totalRoughAmount = computed(() => {
    return roughdata.value.reduce((sum, item) => sum + Number(item.amount), 0).toFixed(2);
});

watch(() => props.isOrderLoss, (newVal) => {
    if (newVal) {
        const params = {
            'opportunitiesId': props.opportunity.id
        }
        if (props.opportunity.type === 'COST') {
            getQuoteDetail('detail', params)
        }
        if (props.opportunity.type === 'ROUGH') {
            getQuoteDetail('rough', params)
        }
        if (props.opportunity.type === 'INCAPABLE' && (props.opportunity.loseType === '2' || props.opportunity.loseType === '3' || props.opportunity.loseType === '4')) {
            getQuoteDetail('presale', params)
        }
        if (props.opportunity.type === 'INCAPABLE' && (props.opportunity.loseType === '1')) {
            loading.value = false;
        }
    }
});
const getQuoteDetail = async (url, params) => {
    try {
        const response = await fetchLoseInfo(url, params);
        if (response && response.code === 200) {
            const detailData = response.data;
            if (props.opportunity.type === 'COST') {
                data.value.forEach(item => {
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
                            item.amount = 0.00; // 默认值  
                    }
                });
                deptData.value = detailData.radioList
                percentages.value = deptData.value.map(dept => dept.radio);
            }
            if (props.opportunity.type === 'ROUGH') {
                roughdata.value = [
                    { key: 1, item: '售前支持小计（元）', amount: detailData.quoteOpportunitiesRoughDetails[0].supportAmount, valuationDesc: detailData.quoteOpportunitiesRoughDetails[0].supportDesc },
                    { key: 2, item: '定制开发小计（元）', amount: detailData.quoteOpportunitiesRoughDetails[0].customAmount, valuationDesc: detailData.quoteOpportunitiesRoughDetails[0].customDesc },
                    { key: 3, item: '产品平台小计（元）', amount: detailData.quoteOpportunitiesRoughDetails[0].productAmount, valuationDesc: detailData.quoteOpportunitiesRoughDetails[0].productDesc },
                    { key: 4, item: '硬件小计（元） - 自研', amount: detailData.quoteOpportunitiesRoughDetails[0].selfAmount, valuationDesc: detailData.quoteOpportunitiesRoughDetails[0].selfDesc },
                    { key: 5, item: '硬件小计（元） - 外采', amount: detailData.quoteOpportunitiesRoughDetails[0].externalAmount, valuationDesc: detailData.quoteOpportunitiesRoughDetails[0].externalDesc },
                    { key: 6, item: '实施小计（元）', amount: detailData.quoteOpportunitiesRoughDetails[0].implementAmount, valuationDesc: detailData.quoteOpportunitiesRoughDetails[0].implementDesc },
                    { key: 7, item: '其他小计（元）', amount: detailData.quoteOpportunitiesRoughDetails[0].otherAmount, valuationDesc: detailData.quoteOpportunitiesRoughDetails[0].otherDesc }
                ];
                deptData.value = detailData.radioList
                percentages.value = deptData.value.map(dept => dept.radio);
            }
            if (props.opportunity.type === 'INCAPABLE' && (props.opportunity.loseType === '2' || props.opportunity.loseType === '3' || props.opportunity.loseType === '4')) {
                console.log(detailData)
                signType.value = detailData.signType
                unableQuoteAmount.value = detailData.unableQuoteAmount
                xxUnableQuoteAmount.value = detailData.xxUnableQuoteAmount
            }

        } else {
            console.error('获取报价详情失败:', response.error);
        }
    } catch (error) {
        console.error('Error fetching cost quote detail:', error);
    } finally {
        loading.value = false;
    }
}
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
        deptData.value = []
        percentages.value = []
        emit('close');
    }

}
const handleCancel = () => {
    isApproval.value = true
    auditDesc.value = ''
    deptData.value = []
    percentages.value = []
    emit('close');
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
    /* text-align: right; */
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