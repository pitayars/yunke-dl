package com.bjpowernode.domain.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

/**
 * 客户表
 * t_customer
 */
@Data
public class TCustomer implements Serializable {

    /**
     * 主键，自动增长，客户ID
     */
    private Integer id;

    /**
     * 线索ID
     */
    private Integer clueId;

    /**
     * 选购产品
     */
    private Integer product;

    /**
     * 客户描述
     */
    private String description;

    /**
     * 下次联系时间
     */
    private Date nextContactTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createBy;

    /**
     * 编辑时间
     */
    private Date editTime;

    /**
     * 编辑人
     */
    private Integer editBy;

    /**
     * 一对一关联8个对象
     */
    private TClue cluePO = new TClue();
    private TUser ownerPO = new TUser();
    private TActivity activityPO = new TActivity();
    private TDicValue appellationPO = new TDicValue();
    private TDicValue needLoanPO = new TDicValue();
    private TDicValue intentionStatePO = new TDicValue();
    private TProduct intentionProductPO = new TProduct();
    private TDicValue statePO = new TDicValue();
    private TDicValue sourcePO = new TDicValue();

    private static final long serialVersionUID = 1L;
}