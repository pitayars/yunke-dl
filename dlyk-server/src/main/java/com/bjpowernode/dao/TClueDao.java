package com.bjpowernode.dao;

import com.bjpowernode.domain.po.TClue;
import com.bjpowernode.domain.result.NameValue;
import com.bjpowernode.domain.result.TimeValue;

import java.util.List;

public interface TClueDao {

    int deleteByPrimaryKey(Integer id);

    int insert(TClue record);

    int insertSelective(TClue record);

    TClue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TClue record);

    int updateByPrimaryKey(TClue record);

    List<TClue> selectCluePage();

    int selectCluePhoneByCount(String phone);

    void insertClueByBatch(List<TClue> tClueList);

    int deleteByBatchId(List<String> idList);

    Integer selectClueByCount();

    List<NameValue> selectBySourceGroup();

    List<TimeValue> selectClueByDay();

}