package com.bjpowernode.dao;

import com.bjpowernode.domain.po.TUserRole;

public interface TUserRoleDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TUserRole record);

    int insertSelective(TUserRole record);

    TUserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TUserRole record);

    int updateByPrimaryKey(TUserRole record);
}