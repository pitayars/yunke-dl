package com.bjpowernode.web;

import com.bjpowernode.domain.po.TClueRemark;
import com.bjpowernode.domain.po.TCustomerRemark;
import com.bjpowernode.domain.query.ClueRemarkQuery;
import com.bjpowernode.domain.query.CustomerRemarkQuery;
import com.bjpowernode.domain.result.R;
import com.bjpowernode.service.CustomerRemarkService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerRemarkController {

    @Resource
    private CustomerRemarkService customerRemarkService;

    /**
     * 分页查询线索跟踪记录
     *
     * @param customerId
     * @param current
     * @return
     */
    @GetMapping(value = "/api/customer/{customerId}/remark")
    public R customerRemarkPage(@PathVariable(value = "customerId") Integer customerId,
                            @RequestParam(value = "current") Integer current) {
        if (current == null) {
            current = 1;
        }

        PageInfo<TCustomerRemark> pageInfo = customerRemarkService.getCustomerRemarkByPage(current, customerId);
        return R.OK(pageInfo);
    }

    /**
     * 提交客户跟踪记录（保存）
     *
     * @param customerRemarkQuery
     * @param token
     * @return
     */
    @PostMapping(value = "/api/customer/remark")
    public R addCustomerRemark(@RequestBody CustomerRemarkQuery customerRemarkQuery,
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
        customerRemarkQuery.setToken(token);
        int save = customerRemarkService.saveCustomerRemark(customerRemarkQuery);

        return save >= 1 ? R.OK() : R.FAIL();
    }
}
