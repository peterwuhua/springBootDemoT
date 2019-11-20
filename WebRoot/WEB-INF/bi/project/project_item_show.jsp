<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<style type="text/css">
 
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
			    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="background-color: white;">
					<tr>
						<td width="50px">序号</td>
						<td>监测点位</td>
						<td>批次</td>
						<td>样品编号</td>
						<td>检测项目</td>
						<td>进度</td>
					</tr>
					<tbody>
						<c:forEach items="${itList}" var="e" varStatus="s">
							<tr>
								<td align="center">${s.index+1}</td>
								<td>${e.schemeDetailVo.pointName}</td>
								<td>${e.pc}</td>
								<td>${e.samplingVo.sampCode}</td>
								<td>${e.itemName}</td>
								<td>${e.status}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>
</body>
</html>