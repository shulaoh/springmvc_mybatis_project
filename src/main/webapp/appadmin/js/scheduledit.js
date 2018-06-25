

var optionForGeneral="<option id=\"{0}\">{1}</option>";
var optionForSelected="<option id=\"{0}\" selected=\"selected\">{1}</option>";
//讲师信息
var currPage;
var totalPage;
var chooseTeaID;  //已选教师ID
var chooseTeaName;  //已选教师ID
var chooseTeacTrs=[];  //已选教师HTML

	/*function getAllSchedules(number){
		
			var content=null;
            $.ajax({
				async : false,
                url: ServerRootName+"/getSchedule.do",
                data:{
                    service_version:1,
                    tid:'tid_get_schedule',
                    lessonId:"1",
                    showCount:10,
                    pageNum:1
                },
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.result.retcode > 0) {
					content=result;

                    } else {
                        bootbox.alert(result.result.retmsg, function () {})
                    }
                },
                error: function () {
                    bootbox.alert("系统错误，请联系管理员", function () {})
                }
            });
			
			return content;
        }*/
		
	function getOneSchedule(shdid){
		var shContent=null;
		$.ajax({
					async : false,
					url: ServerRootName+"/getSingleScheduleByScheduleId.do",
					data:{
						service_version:1,
						tid:'tid_get_ get_single_schedule_by_ scheduleId',
						scheduleId:shdid
					},
					type: 'post',
					dataType: 'json',
					success: function (result) {
						if (result.result.retcode > 0) {
							shContent=result;
						} else {
							bootbox.alert(result.data.retmsg, function () {})
						}
					},
					error: function () {
						bootbox.alert("系统错误，请联系管理员", function () {})
					}
				});
				
		return shContent;
	}
		

    function submitForm() {
        $("form").submit();
    }

    $(function () {
		
		var lid = getUrlParam("lesson");
		var sid=getUrlParam("id");
        getCurrentUserInfo();

        //获取日程信息(后台接口确定后修改相应参数，url，tid均需要更改，其它视具体情况而定)
			
		var item=null;
		if(sid!=null && sid!="-1"){
			var result=getOneSchedule(sid);//getAllSchedules();
			
			if(result!=null&&result.data!=null && result.data.schedule!=null){
				item=result.data.schedule;
			}
		}
		console.log('item',item);
			
		if(item!=null){
			//get exist schedule info
			
			$("#schId").val(item.schId);
			$("#schName").val(item.schName);
			$("#lessonId").val(item.lessonId);
			
			$("#tutorName").val(item.tutor.name);
			$("#tutorId").val(item.tutorId);
			//存储当前课程老师信息
                chooseTeaID = item.tutorId;
                chooseTeacTrs = "<tr id='addTr"+item.tutorId+"'><td >"+item.tutor.name+"</td><td>"+item.tutor.dept+"</td><td>"+item.tutor.email+"</td><td></td></tr>";
			
			$("#ssTime").val(item.ssTime);
			

			switch (item.schLastMNum){
				case "1":
					$("#radio1").prop("checked",true);
					break;
				case "2":
					$("#radio2").prop("checked",true);
					break;
				case "3":
					$("#radio3").prop("checked",true);
					break;
				case "4":
					$("#radio4").prop("checked",true);
					break;
				case "8":
					$("#radio5").prop("checked",true);
					break;
				case "12":
					$("#radio6").prop("checked",true);
					break;
				case "16":
					$("#radio7").prop("checked",true);
					break;
				case "32":
					$("#radio8").prop("checked",true);
					break;
				default:
					$("#radio9").prop("checked",true);
					$("#customInput").attr("style","");
					$("#customTime").val(item.schLastMNum);
			}

			if(item.signFlag == 'Y'){
				$("#signFlag1").prop("checked",true);
			}else {
				$("#signFlag2").prop("checked",true);
				$("#signTimeDiv").attr("style","display:none;");
			}

			if(item.commentFlag == 'Y'){
				$("#commentFlag1").prop("checked",true);
			}else {
				$("#commentFlag2").prop("checked",true);
			}

			if(item.allCommFlag == 'Y'){
				$("#allCommFlag1").prop("checked",true);
			}else {
				$("#allCommFlag2").prop("checked",true);
			}
			/*var commentTemplateId=item.commTemplIds;
			$.ajax({
				url: ServerRootName+"/getCommentTemplate.do",
				data:{
					service_version:1,
					tid:'tid_get_all_lessons',
					templatename:''
				},
				type: 'post',
				dataType: 'json',
				success: function (result) {
				if (result.result.retcode > 0 && ) {
						var optionHtml;
						var optionFormat;
						$.each(result.data,function(i,it){
								
								if(it[i].TEMP_ID!=null && it[i].TEMP_NAME){
									var optionArray=new Aarray();
									
									optionArray[0]=it[i].TEMP_ID;
									optionArray[1]=it[i].TEMP_NAME;
									
									if(it[i].TEMP_ID==commentTemplateId){
										optionFormat=optionForSelected;
									}else{
										optionFormat=optionForGeneral;
									}
									optionHtml=StringFormat(optionFormat,optionArray);
									
									$("#commt_selector").append(optionHtml);
								}
						
						});
				
						
					} else {
						bootbox.alert(result.data.retmsg, function () {})
					}
		
				},
				error: function () {
					bootbox.alert("系统错误，请联系管理员", function () {})
				}

			}
			);*/

			$("#schPlace").val(item.schPlace);
			$("#ssTime").val(item.ssTime);
			$("#schIntro").val(item.schIntro);
			$("#signSTime").val(item.signSTime);
			$("#signETime").val(item.signETime);
			
			if(item.signSTime!=null && item.signETime!=null){
				var signBeginTimeMillsec= new Date(item.signSTime.replace(/-/g,  "/")).getTime();
				var signEndTimeMillsec= new Date(item.signETime.replace(/-/g,  "/")).getTime();

				if(signEndTimeMillsec-signBeginTimeMillsec == 5*60*1000){
					$("#signTime1").prop("checked",true);
				}else if(signEndTimeMillsec-signBeginTimeMillsec == 15*60*1000){
					$("#signTime2").prop("checked",true);
				}else {
					$("#signTime3").prop("checked",true);
				}
			}
			
		}else{
			$("#lessonId").val(lid);
			$("#tid").val("tid_add_shc");
			
			$("#schId").val("-1");
			$("#schName").val("");
			$("#ssTime").val("");
			$("#radio1").prop("checked",true);
			
			//commemt template 'commt_selector' options 
			/*$("#commt_selector").empty();
			
			$.ajax({
				url: ServerRootName+"/getCommentTemplate.do",
				data:{
					service_version:1,
					tid:'tid_get_all_lessons',
					templatename:''
				},
				type: 'post',
				dataType: 'json',
				success: function (result) {
				if (result.result.retcode > 0 && ) {
						var optionHtml;
						
						$.each(result.data,function(i,item){
								
								if(item[i].TEMP_ID!=null && item[i].TEMP_NAME){
									var optionArray=new Aarray();
									
									optionArray[0]=item[i].TEMP_ID;
									optionArray[1]=item[i].TEMP_NAME;
									
									optionHtml=StringFormat(optionForGeneral,optionArray);
									
									$("#commt_selector").append(optionHtml);
								}
						
						});
				
						
					} else {
						bootbox.alert(result.data.retmsg, function () {})
					}
		
				},
				error: function () {
					bootbox.alert("系统错误，请联系管理员", function () {})
				}

			}
			);*/
			
			$("#signFlag1").prop("checked",true);
			$("#signTime1").prop("checked",true);
			$("#commentFlag1").prop("checked",true);
			$("#allCommFlag1").prop("checked",true);
			
			$("#schPlace").val("");
			$("#schIntro").val("");
			
			$("#submitSchdu").val("新 增");
			$("#Ztitle").text("添加日程");
		}

        $("input[name='schLastMNum']").click(function(){
            if($(this).val() == ''){
                $("#customInput").attr("style","padding-left: 0px;");
            }else {
                $("#customInput").attr("style","display:none;");
            }
        });

        $("input[name='signFlag']").click(function(){
            if($(this).val() == 'Y'){
                $("#signTimeDiv").attr("style","");
            }else {
                $("#signTimeDiv").attr("style","display:none;");
            }
        });


        $('form').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },

            fields: {
                schName: {
                    validators: {
                        notEmpty: {
                            message: '日程名称不能为空'
                        }
                    }
                },
                schPlace: {
                    validators: {
                        notEmpty: {
                            message: '地点不能为空'
                        }
                    }
                },
                schIntro: {
                    validators: {
                        notEmpty: {
                            message: '日程简介不能为空'
                        }
                    }
                },
                ssTime: {
                    validators: {
                        notEmpty: {
                            message: '开始时间不能为空'
                        }
                    }
                }
            }
        }).on('success.form.bv', function(e) {
            // Prevent form submission
            e.preventDefault();

            // Get the form instance
            var $form = $(e.target);

            // Get the BootstrapValidator instance
            var bv = $form.data('bootstrapValidator');


            var schTimes = $("input[name='schLastMNum']:checked");
            if($(schTimes).val() == ''){
				var inputValue=$("#customTime").val();
                if(inputValue == '' && isNaN(inputValue)){
                    bootbox.alert("请输入数字类型的日程时长！", function () {});
                    return;
                }
				var timeValue=parseInt(inputValue);
                timeValue=timeValue/15;
				if(timeValue<1){
					timeValue=1;
				}else if(timeValue>1000){
					timeValue=1000
				}
				
				$(schTimes).val(timeValue);
            }

            var schSignFlag = $("input[name='signFlag']:checked").val();
            var beginTime = $("input[name='ssTime']").val();
            var beginTimeMillsec= new Date(beginTime.replace(/-/g,  "/")).getTime();
            var signSTime;
            var signETime;

            if(schSignFlag == 'Y'){
                var schSignTime = $("input[name='signTime']:checked").val();
                var schBegin
                if(schSignTime == -5){
                    signSTime = beginTimeMillsec - 5*60*1000;
                    signETime = beginTime;
                }else if(schSignTime == -15){
                    signSTime = beginTimeMillsec - 15*60*1000;
                    signETime = beginTime;
                }else {
                    signSTime = beginTimeMillsec - 30*60*1000;
                    signETime = beginTimeMillsec + 30*60*1000;
                }
                var signSTimeStr = formatDateTime(new Date(signSTime));
                var signETimeStr = formatDateTime(new Date(signETime));

                $("#signSTime").val(signSTimeStr);
                $("#signETime").val(signETimeStr);
            }

            // Use Ajax to submit form data
			var targetUrl;
			var cgiID;
			if($("#schId").val()=="-1"){
				targetUrl="/addSch.do";
			}else{
				targetUrl="/updateSchedule.do";
			}
			var paramaters=$("form").serialize();
			
			//paramaters+="&dayTime="+$("input[name='schLastMNum']:checked").val();
			//"https://lpg.tianmengit.com/cnooc_training/updateSchedule.do";
            $.ajax({
                url: ServerRootName+targetUrl,
                data: paramaters,
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.result.retcode > 0) {
                        bootbox.alert("日程信息保存成功！", function () {});
                        window.location.href = ServerRootName+"/appadmin/scheduleTab.html?id="+lid;
                    } else {
                        bootbox.alert(result.result.retmsg, function () {})
                    }
                },
                error: function () {
                    bootbox.alert("系统错误，请联系管理员", function () {})
                }
            });

        });

    });

