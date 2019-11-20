<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>

<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style type="text/css">
legend {
	border-bottom: 0px;
	width: 80px;
	margin-bottom: 0px;
	font-size: 14px !important;
}
.form-control{
	padding: 6px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">样品检测</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post" class="form-horizontal" id="thisForm" enctype="multipart/form-data">
					<input name="ids" value="${vo.ids}" type="hidden" />
					<fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
						<legend>待检项目</legend>
					</fieldset>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<thead>
							<tr>
								<th style="text-align: center;width: 50px;">序号</th>
								<th style="text-align: center;width: 200px;">样品编号</th>
								<th style="text-align: center;">检测项目</th>
								<th style="text-align: center;">检测方法</th>
								<th style="text-align: center;width: 200px;">检测值</th>
								<th style="text-align: center;width: 200px;">检出限</th>
							</tr>
						</thead>
						<tbody id="jcTb">
							<c:forEach items="${itemList}" var="e" varStatus="s">
								<tr id="${e.id}">
									<td align="center">
										${s.index+1}
										<input type="hidden" value="${e.id}" name="itemList[${s.index}].id" /> 
									</td>
									<td>${e.samplingVo.sampCode}</td>
									<td>${e.itemName}</td>
									<td>
										<div class="input-group">
											<select id="methodId${s.index}" name="itemList[${s.index}].methodId" class="form-control"  onkeyup="keygo(event,this)" onchange="changeMethod(this,'${s.index}');">
											 	 <option value="">-请选择-</option>
											 	 <c:forEach items="${methodList}" var="es" varStatus="vs">
											 	 	<c:choose>
											 	 		<c:when test="${es.id==e.methodId}">
											 	 			<option value="${es.id}" key="${es.limitLine}" key1="${es.appIds}" selected="selected">${es.code} ${es.name}</option>
											 	 		</c:when>
											 	 		<c:otherwise>
											 	 			<option value="${es.id}" key="${es.limitLine}" key1="${es.appIds}">${es.code} ${es.name}</option>
											 	 		</c:otherwise>
											 	 	</c:choose>
											 	 </c:forEach>
											 </select>
											 <span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copySelect(this);return false;"></span>
										 </div>
									</td>
									<td>
										<input type="text" id="value${s.index}" name="itemList[${s.index}].value" class="form-control required" validate="required" value="${e.value}"  onkeyup="keygo(event,this)">
									</td>
									<td>
										<input type="text" id="limitLine${s.index}" name="itemList[${s.index}].limitLine" class="form-control" value="${e.limitLine}" readonly="readonly">
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
						<legend>仪器信息</legend>
					</fieldset>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<thead>
							<tr>
								<th style="text-align: center;width: 50px;">序号</th>
								<th style="text-align: center;width: 150px;">样品编号</th>
								<th style="text-align: center;">检测项目</th>
								<th style="text-align: center;">使用仪器</th>
								<th style="text-align: center;width: 80px;">温度</th>
								<th style="text-align: center;width: 80px;">湿度</th>
								<th style="text-align: center;width: 160px;">开始时间</th>
								<th style="text-align: center;width: 160px;">结束时间</th>
								<th style="text-align: center;width: 100px;">仪器状态</th>
							</tr>
						</thead>
						<tbody  id="appTb">
							<c:forEach items="${itemList}" var="e" varStatus="s">
								<tr>
									<td align="center">
										${s.index+1}
										<input type="hidden" value="${e.id}" name="itemVoList[${s.index}].id" /> 
									</td>
									<td>${e.samplingVo.sampCode}</td>
									<td>${e.itemName}</td>
									<td>
										<select id="appId${s.index}" name="itemVoList[${s.index}].appId" class="form-control">
										 	 <option value="">-请选择-</option>
										 	 <c:forEach items="${e.appList}" var="es" varStatus="vs">
										 	 	<c:choose>
										 	 		<c:when test="${es.id==e.appId}">
										 	 			<option value="${es.id}" selected="selected">${es.name} ${es.spec}</option>
										 	 		</c:when>
										 	 		<c:otherwise>
										 	 			<option value="${es.id}">${es.name} ${es.spec}</option>
										 	 		</c:otherwise>
										 	 	</c:choose>
										 	 </c:forEach>
										 </select>
									</td>
									<td>
										<input onchange="AutoCopyVal(this)" type="text" id="wd${s.index}" name="itemVoList[${s.index}].wd" class="form-control" value="${e.wd}">
									</td>
									<td>
										<input onchange="AutoCopyVal(this)" type="text" id="sd${s.index}" name="itemVoList[${s.index}].sd" class="form-control" value="${e.sd}">
									</td>
									<td>
										<input type="text" id="appStartTime${s.index}" name="itemVoList[${s.index}].appStartTime" class="form-control datetimeISO" value="${e.appStartTime}" >
									</td>
									<td>
										<input type="text" id="appEndTime${s.index}" name="itemVoList[${s.index}].appEndTime" class="form-control datetimeISO" value="${e.appEndTime}" >
									</td>
									<td>
										<select id="appStat${s.index}" name="itemVoList[${s.index}].appStat" class="form-control">
										 	<c:choose>
										 		<c:when test="${e.appStat=='异常'}">
												 	<option value="正常">正常</option>
												 	<option value="异常" selected="selected">异常</option>
										 		</c:when>
										 		<c:otherwise>
												 	<option value="正常" selected="selected">正常</option>
												 	<option value="异常">异常</option>
										 		</c:otherwise>
										 	</c:choose>
										 </select>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<th class="active"><label class="pull-right">上传文件:</label></th>
								<td>
									<input type="file" name="file" multiple="multiple" class="form-control"/>
								</td>
								<td colspan="2" id="removeFile">
									<c:forEach items="${vo.fileList}" var="e" varStatus="v">
									 	<div style="float: left;margin-right: 10px;">
										 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-w-m btn-info">${e.fileName}</a>
										 	<a onclick="removeFiles('${e.id}',this)" title="删除"><i class="fa fa-close text-danger"></i></a>
									 	</div>
									 </c:forEach>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">检测人:</label></td>
								<td class="width-35">
									<input id="testManId" name="testManId" type="hidden" value="${vo.testManId}" /> 
									<input id="testMan" name="testMan" class="form-control" maxlength=32  type="text" value="${vo.testMan}" readonly="readonly" />
								</td>
								<td class="width-15 active"><label class="pull-right">检测日期:</label></td>
								<td class="width-35">
									<div class="input-group date">
		                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                              	<input id="testTime" name="testTime" class="form-control required dateISO" validate="required" maxlength=20 placeholder="检测日期" type="text" value="${vo.testTime}" />
		                            </div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									<textarea maxlength="128" style="width: 600px;" rows="2" cols="60" id="remark" name="remark" class="form-control">${vo.remark}</textarea>
								</td>
							</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" type="button" onclick="formSubmitSave('update4Data.do?isCommit=0')"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a class="btn btn-w-m btn-primary" type="button" onclick="fnSubmit('update4Data.do?isCommit=1')"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<!-- Sweet alert -->
	<script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
</body>
<script>
$('input[type="file"]').prettyFile();

function removeFiles(id,obj){
	layer.confirm('确认要删除?', {icon:3, title:'系统提示'}, function(index){
		$.ajax({
			url:'${basePath}sys/files/deleteOne.do?id='+id,
			dataType:"json",
			type:"post",
			async:false,
			success: function(data){
				if(data.status=='success'){
					layer.msg(data.message, {icon: 0,time: 1000});
					$(obj).parent().remove();
				}
			},
			error:function(ajaxobj){
		    }  
		});
	});
}
function formSubmitSave(url){
	$('#thisForm').attr('action',url);
	$('#thisForm').submit()
}	
function fnSubmit(url){
	swal({
        title: "您确定要提交该任务吗",
        text: "提交后将无法修改，请谨慎操作！",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定",
        cancelButtonText: "取消"
    }, function () {
    	$('#thisForm').attr('action',url);
		var b = $("#thisForm").FormValidate();
		if(b){
			 $('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success(res.message, '');
			        backMainPage();
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
		}
    });
}
function copySelect(obj){
	obj=$(obj);
	var idValue=obj.closest('td').find('select').val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#jcTb tr:gt('+indexTr+')').each(function(){
		var selObj=$(this).find('td').eq(indexTd).find('select');
		selObj.val(idValue);
		changeMethod(selObj,$(this).index());
	});
}
function keygo(evt,obj){
	var cols=$('#jcTb').find('tr').length-1;
	var key = window .event?evt.keyCode:evt.which; 
	var tdIndex=$(obj).closest('td').index();
	var trIndex=$(obj).closest('tr').index();
	if (key==37){//左 
		if(tdIndex>3){
			var tdStr=$('#jcTb').find('tr:eq('+trIndex+')').find('td:eq('+(tdIndex-1)+')');
			if(tdStr.find('input').length>0){
				tdStr.find('input').focus();
			}else{
				tdStr.find('select').focus();
			}
		}
	}else if(key==38){//上
		if(trIndex>0){
			var tdStr=$('#jcTb').find('tr:eq('+(trIndex-1)+')').find('td:eq('+tdIndex+')');
			if(tdStr.find('input').length>0){
				tdStr.find('input').focus();
			}else{
				tdStr.find('select').focus();
			}
		}
	}else if(key==39){//右 
		if(tdIndex<=4){
			var tdStr=$('#jcTb').find('tr:eq('+trIndex+')').find('td:eq('+(tdIndex+1)+')');
			if(tdStr.find('input').length>0){
				tdStr.find('input').focus();
			}else{
				tdStr.find('select').focus();
			}
		}
	}else if(key==40){//下
		if(trIndex<cols){
			var tdStr=$('#jcTb').find('tr:eq('+(trIndex+1)+')').find('td:eq('+tdIndex+')');
			if(tdStr.find('input').length>0){
				tdStr.find('input').focus();
			}else{
				tdStr.find('select').focus();
			}
		}
	}
}
function changeMethod(obj,trIndex){
	var vl=$(obj).val();
	//回填检出限
	var limitLine=$(obj).find('option[value="'+vl+'"]').attr('key');
	$(obj).closest('td').next().next().find('input').val(limitLine);
	//更新仪器信息
	var appIds=$(obj).find('option[value="'+vl+'"]').attr('key1');
	if(appIds==''){
		appIds='-1';
	}
	$.ajax({ 
		url:"${basePath}res/appara/ajax4AppList.do?ids="+appIds,
		async:false,
		success: function(data){
			var optStr='';
			for(var i=0;i<data.length;i++){
				optStr+='<option value="'+data[i].id+'">'+data[i].name+' '+data[i].spec+'</option>';
			}
			 $('#appId'+trIndex).html(optStr);
		},
		error:function(ajaxobj){  
			layer.msg(ajaxobj, {icon: 0,time: 1000});
    	}  
	});
};
function AutoCopyVal(obj){
	var idValue=$(obj).val();
	var indexTr=$(obj).closest('td').closest('tr').index();
	var indexTd=$(obj).closest('td').index();
	$('#appTb tr:gt('+indexTr+')').each(function(){
		var inputVl=$(this).find('td').eq(indexTd).find('input');
		if(inputVl.val()==''){
			inputVl.val(idValue);
		}
	});
}
</script>
</html>
