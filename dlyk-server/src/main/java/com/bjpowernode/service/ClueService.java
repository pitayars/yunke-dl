package com.bjpowernode.service;

import com.bjpowernode.domain.po.TClue;
import com.bjpowernode.domain.query.ClueQuery;
import com.github.pagehelper.PageInfo;

import java.io.InputStream;
import java.util.List;

public interface ClueService {

    PageInfo<TClue> getClueByPage(Integer current);

    int checkPhoneByCount(String phone);

    int saveClue(ClueQuery clueQuery);

    TClue getClueById(Integer id);

    int updateClue(ClueQuery clueQuery);

    void importExcel(InputStream inputStream, String token);

    int delClueById(Integer id);

    int batchDelClueByIds(List<String> idList);
}
