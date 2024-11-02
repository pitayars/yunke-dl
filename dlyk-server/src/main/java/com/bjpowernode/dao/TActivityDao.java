package com.bjpowernode.dao;

import com.bjpowernode.domain.po.TActivity;
import com.bjpowernode.domain.po.TUser;
import com.bjpowernode.domain.query.ActivityQuery;
import com.bjpowernode.domain.result.TimeValue;

import java.util.List;

public interface TActivityDao {

    int deleteByPrimaryKey(Integer id);

    int insert(TActivity record);

    int insertSelective(TActivity record);

    TActivity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TActivity record);

    int updateByPrimaryKey(TActivity record);

    List<TActivity> selectActivityPage(ActivityQuery activityQuery);

    TActivity selectDetailByPrimaryKey(Integer id);

    int deleteByBatchId(List<String> idList);

    List<TActivity> selectActivityByEffective();

    List<TActivity> selectActivityByAll();

    List<TimeValue> selectActivityByMonth();
}