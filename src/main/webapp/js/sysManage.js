//初始化方法
var userList = new Object();
var userInfo = new Object();

$(function(){
	getMyUserInfo();
	getUserList();
	tableInit();
});

function getUserList(){
	var param = {};
	param.ustatus = "ALL";
	param.showCount = 100000;
	param.pageNum = 1;
	$.ajax({
		url: "getUserList.do",
        type: 'get',
        async: true,
        data: param,
        dataType: "json",
        success: function (msg,success,e) {
	       	 if(e.status == "200") {
	       		if(msg.result.retcode == 1){
	       			userList = msg.data.users;
	       			createTeacherList();
	       			//createUserAgreeList();
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

//创建审批信息
function createUserAgreeList(){
	$('#approveInfoId').append('');
	var html = '<ul data-role="listview" data-view="content" data-select-node="true">'
	for(var i=0;i<userList.length;i++){
		if(userList[i].ustatus == 'WAIT'){
			html += agreeListStyle(userList[i])
		}
	}
	html += '</ul>';
	$('#approveInfoId').append(html);
}

function agreeListStyle(user){
	var html = '<li id="liag'+user.userId+'" data-icon="<span class=\'mif-user-plus mif-1x fg-green\'></span>"'+
		        'data-caption="'+user.userName+'"'+
		        'data-content="<span class=\'text-muted\'>'+user.dept+'</span>">'+
		    	'<span class=\'mif-cross mif-2x fg-red\' style="margin-left:2rem;" id="no'+user.userId+'" onclick="noAgreeUser(this)"></span>'+
		    	'<span class=\'mif-checkmark mif-2x fg-green\' style="margin-left:2rem;" id="yes'+user.userId+'" onclick="yesAgreeUser(this)"></span></li>'
	return html;
}

function noAgreeUser(e){
	var d = dialog({
		title: '提示',
		content: '<span style="line-height:60px;font-size:16px;">确认拒绝该学员的注册信息？</span>',
		okValue: '确定',
		width:240,
		height:60,
		ok: function () {
			var userId = e.id.replace('no','');
			$('#liag'+userId+'').remove();
			var param = {};
			param.userId = userId;
			param.ustatus = 'REJ';
			updateUserStatus(param);
		},
		cancelValue: '取消',
		cancel: function () {}
	});
	d.showModal();
}
function yesAgreeUser(e){
	var userId = e.id.replace('yes','');
	$('#liag'+userId+'').remove();
	var param = {};
	param.userId = userId;
	param.ustatus = 'REGI';
	updateUserStatus(param);
}

function updateUserStatus(param){
	$.ajax({
		url: "updateUserStatus.do",
        type: 'get',
        async: true,
        data: param,
        dataType: "json",
        success: function (msg,success,e) {
	       	 if(e.status == "200") {
	       		if(msg.result.retcode == 1){
	       			
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

function getMyUserInfo(){
	$.ajax({
		url: "getMyUserInfo.do",
        type: 'get',
        async: true,
        data: '',
        dataType: "json",
        success: function (msg,success,e) {
	       	 if(e.status == "200") {
	       		userInfo = msg.data.userInfo;
	       		writeUserInfo(userInfo);
	       		getMyLessList();
	       	 }
       },
       error: function(XMLHttpRequest,textStatus,errorThrown){
			 alert(XMLHttpRequest.status);
			 alert(XMLHttpRequest.readyState);
			 alert(textStatus);
	   }
	});
}

//获取用户添加课程列表  用户至少是二级管理员
function getMyLessList(){
	var param = {};
	if(parseInt(userInfo.adminFlag) == 20){
		//返回所有用于测试
		param.creatorId = '';//userInfo.userId;
	}else if(parseInt(userInfo.adminFlag) == 30){
		param.creatorId = '';
	}else{
		return;
	}
	param.showCount = 10000;
	param.pageNum = 1;
	$.ajax({
		url: "getAllLessons.do",
        type: 'get',
        async: true,
        data: param,
        dataType: "json",
        success: function (msg,success,e) {
	       	 if(e.status == "200") {
	       		 if(msg.result.retcode == 1){
	       			 var html = '';
	       			 for(var i=0;i<msg.data.lessons.length;i++){
	       				var less = msg.data.lessons[i];
	       				if(i == 0){
	       					html += '<div class="row">';
	       				}
	       				if(i == msg.data.lessons[i].length-1){
	       					html += showLessonInfo(less);
	       					html += '</div>';
	       					break;
	       				}
	       				if(((i+1) % 4) > 0){
	       					html += showLessonInfo(less);
	       				}else{
	       					html += showLessonInfo(less);
	       					html += '</div><div class="row">';
	       				}
	       			 }
	       			 $('#lessListId').append(html);
	       			 
	       			addlessManageList(msg.data.lessons);
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

function addlessManageList(lesss){
	var list = new Array();
	for(var i=0;i<lesss.length;i++){
		var less = {};
		less.name = lesss[i].lessonName;
		if(lesss[i].lessonType == 'L'){
			less.type = '课程';
		}else if(lesss[i].lessonType == 'M'){
			less.type = '会议';
		}
		less.sTime = lesss[i].stime;
		less.eTime = lesss[i].etime;
		less.address = lesss[i].place;
		less.teacher = lesss[i].teacher.name;
		if(parseInt(lesss[i].lessonStatus) == 0){
			less.release = '<input type="checkbox" data-role="switch" id="u'+lesss[i].lessonId+'" onclick="updateLessStatus(this)">';
		}else if(parseInt(lesss[i].lessonStatus) == 1){
			less.release = '<input type="checkbox" data-role="switch" id="u'+lesss[i].lessonId+'" onclick="updateLessStatus(this)" checked>';
		}
		less.operate = '<span style="margin-right:1rem" lessid="'+lesss[i].lessonId+'" class="mif-insert-template mif-1x fg-blue" title="修改" onclick="updateLessInfo(this)"></span>'+
				'<span style="margin-right:1rem" lessonId="'+lesss[i].lessonId+'" lessonName="'+lesss[i].lessonName+'" teacherName="'+lesss[i].teacher.name+'" teacherPhone="'+lesss[i].teacher.phone+'" class="mif-enter mif-1x fg-blue" title="查看日程" onclick="openLessSch(this)"></span>';
		list.push(less);
	}
	$('#lessManageTable').dataTable().fnClearTable();//清空表格数据
	$('#lessManageTable').dataTable().fnAddData(list);//给表格添加数据
}

//课程管理列表
function tableInit(){
	$('#lessManageTable').DataTable({
        columns: [
            { title: "课程名称",data:"name",width:160},
            { title: "类型",data:"type",width:40},
            { title: "开始时间",data:"sTime",width:100 },
            { title: "结束时间",data:"eTime",width:100},
            { title: "地点",data:"address"},
            { title: "讲师",data:"teacher",width:60},
            { title: "发布",data:"release",width:40},
            { title: "操作",data:"operate",width:100,asSorting:[]}
        ],
		stateSave: true,//保存状态到cookie 很重要 ， 当搜索的时候页面一刷新会导致搜索的消失。使用这个属性就可避免了 
		language: table_language
    });
	$('#schListTable').DataTable({
        columns: [
            { title: "日程名称",data:"schName",width:100},
            { title: "开始时间",data:"ssTime",width:100 },
            { title: "时长(分钟)",data:"schLastMNum",width:100},
            { title: "地点",data:"schPlace"},
            { title: "操作",data:"operate",width:100,asSorting:[]}
        ],
		stateSave: true,//保存状态到cookie 很重要 ， 当搜索的时候页面一刷新会导致搜索的消失。使用这个属性就可避免了 
		language: table_language
    });	
}

function writeUserInfo(user){
	$('#userNameId').html(user.userName);
	$('#userDeptId').html(user.dept);
}
//修改密码
function updatePsw(){
	var d = dialog({
		title: '修改密码',
		id: 'updatePsw_Dialog',
		width:320,
		align: 'bottom left',
		quickClose: true,// 点击空白处快速关闭
		content: $('#updatePswDiv'),
		okValue: '确认',
		ok: function (){
			var oldPsw = $('#oldPsw').val();
			var newPsw = $('#newPsw').val();
			var againNewPsw = $('#againNewPsw').val();
			if(oldPsw != userInfo.password){
				//success 绿, info 蓝, warning 橙, alert 红
				//showAlert("原始密码错误|提示信息|alert");
				$('#updatePswError').html('原始密码错误');
				return false;
			}
			if(newPsw !== againNewPsw){
				$('#updatePswError').html('两次密码输入不一致');
				//showAlert("两次密码输入不一致|提示信息|alert");
				return false;
			}
			if(oldPsw == newPsw){
				$('#updatePswError').html('新密码不能与旧密码一样');
				//showAlert("新密码不能与旧密码一样|提示信息|alert");
				return false;
			}
			var param = {};
			param.oldPassword = oldPsw;
			param.newPassword = newPsw;
			$.ajax({
				url: "updateMyPassword.do",
		        type: 'get',
		        async: true,
		        data: param,
		        dataType: "json",
		        success: function (msg,success,e) {
			       	 if(e.status == "200") {
			       		$('#updatePswError').html('密码修改成功');
			       		//showAlert("密码修改成功|提示信息|success");
			       	 }
		       },
		       error: function(XMLHttpRequest,textStatus,errorThrown){
					 alert(XMLHttpRequest.status);
					 alert(XMLHttpRequest.readyState);
					 alert(textStatus);
			   }
			});
		}
	});
	d.show(document.getElementById('updatePsw'));
}

function releaseLess(e){
	alert(e)
}

function deleteLess(){
	alert("删除课程的方法");
}
function updateLess(){
	alert("更新课程的方法");
}
//拼课程或会议信息
function showLessonInfo(less){
	//课程图片路径  less.lessPicUrl
	var html = '<span class="lessStyle1">'+
				'<img alt="" src="https://lpg.tianmengit.com/cnooc_training/image/con1-eubg1.png" style="height: 10rem;width: 100%;">'+
				 '<div style="font-size:14px;">';
	html += '<p style="font-weight: 600;font-size:15px;margin-top: .5rem;margin-left: .7rem;">'+less.lessonName+'</p>'+
		'<p style="color: #777777;font-size:14px;">开始时间：'+less.stime+'</p>'+
		'<p style="color: #777777;font-size:14px;">结束时间：'+less.etime+'</p>'+
		'<p style="color: #777777;font-size:14px;">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点：'+less.place+'</p>'+
		'<p style="color: #777777;font-size:14px;">课程简介：'+less.lessonInfo+'</p>';
	html += '</div></span>';
	return html;
}