package com.web.data.pojo;

import java.sql.Timestamp;

public class Notification {

    private String notificationId;

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public Timestamp getNotificationDatetime() {
        return notificationDatetime;
    }

    public void setNotificationDatetime(Timestamp notificationDatetime) {
        this.notificationDatetime = notificationDatetime;
    }

    private String lessonId;

    private String notificationType;

    private Timestamp notificationDatetime;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
