package com.bjpowernode.service;

import com.bjpowernode.domain.po.TPermission;
import com.bjpowernode.domain.po.TUser;
import com.bjpowernode.domain.query.UserQuery;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    PageInfo<TUser> getUserByPage(Integer current);

    TUser getUserById(Integer id);

    int saveUser(UserQuery userQuery);

    int updateUser(UserQuery userQuery);

    int delUserById(Integer id);

    int batchDelUserByIds(List<String> idList);

    List<TUser> getUserOwner();

    int updateUserLoginTime(TUser tUser);

    List<TPermission> getMenuByUserId(Integer userId);
}
