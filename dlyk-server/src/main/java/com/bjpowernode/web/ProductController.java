package com.bjpowernode.web;

import com.bjpowernode.domain.po.TActivity;
import com.bjpowernode.domain.po.TProduct;
import com.bjpowernode.domain.result.R;
import com.bjpowernode.service.ProductService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Resource
    private ProductService productService;

    /**
     * 查询在售的产品
     *
     * @return
     */
    @GetMapping(value = "/api/product/sale")
    public R saleProduct() {
        List<TProduct> tProductList = productService.getProductBySale();
        return R.OK(tProductList);
    }
}
