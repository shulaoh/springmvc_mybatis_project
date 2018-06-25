<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
     <form action="${pageContext.request.contextPath}/login" method="post">  
    	 service_version：<input type="text" name="service_version"/> <br><br>  
    	 input_charset：<input type="text" name="input_charset"/> <br><br>  
    	 tid：<input type="text" name="tid"/> <br><br>  
                      用户Id：<input type="text" name="loginId"/> <br><br>  
                      密码：<input type="password" name="password"/> <br><br>  
       <input type="submit" value="提交"/>  
   </form>
     
   <form action="${pageContext.request.contextPath}/updateMyPassword" method="post">  
    	 service_version：<input type="text" name="service_version"/> <br><br>  
    	 input_charset：<input type="text" name="input_charset"/> <br><br>  
    	 tid：<input type="text" name="tid"/> <br><br>  
    	 oldPassword：<input type="text" name="oldPassword"/> <br><br>  
    	 newPassword：<input type="text" name="newPassword"/> <br><br>      	
       <input type="submit" value="提交"/>  
   </form>  

   <form action="${pageContext.request.contextPath}/getLessons" method="post">  
    	 service_version：<input type="text" name="service_version"/> <br><br>  
    	 input_charset：<input type="text" name="input_charset"/> <br><br>  
    	 tid：<input type="text" name="tid"/> <br><br>  
    	 课程类型：<input type="text" name="lessonType"/> <br><br>  
    	 操作类型：<input type="text" name="oprType"/> <br><br>  
    	 每页记录数：<input type="text" name="showCount"/> <br><br>
    	 当前页：<input type="text" name="pageNum"/> <br><br>     	   	
       <input type="submit" value="提交"/>  
   </form>  
</body>
</html>