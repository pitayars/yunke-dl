package com.bjpowernode.web;

import com.bjpowernode.domain.po.TDicValue;
import com.bjpowernode.domain.result.R;
import com.bjpowernode.service.DicValueService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DicValueController {

    @Resource
    private DicValueService dicValueService;

    /**
     * 根据字典类型code查询字典值
     *
     * @param typeCode
     * @return
     */
    @GetMapping(value = "/api/dicvalue/{typeCode}")
    public R dicValue(@PathVariable(value = "typeCode") String typeCode) {
        List<TDicValue> tDicValueList = dicValueService.getDicValueByTypeCode(typeCode);
        return R.OK(tDicValueList);
    }
}
