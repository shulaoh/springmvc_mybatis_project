<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
     <form action="${pageContext.request.contextPath}/login.do" method="post">  
    	 service_version：<input type="text" name="service_version"/> <br><br>  
    	 input_charset：<input type="text" name="input_charset"/> <br><br>  
    	 tid：<input type="text" name="tid"/> <br><br>  
                      用户Id：<input type="text" name="loginId"/> <br><br>  
                      密码：<input type="password" name="password"/> <br><br>  
       <input type="submit" value="提交"/>  
   </form>
     
   <form action="${pageContext.request.contextPath}/updateMyPassword.do" method="post">  
    	 service_version：<input type="text" name="service_version"/> <br><br>  
    	 input_charset：<input type="text" name="input_charset"/> <br><br>  
    	 tid：<input type="text" name="tid"/> <br><br>  
    	 oldPassword：<input type="text" name="oldPassword"/> <br><br>  
    	 newPassword：<input type="text" name="newPassword"/> <br><br>      	
       <input type="submit" value="提交"/>  
   </form>  

   <form action="${pageContext.request.contextPath}/getLessons.do" method="post">  
    	 service_version：<input type="text" name="service_version"/> <br><br>  
    	 input_charset：<input type="text" name="input_charset"/> <br><br>  
    	 tid：<input type="text" name="tid"/> <br><br>  
    	 课程类型：<input type="text" name="lessonType"/> <br><br>  
    	 操作类型：<input type="text" name="oprType"/> <br><br>  
    	 每页记录数：<input type="text" name="showCount"/> <br><br>
    	 当前页：<input type="text" name="pageNum"/> <br><br>     	   	
       <input type="submit" value="提交"/>  
   </form>  
   
   <form action="${pageContext.request.contextPath}/joinLesson.do" method="post">  
    	 service_version：<input type="text" name="service_version"/> <br><br>  
    	 input_charset：<input type="text" name="input_charset"/> <br><br>  
    	 tid：<input type="text" name="tid"/> <br><br>  
    	 课程Id：<input type="text" name="lessonId"/> <br><br>      	 	
       <input type="submit" value="提交"/>  
   </form> 
   
   
   <form action="${pageContext.request.contextPath}/getLessons.do" method="post">  
    	 
    	 课程id：<input type="text" name="lessonId"/> <br><br>  
    	
    	 当前页：<input type="text" name="startPage"/> <br><br>
    	 每页记录数：<input type="text" name="size"/> <br><br>     	   	
       <input type="submit" value="提交"/>  
   </form>
   
   <form action="${pageContext.request.contextPath}/putLesson.do" method="post">  
    	 
    	 课程id：<input type="text" name="lessonId"/> <br><br>  
    	 课程name：<input type="text" name="lessonName"/> <br><br>
    	 课程name：<input type="text" name="lessonType"/> <br><br>
    	 课程place：<input type="text" name="place"/> <br><br>
    	 课程lessonInfo：<input type="text" name="lessonInfo"/> <br><br>
    	 课程purl：<input type="text" name="purl"/> <br><br>
    	 课程teacherId：<input type="text" name="teacherId"/> <br><br>
    	 课程creatorId：<input type="text" name="creatorId"/> <br><br>
    	 课程allCommFlag：<input type="text" name="allCommFlag"/> <br><br>
    	 课程lessPicUrl：<input type="text" name="lessPicUrl"/> <br><br>
    	 课程lessCycPicUrl：<input type="text" name="lessCycPicUrl"/> <br><br> 
    	 课程inviUserIds：<input type="text" name="inviUserIds"/> <br><br>    	   	
    	 课程commTempIds：<input type="text" name="commTempIds"/> <br><br>    	   	
    	 课程adminIds：<input type="text" name="adminIds"/> <br><br>    	   	   	   	
       <input type="submit" value="提交"/>  
   </form>  
   
   
   <form action="${pageContext.request.contextPath}/getSchedules.do" method="post">  
    	 
    	 课程id：<input type="text" name="lessonId"/> <br><br>  
    	
    	 scheduleId：<input type="text" name="scheduleId"/> <br><br>
    		   	
       <input type="submit" value="提交"/>  
   </form>
   
   
   
   
   
   
   
   <form action="${pageContext.request.contextPath}/putSchedule.do" method="post">  
    	 
    	 日程id：<input type="text" name="lessonId" /> <br><br>  
    	 schPlace：<input type="text" name="schPlace"/> <br><br>
    	 scheduleId：<input type="text" name="scheduleId"/> <br><br>
    	 schName：<input type="text" name="schName"/> <br><br>
    	 schIntro：<input type="text" name="schIntro"/> <br><br>
    	 ssTime：<input type="text" name="ssTime"/> <br><br>
    	 schLastMNum：<input type="text" name="schLastMNum"/> <br><br>
    	 signFlag：<input type="text" name="signFlag"/> <br><br>
    	 signSTime：<input type="text" name="signSTime"/> <br><br>
    	 signETime：<input type="text" name="signETime"/> <br><br>
    	 commentFlag：<input type="text" name="commentFlag"/> <br><br> 
    	 allCommFlag：<input type="text" name="allCommFlag"/> <br><br>  
    	 tutorId：<input type="text" name="tutorId"/> <br><br>  	   	
    	 commTempIds：<input type="text" name="commTempIds"/> <br><br>    	   	
    	 attachments： <input type="text" name="attachments"/> <br><br>    	   	
       <input type="submit" value="提交"/>  
   </form>  
   
   
</body>
</html>