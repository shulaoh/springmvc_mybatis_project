//添加学员信息
function userRegister(e){
	var json = {};
	$("#addUserTarget input[type='text']").each(function(){
		var val = $(this)[0].value;
		if(val.length > 0){
			json[$(this)[0].name] = val;
		}
	});
	json.adminFlag = $("#addUserTarget input[type='radio']:checked").val();
	json.sign = $('#userIntroId').val();
	json.ustatus = 'REGI';
	json.password = $('#userPassword').val();
	//alert(JSON.stringify(json));
	$.ajax({
		url: "userRegist.do",
        type: 'get',
        async: true,
        data: json,
        dataType: "json",
        success: function (msg,success,e) {
	       	 if(e.status == "200") {
	       		if(msg.result.retcode == 1){
	       			$("#errorLogId").css("color","#00d600");
	       			$('#errorLogId').html('添加用户信息成功');
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