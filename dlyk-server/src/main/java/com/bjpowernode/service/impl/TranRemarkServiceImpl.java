package com.bjpowernode.service.impl;

import com.bjpowernode.constant.Constants;
import com.bjpowernode.dao.TTranDao;
import com.bjpowernode.dao.TTranRemarkDao;
import com.bjpowernode.domain.po.TClueRemark;
import com.bjpowernode.domain.po.TCustomerRemark;
import com.bjpowernode.domain.po.TTranRemark;
import com.bjpowernode.domain.query.TranRemarkQuery;
import com.bjpowernode.service.TranRemarkService;
import com.bjpowernode.util.JWTUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TranRemarkServiceImpl implements TranRemarkService {

    @Resource
    private TTranRemarkDao tTranRemarkDao;

    /**
     * 提交交易的跟踪记录
     *
     * @param tranRemarkQuery
     * @return
     */
    @Override
    public int saveTranRemark(TranRemarkQuery tranRemarkQuery) {
        TTranRemark tTranRemark = new TTranRemark();

        //把前端提交过来的参数数据对象TranRemarkQuery复制到TTranRemark对象中
        //Spring框架有个工具类BeanUtils可以进行对象的复制,复制的条件要求是：两个对象的字段名要相同，字段的类型也相同，这样才可以复制
        BeanUtils.copyProperties(tranRemarkQuery, tTranRemark);

        //解析jwt得到userId
        Integer loginUserId = JWTUtils.parseJWTByUserId(tranRemarkQuery.getToken());

        tTranRemark.setCreateTime(new Date()); //创建时间
        tTranRemark.setCreateBy(loginUserId); //创建人id

        return tTranRemarkDao.insertSelective(tTranRemark);
    }

    /**
     * 分页查询交易跟踪记录
     *
     * @param current
     * @param tranId
     * @return
     */
    @Override
    public PageInfo<TTranRemark> getTranRemarkByPage(Integer current, Integer tranId) {
        //1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        //2.查询
        List<TTranRemark> list = tTranRemarkDao.selectTranRemarkPage(tranId);
        //3.封装分页数据到PageInfo
        PageInfo<TTranRemark> info = new PageInfo<>(list);

        return info;
    }
}
