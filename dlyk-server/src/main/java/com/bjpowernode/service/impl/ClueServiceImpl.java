package com.bjpowernode.service.impl;

import com.alibaba.excel.EasyExcel;
import com.bjpowernode.config.listener.UploadDataListener;
import com.bjpowernode.constant.Constants;
import com.bjpowernode.dao.TClueDao;
import com.bjpowernode.domain.po.TActivity;
import com.bjpowernode.domain.po.TClue;
import com.bjpowernode.domain.po.TUser;
import com.bjpowernode.domain.query.ClueQuery;
import com.bjpowernode.service.ClueService;
import com.bjpowernode.util.JWTUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class ClueServiceImpl implements ClueService {

    @Resource
    private TClueDao tClueDao;

    @Override
    public PageInfo<TClue> getClueByPage(Integer current) {
        //1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        //2.查询
        List<TClue> list = tClueDao.selectCluePage();
        //3.封装分页数据到PageInfo
        PageInfo<TClue> info = new PageInfo<>(list);

        return info;
    }

    /**
     * 验证手机号是否录入过
     *
     * @param phone
     * @return
     */
    @Override
    public int checkPhoneByCount(String phone) {
        return tClueDao.selectCluePhoneByCount(phone);
    }

    /**
     * 录入线索提交保存
     *
     * @param clueQuery
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveClue(ClueQuery clueQuery) {
        TClue tClue = new TClue();

        //把前端提交过来的参数数据对象ClueQuery复制到TClue对象中
        //Spring框架有个工具类BeanUtils可以进行对象的复制,复制的条件要求是：两个对象的字段名要相同，字段的类型也相同，这样才可以复制
        BeanUtils.copyProperties(clueQuery, tClue);

        //解析jwt得到userId
        Integer loginUserId = JWTUtils.parseJWTByUserId(clueQuery.getToken());

        tClue.setCreateTime(new Date()); //创建时间
        tClue.setCreateBy(loginUserId); //创建人id

        return tClueDao.insertSelective(tClue);
    }

    /**
     * 根据id查询线索详情
     *
     * @param id
     * @return
     */
    @Override
    public TClue getClueById(Integer id) {
        return tClueDao.selectByPrimaryKey(id);
    }

    /**
     * 编辑线索
     *
     * @param clueQuery
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateClue(ClueQuery clueQuery) {
        TClue tClue = new TClue();

        //把前端提交过来的参数数据对象ClueQuery复制到TClue对象中
        //Spring框架有个工具类BeanUtils可以进行对象的复制,复制的条件要求是：两个对象的字段名要相同，字段的类型也相同，这样才可以复制
        BeanUtils.copyProperties(clueQuery, tClue);

        //解析jwt得到userId
        Integer loginUserId = JWTUtils.parseJWTByUserId(clueQuery.getToken());

        tClue.setEditTime(new Date()); //编辑时间
        tClue.setEditBy(loginUserId); //编辑人id

        return tClueDao.updateByPrimaryKeySelective(tClue);
    }

    /**
     * 上传和读取Excel文件
     *
     * @param inputStream
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void importExcel(InputStream inputStream, String token) {
        //链式编程，三个参数，1、要读取的文件，2、Excel头部所有字段名的描述模板类， 2、读取每一行Excel数据时触发该监听器
        EasyExcel.read(inputStream, TClue.class, new UploadDataListener(tClueDao, token))
                .sheet()
                .doRead();
    }

    /**
     * 根据id删除线索
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delClueById(Integer id) {
        return tClueDao.deleteByPrimaryKey(id);
    }

    /**
     * 根据id批量删除线索
     *
     * @param idList
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchDelClueByIds(List<String> idList) {
        return tClueDao.deleteByBatchId(idList);
    }
}
