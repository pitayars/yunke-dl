package com.bjpowernode.dao;

import com.bjpowernode.domain.po.TProduct;

import java.util.List;

public interface TProductDao {

    int deleteByPrimaryKey(Integer id);

    int insert(TProduct record);

    int insertSelective(TProduct record);

    TProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TProduct record);

    int updateByPrimaryKey(TProduct record);

    List<TProduct> selectProductBySale();
}