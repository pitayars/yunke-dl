package com.bjpowernode.service.impl;

import com.bjpowernode.constant.Constants;
import com.bjpowernode.dao.TActivityDao;
import com.bjpowernode.domain.po.TActivity;
import com.bjpowernode.domain.po.TActivityRemark;
import com.bjpowernode.domain.po.TUser;
import com.bjpowernode.domain.query.ActivityQuery;
import com.bjpowernode.service.ActivityService;
import com.bjpowernode.util.JWTUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private TActivityDao tActivityDao;

    @Override
    public PageInfo<TActivity> getActivityByPage(Integer current, ActivityQuery activityQuery) {
        //1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        //2.查询
        List<TActivity> list = tActivityDao.selectActivityPage(activityQuery);
        //3.封装分页数据到PageInfo
        PageInfo<TActivity> info = new PageInfo<>(list);

        return info;
    }

    /**
     * 根据id查询市场活动详情
     *
     * @param id
     * @return
     */
    @Override
    public TActivity getActivityById(Integer id) {
        return tActivityDao.selectDetailByPrimaryKey(id);
    }

    /**
     * 录入市场活动
     *
     * @param activityQuery
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveActivity(ActivityQuery activityQuery) {
        TActivity tActivity = new TActivity();

        //把前端提交过来的参数数据对象activityQuery复制到TActivity对象中
        //Spring框架有个工具类BeanUtils可以进行对象的复制,复制的条件要求是：两个对象的字段名要相同，字段的类型也相同，这样才可以复制
        BeanUtils.copyProperties(activityQuery, tActivity);

        //解析jwt得到userId
        Integer loginUserId = JWTUtils.parseJWTByUserId(activityQuery.getToken());

        tActivity.setCreateTime(new Date()); //创建时间
        tActivity.setCreateBy(loginUserId); //创建人id

        return tActivityDao.insertSelective(tActivity);
    }

    /**
     * 编辑市场活动
     *
     * @param activityQuery
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateActivity(ActivityQuery activityQuery) {
        TActivity tActivity = new TActivity();

        //把前端提交过来的参数数据对象ActivityQuery复制到TTActivity对象中
        //Spring框架有个工具类BeanUtils可以进行对象的复制,复制的条件要求是：两个对象的字段名要相同，字段的类型也相同，这样才可以复制
        BeanUtils.copyProperties(activityQuery, tActivity);

        //解析jwt得到userId
        Integer loginUserId = JWTUtils.parseJWTByUserId(activityQuery.getToken());

        tActivity.setEditTime(new Date()); //编辑时间
        tActivity.setEditBy(loginUserId); //编辑人id

        return tActivityDao.updateByPrimaryKeySelective(tActivity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delActivityById(Integer id) {
        return tActivityDao.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchDelActivityByIds(List<String> idList) {
        return tActivityDao.deleteByBatchId(idList);
    }

    /**
     * 查询有效的市场活动
     *
     * @return
     */
    @Override
    public List<TActivity> getActivityByEffective() {
        return tActivityDao.selectActivityByEffective();
    }

    /**
     * 查询所有的市场活动
     *
     * @return
     */
    @Override
    public List<TActivity> getActivityByAll() {
        return tActivityDao.selectActivityByAll();
    }
}
