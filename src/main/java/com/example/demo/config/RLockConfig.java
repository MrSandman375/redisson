package com.example.demo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @Auther: fan
 * @Date: 2021/12/7
 * @Description:
 */
@Configuration
public class RLockConfig {
    @Bean
    public RedissonClient redissonClient() throws IOException {
        Config config = Config.fromYAML(RLockConfig.class.getClassLoader().getResource("redisson-cluster-config.yml"));
        return Redisson.create(config);
    }
}
