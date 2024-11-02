package com.bjpowernode.service;

import com.bjpowernode.domain.result.NameValue;
import com.bjpowernode.domain.result.OverviewData;

import java.math.BigDecimal;
import java.util.List;

public interface StatisticService {

    OverviewData getOverviewData();

    List<NameValue> getFunnelChartData();

    List<NameValue> getPieChartData();

    Integer[] getActivityBarChartData();

    Integer[] getClueBarChartData();

    Integer[] getCustomerBarChartData();

    BigDecimal[] getTranBarChartData();

    BigDecimal[] getSuccessTranBarChartData();
}
