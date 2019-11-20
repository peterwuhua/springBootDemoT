<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<style type="text/css">
legend{
	border-bottom:0px;
	width: 80px;
	margin-bottom:0px;
	font-size: 14px !important;
}
.form-control{
	padding: 4px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>
					<ol class="breadcrumb">
						<li><a>样品交接</a></li>
						<li><strong>查看</strong></li>
					</ol>
				</h5>
			</div>
			<div class="ibox-content">
				<form method="post" action="updateData.do" class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: -1px;">
						<tr>
							<td class="active"  style="width: 150px;"><label class="pull-right">任务编号:</label></td>
							<td>
								<a onclick="fnTask('${vo.id}','${vo.no}');">${vo.no}</a>
							</td>
							<td class="active"  style="width: 150px;"><label class="pull-right">单位名称:</label></td>
							<td>
								${vo.custVo.custName}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">检测类型:</label></td>
							<td>
								${vo.taskType}
							</td>
							<td class="active"><label class="pull-right">样品类别:</label></td>
							<td>
								${vo.sampTypeName}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">采/送样人员:</label></td>
							<td>
								${vo.cyName}
							</td>
							<td class="active"><label class="pull-right ">采/送样日期:</label></td>
							<td>
								${vo.cyDate}
								<c:if test="${vo.cyDate!=vo.cyEndDate && vo.cyEndDate!=null}">
									~${vo.cyEndDate}
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.remark}
							</td>
						</tr>
						<tr>
							<td colspan="4">样品信息</td>
						</tr>
					</table>
					<c:choose>
						<c:when test="${vo.source=='外采'}">
							 <div style="overflow-x: auto;">
							 	<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;min-width: 1400px">
								 <thead>
								 	<tr>
								 		<th style="width: 45px;">序号</th>
								 		<th style="width: 90px;">样品名称</th>
								 		<th style="width: 130px;">样品编号</th>
								 		<th style="width: 80px;">采样时间</th>
								 		<th style="width: 10%;">性状描述</th>
								 		<th style="width: 100px;">固定剂</th>
								 		<th style="width: 10%;">保存位置</th>
								 		<th style="width: 65px;">保存<br>时间(h)</th>
								 		<th style="width: 65px;">培养<br>时间(h)</th>
								 		<th style="width: 100px;">保存条件</th>
								 		<th style="width: 50px;">是否<br>留样</th>
								 		<th style="min-width: 100px;">采样容器</th>
								 		<th>检测项目</th>
								 	</tr>
								 </thead>
								 <tbody id="samp_tb">
								 	<c:set var="num" value="0"/>
								 	<c:forEach items="${vo.cydList}" var="e" varStatus="v">
								 		<tr>
								 			<td colspan="12">
									 			检测点：${e.cyDate}-${e.pointName}  【<a href="javascript:void(0);" onclick="fnPoint('${e.id}','${e.sampName}')">采样单</a>】
									 		</td>
								 		</tr>
										<c:forEach items="${e.sampList}" var="e1" varStatus="v1">
											<tr>
										 		<td align="center">
										 			${v1.index+1}
										 			<input type="hidden" class="form-control" name="sampList[${num}].sort" value="${e1.sort}" >
										 			<input type="hidden" name="sampList[${num}].id" value="${e1.id}">
										 		</td>
										 		<td>
										 			 ${e1.sampName}
										 		</td>
										 		<td>
										 			${e1.sampCode}
										 		</td>
										 		<td>
											 		${e1.cyTime}
										 		</td>
										 		<td>
										 			${e1.xz}
										 		</td>
										 		<td>
										 			${e1.tjj}
										 		</td>
										 		<td>
										 			${e1.saveAddress}
										 		</td>
										 		<td>
										 			${e1.saveHour}
										 		</td>
										 		<td>
										 			${e1.pyHour}
										 		</td>
										 		<td>
										 			${e1.saveRequest}
										 		</td>
										 		<td>
										 			${e1.ly}
										 		</td>
										 		<td>
										 			<c:forEach items="${e1.containerList}" var="e2">
										 				${e2.container}
										 			</c:forEach>
										 		</td>
										 		<td>
										 			${e1.itemNames}
										 		</td>
										 	</tr>
										 	<c:set var="num" value="${num+1}"/>
										</c:forEach>
									</c:forEach>
								</tbody>
							</table>
							 
							 </div>
						</c:when>
						<c:otherwise>
							<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;">
								 <thead>
								 	<tr>
								 		<th style="width: 50px;">序号</th>
								 		<th style="min-width: 80px">样品名称</th>
								 		<th style="min-width: 100px">样品编号</th>
								 		<th style="min-width: 140px">收样时间</th>
								 		<th style="min-width: 80px">性状描述</th>
								 		<th style="min-width: 80px">保存位置</th>
								 		<th style="min-width: 80px">保存时间</th>
								 		<th style="min-width: 80px">培养时间</th>
								 		<th style="min-width: 80px">保存条件</th>
								 		<th style="min-width: 80px">是否留样</th>
								 		<th>检测项目</th>
								 	</tr>
								 </thead>
								 <tbody id="samp_tb">
									<c:forEach items="${vo.sampList}" var="e" varStatus="v">
										<tr index="${v.index}">
									 		<td align="center">
									 			<span>${e.sort}</span>
									 			<input type="hidden" name="sampList[${v.index}].id" value="${e.id}">
									 		</td>
									 		<td>
									 			${e.sampName}
									 		</td>
									 		<td>
									 			${e.sampCode}
									 		</td>
									 		<td>
									 			${e.cyTime}
									 		</td>
									 		<td>
									 			${e.xz}
									 		</td>
									 		<td>
									 			${e.saveAddress}
									 		</td>
									 		<td>
									 			${e.saveHour}
									 		</td>
									 		<td>
									 			${e.pyHour}
									 		</td>
									 		<td>
									 			${e.saveRequest}
									 		</td>
									 		<td>
									 			${e.ly}
									 		</td>
									 		<td>
										 		${e.itemNames}
										 	</td>
									 	</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:otherwise>
					</c:choose>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">样品处理要求:</label></td>
							<td class="width-35" colspan="3">
								${vo.dealRequest}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">上传附件:</label></td>
							<td class="width-35" colspan="3">
								<c:forEach items="${vo.fileList}" var="e" varStatus="v">
								 	<div style="float: left;margin-right: 10px;">
									 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-info">${e.fileName}</a>
								 	</div>
								 </c:forEach>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">交接人员:</label></td>
							<td class="width-35">
								${vo.reciveUser}
							</td>
							<td class="width-15 active"><label class="pull-right">交接时间:</label></td>
							<td class="width-35">
								${vo.reciveDate}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.remark}
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
	<script>
	function fnTask(id,no){
		parent.layer.open({
			title:'任务【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/task/show.do?id='+id
		});
	}
	function fnPoint(id,sampName){
		var url='/bus/taskXc/showCyd.do?id='+id;
		var sampType='${vo.sampType}';
		if(sampType!='环境'){
			url='/bus/taskXc/showCyd4Zw.do?id='+id;
		}
		parent.layer.open({
			title:'【'+sampName+'】采样单',	
			type: 2,
			area: ['80%','80%'],
			fix: false, //不固定
			maxmin: true,
			content: url
		});
	}
	function fnSubmitSave(url){
		$('#thisForm').ajaxSubmit(function(res) {
	    	if(res.status=='success'){
	    	    parent.toastr.success(res.message, '');
	        }else{
	        	parent.toastr.error(res.message, '');
	        }
		});
	}
	//打印
	function fnPrint(){
		parent.layer.open({
			title:'条码打印',	
			type: 2,
			 area: ['800px', '500px'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}bus/taskJj/print.do?id=${vo.id}'
		});
	}
	</script>
</body>
</html>
