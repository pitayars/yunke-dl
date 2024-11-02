package com.bjpowernode.service;

import com.bjpowernode.domain.po.TClueRemark;
import com.bjpowernode.domain.query.ClueRemarkQuery;
import com.github.pagehelper.PageInfo;

public interface ClueRemarkService {

    int saveClueRemark(ClueRemarkQuery clueRemarkQuery);

    PageInfo<TClueRemark> getClueRemarkByPage(Integer current, Integer clueId);

}
