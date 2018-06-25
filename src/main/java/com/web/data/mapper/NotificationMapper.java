package com.web.data.mapper;

import com.web.data.pojo.Notification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotificationMapper {

    int insert(Notification notification);

    int deleteByUserId(String userId);

    int delete(String notificationId);

    List<Notification> getNotificationByUserId(@Param("userId") String userId, @Param("offset") int offset, @Param("size") int size);

    int getNotificationCountByUserId(String userId);
}
