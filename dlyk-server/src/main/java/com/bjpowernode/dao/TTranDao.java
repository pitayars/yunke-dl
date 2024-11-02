package com.bjpowernode.dao;

import com.bjpowernode.domain.po.TTran;
import com.bjpowernode.domain.result.TimeValue;

import java.math.BigDecimal;
import java.util.List;

public interface TTranDao {

    int deleteByPrimaryKey(Integer id);

    int insert(TTran record);

    int insertSelective(TTran record);

    TTran selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TTran record);

    int updateByPrimaryKey(TTran record);

    List<TTran> selectTranPage();

    TTran selectById(Integer tranId);

    BigDecimal selectTranBySucessAmount();

    BigDecimal selectTranByTotalAmount();

    Integer selectTranByCount();

    Integer selectSuccessTranByCount();

    List<TimeValue> selectTranByDay();

    List<TimeValue> selectSuccessTranByDay();
}