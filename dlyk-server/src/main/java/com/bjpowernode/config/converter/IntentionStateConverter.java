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

public class IntentionStateConverter implements Converter<Integer> {

    /**
     * 把从Excel中读到的数据，转换为可以存入数据库的数据
     *
     * 比如 Excel里面是“有意向”， 转换为 46 存入数据库
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
        if (StringUtils.hasText(data)) { //excel单元格读到了意向状态的中文，比如是“有意向”
            //把“有意向”转换为46
            List<TDicValue> tDicValueList = (List<TDicValue>) BaseApplication.cacheMap.get(DicEnum.INTENTIONSTATE.getDicName());
            for (TDicValue tDicValue : tDicValueList) {
                if (data.equals(tDicValue.getTypeValue())) {
                    return tDicValue.getId();
                }
            }
        }
        //字典值中没有找到该意向状态数据，返回-1
        return -1;
    }
}
