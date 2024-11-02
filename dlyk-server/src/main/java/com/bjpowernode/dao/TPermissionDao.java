package com.bjpowernode.dao;

import com.bjpowernode.domain.po.TPermission;

import java.util.List;

public interface TPermissionDao {

    int deleteByPrimaryKey(Integer id);

    int insert(TPermission record);

    int insertSelective(TPermission record);

    TPermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TPermission record);

    int updateByPrimaryKey(TPermission record);

    List<TPermission> selectByUserId(Integer userId);

    List<TPermission> selectMenuByUserId(Integer userId);
}