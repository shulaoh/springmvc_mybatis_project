package com.web.task;


import com.web.service.INotificationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class SmsScheduleTask {

    private static Logger logger = Logger.getLogger(SmsScheduleTask.class);
    @Resource
    private INotificationService notificationService;

    @Scheduled(cron = "0 0 9 * * ?")
    public void notifyOneDayAhead() {
        logger.info("为第二天开始的课程发送通知。");
        notificationService.sendNotificationOneDayAhead();
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    public void setNotificationService() {
        logger.info("为两个小时后开始的课程发送通知");
        notificationService.sendNotification2HourAhead();
    }



//    public void show() {
//        System.out.println("user :" + account);
//    }

}
