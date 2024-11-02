package com.bjpowernode.service.impl;

import com.bjpowernode.constant.Constants;
import com.bjpowernode.dao.TClueRemarkDao;
import com.bjpowernode.domain.po.TActivityRemark;
import com.bjpowernode.domain.po.TClueRemark;
import com.bjpowernode.domain.query.ClueRemarkQuery;
import com.bjpowernode.service.ClueRemarkService;
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
public class ClueRemarkServiceImpl implements ClueRemarkService {

    @Resource
    private TClueRemarkDao tClueRemarkDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveClueRemark(ClueRemarkQuery clueRemarkQuery) {
        TClueRemark tClueRemark = new TClueRemark();

        //把前端提交过来的参数数据对象ClueRemarkQuery复制到TClueRemark对象中
        //Spring框架有个工具类BeanUtils可以进行对象的复制,复制的条件要求是：两个对象的字段名要相同，字段的类型也相同，这样才可以复制
        BeanUtils.copyProperties(clueRemarkQuery, tClueRemark);

        //解析jwt得到userId
        Integer loginUserId = JWTUtils.parseJWTByUserId(clueRemarkQuery.getToken());

        tClueRemark.setCreateTime(new Date()); //创建时间
        tClueRemark.setCreateBy(loginUserId); //创建人id

        return tClueRemarkDao.insertSelective(tClueRemark);
    }

    /**
     * 分页查询线索跟踪记录
     *
     * @param current
     * @param clueId
     * @return
     */
    @Override
    public PageInfo<TClueRemark> getClueRemarkByPage(Integer current, Integer clueId) {
        //1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        //2.查询
        List<TClueRemark> list = tClueRemarkDao.selectClueRemarkPage(clueId);
        //3.封装分页数据到PageInfo
        PageInfo<TClueRemark> info = new PageInfo<>(list);

        return info;
    }
}
