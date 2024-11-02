<template>
  <el-form ref="activityRefForm" :model="activityRemark" label-width="120px" :rules="activityRules">
    <el-form-item label="ID">
      <div class="desc">{{activityDetail.id}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="负责人">
      <div class="desc"> {{activityDetail.ownerPO.name}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="活动名称">
      <div class="desc">{{activityDetail.name}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="开始时间">
      <div class="desc">{{activityDetail.startDate}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="结束时间">
      <div class="desc">{{activityDetail.endDate}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="活动预算">
      <div class="desc">{{activityDetail.cost}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="活动描述">
      <div class="desc">{{activityDetail.description}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="创建时间">
      <div class="desc">{{activityDetail.createTime}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="创建人">
      <div class="desc">{{activityDetail.createByPO.name}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="编辑时间">
      <div class="desc">{{activityDetail.editTime}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="编辑人">
      <div class="desc">{{activityDetail.editByPO.name}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="填写备注">
      <el-input
          v-model="activityRemark.noteContent"
          :rows="8"
          type="textarea"/>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="activityRemarkSubmit">提 交</el-button>
      <el-button type="success" plain @click="goBack">返 回</el-button>
    </el-form-item>
  </el-form>

  <!--html横线-->
  <hr/>

  <el-table
      :data="activityRemarkList"
      style="width: 100%">
    <el-table-column type="index" label="序号" width="100"/>
    <el-table-column property="noteContent" label="备注内容"/>
    <el-table-column property="createTime" label="备注时间" width="175"/>
    <el-table-column property="createByPO.name" label="备注人" width="175"/>
    <el-table-column property="editTime" label="编辑时间" width="175"/>
    <el-table-column property="editByPO.name" label="编辑人" width="175"/>
    <el-table-column label="操作" width="150">
      <template #default="scope">
        <a href="javascript:" @click="edit(scope.row.id)">编辑</a>
        &nbsp;
        <a href="javascript:" @click="del(scope.row.id)">删除</a>
      </template>
    </el-table-column>
  </el-table>
  <p>
    <el-pagination
        background
        layout="prev, pager, next"
        :page-size="pageSize"
        :total="total"
        @prev-click="page"
        @next-click="page"
        @current-change="page"/>
  </p>

  <!--编辑市场活动备注记录的弹窗（对话框）-->
  <el-dialog v-model="activityRemarkDialogVisible" title="编辑市场活动备注" width="55%" center>

    <el-form ref="activityRemarkEditRefForm" :model="activityRemarkQuery" label-width="110px" :rules="activityRemarkRules">
      <el-form-item label="备注内容" prop="noteContent">
        <el-input
            v-model="activityRemarkQuery.noteContent"
            :rows="8"
            type="textarea"/>
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="activityRemarkDialogVisible = false">关 闭</el-button>
        <el-button type="primary" @click="activityRemarkEditSubmit">提 交</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import {doDelete, doGet, doPost, doPut} from "../http/httpRequest";
import {goBack, messageConfirm, messageTip} from "../util/utils";

export default {
  name: "ActivityDetailView",

  //注入
  inject : ['reload'],

  data() {
    return {
      //定义市场活动详情数据对象，初始值是空
      activityDetail : {
        ownerPO : {},
        createByPO : {},
        editByPO : {}
      },
      //市场活动备注form对象，初始值是空
      activityRemark : {},
      //定义市场活动备注的form表单验证规则
      activityRules : {
        noteContent : [
          { required: true, message: '备注内容不能为空', trigger: 'blur' },
          { min: 5, max: 255, message: '备注内容长度为5-255个字符', trigger: 'blur' }
        ]
      },
      //市场活动备注列表数据对象，初始值是空
      activityRemarkList : [{
        createByPO : {},
        editByPO : {}
      }],
      //分页时每页显示多少条数据
      pageSize : 0,
      //总共有多少条
      total : 0,
      //定义控制弹窗是否弹出来的变量，false是不弹出来，true就弹出来
      activityRemarkDialogVisible : false,
      //市场活动备注记录表单对象，初始值是空
      activityRemarkQuery : {},
      //定义市场活动备注记录form表单验证规则
      activityRemarkRules : {
        noteContent : [
          { required: true, message: '备注内容不能为空', trigger: 'blur' },
          { min: 5, max: 255, message: '备注内容长度为5-255个字符', trigger: 'blur' }
        ]
      }
    }
  },

  //页面渲染后执行该钩子函数
  mounted() {
    this.loadActivityDetail();
    this.loadActivityRemarkList(1);
  },

  methods : {
    //声明一下导入的函数
    goBack,

    //加载市场活动详情的函数
    loadActivityDetail() {
      //从动态路由地址中获取市场活动id参数
      let id = this.$route.params.id; //id参数名要和路由中配置的path : 'activity/:id', 的参数名相同
      doGet("/api/activity/" + id, {}).then(resp => {
        if (resp.data.code === 200) {
          this.activityDetail = resp.data.data;
        }
      })
    },

    //提交市场活动备注记录
    activityRemarkSubmit() {
      this.$refs.activityRefForm.validate( (isValid) => {
        if (isValid) {
          doPost("/api/activity/remark", {
            activityId : this.activityDetail.id,
            noteContent : this.activityRemark.noteContent
          }).then( (resp) => { //获取ajax异步请求后的结果
            console.log(resp);
            if (resp.data.code === 200) {
              //提交市场活动备注记录成功，提示一下
              messageTip("提交备注记录成功", "success");
              //刷新一下页面
              this.reload();
            } else {
              //新增用户失败，提示一下
              messageTip("提交备注记录失败", "error");
            }
          });
        }
      })
    },

    //分页加载市场活动备注记录列表信息
    loadActivityRemarkList(current) {
      // /api/activity/3/remark
      let activityId = this.$route.params.id;
      doGet("/api/activity/" + activityId + "/remark", {
        current : current
      }).then(resp => {
        if (resp.data.code === 200) {
          this.activityRemarkList = resp.data.data.list; // resp.data  =  R 对象
          this.pageSize = resp.data.data.pageSize;
          this.total = resp.data.data.total;
        }
      })
    },

    //分页函数
    page(number) { //number值是element-plus回调时传给我们的，number这个值就是当前页
      this.loadActivityRemarkList(number);
    },

    //市场活动备注记录编辑
    edit(id) {
      console.log(id);
      //弹个窗，并且把数据查出来
      this.activityRemarkDialogVisible = true;
      //查询出该id备注记录的详情
      this.loadActivityRemarkDetail(id);
    },

    //加载备注记录详情的函数
    loadActivityRemarkDetail(id) {
      doGet("/api/activity/remark/" + id, {}).then(resp => {
        if (resp.data.code === 200) {
          this.activityRemarkQuery = resp.data.data;
        }
      })
    },

    //编辑市场活动备注记录（提交保存）
    activityRemarkEditSubmit() {
      this.$refs.activityRemarkEditRefForm.validate( (isValid) => {
        if (isValid) {
          doPut("/api/activity/remark", {
            id : this.activityRemarkQuery.id,
            noteContent : this.activityRemarkQuery.noteContent
          }).then( (resp) => { //获取ajax异步请求后的结果
            console.log(resp);
            if (resp.data.code === 200) {
              //编辑市场活动备注记录成功，提示一下
              messageTip("编辑备注记录成功", "success");
              //刷新一下页面
              this.reload();
            } else {
              //编辑备注记录失败，提示一下
              messageTip("编辑备注记录失败", "error");
            }
          });
        }
      })
    },

    //删除市场活动备注记录
    del(id) {
      messageConfirm("您确定要删除此数据吗？").then(() => { //当点击“确定”按钮就执行该then函数
        doDelete("/api/activity/remark/" + id, {}).then( (resp) => { //获取ajax异步请求后的结果
          if (resp.data.code === 200) {
            //删除成功，提示一下
            messageTip("删除成功", "success");
            //刷新一下页面
            this.reload();
          } else {
            //删除失败，提示一下
            messageTip("删除失败，原因：" + resp.data.msg, "error");
          }
        });
      }).catch(() => { //当点击“取消”按钮就执行该catch函数
        messageTip("取消删除", "warning");
      })
    }
  }
}
</script>

<style scoped>
.desc {
  width: 100%;
  background-color: #F0FFFF;
  padding-left: 8px;
}
</style>