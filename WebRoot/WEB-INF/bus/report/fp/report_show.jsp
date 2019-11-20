<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../../include/status.jsp"%>

<style type="text/css">
legend{
	border-bottom:0px;
	width: 80px;
	margin-bottom:0px;
	font-size: 14px !important;
}
.form-control{
	padding: 2px 4px;
}
.input-group-addon{
padding: 2px;
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
						<tr>
							<td class="width-15 active"><label class="pull-right">报告编号:</label></td>
							<td class="width-35">
								${vo.reportNo}
							</td>
							<td class="width-15 active"><label class="pull-right">任务编号:</label></td>
							<td class="width-35">
								${vo.taskVo.no}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">客户名称:</label></td>
							<td>
								${vo.custVo.custName}
							</td>
							 <td class="active"><label class="pull-right">检测类型:</label></td>
							<td>
								${vo.taskType}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">样品名称:</label></td>
							<td>
								${vo.sampName}
							</td>
							<td class="active"><label class="pull-right ">检测日期:</label></td>
							<td>
								<div class="input-group date">
	                              	${vo.testDate}&nbsp;&nbsp;&nbsp;<span >至</span>&nbsp;&nbsp;&nbsp;${vo.testEndDate}
	                            </div>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">编制人员:</label></td>
							<td>
								${vo.makeUser}
							</td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">分&nbsp;配&nbsp;&nbsp;人:</label></td>
							<td>
								${vo.fpUser}
							</td>
							<td class="width-15 active"><label class="pull-right">分配日期:</label></td>
							<td>
	                             ${vo.fpDate}
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
