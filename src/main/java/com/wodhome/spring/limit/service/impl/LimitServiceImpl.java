package com.wodhome.spring.limit.service.impl;

import com.wodhome.spring.limit.LimitConfig;
import com.wodhome.spring.limit.model.BaseData;
import com.wodhome.spring.limit.service.LimitService;

/**
 * @Description: java类作用描述
 * @Author: yuDongLi
 * @CreateDate: 2023/6/12
 * @Version: 1.0
 */

public class LimitServiceImpl implements LimitService {
    @Override
    public void addData(String name, BaseData data) {
        LimitConfig.rateModelConcurrentHashMap.get(name).addData(data);
    }

    @Override
    public BaseData getData(String name) {
        return  LimitConfig.rateModelConcurrentHashMap.get(name).getLimitData();
    }
}
