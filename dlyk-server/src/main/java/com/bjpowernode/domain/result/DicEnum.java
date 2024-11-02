package com.bjpowernode.domain.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public enum DicEnum {

    APPELLATION("appellation"),

    NEEDLOAN("needLoan"),

    INTENTIONSTATE("intentionState"),

    CLUESTATE("clueState"),

    SOURCE("source"),

    PRODUCT("product")

    ;

    @Setter
    @Getter
    private String dicName;
}
