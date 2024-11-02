package com.bjpowernode.service;

import com.bjpowernode.domain.po.TActivity;
import com.bjpowernode.domain.query.ActivityQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ActivityService {

    PageInfo<TActivity> getActivityByPage(Integer current, ActivityQuery activityQuery);

    TActivity getActivityById(Integer id);

    int saveActivity(ActivityQuery activityQuery);

    int updateActivity(ActivityQuery activityQuery);

    int delActivityById(Integer id);

    int batchDelActivityByIds(List<String> idList);

    List<TActivity> getActivityByEffective();

    List<TActivity> getActivityByAll();
}
