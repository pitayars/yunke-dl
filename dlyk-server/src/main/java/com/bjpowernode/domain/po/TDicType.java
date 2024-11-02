package com.bjpowernode.domain.po;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 字典类型表
 * t_dic_type
 */
@Data
public class TDicType implements Serializable {

    /**
     * 主键，自动增长，字典类型ID
     */
    private Integer id;

    /**
     * 字典类型代码
     */
    private String code;

    /**
     * 字典类型名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 一对多关联
     */
    private List<TDicValue> tDicValueList;

    private static final long serialVersionUID = 1L;
}