package com.bjpowernode.service;

import com.bjpowernode.domain.po.TActivityRemark;
import com.bjpowernode.domain.query.ActivityRemarkQuery;
import com.github.pagehelper.PageInfo;

public interface ActivityRemarkService {

    int saveActivityRemark(ActivityRemarkQuery activityRemarkQuery);

    PageInfo<TActivityRemark> getActivityRemarkByPage(Integer current, Integer activityId);

    TActivityRemark getActivityRemarkById(Integer id);

    int updateActivityRemark(ActivityRemarkQuery activityRemarkQuery);

    int delActivityRemarkById(Integer id);
}
