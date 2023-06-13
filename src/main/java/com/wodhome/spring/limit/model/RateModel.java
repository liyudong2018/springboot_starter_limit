package com.wodhome.spring.limit.model;

import com.google.common.util.concurrent.RateLimiter;

import com.wodhome.spring.limit.LimitConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


import java.util.Iterator;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 速度模型
 * @Author: yuDongLi
 * @CreateDate: 2023/6/12
 * @Version: 1.0
 */

@Slf4j

public class RateModel {
    /**
     * 速度
     */
    private double speed;
    /**
     * 名称
     */
    private String name;

    /**
     * 队列长度
     */
    private int size;

    private BlockingDeque<BaseData> dataQueue;
    private RateLimiter rateLimiter;


    public void init() {


        dataQueue = new LinkedBlockingDeque<BaseData>(size);
        rateLimiter = RateLimiter.create(speed);
    }
    public  RateModel(){

    }
    public void addData(BaseData data) {

        dataQueue.offer(data);
        log.info("offer :name={},queue={}",name,dataQueue.size());
        LimitConfig.lock.lock();
        try{
            LimitConfig.signCondition.signalAll();
        }finally {
            LimitConfig.lock.unlock();
        }

    }

    public BaseData getLimitData() {
        synchronized (rateLimiter) {
            BaseData obj = null;
            rateLimiter.acquire();
            while (true) {
                obj = dataQueue.poll();
                log.info("poll :name={},queue={}",name,dataQueue.size());
                if (obj != null) {
                    break;
                }

                obj = getOther();
                if (obj != null) {
                    break;
                }

                //进入阻塞态
                log.info("{}","await");
                try {
                    LimitConfig.lock.lock();
                    LimitConfig.signCondition.await(10, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }finally {
                    LimitConfig.lock.unlock();
                }
            }


            return obj;
        }

    }


    private BaseData getOther() {

        Iterator<String> keyIt = LimitConfig.rateModelConcurrentHashMap.keySet().iterator();
        while (keyIt.hasNext()) {

            String key = keyIt.next();

            if (!key.equalsIgnoreCase(name)) {
                BaseData data = LimitConfig.rateModelConcurrentHashMap.get(key).dataQueue.poll();
                if (data != null) {
                    return data;
                }
            }
        }

        return null;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
