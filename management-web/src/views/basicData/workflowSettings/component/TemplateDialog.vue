<template>
  <div class="sales-settings">
    <el-dialog
      :title="isEditing ? '编辑邮件模板' : '查看邮件模板'"
      :visible.sync="visible"
      width="68%"
      :destroy-on-close="true"
      :z-index="1000"
      @close="handleCloseDialog"
    >
      <div class="content">
        <el-form
          ref="addUserRuleForm"
          :model="currentTemplate"
          label-width="80px"
          class="ruleForm"
          :border="true"
          :disabled="!isEditing"
          :rules="rules"
        >
          <el-form-item prop="senderInfo">
            <template #label>
              <span class="el-icon-star" style="color: #F56C6C;">*</span>
              发送至
            </template>
            <el-tag
              v-for="(item, itemIndex) in currentTemplate.senderInfo"
              :key="item.nickName"
              :closable="!item.default"
              :disable-transitions="false"
              effect="light"
              @close="handleClose('senderInfo', index, itemIndex)"
            >
              {{ item.nickName }}
            </el-tag>
            <el-button class="button-new-tag" size="small" type="primary" @click="handleSelectUser(currentTemplate.senderInfo, 0)">+ 添加</el-button>
          </el-form-item>
          <el-form-item label="抄送至" prop="carbonCopyInfo">
            <el-tag
              v-for="(item, itemIndex) in currentTemplate.carbonCopyInfo"
              :key="item.nickName"
              :closable="!item.default"
              :disable-transitions="false"
              effect="light"
              @close="handleClose('carbonCopyInfo', index, itemIndex)"
            >
              {{ item.nickName }}
            </el-tag>
            <el-button class="button-new-tag" size="small" type="primary" @click="handleSelectUser(currentTemplate.carbonCopyInfo, 1)">+ 添加</el-button>
          </el-form-item>
          <el-form-item label="邮件主题" prop="emailSubject">
            <el-input v-model="currentTemplate.emailSubject" placeholder="请输入邮件主题" maxlength="50" @focus="setCurrentInput('emailSubject')" />
          </el-form-item>
          <el-form-item label="邮件模板" prop="emailTemplate">
            <vue-editor
              v-model="currentTemplate.emailTemplate"
              maxlength="400"
              :disabled="!isEditing"
              @focus="setCurrentInput('emailTemplate')"
              @input="validateTemplateContent()"
            />
            <span>主题/模板支持关键字：</span>
            <el-button
              v-for="item in selectListKeys"
              :key="item"
              size="mini"
              plain
              @click="insertText(item)"
            >
              {{ item }}
            </el-button>
          </el-form-item>
          <el-form-item label="所属分类" prop="category">
            <el-input v-model="currentTemplate.category" placeholder="请输入所属分类" maxlength="50" />
          </el-form-item>
        </el-form>
        <div class="actions">
          <el-button type="default" :disabled="!isEditing" @click="handleCloseDialog">取消</el-button>
          <el-button type="primary" :disabled="!isEditing" @click="save">保存</el-button>
        </div>
      </div>
      <select-user
        :select-user-visible.sync="selectUserVisible"
        :user-detail="userDetail"
        :select-type="selectType"
        @send-data="handleChildData"
      />
    </el-dialog>
  </div>
</template>

