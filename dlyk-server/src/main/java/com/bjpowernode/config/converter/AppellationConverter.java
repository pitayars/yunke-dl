package com.bjpowernode.config.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.bjpowernode.commons.BaseApplication;
import com.bjpowernode.domain.po.TDicValue;
import com.bjpowernode.domain.result.DicEnum;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 称呼的转换器
 *
 */
public class AppellationConverter implements Converter<Integer> {

    /**
     * 把从Excel中读到的数据，转换为可以存入数据库的数据
     *
     * 比如 Excel里面是“先生”， 转换为 18 存入数据库
     *
     * @param cellData
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {

        System.out.println("缓存的字典数据: " + BaseApplication.cacheMap);


        String data = cellData.getStringValue();
        if (StringUtils.hasText(data)) { //excel单元格读到了称呼的中文，比如是“先生”
            //把“先生”转换为18
            List<TDicValue> tDicValueList = (List<TDicValue>) BaseApplication.cacheMap.get(DicEnum.APPELLATION.getDicName());
            for (TDicValue tDicValue : tDicValueList) {
                if (data.equals(tDicValue.getTypeValue())) {
                    return tDicValue.getId();
                }
            }
        }
        //字典值中没有找到该称呼，返回-1
        return -1;
    }

    /**
     * 把从数据库查询得到的数据，转换为写入Excel的中文数据
     *
     * 比如 数据库查询得到的是 18， 转换为 "先生" 写入Excel
     *
     * @param value
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    /*@Override
    public WriteCellData<?> convertToExcelData(Object value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return null;
    }*/
}
