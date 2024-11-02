package com.bjpowernode.service.impl;

import com.bjpowernode.constant.Constants;
import com.bjpowernode.dao.TActivityRemarkDao;
import com.bjpowernode.domain.po.TActivityRemark;
import com.bjpowernode.domain.po.TUser;
import com.bjpowernode.domain.query.ActivityRemarkQuery;
import com.bjpowernode.service.ActivityRemarkService;
import com.bjpowernode.util.JWTUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {

    @Resource
    private TActivityRemarkDao tActivityRemarkDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveActivityRemark(ActivityRemarkQuery activityRemarkQuery) {
        TActivityRemark tActivityRemark = new TActivityRemark();

        //把前端提交过来的参数数据对象UserQuery复制到TUser对象中
        //Spring框架有个工具类BeanUtils可以进行对象的复制,复制的条件要求是：两个对象的字段名要相同，字段的类型也相同，这样才可以复制
        BeanUtils.copyProperties(activityRemarkQuery, tActivityRemark);

        //解析jwt得到userId
        Integer loginUserId = JWTUtils.parseJWTByUserId(activityRemarkQuery.getToken());

        tActivityRemark.setCreateTime(new Date()); //创建时间
        tActivityRemark.setCreateBy(loginUserId); //创建人id

        return tActivityRemarkDao.insertSelective(tActivityRemark);
    }

    /**
     * 分页查询市场活动备注记录
     *
     * @param current
     * @return
     */
    @Override
    public PageInfo<TActivityRemark> getActivityRemarkByPage(Integer current, Integer activityId) {
        //1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        //2.查询
        List<TActivityRemark> list = tActivityRemarkDao.selectActivityRemarkPage(activityId);
        //3.封装分页数据到PageInfo
        PageInfo<TActivityRemark> info = new PageInfo<>(list);

        return info;
    }

    /**
     * 根据id查询市场活动备注记录详情
     *
     * @param id
     * @return
     */
    @Override
    public TActivityRemark getActivityRemarkById(Integer id) {
        return tActivityRemarkDao.selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateActivityRemark(ActivityRemarkQuery activityRemarkQuery) {
        TActivityRemark tActivityRemark = new TActivityRemark();

        //把前端提交过来的参数数据对象ActivityRemarkQuery复制到TActivityRemark对象中
        //Spring框架有个工具类BeanUtils可以进行对象的复制,复制的条件要求是：两个对象的字段名要相同，字段的类型也相同，这样才可以复制
        BeanUtils.copyProperties(activityRemarkQuery, tActivityRemark);

        //解析jwt得到userId
        Integer loginUserId = JWTUtils.parseJWTByUserId(activityRemarkQuery.getToken());

        tActivityRemark.setEditTime(new Date()); //编辑时间
        tActivityRemark.setEditBy(loginUserId); //编辑人id

        return tActivityRemarkDao.updateByPrimaryKeySelective(tActivityRemark);
    }

    /**
     * 根据id删除市场活动备注记录
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delActivityRemarkById(Integer id) {
        //逻辑删除：数据没有删，数据还在表里面，只是修改了数据的某个状态字段；deleted = 0 正常， 1删除
        //物理删除：数据真正地删除了，数据表里面已经没有这条数据了
        TActivityRemark tActivityRemark = new TActivityRemark();
        tActivityRemark.setId(id);
        tActivityRemark.setDeleted(1);
        return tActivityRemarkDao.updateByPrimaryKeySelective(tActivityRemark);
    }
}
