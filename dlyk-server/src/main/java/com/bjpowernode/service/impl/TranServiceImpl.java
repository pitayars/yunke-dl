package com.bjpowernode.service.impl;

import com.bjpowernode.constant.Constants;
import com.bjpowernode.dao.TTranDao;
import com.bjpowernode.domain.po.TClueRemark;
import com.bjpowernode.domain.po.TTran;
import com.bjpowernode.domain.po.TUser;
import com.bjpowernode.domain.query.TranQuery;
import com.bjpowernode.manager.RedisManager;
import com.bjpowernode.service.TranService;
import com.bjpowernode.util.JWTUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TranServiceImpl implements TranService {

    @Resource
    private TTranDao tTranDao;

    @Resource
    private RedisManager redisManager;

    /**
     * 创建交易
     *
     * @param tranQuery
     * @return
     */
    @Override
    public int saveTran(TranQuery tranQuery) {
        TTran tTran = new TTran();

        //把前端提交过来的参数数据对象TranQuery复制到TTran对象中
        //Spring框架有个工具类BeanUtils可以进行对象的复制,复制的条件要求是：两个对象的字段名要相同，字段的类型也相同，这样才可以复制
        BeanUtils.copyProperties(tranQuery, tTran);

        String tranNo = redisManager.getOnlyNumber(Constants.REDIS_ONLY_NUMBER_KEY); //交易流水号是不能重复的
        tTran.setTranNo(tranNo);

        //解析jwt得到userId
        Integer loginUserId = JWTUtils.parseJWTByUserId(tranQuery.getToken());

        tTran.setCreateTime(new Date()); //创建时间
        tTran.setCreateBy(loginUserId); //创建人id

        return tTranDao.insertSelective(tTran);
    }

    /**
     * 分页查询交易列表数据
     *
     * @param current
     * @return
     */
    @Override
    public PageInfo<TTran> getTranByPage(Integer current) {
        //1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        //2.查询
        List<TTran> list = tTranDao.selectTranPage();
        //3.封装分页数据到PageInfo
        PageInfo<TTran> info = new PageInfo<>(list);

        return info;
    }

    /**
     * 查询交易详情
     *
     * @param tranId
     * @return
     */
    @Override
    public TTran getTranById(Integer tranId) {
        return tTranDao.selectById(tranId);
    }
}
