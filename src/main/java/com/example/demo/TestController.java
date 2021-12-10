package com.example.demo;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: fan
 * @Date: 2021/12/6
 * @Description:
 */
@RestController
@RequestMapping("/")
@Slf4j
public class TestController {
    @Autowired
    private TestMapper mapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/test")
    public void test() {
        for (int i = 0; i < 10; i++) {
            System.out.println(insert());
        }
    }

    @GetMapping("/test1")
    public void redis() {
        System.out.println(redisTemplate.opsForValue().get("student"));
        redisTemplate.opsForValue().set("student", "zhangsan");
    }

    public Test insert() {
        return BaseImpl.rLock(KeyEnums.CAR_KEY).runFunction(() -> {
            if (mapper.selectList(Wrappers.<Test>lambdaQuery().eq(Test::getName, "张三")).size() == 0) {
                Thread.sleep(31000);
                Test newTest = Test.builder().name("张三").build();
                mapper.insert(newTest);
                return newTest;
            }
            return null;
        });
    }
}
