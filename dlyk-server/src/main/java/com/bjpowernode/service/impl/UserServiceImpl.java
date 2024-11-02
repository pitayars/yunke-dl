package com.bjpowernode.service.impl;

import com.bjpowernode.constant.Constants;
import com.bjpowernode.dao.TPermissionDao;
import com.bjpowernode.dao.TRoleDao;
import com.bjpowernode.dao.TUserDao;
import com.bjpowernode.domain.Base;
import com.bjpowernode.domain.po.TPermission;
import com.bjpowernode.domain.po.TRole;
import com.bjpowernode.domain.po.TUser;
import com.bjpowernode.domain.query.UserQuery;
import com.bjpowernode.manager.RedisManager;
import com.bjpowernode.service.UserService;
import com.bjpowernode.util.CacheUtils;
import com.bjpowernode.util.JWTUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private TUserDao tUserDao;

    @Resource
    private TRoleDao tRoleDao;

    @Resource
    private TPermissionDao tPermissionDao;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private RedisManager redisManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //这里面去数据库查询一下用户即可
        TUser tUser = tUserDao.selectByUserName(username);

        //查询一下用户的角色
        List<TRole> tRoleList = tRoleDao.selectByUserId(tUser.getId());

        List<String> roleStringList = new ArrayList<>();

        tRoleList.forEach( tRole -> {
            if (StringUtils.hasText(tRole.getRole())) {
                roleStringList.add(tRole.getRole());
            }
        });

        //查询一下用户的权限
        List<TPermission> tPermissionList = tPermissionDao.selectByUserId(tUser.getId());
        List<String> permissionStringList = new ArrayList<>();

        tPermissionList.forEach( tPermission -> {
            if (StringUtils.hasText(tPermission.getCode())) {
                permissionStringList.add(tPermission.getCode());
            }
        });

        tUser.setStringRoleList(roleStringList); //用于数据权限过滤
        tUser.setStringAuthorityList(permissionStringList); //基于权限的权限管理

        return tUser;
    }

    /**
     * 用户数据分页查询
     *
     * @param current
     */
    @Override
    public PageInfo<TUser> getUserByPage(Integer current) {
        //1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        //2.查询
        List<TUser> list = tUserDao.selectUserPage(Base.builder().build());
        //3.封装分页数据到PageInfo
        PageInfo<TUser> info = new PageInfo<>(list);

        return info;
    }

    /**
     * 根据id查询用户详情
     *
     * @param id
     * @return
     */
    @Override
    public TUser getUserById(Integer id) {
        TUser tUser = tUserDao.selectDetailByPrimaryKey(id);
        if (ObjectUtils.isEmpty(tUser.getCreateByPO())) {
            tUser.setCreateByPO(new TUser());
        }
        if (ObjectUtils.isEmpty(tUser.getEditByPO())) {
            tUser.setEditByPO(new TUser());
        }
        return tUser;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveUser(UserQuery userQuery) {
        TUser tUser = new TUser();

        //把前端提交过来的参数数据对象UserQuery复制到TUser对象中
        //Spring框架有个工具类BeanUtils可以进行对象的复制,复制的条件要求是：两个对象的字段名要相同，字段的类型也相同，这样才可以复制
        BeanUtils.copyProperties(userQuery, tUser);

        //解析jwt得到userId
        Integer loginUserId = JWTUtils.parseJWTByUserId(userQuery.getToken());

        tUser.setCreateTime(new Date()); //创建时间
        tUser.setCreateBy(loginUserId); //创建人id

        tUser.setLoginPwd(passwordEncoder.encode(userQuery.getLoginPwd())); //密码加密

        return tUserDao.insertSelective(tUser);
    }

    /**
     * 编辑用户提交保存
     *
     * @param userQuery
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateUser(UserQuery userQuery) {
        TUser tUser = new TUser();

        //把前端提交过来的参数数据对象UserQuery复制到TUser对象中
        //Spring框架有个工具类BeanUtils可以进行对象的复制,复制的条件要求是：两个对象的字段名要相同，字段的类型也相同，这样才可以复制
        BeanUtils.copyProperties(userQuery, tUser);

        //解析jwt得到userId
        Integer loginUserId = JWTUtils.parseJWTByUserId(userQuery.getToken());

        tUser.setEditTime(new Date()); //编辑时间
        tUser.setEditBy(loginUserId); //编辑人id

        if (StringUtils.hasText(userQuery.getLoginPwd())) {
            tUser.setLoginPwd(passwordEncoder.encode(userQuery.getLoginPwd())); //密码加密
        }
        return tUserDao.updateByPrimaryKeySelective(tUser);
    }

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delUserById(Integer id) {
        return tUserDao.deleteByPrimaryKey(id);
    }

    /**
     * 根据id批量删除用户
     *
     * @param idList
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchDelUserByIds(List<String> idList) {
        return tUserDao.deleteByBatchId(idList);
    }

    /**
     * 查询所有负责人
     *
     * @return
     */
    @Override
    public List<TUser> getUserOwner() {
        List<TUser> tUserList = CacheUtils.getCacheData(
                () -> (List<TUser>)redisManager.getValue(Constants.REDIS_OWNER_KEY),
                () -> tUserDao.selectUserOwner(),
                (t) -> redisManager.setValue(Constants.REDIS_OWNER_KEY, t)
        );
        return tUserList;
    }

    @Override
    public int updateUserLoginTime(TUser tUser) {
        return tUserDao.updateByPrimaryKeySelective(tUser);
    }

    @Override
    public List<TPermission> getMenuByUserId(Integer userId) {
        return tPermissionDao.selectMenuByUserId(userId);
    }
}
