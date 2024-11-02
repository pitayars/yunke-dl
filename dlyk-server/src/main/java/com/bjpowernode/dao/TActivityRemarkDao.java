package com.bjpowernode.dao;

import com.bjpowernode.domain.po.TActivityRemark;

import java.util.List;

public interface TActivityRemarkDao {

    int deleteByPrimaryKey(Integer id);

    int insert(TActivityRemark record);

    int insertSelective(TActivityRemark record);

    TActivityRemark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TActivityRemark record);

    int updateByPrimaryKey(TActivityRemark record);

    List<TActivityRemark> selectActivityRemarkPage(Integer activityId);
}