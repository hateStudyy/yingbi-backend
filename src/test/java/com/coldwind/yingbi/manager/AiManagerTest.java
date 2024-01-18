package com.coldwind.yingbi.manager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author ckl
 * @since 2023/7/20 14:10
 */
@SpringBootTest
class AiManagerTest {

    @Resource
    AiManager aiManager;

    @Test
    void doChar() {
        String doChar = aiManager.doChart(111L,"日期,用户数\n" +
                "1,20\n" +
                "2,30\n" +
                "3,100000");
        System.out.println(doChar);
    }
}