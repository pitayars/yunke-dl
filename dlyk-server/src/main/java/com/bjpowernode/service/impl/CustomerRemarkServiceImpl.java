package com.bjpowernode.service.impl;

import com.bjpowernode.constant.Constants;
import com.bjpowernode.dao.TCustomerRemarkDao;
import com.bjpowernode.domain.po.TClueRemark;
import com.bjpowernode.domain.po.TCustomerRemark;
import com.bjpowernode.domain.query.CustomerRemarkQuery;
import com.bjpowernode.service.CustomerRemarkService;
import com.bjpowernode.util.JWTUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerRemarkServiceImpl implements CustomerRemarkService {

    @Resource
    private TCustomerRemarkDao tCustomerRemarkDao;

    @Override
    public PageInfo<TCustomerRemark> getCustomerRemarkByPage(Integer current, Integer customerId) {
        //1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        //2.查询
        List<TCustomerRemark> list = tCustomerRemarkDao.selectCustomerRemarkPage(customerId);
        //3.封装分页数据到PageInfo
        PageInfo<TCustomerRemark> info = new PageInfo<>(list);

        return info;
    }

    @Override
    public int saveCustomerRemark(CustomerRemarkQuery customerRemarkQuery) {
        TCustomerRemark tCustomerRemark = new TCustomerRemark();

        //把前端提交过来的参数数据对象CustomerRemarkQuery复制到TCustomerRemark对象中
        //Spring框架有个工具类BeanUtils可以进行对象的复制,复制的条件要求是：两个对象的字段名要相同，字段的类型也相同，这样才可以复制
        BeanUtils.copyProperties(customerRemarkQuery, tCustomerRemark);

        //解析jwt得到userId
        Integer loginUserId = JWTUtils.parseJWTByUserId(customerRemarkQuery.getToken());

        tCustomerRemark.setCreateTime(new Date()); //创建时间
        tCustomerRemark.setCreateBy(loginUserId); //创建人id

        return tCustomerRemarkDao.insertSelective(tCustomerRemark);
    }
}
