<template>
  <el-button type="primary" class="btn" @click="addClue">录入线索</el-button>
  <el-button type="success" class="btn" @click="importExcel">导入线索(Excel)</el-button>
  <el-button type="danger" class="btn" @click="batchDelClue">批量删除</el-button>

  <el-table
      :data="clueList"
      style="width: 100%"
      @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="50"/>
    <el-table-column type="index" label="序号" width="65"/>
    <el-table-column property="ownerPO.name" label="负责人" width="120" />
    <el-table-column property="activityPO.name" label="所属活动"/>
    <el-table-column label="姓名">
      <template #default="scope">
        <a href="javascript:" @click="view(scope.row.id)">{{ scope.row.fullName }}</a>
      </template>
    </el-table-column>
    <el-table-column property="appellationPO.typeValue" label="称呼"/>
    <el-table-column property="phone" label="手机" width="120"/>
    <el-table-column property="weixin" label="微信" width="120"/>
    <el-table-column property="needLoanPO.typeValue" label="是否贷款"/>
    <el-table-column property="intentionStatePO.typeValue" label="意向状态"/>
    <el-table-column property="intentionProductPO.name" label="意向产品"/>
    <el-table-column property="statePO.typeValue" label="线索状态"/>
    <el-table-column property="sourcePO.typeValue" label="线索来源"/>
    <el-table-column property="nextContactTime" label="下次联系时间" width="165"/>
    <el-table-column label="操作" width="230">
      <template #default="scope">
        <el-button type="primary" @click="view(scope.row.id)" v-hasPermission="'clue:view'">详情</el-button>
        <el-button type="success" @click="edit(scope.row.id)" v-hasPermission="'clue:edit'">编辑</el-button>
        <el-button type="danger" @click="del(scope.row.id)" v-hasPermission="'clue:delete'">删除</el-button>
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

  <!--导入线索Excel的弹窗（对话框）-->
  <el-dialog v-model="importExcelDialogVisible" title="导入线索Excel" width="55%" center>
    <el-upload
        ref="uploadRef"
        method="post"
        :http-request="uploadFile"
        :auto-upload="false">
      <template #trigger>
        <el-button type="primary">选择Excel文件</el-button>
        &nbsp;仅支持后缀名为.xls或.xlsx的文件
      </template>
      <br/>
      <br/>
      <div>重要提示：</div>
      <ul>
        <li>上传仅支持后缀名为.xls或.xlsx的文件；</li>
        <li>给定Excel文件的第一行将视为字段名；</li>
        <li>请确认您的文件大小不超过50MB；</li>
        <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式；</li>
        <li>日期时间以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式；</li>
      </ul>
    </el-upload>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="importExcelDialogVisible = false">关 闭</el-button>
        <el-button class="ml-3" type="success" @click="submitUpload">上 传</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import {doDelete, doGet, doPost} from "../http/httpRequest";
import {messageConfirm, messageTip} from "../util/utils";

export default {
  name: "ClueView",

  //注入
  inject : ['reload'],

  data() {
    return {
      //线索列表对象，初始值是空
      clueList : [{
        ownerPO : {},
        activityPO : {},
        appellationPO : {},
        needLoanPO : {},
        intentionStatePO : {},
        intentionProductPO : {},
        statePO : {},
        sourcePO : {}
      }],
      //分页时每页显示多少条数据
      pageSize : 0,
      //总共有多少条
      total : 0,
      //导入Excel的弹窗是否弹出来，默认是false不弹出来，true就弹出来
      importExcelDialogVisible : false,
      //用户勾选的线索id的数组，初始值是空
      clueIdArray : []
    }
  },

  mounted() {
    this.getData(1);
  },

  methods : {
    //获取线索分页列表数据
    getData(current) {
      doGet("/api/clues", {
        current : current //当前页，前面是参数名，后面是参数值
      }).then(resp => {
        if (resp.data.code === 200) {
          this.clueList = resp.data.data.list; // resp.data  =  R 对象
          this.pageSize = resp.data.data.pageSize;
          this.total = resp.data.data.total;
        }
      })
    },

    //分页函数
    page(number) { //number值是element-plus回调时传给我们的，number这个值就是当前页
      this.getData(number);
    },

    //录入线索
    addClue() {
      this.$router.push("/dashboard/clue/add");
    },

    //编辑线索
    edit(id) {
      //跳转到此路由上
      this.$router.push("/dashboard/clue/edit/" + id);
    },

    //导入线索Excel
    importExcel() {
      this.importExcelDialogVisible = true;
    },

    //文件上传的函数，上传文件的处理，饿了么组件会传一个param参数，this.$refs.upload.submit()会自动调用 httpRequest方法，在里面取得file；
    uploadFile(param) {
      console.log(param);
      let fileObj = param.file // 相当于input里取得的files
      let formData = new FormData() // new一个FormData对象
      formData.append('file', fileObj)//文件对象，前面file是参数名，后面fileObj是参数值
      doPost("/api/importExcel", formData).then(resp => {
        if (resp.data.code === 200) {
          //Excel导入成功，提示一下
          messageTip("导入成功", "success");
          //清理一下上传的文件
          this.$refs.uploadRef.clearFiles();
          //页面刷新
          this.reload();
        } else {
          //Excel导入失败
        }
      })
    },

    //提交山传Excel文件
    submitUpload() {
      this.$refs.uploadRef.submit();
    },

    //删除线索
    del(id) {
      messageConfirm("您确定要删除此数据吗？").then(() => { //当点击“确定”按钮就执行该then函数
        doDelete("/api/clue/" + id, {}).then( (resp) => { //获取ajax异步请求后的结果
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
    },

    //批量删除线索
    batchDelClue() {
      if (this.clueIdArray.length <= 0) {
        messageTip("请选择要删除的数据", "warning");
        return;
      }
      messageConfirm("您确定要删除这些数据吗？").then(() => {
        // userIdArray [1,3,5,6,12,15] ==>  ids = "1,3,5,6,12,15";
        let ids = this.clueIdArray.join(",");
        doDelete("/api/clue/batch", {
          ids : ids
        }).then( resp => {
          if (resp.data.code === 200) {
            //批量删除成功，提示一下
            messageTip("批量删除成功", "success");
            //刷新一下页面
            this.reload();
          } else {
            //批量删除失败，提示一下
            messageTip("批量删除失败，原因：" + resp.data.msg, "error");
          }
        })
      }).catch(() => { //当点击“取消”按钮就执行该catch函数
        messageTip("取消批量删除", "warning");
      })
    },

    //当选择项发生变化时会触发该函数，也就是勾选或者取消勾选checkbox的时候触发该函数
    handleSelectionChange(dataObjectArray) {
      //触发该函数的时候，把原来数组里面的id清除掉
      this.clueIdArray = [];
      //循环这个数据对象的数据，把数组里面的每个对象的id取出来
      dataObjectArray.forEach( dataObject => {
        let dataId = dataObject.id; //这个数据id就是我们每一行user对象的id
        this.clueIdArray.push(dataId);
      })
    },

    //查看详情
    view(id) {
      this.$router.push("/dashboard/clue/" + id);
    }
  }
}
</script>

<style scoped>
.btn {
  margin-bottom: 10px;
}
</style>