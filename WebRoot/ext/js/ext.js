

$(document).ready(function() {
	//排序信息
	var orderColumn = $("#orderColumn").val();
	var order = $("#order").val();
	$(".sort-column").each(function(){
		$(this).css("cursor","pointer");
		if ($(this).hasClass(orderColumn)){
			var orderBy = order.toUpperCase()=="DESC"?"down":"up";
			$(this).html($(this).html()+" <i class=\"fa fa-sort-"+orderBy+"\"></i>");
		}else{
			$(this).html($(this).html()+" <i class=\"fa fa-sort\"></i>");
		}
	});
	//排序列点击
	$(".sort-column").click(function(){
		var tempOrder = $(this).attr("class").split(" ");
		if (orderColumn == tempOrder[1]){
			$("#order").val(order.toUpperCase()=="DESC"?"ASC":"DESC");
			$("#orderColumn").val(tempOrder[1]);
		}else{
			$("#order").val("ASC");
			$("#orderColumn").val(tempOrder[1]);
		}
		sortOrSearch();
	});
	//全选
	$('input[class="allCheck"]').on('ifChecked', function(event){
		$('input').iCheck('check');
	});
	//取消
	$('input[class="allCheck"]').on('ifUnchecked', function(event){
		$('input').iCheck('uncheck');
	});
	//操作回执
	if(status.length>0){
		if('error' == status){
			layer.msg(message, {icon: 5,time: 3000});
		}else{
			layer.msg(message, {icon: 6,time: 3000});
		}
	}
	layer.config({
		  extend: 'extend/layer.ext.js',
		  shift: -1,
		});   
});
/**
 * 批量删除
 * @returns {Boolean}
 */
function fnDelete(){
	var selectIds = getSelectIds();
	if(selectIds.length<1){
		layer.msg('请选择要删除的记录', {icon: 0,time: 3000});
		return false;
	}
	layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
		$("#listForm").attr("action","update2del.do");
		$("#listForm").submit();
	});
}
/**
 * 导出文件
 * @param source 模板文件 
 * @param target 目标文件
 * @param type 类型
 */
/*function fnExport(source,target,type){
	var $form = $("#listForm");
	$form.attr("action","export.do?source="+source+"&target="+target+"&type="+type);
	$form.attr("targer","_blank");
	$form.submit();
}*/

/**
 * 确认框
 * @param mess 确认消息
 * @param href 链接
 * @param closed 回调函数
 * @returns {Boolean} 
 */
function confirmx(mess, href, closed){
	 layer.confirm(mess, {icon:3, title:'系统提示'}, function(index){
		if (typeof href == 'function') {
			href();
		}else{
			location = href;
		}
		layer.close(index);
	});
	return false;
}
/**
 * 列表页获取选中checkbox的值
 * @returns {String}
 */
function getTableSelectIds(){
	var ids = "";
	$("input[name='ids']:checkbox").each(function(i){ 
		if(true == $(this).is(':checked')){   
			if(0!=i) ids+=",";  
			ids+=$(this).val();   
		}
	});
	return ids;
}

/**
 * 分页相关
 * @param num 页码
 * @param pageSize 没有条数
 */
function page(num,pageSize){
	 $("#pageNumber").val(num);
	 $("#pageSize").val(pageSize);
	 sortOrSearch();
}
/**
 * 分页Or排序
 */
function sortOrSearch(){
	 $("#listForm").submit();
}

/**
 * 获取屏幕信息
 */
function getWinSize() {
	var winW, winH;
	if(window.innerHeight) {// all except IE
		winW = window.innerWidth;
		winH = window.innerHeight;
	} else if (document.documentElement && document.documentElement.clientHeight) {// IE 6 Strict Mode
		winW = document.documentElement.clientWidth;
		winH = document.documentElement.clientHeight;
	} else if (document.body) { // other
		winW = document.body.clientWidth;
		winH = document.body.clientHeight;
	}
	return {WinW:winW, WinH:winH}
} 

$(function(){
    //改变树组件容器的高度
	 $(".treeboxheight").height(($(window).height()-128));

});

/**
 * 设置树组件高度与jqgrid表格的高度随着窗口大小的改变而改变
 * 
 * 此事件在窗口大小改变时触发
 */
window.onresize = function doResize() {
	 var win = getWinSize();
	 $(".treeboxheight").height(($(window).height()-128));
	 if($("#listTable").length>0){
		 $("#listTable").jqGrid('setGridWidth', win.WinW-10).jqGrid('setGridHeight', win.WinH-260);
	 }
}
/*
 * 时间控件
 */
