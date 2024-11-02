package com.bjpowernode.domain.query;

import com.bjpowernode.domain.Base;
import lombok.Data;

@Data
public class ActivityRemarkQuery extends Base {

    private Integer id;

    private Integer activityId;

    private String noteContent;
}
