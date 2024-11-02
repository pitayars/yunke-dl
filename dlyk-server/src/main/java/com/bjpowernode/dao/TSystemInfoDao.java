package com.bjpowernode.dao;

import com.bjpowernode.domain.po.TSystemInfo;

public interface TSystemInfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TSystemInfo record);

    int insertSelective(TSystemInfo record);

    TSystemInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TSystemInfo record);

    int updateByPrimaryKey(TSystemInfo record);
}