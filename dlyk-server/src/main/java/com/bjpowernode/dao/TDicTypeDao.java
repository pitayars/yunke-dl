package com.bjpowernode.dao;

import com.bjpowernode.domain.po.TDicType;

import java.util.List;

public interface TDicTypeDao {

    int deleteByPrimaryKey(Integer id);

    int insert(TDicType record);

    int insertSelective(TDicType record);

    TDicType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TDicType record);

    int updateByPrimaryKey(TDicType record);

    List<TDicType> selectAllDicTypeAndDicValue();
}