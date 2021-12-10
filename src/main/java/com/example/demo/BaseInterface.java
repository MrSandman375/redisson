package com.example.demo;

import java.util.concurrent.Callable;

/**
 * @Auther: fan
 * @Date: 2021/12/7
 * @Description:
 */
@FunctionalInterface
public interface BaseInterface {
    <T> T runFunction(Callable<T> callable);
}