//增加讲师选择
var chooseUsers=[];
function chooseTut(obj){
	console.log(obj.id)
    //获取全部讲师信息
    chooseUsers=[];
    chooseUsers.push(chooseTeaID);
    $("#chooseUserTable").html(chooseTeacTrs);

    getStudentOrTeacher(1, 10);

    pageUtil(getStudentOrTeacher,currPage,totalPage);
	$('#chooseUser').modal('show')
}

function getStudentOrTeacher(pageNum, type) {
    var trBeginHtml = "<tr>";
    var trEndHtml = "</tr>";
    var tdBeginHtml = "<td>";
    var tdEndHtml = "</td>";

    var ustatus;

    if (type == 0) {
        //获取全部学员信息
        urole = "0";
        $("#modalTitle").text("添加学员");
        $("#chooseUserH4").text("已选择学员信息");
        $("#cancleBtn").attr("onclick","cancleAddUser("+type+")");

    } else {
        //获取全部讲师信息
        urole = "10";
        $("#modalTitle").text("添加管理员");
        $("#chooseUserH4").text("已选择管理员信息");
        $("#cancleBtn").attr("onclick","cancleAddUser("+type+")");
    }


    $.ajax({
        url: "https://lpg.tianmengit.com/cnooc_training/getUserListByRole.do",
        // url: "https://lpg.tianmengit.com/cnooc_training/getUserList.do",
        data: {
            service_version: 1,
            tid: "tid_get_userList_by_role",
            urole: urole,
            ustatus:'ALL',
            showCount: 10,
            pageNum: pageNum
        },
        type: 'post',
        dataType: 'json',
        success: function (result) {
            if (result.result.retcode > 0) {
                $("#tBody").html("");
                console.log('AllStu',result);
                currPage = result.datadesc.page.currentPage;
                totalPage = result.datadesc.page.totalPage;
                

                $.each(result.data.users, function (i, item) {
                    var userName = item.userName;
                    var dept = item.dept;
                    var email = item.email;
                    var userId = item.userId;
                    var isChoose = false;
                    $.each(chooseUsers, function (j,chooseUser) {

                        if(chooseUser == userId){
                            isChoose = true;
                        }
                        console.log(i,chooseUser,userId,isChoose)
                    })

                    var trHtml = "<tr id='preTr"+userId+"'><td>" + userName + '</td><td>' + dept + '</td><td>' + email + '</td><td>' + jointUserHtml(userId,type,isChoose) + '</td>';
                    $("#tBody").append(trHtml);
                });

            } else {
                //获取学生或讲师信息失败提示信息
                bootbox.alert(result.result.retmsg, function () {})
            }
        },
        error: function () {
            bootbox.alert("系统错误，请联系管理员", function () {})
        }
    });
}

