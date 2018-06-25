//动态添加日程
var addCount = 0;
var tempCount = 0;
var signArray = new Array();
$(function(){
	$('#lessSchId input[name=signFlag]').change(function(){
		var signFlag = $('#lessSchId input[name=signFlag]:checked').val();
		for(var i=0;i<signArray.length;i++){
			if(signFlag == 'N'){
				$('#'+signArray[i]+'').hide();
			}else{
				$('#'+signArray[i]+'').show();
			}
		}
	});
	addLessSch();
});
function addLessSch(){
	var signFlag = $('#lessSchId input[name=signFlag]:checked').val();
	addCount++;
	tempCount++;
	var autoId = "lessSch"+tempCount;
	var signschId = "signsch"+tempCount;
	$('#lessSchId').append('<div class="row" style="width:100%;" id="'+autoId+'"><label class="cell-2">'+
			'<span class="place-right" style="line-height: 36px;">开始时间</span></label>'+
			'<div class="cell-3"><input type="text" data-role="input" class="jeinput" placeholder="开始时间" id="ssTime"></div>'+
			'<div class="cell-5"><input type="text" data-role="input" id="schPlace" placeholder="详细地点"></div>'+
			/*'<div class="cell-sm-2">'+
				'<img src="image/con1-eubg1.png" title="添加日程" style="height:28px;width:28px;margin:4px;" onclick="addLessSch()">'+
				'<img src="image/con1-eubg1.png" title="删除日程" style="height:28px;width:28px;margin:4px;" onclick="delLessSch('+autoId+','+signschId+')">'+
			'</div>'+*/
			'</div>');
	if(signFlag == 'Y'){
		$('#lessSchId').append('<div class="row" style="width:100%;" id="signsch1">'+
				'<label class="cell-2"><span class="place-right" style="line-height: 36px;">签到时间</span></label>'+
				'<div class="cell-3">'+
					'<input type="text" data-role="input" placeholder="开始时间" id="signSTime" class="jeinput">'+
				'</div><div class="cell-3">'+
					'<input type="text" data-role="input" placeholder="结束时间" id="signETime" class="jeinput">'+
				'</div></div>');
		$('#lessSchId').append('<div class="cell-7">'+
		'<button class="button success place-right" id="update_Less_Sch" style="width:8rem" onclick="requestLessSch(this)">提交信息</button>'+
		'</div>');
		createJeDate('signSTime');
		createJeDate('signETime');
		signArray.push(signschId);
	}
	lessBodyId.scrollTop=lessBodyId.scrollHeight;
	createJeDate('ssTime');
}
function delLessSch(less,sign){
	if(sign){
		sign.remove();
	}
	addCount--;
	less.remove();
}

