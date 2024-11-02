package com.bjpowernode.service.impl;

import com.bjpowernode.domain.result.NameValue;
import com.bjpowernode.domain.result.OverviewData;
import com.bjpowernode.manager.StatisticManager;
import com.bjpowernode.service.StatisticService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {

    @Resource
    private StatisticManager statisticManager;

    @Override
    public OverviewData getOverviewData() {
        return statisticManager.getOverviewData();
    }

    @Override
    public List<NameValue> getFunnelChartData() {
        return statisticManager.getFunnelChartData();
    }

    @Override
    public List<NameValue> getPieChartData() {
        return statisticManager.getPieChartData();
    }

    @Override
    public Integer[] getActivityBarChartData() {
        return statisticManager.getActivityBarChartData();
    }

    @Override
    public Integer[] getClueBarChartData() {
        return statisticManager.getClueBarChartData();
    }

    @Override
    public Integer[] getCustomerBarChartData() {
        return statisticManager.getCustomerBarChartData();
    }

    @Override
    public BigDecimal[] getTranBarChartData() {
        return statisticManager.getTranBarChartData();
    }

    @Override
    public BigDecimal[] getSuccessTranBarChartData() {
        return statisticManager.getSuccessTranBarChartData();
    }
}
