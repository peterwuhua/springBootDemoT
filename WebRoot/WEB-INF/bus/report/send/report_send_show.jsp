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
.btn-group>button{
	margin-right: 10px;
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
								<a href="javascript:void(0);" onclick="fnTask('${vo.taskVo.id}','${vo.taskVo.no}');">${vo.taskVo.no}</a>
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
							<td class="width-15 active"><label class="pull-right">联&nbsp;系&nbsp;&nbsp;人:</label></td>
							<td class="width-35">
								${vo.custVo.custUser}
							</td>
							<td class="width-15 active"><label class="pull-right">固定电话:</label></td>
							<td class="width-35">
								${vo.custVo.custTel}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">样品类别:</label></td>
							<td>
								${vo.sampTypeName}
							</td>
							<td class="active"><label class="pull-right">样品名称:</label></td>
							<td>
								${vo.sampName}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">报告日期:</label></td>
							<td>
								${vo.reportDate}
							</td>
							<td class="active"><label class="pull-right">要求完成日期:</label></td>
							<td>
								${vo.finishDate}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.remark}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">报告信息:</label></td>
							<td colspan="3">
			                  <div class="btn-group" id="editReprt">
		                  	 		<button class="btn btn-info" type="button" onclick="fnShowReport('');">在线查看报告</button>
		                       		<c:if test="${vo.taskType=='定期检测'}">
		                       			<button class="btn btn-info" type="button" onclick="fnShowReport('dq');">在线查看报告(总)</button>
		                       		</c:if>
			                    </div>
							</td>
						</tr>
					</table>
					<fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
						<legend>发放信息</legend>
					</fieldset>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="active"><label class="pull-right">发放方式:</label></td>
								<td>
									${vo.sendType}
								</td>
								<td class="active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
								<td>
									${vo.toUser}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">其&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;他:</label></td>
								<td colspan="3">
									${vo.demo}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">发&nbsp;放&nbsp;&nbsp;人:</label></td>
								<td>
									${vo.sendUser}
								</td>
								<td class="width-15 active"><label class="pull-right">发放日期:</label></td>
								<td>
									${vo.sendDate}
								</td>
							</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
     <script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
	<script>
	function fnShowReport(type){
		POBrowser.openWindow('${basePath}bus/report/showReport.do?id=${vo.id}&uuid='+type,'width=1200px;height=800px;');
	}
	</script>
</body>
</html>
