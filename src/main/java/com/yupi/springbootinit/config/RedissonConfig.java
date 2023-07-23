package com.yupi.springbootinit.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ckl
 * @since 2023/7/23 10:06
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis") // 读取配置文件yml中的配置 prefix就是你需要变量的前缀
@Data
public class RedissonConfig {

    // 读取配置
    private Integer database;

    private String host;

    private String port;

    private  String password;
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        // 单机
        config.useSingleServer()
                .setDatabase(database)
                .setAddress("redis://"+host+":"+port);
                // .setPassword(password);
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
