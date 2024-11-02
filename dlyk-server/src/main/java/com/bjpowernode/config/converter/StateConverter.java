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

public class StateConverter implements Converter<Integer> {

    /**
     * 把从Excel中读到的数据，转换为可以存入数据库的数据
     *
     * 比如 Excel里面是“未联系”， 转换为 24 存入数据库
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
        if (StringUtils.hasText(data)) { //excel单元格读到了线索状态的中文，比如是“未联系”
            //把“未联系”转换为24
            List<TDicValue> tDicValueList = (List<TDicValue>) BaseApplication.cacheMap.get(DicEnum.CLUESTATE.getDicName());
            for (TDicValue tDicValue : tDicValueList) {
                if (data.equals(tDicValue.getTypeValue())) {
                    return tDicValue.getId();
                }
            }
        }
        //字典值中没有找到该线索状态数据，返回-1
        return -1;
    }
}
