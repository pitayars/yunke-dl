package com.bjpowernode.config.task;

import com.bjpowernode.commons.BaseApplication;
import com.bjpowernode.domain.po.TDicType;
import com.bjpowernode.domain.po.TProduct;
import com.bjpowernode.domain.result.DicEnum;
import com.bjpowernode.service.DicTypeService;
import com.bjpowernode.service.ProductService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@EnableScheduling //开启定时任务的支持
@Component
public class DataCacheTask {

    @Resource
    private DicTypeService dicTypeService;

    @Resource
    private ProductService productService;

    //调度的意思
    //@Scheduled(fixedDelay = 3000, initialDelay = 15000) //单位默认是毫秒
    //@Scheduled(fixedDelay = 3000, initialDelayString = "15000") //单位默认是毫秒
    //@Scheduled(fixedDelayString = "3000") //单位默认是毫秒
    //@Scheduled(fixedRate = 3000) //单位默认是毫秒
    //cron表达式：包含6个或7个参数 ：
    /**
     * 秒（0-59）
     * 分钟（0-59）
     * 小时（0-23）
     * 日（1-31）
     * 月（1-12 或 JAN-DEC）
     * 星期几（0-6 或 SUN-SAT）
     */
    //@Scheduled(cron = "0 0 3 * * *") //每天3点执行
    @Scheduled(cron = "0 0/3 * * * *") //每2秒执行一次
    public void dataCacheTask() {
        System.out.println("这里面就写具体要执行的业务逻辑代码......" + new Date());
       /* try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/

        //加载字典数据到内存中
        List<TDicType> tDicTypeList = dicTypeService.getAllDicTypeAndDicValue();
        tDicTypeList.forEach( tDicType -> {
            BaseApplication.cacheMap.put(tDicType.getCode(), tDicType.getTDicValueList());
        });

        //加载产品数据到内存中
        List<TProduct> tProductList = productService.getProductBySale();
        BaseApplication.cacheMap.put(DicEnum.PRODUCT.getDicName(), tProductList);
    }

}
