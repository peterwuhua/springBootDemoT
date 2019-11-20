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
.table > thead > tr > th,.table > tbody > tr > td{
	text-align: center;
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
							 			<input disabled="disabled" type="text" class="form-control required" validate="required" name="sampList[${v.index}].sort" value="${v.index+1}" >
							 			<input type="hidden" name="sampList[${v.index}].id" value="${e.id}">
							 			<input type="hidden" name="sampList[${v.index}].recordVo.id" value="${e.recordVo.id}">
							 		</td>
							 		<td>
								 		<input disabled="disabled" type="text" class="form-control datetimeISO required" validate="required" name="sampList[${v.index}].cyTime" value="${e.cyTime}">
							 		</td>
							 		<td>
							 			<input disabled="disabled" type="text" class="form-control required" validate="required" name="sampList[${v.index}].sampCode" value="${e.sampCode}" >
							 		</td>
							 		<td>
							 			${e.pointName}
							 		</td>
							 		<td>
							 			${e.sampName}
							 		</td>
							 		<td>
								 		<input disabled="disabled" type="text" class="form-control" name="sampList[${v.index}].xz" value="${e.xz}">
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
								 		${e.cyTime}
							 		</td>
							 		<td>
							 			${e.sampCode}
							 		</td>
							 		<td>
							 			${e.pointName}
							 		</td>
							 		<td>
							 			${e.sampName}
							 		</td>
							 		<td>
								 		${e.xz}
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
				</form>
			</div>
		</div>
	</div>
<%@ include file="../../../../include/js.jsp"%>
</body>
</html>