$('.dateISO').each(function(){
  laydate.render({
    elem: this,
    theme: 'molv',
    calendar: true,
    trigger: 'click'
  });
});
$('.datetimeISO').each(function(){
  laydate.render({
    elem: this,
    type: 'datetime',
    theme: 'molv',
    calendar: true,
    trigger: 'click'
  });
});
$('.timeISO').each(function(){
  laydate.render({
    elem: this,
    type: 'time',
    format: 'HH:mm',
    theme: 'molv',
    calendar: true,
    trigger: 'click'
  });
});

/**
 * 打开PDF 弹窗
 * @param uri 
 * @param tempPath
 * @param type
 */
function openFile(uri,tempPath,type){
	var url = uri+'?tempPath='+tempPath+'&type='+type;
	 layer.confirm("请选择打开方式", {icon:3, title:'系统提示',btn:['当前窗口打开','新窗口打开']
	 },function(index, layero){
		 var index2 = layer.open({
			  type: 2,
			  content: url,
			  area: ['893px', '600px'],
			  shadeClose: true,
			  maxmin: true,
			  fix: false, //不固定
			  resize: true
			});
			/*layer.full(index);*/
		 layer.close(index);
	 }, function(index){
		 window.open(url);
		 layer.close(index);
	 });
	
}



/**
*基本描述：
* 图片缩略,使用js的静态类实现
* by 西楼冷月 20080817 www.chinacms.org | www.xilou.net | qq : 39949376
*参数说明：
* @param im : 可以为image对象或img的id
*基本功能:
* Img(im).Resize(nWidth,nHeight)             : 按给定的宽和高进行智能缩小
* Img(im).ResizedByWH(nWidth,nHeight)   : 按给定的宽和高进行固定缩小(会出现图片变形情况)
* Img(im).ResizedByWidth(nWidth)             : 按给定的宽进行等比例缩小
* Img(im).ResizedByHeight(nHeight)           : 按给定的高进行等比例缩小
* Img(im).ResizedByPer(nWidthPer,nHeightPer) : 宽和高按百分比缩小
*使用例子:
* img id="demo" src="demo.gif" width="191" height="143" onload="Img(this).Resize(200,500);" /
* img id="demo" src="demo.gif" width="191" height="143" onload="Img('demo').ResizedByPer(200,500);" /
**/

function Img(im){
	var ImgCls =
	{
		Obj : null ,
		//按给定的宽和高进行智能缩小
		Resize : function (nWidth,nHeight){
			var w,h,p1,p2;
			//计算宽和高的比例
			p1 = nWidth / nHeight ;
			p2 = ImgCls.Obj.width / ImgCls.Obj.height ;
			
			w = 0 ; h = 0 ;
			if(p1>=p2){
				//按宽度来计算新图片的宽和高
				w = nWidth ;
				h = nWidth * ( 1 / p2 ) ;
			}else{
				//按高度来计算新图片的宽和高
				h = nHeight ;
				w = nHeight * p2 ;
			}
			ImgCls.Obj.width  = w ;
			ImgCls.Obj.height = h ;
		},
		//按给定的宽和高进行固定缩小(会出现图片变形情况)
		//http://bizhi.knowsky.com/
		ResizedByWH : function ( nWidth , nHeight )
		{
			ImgCls.Obj.width  = nWidth ;
			ImgCls.Obj.height = nHeight ;
		},
		
		//按给定的宽进行等比例缩小
		ResizedByWidth : function ( nWidth )
		{
			var p = nWidth / ImgCls.Obj.width ;
			ImgCls.Obj.width  = nWidth ;
			ImgCls.Obj.height = parseInt ( ImgCls.Obj.height * p ) ;
		},
		
		//按给定的高进行等比例缩小
		ResizedByHeight : function ( nHeight )
		{
			var p = nHeight / ImgCls.Obj.height ;
			ImgCls.Obj.height  = nHeight ;
			ImgCls.Obj.width = parseInt ( ImgCls.Obj.width * p ) ;
		},
		
		//宽和高按百分比缩小
		ResizedByPer : function ( nWidthPer,nHeightPer )
		{
			ImgCls.Obj.width  = parseInt(ImgCls.Obj.width * nWidthPer / 100) ;
			ImgCls.Obj.height = parseInt(ImgCls.Obj.height * nHeightPer / 100) ;
		}
	}
	ImgCls.Obj = ( im && typeof im == 'object' ) ? im : document.getElementById(im) ;
    return ImgCls;
}
/**
 * 
 * @param ImgD
 * @param width_s
 * @param height_s
 * 使用例子
 * <div><img src="图片" align="center" onload="DrawImage(this,200,200)"></div>
 */
