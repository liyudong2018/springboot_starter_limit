package com.wodhome.spring.limit.service;

import com.wodhome.spring.limit.model.BaseData;

public interface LimitService {
    public  void addData(String name,BaseData data);
    public BaseData getData(String name);
}
