<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<div class="tabs-container">
					<div class="tab-content">
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
							<tbody>
								<tr>
									<th class="width-15 active"><label class="pull-right">样品编号:</label></th>
									<td class="width-35">${vo.sampCode}</td>
									<td class="width-15 active"><label class="pull-right">样品名称 :</label></td>
									<td class="width-35">${vo.sampName}</td>
								</tr>
								<tr>
									<td class="width-15 active"><label class="pull-right">样品形状:</label></td>
									<td class="width-35">${vo.xz}</td>
									<td class="width-15 active"><label class="pull-right">是否留样:</label></td>
									<td class="width-35">${vo.ly}</td>
								</tr>
								<c:if test="${vo.ly=='是'}">
									<tr>
										<td class="width-15 active"><label class="pull-right">期限:</label></td>
										<td class="width-35">${vo.bcqx}</td>
										<td class="width-15 active"><label class="pull-right">留样原因:</label></td>
										<td class="width-35">${vo.lyReason}</td>
									</tr>
								</c:if>
								<tr>
									<td class="width-15 active"><label class="pull-right">样品类别:</label></td>
									<td class="width-35">${vo.sampTypeName}</td>
									<td class="width-15 active"><label class="pull-right">检测内容:</label></td>
									<td class="width-35">${vo.itemNames}</td>
								</tr>
								<tr>
									<td class="width-15 active"><label class="pull-right">采送样时间:</label></td>
									<td class="width-35">${vo.sampDate}</td>
									<td class="width-15 active"><label class="pull-right">采送样人:</label></td>
									<td class="width-35">${vo.schemeVo.cyUserName}</td>
								</tr>
								<tr>
									<td class="width-15 active"><label class="pull-right">保管部门:</label></td>
									<td class="width-35">${vo.deptName}</td>
									<td class="width-15 active"><label class="pull-right">交接人:</label></td>
									<td class="width-35">${vo.saveUser}</td>
								</tr>
								<tr>
									<td class="width-15 active"><label class="pull-right">存放位置:</label></td>
									<td class="width-35" colspan="3">${vo.saveAddress}</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../../include/js.jsp"%>
		<script>
</script>
</body>
</html>
