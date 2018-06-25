var table_language = {
	    "sLengthMenu": "每页显示 _MENU_ 条记录", 
	    "sZeroRecords": "对不起，查询不到任何相关数据", 
	    "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录", 
	    "sInfoEmpty": "找不到相关数据", 
	    "sInfoFiltered": "", //数据表中共为 _MAX_ 条记录  去掉提示信息，影响页码的宽度
	    "sProcessing": "正在加载中...", 
	    "sSearch": "搜索", 
	    "sUrl": "", 
	    "oPaginate": { 
	        "sFirst": "", 
	        "sPrevious": "上页 ", 
	        "sNext": "下页 ", 
	        "sLast": ""
	    } 
	};

//打开添加课程
function openAddLess(){
	$('#addLessTitleName').html("添加课程");
	controlLessShow();
	ChooseUserList();
	$('#addLessTarget form').attr('status','add');
	clearLessForm();
}

function controlLessShow(){
	$('#addLessLiId').show();
	$("#lessUiId li[class='active']").removeClass("active");
	$('#addLessLiId').addClass("active");
	$('#lessManageTarget').hide();
	$('#addLessTarget').show();
}

function openLessSch(e){
	$('#showSchListLiId').show();
	$("#lessUiId li[class='active']").removeClass("active");
	$('#showSchListLiId').addClass("active");
	$('#lessManageTarget').hide();
	$('#schListTarget').show();
	showSchTableInit(e);
}
//关闭日程列表窗口
function closeLessSch(){
	$('#lessListLiId').show();
	$("#lessUiId li[class='active']").removeClass("active");
	$('#lessListLiId').addClass("active");
	$('#schListTarget').hide();
	$('#showSchListLiId').hide();
	$('#lessManageTarget').show();
}

//点击提交后返回课程列表  并刷新列表
function closeAddLess(){
	$('#lessListLiId').show();
	$("#lessUiId li[class='active']").removeClass("active");
	$('#lessListLiId').addClass("active");
	$('#addLessTarget').hide();
	$('#addLessLiId').hide();
	$('#lessManageTarget').show();
	getMyLessList();
}

$(function(){
	// 初始化插件
	/*$("#uploadFile").zyUpload({
		width            :   "480px",                 // 宽度
		height           :   "260px",                 // 宽度
		itemWidth        :   "60px",                 // 文件项的宽度
		itemHeight       :   "100px",                 // 文件项的高度
		url              :   "/upload/UploadAction",  // 上传文件的路径
		multiple         :   true,                    // 是否可以多个文件上传
		dragDrop         :   true,                    // 是否可以拖动上传文件
		del              :   true,                    // 是否可以删除文件
		finishDel        :   false,  				  // 是否在上传文件完成后删除预览
		 外部获得的回调接口 
		onSelect: function(files, allFiles){                    // 选择文件的回调方法
			console.info("当前选择了以下文件：");
			console.info(files);
			console.info("之前没上传的文件：");
			console.info(allFiles);
		},
		onDelete: function(file, surplusFiles){                     // 删除一个文件的回调方法
			console.info("当前删除了此文件：");
			console.info(file);
			console.info("当前剩余的文件：");
			console.info(surplusFiles);
		},
		onSuccess: function(file){                    // 文件上传成功的回调方法
			console.info("此文件上传成功：");
			console.info(file);
		},
		onFailure: function(file){                    // 文件上传失败的回调方法
			console.info("此文件上传失败：");
			console.info(file);
		},
		onComplete: function(responseInfo){           // 上传完成的回调方法
			console.info("文件上传完成");
			console.info(responseInfo);
		}
	});*/
	$("#lessStimeL").jeDate({
        format: "YYYY-MM-DD hh:mm"//,
        //minDate:new Date().Format('yyyy-MM-dd hh:mm:ss'),
    });
	$("#lessEtimeL").jeDate({
        format: "YYYY-MM-DD hh:mm"//,
        //minDate: new Date().Format('yyyy-MM-dd hh:mm:ss')
    });
	$("#lessSch1Date").jeDate({
        format: "YYYY-MM-DD hh:mm"//,
        //minDate: new Date().Format('yyyy-MM-dd hh:mm:ss')
    });
});
function createJeDate(inputId){
	var je = {
		format: 'YYYY-MM-DD hh:mm',
		//minDate: new Date().Format('yyyy-MM-dd hh:mm:ss'), //设定最小日期为当前日期
		festival:false,
		okfun:function(elem,val){
			//lessEtime.maxDate = val;
		}
		//maxDate: $.nowDate(0) //最大日期
	}
	$("#"+inputId+"").jeDate(je);
}
/**
 * 提示信息
 * info为 信息|标题|类型
 * type为 success 绿, info 蓝, warning 橙, alert 红
 * */
function showAlert(notifyString){
	var notifyInfos = notifyString.split('|');
	if(notifyInfos.length<1)return;
	var notifyCaption = "提示";
	if(notifyInfos.length>1)notifyCaption = notifyInfos[1];
	var notifyType = "info";
	if(notifyInfos.length>2)notifyType = notifyInfos[2];
	Metro.notify.create(notifyInfos[0],notifyCaption,{
        shadow:true,
        timeout:5000,
        type: notifyType
    });
}