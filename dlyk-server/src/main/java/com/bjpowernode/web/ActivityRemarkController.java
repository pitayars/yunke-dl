package com.bjpowernode.web;

import com.bjpowernode.domain.po.TActivityRemark;
import com.bjpowernode.domain.po.TUser;
import com.bjpowernode.domain.query.ActivityRemarkQuery;
import com.bjpowernode.domain.result.R;
import com.bjpowernode.service.ActivityRemarkService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActivityRemarkController {

    @Resource
    private ActivityRemarkService activityRemarkService;

    @PostMapping(value = "/api/activity/remark")
    public R addActivityRemark(@RequestBody ActivityRemarkQuery activityRemarkQuery,
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
        activityRemarkQuery.setToken(token);
        int save = activityRemarkService.saveActivityRemark(activityRemarkQuery);

        return save >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 分页查询市场活动备注记录
     *
     * @param activityId
     * @param current
     * @return
     */
    @GetMapping(value = "/api/activity/{activityId}/remark")
    public R activityRemarkPage(@PathVariable(value = "activityId") Integer activityId,
                                @RequestParam(value = "current") Integer current) {
        if (current == null) {
            current = 1;
        }

        PageInfo<TActivityRemark> pageInfo = activityRemarkService.getActivityRemarkByPage(current, activityId);
        return R.OK(pageInfo);
    }

    /**
     * 根据id查询市场活动备注记录详情
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/api/activity/remark/{id}")
    public R activityRemarkDetail(@PathVariable(value = "id") Integer id) {
        TActivityRemark tActivityRemark = activityRemarkService.getActivityRemarkById(id);
        return R.OK(tActivityRemark);
    }

    /**
     * 编辑市场活动备注记录
     *
     * @param activityRemarkQuery
     * @param token
     * @return
     */
    @PutMapping(value = "/api/activity/remark")
    public R updateActivityRemark(@RequestBody ActivityRemarkQuery activityRemarkQuery,
                               @RequestHeader(value = "Authorization") String token) {
        //1、前端axios的put提交过来的参数，是一个json，后端接收要使用@RequestBody注解接收，代码如下：
        /**
         *       doPut("/api/activity/remark", {
         *         id : this.activityRemarkQuery.id,
         *         noteContent : this.activityRemarkQuery.noteContent
         *       })
         */
        //2、前端axios的post提交一个formData，此时可以使用@RequestParam或者XxxQuery对象接收，代码如下：
        /**
         *       doPost("/api/user", formData)
         */
        activityRemarkQuery.setToken(token);
        int update = activityRemarkService.updateActivityRemark(activityRemarkQuery);

        return update >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 根据id删除市场活动备注记录
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/api/activity/remark/{id}")
    public R delActivityRemark(@PathVariable(value = "id") Integer id) {
        int del = activityRemarkService.delActivityRemarkById(id);
        return del >= 1 ? R.OK() : R.FAIL();
    }
}
