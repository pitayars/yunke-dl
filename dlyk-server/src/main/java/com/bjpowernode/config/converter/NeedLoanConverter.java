package com.bjpowernode.config.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.bjpowernode.commons.BaseApplication;
import com.bjpowernode.dao.TDicValueDao;
import com.bjpowernode.domain.po.TDicValue;
import com.bjpowernode.domain.result.DicEnum;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.util.List;

public class NeedLoanConverter implements Converter<Integer>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * 把从Excel中读到的数据，转换为可以存入数据库的数据
     *
     * 比如 Excel里面是“否”， 转换为 50 存入数据库
     *
     * @param cellData
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String data = cellData.getStringValue();
        TDicValueDao tDicValueDao = this.applicationContext.getBean(TDicValueDao.class);
        List<TDicValue> list = tDicValueDao.selectByTypeCode("source");
        if (StringUtils.hasText(data)) { //excel单元格读到了是否贷款的中文，比如是“否”
            //把“否”转换为50
            List<TDicValue> tDicValueList = (List<TDicValue>) BaseApplication.cacheMap.get(DicEnum.NEEDLOAN.getDicName());
            for (TDicValue tDicValue : tDicValueList) {
                if (data.equals(tDicValue.getTypeValue())) {
                    return tDicValue.getId();
                }
            }
        }
        //字典值中没有找到该是否贷款数据，返回-1
        return -1;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
