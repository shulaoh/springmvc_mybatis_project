package com.web;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web.data.mapper.CommentReviewMapper;
import com.web.data.mapper.LessonMapper;
import com.web.data.pojo.CommentReview;
import com.web.data.pojo.Lession;
import com.web.service.CommentReviewServiceImpl;
import com.web.service.LessonManageServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.web.data.mapper.LessonManageMapper;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        String a = "\\dfdfdf";
        System.out.println(a);
        a = a.replaceAll("\\\\", File.separator);
        System.out.println(a);

        //ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

        //SmsScheduleTask task = (SmsScheduleTask)ac.getBean("smsScheduleTask");
        //task.show();

        /*Calendar date = Calendar.getInstance();
        SimpleDateFormat datef = new SimpleDateFormat("yyMMddHHmmss");
        String tempName = datef.format(date.getTime());
        System.out.println(tempName);*/
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");
        LessonMapper nm = (LessonMapper) ac.getBean("lessonMapper");

        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", " 01bfba19959f4a1ab75c3ccebb0ffe77");
        map.put("lessonType", "P");

        List<Lession> l = nm.selectAllLessonByUserId(map);
        System.out.println(l.size());
        //CommentReviewServiceImpl impl = (CommentReviewServiceImpl)ac.getBean("commentReviewService");
        //System.out.println(impl);



        //LessonManageMapper sd = (LessonManageMapper)ac.getBean("lessonManageMapper");
       // LessonManageServiceImpl ims = (LessonManageServiceImpl)ac.getBean("iLessonManageServiceImpl");
       // System.out.println(ims);

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
