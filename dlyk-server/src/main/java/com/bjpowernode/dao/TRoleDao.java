package com.bjpowernode.dao;

import com.bjpowernode.domain.po.TRole;

import java.util.List;

public interface TRoleDao {

    int deleteByPrimaryKey(Integer id);

    int insert(TRole record);

    int insertSelective(TRole record);

    TRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TRole record);

    int updateByPrimaryKey(TRole record);

    List<TRole> selectByUserId(Integer userId);
}