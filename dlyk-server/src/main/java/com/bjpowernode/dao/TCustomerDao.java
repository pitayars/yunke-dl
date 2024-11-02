package com.bjpowernode.dao;

import com.bjpowernode.domain.po.TCustomer;
import com.bjpowernode.domain.result.CustomerExcel;
import com.bjpowernode.domain.result.TimeValue;

import java.util.List;

public interface TCustomerDao {
    
    int deleteByPrimaryKey(Integer id);

    int insert(TCustomer record);

    int insertSelective(TCustomer record);

    TCustomer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TCustomer record);

    int updateByPrimaryKey(TCustomer record);

    List<TCustomer> selectCustomerPage();

    List<TCustomer> selectCustomerByExcel(List<String> idList);

    Integer selectCustomerByCount();

    List<TimeValue> selectCustomerByDay();
}