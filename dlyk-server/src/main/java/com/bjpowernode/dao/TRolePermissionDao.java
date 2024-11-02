package com.bjpowernode.dao;

import com.bjpowernode.domain.po.TRolePermission;

public interface TRolePermissionDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TRolePermission record);

    int insertSelective(TRolePermission record);

    TRolePermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TRolePermission record);

    int updateByPrimaryKey(TRolePermission record);
}