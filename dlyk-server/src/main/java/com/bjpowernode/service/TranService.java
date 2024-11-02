package com.bjpowernode.service;

import com.bjpowernode.domain.po.TTran;
import com.bjpowernode.domain.query.TranQuery;
import com.github.pagehelper.PageInfo;

public interface TranService {

    int saveTran(TranQuery tranQuery);

    PageInfo<TTran> getTranByPage(Integer current);

    TTran getTranById(Integer tranId);
}
