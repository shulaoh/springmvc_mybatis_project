
var ServerRootName="https://lpg.tianmengit.com/cnooc_training";


//跳转页面
function toHtml(htmlName,id){
	window.location.href="./"+htmlName+".html?id="+id;
}
function toScheuleTab(htmlName,lesson,id){
	window.location.href="./"+htmlName+".html?lesson="+lesson+"&id="+id;
}

//Format String by special form
function StringFormat(form, arguments) {
	
    if (arguments.length == 0 || form.length==0)
        return null;
    var str = form;
	
	
    for ( var i = 0; i < arguments.length; i++) {
		
        var re = new RegExp('\\{' + (i) + '\\}', 'gm');
        str = str.replace(re, arguments[i]);
		
    }
    return str;
}
//拼接HTML代码	
function jointHtml(id,operation){
	if(operation == 'lesson'){
		return '<span class="glyphicon glyphicon-edit" aria-hidden="true" onclick="toHtml(\'saveOrEditLesson\','+id+');">修改</span> &nbsp;&nbsp;&nbsp; <span class="glyphicon glyphicon-th-list" aria-hidden="true" onclick="toHtml(\'scheduleTab\','+id+');">日程信息</span> ';
	}else{
		return '<span class="glyphicon glyphicon-edit" aria-hidden="true" onclick="toScheuleTab(\'editSchedule\',\''+operation+'\',\''+id+'\');">修改</span>';
	}
}

//获取URL参数
function getUrlParam(name){
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
    var r = window.location.search.substr(1).match(reg); 
    if (r != null) return unescape(r[2]); return null; 
}

function submitForm() {
    $("form").submit();
}
  

//格式化时间  
var formatDateTime = function (date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	m = m < 10 ? ('0' + m) : m;
	var d = date.getDate();
	d = d < 10 ? ('0' + d) : d;
	var h = date.getHours();
	h=h < 10 ? ('0' + h) : h;
	var minute = date.getMinutes();
	minute = minute < 10 ? ('0' + minute) : minute;
	var second=date.getSeconds();
	second=second < 10 ? ('0' + second) : second;
	return y + '-' + m + '-' + d+' '+h+':'+minute;//+':'+second;
};

       

//获取当前用户信息
function getCurrentUserInfo(){
    $.ajax({
        url: ServerRootName+"/getMyUserInfo.do",
        data:{
            service_version:1,
            tid:'tid_get_my_user_info'
        },
        type: 'post',
        dataType: 'json',
        success: function (result) {
            if (result.result.retcode > 0) {
                var userName = result.data.userInfo.userName;
                var adminFlag = result.data.userInfo.adminFlag;
                switch (adminFlag){
                    case 0 :
                        $("#userInfo").text(userName+"(学员)");
                        break;
                    case 10:
                        $("#userInfo").text(userName+"(班主任)");
                        break;
                    case 20:
                        $("#userInfo").text(userName+"(二级管理员)");
                        break;
                    default:
                        $("#userInfo").text(userName+"(一级管理员)");
                }

            } else {
                bootbox.alert(result.result.retmsg, function () {});
            }
        },
        error: function () {
            bootbox.alert("系统错误，请联系管理员", function () {});
        }
    });
}

//分页组件
function pageUtil(functionName,currPage,totalPage){
    var element = $('#bp-element');
    options = {
        bootstrapMajorVersion:3, //对应的bootstrap版本
        currentPage: currPage, //当前页数，这里是用的EL表达式，获取从后台传过来的值
        numberOfPages: 10, //每页页数
        totalPages:totalPage, //总页数，这里是用的EL表达式，获取从后台传过来的值
        shouldShowPage:true,//是否显示该按钮
        itemTexts: function (type, page, current) {//设置显示的样式，默认是箭头
            switch (type) {
                case "first":
                    return "首页";
                case "prev":
                    return "上一页";
                case "next":
                    return "下一页";
                case "last":
                    return "末页";
                case "page":
                    return page;
            }
        },
        //点击事件
        onPageClicked: function (event, originalEvent, type, page) {
            console.log('page',page)
            functionName(page);
        }
    };
    element.bootstrapPaginator(options);
}



