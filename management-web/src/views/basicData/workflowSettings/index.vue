<template>
  <div class="app-container">
    <header-title :need-border="true" style="margin-bottom: 20px;">
      <template slot="title"> 邮件模板 </template>
    </header-title>
    <el-row :gutter="20">
      <el-col :span="4" :xs="24" style="border-right: 1px solid #eee;">
        <div class="head-container">
          <el-tree
            ref="tree"
            :data="categoryOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            node-key="id"
            default-expand-all
            highlight-current
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <el-col :span="20" :xs="24">
        <el-form ref="queryForm" size="small" :inline="true" label-width="68px" @submit.native.prevent>
          <el-form-item>
            <el-input
              v-model.trim="queryParams.model.emailSubject"
              placeholder="请输入邮件主题"
              clearable
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="small" @click="handleQuery">搜索</el-button>
            <el-button size="small" @click="resetQuery">刷新</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="small" @click="comfirmDefault">恢复默认模板</el-button>
          </el-form-item>
        </el-form>
        <el-table v-loading="loading" :data="dataList">
          <el-table-column key="userId" type="index" width="50" align="center" />
          <el-table-column key="type" label="模板名称" align="center" prop="type" width="150">
            <template v-slot="scope">
              {{ getWorkflowTabsName(scope.row.type) }}
            </template>
          </el-table-column>
          <el-table-column key="senderInfo" label="发送至" align="center" prop="senderInfo" width="170">
            <template v-slot="scope">
              {{ getNickNameInfo(scope.row.senderInfo) }}
            </template>
          </el-table-column>
          <el-table-column key="carbonCopyInfo" label="抄送至" align="center" prop="carbonCopyInfo">
            <template v-slot="scope">
              {{ getNickNameInfo(scope.row.carbonCopyInfo) }}
            </template>
          </el-table-column>
          <el-table-column key="emailSubject" label="邮件主题" align="center" prop="emailSubject" />
          <el-table-column label="创建时间" align="center" prop="createTime" width="170">
            <template slot-scope="scope">
              <span>{{ scope.row.createTime }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="140"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                v-hasPermi="['system:user:edit']"
                size="middle"
                type="text"
                @click="handleUpdate(scope.row, 'detail')"
              >详情</el-button>
              <el-button
                v-hasPermi="['system:user:edit']"
                size="middle"
                type="text"
                @click="handleUpdate(scope.row, 'edit')"
              >编辑</el-button>
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
      </el-col>
    </el-row>
    <Template-dialog
      :visible.sync="open"
      :current-template="currentTemplate"
      :is-editing="isEditing"
      @visibleChange="visibleChange"
    />
  </div>
</template>

<script>
import { listUser } from '@/api/system/user'
import headerTitle from '@/components/Title/index.vue'
import { getCategory, getWorkflow, saveWorkflow } from '@/api/basicData/workflow'
import TemplateDialog from './component/TemplateDialog.vue'
import currentFormData from './formData'

export default {
  name: 'Template',
  components: { headerTitle, TemplateDialog },
  data() {
    return {
      loading: true,
      total: 0,
      dataList: null,
      categoryOptions: undefined,
      open: false,
      defaultFormData: currentFormData,
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      queryParams: {
        current: 1,
        size: 10,
        model: {
          category: '',
          emailSubject: ''
        }
      },
      WorkflowTabs: [
        { label: '需成本报价', type: 'NEED_COST_QUOTATION' },
        { label: '已完成成本报价', type: 'COMPLETE_COST_QUOTATION' },
        { label: '待更新成本报价', type: 'PENDING_COST_QUOTATION_UPDATE' },
        { label: '报价审批', type: 'QUOTATION_APPROVAL' },
        { label: '报价审批结果通知', type: 'QUOTATION_APPROVAL_RESULT_NOTIFICATION' },
        { label: '签约申请', type: 'SIGNING_APPLICATION' },
        { label: '签约审批结果通知', type: 'SIGNING_APPROVAL_RESULT_NOTIFICATION' },
        { label: '销售报价丢单通知', type: 'SALES_QUOTATION_LOSS_NOTIFICATION' },
        { label: '报价签约丢单通知', type: 'QUOTATION_SIGNING_LOSS_NOTIFICATION' },
        { label: '丢单信息汇总', type: 'WEEKLY_SCHEDULED_NOTIFICATION' },
        { label: '涉及硬件采购的潜在商机', type: 'POT_HC' },
        { label: '涉及硬件采购的预计签约项目', type: 'EST_HC' },
        { label: '涉及硬件采购的已签约项目', type: 'CON_HC' }
      ],
      currentTemplate: {},
      isEditing: false
    }
  },
  created() {
    this.getList()
    this.getDeptTree()
  },
  methods: {
    getWorkflowTabsName(type) {
      const item = this.WorkflowTabs.find((item) => item.type === type)
      return item ? item.label : '-'
    },

    getNickNameInfo(items) {
      return items.map(item => item.nickName).join(', ')
    },

    async getUserList() {
      try {
        const res = await listUser({ size: 9999, current: 1 })
        if (res.code === 200) {
          this.userDataList = res.rows || []
        }
      } catch (error) {
        this.$message.error('获取用户数据失败', error)
      }
    },

    async getList() {
      this.loading = true
      try {
        const res = await getWorkflow(this.queryParams)
        if (res.code === 200) {
          if (res.rows) {
            this.dataList = res.rows.map(item => {
              if (item.emailTemplate) {
                // 替换 <br> 标签并去除多余空白字符
                item.emailTemplate = item.emailTemplate.replace(/<br\s*\/?>/g, '\n').trim()
              }
              return item
            })
            this.total = Number(res.total)
          }
        }
      } catch (error) {
        this.$message.error(error)
      } finally {
        this.loading = false
      }
    },

    getDeptTree() {
      this.categoryOptions = [{ id: 9999, label: '全部模板', children: [] }]
      getCategory().then(response => {
        this.categoryOptions[0].children = response.data
      })
    },

    getUserIds(infoArray) {
      return infoArray.filter(m => m.userId).map(m => m.userId).join(',')
    },

    comfirmDefault() {
      this.$confirm('确认将全部模板恢复为默认模板吗', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(() => {
        const formData = JSON.parse(JSON.stringify(this.defaultFormData))
        this.save(formData)
      }).catch(() => {})
    },

    async save(formData) {
      this.loading = true
      formData.forEach((item, index) => {
        item.type = this.WorkflowTabs[index].type
        item.senderInfo.forEach
        // 处理发送的人
        item.sender = this.getUserIds(item.senderInfo)
        // 处理抄送的人
        item.carbonCopy = this.getUserIds(item.carbonCopyInfo)
      })
      const updatedList = formData.map(item => {
        return {
          ...item,
          emailSubject: item.emailSubject.trim(),
          emailTemplate: item.emailTemplate
            .replace(/\n/g, '<br>') // 替换换行符为 <br>
            .trim()
        }
      })
      try {
        const res = await saveWorkflow(updatedList)
        if (res.code === 200) {
          this.$message.success('保存成功')
          this.getList()
          this.getDeptTree()
        } else {
          this.$message.error(res.message || '加载数据失败，请重试')
        }
      } catch (error) {
        this.$message.error(error.data.msg, error)
      } finally {
        this.loading = false
        window.scrollTo({ top: 0 })
      }
    },

    visibleChange(value) {
      this.open = value
      this.getList()
      this.getDeptTree()
    },

    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },

    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.model.category = data.id === 9999 ? '' : data.label
      this.handleQuery()
    },

    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },

    // 刷新
    resetQuery() {
      this.queryParams.model.emailSubject = ''
      this.$refs.tree.setCurrentKey('9999')
      this.queryParams.model.category = ''
      this.handleQuery()
    },

    handleUpdate(row, type) {
      this.isEditing = type !== 'detail'
      this.currentTemplate = row
      this.open = true
    },

    handleCurrentChange(page) {
      this.queryParams.current = page
      this.getList()
    },

    handleSizeChange(size) {
      this.queryParams.size = size
      this.getList()
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
