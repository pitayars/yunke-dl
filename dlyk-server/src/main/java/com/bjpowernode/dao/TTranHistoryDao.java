package com.bjpowernode.dao;

import com.bjpowernode.domain.po.TTranHistory;

import java.util.List;

public interface TTranHistoryDao {

    int deleteByPrimaryKey(Integer id);

    int insert(TTranHistory record);

    int insertSelective(TTranHistory record);

    TTranHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TTranHistory record);

    int updateByPrimaryKey(TTranHistory record);

    List<TTranHistory> selectByTranId(Integer tranId);
}