<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<style type="text/css">
.form-control{
	padding: 4px;
}
.table > tbody > tr > td{
	padding: 2px;
	text-align: center;
}
.table > thead > tr > th{
	text-align: center;
}
</style>
</head>
<body >
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post" action="update4Item.do" class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<c:forEach items="${timList}" var="e" varStatus="v">
						<table class="table table-bordered">
							<tr>
								<td style="width: 10%;text-align: right;" class="active">检测项目：</td>
								<td style="width: 15%;">
									${e.itemName}
									<input name="timList[${v.index}].id" value="${e.id}" type="hidden" />
									<input id="itemId_${v.index}" name="timList[${v.index}].itemId" value="${e.itemId}" type="hidden" />
								</td>
								<td style="width: 10%;text-align: right;" class="active">分&nbsp;析&nbsp;&nbsp;人：</td>
								<td style="width: 15%;">
									${e.testMan}
								</td>
								<td style="width: 10%;text-align: right;" class="active">校&nbsp;验&nbsp;&nbsp;人：</td>
								<td style="width: 15%;">
									${e.checkMan}
								</td>
								<td style="width: 10%;text-align: right;" class="active">检测时间：</td>
								<td style="width: 15%;">
									${e.testTime}
								</td>
							</tr>
							<tr>
								<td style="text-align: right;" class="active">检测方法：</td>
								<td>
									${e.methodName}
								</td>
								<td style="text-align: right;" class="active">检测仪器：</td>
								<td>
									${e.appName}
								</td>
								<td style="text-align: right;" class="active">检&nbsp;出&nbsp;&nbsp;限：</td>
								<td>
									${e.limitLine}
								</td>
								<td style="text-align: right;" class="active">温&nbsp;湿&nbsp;&nbsp;度：</td>
								<td>
									${e.wd} ℃ 
									${e.sd} % 
								</td>
							</tr>
							<tr>
								<c:if test="${vo.pj=='是'}">
									<td style="text-align: right;" class="active">评价标准：</td>
									<td>
										${e.standName}
									</td>
									<td style="text-align: right;" class="active">限值：</td>
									<td>
										<c:choose>
											<c:when test="${e.limited!=''&&e.limited!=null}">
												${e.limited} ${e.unit}
											</c:when> 
											<c:otherwise>
												<font color="red">限值未找到</font>
											</c:otherwise>
										</c:choose>
									</td>
								</c:if>
								<td style="text-align: right;" class="active">计量单位：</td>
								<td>
									${e.unit}
								</td>
								<td style="text-align: right;" class="active">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
								<td>
									${e.remark}
								</td>
							</tr>
							<tr>
								<td colspan="8">
									<table class="table table-bordered">
										<thead>
											<tr>
												<th width="10%">
													检测点位
												</th>
												<th width="10%">
													检测日期
												</th>
												<th width="100">
													批次
												</th>
												<th width="15%">
													编号
												</th>
												<th>
													检测值
												</th>
												<th width="15%">
													单项判定
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${e.itemList}" var="it" varStatus="iv">
												<tr>
													<td>
														${it.pointVo.pointName}
														<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].id" value="${it.id}">
														<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
													</td>
													<td>
														${it.cyDate}
													</td>
													<td>
														${it.trVo.sampVo.p}
													</td>
													<td>
														${it.trVo.sampVo.sampCode}
													</td>
													<td>
														<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.value" value="${it.trVo.value}">
													</td>
													<td>
														<select id="result_${v.index}_${iv.index}" name="timList[${v.index}].itemList[${iv.index}].result" class="form-control" >
														 	<c:choose>
														 		<c:when test="${it.result=='合格'}">
														 			<option value="">/</option>
																 	<option value="合格" selected="selected">合格</option>
																 	<option value="不合格">不合格</option>
														 		</c:when>
														 		<c:when test="${it.result=='不合格'}">
														 			<option value="">/</option>
																 	<option value="合格">合格</option>
																 	<option value="不合格" selected="selected">不合格</option>
														 		</c:when>
														 		<c:otherwise>
														 			<option value="">/</option>
																 	<option value="合格">合格</option>
																 	<option value="不合格">不合格</option>
														 		</c:otherwise>
														 	</c:choose>
														 </select>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</td>
							</tr>
						</table>
					</c:forEach>
				</form>
			</div>
		</div>
	</div>
<%@ include file="../../../include/js.jsp"%>
<script src="${basePath}h/js/plugins/suggest/bootstrap-suggest.min.js"></script>
<script type="text/javascript">

