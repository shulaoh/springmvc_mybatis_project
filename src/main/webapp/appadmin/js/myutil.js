//课程相关

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
        $(preAddObj).text("移除").attr("onclick","preDelete(this,"+id+","+type+");");
        chooseTeacTrs = $("#chooseUserTable").html();
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


var chooseUsers=[];

function addStuOrTeac(type) {
    if (type == 0) {
        //获取全部学员信息
        chooseUsers  = chooseStuID;
        $("#chooseUserTable").html(chooseStuTrs);
        
    } else {
        //获取全部讲师信息
        chooseUsers=[];
        chooseUsers.push(chooseTeaID);
        $("#chooseUserTable").html(chooseTeacTrs);
        console.log('teacher',chooseUsers)
    }

    getStudentOrTeacher(1, type);

    userType = type;

    pageUtil(getStudentOrTeacher,currPage,totalPage);


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
//获得此课程的信息
function getLessonInfo(){
    $.ajax({
        url: "https://lpg.tianmengit.com/cnooc_training/getLessonById.do",
        data:{
            service_version:1,
            tid:'tid_get_all_lessons',
            lessonId:id
        },
        type: 'post',
        dataType: 'json',
        success: function (result) {
            console.log('course',result);
            if (result.result.retcode > 0) {
                getLessonStu();
                
                //展示当前课程信息
                var item = result.data.lesson
                $("#lessonName").val(item.lessonName);
                $("#place").val(item.place);
                $("#lessonInfo").val(item.lessonInfo);
                if(item.lessonType == 'L'){
                    $("#radio1").prop("checked",true);
                }else {
                    $("#radio2").prop("checked",true);
                }

                $("#lessPicDiv").removeAttr("style");
                $("#choosePicDiv").removeClass().addClass("col-sm-5");
                //$("#lessPicUrl").val(item.lessPicUrl);
                var arr = item.lessPicUrl.split('/');
                console.log(arr[arr.length-1]);
                $("#lessPicUrl").val('https://lpg.tianmengit.com/cnooc_training/image/PIC_DEFAULT_LESS_PEIXUN.PNG');
                $("#lessPic").attr("src",item.lessPicUrl);
                
                $("#lessonId").val(id);
                $("#tid").val('tid_update_lesson');

                //存储当前课程老师信息
                chooseTeaID = item.teacherId;
                chooseTeacTrs = "<tr id='addTr"+item.teacherId+"'><td >"+item.teacher.name+"</td><td>"+item.teacher.dept+"</td><td>"+item.teacher.email+"</td><td><a class='btn green' data-id='"+item.teacherId+"' onclick='preDelete(this,10);'>移除</a></td></tr>";
            } else {
                //获得要修改的课程信息的错误提示信息
                bootbox.alert(result.result.retmsg, function () {});
            }
        },
        error: function () {
            bootbox.alert("系统错误，请联系管理员", function () {});
        }
    });
}

//获得此课程的学员信息
function getLessonStu(){
    $.ajax({
        url: "https://lpg.tianmengit.com/cnooc_training/getLessonStu.do",
        data: {
            service_version: 1,
            tid: 'tid_get_lesson_stu',
            lessonId: id,
            stuStatus:'ALL',
            showCount:300,
            pageNum:1
        },
        type: 'post',
        dataType: 'json',
        success: function (result1) {
            console.log('student',result1);
            if (result1.result.retcode > 0) {
                $.each(result1.data.lessonStus, function (k,lessStu) {
                    //学员ID存入数组
                    chooseStuID.push(lessStu.userId);
                    // chooseStuInputs.push(lessStu.userId);

                    var stuTr =  "<tr id='addTr"+lessStu.userId+"'><td >"+lessStu.name+"</td><td>"+lessStu.dept+"</td><td>"+lessStu.email+'</td><td><a class="btn green" data-id="'+lessStu.userId+'" onclick="preDelete(this,0);">移除</a></td></tr>';
                    chooseStuTrs.push(stuTr);
                })
            }else {
                //获得此课程的学员信息错误提示信息
                bootbox.alert(result1.result.retmsg, function () {});
            }
        },
        error: function () {
            bootbox.alert("系统错误，请联系管理员", function () {});
        }
    });
}
//图片上传初始化

 
function initFileInput() {      
var control = $("#img_file");   
control.fileinput({  
    language: 'zh', //设置语言  
    uploadUrl: "https://lpg.tianmengit.com/cnooc_training/upload.do",  //上传地址
    showUpload: false, //是否显示上传按钮  
    showRemove: false, 
    showCaption:false, 
    dropZoneEnabled: false,
    autoReplace: true,  
    showCaption: true,//是否显示标题
    allowedPreviewTypes: ['image'],  
    allowedFileTypes: ['image'],  
    allowedFileExtensions:  ['jpg', 'gif', 'png','jpeg'], 
    maxFileCount: 1,
      
}).on("filebatchselected", function(event, files) {  
    $(this).fileinput("upload"); 
    
    })  
    .on("fileuploaded", function(event, data) {  
        //$("#path").attr("value",data.response); 
        $("#lessPicUrl").val(data.response);
        $("#lessCycPicUrl").val(data.response);
        console.log(data.response)
    })
    .on("filecleared",function(event, data, msg){
        $("#lessPicUrl").val();
        console.log('clear');
    });
}  

$(function () {  
var path="${base}/admin/product/upload.htm";  
initFileInput("file",path);  
  
})  