function requestLessSch(e){
	var signFlag = $('#lessSchId input[name=signFlag]:checked').val();
	var param = {};
	param.lessonId = $('#update_Less_Sch').attr('lessonId');
	
	param.schName = $('#schName').val();
	if(param.schName==null || !param.schName.length>0)
	{
		$('#errorSchId').html('日程名称不能为空');
	}
	param.schIntro = $('#schIntro').val();
	param.ssTime = $('#ssTime').val();
	param.schLastMNum = $('#lessSchId input[name=schLastMNum]:checked').val();
	param.signFlag = signFlag;
	if(signFlag == 'Y'){
		param.signSTime = $('#signSTime').val();
		param.signETime = $('#signETime').val();
	}else{
		param.signSTime = '1970-01-01 00:00';
		param.signETime = '1970-01-01 00:00';
	}
	param.schPlace = $('#schPlace').val();
	param.commentFlag = $('#lessSchId input[name=commentFlag]:checked').val();
	param.allCommFlag = $('#lessSchId input[name=allCommFlag]:checked').val();
	var addFlag= $('#update_Less_Sch').attr('addFlag');
	if(addFlag==0)
	{
	$.ajax({
		url: "addSch.do",
        type: 'get',
        async: true,
        data: param,
        dataType: "json",
        success: function (msg,success,e) {
	       	 if(e.status == "200") {
	       		if(msg.result.retcode == 1){
	       			$("#errorSchId").css("color","#00d600");
	       			$('#errorSchId').html('信息添加成功');
		       			hideSchDetail();
		       			loadSchData(param.lessonId);
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
	else
	{
		param.schId = $('#update_Less_Sch').attr('schId');
		$.ajax({
			url: "updateSchedule.do",
	        type: 'get',
	        async: true,
	        data: param,
	        dataType: "json",
	        success: function (msg,success,e) {
		       	 if(e.status == "200") {
		       		if(msg.result.retcode == 1){
		       			$("#errorSchId").css("color","#00d600");
		       			$('#errorSchId').html('信息修改成功');
		       			hideSchDetail();
		       			loadSchData(param.lessonId);
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
	
}


var schList = new Object();
function loadSchData(lessonId)
{
	$('#add_Less_Sch').attr('lessonId',lessonId);
	var param = {};
	param.lessonId = lessonId;
	param.showCount = 100000;
	param.pageNum = 1;
	$.ajax({
		url: "getSchedule.do",
        type: 'get',
        async: true,
        data: param,
        dataType: "json",
        success: function (msg,success,e) {
	       	 if(e.status == "200") {
	       		if(msg.result.retcode == 1){
	       			schList = msg.data.schedules;
	       			
	       			creatSchList();
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

function creatSchList()
{
	for(var i=0;i < schList.length;i++){
		schList[i].schLastMNum = parseInt(schList[i].schLastMNum)*15;
		schList[i].operate='<span style="margin-right:.5rem" class="mif-insert-template mif-1x fg-blue" title="修改日程信息" onclick="updateSch(\''+schList[i].lessonId+'\',\''+schList[i].schId+'\');"></span>';
	}
	$('#schListTable').dataTable().fnClearTable();//清空表格数据
	$('#schListTable').dataTable().fnAddData(schList);//给表格添加数据
}

function showsingFlag(tempSignFlag)
{
	if(tempSignFlag== 'N')
	{
		$('#signsch1').hide();
	}
	else
	{
		$('#signsch1').show();
	}
}

function updateSch(lessonId,schId)
{
	for(var i=0;i < schList.length;i++){
		if(schList[i].lessonId==lessonId && schList[i].schId==schId)
		{
			$('#schName').val(schList[i].schName);
			$(":radio[name='schLastMNum'][value='"+parseInt(schList[i].schLastMNum/15)+"']").prop("checked", "checked");
			$(":radio[name='signFlag'][value='"+schList[i].signFlag+"']").prop("checked", "checked");
			$(":radio[name='commentFlag'][value='"+schList[i].commentFlag+"']").prop("checked", "checked");
			$(":radio[name='allCommFlag'][value='"+schList[i].allCommFlag+"']").prop("checked", "checked");
			showsingFlag(schList[i].signFlag);
			$('#ssTime').val(schList[i].ssTime);
			$('#schPlace').val(schList[i].schPlace);
			$('#signSTime').val(schList[i].signSTime);
			$('#signETime').val(schList[i].signETime);
			$('#update_Less_Sch').attr("lessonId",lessonId);
			$('#update_Less_Sch').attr("schId",schId);
			$('#update_Less_Sch').attr("addFlag",1);
			$('#lessSchId').show();
			break;
		}
	}
}

function hideSchDetail()
{
	clearSchForm();
	$('#lessSchId').hide();
}

function clearSchForm()
{
	var signFlag = $('#lessSchId input[name=signFlag]:checked').val();
	$('#schName').val("");
	if(signFlag == 'Y'){
		$('#signSTime').val("");
		$('#signETime').val("");
	}
	$('#ssTime').val("");
	$('#schPlace').val("");
	$('#schIntro').val("");
	
	$('#update_Less_Sch').attr("lessonId",'');
	$('#update_Less_Sch').attr("schId",'');
	$('#update_Less_Sch').attr("addFlag",0);
}

function open_Add_Sch()
{
	clearSchForm();
	var lessonId=$('#add_Less_Sch').attr('lessonId');
	$('#lessSchId').show();
	$('#update_Less_Sch').attr('lessonId',lessonId);
	$('#update_Less_Sch').attr("addFlag",0);
}