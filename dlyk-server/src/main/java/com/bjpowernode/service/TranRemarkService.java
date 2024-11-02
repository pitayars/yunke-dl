package com.bjpowernode.service;

import com.bjpowernode.domain.po.TTranRemark;
import com.bjpowernode.domain.query.TranRemarkQuery;
import com.github.pagehelper.PageInfo;

public interface TranRemarkService {

    int saveTranRemark(TranRemarkQuery tranRemarkQuery);

    PageInfo<TTranRemark> getTranRemarkByPage(Integer current, Integer tranId);
}
