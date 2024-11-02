package com.bjpowernode.service.impl;

import com.bjpowernode.dao.TDicTypeDao;
import com.bjpowernode.domain.po.TDicType;
import com.bjpowernode.service.DicTypeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DicTypeServiceImpl implements DicTypeService {

    @Resource
    private TDicTypeDao tDicTypeDao;

    /**
     * 关联查询所有的字典类型及字典值
     *
     * @return
     */
    @Override
    public List<TDicType> getAllDicTypeAndDicValue() {
        return tDicTypeDao.selectAllDicTypeAndDicValue();
    }
}
