package com.bjpowernode.dao;

import com.bjpowernode.domain.po.TDicValue;

import java.util.List;

public interface TDicValueDao {

    int deleteByPrimaryKey(Integer id);

    int insert(TDicValue record);

    int insertSelective(TDicValue record);

    TDicValue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TDicValue record);

    int updateByPrimaryKey(TDicValue record);

    List<TDicValue> selectByTypeCode(String typeCode);
}