$('.dtISO').each(function(){
  laydate.render({
    elem: this,
    theme: 'molv',
    type:'datetime',
    format: 'yyyy-MM-dd HH:mm',
    calendar: true,
    trigger: 'click'
  });
});
var index = parent.layer.getFrameIndex(window.name); 
function fnSubmit(url){
	var t = $("#thisForm").FormValidate();
	if(t){
		$.ajax({ 
			url:url,
			dataType:"json",
			data:$('#thisForm').serialize(),
			type:"post",
			success: function(data){
				parent.layer.msg(data.message, {icon: 0,time: 3000})
				if("success"==data.status){
					parent.layer.close(index);
				}
			},
			error:function(ajaxobj){  
				layer.msg(ajaxobj, {icon: 0,time: 3000});
		    }  
		});
	}
}
function close(){
	parent.layer.close(index);
}
function copyVals(obj){
	obj=$(obj);
	var value0=obj.closest('td').find('input').eq(0).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#samp_tb tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('input').eq(0).val(value0);
	});
}
function getData(arrStr){
	var data = {
	    	value: []
	    };
	var u=arrStr;
	var ut=u.split(';');
	for(var i=0;i<ut.length;i++){
		data.value.push({word: ut[i]});
	}
	return data;
}
$(function(){
	$('input[id^="limited_"]').each(function(n){
		var arrStr=$('#limitedStr_'+n).val();
		$('#limited_'+n).bsSuggest({
            indexId: 0, //data.value 的第几个数据，作为input输入框的内容
            indexKey: 0, //data.value 的第几个数据，作为input输入框的内容
            data: getData(arrStr),
            showBtn: true
        });
		if(arrStr.indexOf(';')==-1&&arrStr!=''&&$('#limited_'+n).val()==''){
			$('#limited_'+n).val(arrStr);
		}
	})
});
//标准物质 、 方法、 仪器
function chooseMethod(n){
	var methodId=$('#methodId_'+n).val();
	var itemId=$('#itemId_'+n).val();
	layer.open({
		title:'检测方法',	
		type: 2,
		area: ['800px', '400px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}init/itemMethod/showMethod.do?methodVo.id='+methodId+'&itemVo.id='+itemId,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			$('#methodId_'+n).val(data.id);
			$('#methodName_'+n).val(data.name);
			$('#limitLine_'+n).val(data.limitLine);
			if(data.id!='methodId'){
				$('#appId_'+n).val(null);
				$('#appName_'+n).val(null);
			}
			//保存方法
			$('#thisForm').attr('action','update4Item.do?isCommit=0');
			$('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success('更新成功！', '');
		    	    location.reload();
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
		}
	});
}
function chooseApp(n){
	var methodId=$('#methodId_'+n).val();
	if(methodId==''){
		layer.msg('请选择检测方法！', {icon: 0,time: 1000});
		return false;
	}
	var appId=$('#appId_'+n).val();
	layer.open({
		title:'检测仪器',	
		type: 2,
		area: ['800px', '400px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}bus/itemTest/showApp.do?methodId='+methodId+'&appId='+appId,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			$('#appId_'+n).val(data.id);
			$('#appName_'+n).val(data.name);
		}
	});
}
function validtValue(obj,index,itemId){
	var vl=parseFloat($(obj).val());
	$.ajax({ 
		url:"checkValue.do?pointId=${vo.id}&itemId="+itemId+'&value='+vl,
		dataType:"json",
		type:"post",
		async:false,
		success: function(data){
			 if(data.type=='error'){
				 $('#result_'+index).val('不合格');
			 }else{
				 $('#result_'+index).val('合格');
			 }
		},
		error:function(ajaxobj){  
			msg=ajaxobj;
	    }  
	});
}
function checkThis(obj){
	var t=$(obj).find('option:selected').text();
	$(obj).closest('td').find('input').val(t);
}
//测试标准
function choosePstand(n,sampTypeId){
	var standId=$('#standId_'+n).val();
	layer.open({
		title:'评价标准',	
		type: 2,
		area: ['800px', '400px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}init/pstandard/select.do?id='+standId+'&sampTypeId='+sampTypeId,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			$('#standId_'+n).val(data.id);
			$('#standName_'+n).val(data.name);
			//保存方法
			$('#thisForm').attr('action','update4Item.do?isCommit=0');
			$('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success('更新成功！', '');
		    	    location.reload();
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
		}
	});
}
//限值
function chooseLimit(n,timd,itemId,sampTypeName){
	var standId=$('#standId_'+n).val();
	if(standId==null||standId==''){
		layer.msg('请选择评价标准！', {icon: 0,time: 1000});
		return false;
	}
	layer.open({
		title:'评价标准',	
		type: 2,
		area: ['1000px', '400px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}init/pstandItem/select.do?sampType='+encodeURI(sampTypeName)+'&standId='+standId+'&itemVo.id='+itemId,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			$.ajax({ 
				url:'update4Limited.do?id='+timd+'&uuid='+data.id,
				dataType:"json",
				type:"post",
				async:false,
				success: function(data){
					if(data.status=='success'){
			    	    parent.toastr.success('更新成功！', '');
			    	    location.reload();
			        }else{
			        	parent.toastr.error(data.message, '');
			        }
				},
				error:function(ajaxobj){  
					msg=ajaxobj;
			    }  
			});
		}
	});
}
</script>
</body>
</html>
