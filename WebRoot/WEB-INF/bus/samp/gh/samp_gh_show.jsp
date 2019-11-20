<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<%@ include file="../../../include/status.jsp"%>

<style type="text/css">
legend{
border-bottom:0px;
width: 80px;
margin-bottom:0px;
font-size: 14px !important;
}
</style>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">样品编号:</label></td>
							<td class="width-35">
								${vo.sampCode}
							</td>
							<td class="width-15 active"><label class="pull-right">样品名称:</label></td>
							<td class="width-35">
								${vo.sampName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">归&nbsp;还&nbsp;&nbsp;人:</label></td>
							<td class="width-35">
								${vo.useName}
							</td>
							<td class="width-15 active"><label class="pull-right">归还时间:</label></td>
							<td class="width-35">
								${vo.useDate}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态:</label></td>
							<td>
								<c:if test="${vo.status=='SAMP_22'}">待归还</c:if>
								<c:if test="${vo.status=='SAMP_23'}">已归还</c:if>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.content}
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
