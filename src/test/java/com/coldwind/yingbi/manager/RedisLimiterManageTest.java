package com.coldwind.yingbi.manager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author ckl
 * @since 2023/7/23 10:45
 */
@SpringBootTest
class RedisLimiterManageTest {

    @Resource
    private RedisLimiterManage redisLimiterManage;

    @Test
    void doRateLimiter() throws InterruptedException {
        String userId = "1";
        for (int i = 0; i < 2; i++) {
            redisLimiterManage.doRateLimiter(userId);
            System.out.println("成功");
        }

        Thread.sleep(1000);
        for (int i = 0; i < 5; i++) {
            redisLimiterManage.doRateLimiter(userId);
            System.out.println("成功");
        }
    }
}