function jointUserHtml(id,type,isChoose){
    var aStr =  '<a class="';
    //var inputStr = '<input type="hidden" class="chooseUserClass" value="'+id+'"></td>';
    if(isChoose){
        aStr += 'btn blue four" data-id="'+id+'">已添加</a>';
    }else {
        aStr+='btn green" data-id="'+id+'" onclick="preAdd(this,'+type+')">添加</a>';
    }
    return aStr;
}

//学员添加
function preAdd(obj,type){
    var id = $(obj).data('id');
    var preAddHtml = $("#preTr"+id).html();
    var preAddObj;
    if(type == 0){
    //学员
        $("#preTr"+id).find("a").text("已添加").attr("onclick","javascript:void(0);").removeClass().addClass("btn blue four");
        $("#chooseUserTable").append("<tr id='addTr"+id+"'>"+preAddHtml+"</tr>");
        chooseStuID.push(id);

        preAddObj = $("#chooseUserTable tr:last-child").find("a");
        $(preAddObj).text("移除").attr("onclick","preDelete(this,"+type+");");
        chooseStuTrs = $("#chooseUserTable").html();

    }
    else{
        $("#tBody").find("a").text("添加").attr("onclick","preAdd(this,"+type+")").removeClass().addClass("btn green");
        $("#preTr"+id).find("a").text("已添加").attr("onclick","javascript:void(0);").removeClass().addClass("btn blue four");
        $("#chooseUserTable").html("<tr>"+preAddHtml+"</tr>");
        chooseTeaID = id;
        preAddObj = $("#chooseUserTable tr:last-child").find("a");
        preAddObj.remove();
        // $(preAddObj).text("移除").attr("onclick","preDelete(this,"+id+","+type+");");
        chooseTeacTrs = $("#chooseUserTable").html();
        chooseTeaName = $("#chooseUserTable").find('td').html();
    }


}

//学员删除
function preDelete(obj,type){
    var id = $(obj).data('id');
    $(obj).parent().parent().remove();
    $("#preTr"+id).find("a").text("添加").attr("onclick","preAdd(this,"+type+")").removeClass().addClass("btn green");
    if(type == 0){
        chooseStuID.remove(id);
        chooseStuTrs = $("#chooseUserTable").html();
    }else{
        chooseTeaID='';
        chooseTeacTrs = $("#chooseUserTable").html();

    }
}

function cancleAddUser(type){
    if (type == 0) {
        //获取全部学员信息
        chooseStuInputs = '';
        chooseStuTrs = '';
    } else {
        //获取全部讲师信息
        chooseTeacInputs = '';
        chooseTeacTrs = '';
    }
    $("#chooseUserTable").html("");

}
//保存选择的讲师
function saveTut(obj){
	$("#tutorName").val(chooseTeaName);
	$("#tutorId").val(chooseTeaID);
}