<script>
import { putWorkflow } from '@/api/basicData/workflow'
import SelectUser from './SelectUser.vue'
import currentFormData from '../formData'
import { VueEditor } from 'vue2-editor'
export default {
  components: {
    SelectUser,
    VueEditor
  },
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    isEditing: {
      type: Boolean,
      default: false
    },
    currentTemplate: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      selectUserVisible: false,
      selectType: 0,
      userDetail: [],
      defaultFormData: currentFormData,
      formData: {},
      canSelectList: [
        { type: 'NEED_COST_QUOTATION', labels: ['{商机主题}', '{日期}', '{报价系统链接}'] },
        { type: 'COMPLETE_COST_QUOTATION', labels: ['{售前部门}', '{商机主题}', '{报价类型}', '{版本号}', '{日期}', '{成本总额}', '{报价系统链接}'] },
        { type: 'PENDING_COST_QUOTATION_UPDATE', labels: ['{商机主题}', '{日期}', '{所属销售}', '{重新报价说明}', '{报价系统链接}'] },
        { type: 'QUOTATION_APPROVAL', labels: ['{商机主题}', '{日期}', '{所属销售}', '{所属售前}', '{成本总额}', '{销售对外报价}', '{项目报价利润率}', '{继续跟进原因}', '{报价系统链接}'] },
        { type: 'QUOTATION_APPROVAL_RESULT_NOTIFICATION', labels: ['{商机主题}', '{销售报价审批结果}', '{日期}', '{成本总额}', '{销售对外报价}', '{项目报价利润率}', '{报价系统链接}'] },
        { type: 'SIGNING_APPLICATION', labels: ['{商机主题}', '{日期}', '{所属销售}', '{所属售前}', '{成本总额}', '{北光合同总金额}', '{北光合同金额不含硬件部分}', '{总成本利润率}', '{成本利润率}', '{继续签约原因}', '{报价系统链接}', '{签约申请详情图片}'] },
        { type: 'SIGNING_APPROVAL_RESULT_NOTIFICATION', labels: ['{商机主题}', '{日期}', '{签约申请审批结果}', '{成本总额}', '{销售对外报价}', '{项目签约利润率}', '{报价系统链接}'] },
        { type: 'SALES_QUOTATION_LOSS_NOTIFICATION', labels: ['{商机主题}', '{日期}', '{所属销售}', '{所属售前}', '{成本评估类型}', '{成本总额}', '{销售对外报价}', '{项目报价利润率}', '{丢单理由}', '{报价系统链接}'] },
        { type: 'QUOTATION_SIGNING_LOSS_NOTIFICATION', labels: ['{商机主题}', '{日期}', '{所属销售}', '{所属售前}', '{成本评估类型}', '{成本总额}', '{销售对外报价}', '{项目报价利润率}', '{报价申请结果}', '{申请签约金额}', '{项目签约利润率}', '{签约申请审批结果}', '{丢单原因}', '{报价系统链接}'] },
        { type: 'WEEKLY_SCHEDULED_NOTIFICATION', labels: ['{时间周期}', '{管理系统链接}'] },
        { type: 'POT_HC', labels: ['{商机主题}', '{日期}', '{所属销售}', '{所属售前}', '{成本评估类型}', '{成本价总计}'] },
        { type: 'EST_HC', labels: ['{商机主题}', '{日期}', '{所属销售}', '{所属售前}', '{成本评估类型}', '{成本价总计}'] },
        { type: 'CON_HC', labels: ['{商机主题}', '{日期}', '{所属销售}', '{所属售前}', '{成本评估类型}', '{成本价总计}', '{报价系统链接}'] }
      ],
      currentInput: '',
      selectListKeys: [],
      templateError: false,
      // 表单校验
      rules: {
        emailSubject: [
          { required: true, message: '用户名称不能为空', trigger: 'blur' }
        ],
        emailTemplate: [
          { required: true, message: '用户昵称不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    currentTemplate(val) {
      if (val) {
        const item = this.canSelectList.find(item => item.type === val.type)
        this.selectListKeys = item.labels
      }
    }
  },
  created() {
    // this.initFormData()
  },
  methods: {
    // 检查模板内容是否包含样式标签
    validateTemplateContent(index) {
      const template = this.currentTemplate.emailTemplate
      this.templateError = false
      const regex = /\{([^}]+)\}/g
      let match

      // 遍历所有匹配到的模板变量
      while ((match = regex.exec(template)) !== null) {
        const variableContent = match[1] // 获取花括号内的内容

        // 检查花括号内容是否包含 HTML 标签
        if (/<[^>]+>/.test(variableContent)) {
          this.templateError = true
          break
        }
      }
      if (this.templateError) {
        this.templateError = true
        this.$message.error('单个模板关键字样式必须一致，请仔细检查模板内容')
      }
    },

    handleChildData(selectedItems, selectType) {
      if (selectType === 0) {
        this.currentTemplate.senderInfo = selectedItems
      } else {
        this.currentTemplate.carbonCopyInfo = selectedItems
      }
    },

    handleSelectUser(users, type) {
      this.userDetail = users
      this.selectUserVisible = true
      this.selectType = type
    },

    handleClose(type, index, itemIndex) {
      if (Array.isArray(this.currentTemplate[type])) {
        this.currentTemplate[type].splice(itemIndex, 1)
      }
    },

    getUserIds(infoArray) {
      return infoArray.filter(m => m.userId).map(m => m.userId).join(',')
    },

    async save() {
      if (this.currentTemplate.senderInfo.length < 1) {
        this.$message.error('收件人不能为空')
        return
      }
      this.currentTemplate.sender = this.getUserIds(this.currentTemplate.senderInfo)
      this.currentTemplate.carbonCopy = this.getUserIds(this.currentTemplate.carbonCopyInfo)
      const updatedList = {
        ...this.currentTemplate,
        emailSubject: this.currentTemplate.emailSubject.trim(),
        emailTemplate: this.currentTemplate.emailTemplate
          .replace(/\n/g, '<br>') // 替换换行符为 <br>
          .trim()
      }
      try {
        const res = await putWorkflow(updatedList)
        if (res.code === 200) {
          this.$message.success('保存成功')
        } else {
          this.$message.error(res.message || '加载数据失败，请重试')
        }
      } catch (error) {
        this.$message.error(error.data.msg, error)
      } finally {
        this.$emit('visibleChange', false)
        window.scrollTo({ top: 0 })
      }
    },

    setCurrentInput(inputType) {
      // 设置当前输入框类型
      this.currentInput = inputType
    },

    insertText(item) {
      if (this.currentInput === 'emailSubject') {
        this.currentTemplate.emailSubject += item
      } else if (this.currentInput === 'emailTemplate') {
        this.currentTemplate.emailTemplate += item
      }
    },

    handleCloseDialog() {
      this.$emit('visibleChange', false)
    }
  }
}
</script>

<style scoped>
.sales-settings {
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.sales-settings .el-tabs {
  flex: 1;
}

.sales-settings .el-table .el-table__header {
  width: 100% !important;
}

.sales-settings .el-table .el-table__body {
  width: 100% !important;
}

.sales-settings .actions {
  margin-top: 20px;
  text-align: end;
}

.sales-settings .el-table {
  flex: 1;
}

.sales-settings .el-input {
  width: 100%;
  resize: none;
}

.sales-settings .el-input .el-input__inner {
  /* border: none; */
}

@media (max-width: 768px) {
  .sales-settings .el-table-column {
    width: 100% !important;
  }
}

.content {
  padding: 0 20px 0 0;
}

.el-form-item__label {
  text-align: left;
}

.ruleForm {
  padding-left: 30px;
  margin-top: 30px;
}

.el-tag + .el-tag {
    margin-left: 10px;
  }
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>
