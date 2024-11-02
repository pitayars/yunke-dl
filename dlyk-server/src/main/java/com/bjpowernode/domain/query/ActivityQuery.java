package com.bjpowernode.domain.query;

import com.bjpowernode.domain.Base;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ActivityQuery extends Base {

    private Integer id;

    private Integer ownerId;

    private String name;

    /**
     * 前端提交过来了一个string的日期，后端使用Date接收，那么需要加个注解转换一下
     *
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    private BigDecimal cost;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String description;
}
