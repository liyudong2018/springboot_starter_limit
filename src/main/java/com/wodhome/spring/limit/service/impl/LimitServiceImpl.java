package com.wodhome.spring.limit.service.impl;

import com.wodhome.spring.limit.LimitConfig;
import com.wodhome.spring.limit.model.BaseData;
import com.wodhome.spring.limit.service.LimitService;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: java类作用描述
 * @Author: yuDongLi
 * @CreateDate: 2023/6/12
 * @Version: 1.0
 */
@Slf4j
public class LimitServiceImpl implements LimitService {
    @Override
    public void addData(String name, BaseData data) {
        if(LimitConfig.rateModelConcurrentHashMap.get(name)!=null){
            LimitConfig.rateModelConcurrentHashMap.get(name).addData(data);
        }else{
            log.error("不存在name={}的队列",name);
        }

    }

    @Override
    public BaseData getData(String name) {

        if(LimitConfig.rateModelConcurrentHashMap.get(name)!=null){
            return  LimitConfig.rateModelConcurrentHashMap.get(name).getLimitData();
        }else{
            log.error("不存在name={}的队列",name);
            return null;
        }

    }
}
