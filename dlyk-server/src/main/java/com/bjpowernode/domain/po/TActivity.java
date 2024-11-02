package com.bjpowernode.domain.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 市场活动表
 * t_activity
 */
@JsonInclude(value = JsonInclude.Include.NON_EMPTY) //字段的值不是空的就返回，是空的就不返回给前端了
@Data
public class TActivity implements Serializable {

    /**
     * 主键，自动增长，活动ID
     */
    private Integer id;

    /**
     * 活动所属人ID
     */
    private Integer ownerId;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 活动开始时间
     */
    private Date startDate;

    /**
     * 活动结束时间
     */
    private Date endDate;

    /**
     * 活动预算
     */
    private BigDecimal cost;

    /**
     * 活动描述
     */
    private String description;

    /**
     * 活动创建时间
     */
    private Date createTime;

    /**
     * 活动创建人
     */
    private Integer createBy;

    /**
     * 活动编辑时间
     */
    private Date editTime;

    /**
     * 活动编辑人
     */
    private Integer editBy;

    /**
     * 一对一关联 (jaav中其实是组合关系)
     */
    private TUser ownerPO;
    private TUser createByPO = new TUser();
    private TUser editByPO = new TUser();

    private static final long serialVersionUID = 1L;
}