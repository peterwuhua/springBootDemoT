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
				<form method="post"  class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					  <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">项目编号:</label></td>
								<td class="width-35">
									${vo.busNo}
								</td>
								<td class="width-15 active"><label class="pull-right">车辆编号:</label></td>
								<td class="width-35">
									${vo.code}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">去&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向:</label></td>
								<td class="width-35">
										${vo.destination}
								</td>
								<td class="width-15 active"><label class="pull-right">出差人数:</label></td>
								<td class="width-35">
			                           	${vo.destRynum}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">申请人员:</label></td>
								<td class="width-35">
									${vo.userName}
								</td>
								<td class="width-15 active"><label class="pull-right">申请时间:</label></td>
								<td class="width-35">
									${vo.date}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">使用时间:</label></td>
								<td class="width-35">
									${vo.startTime}
								</td>
								<td class="width-15 active"><label class="pull-right">预计归还时间:</label></td>
								<td class="width-35">
									${vo.endTime}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;途:</label></td>
								<td colspan="3">
									${vo.content}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									${vo.remark}
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
