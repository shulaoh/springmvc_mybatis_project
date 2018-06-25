package com.web.service;

import com.web.data.pojo.Notification;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface INotificationService {
    int insert(Notification notification);

    int deleteByUserId(String userId);

    int delete(String nofiticationId);

    int getNotificationCountByUserId(String userId);

    List<Notification> getNotificationByUserId(@Param("userId") String userId, @Param("offset") int offset, @Param("size") int size);

    Map<String, Object> sendNotificationOneDayAhead();

    Map<String, Object> sendNotification2HourAhead();

    Map<String, Object> sendNotificationForChange(String lessonId, String change);

}
