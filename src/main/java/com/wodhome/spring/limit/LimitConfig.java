package com.wodhome.spring.limit;

import com.wodhome.spring.limit.service.LimitService;
import com.wodhome.spring.limit.service.impl.LimitServiceImpl;
import com.wodhome.spring.limit.model.RateModel;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @Author: yuDongLi
 * @CreateDate: 2023/6/12
 * @Version: 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "limit-config")
public class LimitConfig {

    private List<RateModel> list;
    public static Map<String, RateModel> rateModelConcurrentHashMap = new ConcurrentHashMap<>();
    public static Lock  lock = new ReentrantLock();
    public static Condition signCondition = lock.newCondition();

    @Bean
    public LimitService limitService() {

        list.forEach(a -> {
            a.init();
            rateModelConcurrentHashMap.put(a.getName(), a);
        });

        return new LimitServiceImpl();
    }

    public List<RateModel> getList() {
        return list;
    }

    public void setList(List<RateModel> list) {
        this.list = list;
    }
}
