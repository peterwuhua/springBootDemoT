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
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">项目编号:</label></td>
								<td class="width-35">
									<a href="javascript:void();" onclick="fnShow('${vo.id}','${vo.no}');">${vo.no}</a>
								</td>
								<td class="width-15 active"><label class="pull-right">客户名称:</label></td>
								<td class="width-35">
									${vo.custVo.custName}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">检测类型:</label></td>
								<td>
									${vo.taskType}
								</td>
								<td class="width-15 active"><label class="pull-right">样品类别:</label></td>
								<td class="width-35">
									${vo.sampTypeName}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">立项日期:</label></td>
								<td class="width-35">${vo.rdate}</td>
								<td class="width-15 active"><label class="pull-right">拟完成日期:</label></td>
								<td class="width-35">${vo.finishDate}</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									${vo.remark}
								</td>
							</tr>
						</tbody>
					</table>
					<fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
					  <legend>指派信息</legend>
					</fieldset>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">踏勘人员:</label></td>
							<td class="width-35">
								${vo.tkUserName}
							</td>
							<td class="width-15 active"><label class="pull-right">踏勘日期:</label></td>
							<td class="width-35">
								${vo.tkDate}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${logVo.msg}
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
