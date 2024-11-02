package com.bjpowernode.dao;

import com.bjpowernode.domain.po.TCustomerRemark;

import java.util.List;

public interface TCustomerRemarkDao {

    int deleteByPrimaryKey(Integer id);

    int insert(TCustomerRemark record);

    int insertSelective(TCustomerRemark record);

    TCustomerRemark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TCustomerRemark record);

    int updateByPrimaryKey(TCustomerRemark record);

    List<TCustomerRemark> selectCustomerRemarkPage(Integer customerId);
}