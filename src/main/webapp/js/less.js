
function requestLesson(e){
	if(e.attributes.status.value == 'update'){
		updateLessInfoById(e.attributes.uLessId.value);
	}else if(e.attributes.status.value == 'add'){
		addLess();
	}
}

//添加课程基本信息
function addLess(){
	var param = {};
	param.lessonName = $('#lessNameId').val();
	param.lessonType = $("#addLessTarget input[type='radio']:checked").val();    //L：课程 M：会议
	param.stime = $('#lessStimeL').val();
	param.etime = $('#lessEtimeL').val();
	param.place = $('#lessAddressId').val();
	param.lessonInfo = $('#lessIntroId').val();
	param.teacherId = teachIdTemp;
	param.creatorId = userInfo.userId;
	var uArray = new Array();
	for(var i=0;i<userArray.length;i++){
		uArray.push(userArray[i].userId);
	}
	param.inviUserIds = uArray.join(',');
	param.purl = "";
	//param.commentFlag = "N";
	param.allCommFlag = "N";
	param.lessPicUrl = "";
	param.lessCycPicUrl = "";
	if(teachIdTemp.length == 0){
		$("#errorLessId").css("color","#ff4500");
		$('#errorLessId').html('请选择一个讲师');
		return;
	}
	$.ajax({
		url: "addLesson.do",
        type: 'get',
        async: true,
        data: param,
        dataType: "json",
        success: function (msg,success,e) {
	       	 if(e.status == "200") {
	       		if(msg.result.retcode == 1){
	       			//$("#errorLessId").css("color","#00d600");
	       			//$('#errorLessId').html('信息添加成功');
	       			clearLessForm();
	       			$('#requestSchId').attr("lessId","sch"+msg.result.retmsg+"");
	       			$('#requestSchId').attr("lessName",param.lessonName);
	       			closeAddLess();
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

function updateLessInfo(e){
	clearLessForm();
	$('#addLessTitleName').html("修改课程");
	controlLessShow();
	ChooseUserList();
	$('#addLessTarget form').attr('uLessId',e.attributes.lessid.value);
	$('#addLessTarget form').attr('status','update');
	var param = {};
	param.lessonId = e.attributes.lessid.value;
	param.lessonStatus="Y";
	$.ajax({
		url: "getLessonById.do",
        type: 'get',
        async: true,
        data: param,
        dataType: "json",
        success: function (msg,success,e) {
	       	 if(e.status == "200") {
	       		 var less = msg.data.lesson;
	       		 $(":radio[name='lessType'][value='"+less.lessonType+"']").prop("checked", "checked");
	       		 $("#lessNameId").val(less.lessonName);
	       		 $("#lessStimeL").val(less.stime);
	       		 $("#lessEtimeL").val(less.etime);
	       		 $("#lessAddressId").val(less.place);
	       		 $("#lessIntroId").val(less.lessonInfo);
	       		 $('#teach'+less.teacherId+'').css("background-color","#2ac4f466");
	       		 $('#teach'+less.teacherId+'').append("<span class='mif-checkmark mif-2x fg-green place-right' style='margin-right:.5rem'></span>");
	       		 teachIdTemp = less.teacherId;
	       		 getLessStuByLessId(less.lessonId);
	       	 }
       },
       error: function(XMLHttpRequest,textStatus,errorThrown){
			 alert(XMLHttpRequest.status);
			 alert(XMLHttpRequest.readyState);
			 alert(textStatus);
	   }
	});
}

function getLessStuByLessId(lessId){
	var param = {};
	param.lessonId = lessId;
	param.stuStatus = 'ALL';
	param.showCount = 10000;
	param.pageNum = 1;
	$.ajax({
		url: "getLessonStu.do",
        type: 'get',
        async: true,
        data: param,
        dataType: "json",
        success: function (msg,success,e) {
	       	 if(e.status == "200") {
	       		 if(msg.result.retcode == 1){	       			
	       			userArray = new Array();
	       			 for(var i=0;i<msg.data.lessonStus.length;i++){
	       				 var stu = msg.data.lessonStus[i];
	       				$('#user'+stu.userId+'').css("background-color","#2ac4f466");
	       				$('#user'+stu.userId+'').append("<span class='mif-checkmark mif-2x fg-green place-right' style='margin-right:.5rem'></span>");
	       				var json = {};
	       				json.userId = stu.userId;
	       				json.userName = stu.name;	       				
	       				userArray.push(json);
	       			 }
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

function updateLessInfoById(lessId){
	var param = {};
	param.lessonName = $('#lessNameId').val();
	param.lessonType = $("#addLessTarget input[type='radio']:checked").val();    //L：课程 M：会议
	param.stime = $('#lessStimeL').val();
	param.etime = $('#lessEtimeL').val();
	param.place = $('#lessAddressId').val();
	param.lessonInfo = $('#lessIntroId').val();
	param.teacherId = teachIdTemp;
	param.lessonId = lessId;
	var uArray = new Array();
	for(var i=0;i<userArray.length;i++){
		uArray.push(userArray[i].userId);
	}
	param.inviUserIds = uArray.join(',');
	param.purl = "";
	//param.commentFlag = "N";
	param.allCommFlag = "N";
	param.lessPicUrl = "";
	param.lessCycPicUrl = "";
	$.ajax({
		url: "updateLesson.do",
        type: 'get',
        async: true,
        data: param,
        dataType: "json",
        success: function (msg,success,e) {
	       	 if(e.status == "200") {
	       		 if(msg.result.retcode == 1){
	       			closeAddLess();
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

function updateLessStatus(e){
	var param = {};
	param.lessonId = e.id.replace("u","");
	if(e.checked){
		param.lessonStatus = 1;
	}else{
		param.lessonStatus = 0;
	}
	$.ajax({
		url: "updateLessStatus.do",
        type: 'get',
        async: true,
        data: param,
        dataType: "json",
        success: function (msg,success,e) {
	       	 if(e.status == "200") {
	       	 }
       },
       error: function(XMLHttpRequest,textStatus,errorThrown){
			 alert(XMLHttpRequest.status);
			 alert(XMLHttpRequest.readyState);
			 alert(textStatus);
	   }
	});
}

function clearLessForm(){
	$('#lessNameId').val('');
	$('#lessStimeL').val('');
	$('#lessEtimeL').val('');
	$('#lessAddressId').val('');
	$('#lessNameId').val('');
	$('#lessIntroId').val('');
	$('#chooseUserId').hide();
	if(teachIdTemp.length > 0){
		$('#teach'+teachIdTemp+'').css("background-color","#e4e4e4");
		$('#teach'+teachIdTemp+' span[class="mif-checkmark mif-2x fg-green place-right"]').remove();
	}

	for(var i=0;i<userArray.length;i++){
		$('#user'+userArray[i].userId+'').css("background-color","#e4e4e4");
		$('#user'+userArray[i].userId+' span[class="mif-checkmark mif-2x fg-green place-right"]').remove()
	}
	teachIdTemp = '';
	userArray = new Array();
}

function createTeacherList(){
	var html = '';
	$('#teacherListId').html('');
	var userObj = new Array();
	for(var i=0;i < userList.length;i++){
		if(parseInt(userList[i].adminFlag) == 10){
			userObj.push(userList[i]);
		}
	}
	for(var i=0;i < userObj.length;i++){
		var user = userObj[i];
		if(i == 0){
			html += '<ul data-role="listview" data-view="content" data-select-node="true"><div class="row" style="margin-bottom:.5rem">';
		}
		if(i == userObj.length-1){
			html += teacherListStyle(user);
			html += '</div></ul>';
			break;
		}
		if(((i+1) % 2) > 0){
			html += teacherListStyle(user);
		}else{
			html += teacherListStyle(user);
			html += '</div><div class="row" style="margin-bottom:.5rem">';
		}
	}
	$('#teacherListId').append(html);
}
var teachIdTemp = '';
function teacherChoose(e){
	var tid = e.id.replace('teach','');
	if(teachIdTemp.length > 0){
		if(teachIdTemp == tid){
			$('#'+e.id+'').css("background-color","#e4e4e4");
			$('#'+e.id+' span[class="mif-checkmark mif-2x fg-green place-right"]').remove();
			teachIdTemp = '';
			return;
		}
	}
	$('#teach'+teachIdTemp+'').css("background-color","#e4e4e4");
	$('#teach'+teachIdTemp+' span[class="mif-checkmark mif-2x fg-green place-right"]').remove();
	teachIdTemp = '';
	teachIdTemp = tid;
	$('#'+e.id+'').css("background-color","#2ac4f466");
	$('#'+e.id+'').append("<span class='mif-checkmark mif-2x fg-green place-right' style='margin-right:.5rem'></span>");
}
//添加学员列表
function ChooseUserList(){
	var html = '';
	$('#chooseUserListId').html('');
	for(var i=0;i < userList.length;i++){
		var user = userList[i];
		if(i == 0){
			html += '<ul data-role="listview" data-view="content" data-select-node="true"><div class="row" style="margin-bottom:.5rem">';
		}
		if(i == userList.length-1){
			html += userListStyle(user);
			html += '</div></ul>';
			break;
		}
		if(((i+1) % 3) > 0){
			html += userListStyle(user);
		}else{
			html += userListStyle(user);
			html += '</div><div class="row" style="margin-bottom:.5rem">';
		}
	}
	$('#chooseUserListId').append(html);
	/*var d = dialog({
			title: '选择受邀学员',
			id: 'ChooseUser_Dialog',
			width:640,
			align: 'bottom left',
			quickClose: true,// 点击空白处快速关闭
			content: $('#userListDialog'),
			okValue: '确认',
			ok: function (){
				if(userArray.length > 0){
					$('#chooseUserListId').html('');
					for(k=0;k<userArray.length;k++){
						var userhtml = '<button id="but'+userArray[k].userId+'" type="button" class="button secondary" style="width:8rem;margin:0 .3rem .5rem 0">'+userArray[k].userName+
						'<span class="mif-cross mif-2x fg-pink" style="margin-left:1rem;" id="u'+userArray[k].userId+'" onclick="deleteUserFromArray(this)"></span></button>';
						$('#chooseUserListId').append(userhtml);
					}
					$('#chooseUserId').show();
				}else{
					return false;
				}
			}
		});
	d.show(document.getElementById('ChooseUserListId'));*/
}

function deleteUserFromArray(e){
	var uid = e.id.replace('u','');
	for(var i=0;i<userArray.length;i++){
		if(uid == userArray[i].userId){
			userArray.remove(userArray[i]);
			$('#but'+uid+'').remove();
			break;
		}
	}
	if(userArray.length == 0){
		$('#chooseUserId').hide();
	}
}

var userArray = new Array();
function chechedUser(e){
	var userId = e.id.replace('user','');
	var userName = $('#'+e.id+'')[0].attributes.name.value;
	var json = {};
	json.userId = userId;
	json.userName = userName;
	if(userArray.length > 0){
		for(var i=0;i<userArray.length;i++){
			if(userId == userArray[i].userId){
				$('#'+e.id+'').css("background-color","#e4e4e4");
				userArray.remove(userArray[i]);
				$('#'+e.id+' span[class="mif-checkmark mif-2x fg-green place-right"]').remove()
				return;
			}
		}
	}
	userArray.push(json);
	$('#'+e.id+'').css("background-color","#2ac4f466");
	$('#'+e.id+'').append("<span class='mif-checkmark mif-2x fg-green place-right' style='margin-right:.5rem'></span>");
}

function teacherListStyle(user){
	var html = '<div class="cell-6">'+
		'<li id="teach'+user.userId+'" onclick="teacherChoose(this)" style="background-color: #e4e4e4;" data-icon="<span class=\'mif-user-plus mif-1x fg-green\'></span>" '+
	        'data-caption="'+user.userName+' <span style=\'font-size:13px;\'>'+user.phone+'</span>" '+
	        'data-content="<span class=\'text-muted\'>'+user.email+'</span>"> '+
	    '</li></div>';
	return html;
}

function userListStyle(user){
	var html = '<div class="cell-4">'+
		'<li style="background-color: #e4e4e4;" name="'+user.userName+'" data-icon="<span class=\'mif-user mif-1x fg-green\'></span>"'+
	        'data-caption="'+user.userName+'" id="user'+user.userId+'" '+
	        'onclick="chechedUser(this)" data-content="<span class=\'text-muted\'>'+user.dept+
	        '</span>"></li></div>';
	return html;
}

//添加课程多对应的日程
function openAddSch(){
	$('#addLessSchLiId').show();
	document.getElementById('addLessTarget').style.display="none";
	document.getElementById('addlessSchTarget').style.display="block";
	$("#lessUiId li[class='active']").removeClass("active");
	$('#addLessSchLiId').addClass("active");
	var lessname = $('#requestSchId').attr('lessname');
	$('#schLessName').val(lessname);
}
//课程所对应的日程列表
function showSchTableInit(e){
	$('#lessSchId').hide();	
	$('#showSchListLiId').show();
	$('#sch_less_name').html(e.attributes.lessonName.value);
	$('#sch_less_teacher').html(e.attributes.teacherName.value+'  '+e.attributes.teacherPhone.value+'');
	document.getElementById('lessManageTarget').style.display="none";
	document.getElementById('schListTarget').style.display="block";
	$("#lessUiId li[class='active']").removeClass("active");
	$('#showSchListLiId').addClass("active");
	var id = e.attributes.lessonid.value;
	loadSchData(id);
}

//课程对应的资料下载
function downLoadLessFile(id){
	var d = dialog({
		title: '课程资料列表',
		id: ''+id+'_Dialog',
		width:240,
		align: 'bottom left',
		quickClose: true,// 点击空白处快速关闭
		content: $('#downLoadFileList')
	});
	d.show(document.getElementById(id));
}