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
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					  <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">项目编号:</label></td>
								<td class="width-35">
									${vo.no }
								</td>
								<td class="width-15 active"><label class="pull-right">项目名称:</label></td>
								<td class="width-35">
									${vo.title }
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">成&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本:</label></td>
								<td>
									${vo.price }
								</td>
								<td class="active"><label class="pull-right">工&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时:</label></td>
								<td>
									${vo.hours }
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容:</label></td>
								<td colspan="3">
									${vo.content}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;员:</label></td>
								<td>
									${vo.userNames}
								</td>
								<td class="active"><label class="pull-right">申请日期:</label></td>
								<td>
									${vo.sdate}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">审&nbsp;&nbsp;核&nbsp;人:</label></td>
								<td>
									${vo.auditName}
								</td>
								<td class="active"><label class="pull-right">抄&nbsp;&nbsp;送&nbsp;人:</label></td>
								<td>
									${vo.viewNames}
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
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
</body>
</html>
