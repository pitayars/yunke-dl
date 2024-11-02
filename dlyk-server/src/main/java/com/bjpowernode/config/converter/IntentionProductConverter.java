package com.bjpowernode.config.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.bjpowernode.commons.BaseApplication;
import com.bjpowernode.domain.po.TProduct;
import com.bjpowernode.domain.result.DicEnum;
import org.springframework.util.StringUtils;

import java.util.List;

public class IntentionProductConverter implements Converter<Integer> {

    /**
     * 把从Excel中读到的数据，转换为可以存入数据库的数据
     *
     * 比如 Excel里面是“秦PLUS DM-i”， 转换为 6 存入数据库
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
        if (StringUtils.hasText(data)) { //excel单元格读到了意向产品的中文，比如是“秦PLUS DM-i”
            //把“秦PLUS DM-i”转换为 6
            List<TProduct> tProductList = (List<TProduct>) BaseApplication.cacheMap.get(DicEnum.PRODUCT.getDicName());
            for (TProduct tProduct : tProductList) {
                if (data.equals(tProduct.getName())) {
                    return tProduct.getId();
                }
            }
        }
        //字典值中没有找到该意向产品数据，返回-1
        return -1;
    }
}
