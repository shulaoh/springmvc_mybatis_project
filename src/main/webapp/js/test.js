function manualMethod(){
	$('#requestId').hide();
	$('#buttonId').hide();
}

function methodSend(){
	var url = $('#methodName').val() + ".do";
	var param = $('#methodTextarea').val();
	$.ajax({
		url: url,
        type: 'get',
        async: true,
        data: JSON.parse(param),
        dataType: "json",
        success: function (msg,success,e) {
	       	 if(e.status == "200") {
	       		 $('#methodResult').html(JSON.stringify(msg))
	       	 }
       },
       error: function(XMLHttpRequest,textStatus,errorThrown){
			 alert(XMLHttpRequest.status);
			 alert(XMLHttpRequest.readyState);
			 alert(textStatus);
	   }
	});
}


function testConmit(){
	//取得当前显示form对应的div的ID;
	var id = $("li[class='active']")[0].childNodes[0].attributes[0].value;
	//取得div下面所有的input
	var json = {};
	$(""+id+" input[type='text']").each(function(){
		var val = $(this)[0].value;
		if(val.length > 0){
			json[$(this)[0].name] = val;
		}
	});
	$("#requestParam").val(JSON.stringify(json));
}
$(function(){
	$("li").click(function(e){
		$("#requestParam").val("");
		$("#resultJson").val("");
		var id = e.target.attributes[0].value;
		if(id != '#_target_23'){
			$('#requestId').show();
			$('#buttonId').show();
		}
	})
});
function sendRequest(){
	$("#resultJson").val("");
	var id = $("li[class='active']")[0].childNodes[0].attributes[0].value;
	var name = $(''+id+' form')[0].name;
	var url = ""+name+".do";
	var parem = $('#requestParam').val();
	$.ajax({
		url: url,
        type: 'get',
        async: true,
        data: JSON.parse(parem),
        dataType: "json",
        success: function (msg,success,e) {
	       	 if(e.status == "200") {
	       		 $('#resultJson').html(JSON.stringify(msg))
	       	 }
       },
       error: function(XMLHttpRequest,textStatus,errorThrown){
			 alert(XMLHttpRequest.status);
			 alert(XMLHttpRequest.readyState);
			 alert(textStatus);
	   }
	});
}