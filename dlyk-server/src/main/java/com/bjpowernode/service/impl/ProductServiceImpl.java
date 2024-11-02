package com.bjpowernode.service.impl;

import com.bjpowernode.dao.TProductDao;
import com.bjpowernode.domain.po.TProduct;
import com.bjpowernode.service.ProductService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private TProductDao tProductDao;

    /**
     * 查询在售的产品
     *
     * @return
     */
    @Override
    public List<TProduct> getProductBySale() {
        return tProductDao.selectProductBySale();
    }
}
