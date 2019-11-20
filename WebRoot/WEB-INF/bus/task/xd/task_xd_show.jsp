<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style type="text/css">
legend{
border-bottom:0px;
width: 80px;
margin-bottom:0px;
font-size: 14px !important;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<thead>
							<tr>
								<th width="50">序号</th>
								<th>任务编号</th>
								<th>检测项目</th>
								<th>样品数量</th>
								<th>检测科室</th>
								<th>检测要求完成时间</th>
							</tr>
						</thead>
						<tbody id="xdTb">
							<c:forEach items="${vo.timList}" var="e" varStatus="s">
								<tr>
									<td>${s.index+1}
									</td>
									<td>${e.taskVo.no}</td>
									<td>${e.itemName}</td>
									<td>${e.sampNum}</td>
									<td>
										${e.deptName}
									<td>
										${e.compDate}
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">下达人员:</label></td>
							<td class="width-35">
								${vo.xdUser}
							</td>
							<td class="width-15 active"><label class="pull-right">下达时间:</label></td>
							<td class="width-35">
								${vo.xdDate}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.xdMsg}
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
