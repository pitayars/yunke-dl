package com.bjpowernode.dao;

import com.bjpowernode.domain.po.TClueRemark;

import java.util.List;

public interface TClueRemarkDao {

    int deleteByPrimaryKey(Integer id);

    int insert(TClueRemark record);

    int insertSelective(TClueRemark record);

    TClueRemark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TClueRemark record);

    int updateByPrimaryKey(TClueRemark record);

    List<TClueRemark> selectClueRemarkPage(Integer clueId);
}