function DrawImage(ImgD,width_s,height_s){
	var image=new Image();
	image.src=ImgD.src;
	if(image.width>0 && image.height>0){
		flag=true;
		if(image.width/image.height>=width_s/height_s){
			if(image.width>width_s){
				ImgD.width=width_s;
				ImgD.height=(image.height*width_s)/image.width;
			}else{
				ImgD.width=image.width;
				ImgD.height=image.height;
			}
		}else{
			if(image.height>height_s){
				ImgD.height=height_s;
				ImgD.width=(image.width*height_s)/image.height;
			}else{
				ImgD.width=image.width;
				ImgD.height=image.height;
			}
		}
	}
}

/**
 * 保存且返回原页面方法
 */
function formSubmit(url){
	if(url.length>0){
		$("form").attr('action',url);
		$("form").submit();
	}else{
		layer.alert('传入的url有误!', {icon: 2,title:'系统提示',shadeClose: true});
	}
}
/**
 * 保存并返回主页面
 */
function formSubmitAndBack(){
	var b = $("form").FormValidate();
	if(b){
		 $('form').ajaxSubmit(function(res) {
	    	if(res.status=='success'){
	    	    parent.toastr.success(res.message, '');
		        backMainPage();
	        }else{
	        	parent.toastr.error(res.message, '');
	        }
		});
	}
}

/**
 * 重新打开一个编辑页面
 * @param {Object} url
 */
function openEditPage(url){
	 var currentIframe=$(window.parent.document).find(".J_iframe").filter( ":visible" );
	 var mainPageKey=currentIframe.attr('data-id');
	 var editPageKey=Math.uuidFast();
	 window.parent.pageMap.put(mainPageKey,editPageKey);
	 $(currentIframe).attr("data-id",editPageKey).css("display","none");
	 var basePath=location.href.substr(0,location.href.lastIndexOf("/")+1);
	 if(url.indexOf('http')!=0){
		 url=basePath+url;
	 }
	 var editIframe='<iframe class="J_iframe" data-id="'+mainPageKey+'" name="iframe0" width="100%" height="100%" src="'+url+'" frameborder="0"  seamless></iframe>';
     $(window.parent.document).find("#content-main").append(editIframe);
}

/**
 * 从编辑页面返回到主页面
 * @param {Object} url
 */
function backMainPage(){
	 var basePath=location.href.substr(0,location.href.lastIndexOf("/")+1);
	 var currentIframe=$(window.parent.document).find(".J_iframe").filter( ":visible" );
	 var editPageKey=$(currentIframe).attr("data-id");
	 var mainPageKey=window.parent.pageMap.get(editPageKey);
	 window.parent.pageMap.remove(editPageKey);//移除map缓存
	 var mainIframe=$(window.parent.document).find('iframe[data-id="'+mainPageKey+'"]')[0];
	 $(mainIframe).attr("data-id",editPageKey);
	 $(mainIframe).css("display","inline");
	 var treeUrl=mainIframe.contentWindow.treeUrl;
	 if(!!treeUrl&&treeUrl!='undefined'){
		 mainIframe.contentWindow.reloadTree(basePath+treeUrl);
	 }
	 mainIframe.contentWindow.jqgridReload(); 
	 currentIframe.remove();
}
/**
 * 发送消息
 * @param smsData '{"receiver":"receiver","content":"content","busType":"busType","busInfo":"busInfo","remark":"remark"}';
 * @param mailData '{"receiver":"receiver","subject":"subject","content":"content","busType":"busType","busInfo":"busInfo","remark":"remark"}';
 */
function fnSendMessage(smsData,mailData){
	layer.open({
		title:'发送消息',	
		type: 2,
		area: ['70%', '80%'],
		fix: false, //不固定
		maxmin: true,
		content: '/sys/email/sendMessage.do?mailData='+encodeURIComponent(mailData)+'&smsData='+encodeURIComponent(smsData),
		btn: ['发送','取消'], //按钮
		yes: function(index, layero) {
		 var iframeWin = window[layero.find('iframe')[0]['name']];
		 iframeWin.saveMessage();
		}
	});
}
