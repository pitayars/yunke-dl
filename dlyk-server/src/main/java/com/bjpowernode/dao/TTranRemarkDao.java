package com.bjpowernode.dao;

import com.bjpowernode.domain.po.TTranRemark;

import java.util.List;

public interface TTranRemarkDao {

    int deleteByPrimaryKey(Integer id);

    int insert(TTranRemark record);

    int insertSelective(TTranRemark record);

    TTranRemark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TTranRemark record);

    int updateByPrimaryKey(TTranRemark record);

    List<TTranRemark> selectTranRemarkPage(Integer tranId);
}