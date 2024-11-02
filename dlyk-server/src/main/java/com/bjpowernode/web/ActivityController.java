package com.bjpowernode.web;

import com.bjpowernode.domain.po.TActivity;
import com.bjpowernode.domain.po.TUser;
import com.bjpowernode.domain.query.ActivityQuery;
import com.bjpowernode.domain.query.UserQuery;
import com.bjpowernode.domain.result.R;
import com.bjpowernode.service.ActivityService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class ActivityController {

    @Resource
    private ActivityService activityService;

    /**
     * 市场活动数据分页查询
     *
     * @param current
     * @return
     */
    @GetMapping(value = "/api/activitys")
    public R activityPage(@RequestParam(value = "current", required = false) Integer current, ActivityQuery activityQuery) {
        // required = false, 表示current可以传也可以不传；
        // required = true, 表示current必须传，不传就报400错误
        if (current == null) {
            current = 1;
        }

        PageInfo<TActivity> pageInfo = activityService.getActivityByPage(current, activityQuery);
        return R.OK(pageInfo);
    }

    /**
     * 根据id查询市场活动详情
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/api/activity/{id}")
    public R activityDetail(@PathVariable(value = "id") Integer id) {
        TActivity tActivity = activityService.getActivityById(id);

        return R.OK(tActivity);
    }

    /**
     * 录入市场活动
     *
     * @param activityQuery
     * @param token
     * @return
     */
    @PostMapping(value = "/api/activity")
    public R addActivity(ActivityQuery activityQuery, @RequestHeader(value = "Authorization") String token) {
        //前端是通过 FormData 提交过来的参数，怎么接收，方法如下：
        //1、request.getParamter("xxx");
        //2、@RequestParam(value="xxx")
        //3、UserQuery对象接收（只要表单input框里面的字段名和UserQuery对象的属性名相同，就可以接收到）
        activityQuery.setToken(token);
        int save = activityService.saveActivity(activityQuery);

        return save >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 编辑市场活动
     *
     * @param activityQuery
     * @param token
     * @return
     */
    @PutMapping(value = "/api/activity")
    public R updateActivity(ActivityQuery activityQuery, @RequestHeader(value = "Authorization") String token) {
        //前端是通过 FormData 提交过来的参数，怎么接收，方法如下：
        //1、request.getParamter("xxx");
        //2、@RequestParam(value="xxx")
        //3、UserQuery对象接收（只要表单input框里面的字段名和UserQuery对象的属性名相同，就可以接收到）
        activityQuery.setToken(token);
        int update = activityService.updateActivity(activityQuery);

        return update >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 根据id删除市场活动
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/api/activity/{id}")
    public R delActivity(@PathVariable(value = "id") Integer id) {
        int del = activityService.delActivityById(id);
        return del >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 根据id批量删除市场活动
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/api/activity/batch")
    public R batchDelActivity(@RequestParam(value = "ids") String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        int batchDel = activityService.batchDelActivityByIds(idList);
        return batchDel >= idList.size() ? R.OK() : R.FAIL();
    }

    /**
     * 查询有效的市场活动
     *
     * @return
     */
    @GetMapping(value = "/api/activity/effective")
    public R effectiveActivity() {
        List<TActivity> tActivityList = activityService.getActivityByEffective();
        return R.OK(tActivityList);
    }

    /**
     * 查询有效的市场活动
     *
     * @return
     */
    @GetMapping(value = "/api/activity/all")
    public R allActivity() {
        List<TActivity> tActivityList = activityService.getActivityByAll();
        return R.OK(tActivityList);
    }
}
