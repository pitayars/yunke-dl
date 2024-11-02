package com.bjpowernode.web;

import com.bjpowernode.domain.result.NameValue;
import com.bjpowernode.domain.result.OverviewData;
import com.bjpowernode.domain.result.R;
import com.bjpowernode.service.StatisticService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StatisticController {

    @Resource
    private StatisticService statisticService;

    /**
     * 概览统计数据查询
     *
     * @return
     */
    @GetMapping(value = "/api/statistic/overview")
    public R overview() {
        OverviewData overviewData = statisticService.getOverviewData();
        return R.OK(overviewData);
    }

    /**
     * 获取销售漏斗图的数据
     *
     * @return
     */
    @GetMapping(value = "/api/statistic/funnelChart")
    public R funnelChart() {
        /**
         *                 [
         *                   //数据项的值和名称
         *                   { value: 60, name: '交易' },
         *                   { value: 40, name: '成交' },
         *                   { value: 80, name: '客户' },
         *                   { value: 100, name: '线索' }
         *                 ]
         */
        List<NameValue> nameValueList = statisticService.getFunnelChartData();
        return R.OK(nameValueList);
    }

    /**
     * 获取销售漏斗图的数据
     *
     * @return
     */
    @GetMapping(value = "/api/statistic/pieChart")
    public R pieChart() {
        /**
         *                 [
         *                   { value: 1048, name: 'Search Engine' },
         *                   { value: 735, name: 'Direct' },
         *                   { value: 580, name: 'Email' },
         *                   { value: 484, name: 'Union Ads' },
         *                   { value: 300, name: 'Video Ads' }
         *                 ]
         */
        List<NameValue> nameValueList = statisticService.getPieChartData();
        return R.OK(nameValueList);
    }

    /**
     * 获取市场活动柱状图的数据
     *
     * @return
     */
    @GetMapping(value = "/api/statistic/activityBarChart")
    public R activityBarChart() {
        /**
         * 每个月的数据按如下的数组格式返回即可
         * [120, 200, 150, 80, 70, 110, 130],
         */
        Integer[] activityDataArray = statisticService.getActivityBarChartData();
        return R.OK(activityDataArray);
    }


    /**
     * 获取线索柱状图的数据
     *
     * @return
     */
    @GetMapping(value = "/api/statistic/clueBarChart")
    public R clueBarChart() {
        //x轴的数据数组
        int days = LocalDate.now().lengthOfMonth();
        Integer[] xDataArray = new Integer[days];
        int a = 0;
        for (int i = 1; i <= days; i++) {
            xDataArray[a] = i;
            a ++;
        }

        /**
         * 每天的数据按如下的数组格式返回即可
         * [120, 200, 150, 80, 70, 110, 130],
         */
        Integer[] yDataArray = statisticService.getClueBarChartData();

        Map<String, Integer[]> resultMap = new HashMap<>();
        resultMap.put("x", xDataArray);
        resultMap.put("y", yDataArray);
        return R.OK(resultMap);
    }

    /**
     * 获取客户柱状图的数据
     *
     * @return
     */
    @GetMapping(value = "/api/statistic/customerBarChart")
    public R customerBarChart() {
        //x轴的数据数组
        int days = LocalDate.now().lengthOfMonth();
        Integer[] xDataArray = new Integer[days];
        int a = 0;
        for (int i = 1; i <= days; i++) {
            xDataArray[a] = i;
            a ++;
        }

        /**
         * 每天的数据按如下的数组格式返回即可
         * [120, 200, 150, 80, 70, 110, 130],
         */
        Integer[] yDataArray = statisticService.getCustomerBarChartData();

        Map<String, Integer[]> resultMap = new HashMap<>();
        resultMap.put("x", xDataArray);
        resultMap.put("y", yDataArray);
        return R.OK(resultMap);
    }

    /**
     * 获取交易柱状图的数据
     *
     * @return
     */
    @GetMapping(value = "/api/statistic/tranBarChart")
    public R tranBarChart() {
        //x轴的数据数组
        int days = LocalDate.now().lengthOfMonth();
        Integer[] xDataArray = new Integer[days];
        int a = 0;
        for (int i = 1; i <= days; i++) {
            xDataArray[a] = i;
            a ++;
        }

        /**
         * 每天总的交易数据按如下的数组格式返回即可
         * [120, 200, 150, 80, 70, 110, 130],
         */
        BigDecimal[] yDataArray1 = statisticService.getTranBarChartData();

        /**
         * 每天成功的交易数据按如下的数组格式返回即可
         * [120, 200, 150, 80, 70, 110, 130],
         */
        BigDecimal[] yDataArray2 = statisticService.getSuccessTranBarChartData();

        Map<String, Object[]> resultMap = new HashMap<>();
        resultMap.put("x", xDataArray);
        resultMap.put("y1", yDataArray1);
        resultMap.put("y2", yDataArray2);
        return R.OK(resultMap);
    }
}
