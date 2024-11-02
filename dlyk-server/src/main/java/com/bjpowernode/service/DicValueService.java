package com.bjpowernode.service;

import com.bjpowernode.domain.po.TDicValue;

import java.util.List;

public interface DicValueService {

    List<TDicValue> getDicValueByTypeCode(String typeCode);

}
