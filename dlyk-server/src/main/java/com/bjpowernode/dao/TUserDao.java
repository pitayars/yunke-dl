package com.bjpowernode.dao;

import com.bjpowernode.commons.DataScope;
import com.bjpowernode.domain.Base;
import com.bjpowernode.domain.po.TUser;

import java.util.List;

public interface TUserDao {

    int deleteByPrimaryKey(Integer id);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);

    TUser selectByUserName(String username);

    @DataScope(tableAlias = "tu", tableField = "id")
    List<TUser> selectUserPage(Base base);

    TUser selectDetailByPrimaryKey(Integer id);

    int deleteByBatchId(List<String> idList);

    List<TUser> selectUserOwner();
}