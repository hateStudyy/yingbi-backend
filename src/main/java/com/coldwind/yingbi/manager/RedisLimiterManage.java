package com.coldwind.yingbi.manager;

import com.coldwind.yingbi.common.ErrorCode;
import com.coldwind.yingbi.exception.BusinessException;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 专门提供RedisLimiter限流基础服务 (提供了通用能力)
 * @author ckl
 * @since 2023/7/23 10:20
 */
@Service
public class RedisLimiterManage {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 限流操作
     *
     *
     * @param key 区分不同的限流器，比如不同的用户id应该分别统计
     */
    public void doRateLimiter(String key) {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);

        // 每秒钟最多5个权限
        rateLimiter.trySetRate(RateType.OVERALL, 2, 1, RateIntervalUnit.SECONDS);

        // 尝试获取权限
        if (!rateLimiter.tryAcquire(1)) {
            // 执行操作...
            throw new BusinessException(ErrorCode.TOO_MANY_REQUEST);
        }

    }

}
