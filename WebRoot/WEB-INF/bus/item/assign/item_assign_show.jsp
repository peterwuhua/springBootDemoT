<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<%@ include file="../../../include/status.jsp"%>
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
			<div class="ibox-content">
				<form method="post" class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<thead>
							<tr>
								<th width="50">序号</th>
								<th>检测项目</th>
								<th>样品名称</th>
								<th>样品数量</th>
								<th>检测人</th>
								<th>复核人</th>
								<th>检测截止时间</th>
								<th>上报截止日期</th>
							</tr>
						</thead>
						<tbody id="fpTb">
							<c:forEach items="${itemList}" var="e" varStatus="s">
								<tr>
									<td>
										${s.index+1}
									</td>
									<td>${e.itemName}</td>
									<td>${e.sampName}</td>
									<td>${e.sampNum}</td>
									<td>
										${e.testMan}
									</td>
									<td>
										${e.checkMan}
									</td>
									<td>
										${e.compDate}
									</td>
									<td>
										${e.sbDate}
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">分&nbsp;&nbsp;配&nbsp;&nbsp;人:</label></td>
								<td class="width-35">
									${vo.assignUser}
								</td>
								<td class="width-15 active"><label class="pull-right">分配日期:</label></td>
								<td class="width-35">
									${vo.assignDate}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									${vo.assignMsg}
								</td>
							</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
</body>
</html>
