package com.bjpowernode.service;

import com.bjpowernode.domain.po.TCustomerRemark;
import com.bjpowernode.domain.query.CustomerRemarkQuery;
import com.github.pagehelper.PageInfo;

public interface CustomerRemarkService {

    PageInfo<TCustomerRemark> getCustomerRemarkByPage(Integer current, Integer customerId);

    int saveCustomerRemark(CustomerRemarkQuery customerRemarkQuery);
}
