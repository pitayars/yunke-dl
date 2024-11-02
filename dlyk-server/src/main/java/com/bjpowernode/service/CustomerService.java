package com.bjpowernode.service;

import com.bjpowernode.domain.po.TCustomer;
import com.bjpowernode.domain.query.CustomerQuery;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.ServletOutputStream;

public interface CustomerService {

    Boolean convertCustomer(CustomerQuery customerQuery);

    PageInfo<TCustomer> getCustomerByPage(Integer current);

    void exportExcel(ServletOutputStream outputStream, String ids);

    TCustomer getCustomerById(Integer id);
}
