<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../../include/css.jsp"%>
<%@ include file="../../../../include/status.jsp"%>
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style type="text/css">
.form-control{
	padding: 4px;
}
</style>
</head>
<body >
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post" action="updateTaskPoint.do" class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered" style="margin-bottom: 0px;">
						<thead>
							<tr>
								<th width="50">
									<a class="btn btn-primary btn-xs" onclick="addSamp('${vo.id}','')"><i class="fa fa-plus" aria-hidden="true"></i>样品</a>
								</th>
								<th width="60">序号</th>
								<th width="180">采样时间</th>
								<th width="130">样品编号</th>
								<th width="150">采样地点</th>
								<th width="120">样品名称</th>
								<th width="150">样品感观性状</th>
								<th>监测项目</th>
							</tr>
						</thead>
						<tbody id="samp_tb">
							<c:forEach items="${vo.sampList}" var="e" varStatus="v">
								<tr>
									<td align="center">
										<a onclick="deleteSamp('${e.id}')"><i class="fa fa-close text-danger"></i></a>
									</td>
							 		<td align="center">
							 			${v.index+1}
							 			<input type="hidden" name="sampList[${v.index}].id" value="${e.id}">
							 			<input type="hidden" name="sampList[${v.index}].recordVo.id" value="${e.recordVo.id}">
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input  data-mask="99:99" type="text" class="form-control required" validate="required" name="sampList[${v.index}].cyTime" value="${e.cyTime}">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<input type="text" class="form-control required" validate="required" name="sampList[${v.index}].sampCode" value="${e.sampCode}" >
							 		</td>
							 		<td>
							 			${e.pointName}
							 		</td>
							 		<td>
							 			${e.sampName}
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" class="form-control" name="sampList[${v.index}].xz" value="${e.xz}">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			${e.itemNames}
							 		</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<table class="table table-bordered">
						<thead>
							<tr>
								<th colspan="8" style="background-color: #fff;">质控信息</th>
							</tr>
							<tr>
								<th width="60">序号</th>
								<th width="180">采样时间</th>
								<th width="130">样品编号</th>
								<th width="150">采样地点</th>
								<th width="120">样品名称</th>
								<th width="150">样品感观性状</th>
								<th width="100">质控类型</th>
								<th>监测项目</th>
							</tr>
						</thead>
						<tbody id="samp_tb">
							<c:forEach items="${vo.zkList}" var="e" varStatus="v">
								<tr>
							 		<td align="center">
							 			${v.index+1}
							 			<input type="hidden" name="zkList[${v.index}].id" value="${e.id}">
							 			<input type="hidden" name="zkList[${v.index}].recordVo.id" value="${e.recordVo.id}">
							 		</td>
							 		<td>
								 		<input type="text" class="form-control datetimeISO required" validate="required" name="zkList[${v.index}].cyTime" value="${e.cyTime}">
							 		</td>
							 		<td>
							 			<input type="text" class="form-control required" validate="required" name="zkList[${v.index}].sampCode" value="${e.sampCode}" >
							 		</td>
							 		<td>
							 			${e.pointName}
							 		</td>
							 		<td>
							 			${e.sampName}
							 		</td>
							 		<td>
								 		<input type="text" class="form-control" name="zkList[${v.index}].xz" value="${e.xz}">
							 		</td>
							 		<td>
							 			<c:choose>
							 				<c:when test="${e.type==1}">
							 					现场平行样
							 				</c:when>
							 				<c:when test="${e.type==3}">
							 					全程序空白
							 				</c:when>
							 			</c:choose>
							 		</td>
							 		<td>
							 			${e.itemNames}
							 		</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12" style="text-align: right;">
							<a class="btn btn-w-m btn-success" type="button" onclick="fnSubmit4Save('updateCyd.do?isCommit=0')"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a class="btn btn-w-m btn-primary" type="button" onclick="fnSubmit('updateCyd.do?isCommit=1')"><i class="fa fa-check" aria-hidden="true"></i> 完成</a>
							<a class="btn btn-w-m btn-white" href="javascript:close();" ><i class="fa fa-times" aria-hidden="true"></i> 关闭</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
<%@ include file="../../../../include/js.jsp"%>
<script type="text/javascript">
var index = parent.layer.getFrameIndex(window.name); 
function fnSubmit4Save(url){
	selectCyrs();
	$.ajax({ 
		url:url,
		dataType:"json",
		data:$('#thisForm').serialize(),
		type:"post",
		success: function(data){
			parent.layer.msg(data.message, {icon: 0,time: 3000})
		},
		error:function(ajaxobj){  
			layer.msg(ajaxobj, {icon: 0,time: 3000});
	    }  
	});
}
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
function addSamp(pointId,type){
	$.ajax({ 
		url:"${basePath}bus/taskAp/addSamp.do",
		data: {'pointId':pointId,'type':type},
		async:false,
		success: function(data){
			layer.msg(data.message, {icon: 0,time: 3000});
			if(data.status='success'){
				 window.location.reload(true);
			}
		},
		error:function(ajaxobj){  
			layer.msg(ajaxobj, {icon: 0,time: 3000});
	    }
	});
}
function deleteSamp(sampId){
	$.ajax({ 
		url:"${basePath}bus/sampling/deleteSamp.do",
		data: {'id':sampId},
		async:false,
		success: function(data){
			layer.msg(data.message, {icon: 0,time: 3000});
			if(data.status='success'){
				 window.location.reload(true);
			}
		},
		error:function(ajaxobj){  
			layer.msg(ajaxobj, {icon: 0,time: 3000});
	    }
	});
}
</script>
</body>
</html>
