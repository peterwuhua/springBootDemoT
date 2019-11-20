<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
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
								<td class="width-15 active"><label class="pull-right">类型:</label></td>
								<td class="width-35">
									${vo.type}
								</td>
								<td class="width-15 active"><label class="pull-right">补卡人:</label></td>
								<td class="width-35">
									${vo.userName}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">时间:</label></td>
								<td>
									${vo.date}
								</td>
								<td class="active"><label class="pull-right">描述:</label></td>
								<td>
									${vo.content}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备注:</label></td>
								<td colspan="3">
									${vo.remark}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">审&nbsp;&nbsp;核&nbsp;人:</label></td>
								<td>
									${vo.auditName}
								</td>
								<td class="active"><label class="pull-right">审核时间:</label></td>
								<td>
									${vo.auditDate}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">审核结果:</label></td>
								<td>
									${vo.auditResult}
								</td>
								<td class="active"><label class="pull-right">审核意见:</label></td>
								<td>
									${vo.auditMsg}
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
</body>
</html>
