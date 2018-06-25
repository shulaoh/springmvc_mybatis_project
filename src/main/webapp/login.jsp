<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="imagetoolbar" content="no" />
<title>北京人力培训系统</title>
<link rel="stylesheet" href="common/MetroUICSS4/css/metro-all.css">
<link rel="stylesheet" href="common/MetroUICSS4/css/metro-colors.css">
<link rel="stylesheet" href="common/MetroUICSS4/css/metro-icons.css">
<link rel="stylesheet" href="common/MetroUICSS4/css/metro-rtl.css">
<style type="text/css">
html{
	height: 100%;
    width: 100%;
    margin: 0;
    padding: 0;
    background-color : black;
	background-image: url("image/loginImage.png");
	background-repeat: no-repeat;
	background-size:100% 100%;
	font-family: "Arial","Microsoft YaHei","黑体","宋体","sans-serif";
}
body {
  line-height: 1;
  overflow-x: hidden;
  background-color: rgba(0,0,0,0);
}
.fontStyleB {
    margin-bottom: 0px;
    padding-top: 20px;
    width: 100%;
    height: 78px;
    line-height: 48px;
    text-align: center;
    font-size: 24px;
    font-family: "微软雅黑";
    font-weight: normal;
}
</style>
</head>
<script src="common/MetroUICSS4/js/jquery-3.3.1.min.js"></script>
<script src="common/MetroUICSS4/js/jquery.dataTables.js"></script>
<script src="common/MetroUICSS4/js/metro.js"></script>
<script src="js/json2.js"></script>
<body>
<div style="margin:0 auto;border-radius: .5rem;width:500px;height:320px;background:rgba(227, 228, 229, 0.7);text-align:center;margin-top:16%;">
	<div class="row" style="background-color: #e4e4e459;border-radius: .5rem;margin-right:0;margin-left: 0;">
		<span class="fontStyleB">用户登录</span>
	</div>
	<div style="margin:2rem;">
		<div class="row" style="margin-top:1rem;height:4rem;">
			<div class="cell-2" style="line-height:2.215rem;">用户名</div>
			<div class="cell-10">
				<input type="text" data-role="input" id="userName" placeholder="手机号码或邮箱地址"
   					data-prepend="<span class='mif-user'></span>">
			</div>
		</div>
		<div class="row" style="height:4rem;">
			<div class="cell-2" style="line-height:2.215rem;">密码</div>
			<div class="cell-10">
				<input type="password" data-role="input" id="password" placeholder="密码输入框">
			</div>
		</div>
		<div id="loginError" style="color:#ff7f50"></div>
	    <div class="row">
	        <button class="button success" onclick="submitLogin()" style="width:12rem;margin-left: 9rem;">登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</button>
	    </div>
	</div>
</div>
</body>
<script type="text/javascript">
function submitLogin(){
	var param = {};
	param.loginId = $('#userName').val();
	param.password = $('#password').val();
	$.ajax({
		url: "login.do",
        type: 'get',
        async: true,
        data: param,
        dataType: "json",
        success: function (msg,success,e) {
	       	 if(e.status == "200") {
	       		 if(msg.result.retcode == 1){
	       			window.open('manage.html','_self');
	       		 }
	       	 }
       },
       error: function(XMLHttpRequest,textStatus,errorThrown){
			 alert(XMLHttpRequest.status);
			 alert(XMLHttpRequest.readyState);
			 alert(textStatus);
	   }
	});
}
</script>
</html>