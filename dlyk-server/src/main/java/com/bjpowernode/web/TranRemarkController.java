package com.bjpowernode.web;

import com.bjpowernode.domain.po.TClueRemark;
import com.bjpowernode.domain.po.TTranRemark;
import com.bjpowernode.domain.query.CustomerRemarkQuery;
import com.bjpowernode.domain.query.TranRemarkQuery;
import com.bjpowernode.domain.result.R;
import com.bjpowernode.service.TranRemarkService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class TranRemarkController {

    @Resource
    private TranRemarkService tranRemarkService;

    @PostMapping(value = "/api/tran/remark")
    public R addTranRemark(@RequestBody TranRemarkQuery tranRemarkQuery,
                               @RequestHeader(value = "Authorization") String token) {
        //1、前端axios的post提交过来的参数，是一个json，后端接收要使用@RequestBody注解接收，代码如下：
        /**
         *       doPost("/api/activity/remark", {
         *         activityId : this.activityDetail.id,
         *         noteContent : this.activityRemark.noteContent
         *       })
         */
        //2、前端axios的post提交一个formData，此时可以使用@RequestParam或者XxxQuery对象接收，代码如下：
        /**
         *       doPost("/api/user", formData)
         */
        tranRemarkQuery.setToken(token);
        int save = tranRemarkService.saveTranRemark(tranRemarkQuery);

        return save >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 分页查询交易跟踪记录
     *
     * @param tranId
     * @param current
     * @return
     */
    @GetMapping(value = "/api/tran/{tranId}/remark")
    public R tranRemarkPage(@PathVariable(value = "tranId") Integer tranId,
                            @RequestParam(value = "current") Integer current) {
        if (current == null) {
            current = 1;
        }

        PageInfo<TTranRemark> pageInfo = tranRemarkService.getTranRemarkByPage(current, tranId);
        return R.OK(pageInfo);
    }

}
