package com.bjpowernode.domain;

import lombok.*;

/**
 * 基础实体类
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Base {

    private String token;

    private String filterSQL;

}
