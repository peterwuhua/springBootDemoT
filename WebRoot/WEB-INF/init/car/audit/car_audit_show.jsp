<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm"  enctype="multipart/form-data">
					<input name="id" value="${vo.id}" type="hidden" />
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">车辆编号:</label></td>
								<td class="width-35">
									${vo.code}
								</td>
								<td class="width-15 active"><label class="pull-right">项目编号:</label></td>
								<td class="width-35">
									${vo.busNo}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">去&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向:</label></td>
								<td>
									${vo.destination}
								</td>
								<td class="active"><label class="pull-right">出差人数:</label></td>
								<td>
		                           		${vo.destRynum}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">申请人员:</label></td>
								<td>
									${vo.userName}
								</td>
								<td class="active"><label class="pull-right">申请时间:</label></td>
								<td>
									${vo.date}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">使用时间:</label></td>
								<td>
									${vo.startTime}
								</td>
								<td class="active"><label class="pull-right">预计归还时间:</label></td>
								<td>
									${vo.endTime}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;途:</label></td>
								<td colspan="3">
									${vo.content}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									${vo.remark}
								</td>
							</tr>
						</tbody>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="active"><label class="pull-right">审核意见:</label></td>
								<td>
									${vo.auditMsg}
								</td>
								<td class="active"><label class="pull-right">审核结果:</label></td>
								<td>
									${vo.auditResult}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">审&nbsp;&nbsp;核&nbsp;人:</label></td>
								<td>
									${vo.auditName}
								</td>
								<td class="active"><label class="pull-right">审核日期:</label></td>
								<td>
									${vo.auditDate}
								</td>
							</tr>
						 
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
</body>
</html>
