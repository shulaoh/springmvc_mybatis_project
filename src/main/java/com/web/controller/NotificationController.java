package com.web.controller;

import com.web.data.pojo.LessonCommon;
import com.web.data.pojo.Notification;
import com.web.data.pojo.SysUser;
import com.web.service.INotificationService;
import com.web.utils.DataDesc;
import com.web.utils.Page;
import com.web.utils.ResourceDesc;
import com.web.utils.Result;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class NotificationController {

    @Resource
    private INotificationService notificationService;

    private static Logger logger = Logger.getLogger(NotificationController.class);

    @RequestMapping({"/getNotifications"})
    @ResponseBody
    public Map<String, Object> getNotifications(@RequestParam Integer startPage,
                                                @RequestParam Integer size, HttpSession session)
    {
        ResourceDesc rdesc = new ResourceDesc();
        HashMap map = new HashMap();
        Result result = new Result();
        map.put("result", result);
        try
        {
            Map<String, Object> data = new HashMap<>();
            SysUser user = (SysUser) session.getAttribute("userSession");
            String userId = user.getUserId();
            int offset = (startPage - 1) * size;
            List<Notification> list = notificationService.getNotificationByUserId(userId, offset, size);

            int totalCount = notificationService.getNotificationCountByUserId(userId);
            map.put("totalCount", totalCount);
            data.put("notifications", list);
            map.put("data", data);
            result.setRetcode(1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result.setRetcode(-1);
            result.setRetmsg("获取通知列表失败" + e);
        }
        return map;
    }
}
