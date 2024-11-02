package com.bjpowernode.domain.query;

import com.bjpowernode.domain.Base;
import lombok.Data;

import java.util.Date;

@Data
public class CustomerQuery extends Base {

    private Integer clueId;

    private Integer product;

    private String description;

    private Date nextContactTime;
}
