package com.bjpowernode.domain.query;

import com.bjpowernode.domain.Base;
import lombok.Data;

@Data
public class ClueRemarkQuery extends Base {

    private Integer clueId;

    private Integer noteWay;

    private String noteContent;

}
