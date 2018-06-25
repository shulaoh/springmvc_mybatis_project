<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <form action = "toUserListPage" method="post">
    <c:forEach items="${users}" var="item">  
        ${item.userId }--${item.username }--${item.name }<br />
    </c:forEach>
    <div style="padding:20px;">${page.pageStr}</div>
	</form>
</body>
</html>