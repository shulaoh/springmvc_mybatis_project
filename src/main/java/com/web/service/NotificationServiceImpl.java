package com.web.service;

import com.alibaba.fastjson.JSON;
import com.web.data.mapper.LessonManageMapper;
import com.web.data.mapper.NotificationMapper;
import com.web.data.pojo.*;
import com.web.sms.request.SmsVariableRequest;
import com.web.sms.response.SmsVariableResponse;
import com.web.utils.ChuangLanSmsUtil;
import com.web.utils.Tools;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("notificationService")
public class NotificationServiceImpl implements INotificationService{


    @Value("${sms.charset}")
    private String charset;

    @Value("${sms.account}")
    private String account;

    @Value("${sms.password}")
    private String password;

    @Value("${sms.server.url}")
    private String serverUrl;

    @Value("${sms.message.1day}")
    private String message1DayAhead;

    @Value("${sms.message.2hours}")
    private String message2HoursAhead;

    @Value("${sms.message.cancel}")
    private String messageCancel;

    @Value("${sms.message.update}")
    private String messageUpdate;

    @Resource
    private NotificationMapper notificationMapper;

    @Resource
    private LessonManageMapper lessonManageMapper;

    @Override
    public int insert(Notification notification) {
        return notificationMapper.insert(notification);
    }

    @Override
    public int deleteByUserId(String userId) {
        return notificationMapper.deleteByUserId(userId);
    }

    @Override
    public int delete(String notificationId) {
        return notificationMapper.delete(notificationId);
    }

    @Override
    public int getNotificationCountByUserId(String userId) {
        return notificationMapper.getNotificationCountByUserId(userId);
    }

    @Override
    public List<Notification> getNotificationByUserId(@Param("userId") String userId, @Param("offset") int offset, @Param("size") int size) {
        List<Notification> list = notificationMapper.getNotificationByUserId(userId, offset, size);
        for (Notification notification : list) {
            String message = createNotificationMessage(notification);
            notification.setMessage(message);
        }
        return list;
    }

    private String createNotificationMessage(Notification notification) {
        String type = notification.getNotificationType();
        String message;
        String lessonId = notification.getLessonId();
        String datetime = Tools.formatTimestampNoSec(notification.getNotificationDatetime());
        LessonCommon lesson = lessonManageMapper.getLessonByLessonId(lessonId);
        String place = lesson.getPlace();
        String lessonName = lesson.getLessonName();
        String result;
        switch (type) {
            case  "sms.message.1day" :
                message = message1DayAhead;
                String[] m1 = {datetime, place, lessonName};
                result = getMessage(message, m1);
                break;
            case "sms.message.2hours":
                message = message2HoursAhead;
                String[] m2 = {place, lessonName, datetime};
                result = getMessage(message, m2);
                break;
            case "sms.message.cancel":
                message = messageCancel;
                String[] m3 = {datetime, place, lessonName};
                result = getMessage(message, m3);
                break;
            case "sms.message.update":
                message = messageUpdate;
                String[] m4 = {datetime, place, lessonName};
                result = getMessage(message, m4);
                break;
            default:
                result = "";
                break;
        }
        return result;
    }

    private String getMessage(String message, String[] vars) {
        String[] str = message.split("\\{\\$var\\}");
        StringBuffer sb = new StringBuffer();
        int i;
        for(i = 0; i < vars.length; i++) {
            sb.append(str[i]);
            sb.append(vars[i]);
        }
        sb.append(str[i]);
        return sb.toString();
    }

    public Map<String, Object> sendNotificationOneDayAhead() {

        List<LessonCommon> list = lessonManageMapper.getLessonsStartTomorrow();
        Map<String, Object> map = new HashMap<>();
        if (list != null && list.size() > 0) {

            String params = "";
            for (LessonCommon lesson : list) {
                String name = lesson.getLessonName();
                String place = lesson.getPlace();
                java.sql.Timestamp t = lessonManageMapper.getStartDateByLessonId(lesson.getLessonId());
                List<SysUser> users = lesson.getStudentList();
                if (users != null && users.size() > 0) {
                    for (SysUser user: users) {
                        String phone = user.getPhone();
                        Notification n = new Notification();
                        n.setNotificationId(Tools.generateID());
                        n.setNotificationDatetime(new Timestamp(new Date().getTime()));
                        n.setNotificationType("sms.message.1day");
                        n.setLessonId(lesson.getLessonId());
                        n.setUserId(user.getUserId());
                        notificationMapper.insert(n);
                        if (phone != null && phone.trim().length() > 0) {
                            params = params + phone + "," + Tools.formatTimestampNoSec(t) + "," + place + "," + name + ";";
                        }
                    }
                }
            }

            map.putAll(smsNotification(message1DayAhead, params));

        }
        return map;
    }

