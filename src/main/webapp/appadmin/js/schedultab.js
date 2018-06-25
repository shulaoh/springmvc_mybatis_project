$(function(){
        getCurrentUserInfo();

        var lesssonid = getUrlParam("id");
        var currPage;
        var totalPage;
		
		if(lesssonid==null){
			return;
		}
        $("#addScheA").attr("href","./editSchedule.html?lesson="+lesssonid+"&id=-1");

        //显示课程相关信息
        $.ajax({
            url: ServerRootName+"/getLessonById.do",
            data:{
                service_version:1,
                tid:'tid_get_all_lessons',
                lessonId:lesssonid
            },
            type: 'post',
            dataType: 'json',
            success: function (result) {
                if (result.result.retcode > 0) {
                    $("#lessonInfo").text(result.data.lesson.lessonName);
                    $("#teacherInfo").text(result.data.lesson.teacher.name+" "+ result.data.lesson.teacher.phone);
                }
            }
        });


        //获得此课程的全部日程(分页)
        function getSchedules(pageNum){
            $.ajax({
                url: ServerRootName+"/getSchedule.do",
                data:{
                    service_version:1,
                    tid:'tid_get_schedule',
                    lessonId:lesssonid,
                    showCount:10,
                    pageNum:pageNum
                },
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.result.retcode > 0) {
						
                        $("#tbody").append("");

                        var trBeginHtml ="<tr>";
                        var trEndHtml ="</tr>";
                        var thBeginHtml ="<th scope='row'>";
                        var thEndHtml ="</th>";
                        var tdBeginHtml = "<td>";
                        var tdEndHtml = "</td>";


                        $("teacherInfo")
                        currPage = result.datadesc.page.currentPage;
                        totalPage = result.datadesc.page.totalPage;

                        $.each(result.data.schedules,function(i,item){
                            if(i%2 == 0){
                                trBeginHtml = "<tr class='active'>";
                            }else{
                                trBeginHtml = "<tr>";
                            }

                            var schName = item.schName ;
                            var ssTime = item.ssTime ;
							var lastValue=parseInt(item.schLastMNum);
                            var schLastMNum = lastValue*15;
                            var schPlace = item.schPlace;
                            var schId = item.schId;

                            var trHtml = trBeginHtml +thBeginHtml+ schName + thEndHtml + tdBeginHtml + ssTime +tdEndHtml + tdBeginHtml + schLastMNum +tdEndHtml + tdBeginHtml + schPlace +tdEndHtml+tdBeginHtml + jointHtml(schId,lesssonid) +tdEndHtml + trEndHtml;

                            $("#tbody").append(trHtml);
                        });
                    } else {
                        bootbox.alert(result.result.retmsg, function () {})
                    }
                },
                error: function () {
                    bootbox.alert("系统错误，请联系管理员", function () {})
                }
            });
        }

        getSchedules(1);


        pageUtil(getSchedules,currPage,totalPage);


    });