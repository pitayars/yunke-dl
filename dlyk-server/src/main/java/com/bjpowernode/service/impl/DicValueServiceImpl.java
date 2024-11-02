package com.bjpowernode.service.impl;

import com.bjpowernode.dao.TDicValueDao;
import com.bjpowernode.domain.po.TDicValue;
import com.bjpowernode.service.DicValueService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DicValueServiceImpl implements DicValueService {

    @Resource
    private TDicValueDao tDicValueDao;

    @Override
    public List<TDicValue> getDicValueByTypeCode(String typeCode) {
        return tDicValueDao.selectByTypeCode(typeCode);
    }
}