    @Override
    public Map<String, Object> sendNotification2HourAhead() {
        List<LessonCommon> list = lessonManageMapper.getLessonsStart2Hours();
        Map<String, Object> map = new HashMap<>();
        if (list != null && list.size() > 0) {

            String params = "";
            for (LessonCommon lesson : list) {
                String name = lesson.getLessonName();
                String place = lesson.getPlace();
                java.sql.Timestamp t = lessonManageMapper.getStartDateByLessonId(lesson.getLessonId());
                List<SysUser> users = lesson.getStudentList();
                if (users != null && users.size() > 0) {
                    for (SysUser user: users) {
                        String phone = user.getPhone();
                        Notification n = new Notification();
                        n.setNotificationId(Tools.generateID());
                        n.setNotificationDatetime(new Timestamp(new Date().getTime()));
                        n.setNotificationType("sms.message.2hours");
                        n.setLessonId(lesson.getLessonId());
                        n.setUserId(user.getUserId());
                        notificationMapper.insert(n);
                        if (phone != null && phone.trim().length() > 0) {
                            params = params + phone +  "," + place + "," + name + "," + Tools.formatTimestampNoSec(t) + ";";
                        }
                    }
                }
            }

            map.putAll(smsNotification(message2HoursAhead, params));

        }
        return map;
    }

    @Override
    public Map<String, Object> sendNotificationForChange(String lessonId, String change) {
        Map<String, Object> map = new HashMap<>();
        LessonCommon lesson = lessonManageMapper.getLessonByLessonId(lessonId);
        String params = "";
        String message;
        String type;
        if (change.equals("update")) {
            message = messageUpdate;
            type = "sms.message.update";
        } else {
            type = "sms.message.cancel";
            message = messageCancel;
        }
        if (lesson != null) {
            String name = lesson.getLessonName();
            String place = lesson.getPlace();
            java.sql.Timestamp t = lessonManageMapper.getStartDateByLessonId(lesson.getLessonId());
            //当lesson的开始时间晚于当前时间，才会更新短信。
            if (t == null || t.getTime() > new Date().getTime()) {
                List<SysUser> users = lesson.getStudentList();
                if (users != null && users.size() > 0) {
                    for (SysUser user : users) {
                        Notification n = new Notification();
                        n.setNotificationId(Tools.generateID());
                        n.setNotificationDatetime(new Timestamp(new Date().getTime()));
                        n.setNotificationType(type);
                        n.setLessonId(lesson.getLessonId());
                        n.setUserId(user.getUserId());
                        notificationMapper.insert(n);
                        String phone = user.getPhone();
                        if (phone != null && phone.trim().length() > 0) {
                            params = params + phone + "," + Tools.formatTimestampNoSec(t) + "," + place + "," + name + ";";
                        }
                    }
                }

                map.putAll(smsNotification(message, params));
            }
        }
        return map;
    }


    private Map<String, Object> smsNotification(String message, String params) {
        Map<String, Object> map = new HashMap<>();
        String prefix = "【253云通讯】";
        SmsVariableRequest smsVariableRequest=new SmsVariableRequest(account, password, prefix + message, params, "true");
        String requestJson = JSON.toJSONString(smsVariableRequest);
        System.out.println("before request string is: " + requestJson);

        String response = ChuangLanSmsUtil.sendSmsByPost(serverUrl, requestJson);
        System.out.println("response after request result is : " + response);
        SmsVariableResponse smsVariableResponse = JSON.parseObject(response, SmsVariableResponse.class);
        System.out.println("response  toString is : " + smsVariableResponse);
        map.put("errMsg", smsVariableResponse.getErrorMsg());
        map.put("code", smsVariableResponse.getCode());
        return map;
    }

}
