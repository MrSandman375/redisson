package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

/**
 * @Auther: fan
 * @Date: 2021/12/8
 * @Description:
 */
@Slf4j
@Component
public class BaseImpl {

    private static RedissonClient redissonClient;

    @Autowired
    public void setRedissonClient(RedissonClient redissonClient) {
        BaseImpl.redissonClient = redissonClient;
    }

    public static BaseInterface rLock(KeyEnums keyEnum) {
        RLock lock = redissonClient.getLock(keyEnum.name());
        return new BaseInterface() {
            @Override
            public <T> T runFunction(Callable<T> callable) {
                try {
                    lock.lock();
                    return callable.call();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
                return null;
            }
        };
    }

}