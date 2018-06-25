package com.web;

import com.web.data.mapper.*;
import com.web.data.pojo.*;
import com.web.service.ILessonManageService;
import com.web.service.LessonManageServiceImpl;
import com.web.task.SmsScheduleTask;
import com.web.utils.Tools;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        //ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

        //SmsScheduleTask task = (SmsScheduleTask)ac.getBean("smsScheduleTask");
        //task.show();

        /*Calendar date = Calendar.getInstance();
        SimpleDateFormat datef = new SimpleDateFormat("yyMMddHHmmss");
        String tempName = datef.format(date.getTime());
        System.out.println(tempName);*/
        /**ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");
        LessonManageMapper nm = (LessonManageMapper)ac.getBean("lessonManageMapper");
        Map<String, Object> map = new HashMap<>();
        map.put("lessonId", "f29cc12b38354b029cdffc0a887fcbfa");
        map.put("scheduleId", "0d442057b75e4506bb05b37b62e2fea4");
        map.put("offset", 1);
        map.put("size", 2);
        int c = nm.getFilesCountByLessonId(map);
        System.out.println(c);**/
        String a = "培生幼儿英语（共三套）.xlsx,培生幼儿英语（共三套）_180624223154.xlsx;commentTemplate.xlsx,commentTemplate_180624223213.xlsx";
        String[] file = a.split(";");
        for (int i = 0; i < file.length; ++i) {
            String[] filenames = file[i].split(",");
            FileContent fc = new FileContent();
            String filename = filenames[0];
            String attach = filenames[1];

            fc.setFileId("-1");
            fc.setFileName(filename);
            fc.setFileType(filename.substring(filename.lastIndexOf(".") + 1));
            fc.setFileURL(attach);

        }




        //LessonManageMapper sd = (LessonManageMapper)ac.getBean("lessonManageMapper");
        //LessonManageServiceImpl ims = (LessonManageServiceImpl)ac.getBean("iLessonManageServiceImpl");

        /**List<SysUser> list = sd.getStudentListByLessonId("8a1cd77d0d2b45b284a78d549c8bd4fd");
        System.out.println(list.size());

         LessonCommon common = sd.getLessonByLessonId("11a57543dd584eb4a6220617ee96c707");
        System.out.println("test");

        //List<LessonCommon> l2 = ims.getLessonListByUserId("35b62716e3c2400b8c139119b4ffe3f0", 1, 10);
        //System.out.println(l2.size());

        int c = sd.deleteLesson("11a57543dd584eb4a6220617ee96c707");
        System.out.println("test: " + c);


        Schedule s = sd.getScheduleByScheduleId("108dfb2cc1c84bddb1cedf184688d447");
        System.out.println(s.toString());

        List<Schedule> sList = sd.getScheduleByLessonId("c831a80b4618411e9217e9c480eae250");
        System.out.println(sList.size());

        //java.sql.Timestamp ts = sd.getStartDateByLessonId("c831a80b4618411e9217e9c480eae250");
        //System.out.println(ts.toString());**/


        /**LessonAdminMapper lam = (LessonAdminMapper) ac.getBean("lessonAdminMapper");
        LessonAdmin admin = new LessonAdmin();
        String lessonId = "11a57543dd584eb4a6220617ee96c707";
        String userId = "2bc77c4777fb47a3a4dcc1f01adb3996";
        admin.setLessonId(lessonId);
        admin.setAdminId(userId);
        lam.insertLessonAdmin(admin);
        List<String> list = new ArrayList<>();
        list.add("tet");
        //lam.deleteLessonAdminBatch(lessonId, list);**/
    }
}
