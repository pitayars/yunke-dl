package com.bjpowernode.manager;

import com.bjpowernode.dao.TActivityDao;
import com.bjpowernode.dao.TClueDao;
import com.bjpowernode.dao.TCustomerDao;
import com.bjpowernode.dao.TTranDao;
import com.bjpowernode.domain.result.NameValue;
import com.bjpowernode.domain.result.OverviewData;
import com.bjpowernode.domain.result.TimeValue;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class StatisticManager {

    @Resource
    private TActivityDao tActivityDao;

    @Resource
    private TClueDao tClueDao;

    @Resource
    private TCustomerDao tCustomerDao;

    @Resource
    private TTranDao tTranDao;

    /**
     * 查询概览统计数据
     *
     * @return
     */
    public OverviewData getOverviewData() {
        //有效的市场活动总数
        Integer effectiveActivityCount = tActivityDao.selectActivityByEffective().size();

        //总的市场活动数
        Integer totalActivityCount = tActivityDao.selectActivityByAll().size();

        //线索总数
        Integer totalClueCount = tClueDao.selectClueByCount();

        //客户总数
        Integer totalCustomerCount = tCustomerDao.selectCustomerByCount();

        //成功的交易额
        BigDecimal successTranAmount = tTranDao.selectTranBySucessAmount();

        //总的交易额（包含成功和不成功的）
        BigDecimal totalTranAmount = tTranDao.selectTranByTotalAmount();

        return OverviewData.builder()
                .effectiveActivityCount(effectiveActivityCount)
                .totalActivityCount(totalActivityCount)
                .totalClueCount(totalClueCount)
                .totalCustomerCount(totalCustomerCount)
                .successTranAmount(successTranAmount)
                .totalTranAmount(totalTranAmount)
                .build();
    }

    /**
     * 获取销售漏斗图数据
     *
     * @return
     */
    public List<NameValue> getFunnelChartData() {
        List<NameValue> nameValueList = new ArrayList<>();
        /**
         *                 [
         *                   //数据项的值和名称
         *                   { value: 60, name: '交易' },
         *                   { value: 40, name: '成交' },
         *                   { value: 80, name: '客户' },
         *                   { value: 100, name: '线索' }
         *                 ]
         */
        //线索总数
        Integer totalClueCount = tClueDao.selectClueByCount();
        nameValueList.add(NameValue.builder().name("线索").value(totalClueCount).build());

        //客户总数
        Integer totalCustomerCount = tCustomerDao.selectCustomerByCount();
        nameValueList.add(NameValue.builder().name("客户").value(totalCustomerCount).build());

        //交易人数
        Integer totalTranCount = tTranDao.selectTranByCount();
        nameValueList.add(NameValue.builder().name("交易").value(totalTranCount).build());

        //成交人数
        Integer successTranCount = tTranDao.selectSuccessTranByCount();
        nameValueList.add(NameValue.builder().name("成交").value(successTranCount).build());

        return nameValueList;
    }

    /**
     * 获取线索来源统计数据
     *
     * @return
     */
    public List<NameValue> getPieChartData() {
        /**
         *                 [
         *                   { value: 1048, name: 'Search Engine' },
         *                   { value: 735, name: 'Direct' },
         *                   { value: 580, name: 'Email' },
         *                   { value: 484, name: 'Union Ads' },
         *                   { value: 300, name: 'Video Ads' }
         *                 ]
         */
        return tClueDao.selectBySourceGroup();
    }

    /**
     * 获取市场活动统计数据（按月统计，统计今年的）
     *
     * @return
     */
    public Integer[] getActivityBarChartData() {
        List<TimeValue> timeValueList = tActivityDao.selectActivityByMonth();
        /**
         * 01	3
         * 02	1
         * 04	15
         * 05	3
         * 08	5
         * 10	1
         * 11	4
         * 分析一下：
         * 3月没有数据、6月没有数据、7月没有数据、9月没有数据
         * 没有数据的月份要补0，不然总共11个月，只有7条数据，那么就会导致前端显示的时候，月份和对应的数字错位
         */
        //[3, 1, 0, 15, 3, 0, 0, 5, 0, 1, 4]
        int monthValue = LocalDate.now().getMonthValue();//当前是几月（11月）

        //现在是11月，那么数组大小就是11
        Integer[] resultArray = new Integer[monthValue];

        //数组的下标
        int a = 0;
        for (int i=1; i<=monthValue; i++) {//当前是11月，那么我们循环11次，往结果数组中要放11条数据
            for (TimeValue timeValue : timeValueList) { //循环从数据库中查询出来的 月份及对应的数据
                String month = timeValue.getTime(); //月份
                month = month.startsWith("0") ? month.substring(1) : month; //月份如果是0开头，把0去掉

                Integer value = timeValue.getValue();//月份对应的值（条数）

                if (month.equals(String.valueOf(i))) {
                    resultArray[a] = value;
                    break;
                } else {
                    resultArray[a] = 0;
                }
            }
            //数组下标+1
            a++;
        }

        //返回结果数据
        return resultArray;
    }

    /**
     * 查询线索统计数据
     *
     * @return
     */
    public Integer[] getClueBarChartData() {
        List<TimeValue> timeValueList = tClueDao.selectClueByDay();
        /**
         * 03	2
         * 07	5
         * 09	6
         * 12	3
         * 13	3
         * 分析一下：
         * 1、2、4、5、6、8、10、11没有数据
         * 没有数据的那一天要补0，不然总共13天，只有5条数据，那么就会导致前端显示的时候，天和对应的数字错位
         */
        //[0, 0, 2, 0, 0, 0, 5, 0, 6, 0, 0, 3, 3]
        int dayValue = LocalDate.now().getDayOfMonth();//当前是几号（13号）

        //现在是13号，那么数组大小就是13
        Integer[] resultArray = new Integer[dayValue];

        //数组的下标
        int a = 0;
        for (int i=1; i<=dayValue; i++) {//当前是11月，那么我们循环11次，往结果数组中要放11条数据
            for (TimeValue timeValue : timeValueList) { //循环从数据库中查询出来的 月份及对应的数据
                String day = timeValue.getTime(); //天
                day = day.startsWith("0") ? day.substring(1) : day; //月份如果是0开头，把0去掉

                Integer value = timeValue.getValue();//天对应的值（条数）

                if (day.equals(String.valueOf(i))) {
                    resultArray[a] = value;
                    break;
                } else {
                    resultArray[a] = 0;
                }
            }
            //数组下标+1
            a++;
        }

        //返回结果数据
        return resultArray;
    }

    /**
     * 查询客户统计数据（按天）（当前月）
     *
     * @return
     */
    public Integer[] getCustomerBarChartData() {
        List<TimeValue> timeValueList = tCustomerDao.selectCustomerByDay();
        /**
         * 03	2
         * 07	5
         * 09	6
         * 12	3
         * 13	3
         * 分析一下：
         * 1、2、4、5、6、8、10、11没有数据
         * 没有数据的那一天要补0，不然总共13天，只有5条数据，那么就会导致前端显示的时候，天和对应的数字错位
         */
        //[0, 0, 2, 0, 0, 0, 5, 0, 6, 0, 0, 3, 3]
        int dayValue = LocalDate.now().getDayOfMonth();//当前是几号（13号）

        //现在是13号，那么数组大小就是13
        Integer[] resultArray = new Integer[dayValue];

        //数组的下标
        int a = 0;
        for (int i=1; i<=dayValue; i++) {//当前是11月，那么我们循环11次，往结果数组中要放11条数据
            for (TimeValue timeValue : timeValueList) { //循环从数据库中查询出来的 月份及对应的数据
                String day = timeValue.getTime(); //天
                day = day.startsWith("0") ? day.substring(1) : day; //月份如果是0开头，把0去掉

                Integer value = timeValue.getValue();//天对应的值（条数）

                if (day.equals(String.valueOf(i))) {
                    resultArray[a] = value;
                    break;
                } else {
                    resultArray[a] = 0;
                }
            }
            //数组下标+1
            a++;
        }

        //返回结果数据
        return resultArray;
    }

    /**
     * 查询总的交易额数据
     *
     * @return
     */
    public BigDecimal[] getTranBarChartData() {
        List<TimeValue> timeValueList = tTranDao.selectTranByDay();
        /**
         * 03	2
         * 07	5
         * 09	6
         * 12	3
         * 13	3
         * 分析一下：
         * 1、2、4、5、6、8、10、11没有数据
         * 没有数据的那一天要补0，不然总共13天，只有5条数据，那么就会导致前端显示的时候，天和对应的数字错位
         */
        //[0, 0, 2, 0, 0, 0, 5, 0, 6, 0, 0, 3, 3]
        int dayValue = LocalDate.now().getDayOfMonth();//当前是几号（13号）

        //现在是13号，那么数组大小就是13
        BigDecimal[] resultArray = new BigDecimal[dayValue];

        //数组的下标
        int a = 0;
        for (int i=1; i<=dayValue; i++) {//当前是11月，那么我们循环11次，往结果数组中要放11条数据
            for (TimeValue timeValue : timeValueList) { //循环从数据库中查询出来的 月份及对应的数据
                String day = timeValue.getTime(); //天
                day = day.startsWith("0") ? day.substring(1) : day; //月份如果是0开头，把0去掉

                BigDecimal amount = timeValue.getAmount();//天对应的值（条数）

                if (day.equals(String.valueOf(i))) {
                    resultArray[a] = amount;
                    break;
                } else {
                    resultArray[a] = new BigDecimal(0);
                }
            }
            //数组下标+1
            a++;
        }

        //返回结果数据
        return resultArray;
    }

    /**
     * 查询成功的交易额数据
     *
     * @return
     */
    public BigDecimal[] getSuccessTranBarChartData() {
        List<TimeValue> timeValueList = tTranDao.selectSuccessTranByDay();
        /**
         * 03	2
         * 07	5
         * 09	6
         * 12	3
         * 13	3
         * 分析一下：
         * 1、2、4、5、6、8、10、11没有数据
         * 没有数据的那一天要补0，不然总共13天，只有5条数据，那么就会导致前端显示的时候，天和对应的数字错位
         */
        //[0, 0, 2, 0, 0, 0, 5, 0, 6, 0, 0, 3, 3]
        int dayValue = LocalDate.now().getDayOfMonth();//当前是几号（13号）

        //现在是13号，那么数组大小就是13
        BigDecimal[] resultArray = new BigDecimal[dayValue];

        //数组的下标
        int a = 0;
        for (int i=1; i<=dayValue; i++) {//当前是11月，那么我们循环11次，往结果数组中要放11条数据
            for (TimeValue timeValue : timeValueList) { //循环从数据库中查询出来的 月份及对应的数据
                String day = timeValue.getTime(); //天
                day = day.startsWith("0") ? day.substring(1) : day; //月份如果是0开头，把0去掉

                BigDecimal amount = timeValue.getAmount();//天对应的值（条数）

                if (day.equals(String.valueOf(i))) {
                    resultArray[a] = amount;
                    break;
                } else {
                    resultArray[a] = new BigDecimal(0);
                }
            }
            //数组下标+1
            a++;
        }

        //返回结果数据
        return resultArray;
    }
}
