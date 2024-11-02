package com.bjpowernode;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DlykApplicationTests {

    @Test
    void contextLoads() {
        String rememberMe1 = "true"; //true，false，undefined
        String rememberMe2 = "false";
        String rememberMe3 = "undefined";
        //System.out.println(Boolean.parseBoolean(rememberMe1));
        //System.out.println(Boolean.parseBoolean(rememberMe2));
        //System.out.println(Boolean.parseBoolean(rememberMe3));
        String rememberMe4 = null;
        System.out.println(Boolean.parseBoolean(rememberMe4));
    }
}