package com.bjpowernode.service.impl;

import com.alibaba.excel.EasyExcel;
import com.bjpowernode.constant.Constants;
import com.bjpowernode.dao.TClueDao;
import com.bjpowernode.dao.TCustomerDao;
import com.bjpowernode.domain.po.TClue;
import com.bjpowernode.domain.po.TCustomer;
import com.bjpowernode.domain.query.CustomerQuery;
import com.bjpowernode.domain.result.CustomerExcel;
import com.bjpowernode.manager.CustomerManager;
import com.bjpowernode.service.CustomerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    //在一个service中要注入多个Dao，则把代码实现下沉到Manager中去实现（阿里推荐的规范）

    @Resource
    private CustomerManager customerManager;

    @Resource
    private TCustomerDao tCustomerDao;

    @Override
    public Boolean convertCustomer(CustomerQuery customerQuery) {
        return customerManager.convertCustomer(customerQuery);
    }

    /**
     * 客户列表分页查询
     *
     * @param current
     * @return
     */
    @Override
    public PageInfo<TCustomer> getCustomerByPage(Integer current) {
        //1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        //2.查询
        List<TCustomer> list = tCustomerDao.selectCustomerPage();
        //3.封装分页数据到PageInfo
        PageInfo<TCustomer> info = new PageInfo<>(list);

        return info;
    }

    @Override
    public void exportExcel(ServletOutputStream outputStream, String ids) {
        //获取要写入到Excel中的数据List
        List<String> idList = StringUtils.hasText(ids) ? Arrays.asList(ids.split(",")) : null;
        List<TCustomer> tCustomerList =  tCustomerDao.selectCustomerByExcel(idList);

        //现在需要把 List<TCustomer> 的数据转换到 List<CustomerExcel>
        List<CustomerExcel> customerExcelList = new ArrayList<>();

        tCustomerList.forEach( (tCustomer) -> {
            CustomerExcel customerExcel = new CustomerExcel();

            customerExcel.setOwnerName(ObjectUtils.isEmpty(tCustomer.getOwnerPO()) ? Constants.EMPTY : tCustomer.getOwnerPO().getName());
            customerExcel.setActivityName(ObjectUtils.isEmpty(tCustomer.getActivityPO()) ? "" : tCustomer.getActivityPO().getName());
            customerExcel.setFullName(tCustomer.getCluePO().getFullName());
            customerExcel.setAppellationName(tCustomer.getAppellationPO().getTypeValue());
            customerExcel.setPhone(tCustomer.getCluePO().getPhone());
            customerExcel.setWeixin(tCustomer.getCluePO().getWeixin());
            customerExcel.setQq(tCustomer.getCluePO().getQq());
            customerExcel.setEmail(tCustomer.getCluePO().getEmail());
            customerExcel.setAge(tCustomer.getCluePO().getAge());
            customerExcel.setJob(tCustomer.getCluePO().getJob());
            customerExcel.setYearIncome(tCustomer.getCluePO().getYearIncome());
            customerExcel.setAddress(tCustomer.getCluePO().getAddress());
            customerExcel.setNeedLoadName(tCustomer.getNeedLoanPO().getTypeValue());
            customerExcel.setProductName(tCustomer.getIntentionProductPO().getName());
            customerExcel.setSourceName(tCustomer.getSourcePO().getTypeValue());
            customerExcel.setDescription(tCustomer.getDescription());
            customerExcel.setNextContactTime(tCustomer.getNextContactTime());

            customerExcelList.add(customerExcel);
        });

        //链式编程，两个参数，
        //参数1：生成的Excel文件输出到哪里去（输出到servlet的响应流中去），我们也可以输出到D盘某个目录下也是可以的
        //参数2：Excel表头字段的模板类
        EasyExcel.write(outputStream, CustomerExcel.class)
                .sheet("客户信息列表") //生成一个sheet
                .doWrite(customerExcelList); //往sheet里面写入数据
    }

    /**
     * 根据id查询客户详情
     *
     * @param id
     * @return
     */
    @Override
    public TCustomer getCustomerById(Integer id) {
        return tCustomerDao.selectByPrimaryKey(id);
    }
}
