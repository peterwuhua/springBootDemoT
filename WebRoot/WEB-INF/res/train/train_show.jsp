<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<%@ include file="../../include/status.jsp"%>
<style type="text/css">
legend {
	border-bottom: 0px;
	width: 80px;
	margin-bottom: 0px;
	font-size: 14px !important;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="gridPage.do">培训管理</a></li>
					<li><strong>查看</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form id="thisForm" method="post" action="" class="form-horizontal">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门:</label></td>
								<td class="width-35">${vo.orgName}</td>
								<td class="width-15 active"><label class="pull-right">负&nbsp;责&nbsp;人:</label></td>
								<td class="width-35">${vo.userName }</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题 :</label></td>
								<td class="width-35">${vo.title}</td>
								<td class="width-15 active"><label class="pull-right">岗&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位:</label></td>
								<td class="width-35">${vo.post}</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">开始时间:</label></td>
								<td class="width-35">${vo.startDate }</td>
								<td class="width-15 active"><label class="pull-right">结束时间:</label></td>
								<td class="width-35">${vo.endDate }</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型 :</label></td>
								<td class="width-35">${vo.type}</td>
								<td class="width-15 active"><label class="pull-right">培训地点:</label></td>
								<td class="width-35">${vo.address}</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">培训内容:</label></td>
								<td class="width-35" colspan="3">${vo.content }</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td class="width-35" colspan="3">${vo.remark }</td>
							</tr>
						</tbody>
					</table>
					<fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
						<legend>实施信息</legend>
					</fieldset>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<thead>
							<tr>
								<th>序号</th>
								<th>被培训/考核人员</th>
								<th>培训/考核时间</th>
								<th>结果</th>
								<th>备注</th>
							</tr>
						</thead>
						<c:forEach items="${vo.detailList}" var="e" varStatus="v">
							<tr>
								<td>${v.index+1}</td>
								<td>${e.userName}</td>
								<td>${e.trainDate}</td>
								<td>${e.result}</td>
								<td>${e.remark}</td>
							</tr>
						</c:forEach>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-white" href="gridPage.do" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
