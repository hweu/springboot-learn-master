package com.learn.example.util.timmer;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务
 */
@EnableScheduling
@Component
public class Scheduler {
    @Scheduled(cron="0/5 * * * * ?")//每五秒执行一次
    public void huoqudangqian() {
        System.out.println("每五秒执行一次："+new Date());
    }
}
